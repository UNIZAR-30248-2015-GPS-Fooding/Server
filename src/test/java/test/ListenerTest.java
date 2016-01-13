/*
 * DbMethodsTest.java v1.1 30/10/2015
 */

package test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import data.Data;
import data.Ingrediente;
import data.Receta;
import data.Usuario;
import db.DbMethods;
import servlets.Listener;

public class ListenerTest {
	/**
	 * Clase para tests sobre los metodos del servlet Listener
	 * 
	 * @version 1.0
	 * @date 16/11/2015
	 */

	private Listener servlet;
	private MockHttpServletRequest req;
	private MockHttpServletResponse resp;

	/**
	 * Setup para los tests del Listener
	 */
	@Before
	public void setUp() {
		servlet = new Listener();
		req = new MockHttpServletRequest();
		resp = new MockHttpServletResponse();
	}

	/**
	 * Testea que devuelva el mensaje por defecto cuando la peticion no esta
	 * bien formada (GET)
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_get_default_message() throws ServletException, IOException {
		String xml = "default_message";
		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty());
	}

	/**
	 * Testea que devuelva el mensaje por defecto cuando la peticion no esta
	 * bien formada (POST)
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_post_default_message() throws ServletException, IOException {
		String xml = "default_message";
		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty());
	}

	/**
	 * Testea que devuelva una lista de ingredientes (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_ingredientes() throws ServletException, IOException {
		String xml = "<request id=\"" + data.Data.ING_CODE + "\"></request>";

		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("ingrediente"));
	}

	/**
	 * Testea que devuelva una lista de ingredientes (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_ingredientes() throws ServletException, IOException {
		String xml = "<request id=\"" + data.Data.ING_CODE + "\"></request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("ingrediente"));
	}

	/**
	 * Testea que devuelve una lista de tipos (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_tipos() throws ServletException, IOException {
		String xml = "<request id=\"" + data.Data.TIPO_CODE + "\"></request>";
		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("tipo"));
	}

	/**
	 * Testea que devuelve una lista de tipos (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_tipos() throws ServletException, IOException {
		String xml = "<request id=\"" + data.Data.TIPO_CODE + "\"></request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("tipo"));
	}

	/**
	 * Testea que devuelve la informacion de una receta (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_receta() throws ServletException, IOException {

		/* Obtiene el id de una receta de test */
		List<Receta> lista_recetas = DbMethods.get_lista_recetas(null, null, null, true);
		int idRecetaPrueba = 0;
		if (lista_recetas != null && lista_recetas.size() > 0) {
			idRecetaPrueba = lista_recetas.get(0).getId();
		}

