package asterisk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import model.queues.QueueCommand;
import model.queues.WaitQueue;
import persistence.FileHandler;
import exception.ExtensionsConfigException;
import exception.SipConfigException;
import exception.WaitQueueException;

/**
 * Classe responsável por lidar com o arquivo queues.conf
 * 
 * @author yvens
 * 
 */

//TODO mudar os comentários
public class WaitQueueHandler {
	private FileHandler fileHandler;
	private String queueConfPath;
	private int queueConfLines;

	// Semáforo para evitar modificações concorrentes nos arquivos de extensões
	private static final Semaphore mutex;
	static {
		mutex = new Semaphore(1);
	}

	public WaitQueueHandler() throws IOException, WaitQueueException {
		this(AsteriskConfiguration.QUEUES_CONFIG_PATH);
	}

	public WaitQueueHandler(String queueConfPath) throws IOException, WaitQueueException {
		this.queueConfPath = queueConfPath;
		fileHandler = new FileHandler();

		if (!queueConfCertified()) {
			throw new WaitQueueException();
		}
	}

	/**
	 *
	 * Método para a inserção de um plano de discagem dentro do arquivo
	 * extensions.conf do servidor asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a inserção, e falso caso
	 *         alguma outra instância esteja acessando e modificando o arquivo
	 *         no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws WaitQueueException 
	 * @throws ExtensionsConfigException
	 * @throws SipConfigException
	 */
	public boolean insertWaitQueue(WaitQueue waitQueue) throws IOException, WaitQueueException {
		if (mutex.tryAcquire()) {

			// Lê o arquivo extensions.conf
			String[] queueFile = fileHandler.readFile(queueConfPath);

			// Busca pela tag que queremos adicionar
			for (int i = 0; i < queueFile.length; i++) {
				// Lança uma exceção caso já exista a tag enviada
				if (queueFile[i].equals("[" + waitQueue.getTag() + "]")) {
					throw new WaitQueueException("Tag escolhida já existe!");
				}
			}

			// Gera as linhas que devem ser adicionadas ao extensions.conf
			String[] queuePlan = waitQueue.toWaitQueue();

			// Escreve as linhas no arquivo extensions.conf
			fileHandler.writeOnFile(queueFile, queuePlan, queueFile.length);
			
			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para a atualização de um plano de discagem dentro do arquivo
	 * extensions.conf do servidor asterisk Esse método é usado para qualquer
	 * atualização do plano de discagem, desde alteração na tag, alteração nas
	 * rotas e alteração nos comandos das rotas
	 * 
	 * @return retorna verdadeiro caso consiga realizar a inserção, e falso caso
	 *         alguma outra instância esteja acessando e modificando o arquivo
	 *         no momento, assim, evitando conflito entre os arquivos
	 * 
	 * @throws IOException
	 */
	public boolean updateWaitQueue(WaitQueue waitQueue) throws IOException {
		if (mutex.tryAcquire()) {

			// Lê o arquivo extensions.conf
			String[] queueFile = fileHandler.readFile(queueConfPath);

			// Busca pelas linhas que contém a fila que deve ser alterada
			boolean queueFound = false;
			int i = 0;
			int begin = -1;
			int end = -1;
			while (!queueFound && i < queueFile.length) {

				// Procuramos pela linha que contenha a tag da fila desejada
				// E pegamos a posição inicial do ramal e a linha em que ele
				// termina
				if (queueFile[i].equals("[" + waitQueue.getTag() + "]")) {
					begin = i;
					i++;
					while (i < queueFile.length) {
						if (queueFile[i].equals("") == false) {
							if (queueFile[i].charAt(0) == '[') {
								break;
							}
						}

						i++;
					}
					if (i == queueFile.length) {
						i--;
					}
					end = i;

					queueFound = true;
				}
				i++;

			}			

			// Apagado o ramal do arquivo queues.conf
			fileHandler.deleteLineOnFile(queueFile, begin, end);

			// Atualiza o arquivo queues.conf
			queueFile = fileHandler.readFile(queueConfPath);

			String[] newWaitQueue = waitQueue.toWaitQueue();

			// Escreve as linhas no arquivo queues.conf
			fileHandler.writeOnFile(queueFile, newWaitQueue, queueFile.length);

			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para a remoção de um plano de discagem dentro do arquivo
	 * extensions.conf do servidor asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a inserção, e falso caso
	 *         alguma outra instância esteja acessando e modificando o arquivo
	 *         no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws SipConfigException
	 */
	public boolean deleteWaitQueue(WaitQueue waitQueue) throws IOException {
		if (mutex.tryAcquire()) {
			
			// Lê o arquivo queues.conf
			String[] queueFile = fileHandler.readFile(queueConfPath);

			// Busca pelas linhas que contém a fila que deve ser alterada
			boolean queueFound = false;
			int i = 0;
			int begin = -1;
			int end = -1;
			while (!queueFound && i < queueFile.length) {

				// Procuramos pela linha que contenha a tag da fila desejado
				// E pegamos a posição inicial do ramal e a linha em que ele
				// termina
				if (queueFile[i].equals("[" + waitQueue.getTag() + "]")) {
					begin = i;
					i++;
					while (i < queueFile.length) {
						if (!queueFile[i].equals("")) {
							if (queueFile[i].charAt(0) == '[') {
								break;
							}
						}

						i++;
					}
					if (i == queueFile.length) {
						i--;
					}
					end = i;

					queueFound = true;
				}
				i++;
			}
			
			// Apagado o ramal do arquivo queues.conf
			fileHandler.deleteLineOnFile(queueFile, begin, end);
			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para buscar a lista de planos de discagens do arquivo
	 * extensions.conf
	 * 
	 * @return retorna um arraylist de dialplan
	 * @throws IOException
	 */
	public List<WaitQueue> listWaitQueue() throws IOException {
		String[] queueFile = fileHandler.readFile(queueConfPath);
		List<WaitQueue> listWaitQueues = new ArrayList<WaitQueue>();

		for(int i = 0; i < queueFile.length; i++){
			if(!queueFile[i].isEmpty()&& queueFile[i].charAt(0) == '['
					&& !queueFile[i].equals("[general]")) {
				WaitQueue waitQueue = null;
				
				String tag = queueFile[i];
				tag = tag.substring(1, tag.length() - 1);
				
				//Usamos o próprio I como id
				waitQueue = new WaitQueue(i, tag);
				i++;
				
				int id = 1;
				while(i < queueFile.length && (queueFile[i].isEmpty() || queueFile[i] .charAt(0) != '[')) {
					if (queueFile[i].isEmpty()) {
						i++;
						continue;
					}
					
					QueueCommand queueCommand = null;
					
					String line = queueFile[i];
					queueCommand = new QueueCommand(id, id, line);
					
					waitQueue.addCommand(queueCommand);
					id++;
					i++;
				}
				
				listWaitQueues.add(waitQueue);
				i--;
			}
		}

		return listWaitQueues;
	}
	
	public WaitQueue getWaitQueue(String tag) throws IOException, WaitQueueException{
		List<WaitQueue> list = listWaitQueue();
		
		WaitQueue waitQueue = null;
		
		for (WaitQueue queue : list) {
			if(queue.getTag().equals(tag)){
				waitQueue = queue;
			}
		}
		
		if(waitQueue == null){
			throw new WaitQueueException("Erro! Plano de Discagem não encontrado!Tag = "+tag);
		}
		
		return waitQueue;
	}

	/**
	 * Método para fazer a verificação do PATH do queue.conf passado
	 * 
	 * @return verdadeiro caso seja realizada uma leitura com sucesso do arquivo
	 *         do PATH passado
	 * @throws IOException
	 */
	private boolean queueConfCertified() throws IOException {
		String[] queueConf = fileHandler.readFile(queueConfPath);
		this.queueConfLines = queueConf.length;
		return queueConf.length > 0;
	}

	public int getQueueConfLines() {
		return queueConfLines;
	}
}
