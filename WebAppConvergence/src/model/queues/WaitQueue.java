package model.queues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import exception.ExtensionsConfigException;

/**
 * Classe para representar uma fila de espera
 * 
 * Filas de espera possuem uma TAG, um identificador único e uma lista de QueueCommands
 * 
 * @author yvens
 */
public class WaitQueue implements Comparable<WaitQueue>{

	private int id;
	private String tag;
	List<QueueCommand> listCommands;
	
	public WaitQueue(int id, String tag) {
		super();
		this.id = id;
		this.tag = tag;
		
		listCommands = new ArrayList<QueueCommand>();
	}
	
	/**
	 * Método para adição de comandos na fila de espera
	 * Logo depois da inserção a lista é ordenada
	 * 
	 * @param command
	 */
	public void addCommand(QueueCommand command){
		boolean reorder = false;
		for(QueueCommand queueCommand : listCommands){
			if(reorder){
				queueCommand.incrementOrder();
			}
			if(queueCommand.getOrder() == command.getOrder()){
				reorder = true;
				queueCommand.incrementOrder();
			}
		}
		
		listCommands.add(command);
		Collections.sort(listCommands);
	}
	
	public void removeCommand(QueueCommand command){
		boolean reorder = false;
		for(QueueCommand queueCommand : listCommands){
			if(reorder){
				queueCommand.decrementOrder();
			}
			if(queueCommand.getOrder() == command.getOrder()){
				reorder = true;
				queueCommand.decrementOrder();
			}
		}
		
		removeCommand(command.getId());
		
		Collections.sort(listCommands);
		
		//Pega o primeiro comando da lista para verifica se sua ordem é 1
		command = listCommands.get(0);
		
		//Caso não seja 1, ele é substituído para 1
		if(command.getOrder() != 1){
			command.setOrder(1);
		}
	}
	
	/**
	 * Remove o comando da lista que possua id igual ao passado
	 * @param id
	 */
	private void removeCommand(int id){
		QueueCommand queueCommand = null;
		for(QueueCommand command : listCommands){
			if(command.getId() == id){
				queueCommand = command;
			}
		}
		
		listCommands.remove(queueCommand);
	}
	
	public boolean containsCommand(QueueCommand command){
		for(QueueCommand queueCommand : listCommands){
			if(queueCommand.getCommand().equals(command.getCommand())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Método para buscar um QueueCommand pelo id na lista de comandos
	 * 
	 * @param id
	 * @return
	 * @throws ExtensionsConfigException
	 */
	public QueueCommand getCommand(int id) throws ExtensionsConfigException{
		QueueCommand command = null;
		
		for(QueueCommand queueCommand : listCommands){
			if(queueCommand.getId() == id){
				command = queueCommand;
			}
		}
		
		if(command == null){
			throw new ExtensionsConfigException("Erro! Comando não encontrado!");
		}
		
		return command;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<QueueCommand> getListCommands() {
		return listCommands;
	}

	public void setListCommands(List<QueueCommand> listCommands) {
		this.listCommands = listCommands;
	}
	
	/**
	 * Método para transformar um WaitQueue em um array de Strings
	 * 
	 * @return um array de strings com strings no formato
	 * [Tag]
	 * field1=command1
	 * field2=command2
	 * ...
	 * fieldN=commandN
	 */
	public String[] toWaitQueue(){
		String[] waitQueue = new String[listCommands.size()+1];
		
		waitQueue[0] = "["+tag+"]";
		for (int i = 1; i < waitQueue.length; i++) {
			waitQueue[i] = listCommands.get(i-1).toString();
		}
		
		return waitQueue;
	}
	
	/**
	 * Método para buscar os atributos de uma fila de espera que está no request
	 * e retornar o objeto WaitQueue
	 * 
	 * @param request
	 * @return o objeto WaitQueue que está nos parâmetros do request
	 */
	public static WaitQueue getWaitQueueFromParameter(HttpServletRequest request){
		WaitQueue queue = null;
		
		int id = 0;
		String tag = "";
		
		if(request.getParameter("id") != null){
			id = Integer.parseInt(request.getParameter("id"));
		}
		if(request.getParameter("tag") != null){
			tag = request.getParameter("tag");
		}
		
		queue = new WaitQueue(id, tag);
		return queue;
	}
	
	/**
	 * Método para setar os valores da fila de espera no request
	 * 
	 * @param request
	 */
	public void waitQueueToRequest(HttpServletRequest request){
		request.setAttribute("id", id);
		request.setAttribute("tag", tag);
	}

	@Override
	public int compareTo(WaitQueue o) {
		return tag.compareTo(o.getTag());
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
		WaitQueue other = (WaitQueue) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
