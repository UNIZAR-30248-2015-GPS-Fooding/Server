/*
 * Listener.java v1.0 22/10/2015
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Listener extends HttpServlet {

	/**
	 * Clase para obtener las peticiones de la aplicacion
	 * 
	 * @version 1.0
	 * @date 22/10/2015
	 */

	/* serial version */
	private static final long serialVersionUID = 1473037731466545577L;

	/* codigos de las request */
	private final int ING_CODE = 0;
	private final int RECETA_CODE = 1;
	private final int USER_CODE = 2;
	private final int GRUPO_CODE = 3;

	/**
	 * Metodo para recibir las peticiones GET de la aplicacion
	 * @version 1.0
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		manageRequest(req, resp);
	}

	/**
	 * Metodo para recibir las peticiones POST de la aplicacion
	 * @version 1.0
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		manageRequest(req, resp);
	}

	/**
	 * Metodo para controlar las peticiones y respuestas del servidor. Toma la
	 * entrada como documento XML y la parsea.
	 * 
	 * Formato XML de request: 
	 * <request id=number> 
	 * 	cuerpo de la request 
	 * </request>
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

			// obtener identificador de la request
			int id = Integer.parseInt(doc.getDocumentElement().getAttribute("id"));

			// hacer distintas cosas dependiendo del identificador
			switch (id) {
			case ING_CODE:	/* ingredientes de la bd */
				get_ingredientes(resp);
				break;
			case RECETA_CODE:	/* receta */
				break;
			case USER_CODE:		/* usuario */
				break;
			case GRUPO_CODE:	/* grupo */
				break;
			default:			/* no se reconoce el mensaje */
				default_message(resp);
			}

		} catch (SAXException e) {
			default_message(resp);
		} catch (ParserConfigurationException e) {
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
	 * 	<response id="0">
	 * 		<ingrediente>nombre_ingrediente</ingrediente>
	 * 		<ingrediente>nombre_ingrediente</ingrediente>
	 * 		...
	 *	</response>
	 *
	 *@version 1.0
	 */
	private void get_ingredientes(HttpServletResponse resp) {
		// conseguir ingredientes de la bd
		List<String> ingredientes = db.DbMethods.get_lista_ingredientes();

		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + ING_CODE + "\">");

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
}
