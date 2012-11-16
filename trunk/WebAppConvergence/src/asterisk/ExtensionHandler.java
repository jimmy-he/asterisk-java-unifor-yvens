package asterisk;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;

import model.dial.DialPlan;
import persistence.FileHandler;
import exception.ExtensionsConfigException;

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
		
		if (!iaxConfCertified()) {
			throw new ExtensionsConfigException();
		}
	}
	
	
	/**
	 * Método para buscar a lista de planos de discagens do arquivo extensions.conf
	 * 
	 * @return retorna um arraylist de dialplan
	 */
	public List<DialPlan> listDialPlan(){
		return null;
	}

	/**
	 * Método para fazer a verificação do PATH do iax.conf passado
	 * 
	 * @return verdadeiro caso seja realizada uma leitura com sucesso do arquivo
	 *         do PATH passado
	 * @throws IOException
	 */
	private boolean iaxConfCertified() throws IOException {
		String[] extensionsConf = fileHandler.readFile(extensionsConfPath);
		this.extensionsConfLines = extensionsConf.length;
		return extensionsConf.length > 0;
	}
	
	public int getExtensionsConfLines() {
		return extensionsConfLines;
	}
}
