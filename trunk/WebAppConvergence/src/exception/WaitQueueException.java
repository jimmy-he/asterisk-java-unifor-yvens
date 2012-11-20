package exception;

public class WaitQueueException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3784710951072257283L;

	public WaitQueueException(){
		this("Arquivo de queues.conf está vazio!");
	}
	
	public WaitQueueException(String message){
		super(message);
	}
}
