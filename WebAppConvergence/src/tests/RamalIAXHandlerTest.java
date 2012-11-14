package tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import model.RamalIAX;

import org.junit.Test;

import exception.RamalIAXException;
import exception.IAXConfigException;

import asterisk.RamalIAXHandler;

public class RamalIAXHandlerTest {

	public final static String iax_conf = "src" + File.separator + "tests"
			+ File.separator + "input" + File.separator + "iax.conf";

	public final static RamalIAX ramal = new RamalIAX("4666", "4666",
			"TesteApp", "senha");

	@Test
	public void TestReadingLocalIAXConf() throws IOException,
			IAXConfigException {
		// Essa verificação é default e também serve para verificar exceptions
		// lançadas
		// durante a execução do construtor
		RamalIAXHandler ramalHandler = new RamalIAXHandler(iax_conf);

		// Verificação se o arquivo de iax.conf lido possui mais de 1 linha
		Assert.assertEquals(true, ramalHandler.getIAXConfLines() > 0);
	}

	@Test
	public void TestReadingRealIAXConf() throws IOException, IAXConfigException {
		// Essa verificação é default e também serve para verificar exceptions
		// lançadas
		// durante a execução do construtor
		RamalIAXHandler ramalHandler = new RamalIAXHandler();

		// Verificação se o arquivo de iax.conf lido possui mais de 1 linha
		Assert.assertEquals(true, ramalHandler.getIAXConfLines() > 0);
	}

	@Test
	public void TestAddingAndDeletingRamalLocalIAXConf() throws IOException,
			IAXConfigException, InterruptedException, RamalIAXException {
		RamalIAXHandler ramalHandler = new RamalIAXHandler(iax_conf);

		// Criado um ramal default
		ramalHandler.createRamal(ramal);

		// Carregado a lista dos ramais, para buscar pelo ramal adicionado
		List<RamalIAX> listRamal = ramalHandler.listRamal();

		// Iteração na lista de ramais para buscar pelo ramal recentemente
		// adicionado
		boolean exist = false;

		for (RamalIAX insertedRamal : listRamal) {
			System.out.println(insertedRamal.getTag());
			if (insertedRamal.equals(ramal)) {
				exist = true;
			}
		}

		Assert.assertEquals(true, exist);

		// Enviado o comando para remover o ramal que foi adicionado
		ramalHandler.deleteRamal(ramal);

		// Carregado a lista dos ramais, para buscar pelo ramal removido
		listRamal = ramalHandler.listRamal();

		// Iteração na lista de ramais para buscar pelo ramal recentemente
		// removido
		exist = false;
		for (RamalIAX insertedRamal : listRamal) {
			if (insertedRamal.equals(ramal)) {
				exist = true;
			}
		}

		Assert.assertEquals(false, exist);
	}

	@Test
	public void TestAddingUpdatingDeletingRamalLocalIAXConf()
			throws IOException, IAXConfigException, InterruptedException,
			RamalIAXException {
		RamalIAXHandler ramalHandler = new RamalIAXHandler(iax_conf);
		// Para verificar a integridade do arquivo
		int originalLines = ramalHandler.getIAXConfLines();

		// Criado um ramal default
		ramalHandler.createRamal(ramal);

		// Carregado a lista dos ramais, para buscar pelo ramal adicionado
		List<RamalIAX> listRamal = ramalHandler.listRamal();

		// Iteração na lista de ramais para buscar pelo ramal recentemente
		// adicionado
		boolean exist = false;
		for (RamalIAX insertedRamal : listRamal) {
			if (insertedRamal.equals(ramal)) {
				exist = true;
			}
		}

		Assert.assertEquals(true, exist);

		// Modificado um parâmetro dentro do ramal

		String oldCallerId = ramal.getCallerId();
		String newCallerId = "4666Rock";
		ramal.setCallerId(newCallerId);

		// Enviado o comando para alterar o ramal no arquivo
		ramalHandler.updateRamal(ramal);

		// Carregado a lista dos ramais, para buscar pelo ramal alterado
		listRamal = ramalHandler.listRamal();

		RamalIAX realRamal = null;
		for (RamalIAX insertedRamal : listRamal) {
			if (insertedRamal.getTag().equals(ramal.getTag())) {
				realRamal = insertedRamal;
			}
		}

		if (realRamal == null) {
			fail("Ramal modificado não foi encontrado!");
		}
		Assert.assertEquals(newCallerId, realRamal.getCallerId());

		// Agora é feita a reversão para o caller id original
		ramal.setCallerId(oldCallerId);

		// Enviado o comando para alterar o ramal no arquivo
		ramalHandler.updateRamal(ramal);

		// Carregado a lista dos ramais, para buscar pelo ramal alterado
		listRamal = ramalHandler.listRamal();

		realRamal = null;
		for (RamalIAX insertedRamal : listRamal) {
			if (insertedRamal.getTag().equals(ramal.getTag())) {
				realRamal = insertedRamal;
			}
		}

		if (realRamal == null) {
			fail("Ramal modificado não foi encontrado!");
		}
		Assert.assertEquals(oldCallerId, realRamal.getCallerId());

		// Enviado o comando para remover o ramal que foi adicionado
		ramalHandler.deleteRamal(ramal);

		// Carregado a lista dos ramais, para buscar pelo ramal removido
		listRamal = ramalHandler.listRamal();

		// Iteração na lista de ramais para buscar pelo ramal recentemente
		// removido
		exist = false;
		for (RamalIAX insertedRamal : listRamal) {
			if (insertedRamal.equals(ramal)) {
				exist = true;
			}
		}

		Assert.assertEquals(false, exist);

		// Verifica se o arquivo contém o mesmo número de linhas que o original
		Assert.assertEquals(originalLines,
				new RamalIAXHandler(iax_conf).getIAXConfLines());
	}

	@Test
	public void TestListingRamalLocalIAXConf() throws IOException,
			IAXConfigException {
		RamalIAXHandler ramalHandler = new RamalIAXHandler(iax_conf);

		List<RamalIAX> ramalList = ramalHandler.listRamal();

		Assert.assertEquals(2, ramalList.size());
	}
}
