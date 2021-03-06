/*
 * Listener.java v1.0 22/10/2015
 */

package servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import data.Data;
import data.Ingrediente;
import data.Receta;
import data.Usuario;
import utils.Mail;
import utils.Security;

public class Listener extends HttpServlet {

	/**
	 * Clase para obtener las peticiones de la aplicacion
	 * 
	 * @version 1.1 - GET, POST redirigidos a otro metodo - Default message
	 *          agregado - Obtener ingredientes - Obtener recetas
	 * @date 22/10/2015
	 */

	/* serial version */
	private static final long serialVersionUID = 1473037731466545577L;
	// Atributos privados
	private final static String USERNAME = System.getenv("SERVER_MAIL_USER");
	private final static String PASSWORD = System.getenv("SERVER_MAIL_PASS");

	/**
	 * Metodo para recibir las peticiones GET de la aplicacion
	 * 
	 * @version 1.0
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String xml = req.getParameter("xml");
		InputStream is = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
		manageRequest(is, resp);
	}

	/**
	 * Metodo para recibir las peticiones POST de la aplicacion
	 * 
	 * @version 1.0
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		manageRequest(req.getInputStream(), resp);
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
	private void manageRequest(InputStream is, HttpServletResponse resp) throws IOException {

		resp.setContentType("text/xml; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		// crear parser para XML
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(is, "UTF-8");
			doc.getDocumentElement().normalize();

			// obtener identificador de la request
			int id = Integer.parseInt(doc.getDocumentElement().getAttribute("id"));

			// hacer distintas cosas dependiendo del identificador
			switch (id) {
			case Data.ING_CODE: /* ingredientes de la bd */
				get_ingredientes(resp);
				break;
			case Data.RECETA_CODE: /* receta */
				get_receta_info(doc, resp);
				break;
			case Data.USER_CODE: /* info usuario */
				get_usuario_info(doc, resp);
				break;
			case Data.GRUPO_CODE: /* grupo */
				break;
			case Data.TIPO_CODE: /* tipo */
				get_tipos(resp);
				break;
			case Data.CREAR_USER_CODE: /* crear usuario */
				crear_user(doc, resp);
				break;
			case Data.LOGIN_CODE: /* loguear usuario */
				login_user(doc, resp);
				break;
			case Data.CREAR_REC_CODE: /* crear receta */
				crear_receta(doc, resp);
				break;
			case Data.VOTAR_CODE: /* votar receta */
				votar(doc, resp);
				break;
			case Data.VALORACION_CODE: /* valoracion media */
				valoracion_media(doc, resp);
				break;
			case Data.LIST_USER_CODE: /* lista usuarios */
				get_usuarios(doc, resp);
				break;
			case Data.LIST_RECETA_CODE: /* lista recetas */
				get_recetas(doc, resp);
				break;
			case Data.LIST_FAV_CODE:
				get_favs(doc, resp);
				break;
			case Data.FAV_CODE:
				set_fav(doc, resp);
				break;
			case Data.CHECK_FAV_CODE:
				check_fav(doc, resp);
				break;
			default: /* no se reconoce el mensaje */
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
	 * <response id="0"> <ingrediente>nombre_ingrediente</ingrediente>
	 * <ingrediente>nombre_ingrediente</ingrediente> ... </response>
	 *
	 * @version 1.0
	 */
	private void get_ingredientes(HttpServletResponse resp) {
		// conseguir ingredientes de la bd
		List<String> ingredientes = db.DbMethods.get_lista_ingredientes(false);

		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.ING_CODE + "\">");

			// escribir ingredientes
			for (String ing : ingredientes) {
				out.println("<ingrediente>" + ing + "</ingrediente>");
			}

			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escribe en @param resp el XML con los tipos disponibles en la bd
	 * 
	 * El formato del XML generado es:
	 * <response id="4"> <tipo>nombre_tipo</tipo> </response>
	 * 
	 * @version 1.0
	 */
	private void get_tipos(HttpServletResponse resp) {
		// conseguir tipos de la bd
		List<String> tipos = db.DbMethods.get_tipos(false);

		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.TIPO_CODE + "\">");

