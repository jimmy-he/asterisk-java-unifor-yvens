package asterisk;

/**
 * Classe responsável por manter os paths e informações globais para o correto
 * funcionamento da aplicação junto aos arquivos do asterisk
 * 
 * @author yvens
 * 
 */
public class AsteriskConfiguration {

	//Path do arquivo sip.conf
	public static final String SIP_CONFIG_PATH = "/etc/asterisk/sip.conf";

	//Path do arquivo iax.conf
	public static final String IAX_CONFIG_PATH = "/etc/asterisk/iax.conf";

	//Path do arquivo extensions.conf
	public static final String EXTENSIONS_CONFIG_PATH = "/etc/asterisk/extensions.conf";
	
	//IP de conexão com o servidor do asterisk
	public static final String ASTERISK_IP = "127.0.0.1";
	
	//Porta de conexão com o servidor do asterisk
	public static final int ASTERISK_PORT = 5038;
}
