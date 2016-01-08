package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DbMethods;

public class Authenticator extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4656698343123568633L;

	/**
	 * Metodo para recibir las peticiones GET de la aplicacion
	 * 
	 * @version 1.0
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String hash = req.getParameter("key");
		manageKey(resp, hash);
	}

	/**
	 * Metodo para recibir las peticiones POST de la aplicacion
	 * 
	 * @version 1.0
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String hash = req.getParameter("key");
		manageKey(resp, hash);
	}
	
	/**
	 * Metodo para verificar al usuario a partir de su @param key.
	 * Escribe en @param resp el resultado de la verificacion
	 * @throws IOException 
	 */
	private void manageKey (HttpServletResponse resp, String key) throws IOException{
		PrintWriter out = resp.getWriter();
		
		boolean exito = DbMethods.search_for_validation(key, false);	// true si se ha verificado al user o/w
		String mail = null;
		
		if(exito){
			out.println("<html>");
			
			out.println("<head>");
			out.println("<title>Exito</title>");
			out.println("</head>");
			
			out.println("<body>");
			out.println("<h1>Exito al verificar</h1>");
			out.println("<p>Se ha verificado al usuario " + mail + "</p>");
			out.println("</body>");
			out.println("</html>");
		}
		else{
			out.println("<html>");
			
			out.println("<head>");
			out.println("<title>Fallo</title>");
			out.println("</head>");
			
			out.println("<body>");
			out.println("<h1>Fallo al verificar</h1>");
			out.println("<p>No se ha podido verificar al usuario "
					+ mail + "</p>");
			out.println("</body>");
			out.println("</html>");
		}
	}

}
