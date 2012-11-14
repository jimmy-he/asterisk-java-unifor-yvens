package asterisk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import model.RamalIAX;
import model.RamalIAXType;

import persistence.FileHandler;
import exception.IAXConfigException;
import exception.RamalIAXException;

/**
 * Classe responsável por realizar todas as operações relativas aos ramais IAX,
 * como criação, alteração, listagem e deleção de ramais.
 * 
 * @author daniel
 * 
 */
public class RamalIAXHandler {

	private FileHandler fileHandler;
	private String iaxConfPath;
	private int iaxConfLines;

	// Semáforo para evitar modificações concorrentes nos arquivos do ramal
	private static final Semaphore mutex;
	static {
		mutex = new Semaphore(1);
	}

	public RamalIAXHandler() throws IOException, IAXConfigException {
		this(AsteriskConfiguration.IAX_CONFIG_PATH);
	}

	public RamalIAXHandler(String iaxConfPath) throws IOException,
			IAXConfigException {
		this.iaxConfPath = iaxConfPath;
		fileHandler = new FileHandler();
		
		if (!iaxConfCertified()) {
			throw new IAXConfigException();
		}
	}

	/**
	 * Método para fazer a verificação do PATH do iax.conf passado
	 * 
	 * @return verdadeiro caso seja realizada uma leitura com sucesso do arquivo
	 *         do PATH passado
	 * @throws IOException
	 */
	private boolean iaxConfCertified() throws IOException {
		String[] iaxConf = fileHandler.readFile(iaxConfPath);
		this.iaxConfLines = iaxConf.length;
		return iaxConf.length > 0;
	}

