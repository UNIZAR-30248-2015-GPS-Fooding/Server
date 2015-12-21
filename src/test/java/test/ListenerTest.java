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

public class ListenerTest{
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
	public void setUp(){
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
	public void test_get_default_message() throws ServletException, IOException{
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
	public void test_post_default_message() throws ServletException, IOException{
		String xml = "default_message";
		req.setContent(xml.getBytes());
		
		servlet.doPost(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty());
	}
	
	/**
	 * Testea que devuelva una lista de ingredientes (GET)
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_get_ingredientes() throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.ING_CODE +"\"></request>";

		req.addParameter("xml", xml);
		
		servlet.doGet(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
					&& respuesta.contains("ingrediente"));
	}
	
	/**
	 * Testea que devuelva una lista de ingredientes (POST)
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_post_ingredientes() throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.ING_CODE +"\"></request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<","<");
		
		req.setContent(xml.getBytes());
		
		servlet.doPost(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("ingrediente"));
	}

	/**
	 * Testea que devuelve una lista de tipos (GET)
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_get_tipos() throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.TIPO_CODE +"\"></request>";
		req.addParameter("xml", xml);
		
		servlet.doGet(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("tipo"));
	}
	
	/**
	 * Testea que devuelve una lista de tipos (POST)
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_post_tipos() throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.TIPO_CODE +"\"></request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<","<");
		
		req.setContent(xml.getBytes());
		
		servlet.doPost(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("tipo"));
	}
	
	/**
	 * Testea que devuelve una lista de recetas (GET)
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_get_recetas() throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.RECETA_CODE +"\"></request>";
		req.addParameter("xml", xml);
		
		servlet.doGet(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("receta"));
	}
	
	/**
	 * Testea que devuelve una lista de recetas (POST)
	 * 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_post_recetass() throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.RECETA_CODE +"\"></request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<","<");
		
		req.setContent(xml.getBytes());
		
		servlet.doPost(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("receta"));
	}
	
	/**
	 * Testea la creacion de usuarios (GET)
	 * 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_get_crear_user() throws ServletException, IOException{
		String nombreGet = "pruebaListenerTest0" + System.nanoTime();
		
		String respuesta = get_crear_user(nombreGet);
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("hecho")
				&& respuesta.contains("yes"));
	}
	
	/**
	 * Testea la creacion de usuarios (POST)
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_post_crear_user() throws ServletException, IOException{
		String nombrePOST = "pruebaListenerTest1" + System.nanoTime();
		String respuesta = post_crear_user(nombrePOST);
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("hecho")
				&& respuesta.contains("yes"));
	}
	
	/**
	 * Testea el logueo de usuarios (GET)
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_get_login_user() throws ServletException, IOException{
		String nombreGet = "pruebaListenerTest0" + System.nanoTime();
		
		get_crear_user(nombreGet);
		
		String xml = "<request id=\"" + data.Data.LOGIN_CODE +"\">" 
				+ "<mail>" + nombreGet + "</mail>"
				+ "<pw>pw_prueba</pw>"
				+ "<test>yes</test>"
				+ "</request>";
		req.addParameter("xml", xml);
		
		servlet.doGet(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("hecho")
				&& respuesta.contains("yes"));
	}
	
	/**
	 * Testea el logueo de usuarios (POST) 
	 * 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_post_login_user() throws ServletException, IOException{
		String nombrePOST = "pruebaListenerTest1" + System.nanoTime();
		post_crear_user(nombrePOST);
		
		String xml = "<request id=\"" + data.Data.LOGIN_CODE +"\">"
				+ "<mail>" + nombrePOST + "</mail>"
				+ "<pw>pw_prueba</pw>"
				+ "<test>yes</test>"
				+ "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<","<");
		
		req.setContent(xml.getBytes());
		
		servlet.doPost(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("hecho")
				&& respuesta.contains("yes"));
	}
	
	/**
	 * Testea la creacion de recetas (GET)
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_get_crear_receta() throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.CREAR_REC_CODE +"\">" 
				+ "<nombre>NombreGET" + System.nanoTime() + "</nombre>"
				+ "<tipo>Pasta</tipo>"
				+ "<instrucciones>Instrucciones</instrucciones>"
				+ "<test>yes</test>"
				+ "</request>";
		req.addParameter("xml", xml);
		
		servlet.doGet(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("hecho")
				&& respuesta.contains("yes"));
	}
	
	/**
	 * Testea la creacion de recetas (POST) 
	 * 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_post_crear_receta() throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.CREAR_REC_CODE +"\">" 
				+ "<nombre>NombrePOST" + System.nanoTime() + "</nombre>"
				+ "<tipo>Pasta</tipo>"
				+ "<instrucciones>Instrucciones</instrucciones>"
				+ "<ingrediente cantidad=\"1\" uds=\"uds\">ingrediente</ingrediente>"
				+ "<test>yes</test>"
				+ "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<","<");
		
		req.setContent(xml.getBytes());
		
		servlet.doPost(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("hecho")
				&& respuesta.contains("yes"));
	}
	
	/**
	 * Testea la valoracion de recetas (GET)
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_get_valorar_receta() throws ServletException, IOException{
		String mail = "ListenerTest" + System.nanoTime();
		if(!DbMethods.registrar_usuario(mail, mail, "pw", "NULL", true)){
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
		if(!DbMethods.crear_receta(nombre, tipo, instrucciones, ings, true)){
			assertTrue("No se ha creado la receta", false);
		}
		
		List<Receta> recetas = DbMethods.get_recetas(nombre, null, null, true);
		if(recetas == null || recetas.isEmpty()){
			assertTrue("receta no recuperada", false);
		}
		int id = recetas.get(0).getId();
		
		String xml = "<request id=\"" + data.Data.VOTAR_CODE +"\">"
				+ "<id>" + id + "</id>"
				+ "<mail>" + mail + "</mail>"
				+ "<voto>" + 1 + "</voto>"
				+ "<test>yes</test>"
				+ "</request>";
		req.addParameter("xml", xml);
		servlet.doGet(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("hecho")
				&& respuesta.contains("yes"));
	}
	
	/**
	 * Testea la valoracion de recetas (POST) 
	 * 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_post_valorar_receta() throws ServletException, IOException{
		String mail = "ListenerTest" + System.nanoTime();
		if(!DbMethods.registrar_usuario(mail, mail, "pw", "NULL", true)){
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
		if(!DbMethods.crear_receta(nombre, tipo, instrucciones, ings, true)){
			assertTrue("No se ha creado la receta", false);
		}
		
		List<Receta> recetas = DbMethods.get_recetas(nombre, null, null, true);
		if(recetas == null || recetas.isEmpty()){
			assertTrue("receta no recuperada", false);
		}
		int id = recetas.get(0).getId();
		
		String xml = "<request id=\"" + data.Data.VOTAR_CODE +"\">"
				+ "<id>" + id + "</id>"
				+ "<mail>" + mail + "</mail>"
				+ "<voto>" + 1 + "</voto>"
				+ "<test>yes</test>"
				+ "</request>";
		req.setContent(xml.getBytes());
		servlet.doPost(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("hecho")
				&& respuesta.contains("yes"));
	}
	
	/**
	 * Testea la valoracion media de recetas (GET) 
	 * 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_get_valoracion_media() throws ServletException, IOException{
		/* crear receta */
		List<Ingrediente> ings = new LinkedList<Ingrediente>();
		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());
		ings.add(ing);
		String nombreReceta = "nombreDB" + System.nanoTime();
		if(!DbMethods.crear_receta(nombreReceta, "Pasta", "instrucciones", ings, true)){
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_recetas(nombreReceta, null, null, true);
		if(recetas == null || recetas.isEmpty()){
			assertTrue("receta no encontrada", false);
		}
		int idReceta = recetas.get(0).getId();
		
		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();
		
		assertTrue(DbMethods.votar(idReceta, mailUsuario, 1, true));
		
		/* crear usuario 2 */
		nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		user = DbMethods.get_usuario(nombre, true);
		mailUsuario = user.getMail();
		
		assertTrue(DbMethods.votar(idReceta, mailUsuario, 1, true));
		
		String xml = "<request id=\"" + Data.VALORACION_CODE + "\">"
				+ "<id>" + idReceta + "</id>"
				+ "<test>yes</test>"
				+ "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<","<");
		
		req.addParameter("xml", xml);
		
		servlet.doGet(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("valoracion")
				&& respuesta.contains("1"));
	}
	
