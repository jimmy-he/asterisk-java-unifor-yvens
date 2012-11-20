package model;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe modelo da Sala de Conferência
 * 
 * Contém as informações relevantes à sala de conferência
 * 
 * @author daniel
 * 
 */

public class ConferenceRoom {

	private String number;
	private String context; // conferência só ocorre entre números em um mesmo
							// contexto
	private boolean announceUserCount; // anuncia número de usuários quando
										// alguém entra
	private boolean musicOnHold; // toca música de espera quando só tem 1 pessoa
									// na sala de conferência
	private boolean quietMode; // não toca músicas

	/**
	 * Construtor com os parâmetros que normalmente serão inseridos e os demais
	 * valores serão os padrões
	 * 
	 * @param number
	 * @param context
	 */

	public ConferenceRoom(String number, String context) {
		this(number, context, true, true, false);
	}

	/**
	 * Construtor com todos os parâmetros
	 * 
	 * @param number
	 * @param context
	 * @param announceUserCount
	 * @param musicOnHold
	 * @param quietMode
	 */

	public ConferenceRoom(String number, String context,
			boolean announceUserCount, boolean musicOnHold, boolean quietMode) {
		super();
		this.number = number;
		this.context = context;
		this.announceUserCount = announceUserCount;
		this.musicOnHold = musicOnHold;
		this.quietMode = quietMode;
	}

	/**
	 * Método para retornar a conferência no formato que deve ser escrita no
	 * extensions.conf
	 * 
	 * @return retorna uma String a ser adicionada como comando da rota no
	 *         arquivo extensions.conf
	 */
	public String toConference() {
		String conference = "ConfBridge(" + number + ","
				+ ((announceUserCount) ? "c" : "") + ""
				+ ((musicOnHold) ? "M" : "") + "" + ((quietMode) ? "q" : "")
				+ ")";

		return conference;
	}

	public static ConferenceRoom getConferenceFromParameter(
			HttpServletRequest request) {
		ConferenceRoom conference = null;

		String number = "";
		String context = "";
		boolean announceUserCount = false;
		boolean musicOnHold = false;
		boolean quietMode = false;

		if (request.getParameter("number") != null) {
			number = request.getParameter("number");
		}

		if (request.getParameter("context") != null) {
			context = request.getParameter("context");
		}

		if (request.getParameter("announceUserCount") != null) {
			announceUserCount = (request.getParameter("announceUserCount")
					.equalsIgnoreCase("true")) ? true : false;
		}

		if (request.getParameter("musicOnHold") != null) {
			musicOnHold = (request.getParameter("musicOnHold")
					.equalsIgnoreCase("true")) ? true : false;
		}

		if (request.getParameter("quietMode") != null) {
			quietMode = (request.getParameter("quietMode")
					.equalsIgnoreCase("true")) ? true : false;
		}

		conference = new ConferenceRoom(number, context, announceUserCount,
				musicOnHold, quietMode);
		return conference;
	}

	/**
	 * 
	 * @param request
	 */
	public void conferenceToRequest(HttpServletRequest request) {

		request.setAttribute("number", number);
		request.setAttribute("context", context);
		request.setAttribute("announceUserCount", (announceUserCount) ? "yes"
				: "no");
		request.setAttribute("musicOnHold", (musicOnHold) ? "yes" : "no");
		request.setAttribute("quietMode", (quietMode) ? "yes" : "no");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		ConferenceRoom other = (ConferenceRoom) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public boolean isAnnounceUserCount() {
		return announceUserCount;
	}

	public void setAnnounceUserCount(boolean announceUserCount) {
		this.announceUserCount = announceUserCount;
	}

	public boolean isMusicOnHold() {
		return musicOnHold;
	}

	public void setMusicOnHold(boolean musicOnHold) {
		this.musicOnHold = musicOnHold;
	}

	public boolean isQuietMode() {
		return quietMode;
	}

	public void setQuietMode(boolean quietMode) {
		this.quietMode = quietMode;
	}

}
