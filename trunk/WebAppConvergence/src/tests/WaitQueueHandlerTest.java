package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import model.queues.QueueCommand;
import model.queues.WaitQueue;

import org.junit.Before;
import org.junit.Test;

import asterisk.WaitQueueHandler;
import exception.WaitQueueException;

public class WaitQueueHandlerTest {

	public final static String queues_conf = "src"+File.separator+"tests"+File.separator+"input"+File.separator+"queues.conf";
	
	private WaitQueue queue;
	private QueueCommand command;
	private QueueCommand anotherCommand;
	
	@Before
	public void setUp() {
		queue = new WaitQueue(1, "TESTE");
		command = new QueueCommand(1, 1, "musicclass", "default");
		anotherCommand = new QueueCommand(6, 6, "mimimi", "rock");
		queue.addCommand(command);
    }
	
	@Test
	public void TestReadingLocalQueuesConf() throws IOException, WaitQueueException{
		//Essa verificação é default e também serve para verificar exceptions lançadas 
		//durante a execução do construtor
		WaitQueueHandler handler = new WaitQueueHandler(queues_conf);
		
		//Verificação se o arquivo de extensions.conf lido possui mais de 1 linha
		Assert.assertEquals(true, handler.getQueueConfLines() > 0);
	}
	
	@Test
	public void TestReadingRealQueuesConf() throws IOException, WaitQueueException {
		//Essa verificação é default e também serve para verificar exceptions lançadas 
		//durante a execução do construtor
		WaitQueueHandler handler = new WaitQueueHandler();
		
		//Verificação se o arquivo de extensions.conf lido possui mais de 1 linha
		Assert.assertEquals(true, handler.getQueueConfLines() > 0);
	}
	
	@Test
	public void insertDeleteWaitQueue() throws IOException, WaitQueueException{
		WaitQueueHandler handler = new WaitQueueHandler(queues_conf);
		
		handler.insertWaitQueue(queue);
		
		List<WaitQueue> list = handler.listWaitQueue();
		debugList(list);
		
		boolean existe = false;
		for(WaitQueue waitQueue : list){
			if(waitQueue.getTag().equals(queue.getTag())){
				existe = true;
			}
		}
		
		Assert.assertEquals(true, existe);
		
		handler.deleteWaitQueue(queue);
		list = handler.listWaitQueue();		
		
		existe = false;
		for(WaitQueue waitQueue : list){
			if(waitQueue.getTag().equals(queue.getTag())){
				existe = true;
			}
		}
		
		Assert.assertEquals(false, existe);
	}
	
	@Test
	public void insertDeleteQueueCommand() throws IOException, WaitQueueException{
		WaitQueueHandler handler = new WaitQueueHandler(queues_conf);
		
		List<WaitQueue> list = handler.listWaitQueue();
		
		//Pega uma fila qualquer da lista, por comodidade pegamos o primeiro
		WaitQueue queue = list.get(0);
		
		//Adiciona o comando na fila
		queue.addCommand(anotherCommand);
		
		//Atualiza a fila na lista
		handler.updateWaitQueue(queue);
		
		list = handler.listWaitQueue();
		
		boolean existe = false;
		for(WaitQueue waitQueue : list){
			if(waitQueue.getTag().equals(queue.getTag())){
				existe = queue.containsCommand(anotherCommand);
			}
		}
		
		Assert.assertEquals(true, existe);
		
		queue.removeCommand(anotherCommand);
		
		//Atualiza a fila na lista
		handler.updateWaitQueue(queue);
		
		list = handler.listWaitQueue();
		
		existe = false;
		for(WaitQueue waitQueue : list){
			if(waitQueue.getTag().equals(queue.getTag())){
				existe = queue.containsCommand(anotherCommand);
			}
		}
		
		Assert.assertEquals(false, existe);
	}

	@Test
	public void testSort() throws IOException, WaitQueueException{
		//Esse teste é para verificar os métodos de ordenação
		WaitQueueHandler handler = new WaitQueueHandler(queues_conf);
		
		List<WaitQueue> list = handler.listWaitQueue();
		
		for(WaitQueue waitQueue : list){
			List<QueueCommand> commands = waitQueue.getListCommands();
			
			for (int i = 0; i < commands.size() - 1; i++) {
				Assert.assertEquals(true, commands.get(i).getOrder() < commands.get(i + 1).getOrder());
			}
		}
	}
	
	@Test
	public void testListExtensions() throws IOException, WaitQueueException {
		WaitQueueHandler handler = new WaitQueueHandler(queues_conf);
		
		List<WaitQueue> list = handler.listWaitQueue();
		
		debugList(list);
		
		Assert.assertEquals(true, list != null);
		Assert.assertEquals(true, list.size() > 0);
	}
	
	private void debugList(List<WaitQueue> list){
		for (WaitQueue waitQueue : list) {
			System.out.println("TAG: "+waitQueue.getTag());
			
			List<QueueCommand> commands = waitQueue.getListCommands();
			
			for (QueueCommand queueCommand : commands) {
				System.out.println("->>"+queueCommand.getId()+" "+queueCommand.getField()+" = "+queueCommand.getCommand());
			}
		}
	}
}
