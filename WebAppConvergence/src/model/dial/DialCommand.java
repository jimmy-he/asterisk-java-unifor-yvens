package model.dial;

/**
 * Classe que representa os comandos de uma rota do plano de discagem
 * 
 * Exemplo:
 * exten => *35,1,Answer()                    <-- Order = 1; Command = Answer()
 * 
 * O id serve como identificador para alterações
 * 
 * @author yvens
 *
 */
public class DialCommand implements Comparable<DialCommand>{

	private int id;
	private int order;
	private String command;
	
	public DialCommand(int id, int order, String command) {
		super();
		this.id = id;
		this.order = order;
		this.command = command;
	}
	
	public void incrementOrder(){
		incrementOrder(1);
	}
	
	public void incrementOrder(int increment){
		this.order += increment;
	}
	
	public void decrementOrder(){
		decrementOrder(1);
	}

	public void decrementOrder(int decrement){
		this.order -= decrement;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	 * 
	 * Caso a ordem seja 1, é escrito no formato: 1, command
	 * Caso a ordem seja diferente de 1, escrito: n, command
	 * 
	 * @return order, command
	 */
	public String toString(){
		return ((order == 1)? "1" : "n")+","+command;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		return result;
	}

	@Override
	/**
	 * Compara os valores do command
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DialCommand other = (DialCommand) obj;
		if (command == null) {
			if (other.command != null)
				return false;
		} else if (!command.equals(other.command))
			return false;
		return true;
	}

	@Override
	/**
	 * Método de comparação, comparando os comandos pela sua ordem
	 */
	public int compareTo(DialCommand o) {
		if(o.order == this.order){
			return 0;
		}
		return (o.order > this.order)? -1 : 1;
	}
	
}
