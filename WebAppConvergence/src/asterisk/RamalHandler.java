package asterisk;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;

import exception.SipConfigException;

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
	private String sipConfPath;
	private int sipConfLines;
	
	//Semáforo para evitar modificações concorrentes nos arquivos do ramal
	private static final Semaphore mutex;
	static{
		mutex = new Semaphore(1);
	}
	
	public RamalHandler() throws IOException, SipConfigException{
		this(AsteriskConfiguration.SIP_CONFIG_PATH);
	}
	
	public RamalHandler(String sipConfPath) throws IOException, SipConfigException{
		fileHandler = new FileHandler();
		this.sipConfPath = sipConfPath;
		if(!sipConfCertified()){
			throw new SipConfigException();
		}
	}
	
	/**
	 * Método para fazer a verificação do PATH do sip.conf passado
	 * 
	 * @return verdadeiro caso seja realizada uma leitura com sucesso do arquivo do PATH passado
	 * @throws IOException 
	 */
	private boolean sipConfCertified() throws IOException{
		String[] sipConf = fileHandler.readFile(sipConfPath);
		this.sipConfLines= sipConf.length; 
		return sipConf.length > 0;
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

	public int getSipConfLines() {
		return sipConfLines;
	}
}
