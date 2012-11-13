package model;

/**
 * Classe modelo do Ramal
 * 
 * Contém as informações relevantes ao ramal
 * 
 * @author yvens
 *
 */
public class RamalSip {

	/**
	 * Exemplo de Ramal:
	 
	    [4600]                         :Tag            (String)
		callerid="Ramal-SIP" <4600>    :CallerId       (String)
		type=friend                    :Type           (Enum)
		accountcode=viglocal           :AccountCode    (String)
		username=4600                  :Username       (String)
		secret=1234                    :Secret         (String)
		canreinvite=no                 :CanReinvite    (boolean)
		host=dynamic                   :Host           (String)
		context=LOCAL                  :Context        (String)
		dtmfmode=rfc2833               :DtmfMode       (String)
		call-limit=2                   :CallLimit      (int)
		nat=no                         :Nat            (boolean)
	 */
	
	private String tag;
	private String callerId;
	private RamalSipType type;
	private String username;
	private String secret;
	private boolean canReinvite;
	private String host; 
	private String context;
	private String dtmfMode;
	private String accountCode;
	private int callLimit;
	private boolean nat;
	
	/**
	 * Construtor com os parâmetros que normalmente serão inseridos e os demais 
	 * valores serão os padrões dos exemplos passados em sala de aula
	 *  
	 * @param tag
	 * @param callerId
	 * @param username
	 * @param secret
	 */
	public RamalSip (String tag, String callerId, String username, String secret){
		this(tag, callerId, RamalSipType.FRIEND, username, secret, false, "dynamic", "LOCAL", "rfc2833", "viglocal", 2, false);
	}
	
	/**
	 * Construtor com todos os parâmetros
	 * 
	 * @param tag
	 * @param callerId
	 * @param type
	 * @param username
	 * @param secret
	 * @param canReinvite
	 * @param host
	 * @param context
	 * @param dtmfMode
	 * @param accountCode
	 * @param callLimit
	 * @param nat
	 */
	public RamalSip(String tag, String callerId, RamalSipType type,
			String username, String secret, boolean canReinvite, String host,
			String context, String dtmfMode, String accountCode, int callLimit,
			boolean nat) {
		super();
		this.tag = tag;
		this.callerId = callerId;
		this.type = type;
		this.username = username;
		this.secret = secret;
		this.canReinvite = canReinvite;
		this.host = host;
		this.context = context;
		this.dtmfMode = dtmfMode;
		this.accountCode = accountCode;
		this.callLimit = callLimit;
		this.nat = nat;
	}

	/**
	 * Método para retornar o RAMAL no formato que deve ser escrito no sip.conf
	 * 
	 * @return retorna um vetor de Strings com as linhas a serem adicionadas no arquivo sip.conf
	 */
	public String[] toRamalSip(){
		String[] ramalSip = new String[12];
		ramalSip[0] = "\r["+tag+"]\r";
		ramalSip[1] = "callerid="+callerId+"\r";
		ramalSip[2] = "type="+type.toString()+"\r";
		ramalSip[3] = "accountcode="+accountCode+"\r";
		ramalSip[4] = "username="+username+"\r";
		ramalSip[5] = "secret="+secret+"\r";
		ramalSip[6] = "canreinvite="+((canReinvite)? "yes" : "no")+"\r";
		ramalSip[7] = "host="+host+"\r";
		ramalSip[8] = "context="+context+"\r";
		ramalSip[9] = "dtmfmode="+dtmfMode+"\r";
		ramalSip[9] = "dtmfmode="+dtmfMode+"\r";
		ramalSip[10] = "call-limit="+callLimit+"\r";
		ramalSip[11] = "nat="+((nat)? "yes" : "no")+"\r";
		
		return ramalSip;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RamalSip other = (RamalSip) obj;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}

	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getCallerId() {
		return callerId;
	}
	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}
	public RamalSipType getType() {
		return type;
	}
	public void setType(RamalSipType type) {
		this.type = type;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public boolean isCanReinvite() {
		return canReinvite;
	}
	public void setCanReinvite(boolean canReinvite) {
		this.canReinvite = canReinvite;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getDtmfMode() {
		return dtmfMode;
	}
	public void setDtmfMode(String dtmfMode) {
		this.dtmfMode = dtmfMode;
	}
	public int getCallLimit() {
		return callLimit;
	}
	public void setCallLimit(int callLimit) {
		this.callLimit = callLimit;
	}
	public boolean isNat() {
		return nat;
	}
	public void setNat(boolean nat) {
		this.nat = nat;
	}
}
