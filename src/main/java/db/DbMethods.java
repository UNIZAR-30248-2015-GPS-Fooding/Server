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

import data.Ingrediente;
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
			st.close();
			
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
	 * 
	 * @version 1.1 -- Devuelve toda la informacion relativa a una receta.
	 * 				Filtros disponibles:
	 * 					-Nombre (parcial o completo)
	 */
	public static List<Receta> get_recetas(String nombre, String tipo, List<String> ings) {
		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();
		
		// obtener lista de recetas
		List<Receta> recetas = new LinkedList<Receta>();
		String query = "SELECT * FROM receta, receta-ingrediente, usuario-valora-receta"
						+ " WHERE receta.id = receta-ingrediente.idReceta"
						+ " AND receta.id = usuario-valora-receta.id";
		
		// aplica los distintos filtros a la consulta
		if (nombre != null) {
			query = query + " AND receta.nombre LIKE '%" + nombre + "%'";
		}
		
		Statement st,st2;
		
		try {
			st = conexion.createStatement();
			ResultSet res = st.executeQuery(query);
			Receta rec;
			
			// Obtiene toda la informacion de cada receta
			while(res.next()) {
				rec = new Receta();
				int id = res.getInt("id");
				rec.setNombre(res.getString("nombre"));
				rec.setTipo(res.getString("tipo"));
				rec.setInstrucciones(res.getString("instrucciones"));
				
				// Obtiene la informacion de los ingredientes de cada receta
				List<Ingrediente> ingredientes = new LinkedList<Ingrediente>();
				String query2 = "SELECT * FROM receta-ingrediente"
								+ " WHERE idReceta = " + id;
				st2 = conexion.createStatement();
				ResultSet res2 = st2.executeQuery(query2);
				Ingrediente ing;
				
				while(res2.next()) {
					ing = new Ingrediente();
					ing.setNombre(res.getString("nombreIngrediente"));
					ing.setCantidad(res.getInt("cantidad"));
					ing.setUds(res.getString("medida"));
					ingredientes.add(ing);
				}
				recetas.add(rec);
				st2.close();
			}
			
			st.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// cerrar conexion
		DbConnection.closeConnection();
		
		return recetas;
	}
}
