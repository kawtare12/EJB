package controllers;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.IDaoLocale;
import entities.Ville;

/**
 * Servlet implementation class VilleController
*/
@WebServlet("/VilleController")
public class VilleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private IDaoLocale<Ville> ejb;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VilleController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Process operations (delete, update, create)
		if (request.getParameter("op") != null) {
			String op = request.getParameter("op");
			if (op.equals("delete")) {
				int id = Integer.parseInt(request.getParameter("id"));
				Ville v = ejb.findById(id);
				ejb.delete(v);	
			}
			if (op.equals("update")) {
				int id = Integer.parseInt(request.getParameter("id"));
				Ville v = ejb.findById(id);
				String nom = request.getParameter("ville");
				v.setNom(nom);
				ejb.update(v);
			}
			if (op.equals("add")) {
				String nom = request.getParameter("ville");
				ejb.create(new Ville(nom));
			}
		}

		// Fetch the list of cities after processing the operation
		List<Ville> villes = ejb.findAll();
		request.setAttribute("villes", villes);

		// Forward the request to the JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("ville.jsp");
		dispatcher.forward(request, response);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
