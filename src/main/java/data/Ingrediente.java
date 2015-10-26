/*
 * Ingrediente.java v1.0 26/10/2015
 */

package data;

public class Ingrediente {

	/**
	 * Clase para representar los ingredientes en las recetas
	 * 
	 * @version 1.0
	 * @date 26/10/2015
	 */

	/* atributos de los ingredientes */
	private String nombre = null;
	private int cantidad = 0;
	private String uds = null;

	/**
	 * Metodo de creacion de ingredientes
	 */
	public Ingrediente(){
	}
	
	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nuevo nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return la cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 *            la nueva cantidad
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return las uds
	 */
	public String getUds() {
		return uds;
	}

	/**
	 * @param uds
	 *            las nuevas uds
	 */
	public void setUds(String uds) {
		this.uds = uds;
	}

}
