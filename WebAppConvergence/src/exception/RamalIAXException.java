package exception;

/**
 * Exceção para erros e exceções gerais para o CRUD de Ramais do IAX
 * 
 * @author daniel
 * 
 */
public class RamalIAXException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1229634001768418865L;

	public RamalIAXException() {
		this("Tag escolhida já existe!");
	}

	public RamalIAXException(String msg) {
		super(msg);
	}

}