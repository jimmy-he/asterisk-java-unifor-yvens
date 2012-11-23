package model.queues;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe para representar o agente de atendimento
 * 
 * Agentes são escritos no formato
 * 
 * agent=>código_do_agente, senha, nome
 * 
 * Por convenção, não vai ser permitido alterar o código do agente
 * 
 * @author yvens
 *
 */
public class Agent implements Comparable<Agent>{

	private int id;
	private String code;
	private String name;
	private String secret;
	
	public Agent(int id, String code, String name, String secret) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.secret = secret;
	}
	
	/**
	 * Escreve o agente no formato
	 * 
	 * agent=>code,secret,name
	 */
	public String toString(){
		return "agent=>"+code+","+secret+","+name;
	}

	/**
	 * Método para buscar os atributos de um ramal sip que está no request
	 * e retornar o objeto ramal sip
	 * 
	 * @param request
	 * @return o objeto ramalSip que está nos parâmetros do request
	 */
	public static Agent getAgentFromParameter(HttpServletRequest request){
		Agent agent = null;

		String id = "";
		String code = "";
		String secret = "";
		String name = "";
		
		if(request.getParameter("id") != null){
			id = request.getParameter("id");
		}
		if(request.getParameter("code") != null){
			code = request.getParameter("code");
		}
		if(request.getParameter("secret") != null){
			secret = request.getParameter("secret");
		}
		if(request.getParameter("name") != null){
			name = request.getParameter("name");
		}
		
		if(id.isEmpty()){
			id = "0";
		}

		agent = new Agent(Integer.parseInt(id),code, name, secret);
		return agent;
	}

	/**
	 * Método para setar os valores do queueCommand atual no request
	 * 
	 * @param request
	 */
	public void agentToRequest(HttpServletRequest request){
		request.setAttribute("id", id);
		request.setAttribute("code", code);
		request.setAttribute("name", name);
		request.setAttribute("secret", secret);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	/**
	 * Comparação pelo código
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agent other = (Agent) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public int compareTo(Agent o) {
		return code.compareTo(o.getCode());
	}
}
