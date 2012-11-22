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
import asterisk.ReloadHandler;

/**
 * Servlet responsável pelo CRUD das rotas de discagem
 * 
 * @author yvens
 *
 */
@WebServlet("/CrudDialRouteServlet")
public class CrudDialRouteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrudDialRouteServlet() {
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
		
		String forward = "/Pages/Application/crudDialRoute.jsp";
		
		//Tag do DialPlan ao qual o DialRoute pertence
		String tag = request.getParameter("tag");
		
		try {
			ExtensionHandler handler = new ExtensionHandler();
			
			ReloadHandler reload = new ReloadHandler();
			
			if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("inserir")){
				//Caso seja realizada uma inserção de ramal
				DialRoute dialRoute = DialRoute.getDialPlanFromParameter(request);
				
				//TODO remover
				System.out.println(dialRoute.toString());
				System.out.println(dialRoute.getIdentifier());
				
				//Adição de um comando default
				DialCommand dialCommand = new DialCommand(1, 1, "Answer()");
				//Adição do comando hangup como default
				DialCommand dialCommand2 = new DialCommand(2, 2, "HangUp()");
				dialRoute.addCommand(dialCommand);
				dialRoute.addCommand(dialCommand2);
				
				DialPlan dialPlan = handler.getDialPlan(tag);
				dialPlan.addRoute(dialRoute);
				boolean update = handler.updateDialPlan(dialPlan);
				if(update){
					feedback = "Rota de discagem "+dialRoute.getIdentifier()+" adicionada com sucesso!";
				}else{
					error = "Erro! Algum outro usuário está fazendo modificações no sistema, tente novamente mais tarde.";
				}
				
				//recarregando o serviço
				reload.reload();
				forward = "/ListDialRouteServlet";
			}else{
				//Else caso não seja nenhum dos três comandos dos CRUD
				
				//Instanciado um plano de discagem apenas com os valores para os campos avançados
				DialRoute dialRoute = new DialRoute("");
				
				//Setado os valores avançados para o request
				dialRoute.dialRouteToRequest(request);
				
				request.setAttribute("atividade", "inserir");
				request.setAttribute("tarefa", "Inserir Rota de Discagem");
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
