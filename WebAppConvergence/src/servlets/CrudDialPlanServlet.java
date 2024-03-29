package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dial.DialPlan;
import asterisk.ExtensionHandler;
import asterisk.ReloadHandler;

/**
 * Servlet responsável pelo CRUD dos planos de discagem
 * 
 * @author yvens
 *
 */
@WebServlet("/CrudDialPlanServlet")
public class CrudDialPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrudDialPlanServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		
		String forward = "/Pages/Application/crudDialPlan.jsp";
		try {
			ExtensionHandler handler = new ExtensionHandler();
			
			ReloadHandler reload = new ReloadHandler();
			
			if(request.getParameter("atividade") != null && request.getParameter("atividade").equals("inserir")){
				//Caso seja realizada uma inserção de ramal
				DialPlan dialPlan = DialPlan.getDialPlanFromParameter(request);
				
				//TODO remover
				System.out.println(dialPlan.toString());
				
				handler.insertDialPlan(dialPlan);
				
				feedback = "Plano de discagem "+dialPlan.getTag()+" adicionado com sucesso!";
				
				//recarregando o serviço
				reload.reload();
				
				forward = "/ListDialPlanServlet";
			}else{
				//Else caso não seja nenhum dos três comandos dos CRUD
				
				//Instanciado um plano de discagem apenas com os valores para os campos avançados
				DialPlan dialPlan = new DialPlan("");
				
				//Setado os valores avançados para o request
				dialPlan.dialPlanToRequest(request);
				
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
