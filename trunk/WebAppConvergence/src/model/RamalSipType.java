package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum para representar os tipos de ramal
 * 
 * @author yvens
 *
 */
public enum RamalSipType {
	FRIEND("FRIEND");
	
	private String type;
	private RamalSipType(String type){
		this.type = type;
	}
	
	/**
	 * Imprime o type do RamalType em um objeto do tipo String
	 */
	public String toString(){
		return type;
	}

	/**
	 * Método para dado um type retornar o Enum do tipo passado
	 * 
	 * @param type
	 * @return retorna um Enum RamalType que tenha o type passado, ou nulo caso não exista
	 */
	public static RamalSipType getRamalType(String type){
		RamalSipType[] list = RamalSipType.class.getEnumConstants();
		
		for (int i = 0; i < list.length; i++) {
			if(list[i].toString().equalsIgnoreCase(type)){
				return list[i];
			}
		}
		return null;
	}
	
	/**
	 * Método para retornar uma lista de Strings com o nome de todos os tipos de ramal
	 * @return retorna um ArrayList de Strings
	 */
	public static List<String> listRamalType(){
		RamalSipType[] list = RamalSipType.class.getEnumConstants();
		List<String> listRamalType = new ArrayList<String>();
		
		for (int i = 0; i < list.length; i++) {
			listRamalType.add(list[i].toString());
		}
		
		return listRamalType;
	}
}
