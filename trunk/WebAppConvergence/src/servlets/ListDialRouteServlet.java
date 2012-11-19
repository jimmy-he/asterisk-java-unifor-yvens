package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dial.DialPlan;
import asterisk.ExtensionHandler;

/**
 * Servlet implementation class ListDialRouteServlet
 */
@WebServlet("/ListDialRouteServlet")
public class ListDialRouteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListDialRouteServlet() {
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

		String forward = "/Pages/Application/tableRotasDiscagem.jsp";
		try {
			ExtensionHandler handler = new ExtensionHandler();
			
			//TODO alterar
			if(request.getParameter("remover") != null){
				//Manda o comando para remover o DialRoute que possua a tag recebida pela URL
				boolean remocao = handler.deleteDialPlan(new DialPlan(request.getParameter("remover")));
				
				if(remocao){
					feedback = "Rota de Discagem removido com sucesso!";
				}else{
					error = "Algum outro usuário está alterando o arquivo, por favor, tente novamente!";
				}
			}
			
			//Atributo deve vir do request indicando a tag do DialPlan
			String tag = request.getParameter("tag");
			
			//Busca o dialPlan com a tag passada
			DialPlan dialPlan = handler.getDialPlan(tag);
			
			//Adiciona no request a lista de rotas do dialPlan
			request.setAttribute("list", dialPlan.getRouteList());
			
			//Adiciona a tag de volta ao request, ela será exibida na tela
			request.setAttribute("tag", tag);
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
