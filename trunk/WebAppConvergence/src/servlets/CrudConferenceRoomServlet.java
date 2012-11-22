package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ConferenceRoom;
import model.dial.DialCommand;
import model.dial.DialPlan;
import model.dial.DialRoute;
import asterisk.ExtensionHandler;
import asterisk.ReloadHandler;

/**
 * Servlet implementation class CrudConferenceServlet
 */
@WebServlet("/CrudConferenceRoomServlet")
public class CrudConferenceRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CrudConferenceRoomServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String error = "";
		String feedback = "";

		String forward = "/Pages/Application/crudConferenceRoom.jsp";
		try {

			ExtensionHandler handler = new ExtensionHandler();
			
			ReloadHandler reload = new ReloadHandler();

			List<DialPlan> dialPlanList = handler.listDialPlan();

			request.setAttribute("dialPlanList", dialPlanList);

			if (request.getParameter("atividade") != null
					&& request.getParameter("atividade").equals("inserir")) {
				// Caso seja realizada uma inserção de conferência

				ConferenceRoom conference = ConferenceRoom
						.getConferenceFromParameter(request);

				DialRoute dialRoute = new DialRoute(conference.getNumber());

				// Adição de um comando default
				DialCommand dialCommand = new DialCommand(1, 1, "Answer()");
				dialRoute.addCommand(dialCommand);

				// Adição da autenticação por senha
				DialCommand authenticateCommand = new DialCommand(2, 2, "Authenticate("+conference.getPassword()+")");
				dialRoute.addCommand(authenticateCommand);
				
				// Adição da sala de conferência em si
				DialCommand conferenceCommand = new DialCommand(3, 3,
						conference.toConference());
				dialRoute.addCommand(conferenceCommand);
				
				//Comando hangup
				DialCommand hangupCommand = new DialCommand(4, 4,
						"Hangup()");
				dialRoute.addCommand(hangupCommand);

				DialPlan dialPlan = handler
						.getDialPlan(conference.getContext());
				dialPlan.addRoute(dialRoute);
				boolean update = handler.updateDialPlan(dialPlan);
				if (update) {
					feedback = "Conferência " + dialRoute.getIdentifier()
							+ " adicionada com sucesso!";
				} else {
					error = "Erro! Algum outro usuário está fazendo modificações no sistema, tente novamente mais tarde.";
				}
				
				//recarregando o serviço
				reload.reload();

				forward = "/ListConferenceRoomServlet";
			} else if (request.getParameter("atividade") != null
					&& request.getParameter("atividade").equals("alteracao")) {
				// Redirecionamento para a página de inserção, mas com os campos
				// já preenchidos
				String number = request.getParameter("number");
				String context = request.getParameter("context");

				ConferenceRoom conference = handler.getConferenceRoom(number,
						context);

				conference.conferenceToRequest(request);
				request.setAttribute("atividade", "alterar");
				request.setAttribute("tarefa", "Alterar Sala de Conferência");
				request.setAttribute("btnSubmit", "Alterar");

			} else if (request.getParameter("atividade") != null
					&& request.getParameter("atividade").equals("alterar")) {
				// Caso seja realizada uma alteração em uma conferência já
				// existente
				ConferenceRoom conference = ConferenceRoom
						.getConferenceFromParameter(request);
				DialPlan dialPlan = handler
						.getDialPlan(conference.getContext());

				//caso você mude o número da conferência, deleta a conferência com o número antigo
				DialRoute dialRoute = dialPlan.getRoute(request.getParameter("oldNumber"));
				
				dialPlan.removeRoute(dialRoute);
				
				dialRoute = new DialRoute(conference.getNumber());

				// Adição de um comando default
				DialCommand dialCommand = new DialCommand(1, 1, "Answer()");
				dialRoute.addCommand(dialCommand);

				// Adição da autenticação por senha
				DialCommand authenticateCommand = new DialCommand(2, 2, "Authenticate("+conference.getPassword()+")");
				dialRoute.addCommand(authenticateCommand);
				
				// Adição da sala de conferência em si
				DialCommand conferenceCommand = new DialCommand(3, 3,
						conference.toConference());
				dialRoute.addCommand(conferenceCommand);
				
				//Comando hangup
				DialCommand hangupCommand = new DialCommand(4, 4,
						"Hangup()");
				dialRoute.addCommand(hangupCommand);				
				
				dialPlan.addRoute(dialRoute);
				boolean update = handler.updateDialPlan(dialPlan);
				if (update) {
					feedback = "Sala de conferência "+conference.getNumber()+" alterada com sucesso!";
				} else {
					error = "Erro! Algum outro usuário está fazendo modificações no sistema, tente novamente mais tarde.";
				}
				
				
				//recarregando o serviço
				reload.reload();
				
				forward = "/ListConferenceRoomServlet";

			} else if (request.getParameter("atividade") != null
					&& request.getParameter("atividade").equals("remocao")) {
				// Caso seja feita a remoção de uma conferência já existente
				ConferenceRoom conference = ConferenceRoom
						.getConferenceFromParameter(request);
				DialRoute dialRoute = new DialRoute(conference.getNumber());

				// Adição de um comando default
				DialCommand dialCommand = new DialCommand(1, 1, "Answer()");
				dialRoute.addCommand(dialCommand);

				// Adição da autenticação por senha
				DialCommand authenticateCommand = new DialCommand(2, 2, "Authenticate("+conference.getPassword()+")");
				dialRoute.addCommand(authenticateCommand);
				
				// Adição da sala de conferência em si
				DialCommand conferenceCommand = new DialCommand(3, 3,
						conference.toConference());
				dialRoute.addCommand(conferenceCommand);
				
				//Comando hangup
				DialCommand hangupCommand = new DialCommand(4, 4,
						"Hangup()");
				dialRoute.addCommand(hangupCommand);

				DialPlan dialPlan = handler
						.getDialPlan(conference.getContext());
				//remove a rota equivalente à conferência
				dialPlan.removeRoute(dialRoute);
				boolean update = handler.updateDialPlan(dialPlan);
				if (update) {
					feedback = "Sala de conferência "+conference.getNumber()+" removida com sucesso!";
				} else {
					error = "Erro! Algum outro usuário está fazendo modificações no sistema, tente novamente mais tarde.";
				}
				
				//recarregando o serviço
				reload.reload();
				
				forward = "/ListConferenceRoomServlet";

			} else {
				// Else caso não seja nenhum dos três comandos dos CRUD
				// Instanciado um ramal apenas com os valores para os campos
				// avançados
				ConferenceRoom conference = new ConferenceRoom("", "", "");

				// Setado os valores avançados para o request
				conference.conferenceToRequest(request);

				request.setAttribute("atividade", "inserir");
				request.setAttribute("tarefa", "Inserir Conferência");
				request.setAttribute("btnSubmit", "Inserir");
			}
		} catch (Exception e) {
			error = e.getMessage();
			e.printStackTrace();
		} finally {
			request.setAttribute("feedback", feedback);
			request.setAttribute("error", error);
			getServletContext().getRequestDispatcher(forward).forward(request,
					response);
		}
	}

}
