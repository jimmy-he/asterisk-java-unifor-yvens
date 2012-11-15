package model;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe modelo do Ramal IAX
 * 
 * Contém as informações relevantes ao ramal IAX
 * 
 * @author daniel
 * 
 */
public class RamalIAX {

	/**
	 * Exemplo de Ramal:
	 * 
	 * [3000] :Tag (String) callerid="Ramal" <3000> :CallerId (String)
	 * type=friend :Type (Enum) defaultuser=3000 :DefaultUser (String)
	 * secret=lab$3000 :Secret (String) context=LOCAL :Context (String)
	 * host=dynamic :Host (String) auth=md5 :Auth (String) transfer=yes
	 * :Transfer (boolean) requirecalltoken=no :RequireCallToken (boolean)
	 */

	private String tag;
	private String callerId;
	private RamalIAXType type;
	private String defaultUser;
	private String secret;
	private String context;
	private String host;
	private String auth;
	private boolean transfer;
	private boolean requireCallToken;

	/**
	 * Construtor com os parâmetros que normalmente serão inseridos e os demais
	 * valores serão os padrões dos exemplos passados em sala de aula
	 * 
	 * @param tag
	 * @param callerId
	 * @param defaultUser
	 *            ;
	 * @param secret
	 */
	public RamalIAX(String tag, String callerId, String defaultUser,
			String secret) {
		this(tag, callerId, RamalIAXType.FRIEND, defaultUser, secret, "LOCAL",
				"dynamic", "md5", true, false);
	}

	/**
	 * Construtor com todos os parâmetros
	 * 
	 * @param tag
	 * @param callerId
	 * @param type
	 * @param defaultUser
	 * @param secret
	 * @param context
	 * @param host
	 * @param auth
	 * @param transfer
	 * @param requireCallToken
	 */

	public RamalIAX(String tag, String callerId, RamalIAXType type,
			String defaultUser, String secret, String context, String host,
			String auth, boolean transfer, boolean requireCallToken) {
		super();
		this.tag = tag;
		this.callerId = callerId;
		this.type = type;
		this.defaultUser = defaultUser;
		this.secret = secret;
		this.context = context;
		this.host = host;
		this.auth = auth;
		this.transfer = transfer;
		this.requireCallToken = requireCallToken;
	}

	/**
	 * Método para retornar o RAMAL no formato que deve ser escrito no iax.conf
	 * 
	 * @return retorna um vetor de Strings com as linhas a serem adicionadas no
	 *         arquivo iax.conf
	 */
	public String[] toRamalIAX() {
		String[] ramalIAX = new String[10];
		ramalIAX[0] = "\r[" + tag + "]\r";
		ramalIAX[1] = "callerid=" + callerId + "\r";
		ramalIAX[2] = "type=" + type.toString() + "\r";
		ramalIAX[3] = "defaultuser=" + defaultUser + "\r";
		ramalIAX[4] = "secret=" + secret + "\r";
		ramalIAX[5] = "context=" + context + "\r";
		ramalIAX[6] = "host=" + host + "\r";
		ramalIAX[7] = "auth=" + auth + "\r";
		ramalIAX[8] = "transfer=" + ((transfer) ? "yes" : "no") + "\r";
		ramalIAX[9] = "requirecalltoken=" + ((requireCallToken) ? "yes" : "no")
				+ "";

		return ramalIAX;
	}

	public static RamalIAX getRamalFromParameter(HttpServletRequest request) {
		RamalIAX ramal = null;

		String tag = "";
		String callerId = "";
		RamalIAXType type = null;
		String defaultUser = "";
		String secret = "";
		String context = "";
		String host = "";
		String auth = "";
		boolean transfer = false;
		boolean requireCallToken = false;

		if (request.getParameter("tag") != null) {
			tag = request.getParameter("tag");
		}
		if (request.getParameter("callerId") != null) {
			callerId = request.getParameter("callerId");
		}
		if (request.getParameter("type") != null) {
			type = RamalIAXType.getRamalType(request.getParameter("type"));
		}
		if (request.getParameter("defaultUser") != null) {
			defaultUser = request.getParameter("defaultUser");
		}
		if (request.getParameter("secret") != null) {
			secret = request.getParameter("secret");
		}

		if (request.getParameter("context") != null) {
			context = request.getParameter("context");
		}
		if (request.getParameter("host") != null) {
			host = request.getParameter("host");
		}

		if (request.getParameter("auth") != null) {
			auth = request.getParameter("auth");
		}
		if (request.getParameter("transfer") != null) {
			transfer = (request.getParameter("transfer").equals("yes")) ? true
					: false;
		}

		if (request.getParameter("requireCallToken") != null) {
			requireCallToken = (request.getParameter("requireCallToken")
					.equals("yes")) ? true : false;
		}

		ramal = new RamalIAX(tag, callerId, type, defaultUser, secret, context,
				host, auth, transfer, requireCallToken);
		return ramal;
	}

	/**
	 * 
	 * @param request
	 */
	public void ramalToRequest(HttpServletRequest request) {

		request.setAttribute("tag", tag);
		request.setAttribute("callerId", callerId);
		request.setAttribute("type", type);
		request.setAttribute("defaultUser", defaultUser);
		request.setAttribute("secret", secret);
		request.setAttribute("context", context);
		request.setAttribute("host", host);
		request.setAttribute("auth", auth);
		request.setAttribute("transfer", (transfer) ? "yes" : "no");
		request.setAttribute("requireCallToken", (requireCallToken) ? "yes"
				: "no");
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
		RamalIAX other = (RamalIAX) obj;
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

	public RamalIAXType getType() {
		return type;
	}

	public void setType(RamalIAXType type) {
		this.type = type;
	}

	public String getDefaultUser() {
		return defaultUser;
	}

	public void setDefaultUser(String defaultUser) {
		this.defaultUser = defaultUser;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public boolean isTransfer() {
		return transfer;
	}

	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}

	public boolean isRequireCallToken() {
		return requireCallToken;
	}

	public void setRequireCallToken(boolean requireCallToken) {
		this.requireCallToken = requireCallToken;
	}

}
