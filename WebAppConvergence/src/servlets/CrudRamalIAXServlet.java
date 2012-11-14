package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RamalIAX;
import asterisk.RamalIAXHandler;

/**
 * Servlet responsável pelo CRUD dos ramais IAX
 * 
 * @author daniel
 * 
 */
@WebServlet("/CrudRamalIAXServlet")
public class CrudRamalIAXServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CrudRamalIAXServlet() {
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
		String error = "error";
		String feedback = "feedback";

		String forward = "/Pages/Application/crudRamalIAX.jsp";
		try {
			if (request.getParameter("alterar") != null
					&& !request.getParameter("alterar").isEmpty()) {
				// IF para verificar se acontecerá uma alteração em um ramal já
				// existente
				// TODO pegar o conteúdo do ramal passado

				request.setAttribute("tarefa", "Alterar");
				request.setAttribute("btnSubmit", "Alterar");
			} else if (request.getParameter("tarefa") != null
					&& !request.getParameter("tarefa").isEmpty()) {
				// IF caso seja realizada uma tarefa do tipo Inserção, Alteração
				// e Deleção
				String task = request.getParameter("tarefa");
				RamalIAXHandler handler = new RamalIAXHandler();
				RamalIAX ramalIAX = RamalIAX.getRamalFromParameter(request);

				if (task.equals("Inserir")) {
					handler.createRamal(ramalIAX);
					feedback = "Ramal " + ramalIAX.getTag()
							+ " adicionado com sucesso!";
				} else if (task.equals("Alterar")) {
					handler.updateRamal(ramalIAX);
					feedback = "Ramal " + ramalIAX.getTag()
							+ " atualizado com sucesso!";
				} else if (task.equals("Remover")) {
					handler.deleteRamal(ramalIAX);
					feedback = "Ramal " + ramalIAX.getTag()
							+ " removido com sucesso!";
				}

				// TODO inserir a linha de comando para dar o reload no asterisk

				// Dá um forward para a tabela com todos os IAXs
				forward = "/Pages/Application/tableRamalIAX.jsp";
			} else {
				// Else caso não seja nenhum dos três comando dos CRUD
				request.setAttribute("tarefa", "Inserir");
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
