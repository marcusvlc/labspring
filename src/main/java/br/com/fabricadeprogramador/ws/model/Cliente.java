package br.com.fabricadeprogramador.ws.model;



import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Cliente_Tabela")
public class Cliente {
	
	@Id
	@GeneratedValue
	private Long id;
	private String login;
	private String senha;
	private String email;
	private boolean estaLogado;
	
	@OneToMany(mappedBy="cliente")
	private List<Artista> artistas;
	
	
	public Cliente() {

	}
	
	public boolean getEstaLogado() {
		return estaLogado;
	}
	
	public void setEstaLogado (boolean estaLogado) {
		this.estaLogado = estaLogado;
	}
 	
	public List<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(List<Artista> artistas) {
		this.artistas = artistas;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

}
