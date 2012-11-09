package persistence;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
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
		DataInputStream in = null;
		BufferedReader br = null;
		
		try {
			//Lê o arquivo que está no path
			fstream = new FileInputStream(path);
			
			//Carrega as classes de leitura
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			
			//Instanciado uma String para a leitura linha a linha
			String strLine;
			while ((strLine = br.readLine()) != null) {
				if(text == ""){
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
			br.close();
			in.close();
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
	 */
	public void writeOnFile(String[] file, String newText, int line)
	{
		
	}
	
	/**
	 * Método para escrever um texto em uma determinada posição em um
	 * arquivo deslocando o resto do texto (se houver) para as linhas 
	 * mais abaixo
	 *  
	 * @param file
	 * @param newText
	 * @param line
	 */
	public void writeOnFile(String[] file, String[] newText, int line)
	{
		
	}
	
	/**
	 * Método para remover uma linha do texto e reposicionar as linhas
	 * abaixo (se houverem) para uma posição acima
	 * 
	 * @param file
	 * @param line
	 */
	public void deleteLineOnFile(String [] file, int line)
	{
		
	}
	
	/**
	 * Método para remover um intervalo contínuo de linhas do texto e 
	 * reposicionar as linhas abaixo (se houverem) para as posições 
	 * acima
	 * 
	 * @param file
	 * @param begin
	 * @param end
	 */
	public void deleteLineOnFile(String [] file, int begin, int end)
	{
		
	}
}
