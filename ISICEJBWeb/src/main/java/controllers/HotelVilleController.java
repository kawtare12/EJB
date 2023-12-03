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

/**
 * Servlet implementation class HotelVilleController
 */
@WebServlet("/HotelVilleController")
public class HotelVilleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private IDaoLocaleHotel ejb;
	
	@EJB
	private IDaoLocale<Ville> ejbv;
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HotelVilleController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String villeStr = request.getParameter("ville");
		if (villeStr != null) {
			int id = Integer.parseInt(villeStr);
			Ville v = ejbv.findById(id);
			List<Hotel> hotels = ejb.findByVille(v);
			request.setAttribute("hotels", hotels);
		}

		// Fetch the list of cities after processing the operation
        List<Ville> villes = ejbv.findAll();
        request.setAttribute("villes", villes);
		
		// Forward the request to the JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("hotelVille.jsp");
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
