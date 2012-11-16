package model;

/**
 * Classe para representar o ramal dos comandos de sip show peers
 * @author yvens
 *
 */
public class MonitorRamalSip {

	private String name;
	private String host;
	private String dyn;
	private String forceport;
	private String acl;
	private int port;
	private String status;
	
	public MonitorRamalSip(){
		this("","","","","",0,"");
	}
	
	public MonitorRamalSip(String name, String host, String dyn,
			String forceport, String acl, int port, String status) {
		super();
		this.name = name;
		this.host = host;
		this.dyn = dyn;
		this.forceport = forceport;
		this.acl = acl;
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

	public String getForceport() {
		return forceport;
	}

	public void setForceport(String forceport) {
		this.forceport = forceport;
	}

	public String getAcl() {
		return acl;
	}

	public void setAcl(String acl) {
		this.acl = acl;
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
