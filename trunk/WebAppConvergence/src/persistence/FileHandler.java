package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe responsável pela leitura e alteração em arquivos
 * 
 * Inicialmente, a idéia é que os arquivos do asterisk serão carregados em disco 
 * na máquina que estará fazendo as alterações, modificada e salva localmente, 
 * e depois reenviada ao servidor.
 * 
 * @author yvens
 *
 */
public class FileHandler {

	//Essa variável ativa/desativa a escrita de logs de debug no console
	public static boolean DEBUG = false;
	
	//Essa variável interna da classe mantém o path original do arquivo recentemente lido
	private String path;
	
	/**
	 * Construtor default
	 */
	public FileHandler()
	{
	}
	
	/**
	 * Método para ler um arquivo de um determinado path
	 * 
	 * @param path
	 * @return um vetor de String através de um split em "/r/n" do arquivo lido
	 * @throws IOException 
	 */
	public String[] readFile(String path) throws IOException
	{
		String text = "";
		String file[] = null;
		
		FileInputStream fstream = null;
		DataInputStream ds = null;
		InputStreamReader is = null;
		BufferedReader br = null;
		
		try {
			//Lê o arquivo que está no path
			fstream = new FileInputStream(path);
			
			//Carrega as classes de leitura
			ds = new DataInputStream(fstream);
			is = new InputStreamReader(ds);
			br = new BufferedReader(is);
			
			//Instanciado uma String para a leitura linha a linha
			String strLine;
			while ((strLine = br.readLine()) != null) {
				if(text.equals("")){
					text += strLine;
				}
				else
				{
					text += "\r\n"+strLine;
				}
			}
		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		} finally {
			this.path = path; 
			
			br.close();
			is.close();
			ds.close();
			fstream.close();
		}
		
		file = text.split("\r\n");
		
		return file;
	}
	
	/**
	 * Método para escrever uma linha de texto em uma determinada posição
	 * em um arquivo deslocando o resto do texto (se houver) para as linhas 
	 * mais abaixo
	 *  
	 * @param file
	 * @param newText
	 * @param line
	 * @throws IOException 
	 */
	public void writeOnFile(String[] file, String newText, int line) throws IOException
	{
		//Esse método deve, inicialmente, criar um novo vetor de linhas
		//que seja o número de linhas antigas mais 1 (da linha nova)
		String[] newFile = new String[file.length + 1];
		
		//Esse laço vai copiar todas as linhas do texto antigo para o novo
		//até o índice I for igual à linha onde será inserida a nova
		//linha, nesse momento a nova linha é colocada e daí em diante o
		//backUp continua, mas com I - 1 no arquivo original para não repercurtir
		//os efeitos da adição da nova linha
		for (int i = 0; i < newFile.length; i++) {
			if(i < line)
			{
				newFile[i] = file[i];
			}
			else if(i == line)
			{
				newFile[i] = newText;
			}
			else
			{
				newFile[i] = file[i - 1];	
			}
		}
		
		//Depois desse processo, chamamos o método de salvar o arquivo em disco
		//usando seu path original, que foi mantido na variável PATH durante sua
		//leitura
		outputFile(newFile);
	}
	
	/**
	 * Método para escrever um texto em uma determinada posição em um
	 * arquivo deslocando o resto do texto (se houver) para as linhas 
	 * mais abaixo
	 *  
	 * @param file
	 * @param newText
	 * @param line
	 * @throws IOException 
	 */
	public void writeOnFile(String[] file, String[] newText, int line) throws IOException
	{
		//Adiciona uma linha a mais no documento caso a última linha não seja um espaço vazio
		int aditionalLine = (file[file.length - 1].isEmpty()) ? 1 : 0;
		
		//Esse método deve, inicialmente, criar um novo vetor de linhas
		//que seja o número de linhas antigas mais o número de linhas novas
		String[] newFile = new String[file.length + newText.length + aditionalLine];
		
		//Esse laço vai copiar todas as linhas do texto antigo para o novo
		//até o índice I for igual à linha onde será inserida as novas
		//linhas, nesse momento as novas linhas serão colocadas e daí em diante o
		//backUp continua, mas com I - 'qtde. linhas novas' no arquivo original 
		//para não repercurtir os efeitos da adição das novas linhas

		//Caso a linha seja maior do que o tamanho do arquivo
		//Setamos a linha para ser na última linha do arquivo
		if(line > file.length){
			//A adição de uma linha caso a última linha possua conteúdo
			line = (file.length - 1) + aditionalLine;
		}
		
		for (int i = 0, j = 0; i < newFile.length; i++) {
			if(i < line)
			{
				newFile[i] = file[i];
			}
			else if(i <= line + newText.length - 1)
			{
				newFile[i] = newText[j];
				j++;
			}
			else
			{
				newFile[i] = file[i - newText.length - 1];	
			}
		}
		
		//Depois desse processo, chamamos o método de salvar o arquivo em disco
		//usando seu path original, que foi mantido na variável PATH durante sua
		//leitura
		outputFile(newFile);
	}
	
