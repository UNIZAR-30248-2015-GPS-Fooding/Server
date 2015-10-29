/*
 * DbConnection.java v1.1 19/10/2015
 */

package db;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DbConnection {
	/**
	 * Clase para establecer una conexion con la base de datos de MySQL asociada
	 * 
	 * @version 1.1
	 * 	- Abrir conexion con la BD
	 * 	- Agregado cerrar conexion con la BD
	 * @date 19/10/2015
	 */

	/* objeto que almacena la conexion */
	private static Connection conexion = null;

	/**
	 * Establece una conexion con la base de datos MySQL asociada a la
	 * aplicacion
	 * 
	 * @return <true> si la conexion se ha establecido satisfactoriamente o
	 *         <false> en caso contrario
	 *         
	 * @version 1.0
	 */
	public static boolean initConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Variables de entorno para crear la url de acceso
			String name = System.getenv("OPENSHIFT_APP_NAME");
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
			String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");

			// url de acceso a la bd
			String url = "jdbc:mysql://" + host + ":" + port + "/" + name;

			// usuario y password de la bd
			String user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
			String password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");

			// crear la conexion con la bd
			conexion = (Connection) DriverManager.getConnection(url, user, password);

			return true;

		} catch (SQLException e) {
			// Excepcion SQL
			System.err.println("Error al conectar con la base de datos");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// Excepcion del driver
			System.err.println("Driver de MySQL no encontrado");
			e.printStackTrace();
		}

		return false;
	}
	
	/**
	 * Devuelve una conexion con la base de datos de MySQL
	 * 
	 * @version 1.0
	 */
	public static Connection getConnection() {
		return conexion;
	}

	/**
	 * Cierra la conexion con la base de datos de MySQL
	 * 
	 * @version 1.1
	 */
	public static void closeConnection() {
		if (conexion != null) {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
