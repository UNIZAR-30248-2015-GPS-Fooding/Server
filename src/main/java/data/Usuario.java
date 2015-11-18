/*
 * Usuario.java v1.0 16/11/2015
 */

package data;

public class Usuario {
	
	/**
	 * Clase para representar usuarios
	 * 
	 * @version 1.0
	 * @date 16/11/2015
	 */
	
	/* atributos del usuario */
	private String mail = null;
	private String nick = null;
	private int verificado = 0;
	private int score = 0;
	
	/**
	 * Metodo para crear usuarios
	 */
	public Usuario(){
	}
	
	/**
	 * @return el mail
	 */
	public String getMail() {
		return mail;
	}
	
	/**
	 * @param mail
	 *            correo del usuario
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	/**
	 * @return el nombre de usuario
	 */
	public String getNick() {
		return nick;
	}
	
	/**
	 * @param nick
	 *            nombre del usuario
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * @return <true> si el correo del usuario ha sido verificado
	 * o <false> en caso contrario
	 */
	public boolean isVerificado() {
		return verificado == 1;
	}
	
	/**
	 * @param verificado
	 *            <1> para marcar como verificado
	 *            <0> para anular la verificacion
	 */
	public void setVerificado(int verificado) {
		this.verificado = verificado;
	}
	
	/**
	 * @return la puntuacion del usuario
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * @param score
	 *            nueva puntuacion del usuario
	 */
	public void setScore(int score) {
		this.score = score;
	}

}
