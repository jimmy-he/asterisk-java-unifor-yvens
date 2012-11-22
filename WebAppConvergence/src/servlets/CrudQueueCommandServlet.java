package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dial.DialCommand;
import model.queues.QueueCommand;
import model.queues.WaitQueue;
import asterisk.ReloadHandler;
import asterisk.WaitQueueHandler;

/**
 * Servlet implementation class CrudQueueCommandServlet
 */
@WebServlet("/CrudQueueCommandServlet")
public class CrudQueueCommandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrudQueueCommandServlet() {
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
		
		String forward = "/Pages/Application/crudQueueCommand.jsp";
		
		//Tag do WaitQueue na qual o QueueCommand pertence
		String tag = request.getParameter("tag");
		
		try {
			WaitQueueHandler handler = new WaitQueueHandler();
			
			ReloadHandler reload = new ReloadHandler();
			
			if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("inserir")){
				//Caso seja realizada uma inserção de um comando
				QueueCommand queueCommand = QueueCommand.getQueueCommandFromParameter(request);
				WaitQueue waitQueue = handler.getWaitQueue(tag);
				
				//TODO remover
				System.out.println(queueCommand.toString());
				
				waitQueue.addCommand(queueCommand);
				
				boolean update = handler.updateWaitQueue(waitQueue);
				
				if(update){
					feedback = "Comando "+queueCommand.getField()+" adicionado com sucesso!";
				}else{
					error = "Erro! Algum outro usuário está fazendo modificações no sistema, tente novamente mais tarde.";
				}
				
				//recarregando o serviço
				reload.reload();
				
				forward = "/ListQueueCommandServlet";
			}
			else if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("alteracao")){
				//Redirecionamento para a página de inserção, mas com os campos já preenchidos
				
				//ID do comando
				int id = Integer.parseInt(request.getParameter("id"));
				System.out.println(id);
				
				WaitQueue waitQueue = handler.getWaitQueue(tag);
				QueueCommand queueCommand = waitQueue.getCommand(id);
				
				queueCommand.queueCommandToRequest(request);
				
				request.setAttribute("atividade", "alterar");
				request.setAttribute("tarefa", "Alterar Comando");
				request.setAttribute("btnSubmit", "Alterar");
			}
			else if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("alterar")){
				//Caso seja realizada uma alteração em um ramal já existente
				QueueCommand queueCommand = QueueCommand.getQueueCommandFromParameter(request);

				WaitQueue waitQueue = handler.getWaitQueue(tag);
				
				//TODO remover
				System.out.println(queueCommand.toString());
				
				//Remove o dialCommand antigo
				waitQueue.removeCommand(queueCommand);
				
				//Adiciona o novo
				waitQueue.addCommand(queueCommand);
				
				boolean update = handler.updateWaitQueue(waitQueue);
				
				if(update){
					feedback = "Comando "+ queueCommand.getField() +" alterado com sucesso!";
				}else{
					error = "Erro! Algum outro usuário está fazendo modificações no sistema, tente novamente mais tarde.";
				}
				
				//recarregando o serviço
				reload.reload();
				
				forward = "/ListQueueCommandServlet";
			}
			else if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("remocao")){
				//Caso seja feita a remoção de um ramal já existente
				int id = Integer.parseInt(request.getParameter("id"));
				System.out.println(id);
				
				WaitQueue waitQueue = handler.getWaitQueue(tag);
				QueueCommand queueCommand = waitQueue.getCommand(id);
				
				queueCommand.queueCommandToRequest(request);
				
				//TODO remover
				System.out.println(queueCommand.toString());
				
				//Remove o dialCommand antigo
				waitQueue.removeCommand(queueCommand);
				
				boolean update = handler.updateWaitQueue(waitQueue);
				
				if(update){
					feedback = "Comando "+ queueCommand.getField() +" removido com sucesso!";
				}else{
					error = "Erro! Algum outro usuário está fazendo modificações no sistema, tente novamente mais tarde.";
				}
				
				//recarregando o serviço
				reload.reload();
				
				forward = "/ListQueueCommandServlet";
			}else{
				//Else caso não seja nenhum dos três comando dos CRUD
				
				//Instanciado um comando apenas com os valores para os campos avançados
				DialCommand command = new DialCommand(0, 0, "");
				
				//Setado os valores avançados para o request
				command.dialCommandToRequest(request);
				
				request.setAttribute("atividade", "inserir");
				request.setAttribute("tarefa", "Inserir Comando");
				request.setAttribute("btnSubmit", "Inserir");
			}
		} catch (Exception e) {
			error = e.getMessage();
			e.printStackTrace();
		}finally{
			request.setAttribute("feedback", feedback);
			request.setAttribute("error", error);
			request.setAttribute("tag", tag);
			getServletContext().getRequestDispatcher(forward).forward(request,response); 
		}
	}

}
