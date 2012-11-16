package servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import asterisk.MonitorRamalHandler;

import model.MonitorRamalIAX;
import model.MonitorRamalSip;

/**
 * Servlet implementation class RamalMonitorServlet
 */
@WebServlet("/RamalMonitorServlet")
public class RamalMonitorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RamalMonitorServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String error = "";
		String feedback = "";

		String forward = "/Pages/Application/monitorRamal.jsp";
		try {

			MonitorRamalHandler handler = new MonitorRamalHandler();

			if (request.getParameter("update") != null) {
				feedback = request.getParameter("update");
			}

			List<MonitorRamalSip> listSip = handler.listRamalSip();
			request.setAttribute("listSip", listSip);

			List<MonitorRamalIAX> listIAX = handler.listRamalIAX();
			request.setAttribute("listIAX", listIAX);

		} catch (Exception e) {
			error = e.getMessage();
			e.printStackTrace();
		} finally {
			request.setAttribute("feedback", feedback);
			request.setAttribute("error", error);
			getServletContext().getRequestDispatcher(forward).forward(request,
					response);
		}
	}

}
