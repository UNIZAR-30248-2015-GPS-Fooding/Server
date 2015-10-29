/*
 * DbMethods.java v1.0 23/10/2015
 */

package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.Connection;

import data.Receta;

public class DbMethods {
	/**
	 * Clase para obtener informacion de la base de datos de MySQL
	 * 
	 * @version 1.1
	 * 	- Obtener lista de ingredientes
	 * 	- Obtener lista de recetas
	 * @date 23/10/2015
	 */

	/**
	 * @return una lista con los ingredientes de la BD
	 * 
	 * @version 1.1 -- Devuelve una lista con todos los ingredientes
	 */
	public static List<String> get_lista_ingredientes() {
		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();

		// obtener lista de ingredientes
		List<String> ings = new LinkedList<String>();
		String query = "SELECT * FROM INGREDIENTE";
		Statement st;
		
		try {
			st = conexion.createStatement();
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				String nombre = res.getString("nombre");
				ings.add(nombre);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// cerrar conexion
		DbConnection.closeConnection();

		return ings;
	}

	/**
	 * 
	 * @param nombre
	 *            : nombre de la receta o <null>
	 * @param tipo
	 *            : tipo de la receta o <null>
	 * @param ings
	 *            : ingredientes de la receta (vacio si no hay ingredientes)
	 * @return una lista con las recetas que coinciden con la query en la BD
	 */
	public static List<Receta> get_recetas(String nombre, String tipo, List<String> ings) {
		// abrir conexion
		DbConnection.initConnection();
		
		List<Receta> recetas = new LinkedList<Receta>();
		
		// obtener lista de recetas
		
		// cerrar conexion
		DbConnection.closeConnection();
		
		return recetas;
	}
}
