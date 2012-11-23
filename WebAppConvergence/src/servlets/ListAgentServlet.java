package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.queues.Agent;
import asterisk.AgentHandler;

/**
 * Servlet implementation class ListAgentServlet
 */
@WebServlet("/ListAgentServlet")
public class ListAgentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListAgentServlet() {
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
		
		String forward = "/Pages/Application/tableAgent.jsp";
		try {
			AgentHandler handler = new AgentHandler();
			
			if(request.getParameter("remover") != null){
				//Manda o comando para remover o WaitQueue que possua a tag recebida pela URL
				
				String id = request.getParameter("id");
				
				Agent agent = handler.getAgent(Integer.parseInt(id));
				boolean remocao = handler.deleteAgent(agent);
				
				if(remocao){
					feedback = "Agente removido com sucesso!";
				}else{
					error = "Algum outro usuário está alterando o arquivo, por favor, tente novamente!";
				}
			}
			
			//Preenchendo o feedback de alteração de plano
			if (request.getAttribute("feedback") != null){				
				feedback = (String) request.getAttribute("feedback");
			}
			
			List<Agent> list = handler.listAgent();
			
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
