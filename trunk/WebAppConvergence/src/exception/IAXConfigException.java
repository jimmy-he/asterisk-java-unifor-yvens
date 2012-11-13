package exception;

/**
 * Exceção para quando o arquivo iax.conf passado for lido e retornar um vetor
 * de Strings com length = 0
 * 
 * @author daniel
 * 
 */
public class IAXConfigException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1985940370669325224L;

	public IAXConfigException() {
		super("Arquivo de iax.conf está vazio!");
	}
}