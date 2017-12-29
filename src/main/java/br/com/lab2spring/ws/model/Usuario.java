package br.com.lab2spring.ws.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Usuario_Tabela")
public class Usuario implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String login;
	private String senha;
	private String email;
	
	@JsonManagedReference
	@OneToMany(mappedBy="usuario")
	private List<Artista> artistas;
	
	@JsonManagedReference
	@OneToMany(mappedBy="usuario")
	private List<PlayList> playlists;
	
	public Usuario() {
		
	}
	
	
	public List<PlayList> getPlaylists() {
		return playlists;
	}


	public void setPlaylists(List<PlayList> playlists) {
		this.playlists = playlists;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Artista> getArtistas() {
		return artistas;
	}
	public void setArtistas(List<Artista> artistas) {
		this.artistas = artistas;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


}
