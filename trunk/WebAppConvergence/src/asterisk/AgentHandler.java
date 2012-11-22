package asterisk;

import java.io.IOException;
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
	 */
	public boolean updateAgent(Agent agent) throws IOException {
		if (mutex.tryAcquire()) {

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
	 * @throws SipConfigException
	 */
	public boolean deleteAgent(Agent agent) throws IOException {
		if (mutex.tryAcquire()) {

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
		//TODO
		return null;
	}

	public Agent getAgent(String name) throws AgentException{
		Agent agent = null;
		
//		if (agent == null) {
//			throw new AgentException("Erro! Plano de Discagem não encontrado!Name = " + name);
//		}
		
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
