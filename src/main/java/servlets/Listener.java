/*
 * Listener.java v1.0 22/10/2015
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Listener extends HttpServlet {

	/**
	 * Clase para obtener las peticiones de la aplicacion
	 * 
	 * @version 1.0
	 * @date 22/10/2015
	 */

	/* serial version */
	private static final long serialVersionUID = 1473037731466545577L;

	/**
	 * Metodo para recibir las peticiones GET de la aplicacion
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		manageRequest(req, resp);
	}

	/**
	 * Metodo para recibir las peticiones POST de la aplicacion
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		manageRequest(req, resp);
	}
	
	/**
	 * Metodo para controlar las peticiones y respuestas del servidor
	 */
	private void manageRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		PrintWriter out = resp.getWriter();
		Enumeration<String> en = req.getParameterNames();
		
		while(en.hasMoreElements()){
			String s = en.nextElement();
			out.println(s);
		}
	}
}