	/**
	 * Testea la valoracion media de recetas (POST) 
	 * 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_post_valoracion_media() throws ServletException, IOException{
		/* crear receta */
		List<Ingrediente> ings = new LinkedList<Ingrediente>();
		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());
		ings.add(ing);
		String nombreReceta = "nombreDB" + System.nanoTime();
		if(!DbMethods.crear_receta(nombreReceta, "Pasta", "instrucciones", ings, true)){
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_recetas(nombreReceta, null, null, true);
		if(recetas == null || recetas.isEmpty()){
			assertTrue("receta no encontrada", false);
		}
		int idReceta = recetas.get(0).getId();
		
		/* crear usuario */
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		String mailUsuario = user.getMail();
		
		assertTrue(DbMethods.votar(idReceta, mailUsuario, 1, true));
		
		/* crear usuario 2 */
		nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL", true);
		user = DbMethods.get_usuario(nombre, true);
		mailUsuario = user.getMail();
		
		assertTrue(DbMethods.votar(idReceta, mailUsuario, 1, true));
		
		String xml = "<request id=\"" + Data.VALORACION_CODE + "\">"
				+ "<id>" + idReceta + "</id>"
				+ "<test>yes</test>"
				+ "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<","<");
		
		req.setContent(xml.getBytes());
		
		servlet.doPost(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.contains("valoracion")
				&& respuesta.contains("1"));
	}
	
	/**
	 * @param mail email del usuario nuevo
	 * @return respuesta del servidor
	 * @throws ServletException
	 * @throws IOException
	 */
	private String get_crear_user(String mail) throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.CREAR_USER_CODE +"\">" 
				+ "<mail>" + mail + "</mail>"
				+ "<nick>nick_prueba</nick>"
				+ "<pw>pw_prueba</pw>"
				+ "<test>yes</test>"
				+ "</request>";
		req.addParameter("xml", xml);
		
		servlet.doGet(req, resp);
		
		String respuesta = resp.getContentAsString();
		return respuesta;
	}
	
	/**
	 * @param mail email del nuevo usuario
	 * @return respuesta del servidor
	 * @throws ServletException
	 * @throws IOException
	 */
	private String post_crear_user(String mail) throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.CREAR_USER_CODE +"\">"
				+ "<mail>" + mail + "</mail>"
				+ "<nick>nick_prueba</nick>"
				+ "<pw>pw_prueba</pw>"
				+ "<test>yes</test>"
				+ "</request>";
		xml = xml.trim().replaceFirst("^([\\W]+)<","<");
		
		req.setContent(xml.getBytes());
		
		servlet.doPost(req, resp);
		
		String respuesta = resp.getContentAsString();
		
		return respuesta;
	}
}