		String xml = "<request id=\"" + data.Data.RECETA_CODE + "\">" + "<id>" + idRecetaPrueba + "</id>"
				+ "<test>yes</test>" + "</request>";

		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("instrucciones"));
	}

	/**
	 * Testea que devuelve la informacion de una receta (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_receta() throws ServletException, IOException {

		/* Obtiene el id de una receta de test */
		List<Receta> lista_recetas = DbMethods.get_lista_recetas(null, null, null, true);
		int idRecetaPrueba = 0;
		if (lista_recetas != null && lista_recetas.size() > 0) {
			idRecetaPrueba = lista_recetas.get(0).getId();
		}

		String xml = "<request id=\"" + data.Data.RECETA_CODE + "\">" + "<id>" + idRecetaPrueba + "</id>"
				+ "<test>yes</test>" + "</request>";

		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("instrucciones"));
	}

	/**
	 * Testea que devuelve una lista de recetas (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_lista_recetas() throws ServletException, IOException {
		String xml = "<request id=\"" + data.Data.LIST_RECETA_CODE + "\"></request>";
		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("receta"));
	}

	/**
	 * Testea que devuelve una lista de recetas (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_lista_recetas() throws ServletException, IOException {
		String xml = "<request id=\"" + data.Data.LIST_RECETA_CODE + "\"></request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("receta"));
	}

	/**
	 * Testea que devuelve la informacion de un usuario (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_usuario() throws ServletException, IOException {

		// crea un usuario
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);

		// crea la peticion
		String xml = "<request id=\"" + data.Data.USER_CODE + "\">" + "<mail>" + nombre + "</mail>" + "<test>yes</test>"
				+ "</request>";
		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		// borra al usuario creado
		DbMethods.borrar_usuario(nombre, true);

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("score"));
	}

	/**
	 * Testea que devuelve la informacion de un usuario (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_usuario() throws ServletException, IOException {

		// crea un usuario
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);

		String xml = "<request id=\"" + data.Data.USER_CODE + "\">" + "<mail>" + nombre + "</mail>" + "<test>yes</test>"
				+ "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		// borra al usuario creado
		DbMethods.borrar_usuario(nombre, true);

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("score"));
	}

	/**
	 * Testea que devuelve una lista de usuarios (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_lista_usuarios() throws ServletException, IOException {
		String xml = "<request id=\"" + data.Data.LIST_USER_CODE + "\">" + "<nick>nick_prueba</nick>"
				+ "<test>yes</test>" + "</request>";
		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("usuario"));
	}

	/**
	 * Testea que devuelve una lista de usuarios (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_lista_usuarios() throws ServletException, IOException {
		String xml = "<request id=\"" + data.Data.LIST_USER_CODE + "\">" + "<nick>nick_prueba</nick>"
				+ "<test>yes</test>" + "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("usuario"));
	}

	/**
	 * Testea la creacion de usuarios (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_crear_user() throws ServletException, IOException {
		String nombreGet = "pruebaListenerTest0" + System.nanoTime();

		String respuesta = get_crear_user(nombreGet);

		assertTrue(
				respuesta != null && !respuesta.isEmpty() && respuesta.contains("hecho") && respuesta.contains("yes"));
	}

	/**
	 * Testea la creacion de usuarios (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_crear_user() throws ServletException, IOException {
		String nombrePOST = "pruebaListenerTest1" + System.nanoTime();
		String respuesta = post_crear_user(nombrePOST);

		assertTrue(
				respuesta != null && !respuesta.isEmpty() && respuesta.contains("hecho") && respuesta.contains("yes"));
	}

	/**
	 * Testea el logueo de usuarios (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_login_user() throws ServletException, IOException {
		String nombreGet = "pruebaListenerTest0" + System.nanoTime();

		get_crear_user(nombreGet);

		String xml = "<request id=\"" + data.Data.LOGIN_CODE + "\">" + "<mail>" + nombreGet + "</mail>"
				+ "<pw>pw_prueba</pw>" + "<test>yes</test>" + "</request>";
		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(
				respuesta != null && !respuesta.isEmpty() && respuesta.contains("hecho") && respuesta.contains("yes"));
	}

	/**
	 * Testea el logueo de usuarios (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_login_user() throws ServletException, IOException {
		String nombrePOST = "pruebaListenerTest1" + System.nanoTime();
		post_crear_user(nombrePOST);

		String xml = "<request id=\"" + data.Data.LOGIN_CODE + "\">" + "<mail>" + nombrePOST + "</mail>"
				+ "<pw>pw_prueba</pw>" + "<test>yes</test>" + "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(
				respuesta != null && !respuesta.isEmpty() && respuesta.contains("hecho") && respuesta.contains("yes"));
	}

	/**
	 * Testea la creacion de recetas (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_crear_receta() throws ServletException, IOException {
		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();

		String xml = "<request id=\"" + data.Data.CREAR_REC_CODE + "\">" + "<mail>" + mailUsuario + "</mail>"
				+ "<nombre>NombreGET" + System.nanoTime() + "</nombre>" + "<tipo>Pasta</tipo>"
				+ "<instrucciones>Instrucciones</instrucciones>" + "<test>yes</test>" + "</request>";
		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(
				respuesta != null && !respuesta.isEmpty() && respuesta.contains("hecho") && respuesta.contains("yes"));
	}

	/**
	 * Testea la creacion de recetas (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_crear_receta() throws ServletException, IOException {
		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();

		String xml = "<request id=\"" + data.Data.CREAR_REC_CODE + "\">" + "<mail>" + mailUsuario + "</mail>"
				+ "<nombre>NombrePOST" + System.nanoTime() + "</nombre>" + "<tipo>Pasta</tipo>"
				+ "<instrucciones>Instrucciones</instrucciones>"
				+ "<ingrediente cantidad=\"1\" uds=\"uds\">ingrediente</ingrediente>" + "<test>yes</test>"
				+ "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(
				respuesta != null && !respuesta.isEmpty() && respuesta.contains("hecho") && respuesta.contains("yes"));
	}

	/**
	 * Testea la valoracion de recetas (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_valorar_receta() throws ServletException, IOException {
		String mail = "ListenerTest" + System.nanoTime();
		if (!DbMethods.registrar_usuario(mail, mail, "pw", "NULL", true)) {
			assertTrue("usuario no reigstrado", false);
		}

		String nombre = "NombreListener" + System.nanoTime();
		String tipo = "Pasta";
		String instrucciones = "Instrucciones";
		List<Ingrediente> ings = new LinkedList<Ingrediente>();

		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());

		ings.add(ing);
		if (!DbMethods.crear_receta(mail, nombre, tipo, instrucciones, ings, true)) {
			assertTrue("No se ha creado la receta", false);
		}

		List<Receta> recetas = DbMethods.get_lista_recetas(nombre, null, null, true);
		if (recetas == null || recetas.isEmpty()) {
			assertTrue("receta no recuperada", false);
		}
		int id = recetas.get(0).getId();

		String xml = "<request id=\"" + data.Data.VOTAR_CODE + "\">" + "<id>" + id + "</id>" + "<mail>" + mail
				+ "</mail>" + "<voto>" + 1 + "</voto>" + "<test>yes</test>" + "</request>";
		req.addParameter("xml", xml);
		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(
				respuesta != null && !respuesta.isEmpty() && respuesta.contains("hecho") && respuesta.contains("yes"));
	}

	/**
	 * Testea la valoracion de recetas (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_valorar_receta() throws ServletException, IOException {
		String mail = "ListenerTest" + System.nanoTime();
		if (!DbMethods.registrar_usuario(mail, mail, "pw", "NULL", true)) {
			assertTrue("usuario no reigstrado", false);
		}

		String nombre = "NombreListener2" + System.nanoTime();
		String tipo = "Pasta";
		String instrucciones = "Instrucciones";
		List<Ingrediente> ings = new LinkedList<Ingrediente>();

		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());

		ings.add(ing);
		if (!DbMethods.crear_receta(mail, nombre, tipo, instrucciones, ings, true)) {
			assertTrue("No se ha creado la receta", false);
		}

		List<Receta> recetas = DbMethods.get_lista_recetas(nombre, null, null, true);
		if (recetas == null || recetas.isEmpty()) {
			assertTrue("receta no recuperada", false);
		}
		int id = recetas.get(0).getId();

		String xml = "<request id=\"" + data.Data.VOTAR_CODE + "\">" + "<id>" + id + "</id>" + "<mail>" + mail
				+ "</mail>" + "<voto>" + 1 + "</voto>" + "<test>yes</test>" + "</request>";
		req.setContent(xml.getBytes());
		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(
				respuesta != null && !respuesta.isEmpty() && respuesta.contains("hecho") && respuesta.contains("yes"));
	}

	/**
	 * Testea la valoracion media de recetas (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_valoracion_media() throws ServletException, IOException {

		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();
		/* crear receta */

		List<Ingrediente> ings = new LinkedList<Ingrediente>();
		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());
		ings.add(ing);
		String nombreReceta = "nombreDB" + System.nanoTime();
		if (!DbMethods.crear_receta(nombre, nombreReceta, "Pasta", "instrucciones", ings, true)) {
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_lista_recetas(nombreReceta, null, null, true);
		if (recetas == null || recetas.isEmpty()) {
			assertTrue("receta no encontrada", false);
		}
		int idReceta = recetas.get(0).getId();

		assertTrue(DbMethods.votar(idReceta, mailUsuario, 1, true));

		/* crear usuario 2 */
		nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		user = DbMethods.get_usuario(nombre, true);
		mailUsuario = user.getMail();

		assertTrue(DbMethods.votar(idReceta, mailUsuario, 1, true));

		String xml = "<request id=\"" + Data.VALORACION_CODE + "\">" + "<id>" + idReceta + "</id>" + "<test>yes</test>"
				+ "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("valoracion")
				&& respuesta.contains("1"));
	}

	/**
	 * Testea la valoracion media de recetas (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_valoracion_media() throws ServletException, IOException {

		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();

		/* crear receta */
		List<Ingrediente> ings = new LinkedList<Ingrediente>();
		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());
		ings.add(ing);
		String nombreReceta = "nombreDB" + System.nanoTime();
		if (!DbMethods.crear_receta(nombre, nombreReceta, "Pasta", "instrucciones", ings, true)) {
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_lista_recetas(nombreReceta, null, null, true);
		if (recetas == null || recetas.isEmpty()) {
			assertTrue("receta no encontrada", false);
		}
		int idReceta = recetas.get(0).getId();

		assertTrue(DbMethods.votar(idReceta, mailUsuario, 1, true));

		/* crear usuario 2 */
		nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		user = DbMethods.get_usuario(nombre, true);
		mailUsuario = user.getMail();

		assertTrue(DbMethods.votar(idReceta, mailUsuario, 1, true));

		String xml = "<request id=\"" + Data.VALORACION_CODE + "\">" + "<id>" + idReceta + "</id>" + "<test>yes</test>"
				+ "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("valoracion")
				&& respuesta.contains("1"));
	}

	/**
	 * Testea la valoracion media de recetas (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_lista_favoritas() throws ServletException, IOException {

		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();

		/* crear receta */
		List<Ingrediente> ings = new LinkedList<Ingrediente>();
		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());
		ings.add(ing);
		String nombreReceta = "nombreDB" + System.nanoTime();
		if (!DbMethods.crear_receta(nombre, nombreReceta, "Pasta", "instrucciones", ings, true)) {
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_lista_recetas(nombreReceta, null, null, true);
		if (recetas == null || recetas.isEmpty()) {
			assertTrue("receta no encontrada", false);
		}
		int idReceta = recetas.get(0).getId();

		assertTrue(DbMethods.setFav(idReceta, mailUsuario, true));

		String xml = "<request id=\"" + Data.LIST_FAV_CODE + "\">" + "<mail>" + mailUsuario + "</mail>"
				+ "<test>yes</test>" + "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("receta"));
	}

	/**
	 * Testea la valoracion media de recetas (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_lista_favoritas() throws ServletException, IOException {

		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();

		/* crear receta */
		List<Ingrediente> ings = new LinkedList<Ingrediente>();
		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());
		ings.add(ing);
		String nombreReceta = "nombreDB" + System.nanoTime();
		if (!DbMethods.crear_receta(nombre, nombreReceta, "Pasta", "instrucciones", ings, true)) {
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_lista_recetas(nombreReceta, null, null, true);
		if (recetas == null || recetas.isEmpty()) {
			assertTrue("receta no encontrada", false);
		}
		int idReceta = recetas.get(0).getId();

		assertTrue(DbMethods.setFav(idReceta, mailUsuario, true));

		String xml = "<request id=\"" + Data.LIST_FAV_CODE + "\">" + "<mail>" + mailUsuario + "</mail>"
				+ "<test>yes</test>" + "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("receta"));
	}

	/**
	 * Testea la valoracion media de recetas (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_set_favorita() throws ServletException, IOException {

		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();

		/* crear receta */
		List<Ingrediente> ings = new LinkedList<Ingrediente>();
		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());
		ings.add(ing);
		String nombreReceta = "nombreDB" + System.nanoTime();
		if (!DbMethods.crear_receta(nombre, nombreReceta, "Pasta", "instrucciones", ings, true)) {
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_lista_recetas(nombreReceta, null, null, true);
		if (recetas == null || recetas.isEmpty()) {
			assertTrue("receta no encontrada", false);
		}
		int idReceta = recetas.get(0).getId();

		String xml = "<request id=\"" + Data.FAV_CODE + "\">" + "<mail>" + mailUsuario + "</mail>" + "<id>" + idReceta
				+ "</id>" + "<test>yes</test>" + "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("hecho")
				&& DbMethods.checkFav(idReceta, mailUsuario, true));
	}

	/**
	 * Testea la valoracion media de recetas (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_set_favorita() throws ServletException, IOException {

		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();

		/* crear receta */
		List<Ingrediente> ings = new LinkedList<Ingrediente>();
		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());
		ings.add(ing);
		String nombreReceta = "nombreDB" + System.nanoTime();
		if (!DbMethods.crear_receta(nombre, nombreReceta, "Pasta", "instrucciones", ings, true)) {
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_lista_recetas(nombreReceta, null, null, true);
		if (recetas == null || recetas.isEmpty()) {
			assertTrue("receta no encontrada", false);
		}
		int idReceta = recetas.get(0).getId();

		String xml = "<request id=\"" + Data.FAV_CODE + "\">" + "<mail>" + mailUsuario + "</mail>" + "<id>" + idReceta
				+ "</id>" + "<test>yes</test>" + "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("hecho")
				&& DbMethods.checkFav(idReceta, mailUsuario, true));
	}

	/**
	 * Testea la valoracion media de recetas (GET)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_get_check_favorita() throws ServletException, IOException {

		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();

		/* crear receta */
		List<Ingrediente> ings = new LinkedList<Ingrediente>();
		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());
		ings.add(ing);
		String nombreReceta = "nombreDB" + System.nanoTime();
		if (!DbMethods.crear_receta(nombre, nombreReceta, "Pasta", "instrucciones", ings, true)) {
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_lista_recetas(nombreReceta, null, null, true);
		if (recetas == null || recetas.isEmpty()) {
			assertTrue("receta no encontrada", false);
		}
		int idReceta = recetas.get(0).getId();

		assertTrue(DbMethods.setFav(idReceta, mailUsuario, true));

		String xml = "<request id=\"" + Data.CHECK_FAV_CODE + "\">" + "<mail>" + mailUsuario + "</mail>"
				+ "<id>" + idReceta + "</id>"
				+ "<test>yes</test>" + "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("favorita"));
	}

	/**
	 * Testea la valoracion media de recetas (POST)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void test_post_check_favorita() throws ServletException, IOException {
		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();

		/* crear receta */
		List<Ingrediente> ings = new LinkedList<Ingrediente>();
		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());
		ings.add(ing);
		String nombreReceta = "nombreDB" + System.nanoTime();
		if (!DbMethods.crear_receta(nombre, nombreReceta, "Pasta", "instrucciones", ings, true)) {
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_lista_recetas(nombreReceta, null, null, true);
		if (recetas == null || recetas.isEmpty()) {
			assertTrue("receta no encontrada", false);
		}
		int idReceta = recetas.get(0).getId();

		assertTrue(DbMethods.setFav(idReceta, mailUsuario, true));

		String xml = "<request id=\"" + Data.CHECK_FAV_CODE + "\">" + "<mail>" + mailUsuario + "</mail>"
				+ "<id>" + idReceta + "</id>"
				+ "<test>yes</test>" + "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		assertTrue(respuesta != null && !respuesta.isEmpty() && respuesta.contains("favorita"));
	}
	
	/**
	 * @param mail
	 *            email del usuario nuevo
	 * @return respuesta del servidor
	 * @throws ServletException
	 * @throws IOException
	 */
	private String get_crear_user(String mail) throws ServletException, IOException {
		String xml = "<request id=\"" + data.Data.CREAR_USER_CODE + "\">" + "<mail>" + mail + "</mail>"
				+ "<nick>nick_prueba</nick>" + "<pw>pw_prueba</pw>" + "<test>yes</test>" + "</request>";
		req.addParameter("xml", xml);

		servlet.doGet(req, resp);

		String respuesta = resp.getContentAsString();
		return respuesta;
	}

	/**
	 * @param mail
	 *            email del nuevo usuario
	 * @return respuesta del servidor
	 * @throws ServletException
	 * @throws IOException
	 */
	private String post_crear_user(String mail) throws ServletException, IOException {
		String xml = "<request id=\"" + data.Data.CREAR_USER_CODE + "\">" + "<mail>" + mail + "</mail>"
				+ "<nick>nick_prueba</nick>" + "<pw>pw_prueba</pw>" + "<test>yes</test>" + "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<", "<");

		req.setContent(xml.getBytes());

		servlet.doPost(req, resp);

		String respuesta = resp.getContentAsString();

		return respuesta;
	}
}