			for (String tipo : tipos) {
				out.println("<tipo>" + tipo + "</tipo>");
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
	 * @version 1.1
	 */
	private void get_recetas(Document doc, HttpServletResponse resp) {
		// parsear la consulta
		String nombre = null;
		String tipo = null;
		boolean test = false;

		if (doc.getElementsByTagName("nombre") != null && doc.getElementsByTagName("nombre").getLength() > 0) {
			nombre = doc.getElementsByTagName("nombre").item(0).getTextContent();
		}

		if (doc.getElementsByTagName("tipo") != null && doc.getElementsByTagName("tipo").getLength() > 0) {
			tipo = doc.getElementsByTagName("tipo").item(0).getTextContent();
		}

		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			test = doc.getElementsByTagName("test").item(0).getTextContent().equals("yes");
		}

		List<String> ings = new LinkedList<String>();

		NodeList ingredientes = doc.getElementsByTagName("ingrediente");
		if (ingredientes != null) {
			for (int i = 0; i < ingredientes.getLength(); i++) {
				ings.add(ingredientes.item(i).getTextContent());
			}
		}

		// conseguir las recetas de la bd
		List<Receta> recetas = db.DbMethods.get_lista_recetas(nombre, tipo, ings, test);

		// escribir las recetas en la respuesta
		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.LIST_RECETA_CODE + "\">");

			for (Receta r : recetas) {
				out.println("<receta>");

				out.println("<id>" + r.getId() + "</id>");
				out.println("<nombre>" + r.getNombre() + "</nombre>");
				out.println("<tipo>" + r.getTipo() + "</tipo>");
				out.println("<me_gusta>" + r.getMe_gusta() + "</me_gusta>");
				out.println("<no_me_gusta>" + r.getNo_me_gusta() + "</no_me_gusta>");

				out.println("</receta>");
			}

			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escribe en @param resp el XML con la receta recuperada de la base de
	 * datos. La peticion esta en @param doc.
	 * 
	 * @version 1.0
	 */
	private void get_receta_info(Document doc, HttpServletResponse resp) {
		// parsear la consulta
		int id = -1;
		boolean test = false;

		if (doc.getElementsByTagName("id") != null && doc.getElementsByTagName("id").getLength() > 0) {
			id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
		}

		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			test = doc.getElementsByTagName("test").item(0).getTextContent().equals("yes");
		}

		// conseguir la receta de la bd
		Receta r = db.DbMethods.get_receta(id, test);
		Usuario autor = r.getAutor();

