package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dial.DialPlan;
import model.dial.DialRoute;
import asterisk.ExtensionHandler;

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
		
		//Tag do DialPlan na qual o DialRoute pertence
		String tag = request.getParameter("tag");
		try {
			ExtensionHandler handler = new ExtensionHandler();
			
			if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("inserir")){
				//Caso seja realizada uma inserção de ramal
				DialRoute dialRoute = DialRoute.getDialPlanFromParameter(request);
				
				//TODO remover
				System.out.println(dialRoute.toString());
				
				DialPlan dialPlan = handler.getDialPlan(tag);
				dialPlan.addRoute(dialRoute);
				handler.updateDialPlan(dialPlan);
				
				feedback = "Rota de discagem "+dialRoute.getIdentifier()+" adicionado com sucesso!";
				
				forward = "/ListDialRouteServlet";
			}else{
				//Else caso não seja nenhum dos três comando dos CRUD
				
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
