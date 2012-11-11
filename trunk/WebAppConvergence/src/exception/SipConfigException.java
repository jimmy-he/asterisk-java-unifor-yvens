package exception;

/**
 * Exceção para quando o arquivo sip.conf passado for lido e retornar um vetor de Strings com lenght = 0
 * @author yvens
 *
 */
public class SipConfigException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1985940370669325224L;

	public SipConfigException(){
		super("Arquivo de sip.conf está vazio!");
	}
}
