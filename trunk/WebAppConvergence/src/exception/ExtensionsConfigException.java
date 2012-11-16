package exception;

/**
 * Exceção para quando o arquivo extensions.conf passado for lido e retornar um vetor
 * de Strings com length = 0
 * 
 * @author yvens
 * 
 */
public class ExtensionsConfigException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3366923218004582828L;

	public ExtensionsConfigException(){
		this("Arquivo de extensions.conf está vazio!");
	}
	
	public ExtensionsConfigException(String message){
		super(message);
	}
	
}
