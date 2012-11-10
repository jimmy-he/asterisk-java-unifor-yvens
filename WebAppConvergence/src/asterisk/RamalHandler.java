package asterisk;

import java.util.List;
import java.util.concurrent.Semaphore;

import model.Ramal;
import persistence.FileHandler;

/**
 * Classe responsável por realizar todas as operações relativas aos ramais, 
 * como criação, alteração, listagem e deleção de ramais.
 * 
 * @author yvens
 *
 */
public class RamalHandler {

	private FileHandler fileHandler;
	
	//Semáforo para evitar modificações concorrentes nos arquivos do ramal
	private static final Semaphore mutex;
	static{
		mutex = new Semaphore(1);
	}
	
	public RamalHandler(){
		fileHandler = new FileHandler();
	}
	
	/**
	 * Método para a criação de um ramal dentro do arquivo sip.conf
	 * do servidor asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a inserção, e falso
	 * caso alguma outra instância esteja acessando e modificando o arquivo
	 * no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException 
	 */
	public boolean createRamal(Ramal ramal) throws InterruptedException{
		if(mutex.tryAcquire()){
			mutex.acquire();
			
			//TODO 
			
			mutex.release();
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Método para a deleção de um ramal dentro do arquivo sip.conf
	 * do servidor asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a deleção, e falso
	 * caso alguma outra instância esteja acessando e modificando o arquivo
	 * no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException 
	 */
	public boolean deleteRamal(Ramal ramal) throws InterruptedException{
		if(mutex.tryAcquire()){
			mutex.acquire();
			
			//TODO
			
			mutex.release();
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Método para a alteração de um ramal dentro do arquivo sip.conf
	 * do servidor asterisk
	 * 
	 * @return retorna verdadeiro caso consiga realizar a alteração, e falso
	 * caso alguma outra instância esteja acessando e modificando o arquivo
	 * no momento, assim, evitando conflito entre os arquivos
	 * @throws InterruptedException 
	 */
	public boolean updateRamal(Ramal ramal) throws InterruptedException{
		if(mutex.tryAcquire()){
			mutex.acquire();
			
			//TODO
			
			mutex.release();
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Método para buscar a lista de todos os ramais do arquivo sip.conf
	 * 
	 * Esse método acontece mesmo que alguém esteja modificando o arquivo
	 * @return
	 */
	public List<Ramal> listRamal(){
		return null;
	}
}
