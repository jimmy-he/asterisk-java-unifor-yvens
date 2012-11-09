package persistence;

/**
 * Classe responsável pela leitura e alteração em arquivos
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
	 */
	public String[] readFile(String path)
	{
		return null;
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
