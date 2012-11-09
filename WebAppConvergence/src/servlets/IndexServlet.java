package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.ConnectionProperties;

/**
 * Servlet responsável por carregar as informações iniciais da aplicação e chamar
 * a página index.jsp
 * 
 * @author yvens
 *
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
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
		String servidor = request.getLocalAddr();
		String servidorAtual = ConnectionProperties.getInstace().getServer();
		if(servidorAtual.isEmpty()){
			ConnectionProperties.getInstace().setServer(servidor);
			System.out.println("Servidor: "+servidor);
			request.setAttribute("servidor", servidor);
		}
		else
		{
			System.out.println("Servidor: "+servidorAtual);
			request.setAttribute("servidor", servidorAtual);
		}
		
		getServletContext().getRequestDispatcher("/Pages/Application/index.jsp").forward(request,response); 
	}
}
