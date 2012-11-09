package tests;

import junit.framework.Assert;

import org.junit.Test;

import persistence.FileHandler;

/**
 * Testes para as classes que fazem leitura/alteração
 * 
 * @author yvens
 *
 */
public class PersistenceTest {
	
	@Test
	public void testReading() {
		FileHandler fileHandler = new FileHandler();
		
		String[] file = fileHandler.readFile(""); //O arquivo deve vir por aqui
		
		int lineNumber = 4; //número de linhas do arquivo
		Assert.assertEquals(lineNumber, file.length);
		
		String firstline = "[intro]"; //primeira linha do arquivo
		Assert.assertEquals(firstline, file[0]);
	}
	
	@Test
	public void testWritingDeleting(){
		FileHandler fileHandler = new FileHandler();
		
		String[] file = fileHandler.readFile(""); //O arquivo deve vir por aqui
		
		String newLine = "[intro2]";
		
		//Escrita a nova linha no final do texto
		fileHandler.writeOnFile(file, newLine, file.length);
		
		//Arquivo é relido
		file = fileHandler.readFile("");
		
		//Verificamos se a última linha corresponde à nova linha adicionada
		Assert.assertEquals(file[file.length-1], newLine);
		
		//Deletamos a última linha
		fileHandler.deleteLineOnFile(file, file.length-1);
		
		//Arquivo é relido
		file = fileHandler.readFile("");
		
		//Comparamos se a última linha realmente mudou
		Assert.assertNotSame(file[file.length-1], newLine);
	}
	
	@Test
	public void testWritingDeletingMultipleLines()
	{
		FileHandler fileHandler = new FileHandler();
		
		String[] file = fileHandler.readFile(""); //O arquivo deve vir por aqui
		String oldLastLine = file[file.length-1]; //Ultima linha guardada para comparação futura
		
		String[] newLine = new String[]{"[intro2]", "texto1", "texto1", "texto1"}; //Novas linhas que serão adicionadas
		
		int lines = file.length;
		int newLinesNumber = 4;
		
		//Escrito no arquivo as novas linhas
		fileHandler.writeOnFile(file, newLine, file.length);
		
		//O arquivo é relido
		file = fileHandler.readFile("");
		
		//As novas linhas são comparadas uma a uma para verificar se foram corretamente inseridas
		for (int i = lines, j = 0; i < lines + newLinesNumber; i++, j++) {
			Assert.assertEquals(file[i], newLine[j]);
		}
		
		//As linhas que foram inseridas são deletadas
		fileHandler.deleteLineOnFile(file, file.length-newLinesNumber, file.length - 1);

		//O arquivo é relido
		file = fileHandler.readFile("");
		
		//Verificamos se a última linha do arquivo corresponde a antiga última linha guardada
		Assert.assertEquals(file[file.length-1], oldLastLine);
	}
}
