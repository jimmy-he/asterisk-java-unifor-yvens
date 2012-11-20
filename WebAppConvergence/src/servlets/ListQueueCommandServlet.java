package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.queues.QueueCommand;
import model.queues.WaitQueue;
import asterisk.WaitQueueHandler;

/**
 * Servlet implementation class ListQueueCommandServlet
 */
@WebServlet("/ListQueueCommandServlet")
public class ListQueueCommandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListQueueCommandServlet() {
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
		String tag = request.getParameter("tag");
		
		String forward = "/Pages/Application/tableComandosFila.jsp";
		try {
			WaitQueueHandler handler = new WaitQueueHandler();
			WaitQueue queue = handler.getWaitQueue(tag);
			
			if(request.getParameter("remover") != null){
				//Manda o comando para remover o WaitQueue que possua a tag recebida pela URL
				queue.removeCommand(new QueueCommand(Integer.parseInt(request.getParameter("id")),0, ""));
				boolean remocao = handler.updateWaitQueue(queue);
				
				if(remocao){
					feedback = "Comando removido com sucesso!";
				}else{
					error = "Algum outro usuário está alterando o arquivo, por favor, tente novamente!";
				}
			}
			
			//Preenchendo o feedback de alteração de plano
			if (request.getAttribute("feedback") != null){				
				feedback = (String) request.getAttribute("feedback");
			}
			
			List<QueueCommand> list = queue.getListCommands();
			
			request.setAttribute("list", list);
		} catch (Exception e) {
			error = e.getMessage();
			e.printStackTrace();
		} finally {
			request.setAttribute("feedback", feedback);
			request.setAttribute("error", error);
			request.setAttribute("tag", tag);
			getServletContext().getRequestDispatcher(forward).forward(request, response);
		}
	}

}
