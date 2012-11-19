package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dial.DialPlan;
import model.dial.DialRoute;
import asterisk.ExtensionHandler;

/**
 * Servlet implementation class ListDialCommandServlet
 */
@WebServlet("/ListDialCommandServlet")
public class ListDialCommandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListDialCommandServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = "";
		String feedback = "";

		String forward = "/Pages/Application/tableComandosDiscagem.jsp";
		try {
			ExtensionHandler handler = new ExtensionHandler();
			
			//Atributo deve vir do request indicando a tag do DialPlan
			String tag = request.getParameter("tag");
			
			//Atributo deve vir do request indicando o identifier do DialRoute
			String identifier = request.getParameter("identifier");
			
			//Busca o dialPlan com a tag passada
			DialPlan dialPlan = handler.getDialPlan(tag);
			//Busca o dialRoute com o identifier passado
			DialRoute dialRoute = dialPlan.getRoute(identifier);
			
			//Adiciona no request a lista de rotas do dialPlan
			request.setAttribute("list", dialRoute.getListCommands());
			
			//Adiciona a tag de volta ao request, ela será exibida na tela
			request.setAttribute("tag", tag);
			
			//Adiciona a tag de volta ao request, ela será exibida na tela
			request.setAttribute("identifier", identifier);
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
