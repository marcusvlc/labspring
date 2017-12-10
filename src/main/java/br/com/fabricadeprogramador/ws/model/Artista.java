package br.com.fabricadeprogramador.ws.model;

import java.io.Serializable;

public class Artista implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String imagem;
	private String comentario;
	private boolean ehFavorito;
	private String ultimaMusicaOuvida;
	
	public Artista() {
		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public boolean isEhFavorito() {
		return ehFavorito;
	}
	public void setEhFavorito(boolean ehFavorito) {
		this.ehFavorito = ehFavorito;
	}
	public String getUltimaMusicaOuvida() {
		return ultimaMusicaOuvida;
	}
	public void setUltimaMusicaOuvida(String ultimaMusicaOuvida) {
		this.ultimaMusicaOuvida = ultimaMusicaOuvida;
	}
	
	
}
