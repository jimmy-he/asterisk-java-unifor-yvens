package asterisk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import model.dial.DialCommand;
import model.dial.DialPlan;
import model.dial.DialRoute;
import persistence.FileHandler;
import exception.ExtensionsConfigException;
import exception.SipConfigException;

/**
 * Classe responsável por lidar com o arquivo extensions.conf
 * 
 * @author yvens
 * 
 */
public class ExtensionHandler {
	private FileHandler fileHandler;
	private String extensionsConfPath;
	private int extensionsConfLines;

	// Semáforo para evitar modificações concorrentes nos arquivos de extensões
	private static final Semaphore mutex;
	static {
		mutex = new Semaphore(1);
	}

	public ExtensionHandler() throws IOException, ExtensionsConfigException {
		this(AsteriskConfiguration.EXTENSIONS_CONFIG_PATH);
	}

	public ExtensionHandler(String extensionsConfPath) throws IOException,
			ExtensionsConfigException {
		this.extensionsConfPath = extensionsConfPath;
		fileHandler = new FileHandler();

		if (!extensionsConfCertified()) {
			throw new ExtensionsConfigException();
		}
	}

	/**
	 * Método para a inserção de um plano de discagem dentro do arquivo
	 * extensions.conf do servidor asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a inserção, e falso caso
	 *         alguma outra instância esteja acessando e modificando o arquivo
	 *         no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws ExtensionsConfigException
	 * @throws SipConfigException
	 */
	public boolean insertDialPlan(DialPlan dial) throws IOException,
			ExtensionsConfigException {
		if (mutex.tryAcquire()) {

			// Lê o arquivo extensions.conf
			String[] extensionsFile = fileHandler.readFile(extensionsConfPath);

			// Busca pela tag que queremos adicionar
			for (int i = 0; i < extensionsFile.length; i++) {
				// Lança uma exceção caso já exista a tag enviada
				if (extensionsFile[i].equals("[" + dial.getTag() + "]")) {
					throw new ExtensionsConfigException(
							"Tag escolhida já existe!");
				}
			}

			// Gera as linhas que devem ser adicionadas ao extensions.conf
			String[] dialPlan = dial.toDialPlan();

			// Escreve as linhas no arquivo extensions.conf
			fileHandler.writeOnFile(extensionsFile, dialPlan,
					extensionsFile.length);

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
	public boolean updateDialPlan(DialPlan dial) throws IOException {
		if (mutex.tryAcquire()) {

			// Lê o arquivo extensions.conf
			String[] extensionsFile = fileHandler.readFile(extensionsConfPath);

			// Busca pelas linhas que contém o ramal que deve ser alterado
			boolean dialFound = false;
			int i = 0;
			int begin = -1;
			int end = -1;
			while (!dialFound && i < extensionsFile.length) {

				// Procuramos pela linha que contenha a tag do dial desejado
				// E pegamos a posição inicial do ramal e a linha em que ele
				// termina
				if (extensionsFile[i].equals("[" + dial.getTag() + "]")) {
					begin = i;
					i++;
					while (i < extensionsFile.length) {
						if (extensionsFile[i].equals("") == false) {
							if (extensionsFile[i].charAt(0) == '[') {
								break;
							}
						}

						i++;
					}
					if (i == extensionsFile.length) {
						i--;
					}
					end = i;

					dialFound = true;
				}
				i++;

			}			

			// Apagado o ramal do arquivo extensions.conf
			fileHandler.deleteLineOnFile(extensionsFile, begin, end);

			// Atualiza o arquivo extensions.conf
			extensionsFile = fileHandler.readFile(extensionsConfPath);

			String[] dialPlan = dial.toDialPlan();

			// Escreve as linhas no arquivo extensions.conf
			fileHandler.writeOnFile(extensionsFile, dialPlan,
					extensionsFile.length);

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
	public boolean deleteDialPlan(DialPlan dial) throws IOException {
		if (mutex.tryAcquire()) {

			// Lê o arquivo extensions.conf
			String[] extensionsFile = fileHandler.readFile(extensionsConfPath);

			// Busca pelas linhas que contém o ramal que deve ser alterado
			boolean dialFound = false;
			int i = 0;
			int begin = -1;
			int end = -1;
			while (!dialFound && i < extensionsFile.length) {

				// Procuramos pela linha que contenha a tag do dial desejado
				// E pegamos a posição inicial do ramal e a linha em que ele
				// termina
				if (extensionsFile[i].equals("[" + dial.getTag() + "]")) {
					begin = i;
					i++;
					while (i < extensionsFile.length) {
						if (!extensionsFile[i].equals("")) {
							if (extensionsFile[i].charAt(0) == '[') {
								break;
							}
						}

						i++;
					}
					if (i == extensionsFile.length) {
						i--;
					}
					end = i;

					dialFound = true;
				}
				i++;
			}
			
			// Apagado o ramal do arquivo extensions.conf
			fileHandler.deleteLineOnFile(extensionsFile, begin, end);
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
	public List<DialPlan> listDialPlan() throws IOException {
		String[] extensionsFile = fileHandler.readFile(extensionsConfPath);
		List<DialPlan> listDialPlan = new ArrayList<DialPlan>();

		for (int i = 0; i < extensionsFile.length; i++) {
			if (!extensionsFile[i].isEmpty()
					&& extensionsFile[i].charAt(0) == '['
					&& !extensionsFile[i].equals("[general]")) {
				DialPlan dialPlan = null;

				String tag = extensionsFile[i];
				tag = tag.substring(1, tag.length() - 1);
				dialPlan = new DialPlan(tag);
				i++;

				while (i < extensionsFile.length
						&& (extensionsFile[i].isEmpty() || extensionsFile[i]
								.charAt(0) != '[')) {
					if (extensionsFile[i].isEmpty()) {
						i++;
						continue;
					}

					DialRoute dialRoute = null;

					String line = extensionsFile[i].trim();

					// Para remover o "exten=>"
					line = line.substring(9);

					// Não pode dar split ilimitado pois pode haver vírgulas
					// dentro de um comando
					String[] parameters = line.split(",", 3);

					// Primeiro índice
					String identifier = parameters[0];

					dialRoute = new DialRoute(identifier);
					int order = 1;
					do {
						DialCommand command = null;
						String commandTxt = parameters[2];
						
						//Durante a leitura, usamos a própria order como id do comando
						command = new DialCommand(order, order, commandTxt);

						dialRoute.addCommand(command);

						i++;
						order++;
						if (i >= extensionsFile.length) {
							break;
						}
						line = extensionsFile[i];

						if (!line.isEmpty() && line.length() > 9) {
							line = extensionsFile[i].trim();
							// Para remover o "exten=>"
							line = line.substring(9);

							parameters = line.split(",", 3);
						}
					} while (!line.isEmpty() && line.length() > 9
							&& parameters[0].equals(identifier));

					dialPlan.addRoute(dialRoute);
				}

				listDialPlan.add(dialPlan);
				i--;
			}
		}

		return listDialPlan;
	}
	
	public DialPlan getDialPlan(String tag) throws IOException, ExtensionsConfigException{
		List<DialPlan> list = listDialPlan();
		
		DialPlan dialPlan = null;
		
		for (DialPlan plan : list) {
			if(plan.getTag().equals(tag)){
				dialPlan = plan;
			}
		}
		
		if(dialPlan == null){
			throw new ExtensionsConfigException("Erro! Plano de Discagem não encontrado!Tag = "+tag);
		}
		
		return dialPlan;
	}

	/**
	 * Método para fazer a verificação do PATH do extensions.conf passado
	 * 
	 * @return verdadeiro caso seja realizada uma leitura com sucesso do arquivo
	 *         do PATH passado
	 * @throws IOException
	 */
	private boolean extensionsConfCertified() throws IOException {
		String[] extensionsConf = fileHandler.readFile(extensionsConfPath);
		this.extensionsConfLines = extensionsConf.length;
		return extensionsConf.length > 0;
	}

	public int getExtensionsConfLines() {
		return extensionsConfLines;
	}
}
