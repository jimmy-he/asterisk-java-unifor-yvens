package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.queues.WaitQueue;
import asterisk.WaitQueueHandler;

/**
 * Servlet implementation class ListWaitQueueServlet
 */
@WebServlet("/ListWaitQueueServlet")
public class ListWaitQueueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListWaitQueueServlet() {
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

		String forward = "/Pages/Application/tableFilasEspera.jsp";
		try {
			WaitQueueHandler handler = new WaitQueueHandler();
			
			if(request.getParameter("remover") != null){
				//Manda o comando para remover o WaitQueue que possua a tag recebida pela URL
				boolean remocao = handler.deleteWaitQueue(new WaitQueue(Integer.parseInt(request.getParameter("id")), request.getParameter("remover")));
				
				if(remocao){
					feedback = "Fila de espera removida com sucesso!";
				}else{
					error = "Algum outro usuário está alterando o arquivo, por favor, tente novamente!";
				}
			}
			
			//Preenchendo o feedback de alteração de plano
			if (request.getAttribute("feedback") != null){				
				feedback = (String) request.getAttribute("feedback");
			}
			
			List<WaitQueue> list = handler.listWaitQueue();
			
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
