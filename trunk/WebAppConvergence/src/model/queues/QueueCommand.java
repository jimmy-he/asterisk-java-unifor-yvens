package model.queues;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe para representar os comandos de uma fila de espera
 * Comandos possuem um identificador único, um campo, um comando e uma ordem
 * São semelhantes aos comandos dos planos de discagem
 * 
 * O campo indica o campo qual campo da queue corresponde, por exemplo:
 * musicclass, announce, strategy, announce-position
 * 
 * O comando indica o valor para o campo, por exemplo:
 * musicclass = default
 * strategy = ringall
 * 
 * @author yvens
 */
public class QueueCommand implements Comparable<QueueCommand>{

	private int id;
	private int order;
	private String field;
	private String command;

	/**
	 * Esse construtor é para receber uma linha do arquivo, fazer o trim e o split pelo /
	 * e colocar o atributo 0 no field e o atributo 1 no command
	 * 
	 * @param id
	 * @param order
	 * @param line
	 */
	public QueueCommand(int id, int order, String line){
		line = line.trim();
		String[] parameter = line.split("=");
		
		this.id = id;
		this.order = order;
		this.field = parameter[0];
		this.command = parameter[1];
	}
	
	public QueueCommand(int id, int order, String field, String command) {
		super();
		this.id = id;
		this.order = order;
		this.field = field;
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

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	/**
	 * Método para buscar os atributos de um ramal sip que está no request
	 * e retornar o objeto ramal sip
	 * 
	 * @param request
	 * @return o objeto ramalSip que está nos parâmetros do request
	 */
	public static QueueCommand getQueueCommandFromParameter(HttpServletRequest request){
		QueueCommand queueCommand = null;
		
		int id = 0;
		int order = 0;
		String field = "";
		String command = "";
		
		if(request.getParameter("id") != null){
			id = Integer.parseInt(request.getParameter("id"));
		}
		if(request.getParameter("order") != null){
			order = Integer.parseInt(request.getParameter("order"));
		}
		if(request.getParameter("field") != null){
			field = request.getParameter("field");
		}
		if(request.getParameter("command") != null){
			command = request.getParameter("command");
		}

		queueCommand = new QueueCommand(id, order, field, command);
		return queueCommand;
	}

	/**
	 * Método para setar os valores do queueCommand atual no request
	 * 
	 * @param request
	 */
	public void queueCommandToRequest(HttpServletRequest request){
		request.setAttribute("id", id);
		request.setAttribute("order", order);
		request.setAttribute("field", field);
		request.setAttribute("command", command);
	}
	
	@Override
	/**
	 * Método para escrever o queueCommand em uma String
	 * 
	 * Será escrito na forma "field = command"
	 * 
	 * @return order, command
	 */
	public String toString(){
		return field+"="+command;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueueCommand other = (QueueCommand) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(QueueCommand o) {
		if(o.order == this.order){
			return 0;
		}
		return (o.order > this.order)? -1 : 1;
	}
}
