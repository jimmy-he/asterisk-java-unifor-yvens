package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistence.FileHandler;
import asterisk.AsteriskConfiguration;

/**
 * Servlet implementation class CommandHelpServlet
 */
@WebServlet("/CommandHelpServlet")
public class CommandHelpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommandHelpServlet() {
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
		
		String forward = "/Pages/Application/janelaAjudaComandos.jsp";
		
		FileHandler handler = new FileHandler();
		
		try {
			String[] helpFile = handler.readFile(AsteriskConfiguration.COMMAND_HELP_PATH);
			List<String> helpList = new ArrayList<String>();
			
			for (int i = 0; i < helpFile.length; i++) {
				helpList.add(helpFile[i]);
			}
			Collections.sort(helpList);
			
			request.setAttribute("list", helpList);
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
