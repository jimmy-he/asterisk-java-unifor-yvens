package connection;
/**
 * Singleton class.
 * 
 * Classe responsável por guardar os valores estáticos da conexão, como IP do servidor.
 * 
 * @author yvens
 *
 */
public class ConnectionProperties {

	private static ConnectionProperties properties;
	public static ConnectionProperties getInstace()
	{
		if(properties == null){
			properties = new ConnectionProperties();
		}
		
		return properties;
	}
	
	private String server;
	
	private ConnectionProperties(){
		this("");
	}
	
	private ConnectionProperties(String server) {
		super();
		this.server = server;
	}
	
	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
}
