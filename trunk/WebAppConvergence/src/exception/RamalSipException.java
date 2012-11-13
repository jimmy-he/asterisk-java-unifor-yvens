package exception;

/**
 * Exceção para erros e exceções gerais para o CRUD de Ramais do SIP
 * @author yvens
 *
 */
public class RamalSipException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1229634001768418865L;
	
	public RamalSipException(){
		this("Tag escolhida já existe!");
	}
	
	public RamalSipException(String msg){
		super(msg);
	}

}
