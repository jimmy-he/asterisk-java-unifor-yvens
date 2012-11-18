package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import model.dial.DialCommand;
import model.dial.DialPlan;
import model.dial.DialRoute;

import org.junit.Before;
import org.junit.Test;

import asterisk.ExtensionHandler;
import exception.ExtensionsConfigException;

public class ExtensionHandlerTest {

	public final static String extensions_conf = "src"+File.separator+"tests"+File.separator+"input"+File.separator+"extensions.conf";
	
	private DialPlan plan;
	private DialRoute route;
	private DialCommand command;
	
	@Before
	public void setUp() {
		DialCommand dialCommand = new DialCommand(1, "Answer()");
		
		//não é para adicionar um comando que já existe no plano de discagem
		command = new DialCommand(3, "Troço()");
		
		DialRoute dialRoute = new DialRoute("*444");
		dialRoute.addCommand(dialCommand);
		
		plan = new DialPlan("TEST");
		plan.addRoute(dialRoute);
		
		route = new DialRoute("*666");
		route.addCommand(dialCommand);
    }
	
	@Test
	public void TestReadingLocalSipConf() throws IOException, ExtensionsConfigException{
		//Essa verificação é default e também serve para verificar exceptions lançadas 
		//durante a execução do construtor
		ExtensionHandler handler = new ExtensionHandler(extensions_conf);
		
		//Verificação se o arquivo de extensions.conf lido possui mais de 1 linha
		Assert.assertEquals(true, handler.getExtensionsConfLines() > 0);
	}
	
	@Test
	public void TestReadingRealSipConf() throws IOException, ExtensionsConfigException{
		//Essa verificação é default e também serve para verificar exceptions lançadas 
		//durante a execução do construtor
		ExtensionHandler handler = new ExtensionHandler();
		
		//Verificação se o arquivo de extensions.conf lido possui mais de 1 linha
		Assert.assertEquals(true, handler.getExtensionsConfLines() > 0);
	}
	
	@Test
	public void insertDeleteDialPlan() throws IOException, ExtensionsConfigException{
		ExtensionHandler handler = new ExtensionHandler(extensions_conf);
		
		handler.insertDialPlan(plan);
		
		List<DialPlan> list = handler.listDialPlan();
		
		boolean existe = false;
		for(DialPlan dialPlan : list){
			if(dialPlan.equals(plan)){
				existe = true;
			}
		}
		
		Assert.assertEquals(true, existe);
		
		handler.deleteDialPlan(plan);
		list = handler.listDialPlan();
		
		existe = false;
		for(DialPlan dialPlan : list){
			if(dialPlan.equals(plan)){
				existe = true;
			}
		}
		
		Assert.assertEquals(false, existe);
	}
	
	@Test
	public void insertDeleteDialRoute() throws IOException, ExtensionsConfigException{
		ExtensionHandler handler = new ExtensionHandler(extensions_conf);
		
		List<DialPlan> list = handler.listDialPlan();
		
		//Pega um plano qualquer da lista, por comodidade pegamos o primeiro
		DialPlan plan = list.get(0);
		
		//Adiciona a rota no plano
		plan.addRoute(route);
		
		//Atualiza o plano na lista
		handler.updateDialPlan(plan);
		
		list = handler.listDialPlan();
		
		boolean existe = true;
		for(DialPlan dialPlan : list){
			if(dialPlan.equals(plan)){
				existe = dialPlan.containsRoute(route);
			}
		}
		
		Assert.assertEquals(true, existe);
		
		plan.removeRoute(route);
		
		//Atualiza o plano na lista
		handler.updateDialPlan(plan);
		
		list = handler.listDialPlan();
		
		existe = true;
		for(DialPlan dialPlan : list){
			if(dialPlan.equals(plan)){
				existe = dialPlan.containsRoute(route);
			}
		}
		
		Assert.assertEquals(false, existe);
	}
	
	@Test
	public void insertDeleteDialCommand() throws IOException, ExtensionsConfigException{
		ExtensionHandler handler = new ExtensionHandler(extensions_conf);
		
		List<DialPlan> list = handler.listDialPlan();
		
		//Pega um plano qualquer da lista, por comodidade pegamos o primeiro
		DialPlan plan = list.get(0);
		
		List<DialRoute> routeList = plan.getRouteList();
		
		//Pega uma rota qualquer da lista, por comodidade pegamos o primeiro
		DialRoute dialRoute = routeList.get(0);
		
		dialRoute.addCommand(command);
		
		//Atualiza o plano na lista
		handler.updateDialPlan(plan);
		
		list = handler.listDialPlan();
		
		boolean existe = false;
		for(DialPlan dialPlan : list){
			if(dialPlan.equals(plan)){
				routeList = dialPlan.getRouteList();
				
				for(DialRoute dialRouteList : routeList){
					if (dialRouteList.containsCommand(command)){
						//se foi adicionado corretamente, ele existirá 
						existe = true;
						break;
					}
					
				}
			}
		}
		
		Assert.assertEquals(true, existe);
		
		dialRoute.removeCommand(command);
		
		//Atualiza o plano na lista
		handler.updateDialPlan(plan);
		
		list = handler.listDialPlan();
		
		existe = false;
		for(DialPlan dialPlan : list){
			if(dialPlan.equals(plan)){
				routeList = dialPlan.getRouteList();
				
				for(DialRoute dialRouteList : routeList){
					if (dialRouteList.containsCommand(command)){
						//se foi deletado corretamente, ele nunca entrará aqui 
						existe = true;
						break;
					}
				}
			}
		}
		
		Assert.assertEquals(false, existe);
	}
	
	@Test
	public void testSort() throws IOException, ExtensionsConfigException{
		//Esse teste é para verificar os métodos de ordenação
		ExtensionHandler handler = new ExtensionHandler(extensions_conf);
		
		List<DialPlan> list = handler.listDialPlan();
		
		for(DialPlan plan : list){
			List<DialRoute> routes = plan.getRouteList();
			
			for(DialRoute route : routes){
				List<DialCommand> commands = route.getListCommands();
				
				for (int i = 0; i < commands.size() - 1; i++) {
					Assert.assertEquals(true, commands.get(i).getOrder() < commands.get(i + 1).getOrder());
				}
			}
		}
	}
	
	@Test
	public void testListExtensions() throws IOException, ExtensionsConfigException {
		ExtensionHandler handler = new ExtensionHandler(extensions_conf);
		
		List<DialPlan> list = handler.listDialPlan();
		
		debugList(list);
		
		Assert.assertEquals(true, list != null);
		Assert.assertEquals(true, list.size() > 0);
	}

	private void debugList(List<DialPlan> list){
		System.out.println("List");
		for(DialPlan dialPlan : list){
			System.out.println(dialPlan.getTag());
			System.out.println("Suas rotas:");
			List<DialRoute> listRoute = dialPlan.getRouteList();
			
			for(DialRoute dialRoute : listRoute){
				System.out.println("Rota: " + dialRoute.getIdentifier());
				System.out.println("Seus comandos:");
				List<DialCommand> listCommands = dialRoute.getListCommands();
				
				for(DialCommand dialCommand : listCommands){
					System.out.println("---: " + dialCommand.getOrder()+" : " + dialCommand.getCommand());
				}
			}
		}
	}
}
