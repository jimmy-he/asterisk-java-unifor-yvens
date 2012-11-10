package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet responsável pelo CRUD dos ramais
 * 
 * @author yvens
 *
 */
@WebServlet("/CrudRamalServlet")
public class CrudRamalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrudRamalServlet() {
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
		//TODO implementar
		if(request.getParameter("tarefa") != null && !request.getParameter("tarefa").isEmpty()){
			
		}else{
			request.setAttribute("tarefa", "Inserção de Ramal");
			request.setAttribute("btnSubmit", "Inserir");
		}
		
		getServletContext().getRequestDispatcher("/Pages/Application/crudRamal.jsp").forward(request,response); 
	}

}
