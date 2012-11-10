package model;

/**
 * Classe modelo do Ramal
 * 
 * Contém as informações relevantes ao ramal
 * 
 * @author yvens
 *
 */
public class Ramal {

	/**
	 * Exemplo de Ramal:
	 
	    [4600]                         :Tag            (String)
		callerid="Ramal-SIP" <4600>    :CallerId       (String)
		type=friend                    :Type           (Enum)
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
	private RamalType type;
	private String username;
	private String secret;
	private boolean canReinvite;
	private String context;
	private String dtmfMode;
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
	public Ramal (String tag, String callerId, String username, String secret){
		this(tag, callerId, RamalType.FRIEND, username, secret, false, "LOCAL", "rfc2833", 2, false);
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
	 * @param context
	 * @param dtmfMode
	 * @param callLimit
	 * @param nat
	 */
	private Ramal(String tag, String callerId, RamalType type, String username,
			String secret, boolean canReinvite, String context,
			String dtmfMode, int callLimit, boolean nat) {
		super();
		this.tag = tag;
		this.callerId = callerId;
		this.type = type;
		this.username = username;
		this.secret = secret;
		this.canReinvite = canReinvite;
		this.context = context;
		this.dtmfMode = dtmfMode;
		this.callLimit = callLimit;
		this.nat = nat;
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
	public RamalType getType() {
		return type;
	}
	public void setType(RamalType type) {
		this.type = type;
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
