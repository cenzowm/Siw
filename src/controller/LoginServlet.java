package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;

import persistence.DAOFactory;
import persistence.PostgresDAOFactory;

public class LoginServlet extends HttpServlet{

	String password;
	Utente utente;
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		
		//System.out.println("user key: "+userid);
		password = request.getParameter("password");
		HttpSession session =  request.getSession();
		
		utente = new PostgresDAOFactory().getUtenteDAO().findByEmail(userid);
		if (utente != null) {
			if (comparisonPassword(utente)) {
			String messaggio = "Welcome " + utente.getNome()+"  ";
			//session.setAttribute("userid", arg1);
			session.setAttribute("username", userid);
			//System.out.println(session.getAttribute("username"));
			session.setAttribute("mex", messaggio);
			session.setAttribute("loggato", utente.getEmail());
			
			
			session.setAttribute("email", utente.getEmail());
			session.setAttribute("nome", utente.getNome());
			session.setAttribute("cognome", utente.getCognome());
			
			session.setAttribute("tipo", "normale");
			System.out.println("login servet");
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp"); //da cambiare, usato solo per prova
			dispatcher.forward(request, response);
			}
		}
	}

	//funziona che mi controlla la correttezza della password
	private boolean comparisonPassword(Utente utente) {
		if (password.equals(utente.getPassword()))
			return true;
		return false;

	}



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		HttpSession session=req.getSession();
		
		//log-out
		session.removeAttribute("mex");
		session.removeAttribute("tipo");
		session.removeAttribute("username");
		session.removeAttribute("loggato");
		session.removeAttribute("utente");
			
		RequestDispatcher disp;
		disp= req.getRequestDispatcher("index.jsp");
		disp.forward(req, resp);
	}
	
}

	
	
		
