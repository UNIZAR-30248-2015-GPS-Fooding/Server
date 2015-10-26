/*
 * Receta.java v1.0 26/10/2015
 */

package data;

import java.util.List;

public class Receta {

	/**
	 * Clase para representar las recetas
	 * 
	 * @version 1.0
	 * @date 26/10/2015
	 */

	/* atributos de las recetas */
	private String nombre = null;
	private String tipo = null;
	private String instrucciones = null;
	private List<Ingrediente> ingredientes = null;
	private int me_gusta = 0;
	private int no_me_gusta = 0;

	/**
	 * Metodo para crear recetas
	 */
	public Receta(){
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
	 * @return el tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            el nuevo tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return las instrucciones
	 */
	public String getInstrucciones() {
		return instrucciones;
	}

	/**
	 * @param instrucciones
	 *            las nuevas instrucciones
	 */
	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	/**
	 * @return los ingredientes
	 */
	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	/**
	 * @param ingredientes
	 *            los nuevos ingredientes
	 */
	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	/**
	 * @return el me_gusta
	 */
	public int getMe_gusta() {
		return me_gusta;
	}

	/**
	 * @param me_gusta
	 *            el nuevo me_gusta
	 */
	public void setMe_gusta(int me_gusta) {
		this.me_gusta = me_gusta;
	}

	/**
	 * @return el no_me_gusta
	 */
	public int getNo_me_gusta() {
		return no_me_gusta;
	}

	/**
	 * @param no_me_gusta
	 *            el nuevo no_me_gusta
	 */
	public void setNo_me_gusta(int no_me_gusta) {
		this.no_me_gusta = no_me_gusta;
	}

}