	/**
	 * Método para remover uma linha do texto e reposicionar as linhas
	 * abaixo (se houverem) para uma posição acima
	 * 
	 * @param file
	 * @param line
	 * @throws IOException 
	 */
	public void deleteLineOnFile(String [] file, int line) throws IOException
	{
		//Esse método deve, inicialmente, criar um novo vetor de linhas
		//que seja o número de linhas antigas menos 1 (da linha removida)
		String[] newFile = new String[file.length - 1];
		
		//Esse laço vai copiar todas as linhas do texto antigo para o novo
		//até o índice I for igual à linha onde será removida a linha,
		//nesse momento a nova linha é colocada e daí em diante o
		//backUp continua, mas com I + 1 no arquivo original para não repercurtir
		//os efeitos da remoção da nova linha
		for (int i = 0; i < file.length; i++) {
			if(i < line)
			{
				newFile[i] = file[i];
			}
			else if(i > line)
			{
				newFile[i - 1] = file[i];	
			}
		}
		
		//Depois desse processo, chamamos o método de salvar o arquivo em disco
		//usando seu path original, que foi mantido na variável PATH durante sua
		//leitura
		outputFile(newFile);
	}
	
	/**
	 * Método para remover um intervalo contínuo de linhas do texto e 
	 * reposicionar as linhas abaixo (se houverem) para as posições 
	 * acima
	 * 
	 * @param file
	 * @param begin
	 * @param end
	 * @throws IOException 
	 */
	public void deleteLineOnFile(String [] file, int begin, int end) throws IOException
	{
		//Esse método deve, inicialmente, criar um novo vetor de linhas
		//que seja o número de linhas antigas menos o número de linhas removidas
		String[] newFile = new String[file.length - (end - begin) - 1];
		
		//Esse laço vai copiar todas as linhas do texto antigo para o novo
		//até o índice I for igual ao início das linhas que devem ser removidas,
		//nesse momento ignoramos todas as linhas que vierem até chegar ao ponto end,
		//daí o backUp continua, mas com I + 'end + 1' no arquivo original para não 
		//repercurtir os efeitos da remoção da nova linha
		for (int i = 0; i < file.length; i++) {
			if(i < begin)
			{
				newFile[i] = file[i];
			}
			else if(i > end)
			{
				
				newFile[i - (end - begin)] = file[i];	
			}
			
		}		
		
		//Depois desse processo, chamamos o método de salvar o arquivo em disco
		//usando seu path original, que foi mantido na variável PATH durante sua
		//leitura
		outputFile(newFile);
	}
	
	/**
	 * Método para escrever um vetor de strings em um arquivo, substituindo-o
	 * caso ele exista
	 * 
	 * Na forma default, sem a passagem do path, o objeto usa o path salvo quando
	 * o arquivo foi lido. Caso não exista path salvo, é lançada uma exceção.
	 * 
	 * @param file
	 * @param path
	 * @throws IOException 
	 */
	public void outputFile(String[] file) throws IOException
	{
		if(null != path && !path.isEmpty()){
			outputFile(file, path);
		}else{
			throw new IOException("Nenhum PATH especificado");
		}
	}
	
	/**
	 * Método para escrever um vetor de strings em um arquivo, substituindo-o
	 * caso ele exista
	 * 
	 * @param file
	 * @param path
	 * @throws IOException 
	 */
	public void outputFile(String[] file, String path) throws IOException
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		file = normalizeFile(file);
		
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);

			for (int i = 0; i < file.length; i++) {
				bw.write(file[i]);
				if(i != file.length - 1){
					bw.newLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			bw.close();
			fw.close();
		}
	}
	
	/**
	 * Procura por linhas nulas no arquivo e inicializa com o valor ""
	 * 
	 * @param file
	 * @return retorna o arquivo sem linhas null
	 */
	private String[] normalizeFile(String[] file){
		for (int i = 0; i < file.length; i++) {
			if(file[i] == null){
				file[i] = "";
			}
		}
		
		return file;
	}
}
