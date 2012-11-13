package asterisk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import model.RamalSip;
import model.RamalSipType;
import persistence.FileHandler;
import exception.SipConfigException;

/**
 * Classe responsável por realizar todas as operações relativas aos ramais, 
 * como criação, alteração, listagem e deleção de ramais.
 * 
 * @author yvens
 *
 */
public class RamalSipHandler {

	private FileHandler fileHandler;
	private String sipConfPath;
	private int sipConfLines;
	
	//Semáforo para evitar modificações concorrentes nos arquivos do ramal
	private static final Semaphore mutex;
	static{
		mutex = new Semaphore(1);
	}
	
	public RamalSipHandler() throws IOException, SipConfigException{
		this(AsteriskConfiguration.SIP_CONFIG_PATH);
	}
	
	public RamalSipHandler(String sipConfPath) throws IOException, SipConfigException{
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
	public boolean createRamal(RamalSip ramal) throws InterruptedException{
		if(mutex.tryAcquire()){
			
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
	public boolean deleteRamal(RamalSip ramal) throws InterruptedException{
		if(mutex.tryAcquire()){
			
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
	public boolean updateRamal(RamalSip ramal) throws InterruptedException{
		if(mutex.tryAcquire()){
			
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
	 * @throws IOException 
	 */
	public List<RamalSip> listRamal() throws IOException{
		String[] sipConfFile = fileHandler.readFile(sipConfPath);
		List<RamalSip> listRamal = new ArrayList<RamalSip>();
		
		for (int i = 0; i < sipConfFile.length; i++) {
			//Verificando se acha uma TAG no estilo [4666]
			if(!sipConfFile[i].isEmpty() && sipConfFile[i].charAt(0) == '[' && !sipConfFile[i].equals("[general]")){
				RamalSip ramal = null;
				
				//1 - TAG
				String tag = sipConfFile[i++];

				//Declaração das variáveis do RAMAL
				String callerId = "";
				RamalSipType type = null;
				String accountCode = null;
				String username = null;
				String secret = null;
				boolean canReinvite = false;
				String context = null;
				String dtmfMode = null;
				int callLimit = 0;
				boolean nat = false;
				
				while(i < sipConfFile.length && !sipConfFile[i].equals("")){
					String parameters[] = sipConfFile[i++].split("=");
					if(parameters[0].equals("callerid")){
						callerId = parameters[1];
					}else if(parameters[0].equals("type")){
						type = RamalSipType.getRamalType(sipConfFile[i++].split("=")[1]);
					}else if(parameters[0].equals("accountcode")){
						accountCode = parameters[1];
					}else if(parameters[0].equals("username")){
						username = parameters[1];
					}else if(parameters[0].equals("secret")){
						secret = parameters[1];
					}else if(parameters[0].equals("canreinvite")){
						canReinvite = (parameters[1].equals("yes")) ? true : false;
					}else if(parameters[0].equals("host")){
						callerId = parameters[1];
					}else if(parameters[0].equals("context")){
						context = parameters[1];
					}else if(parameters[0].equals("dtmfmode")){
						dtmfMode = parameters[1];
					}else if(parameters[0].equals("call-limit")){
						callLimit = Integer.parseInt(parameters[1]);
					}else if(parameters[0].equals("nat")){
						nat = (parameters[1].equals("yes")) ? true : false;
					}
					
					i++;
				}
				
				ramal = new RamalSip(tag, callerId, type, accountCode, username, secret, canReinvite, context, dtmfMode, callLimit, nat);
				
				listRamal.add(ramal);
			}
		}
		return listRamal;
	}

	public int getSipConfLines() {
		return sipConfLines;
	}
}
