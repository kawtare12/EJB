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
import dao.IDaoLocaleHotel;
import entities.Hotel;
import entities.Ville;


@WebServlet("/HotelController")
public class HotelController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private IDaoLocaleHotel ejb;
	
	@EJB
	private IDaoLocale<Ville> ejbv;
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HotelController() {
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
				Hotel v = ejb.findById(id);
				ejb.delete(v);	
			}
			if (op.equals("update")) {
				int id = Integer.parseInt(request.getParameter("id"));
				Hotel h = ejb.findById(id);
				String nom = request.getParameter("nom");
				String adresse = request.getParameter("adresse");
				String numero = request.getParameter("numero");
				int villeId = Integer.parseInt(request.getParameter("ville"));
				h.setNom(nom);
				h.setAdresse(adresse);
				h.setTelephone(numero);
				h.setVille(ejbv.findById(villeId));
				ejb.update(h);
			}
			if (op.equals("add")) {
				String nom = request.getParameter("nom");
				String adresse = request.getParameter("adresse");
				String numero = request.getParameter("numero");
				int villeId = Integer.parseInt(request.getParameter("ville"));
				Hotel h = new Hotel();
				h.setNom(nom);
				h.setAdresse(adresse);
				h.setTelephone(numero);
				h.setVille(ejbv.findById(villeId));
				ejb.create(h);	
			}
		}

		// Fetch the list of cities after processing the operation
		List<Hotel> hotels = ejb.findAll();
        List<Ville> villes = ejbv.findAll();
		request.setAttribute("hotels", hotels);
        request.setAttribute("villes", villes);
		

		// Forward the request to the JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("hotel.jsp");
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