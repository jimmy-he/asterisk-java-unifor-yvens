package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RamalSip;
import model.dial.DialPlan;
import asterisk.ExtensionHandler;
import asterisk.RamalSipHandler;
import asterisk.ReloadHandler;

/**
 * Servlet responsável pelo CRUD dos ramais
 * 
 * @author yvens
 *
 */
@WebServlet("/CrudRamalSipServlet")
public class CrudRamalSipServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrudRamalSipServlet() {
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
		
		String forward = "/Pages/Application/crudRamalSip.jsp";
		try {
			RamalSipHandler handler = new RamalSipHandler();
			
			ReloadHandler reload = new ReloadHandler();
			
			//carregando a lista de planos de discagem
			ExtensionHandler exhandler = new ExtensionHandler();
			
			List<DialPlan> dialPlanList = exhandler.listDialPlan();
			
			request.setAttribute("dialPlanList", dialPlanList);
			
			if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("inserir")){
				//Caso seja realizada uma inserção de ramal
				RamalSip ramalSip = RamalSip.getRamalFromParameter(request);
				
				//TODO remover
				System.out.println(ramalSip.toString());
				
				handler.createRamal(ramalSip);
				feedback = "Ramal "+ramalSip.getTag()+" adicionado com sucesso!";
				
				//recarregando o serviço
				reload.reload();
				
				forward = "/TableRamalSipServlet";
			}
			else if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("alteracao")){
				//Redirecionamento para a página de inserção, mas com os campos já preenchidos
				String tag = request.getParameter("tag");
				RamalSip ramal = handler.getRamal(tag);
				
				ramal.ramalToRequest(request);
				
				request.setAttribute("atividade", "alterar");
				request.setAttribute("tarefa", "Alterar Ramal SIP");
				request.setAttribute("btnSubmit", "Alterar");
			}
			else if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("alterar")){
				//Caso seja realizada uma alteração em um ramal já existente
				RamalSip ramalSip = RamalSip.getRamalFromParameter(request);
				handler.updateRamal(ramalSip);
				
				System.out.println(ramalSip.getCallerId());
				feedback = "Ramal "+ramalSip.getTag()+" alterado com sucesso!";
				
				//recarregando o serviço
				reload.reload();
				
				forward = "/TableRamalSipServlet";
			}
			else if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("remocao")){
				//Caso seja feita a remoção de um ramal já existente
				RamalSip ramalSip = RamalSip.getRamalFromParameter(request);
				handler.deleteRamal(ramalSip);
				feedback = "Ramal "+ramalSip.getTag()+" removido com sucesso!";
				
				//recarregando o serviço
				reload.reload();
				
				forward = "/TableRamalSipServlet";
			}else{
				//Else caso não seja nenhum dos três comandos dos CRUD
				
				//Instanciado um ramal apenas com os valores para os campos avançados
				RamalSip ramal = new RamalSip("", "", "","");
				
				//Setado os valores avançados para o request
				ramal.ramalToRequest(request);
				
				request.setAttribute("atividade", "inserir");
				request.setAttribute("tarefa", "Inserir Ramal SIP");
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
