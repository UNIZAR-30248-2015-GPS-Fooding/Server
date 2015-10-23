/*
 * DbMethods.java v1.0 23/10/2015
 */

package db;

import java.util.LinkedList;
import java.util.List;

public class DbMethods {
	/**
	 * Clase para obtener informacion de la base de datos de MySQL
	 * 
	 * @version 1.0
	 * @date 23/10/2015
	 */

	/**
	 * @return una lista con los ingredientes de la BD
	 * 
	 * @version 1.0
	 * 	-- Metodo vacio
	 */
	public static List<String> get_lista_ingredientes() {
		// abrir conexion
		DbConnection.initConnection();
		
		List<String> ings = new LinkedList<String>();
		
		// obtener lista de ingredientes
		
		// cerrar conexion
		DbConnection.closeConnection();
		
		return ings;
	}
}
