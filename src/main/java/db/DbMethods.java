/*
 * DbMethods.java v1.0 23/10/2015
 */

package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.Connection;

import data.Ingrediente;
import data.Receta;
import data.Usuario;

public class DbMethods {
	/**
	 * Clase para obtener informacion de la base de datos de MySQL
	 * 
	 * @version 1.1 - Obtener lista de ingredientes - Obtener lista de recetas
	 * @date 23/10/2015
	 */

	/**
	 * @return una lista con los ingredientes de la BD
	 * 
	 * @version 1.1 -- Devuelve una lista con todos los ingredientes
	 */
	public static List<String> get_lista_ingredientes(boolean test) {
		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();

		// obtener lista de ingredientes
		List<String> ings = new LinkedList<String>();
		String query = "SELECT * FROM Ingrediente";
		if (test)
			query = query + "Test";
		Statement st;

		try {
			st = conexion.createStatement();
			ResultSet res = st.executeQuery(query);
			while (res.next()) {
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
	 * @return una lista con los tipos disponibles en la bd
	 * 
	 * @version 1.1 -- Devuelve una lista de todos los tipos de recetas
	 */
	public static List<String> get_tipos(boolean test) {
		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();

		// obtener lista de ingredientes
		List<String> tipos = new LinkedList<String>();
		String query = "SELECT DISTINCT tipo FROM Receta";
		if (test)
			query = query + "Test";
		Statement st;

		try {
			st = conexion.createStatement();
			ResultSet res = st.executeQuery(query);
			while (res.next()) {
				String tipo = res.getString("tipo");
				tipos.add(tipo);
			}
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// cerrar conexion
		DbConnection.closeConnection();

		return tipos;
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
	 * @version 1.3 -- Devuelve la lista de recetas que satisfaga los filtros.
	 *          Filtros disponibles: -Nombre (parcial o completo) -Tipo
	 *          (completo)
	 */
	public static List<Receta> get_lista_recetas(String nombre, String tipo, List<String> ings, boolean test) {
		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();

		// obtener lista de recetas
		List<Receta> recetas = new LinkedList<Receta>();
		String query = "SELECT DISTINCT id, nombre, tipo" 
				+ " FROM Receta, RecetaIngrediente"
				+ " WHERE Receta.id = RecetaIngrediente.idReceta";
		if (test) {
			query = "SELECT DISTINCT id, nombre, tipo" 
					+ " FROM RecetaTest, RecetaIngredienteTest"
					+ " WHERE RecetaTest.id = RecetaIngredienteTest.idReceta";
		}

		// aplica los distintos filtros a la consulta
		if (nombre != null) {

			// filtro de busqueda por nombre (parcial o completo)
			if (!test) {
				query = query + " AND Receta.nombre LIKE '%" + nombre + "%'";
			} else {
				query = query + " AND RecetaTest.nombre LIKE '%" + nombre + "%'";
			}

		}
		if (tipo != null) {

			// filtro de busqueda por tipo (completo)
			if (!test) {
				query = query + " AND Receta.tipo = '" + tipo + "'";
			} else {
				query = query + " AND RecetaTest.tipo = '" + tipo + "'";
			}
		}
		if (ings != null && ings.size() > 0) {

			// filtro de busqueda por ingredientes (uno o varios)
			if (!test) {
				query = query + " AND RecetaIngrediente.nombreIngrediente in ('" + ings.get(0) + "'";
			} else {
				query = query + " AND RecetaIngredienteTest.nombreIngrediente in ('" + ings.get(0) + "'";
			}

			for (int i = 1; i < ings.size(); i++) {
				query = query + ",'" + ings.get(i) + "'";
			}
			if (!test) {
				query = query + ")" + " GROUP BY Receta.id"
						+ " HAVING COUNT(DISTINCT RecetaIngrediente.nombreIngrediente) >= " + ings.size();
			} else {
				query = query + ")" + " GROUP BY RecetaTest.id"
						+ " HAVING COUNT(DISTINCT RecetaIngredienteTest.nombreIngrediente) >= " + ings.size();
			}
		}

		Statement st;

		try {
			st = conexion.createStatement();
			ResultSet res = st.executeQuery(query);
			Receta rec;

			// Obtiene toda la informacion de cada receta
			while (res.next()) {

				rec = new Receta();
				int id = res.getInt("id");
				rec.setId(id);
				rec.setNombre(res.getString("nombre"));
				rec.setTipo(res.getString("tipo"));
				rec.setMe_gusta(getMeGusta(id,test));
				rec.setNo_me_gusta(getNoMeGusta(id,test));
				recetas.add(rec);
			}
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// cerrar conexion
		DbConnection.closeConnection();

		return recetas;
	}
	
	public static Receta get_receta(int id, boolean test) {
		
		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();

		// obtener lista de recetas
		Receta rec = null;
		String query = "SELECT DISTINCT id, nombre, tipo, instrucciones, "
						+ "Usuario.nick, Usuario.mail" 
				+ " FROM Receta, RecetaIngrediente, UsuarioPoseeReceta, Usuario"
				+ " WHERE Receta.id = '" + id + "'"
				+ " AND Receta.id = RecetaIngrediente.idReceta"
				+ " AND Receta.id = UsuarioPoseeReceta.idReceta"
				+ " AND UsuarioPoseeReceta.mail = Usuario.mail";
		if (test) {
			query = "SELECT DISTINCT id, nombre, tipo, instrucciones, "
					+ "UsuarioTest.nick, UsuarioTest.mail" 
			+ " FROM RecetaTest, RecetaIngredienteTest, UsuarioPoseeRecetaTest, UsuarioTest"
			+ " WHERE RecetaTest.id = '" + id + "'"
			+ " AND RecetaTest.id = RecetaIngredienteTest.idReceta"
			+ " AND RecetaTest.id = UsuarioPoseeRecetaTest.idReceta"
			+ " AND UsuarioPoseeRecetaTest.mail = UsuarioTest.mail";
		}

		Statement st, st2;

		try {
			st = conexion.createStatement();
			ResultSet res = st.executeQuery(query);
			Usuario autor;

			// Obtiene toda la informacion de la receta
			if (res.next()) {

				rec = new Receta();
				autor = new Usuario();
				rec.setId(res.getInt("id"));
				rec.setNombre(res.getString("nombre"));
				rec.setTipo(res.getString("tipo"));
				rec.setInstrucciones(res.getString("instrucciones"));
				rec.setMe_gusta(getMeGusta(rec.getId(),test));
				rec.setNo_me_gusta(getNoMeGusta(rec.getId(),test));
				autor.setNick(res.getString("nick"));
				autor.setMail(res.getString("mail"));
				rec.setAutor(autor);

				// Obtiene la informacion de los ingredientes de cada receta
				List<Ingrediente> ingredientes = new LinkedList<Ingrediente>();
				String query2 = "SELECT * FROM RecetaIngrediente" + " WHERE idReceta = " + id;
				if (test) {
					query2 = "SELECT * FROM RecetaIngredienteTest" + " WHERE idReceta = " + id;
				}
				st2 = conexion.createStatement();
				ResultSet res2 = st2.executeQuery(query2);
				Ingrediente ing;

				while (res2.next()) {
					ing = new Ingrediente();
					ing.setNombre(res2.getString("nombreIngrediente"));
					ing.setCantidad(res2.getInt("cantidad"));
					ing.setUds(res2.getString("medida"));
					ingredientes.add(ing);
				}
				rec.setIngredientes(ingredientes);
				st2.close();
			}

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// cerrar conexion
		DbConnection.closeConnection();
		
		return rec;
	}

	/**
	 * @param mail
	 *            : email con el que el usuario inicia sesion
	 * @param pw
	 *            : password cifrada del usuario
	 * @return <true> si el usuario ha podido iniciar sesion, o <false> en caso
	 *         contrario
	 */
	public static boolean login_usuario(String mail, String pw, boolean test) {

		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();

		String tabla = "";
		if (test) {
			tabla = "UsuarioTest";
		} else {
			tabla = "Usuario";
		}

		// obtener informacion del usuario
		String query = "SELECT * FROM " + tabla + " WHERE mail = '" + mail + "'" + " AND pass = '" + pw + "'";
		Statement st;

		boolean encontrado = false;

		try {
			st = conexion.createStatement();
			ResultSet res = st.executeQuery(query);

			// comprueba si se ha encontrado al usuario
			encontrado = res.next();

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// cerrar conexion
		DbConnection.closeConnection();

		return encontrado;
	}

	/**
	 * Registra al usuario
	 * 
	 * @param mail
	 *            : email con el que el usuario se registra
	 * @param nick
	 *            : nickname del usuario
	 * @param pw
	 *            : password cifrada del usuario
	 * @return <true> si se ha podido registrar al usuario, o <false> en caso
	 *         contrario
	 */
	public static boolean registrar_usuario(String mail, String nick, String pw, String uniqueKey, boolean test) {

		boolean registrado = false;
		String tabla = null;

		if (test) {
			tabla = "UsuarioTest";
		} else {
			tabla = "Usuario";
		}

		java.sql.Date sqlDate = null;
		try {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
			int day = Calendar.getInstance().get(Calendar.DATE);

			String fecha = year + "-" + month + "-" + day;

			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date myDate = formatter.parse(fecha);
			sqlDate = new java.sql.Date(myDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Registra al usuario si no se ha encontrado su mail en la bd
		if (!buscar_usuario(mail, test)) {

			// abrir conexion
			DbConnection.initConnection();
			Connection conexion = DbConnection.getConnection();

			// inserta en la bd la info del nuevo usuario
			String query = "INSERT INTO " + tabla + " (mail,nick,pass,verificado,score,fecha,uniqueKey)"
					+ " VALUES (?,?,?,0,0,?,?)";
			try {
				PreparedStatement preparedStatement = conexion.prepareStatement(query);

				preparedStatement.setString(1, mail);
				preparedStatement.setString(2, nick);
				preparedStatement.setString(3, pw);
				preparedStatement.setDate(4, sqlDate);
				preparedStatement.setString(5, uniqueKey);

				preparedStatement.execute();
				registrado = true;
			} catch (SQLException ex) {
				registrado = false;
				ex.printStackTrace();
			}

			// cerrar conexion
			DbConnection.closeConnection();
		}

		return registrado;
	}

	/**
	 * @param mail:
	 *            mail del usuario a borrar
	 * @param test:
	 *            <true> si es prueba/test
	 * @return <true> si se ha borrado, <false> en caso contrario
	 */
	public static boolean borrar_usuario(String mail, boolean test) {
		boolean deleted = false;
		String t = "";

		if (test) {
			t = "UsuarioTest";
		} else {
			t = "Usuario";
		}

		// abrir conexion
		DbConnection.initConnection();
		Connection conn = DbConnection.getConnection();

		// query
		String query = "DELETE FROM " + t + " WHERE mail =?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, mail);

			preparedStatement.execute();
			deleted = true;
		} catch (SQLException e) {
			deleted = false;
			e.printStackTrace();
		}
		DbConnection.closeConnection();

		return deleted;
	}
	
	/**
	 * @param test:
	 *            <true> si es prueba/test
	 * @return lista con los nombres de todos los usuarios de la
	 * base de datos
	 */
	public static List<Usuario> get_lista_usuarios(String nombre, boolean test) {
		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();

		// obtener informacion del usuario
		List<Usuario> usuarios = new LinkedList<Usuario>();
		Usuario usuario = null;
		String query = "SELECT mail, nick, score FROM Usuario";
		if (test) {
			query = "SELECT mail, nick, score FROM UsuarioTest";
		}
		
		// aplica el filtro de nombre de usuario (parcial o completo)
		if (nombre != null) {
			query = query + " WHERE nick LIKE '%" + nombre + "%'";
		}
		
		Statement st, st2;

		try {
			st = conexion.createStatement();
			ResultSet res = st.executeQuery(query);

			while (res.next()) {
				usuario = new Usuario();
				String mail = res.getString("mail");
				usuario.setNick(res.getString("nick"));
				usuario.setScore(res.getInt("score"));
				
				/* Obtiene las recetas de cada usuario */
				String query2 = "SELECT idReceta, nombre" +
						" FROM Receta, UsuarioPoseeReceta" +
						" WHERE Receta.id = UsuarioPoseeReceta.idReceta" +
						" AND UsuarioPoseeReceta.mail = '" + mail + "'";
				if (test) {
					query2 = "SELECT idReceta, nombre" +
							" FROM Receta, UsuarioPoseeRecetaTest" +
							" WHERE Receta.id = UsuarioPoseeRecetaTest.idReceta" +
							" AND UsuarioPoseeRecetaTest.mail = '" + mail + "'";
				}
				
				st2 = conexion.createStatement();
				ResultSet res2 = st2.executeQuery(query2);
				List<Receta> recetas = new LinkedList<Receta>();
				
				while (res2.next()) {
					Receta r = new Receta();
					int id = res2.getInt("idReceta");
					String nombreReceta = res2.getString("nombre");
					r.setId(id);
					r.setNombre(nombreReceta);
					recetas.add(r);
				}
				
				usuario.setRecetas(recetas);
				usuarios.add(usuario);
			}
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// cerrar conexion
		DbConnection.closeConnection();

		return usuarios;
	}

	/**
	 * @param mail
	 *            : email con el que el usuario se registra
	 * @return Usuario != null si se ha encontrado al usuario, o <null> en caso
	 *         contrario
	 */
	public static Usuario get_usuario(String mail, boolean test) {
		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();

		// obtener informacion del usuario
		Usuario usuario = null;
		String query = "SELECT * FROM Usuario WHERE mail='" + mail + "'";
		if (test) {
			query = "SELECT * FROM UsuarioTest WHERE mail='" + mail + "'";
		}
		Statement st, st2;

		try {
			st = conexion.createStatement();
			ResultSet res = st.executeQuery(query);

			if (res.next()) {
				usuario = new Usuario();
				usuario.setMail(res.getString("mail"));
				usuario.setNick(res.getString("nick"));
				usuario.setScore(res.getInt("score"));
				
				/* Obtiene las recetas del usuario */
				String query2 = "SELECT idReceta, nombre" +
						" FROM Receta, UsuarioPoseeReceta" +
						" WHERE Receta.id = UsuarioPoseeReceta.idReceta" +
						" AND UsuarioPoseeReceta.mail = '" + mail + "'";
				if (test) {
					query2 = "SELECT idReceta, nombre" +
							" FROM Receta, UsuarioPoseeRecetaTest" +
							" WHERE Receta.id = UsuarioPoseeRecetaTest.idReceta" +
							" AND UsuarioPoseeRecetaTest.mail = '" + mail + "'";
				}
				
				st2 = conexion.createStatement();
				ResultSet res2 = st2.executeQuery(query2);
				List<Receta> recetas = new LinkedList<Receta>();
				
				while (res2.next()) {
					Receta r = new Receta();
					r.setId(res2.getInt("idReceta"));
					r.setNombre(res2.getString("nombre"));
					recetas.add(r);
				}
				
				usuario.setRecetas(recetas);
			}
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// cerrar conexion
		DbConnection.closeConnection();

		return usuario;
	}

	/**
	 * @param key
	 *            : unique key del usuario que se registra
	 * @return true si se ha validado al usuario. false si no se ha encontrado.
	 */
	public static boolean search_for_validation(String key, boolean test) {
		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();

		// encontrar usuario
		int mods = 0;
		try {
			String query = "UPDATE Usuario SET verificado=1, uniqueKey=DEFAULT WHERE uniqueKey=?";
			if (test) {
				query = "UPDATE UsuarioTest SET verificado=1, uniqueKey=DEFAULT WHERE uniqueKey=?";
			}
			PreparedStatement st = conexion.prepareStatement(query);
			st.setString(1, key);
			mods = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// cerrar conexion
		DbConnection.closeConnection();

		return (mods > 0);
	}

	/**
	 * @param mail
	 *            : email con el que el usuario se registra
	 * @return <true> si se ha encontrado al usuario, o <false> en caso
	 *         contrario
	 */
	private static boolean buscar_usuario(String mail, boolean test) {
		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();

		// obtener informacion del usuario
		String query = "SELECT * FROM Usuario WHERE mail = '" + mail + "'";
		if (test) {
			query = "SELECT * FROM UsuarioTest WHERE mail = '" + mail + "'";
		}
		Statement st;

		boolean encontrado = false;

		try {
			st = conexion.createStatement();
			ResultSet res = st.executeQuery(query);

			// comprueba si se ha encontrado al usuario
			encontrado = res.next();

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// cerrar conexion
		DbConnection.closeConnection();

		return encontrado;
	}

	/**
	 * @param nombre
	 *            : nombre de la nueva receta
	 * @param tipo
	 *            : tipo de la nueva receta
	 * @param instrucciones
	 *            : instrucciones de la nueva receta
	 * @param ings
	 *            : ingredientes de la nueva receta
	 * @param test
	 *            : <true> si es test, <false> en caso contrario
	 * @return <true> si se ha creado la nueva receta, <false> si no se ha
	 *         podido crear
	 */
	public static boolean crear_receta(String mail, String nombre, String tipo, String instrucciones, List<Ingrediente> ings,
			boolean test) {
		boolean creado = false;
		String tabla = "Receta";
		if (test) {
			tabla = "RecetaTest";
		}

		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();

		try {
			// inserta en la bd la info de la nueva receta
			String query = "INSERT INTO " + tabla + " (nombre, tipo, instrucciones)" + " VALUES (?,?,?)";

			PreparedStatement preparedStatement = conexion.prepareStatement(query);

			preparedStatement.setString(1, nombre);
			preparedStatement.setString(2, tipo);
			preparedStatement.setString(3, instrucciones);

			preparedStatement.executeUpdate();

			query = "SELECT * from " + tabla + " where nombre = ?";
			preparedStatement = conexion.clientPrepareStatement(query);
			preparedStatement.setString(1, nombre);
			ResultSet rs = preparedStatement.executeQuery();

			Receta r = new Receta();
			if (rs.next()) {
				r.setId(rs.getInt("id"));
			}
			int id = r.getId();
			
			tabla = "UsuarioPoseeReceta";
			if (test){
				tabla = tabla + "Test";
			}
			query = "insert into " + tabla + " (mail, idReceta) values (?,?)";
			PreparedStatement upr = conexion.clientPrepareStatement(query);
			upr.setString(1, mail);
			upr.setInt(2, id);
			upr.executeUpdate();
			
			if (ings != null && ings.size() > 0) {
				for (Ingrediente i : ings) {
					tabla = "Ingrediente";
					if (test) {
						tabla = "IngredienteTest";
					}
					query = "SELECT * from " + tabla + " where nombre ='" + i.getNombre() + "'";

					PreparedStatement prepStat= conexion.clientPrepareStatement(query);
					rs = prepStat.executeQuery();

					if (!rs.next()) {
						query = "INSERT INTO " + tabla + " (nombre) VALUES (?)";
						PreparedStatement ps = conexion.clientPrepareStatement(query);
						ps.setString(1, i.getNombre());

						ps.execute();
					}

					tabla = "RecetaIngrediente";
					if (test) {
						tabla = tabla + "Test";
					}

					query = "INSERT INTO " + tabla + " (idReceta, nombreIngrediente, cantidad, medida) "
							+ "VALUES (?,?,?,?)";
					PreparedStatement pst = conexion.clientPrepareStatement(query);

					pst.setInt(1, id);
					pst.setString(2, i.getNombre());
					pst.setInt(3, i.getCantidad());
					pst.setString(4, i.getUds());

					pst.execute();
				}
			}
			creado = true;
		} catch (SQLException ex) {
			creado = false;
			ex.printStackTrace();
		}

		// cerrar conexion
		DbConnection.closeConnection();

		return creado;
	}
	
	/**
	 * @param id identificador de la receta
	 * @param voto voto del usuario
	 * @param test <true> si es test
	 * @return <true> si se ha podido votar
	 */
	public static boolean votar(int id, String mail, int voto, boolean test){
		String tabla = "UsuarioValoraReceta";
		if(test) tabla = tabla + "Test";
		
		// abrir conexion
		DbConnection.initConnection();
		Connection conexion = DbConnection.getConnection();
		
		String query_select = "select * from " + tabla + " where mail=? and idReceta=?";
		try{
			PreparedStatement select = conexion.clientPrepareStatement(query_select);
			select.setString(1, mail);
			select.setInt(2, id);
			
			ResultSet rs = select.executeQuery();
			
			PreparedStatement insert_update;
			if(rs.next()){
				String query = "update " + tabla + " set valoracion=? where mail=? and idReceta=?";
				insert_update = conexion.clientPrepareStatement(query);
				insert_update.setInt(1, voto);
				insert_update.setString(2, mail);
				insert_update.setInt(3, id);
				
			}
			else{
				String query = "insert into " + tabla + " (mail, idReceta, valoracion) values (?,?,?)";
				insert_update = conexion.clientPrepareStatement(query);
				insert_update.setString(1, mail);
				insert_update.setInt(2, id);
				insert_update.setInt(3, voto);
			}
			int returned = insert_update.executeUpdate();
			
			select.close();
			insert_update.close();
			DbConnection.closeConnection();
			
			return returned > 0;
		} catch(SQLException e){
			DbConnection.closeConnection();
			return false;
		}
	}	
	
	/**
	 * @param id identificador de la receta
	 * @param test <true> si es test
	 * @return numero de me gusta
	 */
	public static int getMeGusta(int id, boolean test){
		DbConnection.initConnection();
		Connection conn = DbConnection.getConnection();
		PreparedStatement ps;
		int numMeGusta = 0;
		String tabla = "UsuarioValoraReceta";
		if(test) tabla = tabla + "Test";
		
		try{
			String query = "select valoracion from " + tabla + " where idReceta=?";
			ps = conn.clientPrepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int valoracion = rs.getInt(1);
				if(valoracion>0) numMeGusta++;
			}
			ps.close();
			
		}
		catch(Exception e){
			numMeGusta = 0;
		}
		DbConnection.closeConnection();
		
		return numMeGusta;
	}
	
	/**
	 * @param id identificador de la receta
	 * @param test <true> si es test
	 * @return numero de no me gusta
	 */
	public static int getNoMeGusta(int id, boolean test){
		DbConnection.initConnection();
		Connection conn = DbConnection.getConnection();
		PreparedStatement ps;
		int numNoMeGusta = 0;
		String tabla = "UsuarioValoraReceta";
		if(test) tabla = tabla + "Test";
		
		try{
			String query = "select valoracion from " + tabla + " where idReceta=?";
			ps = conn.clientPrepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int valoracion = rs.getInt(1);
				if(valoracion<0) numNoMeGusta++;
			}
			ps.close();
			
		}
		catch(Exception e){
			numNoMeGusta = 0;
		}
		DbConnection.closeConnection();
		
		return numNoMeGusta;
	}
	
	/**
	 * @param id identificador de la receta
	 * @param test <true> si es test
	 * @return media de valoraciones
	 */
	public static double valoracion_media(int id, boolean test){
		DbConnection.initConnection();
		Connection conn = DbConnection.getConnection();
		PreparedStatement ps;
		double media = 0;
		
		String tabla = "UsuarioValoraReceta";
		if(test) tabla = tabla + "Test";
		
		try{
			String query = "select valoracion from " + tabla + " where idReceta=?";
			ps = conn.clientPrepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			int numValoraciones = 0;
			while(rs.next()){
				int valoracion = rs.getInt(1);
				numValoraciones++;
				media = media + valoracion;
			}
			ps.close();
			
			media = (double) ((double) media / (double) numValoraciones);
		}
		catch(Exception e){
			media = -2;
		}
		DbConnection.closeConnection();
		
		return media;
	}
}
