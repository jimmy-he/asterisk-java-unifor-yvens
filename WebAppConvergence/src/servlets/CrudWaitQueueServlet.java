package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.queues.WaitQueue;
import asterisk.WaitQueueHandler;

/**
 * Servlet implementation class CrudWaitQueueServlet
 */
@WebServlet("/CrudWaitQueueServlet")
public class CrudWaitQueueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrudWaitQueueServlet() {
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
		
		String forward = "/Pages/Application/crudWaitQueue.jsp";
		try {
			WaitQueueHandler handler = new WaitQueueHandler();
			
			if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("inserir")){
				//Caso seja realizada uma inserção de ramal
				WaitQueue waitQueue = WaitQueue.getWaitQueueFromParameter(request);
				
				//TODO remover
				System.out.println(waitQueue.toString());
				
				handler.insertWaitQueue(waitQueue);
				
				feedback = "Fila de espera "+waitQueue.getTag()+" adicionada com sucesso!";
				
				forward = "/ListWaitQueueServlet";
			}else{
				//Else caso não seja nenhum dos três comando dos CRUD
				
				//Instanciado um plano de discagem apenas com os valores para os campos avançados
				WaitQueue waitQueue = new WaitQueue(0, "");
				
				//Setado os valores avançados para o request
				waitQueue.waitQueueToRequest(request);
				
				request.setAttribute("atividade", "inserir");
				request.setAttribute("tarefa", "Inserir Plano de Discagem");
				request.setAttribute("btnSubmit", "Inserir");
			}
		} catch (Exception e) {
			error = e.getMessage();
			e.printStackTrace();
		}finally{
			request.setAttribute("feedback", feedback);
			request.setAttribute("error", error);
			getServletContext().getRequestDispatcher(forward).forward(request,response); 
		}
	}

}
