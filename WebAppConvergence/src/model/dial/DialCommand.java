package model.dial;

/**
 * Classe que representa os comandos de uma rota do plano de discagem
 * 
 * Exemplo:
 * exten => *35,1,Answer()                    <-- Order = 1; Command = Answer()  
 * @author yvens
 *
 */
public class DialCommand implements Comparable<DialCommand>{

	private int order;
	private String command;
	
	public DialCommand(int order, String command) {
		super();
		this.order = order;
		this.command = command;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	/**
	 * Método para escrever o dialcommand como uma String
	 * @return order, command
	 */
	public String toString(){
		return order+","+command;
	}
	
	@Override
	/**
	 * Método de comparação, comparando os comandos pela sua ordem
	 */
	public int compareTo(DialCommand o) {
		if(o.order == this.order){
			return 0;
		}
		return (o.order > this.order)? 1 : -1;
	}
	
}
