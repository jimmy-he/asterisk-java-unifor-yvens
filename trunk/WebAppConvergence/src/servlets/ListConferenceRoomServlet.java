package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ConferenceRoom;
import model.dial.DialPlan;
import model.dial.DialRoute;
import asterisk.ExtensionHandler;

/**
 * Servlet implementation class ListConferenceRoomServlet
 */
@WebServlet("/ListConferenceRoomServlet")
public class ListConferenceRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListConferenceRoomServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = "";
		String feedback = "";

		String forward = "/Pages/Application/tableConferencias.jsp";
		try {
			ExtensionHandler handler = new ExtensionHandler();
			
			//Atributo deve vir do request indicando a tag do DialPlan
			String tag = request.getParameter("tag");
			
			if(request.getParameter("remover") != null){
				//Manda o comando para remover o DialRoute com a conferência que possua a tag recebida pela URL
				DialPlan dialPlan = handler.getDialPlan(tag);
				String identifier = request.getParameter("remover");
				DialRoute dialRoute = dialPlan.getRoute(identifier);
				dialPlan.removeRoute(dialRoute);
				boolean remocao = handler.updateDialPlan(dialPlan);
				
				if(remocao){
					feedback = "Conferência removida com sucesso!";
				}else{
					error = "Algum outro usuário está alterando o arquivo, por favor, tente novamente!";
				}
			}
			
			//Preenchendo o feedback de alteração de plano
			if (request.getAttribute("feedback") != null){				
				feedback = (String) request.getAttribute("feedback");
			}
			
			List<ConferenceRoom> list = handler.listConferenceRooms();
			
			request.setAttribute("list", list);
		} catch (Exception e) {
			error = e.getMessage();
			e.printStackTrace();
		} finally {
			request.setAttribute("feedback", feedback);
			request.setAttribute("error", error);
			getServletContext().getRequestDispatcher(forward).forward(request, response);
		}
	}

}