	/**
	 * Método para a criação de um ramal dentro do arquivo iax.conf do servidor
	 * asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a inserção, e falso caso
	 *         alguma outra instância esteja acessando e modificando o arquivo
	 *         no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException
	 */
	public boolean createRamal(RamalIAX ramal) throws InterruptedException,
			IOException, RamalIAXException {
		if (mutex.tryAcquire()) {

			// Lê o arquivo iax.conf
			String[] iaxConf = fileHandler.readFile(iaxConfPath);

			// Busca pela tag que queremos adicionar
			for (int i = 0; i < iaxConf.length; i++) {
				// Lança uma exceção caso já exista a tag enviada
				if (iaxConf[i].equals("[" + ramal.getTag() + "]")) {
					throw new RamalIAXException();
				}
			}

			// Gera as linhas que devem ser adicionadas ao iax.conf
			String[] newRamal = ramal.toRamalIAX();

			// Escreve as linhas no arquivo iax.conf
			fileHandler.writeOnFile(iaxConf, newRamal, iaxConf.length);

			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para a deleção de um ramal dentro do arquivo iax.conf do servidor
	 * asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a deleção, e falso caso
	 *         alguma outra instância esteja acessando e modificando o arquivo
	 *         no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException
	 */
	public boolean deleteRamal(RamalIAX ramal) throws InterruptedException,
			IOException, RamalIAXException {
		if (mutex.tryAcquire()) {

			// Lê o arquivo iax.conf
			String[] iaxConf = fileHandler.readFile(iaxConfPath);

			// Busca pelas linhas que contém o ramal que deve ser alterado
			boolean ramalFound = false;
			int i = 0;
			int begin = -1;
			int end = -1;
			while (!ramalFound && i < iaxConf.length) {

				// Procuramos pela linha que contenha a tag do ramal desejado
				// E pegamos a posição inicial do ramal e a linha em que ele
				// termina
				if (iaxConf[i].equals("[" + ramal.getTag() + "]")) {
					begin = i;

					while (i < iaxConf.length && !iaxConf[i].equals("")) {
						i++;
					}
					if (i == iaxConf.length) {
						i--;
					}
					end = i;

					ramalFound = true;
				}
				i++;
			}

			// Apagado o ramal do arquivo iax.conf
			fileHandler.deleteLineOnFile(iaxConf, begin, end);

			// Caso não tenha sido encontrado o ramal, é lançada uma exceção
			if (begin == -1 || end == -1) {
				throw new RamalIAXException(
						"Não existe o Ramal passado. Tag = " + ramal.getTag());
			}

			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para a alteração de um ramal dentro do arquivo iax.conf do
	 * servidor asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a alteração, e falso
	 *         caso alguma outra instância esteja acessando e modificando o
	 *         arquivo no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public boolean updateRamal(RamalIAX ramal) throws InterruptedException,
			IOException {
		if (mutex.tryAcquire()) {

			// Lê o arquivo iax.conf
			String[] iaxConf = fileHandler.readFile(iaxConfPath);
			String[] updatedRamal = ramal.toRamalIAX();

			// Busca pelas linhas que contém o ramal que deve ser alterado
			boolean ramalFound = false;
			int i = 0;
			int begin = -1;
			int end = -1;
			while (!ramalFound && i < iaxConf.length) {

				// Procuramos pela linha que contenha a tag do ramal desejado
				// E pegamos a posição inicial do ramal e a linha em que ele
				// termina
				if (iaxConf[i].equals("[" + ramal.getTag() + "]")) {
					begin = i;

					while (i < iaxConf.length && !iaxConf[i].equals("")) {
						i++;
					}
					if (i == iaxConf.length) {
						i--;
					}
					end = i;

					ramalFound = true;
				}
				i++;
			}

			// Apagado o ramal do arquivo iax.conf
			fileHandler.deleteLineOnFile(iaxConf, begin, end);

			// Atualiza o valor do vetor do iax.conf
			iaxConf = fileHandler.readFile(iaxConfPath);

			// Adicionado o ramal atualizado no final do arquivo
			fileHandler.writeOnFile(iaxConf, updatedRamal, iaxConf.length);

			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para buscar a lista de todos os ramais do arquivo iax.conf
	 * 
	 * Esse método acontece mesmo que alguém esteja modificando o arquivo
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<RamalIAX> listRamal() throws IOException {
		String[] iaxConfFile = fileHandler.readFile(iaxConfPath);
		List<RamalIAX> listRamal = new ArrayList<RamalIAX>();

		for (int i = 0; i < iaxConfFile.length; i++) {
			// Verificando se acha uma TAG no estilo [4666]
			if (!iaxConfFile[i].isEmpty() && iaxConfFile[i].charAt(0) == '['
					&& !iaxConfFile[i].equals("[general]")) {
				RamalIAX ramal = null;

				// 1 - TAG
				String tag = iaxConfFile[i];
				tag = tag.substring(1, tag.length() - 1);
				i++;

				// Declaração das variáveis do RAMAL
				String callerId = "";
				RamalIAXType type = null;
				String defaultUser = null;
				String secret = null;
				String context = null;
				String host = null;
				String auth = null;
				boolean transfer = false;
				boolean requireCallToken = false;

				while (i < iaxConfFile.length && !iaxConfFile[i].equals("")
						&& !iaxConfFile[i].isEmpty()
						&& iaxConfFile[i].charAt(0) != '[') {
					String parameters[] = iaxConfFile[i++].split("=");

					if (parameters[0].equals("callerid")) {
						callerId = parameters[1];
					} else if (parameters[0].equals("type")) {
						type = RamalIAXType.getRamalType(iaxConfFile[i++]
								.split("=")[1]);
					} else if (parameters[0].equals("defaultuser")) {
						defaultUser = parameters[1];
					} else if (parameters[0].equals("secret")) {
						secret = parameters[1];
					} else if (parameters[0].equals("host")) {
						host = parameters[1];
					} else if (parameters[0].equals("context")) {
						context = parameters[1];
					} else if (parameters[0].equals("auth")) {
						auth = parameters[1];
					} else if (parameters[0].equals("transfer")) {
						transfer = (parameters[1].equals("yes")) ? true : false;
					} else if (parameters[0].equals("requirecalltoken")) {
						requireCallToken = (parameters[1].equals("yes")) ? true
								: false;
					}

					i++;
				}

				ramal = new RamalIAX(tag, callerId, type, defaultUser, secret,
						context, host, auth, transfer, requireCallToken);

				listRamal.add(ramal);
				i--;
			}
		}
		return listRamal;
	}

	public int getIAXConfLines() {
		return iaxConfLines;
	}
}