		// escribir las recetas en la respuesta
		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.RECETA_CODE + "\">");

			out.println("<id>" + r.getId() + "</id>");
			out.println("<nombre>" + r.getNombre() + "</nombre>");
			out.println("<tipo>" + r.getTipo() + "</tipo>");
			out.println("<instrucciones>" + r.getInstrucciones() + "</instrucciones>");
			out.println("<nick_autor>" + autor.getNick() + "</nick_autor>");
			out.println("<mail_autor>" + autor.getMail() + "</mail_autor>");
			out.println("<me_gusta>" + r.getMe_gusta() + "</me_gusta>");
			out.println("<no_me_gusta>" + r.getNo_me_gusta() + "</no_me_gusta>");

			if (r.getIngredientes() != null) {
				for (Ingrediente i : r.getIngredientes()) {
					out.println("<ingrediente cantidad=\"" + i.getCantidad() + "\" uds=\"" + i.getUds() + "\">"
							+ i.getNombre() + "</ingrediente>");
				}
			}

			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escribe en @param resp el XML con los usuarios disponibles en la bd
	 * 
	 * El formato del XML generado es:
	 * <response id=”10”> <usuario> <nick>nick</nick>
	 * <score>numero</score> </usuario> </response>
	 *
	 * @version 1.1
	 */
	private void get_usuarios(Document doc, HttpServletResponse resp) {

		// parsear la consulta
		String nick = null;
		boolean test = false;

		if (doc.getElementsByTagName("nick") != null && doc.getElementsByTagName("nick").getLength() > 0) {
			nick = doc.getElementsByTagName("nick").item(0).getTextContent();
		}

		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			test = doc.getElementsByTagName("test").item(0).getTextContent().equalsIgnoreCase("yes");
		}

		// conseguir usuarios de la bd
		List<Usuario> usuarios = db.DbMethods.get_lista_usuarios(nick, test);

		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.LIST_USER_CODE + "\">");

			// escribir informacion de usuarios
			for (Usuario user : usuarios) {
				out.println("<usuario>");

				out.println("<mail>" + user.getMail() + "</mail>");
				out.println("<nick>" + user.getNick() + "</nick>");
				out.println("<score>" + user.getScore() + "</score>");

				out.println("</usuario>");
			}

			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escribe en @param resp el XML con la informacion del usuario cuyo mail
	 * coincide con el solicitado
	 * 
	 * El formato del XML generado es:
	 * <response id=”2”> <usuario> <nick>nick</nick> <score>numero</score>
	 * <receta id="numero">nombre</receta> </usuario> </response>
	 *
	 * @version 1.0
	 */
	private void get_usuario_info(Document doc, HttpServletResponse resp) {
		// parsear la consulta
		String mail = null;
		boolean test = false;

		if (doc.getElementsByTagName("mail") != null && doc.getElementsByTagName("mail").getLength() > 0) {
			mail = doc.getElementsByTagName("mail").item(0).getTextContent();
		}

		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			test = doc.getElementsByTagName("test").item(0).getTextContent().equalsIgnoreCase("yes");
		}

		// consigue el usuario de la bd con mail <mail>
		Usuario user = db.DbMethods.get_usuario(mail, test);

		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.USER_CODE + "\">");

			// escribe la informacion del usuario
			out.println("<mail>" + user.getMail() + "</mail>");
			out.println("<nick>" + user.getNick() + "</nick>");
			out.println("<score>" + user.getScore() + "</score>");

			// escribe las recetas del usuario
			if (user.getRecetas() != null) {
				for (Receta r : user.getRecetas()) {
					out.println("<receta id=\"" + r.getId() + "\">" + r.getNombre() + "</receta>");
				}
			}

			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crea el usuario contenido en @param doc y escribe en @param resp si ha
	 * sido creado o no.
	 * 
	 * @version 1.0
	 */
	private void crear_user(Document doc, HttpServletResponse resp) {
		// parsear la consulta
		String mail = null;
		String nick = null;
		String pw = null;
		boolean test = true;

		if (doc.getElementsByTagName("mail") != null && doc.getElementsByTagName("mail").getLength() > 0) {
			mail = doc.getElementsByTagName("mail").item(0).getTextContent();
		}
		if (doc.getElementsByTagName("nick") != null && doc.getElementsByTagName("nick").getLength() > 0) {
			nick = doc.getElementsByTagName("nick").item(0).getTextContent();
		}
		if (doc.getElementsByTagName("pw") != null && doc.getElementsByTagName("pw").getLength() > 0) {
			pw = doc.getElementsByTagName("pw").item(0).getTextContent();
		}
		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			test = doc.getElementsByTagName("test").item(0).getTextContent().equalsIgnoreCase("yes");
		}
		String uniqueKey = "NULL";
		try {
			uniqueKey = Security.encrypt_password(mail + USERNAME + PASSWORD);
		} catch (NoSuchAlgorithmException e1) {
			// Pero como no va a existir
		}
		// registrar al usuario
		boolean registrado = db.DbMethods.registrar_usuario(mail, nick, pw, uniqueKey, test);

		if (registrado && !test) {
			registrado = Mail.sendRegistrationMail(mail, uniqueKey);
		}

		// informar al usuario
		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.CREAR_USER_CODE + "\">");
			if (registrado) {
				out.println("<hecho>yes</hecho>");
			} else {
				out.println("<hecho>no</hecho>");
			}
			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Borrar si no se pudo mandar el correo
		if (!registrado) {
			db.DbMethods.borrar_usuario(mail, test);
		}
	}

	/**
	 * Loguea al usuario contenido en @param doc y escribe en @param resp si ha
	 * sido logueado satisfactoriamente o no.
	 * 
	 * @version 1.0
	 */
	private void login_user(Document doc, HttpServletResponse resp) {
		// parsear la consulta
		String mail = null;
		String pw = null;
		String t = null;
		boolean test = false;

		if (doc.getElementsByTagName("mail") != null && doc.getElementsByTagName("mail").getLength() > 0) {
			mail = doc.getElementsByTagName("mail").item(0).getTextContent();
		}
		if (doc.getElementsByTagName("pw") != null && doc.getElementsByTagName("pw").getLength() > 0) {
			pw = doc.getElementsByTagName("pw").item(0).getTextContent();
		}
		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			t = doc.getElementsByTagName("test").item(0).getTextContent();
			test = t.equalsIgnoreCase("yes");
		}

		// loguear al usuario
		boolean login = db.DbMethods.login_usuario(mail, pw, test);

		// informar al usuario
		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.LOGIN_CODE + "\">");
			if (login) {
				out.println("<hecho>yes</hecho>");
			} else {
				out.println("<hecho>no</hecho>");
			}
			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crea una nueva receta en la BD
	 */
	private void crear_receta(Document doc, HttpServletResponse resp) {
		// parsear la consulta
		String nombre = null;
		String mail = null;
		String tipo = null;
		String instrucciones = null;
		List<Ingrediente> ings = null;
		boolean test = true;

		if (doc.getElementsByTagName("mail") != null && doc.getElementsByTagName("mail").getLength() > 0) {
			mail = doc.getElementsByTagName("mail").item(0).getTextContent();
		}
		if (doc.getElementsByTagName("nombre") != null && doc.getElementsByTagName("nombre").getLength() > 0) {
			nombre = doc.getElementsByTagName("nombre").item(0).getTextContent();
		}
		if (doc.getElementsByTagName("tipo") != null && doc.getElementsByTagName("tipo").getLength() > 0) {
			tipo = doc.getElementsByTagName("tipo").item(0).getTextContent();
		}
		if (doc.getElementsByTagName("instrucciones") != null
				&& doc.getElementsByTagName("instrucciones").getLength() > 0) {
			instrucciones = doc.getElementsByTagName("instrucciones").item(0).getTextContent();
		}
		if (doc.getElementsByTagName("ingrediente") != null
				&& doc.getElementsByTagName("ingrediente").getLength() > 0) {
			ings = new LinkedList<Ingrediente>();
			for (int i = 0; i < doc.getElementsByTagName("ingrediente").getLength(); i++) {
				Element e = (Element) doc.getElementsByTagName("ingrediente").item(i);

				String nom = e.getTextContent();
				String uds = null;
				int cant = -1;

				if (e.hasAttribute("uds")) {
					uds = e.getAttribute("uds");
				}
				if (e.hasAttribute("cantidad")) {
					cant = Integer.parseInt(e.getAttribute("cantidad"));
				}
				Ingrediente ing = new Ingrediente();
				ing.setNombre(nom);
				ing.setCantidad(cant);
				ing.setUds(uds);
				ings.add(ing);
			}
		}
		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			test = doc.getElementsByTagName("test").item(0).getTextContent().equalsIgnoreCase("yes");
		}

		// crear receta
		boolean creada = db.DbMethods.crear_receta(mail, nombre, tipo, instrucciones, ings, test);

		// informar al usuario
		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.CREAR_REC_CODE + "\">");
			if (creada) {
				out.println("<hecho>yes</hecho>");
			} else {
				out.println("<hecho>no</hecho>");
			}
			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Registrar el voto del usuario
	 */
	private void votar(Document doc, HttpServletResponse resp) {
		int id = -1;
		String mail = null;
		boolean test = false;
		int voto = 0;

		if (doc.getElementsByTagName("id") != null && doc.getElementsByTagName("id").getLength() > 0) {
			id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
		}

		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			test = doc.getElementsByTagName("test").item(0).getTextContent().equals("yes");
		}

		if (doc.getElementsByTagName("voto") != null && doc.getElementsByTagName("voto").getLength() > 0) {
			if (doc.getElementsByTagName("voto").item(0).getTextContent().equals("mg")) {
				voto = 1;
			} else if (doc.getElementsByTagName("voto").item(0).getTextContent().equals("nmg")) {
				voto = -1;
			}
		}

		if (doc.getElementsByTagName("mail") != null && doc.getElementsByTagName("mail").getLength() > 0) {
			mail = doc.getElementsByTagName("mail").item(0).getTextContent();
		}

		// Booleanito
		boolean hecho = db.DbMethods.votar(id, mail, voto, test);
		// informar al usuario
		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.VOTAR_CODE + "\">");
			if (hecho) {
				out.println("<hecho>yes</hecho>");
			} else {
				out.println("<hecho>no</hecho>");
			}
			out.println("</response>");
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Obtener la valoracion media de una receta
	 * 
	 * @param doc
	 * @param resp
	 */
	private void valoracion_media(Document doc, HttpServletResponse resp) {
		int id = -1;
		boolean test = false;

		if (doc.getElementsByTagName("id") != null && doc.getElementsByTagName("id").getLength() > 0) {
			id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
		}

		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			test = doc.getElementsByTagName("test").item(0).getTextContent().equals("yes");
		}

		double valoracion = db.DbMethods.valoracion_media(id, test);
		// informar al usuario
		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.VALORACION_CODE + "\">");
			out.println("<valoracion>" + valoracion + "</valoracion>");
			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devolver los favoritos de un usuario
	 */
	private void get_favs(Document doc, HttpServletResponse resp) {
		String mail = null;
		boolean test = false;

		if (doc.getElementsByTagName("mail") != null && doc.getElementsByTagName("mail").getLength() > 0) {
			mail = doc.getElementsByTagName("mail").item(0).getTextContent();
		}
		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			test = doc.getElementsByTagName("test").item(0).getTextContent().equals("yes");
		}

		// conseguir las recetas de la bd
		List<Receta> recetas = db.DbMethods.get_favs(mail, test);

		// escribir las recetas en la respuesta
		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.LIST_FAV_CODE + "\">");

			if(recetas != null){
			for (Receta r : recetas) {
				out.println("<receta>");

				out.println("<id>" + r.getId() + "</id>");
				out.println("<nombre>" + r.getNombre() + "</nombre>");
				out.println("<tipo>" + r.getTipo() + "</tipo>");
				out.println("<me_gusta>" + r.getMe_gusta() + "</me_gusta>");
				out.println("<no_me_gusta>" + r.getNo_me_gusta() + "</no_me_gusta>");

				out.println("</receta>");
			}
			}

			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Registrar el favorito del usuario
	 */
	private void set_fav(Document doc, HttpServletResponse resp) {
		int id = -1;
		String mail = null;
		boolean test = false;

		if (doc.getElementsByTagName("id") != null && doc.getElementsByTagName("id").getLength() > 0) {
			id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
		}

		if (doc.getElementsByTagName("mail") != null && doc.getElementsByTagName("mail").getLength() > 0) {
			mail = doc.getElementsByTagName("mail").item(0).getTextContent();
		}
		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			test = doc.getElementsByTagName("test").item(0).getTextContent().equals("yes");
		}

		// Booleanito
		boolean hecho = db.DbMethods.set_fav(id, mail, test);

		// informar al usuario
		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.FAV_CODE + "\">");
			if (hecho) {
				out.println("<hecho>yes</hecho>");
			} else {
				out.println("<hecho>no</hecho>");
			}
			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Comprobar el favorito del usuario
	 */
	private void check_fav(Document doc, HttpServletResponse resp) {
		int id = -1;
		String mail = null;
		boolean test = false;

		if (doc.getElementsByTagName("id") != null && doc.getElementsByTagName("id").getLength() > 0) {
			id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
		}

		if (doc.getElementsByTagName("mail") != null && doc.getElementsByTagName("mail").getLength() > 0) {
			mail = doc.getElementsByTagName("mail").item(0).getTextContent();
		}

		if (doc.getElementsByTagName("test") != null && doc.getElementsByTagName("test").getLength() > 0) {
			test = doc.getElementsByTagName("test").item(0).getTextContent().equals("yes");
		}

		// Booleanito
		boolean hecho = db.DbMethods.check_fav(id, mail, test);

		// informar al usuario
		try {
			PrintWriter out = resp.getWriter();
			out.println("<response id=\"" + Data.CHECK_FAV_CODE + "\">");
			if (hecho) {
				out.println("<favorita>yes</favorita>");
			} else {
				out.println("<favorita>no</favorita>");
			}
			out.println("</response>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
