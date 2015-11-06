/*
 * DataPackageTest.java v1.0 06/11/2015
 */

package test;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import data.Ingrediente;
import data.Receta;

public class DataPackageTest {
	/**
	 * Clase para tests sobre las estructuras de datos utilizadas
	 * 
	 * @version 1.0
	 * @date 06/11/2015
	 */

	/**
	 * Test para comprobar que las recetas funcionan correctamente
	 */
	@Test
	public void test_data_Receta() {
		/* crear la receta */
		Receta test = new Receta();

		test.setNombre("nombre");
		test.setInstrucciones("instrucciones");
		test.setTipo("tipo");

		test.setMe_gusta(1);
		test.setNo_me_gusta(-1);

		List<Ingrediente> ings = new LinkedList<Ingrediente>();

		Ingrediente ing = new Ingrediente();
		ing.setNombre("ingrediente");
		ing.setCantidad(1);
		ing.setUds("uds");

		ings.add(ing);
		test.setIngredientes(ings);

		/* test */
		assertTrue("Los nombres no coinciden", test.getNombre().equals("nombre"));
		assertTrue("Las instrucciones no coinciden", test.getInstrucciones().equals("instrucciones"));
		assertTrue("El tipo no coincide", test.getTipo().equals("tipo"));
		assertTrue("Me_gusta no coinciden", test.getMe_gusta() == 1);
		assertTrue("No_me_gusta no coincide", test.getNo_me_gusta() == -1);
		assertTrue("Ingredientes no coinciden",
				(test.getIngredientes() != null && test.getIngredientes().size() == 1
						&& test.getIngredientes().get(0).getNombre().equals("ingrediente")
						&& test.getIngredientes().get(0).getCantidad() == 1
						&& test.getIngredientes().get(0).getUds().equals("uds")));
	}

	/**
	 * Test para comprobar que los ingredientes funcionan correctamente
	 */
	@Test
	public void test_data_Ingrediente() {
		/* crear el ingrediente */
		Ingrediente ing = new Ingrediente();
		ing.setNombre("ingrediente");
		ing.setCantidad(1);
		ing.setUds("uds");

		assertTrue("Ingredientes no coinciden",
				(ing.getNombre().equals("ingrediente") && ing.getCantidad() == 1 && ing.getUds().equals("uds")));
	}
}
