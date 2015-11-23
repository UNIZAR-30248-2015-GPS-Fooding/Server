/*
 * DbMethodsTest.java v1.1 30/10/2015
 */

package test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

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
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_get_crear_user() throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.CREAR_USER_CODE +"\">" 
				+ "<mail>pruebaListenerTest0</mail>"
				+ "<nick>nick_prueba</nick>"
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
	 * Testea la creacion de usuarios (POST)
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_post_crear_user() throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.CREAR_USER_CODE +"\">"
				+ "<mail>pruebaListenerTest1</mail>"
				+ "<nick>nick_prueba</nick>"
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
	 * Testea el logueo de usuarios (GET)
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	public void test_get_login_user() throws ServletException, IOException{
		String xml = "<request id=\"" + data.Data.LOGIN_CODE +"\">" 
				+ "<mail>pruebaListenerTest0</mail>"
				+ "<pw>prueba</pw>"
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
		String xml = "<request id=\"" + data.Data.LOGIN_CODE +"\">"
				+ "<mail>pruebaListenerTest1</mail>"
				+ "<pw>prueba</pw>"
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
}
