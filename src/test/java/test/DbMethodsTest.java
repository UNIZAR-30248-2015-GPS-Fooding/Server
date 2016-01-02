/*
 * DbMethodsTest.java v1.1 30/10/2015
 */

package test;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import data.Ingrediente;
import data.Receta;
import data.Usuario;
import db.DbMethods;

public class DbMethodsTest {
	/**
	 * Clase para tests sobre los metodos de acceso
	 * 
	 * @version 1.1 - Test de lista de ingredientes - Test de lista de recetas
	 *          (generico) - Test de lista de recetas (nombre)
	 * @date 23/10/2015
	 */

	/**
	 * Test para comprobar que el metodo para obtener la lista de ingredientes
	 * no devuelve <null> o lista vacia
	 */
	@Test
	public void test_lista_ingredientes() {
		List<String> ings = DbMethods.get_lista_ingredientes(true);
		assertTrue(ings != null && ings.size() > 0);
	}

	/**
	 * Test para comprobar que el metodo para obtener la lista de tipos no
	 * devuelve <null> o lista vacia
	 */
	@Test
	public void test_lista_tipos() {
		List<String> ings = DbMethods.get_tipos(true);
		assertTrue(ings != null && ings.size() > 0);
	}

	/**
	 * Test para comprobar que el metodo para obtener recetas (de forma
	 * generica, sin filtrados) no devuelve <null> o lista vacia
	 */
	@Test
	public void test_recetas() {
		List<Receta> recetas = DbMethods.get_recetas(null, null, null, true);
		assertTrue(recetas != null && recetas.size() > 0);
	}

	/**
	 * Test para comprobar que el metodo para obtener recetas por nombre no
	 * devuelve <null> o lista vacia
	 */
	@Test
	public void test_recetas_nombre() {
		List<Receta> recetas = DbMethods.get_recetas("mac", null, null, true);
		assertTrue(recetas != null && recetas.size() > 0);
	}

	/**
	 * Test para comprobar que el metodo para obtener recetas por tipo no
	 * devuelve <null> o lista vacia
	 */
	@Test
	public void test_recetas_tipo() {
		List<Receta> recetas = DbMethods.get_recetas(null, "Pasta", null, true);
		assertTrue(recetas != null && recetas.size() > 0);
	}

	/**
	 * Test para comprobar que el metodo para registrar al usuario funciona
	 */
	@Test
	public void test_registrar_usuario() {
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		boolean registrado = DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL",
				true);
		assertTrue(registrado);
	}

	/**
	 * Test para comprobar que el metodo para loguear al usuario funciona
	 */
	@Test
	public void test_login_usuario() {
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", 
				"pw_prueba", "NULL", true);
		assertTrue(DbMethods.login_usuario(nombre, "pw_prueba", true));
	}

	/**
	 * Test para comprobar que el metodo para recuperar usuario de la bd
	 * funciona
	 */
	@Test
	public void test_get_usuario() {
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", 
				"pw_prueba", "NULL", true);
		Usuario user = DbMethods.get_usuario(nombre, true);
		assertTrue(nombre, user != null);
	}
	
	/**
	 * Test para comprobar que el metodo para obtener la lista de
	 * usuarios no devuelve <null> o lista vacia
	 */
	@Test
	public void test_lista_usuarios() {
		List<Usuario> usuarios = DbMethods.get_lista_usuarios(true);
		assertTrue(usuarios != null && usuarios.size() > 0);
	}

	/**
	 * Test para comprobar que el metodo para crear una receta en la BD
	 * funciona (sin ingredientes)
	 */
	@Test
	public void test_crear_receta_sin_ingredientes() {
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", 
				"pw_prueba", "NULL", true);
		assertTrue(DbMethods.crear_receta(nombre, "nombreDB" + System.nanoTime(), "Pasta", "instrucciones", null, true) == true);
	}
	
	/**
	 * Test para comprobar que el metodo para crear una receta en la BD
	 * funciona
	 */
	@Test
	public void test_crear_receta() {
		String nombre = "mail_pruebaDbMethods" + System.nanoTime();
		DbMethods.registrar_usuario(nombre, "nick_prueba", 
				"pw_prueba", "NULL", true);
		List<Ingrediente> ings = new LinkedList<Ingrediente>();
		
		Ingrediente ing = new Ingrediente();
		ing.setCantidad(3);
		ing.setUds("uds");
		ing.setNombre("Ingrediente" + System.nanoTime());
		
		ings.add(ing);
		
		assertTrue(DbMethods.crear_receta(nombre, "nombreDB" + System.nanoTime(), "Pasta", "instrucciones", null, true) == true);
	}
	
	/**
	 * Test para comprobar que el metodo para valorar una receta en la BD
	 * funciona
	 */
	@Test
	public void test_valorar_receta() {
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
		if(!DbMethods.crear_receta(nombre, nombreReceta, "Pasta", "instrucciones", ings, true)){
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_recetas(nombreReceta, null, null, true);
		if(recetas == null || recetas.isEmpty()){
			assertTrue("receta no encontrada", false);
		}
		int idReceta = recetas.get(0).getId();
		
		
		assertTrue(DbMethods.votar(idReceta, mailUsuario, 1, true));
		
	}
	
	/**
	 * Test para comprobar que el metodo para obtener valoracion media una 
	 * receta en la BD funciona
	 */
	@Test
	public void test_valoracion_media_receta() {
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
		if(!DbMethods.crear_receta(nombre, nombreReceta, "Pasta", "instrucciones", ings, true)){
			assertTrue("receta no creada", false);
		}
		List<Receta> recetas = DbMethods.get_recetas(nombreReceta, null, null, true);
		if(recetas == null || recetas.isEmpty()){
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
		
		assertTrue(DbMethods.valoracion_media(idReceta, true) == 1);
		
	}
}
