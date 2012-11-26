package asterisk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import model.queues.Agent;
import persistence.FileHandler;
import exception.AgentException;
import exception.ExtensionsConfigException;
import exception.SipConfigException;

public class AgentHandler {
	private FileHandler fileHandler;
	private String agentConfPath;
	private int agentConfLines;

	// Semáforo para evitar modificações concorrentes nos arquivos de extensões
	private static final Semaphore mutex;
	static {
		mutex = new Semaphore(1);
	}

	public AgentHandler() throws IOException, AgentException {
		this(AsteriskConfiguration.AGENT_CONFIG_PATH);
	}

	public AgentHandler(String agentConfPath) throws IOException, AgentException {
		this.agentConfPath = agentConfPath;
		fileHandler = new FileHandler();

		if (!agentConfCertified()) {
			throw new AgentException();
		}
	}

	/**
	 * Método para a inserção de um plano de discagem dentro do arquivo
	 * extensions.conf do servidor asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a inserção, e falso caso
	 *         alguma outra instância esteja acessando e modificando o arquivo
	 *         no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws ExtensionsConfigException
	 * @throws SipConfigException
	 */
	public boolean insertAgent(Agent agent) throws IOException, AgentException {
		if (mutex.tryAcquire()) {
			List<Agent> listAgent = listAgent();
			
			for (Agent listedAgent : listAgent) {
				if(listedAgent.getCode().equals(agent.getCode())){
					throw new AgentException();
				}
			}
			
			String[] agentConf = fileHandler.readFile(agentConfPath);
			
			String newAgent = agent.toString();
			
			fileHandler.writeOnFile(agentConf, newAgent, agentConf.length);
			
			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para a atualização de um plano de discagem dentro do arquivo
	 * extensions.conf do servidor asterisk Esse método é usado para qualquer
	 * atualização do plano de discagem, desde alteração na tag, alteração nas
	 * rotas e alteração nos comandos das rotas
	 * 
	 * @return retorna verdadeiro caso consiga realizar a inserção, e falso caso
	 *         alguma outra instância esteja acessando e modificando o arquivo
	 *         no momento, assim, evitando conflito entre os arquivos
	 * 
	 * @throws IOException
	 * @throws AgentException 
	 */
	public boolean updateAgent(Agent agent) throws IOException, AgentException {
		if (mutex.tryAcquire()) {

			String[] agentConf = fileHandler.readFile(agentConfPath);
			Agent oldAgent = null;
			
			List<Agent> listAgent = listAgent();
			
			for (Agent listedAgent : listAgent) {
				if(listedAgent.getId() == agent.getId()){
					oldAgent = listedAgent;
				}
			}
			
			if(oldAgent == null){
				throw new AgentException("Agente não encontrando com código igual a "+agent.getCode());
			}
			
			int line = -1;
			for (int i = 0; i < agentConf.length; i++) {
				if(agentConf[i].trim().equals(oldAgent.toString().trim())){
					line = i;
				}
			}
			
			if(line == -1){
				throw new AgentException("Agente não encontrando com código igual a "+agent.getCode());
			}
			
			fileHandler.deleteLineOnFile(agentConf, line);
			
			agentConf = fileHandler.readFile(agentConfPath);
			
			fileHandler.writeOnFile(agentConf, agent.toString(), agentConf.length);
			
			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para a remoção de um plano de discagem dentro do arquivo
	 * extensions.conf do servidor asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a inserção, e falso caso
	 *         alguma outra instância esteja acessando e modificando o arquivo
	 *         no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AgentException 
	 * @throws SipConfigException
	 */
	public boolean deleteAgent(Agent agent) throws IOException, AgentException {
		if (mutex.tryAcquire()) {
			String[] agentConf = fileHandler.readFile(agentConfPath);
			
			System.out.println("Remover: "+agent.toString()+" >> "+agent.getId());
			
			int line = -1;
			for (int i = 0; i < agentConf.length; i++) {
				if(agentConf[i].trim().equals(agent.toString().trim())){
					line = i;
				}
			}
			
			if(line == -1){
				throw new AgentException("Agente não encontrando com código igual a "+agent.getCode());
			}
			
			fileHandler.deleteLineOnFile(agentConf, line);
			
			mutex.release();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para buscar a lista dos agents do arquivo
	 * agents.conf
	 * 
	 * @return retorna um arraylist de dialplan
	 * @throws IOException
	 */
	public List<Agent> listAgent() throws IOException {
		String[] agentConfFile = fileHandler.readFile(agentConfPath);
		List<Agent> listAgent = new ArrayList<Agent>();
		
		for (int i = 0; i < agentConfFile.length; i++) {
			// Verificando se acha uma TAG no estilo [4666]
			if (!agentConfFile[i].isEmpty() && agentConfFile[i].charAt(0) == '[' && !agentConfFile[i].equals("[general]")) {
				//Linha de agentes
				//Ignora a linha [Agents]
				i++;
				
				int id = 1;
				while(i < agentConfFile.length){
					if(!agentConfFile[i].isEmpty()){
						String line = agentConfFile[i].replace('>', ' ').trim();
						
						//agent=>1001,4321,Mark Spencer
						line = line.substring(7);
						
						String parameters[] = line.split(",");
						
						String code = parameters[0].trim();
						String secret = parameters[1];
						String name = parameters[2];
						
						Agent agent = new Agent(id++, code, name, secret);
						
						listAgent.add(agent);
					}
					i++;
				}
			}
		}
		
		return listAgent;
	}

	public Agent getAgent(String name) throws AgentException, IOException{
		Agent agent = null;
		
		List<Agent> listAgent = listAgent();
		
		for (Agent listedAgent : listAgent) {
			if(listedAgent.getName().equals(name)){
				agent = listedAgent;
			}
		}
		
		if (agent == null) {
			throw new AgentException("Erro! Agente não encontrado com nome = " + name);
		}
		
		return agent;
	}
	
	public Agent getAgent(int id) throws AgentException, IOException{
		Agent agent = null;
		
		List<Agent> listAgent = listAgent();
		
		for (Agent listedAgent : listAgent) {
			if(listedAgent.getId() == id){
				agent = listedAgent;
			}
		}
		
		if (agent == null) {
			throw new AgentException("Erro! Agente não encontrado com id = " + id);
		}
		
		return agent;
	}

	/**
	 * Método para fazer a verificação do PATH do agent.conf passado
	 * 
	 * @return verdadeiro caso seja realizada uma leitura com sucesso do arquivo
	 *         do PATH passado
	 * @throws IOException
	 */
	private boolean agentConfCertified() throws IOException {
		String[] agentConf = fileHandler.readFile(agentConfPath);
		this.agentConfLines = agentConf.length;
		return agentConf.length > 0;
	}

	public int getAgentConfLines() {
		return agentConfLines;
	}
}
