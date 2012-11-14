package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RamalSip;
import asterisk.RamalSipHandler;

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
		String error = "error";
		String feedback = "feedback";
		
		String forward = "/Pages/Application/crudRamalSip.jsp";
		try {
			if(request.getParameter("alterar") != null && !request.getParameter("alterar").isEmpty()){
				//IF para verificar se acontecerá uma alteração em um ramal já existente
				//TODO pegar o conteúdo do ramal passado
				
				
				request.setAttribute("tarefa", "Alterar");
				request.setAttribute("btnSubmit", "Alterar");
			}else if(request.getParameter("tarefa") != null && !request.getParameter("tarefa").isEmpty()){
				//IF caso seja realizada uma tarefa do tipo Inserção, Alteração e Deleção
				String task = request.getParameter("tarefa");
				RamalSipHandler handler = new RamalSipHandler();
				RamalSip ramalSip = RamalSip.getRamalFromParameter(request);
				
				if(task.equals("Inserir")){
					handler.createRamal(ramalSip);
					feedback = "Ramal "+ramalSip.getTag()+" adicionado com sucesso!";
				}else if(task.equals("Alterar")){
					handler.updateRamal(ramalSip);
					feedback = "Ramal "+ramalSip.getTag()+" atualizado com sucesso!";
				}else if(task.equals("Remover")){
					handler.deleteRamal(ramalSip);
					feedback = "Ramal "+ramalSip.getTag()+" removido com sucesso!";
				}
				
				//TODO inserir a linha de comando para dar o reload no asterisk
				
				//Dá um forward para a tabela com todos os SIPs
				forward = "/Pages/Application/tableRamalSip.jsp";
			}else{
				//Else caso não seja nenhum dos três comando dos CRUD
				request.setAttribute("tarefa", "Inserir");
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
