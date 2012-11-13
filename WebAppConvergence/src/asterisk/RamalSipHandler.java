package asterisk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import model.RamalSip;
import model.RamalSipType;
import persistence.FileHandler;
import exception.RamalSipException;
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
		this.sipConfPath = sipConfPath;
		fileHandler = new FileHandler();
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
	 * @throws IOException 
	 * @throws SipConfigException 
	 */
	public boolean createRamal(RamalSip ramal) throws InterruptedException, IOException, RamalSipException{
		if(mutex.tryAcquire()){
			
			//Lê o arquivo sip.conf
			String[] sipConf = fileHandler.readFile(sipConfPath);
			
			//Busca pela tag que queremos adicionar
			for (int i = 0; i < sipConf.length; i++) {
				//Lança uma excessão caso já exista a tag enviada
				if(sipConf[i].equals("["+ramal.getTag()+"]")){
					throw new RamalSipException();
				}
			}
			
			//Gera as linhas que devem ser adicionadas ao sip.conf
			String[] newRamal = ramal.toRamalSip();
			
			//Escreve as linhas no arquivo sip.conf
			fileHandler.writeOnFile(sipConf, newRamal, sipConf.length);
			
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
	 * @throws IOException 
	 * @throws RamalSipException 
	 */
	public boolean deleteRamal(RamalSip ramal) throws InterruptedException, IOException, RamalSipException{
		if(mutex.tryAcquire()){
			
			//Lê o arquivo sip.conf
			String[] sipConf = fileHandler.readFile(sipConfPath);
			
			//Busca pelas linhas que contém o ramal que deve ser apagado
			int begin = -1;
			int end = -1;
			for (int i = 0; i < sipConf.length; i++) {
				if(sipConf[i].equals("["+ramal.getTag()+"]")){
					begin = i;
					
					//Itera pelas linhas do ramal até chegar ao fim do arquivo ou a uma linha em branco (final do ramal)
					while(i < sipConf.length && !sipConf[i].equals("")){
						i++;
					}
					end = --i;
					
					break;
				}
			}
			
			//Caso não tenha sido encontrado o ramal, é lançada uma excessão
			if(begin == -1 || end == -1){
				throw new RamalSipException("Não existe o Ramal passado. Tag = "+ramal.getTag());
			}
			
			//Enviado o comando para apagar as linhas correspondentes ao ramal
			fileHandler.deleteLineOnFile(sipConf, begin, end);
			
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
	 * @throws IOException 
	 */
	public boolean updateRamal(RamalSip ramal) throws InterruptedException, IOException{
		if(mutex.tryAcquire()){
			
			//Lê o arquivo sip.conf
			String[] sipConf = fileHandler.readFile(sipConfPath);
			String[] updatedRamal = ramal.toRamalSip();
			
			//Busca pelas linhas que contém o ramal que deve ser alterado
			for (int i = 0; i < sipConf.length; i++) {
				//Procuramos pela linha que contenha a tag do ramal desejado
				if(sipConf[i].equals(ramal.getTag())){
					//Iteramos todas as linhas de propriedades do ramal atualizado
					for (int j = 0; j < updatedRamal.length; j++, i++) {
						//Para cada linha diferente entre a versão atual e a atualizada
						if(!updatedRamal[j].equals(sipConf[i])){
							//Deletamos a linha atual
							fileHandler.deleteLineOnFile(sipConf, i);
							//Escrevemos a linha nova no lugar
							fileHandler.writeOnFile(sipConf, updatedRamal[j], i);
						}
					}
				}
			}
			
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
				String tag = sipConfFile[i];
				tag = tag.substring(1, tag.length()-1);
				i++;

				//Declaração das variáveis do RAMAL
				String callerId = "";
				RamalSipType type = null;
				String accountCode = null;
				String username = null;
				String secret = null;
				boolean canReinvite = false;
				String host = null;
				String context = null;
				String dtmfMode = null;
				int callLimit = 0;
				boolean nat = false;
				
				while(i < sipConfFile.length && !sipConfFile[i].equals("")){
					String parameters[] = sipConfFile[i++].split("=");
					if(parameters[0].equals("callerid")){
						callerId = parameters[1];
					}else if(parameters[0].equals("type")){
						type = RamalSipType.getRamalType(parameters[1]);
					}else if(parameters[0].equals("accountcode")){
						accountCode = parameters[1];
					}else if(parameters[0].equals("username")){
						username = parameters[1];
					}else if(parameters[0].equals("secret")){
						secret = parameters[1];
					}else if(parameters[0].equals("canreinvite")){
						canReinvite = (parameters[1].equals("yes")) ? true : false;
					}else if(parameters[0].equals("host")){
						host = parameters[1];
					}else if(parameters[0].equals("context")){
						context = parameters[1];
					}else if(parameters[0].equals("dtmfmode")){
						dtmfMode = parameters[1];
					}else if(parameters[0].equals("call-limit")){
						callLimit = Integer.parseInt(parameters[1]);
					}else if(parameters[0].equals("nat")){
						nat = (parameters[1].equals("yes")) ? true : false;
					}
					
					ramal = new RamalSip(tag, callerId, type, username, secret, canReinvite, host, context, dtmfMode, accountCode, callLimit, nat);  
					i++;
				}
				
				listRamal.add(ramal);
			}
		}
		return listRamal;
	}

	public int getSipConfLines() {
		return sipConfLines;
	}
}
