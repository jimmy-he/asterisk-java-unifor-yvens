package model;

/**
 * Classe para representar o ramal dos comandos de iax2 show peers
 * 
 * @author daniel
 * 
 */

public class MonitorRamalIAX {
	private String name;
	private String host;
	private String dyn;
	private String mask;
	private int port;
	private String status;

	public MonitorRamalIAX() {
		this("", "", "", "", 0, "");
	}

	public MonitorRamalIAX(String name, String host, String dyn, String mask,
			int port, String status) {
		super();
		this.name = name;
		this.host = host;
		this.dyn = dyn;
		this.mask = mask;
		this.port = port;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDyn() {
		return dyn;
	}

	public void setDyn(String dyn) {
		this.dyn = dyn;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
