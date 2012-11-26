package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.queues.Agent;
import asterisk.AgentHandler;
import asterisk.ReloadHandler;

/**
 * Servlet implementation class CrudAgentServlet
 */
@WebServlet("/CrudAgentServlet")
public class CrudAgentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrudAgentServlet() {
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
		
		String forward = "/Pages/Application/crudAgent.jsp";
		try {
			AgentHandler handler = new AgentHandler();
			
			ReloadHandler reload = new ReloadHandler();
			
			if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("inserir")){
				//Caso seja realizada uma inserção de ramal
				Agent agent = Agent.getAgentFromParameter(request);
				
				//TODO remover
				System.out.println(agent.toString());
				
				handler.insertAgent(agent);
				feedback = "Agente "+agent.getName() +" adicionado com sucesso!";
				
				reload.reload();
				//recarregando o serviço
				
				forward = "/ListAgentServlet";
			}
			else if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("alteracao")){
				//Redirecionamento para a página de inserção, mas com os campos já preenchidos
				
				String id = request.getParameter("id");
				Agent agent = handler.getAgent(Integer.parseInt(id));

				agent.agentToRequest(request);
				
				request.setAttribute("atividade", "alterar");
				request.setAttribute("tarefa", "Alterar Agente");
				request.setAttribute("btnSubmit", "Alterar");
			}
			else if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("alterar")){
				//Caso seja realizada uma alteração em um ramal já existente
				Agent agent = Agent.getAgentFromParameter(request);
				
				boolean updated = handler.updateAgent(agent);
				
				if(updated){
					System.out.println(agent.getName());
					feedback = "Agente "+agent.getName()+" alterado com sucesso!";
				}else{
					error = "Erro! Algum outro usuário está fazendo alterações no arquivo.";
				}
				
				//recarregando o serviço
				reload.reload();
				
				forward = "/ListAgentServlet";
			}else{
				//Else caso não seja nenhum dos três comandos dos CRUD
				
				//Instanciado um ramal apenas com os valores para os campos avançados
				Agent agent = new Agent(0,"","","");
				
				//Setado os valores avançados para o request
				agent.agentToRequest(request);
				
				request.setAttribute("atividade", "inserir");
				request.setAttribute("tarefa", "Agente Ramal SIP");
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
