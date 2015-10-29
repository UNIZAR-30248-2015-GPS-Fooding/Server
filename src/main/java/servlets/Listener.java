/*
 * Listener.java v1.0 22/10/2015
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import data.Data;
import data.Ingrediente;
import data.Receta;

public class Listener extends HttpServlet {

	/**
	 * Clase para obtener las peticiones de la aplicacion
	 * 
	 * @version 1.1
	 * 	- GET, POST redirigidos a otro metodo
	 * 	- Default message agregado
	 * 	- Obtener ingredientes
	 * 	- Obtener recetas
	 * @date 22/10/2015
	 */

	/* serial version */
	private static final long serialVersionUID = 1473037731466545577L;

	/**
	 * Metodo para recibir las peticiones GET de la aplicacion
	 * 
	 * @version 1.0
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("GET");
		manageRequest(req, resp);
	}

	/**
	 * Metodo para recibir las peticiones POST de la aplicacion
	 * 
	 * @version 1.0
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("POST");
		manageRequest(req, resp);
	}

	/**
	 * Metodo para controlar las peticiones y respuestas del servidor. Toma la
	 * entrada como documento XML y la parsea.
	 * 
	 * Formato XML de request: <request id=number> cuerpo de la
	 * request </request>
	 * 
	 * @version 1.0
	 */
	private void manageRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// crear parser para XML
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(req.getInputStream());
			doc.getDocumentElement().normalize();

			// obtener identificador de la request
			int id = Integer.parseInt(doc.getDocumentElement().getAttribute("id"));
			
			PrintWriter out = resp.getWriter();
			out.println("ID: " + id);

			// hacer distintas cosas dependiendo del identificador
			switch (id) {
			case Data.ING_CODE: /* ingredientes de la bd */
				out.println("INGS");
				get_ingredientes(resp);
				break;
			case Data.RECETA_CODE: /* receta */
				out.println("RECETA");
				get_recetas(doc, resp);
				break;
			case Data.USER_CODE: /* usuario */
				out.println("USER");
				break;
			case Data.GRUPO_CODE: /* grupo */
				out.println("GRUPO");
				break;
			default: /* no se reconoce el mensaje */
				out.println("DEFAULT");
				default_message(resp);
			}

		} catch (SAXException e) {
			PrintWriter out = resp.getWriter();
			out.println("SAXException: " + e.getMessage());
			default_message(resp);
		} catch (ParserConfigurationException e) {
			PrintWriter out = resp.getWriter();
			out.println("ParserException: " + e.getMessage());
			default_message(resp);
		}
	}

	/**
	 * Crea una respuesta vacia
	 * 
	 * @version 1.0
	 */
	private void default_message(HttpServletResponse resp) {
		try {
			PrintWriter out = resp.getWriter();
			out.println("<response></response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escribe en @param resp el XML con los ingrdientes disponibles en la bd
	 * 
	 * El formato del XML generado es:
	 * <response id="0"> <ingrediente>nombre_ingrediente</ingrediente>
	 * <ingrediente>nombre_ingrediente</ingrediente> ... </response>
	 *
	 * @version 1.0
	 */
	private void get_ingredientes(HttpServletResponse resp) {
		// conseguir ingredientes de la bd
		List<String> ingredientes = db.DbMethods.get_lista_ingredientes();

		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.ING_CODE + "\">");

			// escribir ingredientes
			for (String ing : ingredientes) {
				out.println("<ingrediente>");
				out.println(ing);
				out.println("</ingrediente>");
			}

			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escribe en @param resp el XML con las recetas recuperadas de la base de
	 * datos. La peticion esta en @param doc.
	 * 
	 * @version 1.0
	 */
	private void get_recetas(Document doc, HttpServletResponse resp) {
		// parsear la consulta
		String nombre = doc.getElementsByTagName("nombre").item(0).getTextContent();
		String tipo = doc.getElementsByTagName("tipo").item(0).getTextContent();
		List<String> ings = new LinkedList<String>();

		NodeList ingredientes = doc.getElementsByTagName("ingrediente");
		for (int i = 0; i < ingredientes.getLength(); i++) {
			ings.add(ingredientes.item(i).getTextContent());
		}

		// conseguir las recetas de la bd
		List<Receta> recetas = db.DbMethods.get_recetas(nombre, tipo, ings);

		// escribir las recetas en la respuesta
		try {
			PrintWriter out = resp.getWriter();
			out.println("<responde id=\"" + Data.RECETA_CODE + "\">");

			for (Receta r : recetas) {
				out.println("<receta>");

				out.println("<nombre>" + r.getNombre() + "</nombre>");
				out.println("<tipo>" + r.getTipo() + "</tipo>");
				out.println("<instrucciones>" + r.getInstrucciones() + "</instrucciones>");
				out.println("<me_gusta>" + r.getMe_gusta() + "</me_gusta>");
				out.println("<no_me_gusta>" + r.getNo_me_gusta() + "</no_me_gusta>");

				for (Ingrediente i : r.getIngredientes()) {
					out.println("<ingrediente cantidad=\"" + i.getCantidad() + "\" uds=\"" + i.getUds() + "\">"
							+ i.getNombre() + "</ingrediente>");
				}

				out.println("</receta>");
			}

			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
