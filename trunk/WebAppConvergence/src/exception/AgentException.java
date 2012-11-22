package exception;

public class AgentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4029827794357443210L;

	public AgentException() {
		this("Código escolhido já existe!");
	}

	public AgentException(String msg) {
		super(msg);
	}
}
