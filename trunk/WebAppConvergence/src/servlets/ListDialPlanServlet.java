package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import asterisk.ExtensionHandler;

import model.dial.DialPlan;

/**
 * Servlet implementation class ListDialPlanServlet
 */
@WebServlet("/ListDialPlanServlet")
public class ListDialPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListDialPlanServlet() {
        super();
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

		String forward = "/Pages/Application/tablePlanosDiscagem.jsp";
		try {
			ExtensionHandler handler = new ExtensionHandler();
			
			if(request.getParameter("remover") != null){
				//Manda o comando para remover o DialPlan que possua a tag recebida pela URL
				boolean remocao = handler.deleteDialPlan(new DialPlan(request.getParameter("remover")));
				
				if(remocao){
					feedback = "Plano de Discagem removido com sucesso!";
				}else{
					error = "Algum outro usuário está alterando o arquivo, por favor, tente novamente!";
				}
			}
			
			//Preenchendo o feedback de alteração de plano
			if (request.getAttribute("feedback") != null){				
				feedback = (String) request.getAttribute("feedback");
			}
			
			List<DialPlan> list = handler.listDialPlan();
			
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
