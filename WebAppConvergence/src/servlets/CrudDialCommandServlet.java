package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dial.DialCommand;
import model.dial.DialPlan;
import model.dial.DialRoute;
import asterisk.ExtensionHandler;

/**
 * Servlet implementation class CrudDialCommandServlet
 */
@WebServlet("/CrudDialCommandServlet")
public class CrudDialCommandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrudDialCommandServlet() {
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
		
		String forward = "/Pages/Application/crudDialCommand.jsp";
		
		//Tag do DialPlan na qual o DialRoute pertence
		String tag = request.getParameter("tag");
		
		//Tag do DialRoute na qual o DialCommand pertence
		String identifier = request.getParameter("identifier");
		try {
			ExtensionHandler handler = new ExtensionHandler();
			
			if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("inserir")){
				//Caso seja realizada uma inserção de um comando
				DialCommand dialCommand = DialCommand.getDialCommandFromParameter(request);
				
				DialPlan dialPlan = handler.getDialPlan(tag);
				DialRoute dialRoute = dialPlan.getRoute(identifier);
				
				//TODO remover
				System.out.println(dialCommand.toString());
				
				dialRoute.addCommand(dialCommand);
				
				boolean update = handler.updateDialPlan(dialPlan);
				
				if(update){
					feedback = "Comando "+dialCommand.getCommand()+" adicionado com sucesso!";
				}else{
					error = "Erro! Algum outro usuário está fazendo modificações no sistema, tente novamente mais tarde.";
				}
				
				forward = "/ListDialCommandServlet";
			}
			else if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("alteracao")){
				//Redirecionamento para a página de inserção, mas com os campos já preenchidos
				
				//ID do comando
				int id = Integer.parseInt(request.getParameter("id"));
				System.out.println(id);
				
				DialPlan dialPlan = handler.getDialPlan(tag);
				DialRoute dialRoute = dialPlan.getRoute(identifier);
				DialCommand dialCommand = dialRoute.getCommand(id);
				
				dialCommand.dialCommandToRequest(request);
				
				request.setAttribute("atividade", "alterar");
				request.setAttribute("tarefa", "Alterar Comando");
				request.setAttribute("btnSubmit", "Alterar");
			}
			else if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("alterar")){
				//Caso seja realizada uma alteração em um ramal já existente
				DialCommand dialCommand = DialCommand.getDialCommandFromParameter(request);
				
				DialPlan dialPlan = handler.getDialPlan(tag);
				DialRoute dialRoute = dialPlan.getRoute(identifier);
				
				//TODO remover
				System.out.println(dialCommand.toString());
				
				//Remove o dialCommand antigo
				dialRoute.removeCommand(dialCommand);
				
				//Adiciona o novo
				dialRoute.addCommand(dialCommand);
				
				boolean update = handler.updateDialPlan(dialPlan);
				
				if(update){
					feedback = "Comando "+dialCommand.getCommand()+" alterado com sucesso!";
				}else{
					error = "Erro! Algum outro usuário está fazendo modificações no sistema, tente novamente mais tarde.";
				}
				
				forward = "/ListDialCommandServlet";
			}
			else if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("remocao")){
				//Caso seja feita a remoção de um ramal já existente
				int id = Integer.parseInt(request.getParameter("id"));
				System.out.println(id);
				
				DialPlan dialPlan = handler.getDialPlan(tag);
				DialRoute dialRoute = dialPlan.getRoute(identifier);
				DialCommand dialCommand = dialRoute.getCommand(id);
				
				dialCommand.dialCommandToRequest(request);
				
				//TODO remover
				System.out.println(dialCommand.toString());
				
				//Remove o dialCommand antigo
				dialRoute.removeCommand(dialCommand);
				
				boolean update = handler.updateDialPlan(dialPlan);
				
				if(update){
					feedback = "Comando "+dialCommand.getCommand()+" removido com sucesso!";
				}else{
					error = "Erro! Algum outro usuário está fazendo modificações no sistema, tente novamente mais tarde.";
				}
				
				forward = "/ListDialCommandServlet";
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
			request.setAttribute("identifier", identifier);
			getServletContext().getRequestDispatcher(forward).forward(request,response); 
		}
	}

}
