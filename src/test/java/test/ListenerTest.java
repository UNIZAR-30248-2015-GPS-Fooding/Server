/*
 * DbMethodsTest.java v1.1 30/10/2015
 */

package test;

import static org.junit.Assert.*;

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
		System.out.println(respuesta);
		
		assertTrue(respuesta != null && !respuesta.isEmpty()
				&& respuesta.equalsIgnoreCase("<response></response>"));
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
		
		assertTrue(respuesta != null && !respuesta.isEmpty());
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
		
		assertTrue(respuesta != null && !respuesta.isEmpty());
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
		
		assertTrue(respuesta != null && !respuesta.isEmpty());
	}
	
	/**
	 * Testea que devuelve una lista de tipos (GET)
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
		
		assertTrue(respuesta != null && !respuesta.isEmpty());
	}
}
