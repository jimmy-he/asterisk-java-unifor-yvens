package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import model.queues.Agent;

import org.junit.Test;

import asterisk.AgentHandler;
import exception.AgentException;

public class AgentHandlerTest {

public final static String agents_conf = "src"+File.separator+"tests"+File.separator+"input"+File.separator+"agents.conf";
	
	public final static Agent agent = new Agent(3, "666", "rock", "Rockeiro");
	
	@Test
	public void TestReadingLocalAgentConf() throws IOException, AgentException {
		//Essa verificação é default e também serve para verificar exceptions lançadas 
		//durante a execução do construtor
		AgentHandler agentHandler = new AgentHandler(agents_conf);
		
		//Verificação se o arquivo de sip.conf lido possui mais de 1 linha
		Assert.assertEquals(true, agentHandler.getAgentConfLines() > 0);
	}
	
	@Test
	public void TestReadingRealAgentConf() throws IOException, AgentException {
		//Essa verificação é default e também serve para verificar exceptions lançadas 
		//durante a execução do construtor
		AgentHandler agentHandler = new AgentHandler();
		
		//Verificação se o arquivo de sip.conf lido possui mais de 1 linha
		Assert.assertEquals(true, agentHandler.getAgentConfLines() > 0);
	}

	@Test
	public void TestCRUDAgentLocalSipConf() throws IOException, AgentException {
		AgentHandler handler = new AgentHandler(agents_conf);
		
		handler.insertAgent(agent);
		
		List<Agent> list = handler.listAgent();
		
		//Iteração na lista de ramais para buscar pelo ramal recentemente adicionado
		boolean exist = false;
		for (Agent listedAgent : list) {
			if(listedAgent.equals(agent)){
				exist = true;
			}
		}
		
		Assert.assertEquals(true, exist);
		
		Agent newAgent = handler.getAgent(agent.getName());
		newAgent.setSecret("1234");
		
		handler.updateAgent(newAgent);
		
		list = handler.listAgent();
		
		//Iteração na lista de ramais para buscar pelo ramal recentemente adicionado
		exist = false;
		boolean passwordChanged = false;
		for (Agent listedAgent : list) {
			if(listedAgent.equals(agent)){
				exist = true;
				
				if(listedAgent.getSecret().equals(newAgent.getSecret())){
					passwordChanged = true;
				}
			}
		}
		
		Assert.assertEquals(true, exist);
		Assert.assertEquals(true, passwordChanged);
		
		handler.deleteAgent(newAgent);
		
		list = handler.listAgent();
		
		//Iteração na lista de ramais para buscar pelo ramal recentemente adicionado
		exist = false;
		for (Agent listedAgent : list) {
			if(listedAgent.equals(agent)){
				exist = true;
			}
		}
		
		Assert.assertEquals(false, exist);
	}
	
	@Test
	public void TestListingAgentsLocalSipConf() throws IOException, AgentException {
		AgentHandler handler = new AgentHandler(agents_conf);
		
		List<Agent> agentList = handler.listAgent();
		
		for (Agent agent : agentList) {
			System.out.println(agent+" c: "+agent.getCode());
		}
		
		Assert.assertEquals(true, agentList != null);
		Assert.assertEquals(true, agentList.size() > 0);
	}

}
