package tests;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

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
	
	private static final String simple_input_path = "src"+File.separator+"tests"+File.separator+"input"+File.separator+"simple_input.txt";
	private static final String simple_input_write_path = "src"+File.separator+"tests"+File.separator+"input"+File.separator+"simple_input_write.txt";
	private static final String simple_input_write_multi_path = "src"+File.separator+"tests"+File.separator+"input"+File.separator+"simple_input_write_multi.txt";
	
	@Test
	public void testReading() throws IOException {
		FileHandler fileHandler = new FileHandler();
		
		String[] file = fileHandler.readFile(simple_input_path); //O arquivo deve vir por aqui
		
		int lineNumber = 4; //número de linhas do arquivo
		Assert.assertEquals(lineNumber, file.length);
		
		String firstline = "[intro]"; //primeira linha do arquivo
		Assert.assertEquals(firstline, file[0]);
	}
	
	@Test
	public void testWritingDeleting() throws IOException{
		FileHandler fileHandler = new FileHandler();
		
		String[] file = fileHandler.readFile(simple_input_write_path); //O arquivo deve vir por aqui
		
		String newLine = "[intro2]";
		
		//Escrita a nova linha no final do texto
		fileHandler.writeOnFile(file, newLine, file.length);
		
		//Arquivo é relido
		file = fileHandler.readFile(simple_input_write_path);
		
		//Verificamos se a última linha corresponde à nova linha adicionada
		Assert.assertEquals(file[file.length-1], newLine);
		
		//Deletamos a última linha
		fileHandler.deleteLineOnFile(file, file.length-1);
		
		//Arquivo é relido
		file = fileHandler.readFile(simple_input_write_path);
		
		//Comparamos se a última linha realmente mudou
		Assert.assertEquals(false, file[file.length-1].equals(newLine));
	}
	
	@Test
	public void testWritingDeletingLineInbetween() throws IOException
	{
		FileHandler fileHandler = new FileHandler();
		
		String[] file = fileHandler.readFile(simple_input_write_path); //O arquivo deve vir por aqui
		
		String newLine = "[intro2]";
		String oldLine = file[2];
		
		//Escrita a nova linha na linha 2
		fileHandler.writeOnFile(file, newLine, 2);
		
		file = fileHandler.readFile(simple_input_write_path);
		
		//Verifica se a linha foi corretamente inserida
		Assert.assertEquals(newLine, file[2]);
		
		//Deleta a segunda linha para deixar o texto como estava
		fileHandler.deleteLineOnFile(file, 2);
		
		file = fileHandler.readFile(simple_input_write_path);
		
		//Verifica se a linha mudou
		Assert.assertEquals(oldLine, file[2]);
	}
	
	@Test
	public void testDeletingLines() throws IOException
	{
		FileHandler fileHandler = new FileHandler();
		
		String[] file = fileHandler.readFile(simple_input_write_path); //O arquivo deve vir por aqui
		int lines = file.length;
		
		String oldLine = file[2];

		//Escrita de 5 linhas para ter conteúdo para ser apagado
		for (int i = 0; i < 5; i++) {
			fileHandler.writeOnFile(file, Calendar.getInstance()+"\r", 2);
			file = fileHandler.readFile(simple_input_write_path);
		}
		
		file = fileHandler.readFile(simple_input_write_path);
		
		//Deleta várias vezes a linha 2
		for (int i = 0; i < 5; i++) {
			fileHandler.deleteLineOnFile(file, 2);
			file = fileHandler.readFile(simple_input_write_path);
		}
		
		Assert.assertEquals(oldLine, file[2]);
		Assert.assertEquals(lines, file.length);
	}
	
	@Test
	public void testWritingDeletingMultipleLines() throws IOException
	{
		FileHandler fileHandler = new FileHandler();
		
		String[] file = fileHandler.readFile(simple_input_write_multi_path); //O arquivo deve vir por aqui
		String oldLastLine = file[file.length-1]; //Ultima linha guardada para comparação futura
		
		String[] newLine = new String[]{"[intro2]", "texto1", "texto1", "texto1"}; //Novas linhas que serão adicionadas
		
		int lines = file.length;
		int newLinesNumber = 4;
		
		//Escrito no arquivo as novas linhas
		fileHandler.writeOnFile(file, newLine, file.length);
		
		//O arquivo é relido
		file = fileHandler.readFile(simple_input_write_multi_path);
		
		//As novas linhas são comparadas uma a uma para verificar se foram corretamente inseridas
		for (int i = lines, j = 0; i < lines + newLinesNumber; i++, j++) {
			Assert.assertEquals(file[i], newLine[j]);
		}
		
		//As linhas que foram inseridas são deletadas
		fileHandler.deleteLineOnFile(file, file.length-newLinesNumber, file.length - 1);

		//O arquivo é relido
		file = fileHandler.readFile(simple_input_write_multi_path);
		
		//Verificamos se a última linha do arquivo corresponde a antiga última linha guardada
		Assert.assertEquals(file[file.length-1], oldLastLine);

		//Verifica se o arquivo tem o mesmo número de linhas que originalmente tinha
		Assert.assertEquals(lines, file.length);
	}
}
