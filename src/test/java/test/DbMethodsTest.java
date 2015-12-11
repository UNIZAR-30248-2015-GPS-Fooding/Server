/*
 * DbMethodsTest.java v1.1 30/10/2015
 */

package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

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

	private String nombre = "mail_pruebaDbMethods" + System.nanoTime();;
	
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
		System.out.println(nombre.length());
		boolean registrado = DbMethods.registrar_usuario(nombre, "nick_prueba", "pw_prueba", "NULL",
				true);
		assertTrue(registrado);
	}

	/**
	 * Test para comprobar que el metodo para loguear al usuario funciona
	 */
	@Test
	public void test_login_usuario() {
		assertTrue(DbMethods.login_usuario(nombre, "pw_prueba", true));
	}

	/**
	 * Test para comprobar que el metodo para recuperar usuario de la bd
	 * funciona
	 */
	@Test
	public void test_get_usuario() {
		Usuario user = DbMethods.get_usuario(nombre, true);
		assertTrue(nombre, user != null && user.getMail().equalsIgnoreCase(nombre));
	}

	/**
	 * Test para comprobar que el metodo para crear una receta en la BD
	 * funciona
	 */
	@Test
	public void test_crear_receta() {
		assertTrue(DbMethods.crear_receta("nombreDB" + System.nanoTime(), "Pasta", "instrucciones", null, true) == true);
	}
}
