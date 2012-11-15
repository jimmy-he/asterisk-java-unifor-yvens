package asterisk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import model.RamalSip;
import model.RamalSipType;
import persistence.FileHandler;
import exception.RamalSipException;
import exception.SipConfigException;

/**
 * Classe responsável por realizar todas as operações relativas aos ramais, como
 * criação, alteração, listagem e deleção de ramais.
 * 
 * @author yvens
 * 
 */
public class RamalSipHandler {

	private FileHandler fileHandler;
	private String sipConfPath;
	private int sipConfLines;

	// Semáforo para evitar modificações concorrentes nos arquivos do ramal
	private static final Semaphore mutex;
	static {
		mutex = new Semaphore(1);
	}

	public RamalSipHandler() throws IOException, SipConfigException {
		this(AsteriskConfiguration.SIP_CONFIG_PATH);
	}

	public RamalSipHandler(String sipConfPath) throws IOException, SipConfigException {
		this.sipConfPath = sipConfPath;
		fileHandler = new FileHandler();
		if (!sipConfCertified()) {
			throw new SipConfigException();
		}
	}

	/**
	 * Método para fazer a verificação do PATH do sip.conf passado
	 * 
	 * @return verdadeiro caso seja realizada uma leitura com sucesso do arquivo
	 *         do PATH passado
	 * @throws IOException
	 */
	private boolean sipConfCertified() throws IOException {
		String[] sipConf = fileHandler.readFile(sipConfPath);
		this.sipConfLines = sipConf.length;
		return sipConf.length > 0;
	}

