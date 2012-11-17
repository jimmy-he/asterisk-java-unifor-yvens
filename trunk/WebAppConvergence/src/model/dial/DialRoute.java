package model.dial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe para representar uma rota do plano de discagem
 * Uma rota possui diversos comandos que retratam o procedimento da rota
 * 
 * Exemplo:
 * 	exten => *35,1,Answer()                    <-- Identifier = *35
	exten => *35,n,MusicOnHold(default,90)     <-- Commands: 1, Answer(); 2, MusicOnHold(default, 90)
	exten => *35,n,Hangup()                    <-- Commands: 3, Hangup();
 * 
 * @author yvens
 *
 */
public class DialRoute implements Comparable<DialRoute>{

	private String identifier;
	private List<DialCommand> listCommands;
	
	public DialRoute(String identifier) {
		super();
		this.identifier = identifier;
		listCommands = new ArrayList<DialCommand>();
	}
	
	/**
	 * Método para adição de comandos na lista de comandos da rota de discagem
	 * Logo depois da inserção a lista é ordenada
	 * 
	 * @param command
	 */
	public void addCommand(DialCommand command){
		boolean reorder = false;
		for(DialCommand dialCommand : listCommands){
			if(reorder){
				dialCommand.incrementOrder();
			}
			if(dialCommand.getOrder() == command.getOrder()){
				reorder = true;
				dialCommand.incrementOrder();
			}
		}
		
		listCommands.add(command);
		Collections.sort(listCommands);
	}
	
	public void removeCommand(DialCommand command){
		boolean reorder = false;
		for(DialCommand dialCommand : listCommands){
			if(reorder){
				dialCommand.decrementOrder();
			}
			if(dialCommand.getOrder() == command.getOrder()){
				reorder = true;
				dialCommand.decrementOrder();
			}
		}
		
		listCommands.remove(command);
		Collections.sort(listCommands);
		
		//Pega o primeiro comando da lista para verifica se sua ordem é 1
		command = listCommands.get(0);
		
		//Caso não seja 1, ele é substituído para 1
		if(command.getOrder() != 1){
			command.setOrder(1);
		}
	}
	
	public boolean containsCommand(DialCommand command){
		for(DialCommand dialCommand : listCommands){
			if(dialCommand.equals(command)){
				return true;
			}
		}
		return false;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public List<DialCommand> getListCommands() {
		return listCommands;
	}

	public void setListCommands(List<DialCommand> listCommands) {
		this.listCommands = listCommands;
	}

	/**
	 * Método para transformar um dialRoute em um array de Strings
	 * 
	 * @return um array de strings com strings no formato: exten => identifier, command.toString()
	 */
	public String[] toDialRoute(){
		String[] dialRoute = new String[listCommands.size()];
		
		for (int i = 0; i < dialRoute.length; i++) {
			dialRoute[i] = "exten => "+identifier+", "+listCommands.get(i).toString();
		}
		
		return dialRoute;
	}
	
	@Override
	/**
	 * Método para comparação, compara os identifiers de ambas as rotas
	 */
	public int compareTo(DialRoute o) {
		return o.getIdentifier().compareTo(this.getIdentifier());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}

	@Override
	/**
	 * Compara os valores do identifier 
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DialRoute other = (DialRoute) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}
}
