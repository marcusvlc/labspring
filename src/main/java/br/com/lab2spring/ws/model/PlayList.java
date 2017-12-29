package br.com.lab2spring.ws.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class PlayList implements Serializable{
	
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="Usuario_id")
	private Usuario usuario;
	
	@JsonManagedReference
	@OneToMany(mappedBy="playlist")
	private List<MusicaDaPlaylist> musicas;
	
	
	public PlayList() {
		
	}
	

	public List<MusicaDaPlaylist> getMusicas() {
		return musicas;
	}


	public void setMusicas(List<MusicaDaPlaylist> musicas) {
		this.musicas = musicas;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