	/**
	 * Método para a criação de um ramal dentro do arquivo sip.conf do servidor
	 * asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a inserção, e falso caso
	 *         alguma outra instância esteja acessando e modificando o arquivo
	 *         no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws SipConfigException
	 */
	public boolean createRamal(RamalSip ramal) throws InterruptedException, IOException, RamalSipException {
		if (mutex.tryAcquire()) {

			// Lê o arquivo sip.conf
			String[] sipConf = fileHandler.readFile(sipConfPath);

			// Busca pela tag que queremos adicionar
			for (int i = 0; i < sipConf.length; i++) {
				// Lança uma exceção caso já exista a tag enviada
				if (sipConf[i].equals("[" + ramal.getTag() + "]")) {
					throw new RamalSipException();
				}
			}

			// Gera as linhas que devem ser adicionadas ao sip.conf
			String[] newRamal = ramal.toRamalSip();

			// Escreve as linhas no arquivo sip.conf
			fileHandler.writeOnFile(sipConf, newRamal, sipConf.length);

			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para a deleção de um ramal dentro do arquivo sip.conf do servidor
	 * asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a deleção, e falso caso
	 *         alguma outra instância esteja acessando e modificando o arquivo
	 *         no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws RamalSipException
	 */
	public boolean deleteRamal(RamalSip ramal) throws InterruptedException,
			IOException, RamalSipException {
		if (mutex.tryAcquire()) {

			// Lê o arquivo sip.conf
			String[] sipConf = fileHandler.readFile(sipConfPath);

			// Busca pelas linhas que contém o ramal que deve ser alterado
			boolean ramalFound = false;
			int i = 0;
			int begin = -1;
			int end = -1;
			while (!ramalFound && i < sipConf.length) {

				// Procuramos pela linha que contenha a tag do ramal desejado
				// E pegamos a posição inicial do ramal e a linha em que ele
				// termina
				if (sipConf[i].equals("[" + ramal.getTag() + "]")) {
					begin = i;

					while (i < sipConf.length && !sipConf[i].equals("")) {
						i++;
					}
					if (i == sipConf.length) {
						i--;
					}
					end = i;

					ramalFound = true;
				}
				i++;
			}

			// Apagado o ramal do arquivo sip.conf
			fileHandler.deleteLineOnFile(sipConf, begin, end);

			// Caso não tenha sido encontrado o ramal, é lançada uma exceção
			if (begin == -1 || end == -1) {
				throw new RamalSipException(
						"Não existe o Ramal passado. Tag = " + ramal.getTag());
			}

			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para a alteração de um ramal dentro do arquivo sip.conf do
	 * servidor asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a alteração, e falso
	 *         caso alguma outra instância esteja acessando e modificando o
	 *         arquivo no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public boolean updateRamal(RamalSip ramal) throws InterruptedException,
			IOException {
		if (mutex.tryAcquire()) {

			// Lê o arquivo sip.conf
			String[] sipConf = fileHandler.readFile(sipConfPath);
			String[] updatedRamal = ramal.toRamalSip();

			// Busca pelas linhas que contém o ramal que deve ser alterado
			boolean ramalFound = false;
			int i = 0;
			int begin = -1;
			int end = -1;
			while (!ramalFound && i < sipConf.length) {

				// Procuramos pela linha que contenha a tag do ramal desejado
				// E pegamos a posição inicial do ramal e a linha em que ele
				// termina
				if (sipConf[i].equals("[" + ramal.getTag() + "]")) {
					begin = i;

					while (i < sipConf.length && !sipConf[i].equals("")) {
						i++;
					}
					if (i == sipConf.length) {
						i--;
					}
					end = i;

					ramalFound = true;
				}
				i++;
			}

			// Apagado o ramal do arquivo sip.conf
			fileHandler.deleteLineOnFile(sipConf, begin, end);

			// Atualiza o valor do vetor do sip.conf
			sipConf = fileHandler.readFile(sipConfPath);

			// Adicionado o ramal atualizado no final do arquivo
			fileHandler.writeOnFile(sipConf, updatedRamal, sipConf.length);

			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para buscar a lista de todos os ramais do arquivo sip.conf
	 * 
	 * Esse método acontece mesmo que alguém esteja modificando o arquivo
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<RamalSip> listRamal() throws IOException {
		String[] sipConfFile = fileHandler.readFile(sipConfPath);
		List<RamalSip> listRamal = new ArrayList<RamalSip>();

		for (int i = 0; i < sipConfFile.length; i++) {
			// Verificando se acha uma TAG no estilo [4666]
			if (!sipConfFile[i].isEmpty() && sipConfFile[i].charAt(0) == '['
					&& !sipConfFile[i].equals("[general]")) {
				RamalSip ramal = null;

				// 1 - TAG
				String tag = sipConfFile[i];
				tag = tag.substring(1, tag.length() - 1);
				i++;

				// Declaração das variáveis do RAMAL
				String callerId = "";
				RamalSipType type = null;
				String accountCode = null;
				String username = null;
				String secret = null;
				boolean canReinvite = false;
				String host = null;
				String context = null;
				String dtmfMode = null;
				int callLimit = 0;
				boolean nat = false;

				while (i < sipConfFile.length && !sipConfFile[i].equals("")
						&& !sipConfFile[i].isEmpty()
						&& sipConfFile[i].charAt(0) != '[') {

					String parameters[] = sipConfFile[i++].split("=");

					if (parameters[0].equals("callerid")) {
						callerId = parameters[1];
					} else if (parameters[0].equals("type")) {
						type = RamalSipType.getRamalType(parameters[1]);
					} else if (parameters[0].equals("accountcode")) {
						accountCode = parameters[1];
					} else if (parameters[0].equals("username")) {
						username = parameters[1];
					} else if (parameters[0].equals("secret")) {
						secret = parameters[1];
					} else if (parameters[0].equals("canreinvite")) {
						canReinvite = (parameters[1].equals("yes")) ? true : false;
					} else if (parameters[0].equals("host")) {
						host = parameters[1];
					} else if (parameters[0].equals("context")) {
						context = parameters[1];
					} else if (parameters[0].equals("dtmfmode")) {
						dtmfMode = parameters[1];
					} else if (parameters[0].equals("call-limit")) {
						callLimit = Integer.parseInt(parameters[1]);
					} else if (parameters[0].equals("nat")) {
						nat = (parameters[1].equals("yes")) ? true : false;
					}
				}
				ramal = new RamalSip(tag, callerId, type, username, secret,
						canReinvite, host, context, dtmfMode, accountCode,
						callLimit, nat);
				listRamal.add(ramal);

				i--;
			}
		}
		return listRamal;
	}
	
	/**
	 * Método para buscar o ramal sip com determinado tag
	 * 
	 * @param tag
	 * @return retorna o ramal com a tag passada
	 * @throws IOException
	 * @throws RamalSipException
	 */
	public RamalSip getRamal(String tag) throws IOException, RamalSipException{
		List<RamalSip> list = listRamal();
		
		RamalSip ramal = null;
		
		for (RamalSip ramalSip : list) {
			if(ramalSip.getTag().equals(tag)){
				ramal = ramalSip;
			}
		}
		
		if(ramal == null){
			throw new RamalSipException("Erro! Ramal não encontrado!");
		}
		
		return ramal;
	}

	public int getSipConfLines() {
		return sipConfLines;
	}
}
