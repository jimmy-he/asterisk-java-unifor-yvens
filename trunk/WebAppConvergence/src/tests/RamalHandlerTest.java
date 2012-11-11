package tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import model.Ramal;

import org.junit.Test;

import exception.SipConfigException;

import asterisk.RamalHandler;

public class RamalHandlerTest {

	public final static String sip_conf = "src"+File.separator+"tests"+File.separator+"input"+File.separator+"sip.conf";
	
	public final static Ramal ramal = new Ramal("4666", "4666", "TesteApp", "senha");
	
	@Test
	public void TestReadingLocalSipConf() throws IOException, SipConfigException {
		//Essa verificação é default e também serve para verificar exceptions lançadas 
		//durante a execução do construtor
		RamalHandler ramalHandler = new RamalHandler(sip_conf);
		
		//Verificação se o arquivo de sip.conf lido possui mais de 1 linha
		Assert.assertEquals(true, ramalHandler.getSipConfLines() > 0);
	}
	
	@Test
	public void TestReadingRealSipConf() throws IOException, SipConfigException {
		//Essa verificação é default e também serve para verificar exceptions lançadas 
		//durante a execução do construtor
		RamalHandler ramalHandler = new RamalHandler();
		
		//Verificação se o arquivo de sip.conf lido possui mais de 1 linha
		Assert.assertEquals(true, ramalHandler.getSipConfLines() > 0);
	}

	@Test
	public void TestAddingAndDeletingRamalLocalSipConf() throws IOException, SipConfigException, InterruptedException {
		RamalHandler ramalHandler = new RamalHandler(sip_conf);
		
		//Criado um ramal default
		ramalHandler.createRamal(ramal);
		
		//Carregado a lista dos ramais, para buscar pelo ramal adicionado
		List<Ramal> listRamal = ramalHandler.listRamal();
		
		//Iteração na lista de ramais para buscar pelo ramal recentemente adicionado
		boolean exist = false;
		for(Ramal insertedRamal : listRamal){
			if(insertedRamal.equals(ramal)){
				exist = true;
			}
		}
		
		Assert.assertEquals(true, exist);
		
		//Enviado o comando para remover o ramal que foi adicionado
		ramalHandler.deleteRamal(ramal);
		
		//Carregado a lista dos ramais, para buscar pelo ramal removido
		listRamal = ramalHandler.listRamal();
		
		//Iteração na lista de ramais para buscar pelo ramal recentemente removido
		exist = false;
		for(Ramal insertedRamal : listRamal){
			if(insertedRamal.equals(ramal)){
				exist = true;
			}
		}
		
		Assert.assertEquals(false, exist);
	}
	
	@Test
	public void TestAddingUpdatingDeletingRamalLocalSipConf() throws IOException, SipConfigException, InterruptedException {
		RamalHandler ramalHandler = new RamalHandler(sip_conf);
		
		//Criado um ramal default
		ramalHandler.createRamal(ramal);
		
		//Carregado a lista dos ramais, para buscar pelo ramal adicionado
		List<Ramal> listRamal = ramalHandler.listRamal();
		
		//Iteração na lista de ramais para buscar pelo ramal recentemente adicionado
		boolean exist = false;
		for(Ramal insertedRamal : listRamal){
			if(insertedRamal.equals(ramal)){
				exist = true;
			}
		}
		
		Assert.assertEquals(true, exist);
		
		//Modificado um parâmetro dentro do ramal
		String oldCallerId = ramal.getCallerId();
		String newCallerId = "4666 Rock";
		ramal.setCallerId(newCallerId);
		
		//Enviado o comando para alterar o ramal no arquivo
		ramalHandler.updateRamal(ramal);
		
		//Carregado a lista dos ramais, para buscar pelo ramal alterado
		listRamal = ramalHandler.listRamal();		
		
		Ramal realRamal = null;
		for(Ramal insertedRamal : listRamal){
			if(insertedRamal.getTag().equals(ramal.getTag())){
				realRamal = insertedRamal;
			}
		}
		
		if(realRamal == null){
			fail("Ramal modificado não foi encontrado!");
		}
		Assert.assertEquals(newCallerId, realRamal.getCallerId());
		
		//Agora é feita a reversão para o caller id original
		ramal.setCallerId(oldCallerId);
		
		//Carregado a lista dos ramais, para buscar pelo ramal alterado
		listRamal = ramalHandler.listRamal();		
		
		realRamal = null;
		for(Ramal insertedRamal : listRamal){
			if(insertedRamal.getTag().equals(ramal.getTag())){
				realRamal = insertedRamal;
			}
		}
		
		if(realRamal == null){
			fail("Ramal modificado não foi encontrado!");
		}
		Assert.assertEquals(oldCallerId, realRamal.getCallerId());
		
		//Enviado o comando para alterar o ramal no arquivo
		ramalHandler.updateRamal(ramal);
		
		//Enviado o comando para remover o ramal que foi adicionado
		ramalHandler.deleteRamal(ramal);
		
		//Carregado a lista dos ramais, para buscar pelo ramal removido
		listRamal = ramalHandler.listRamal();
		
		//Iteração na lista de ramais para buscar pelo ramal recentemente removido
		exist = false;
		for(Ramal insertedRamal : listRamal){
			if(insertedRamal.equals(ramal)){
				exist = true;
			}
		}
		
		Assert.assertEquals(false, exist);
	}
	
	@Test
	public void TestListingRamalLocalSipConf() throws IOException, SipConfigException {
		RamalHandler ramalHandler = new RamalHandler(sip_conf);
		
		List<Ramal> ramalList = ramalHandler.listRamal();
		
		Assert.assertEquals(2, ramalList.size());
	}
}
