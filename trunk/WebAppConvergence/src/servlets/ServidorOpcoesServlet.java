package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.ConnectionProperties;

/**
 * Servlet responsável por carregar as informações iniciais da tela de servidorOpcoes.jsp
 * e lidar com as atualizações do usuário
 * 
 * 
 * @author yvens
 *
 */
@WebServlet("/ServidorOpcoesServlet")
public class ServidorOpcoesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServidorOpcoesServlet() {
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
		String servidor = "";
		
		servidor = request.getLocalAddr();
		String servidorAtual = ConnectionProperties.getInstace().getServer();
		if(servidorAtual.isEmpty())
		{
			ConnectionProperties.getInstace().setServer(servidor);
			System.out.println("Servidor: "+servidor);
		}
		else
		{
			servidor = servidorAtual;
			System.out.println("Servidor: "+servidorAtual);
		}
		
		request.setAttribute("servidor", servidor);
		getServletContext().getRequestDispatcher("/Pages/Application/servidorOpcoes.jsp").forward(request,response);
	}

}
