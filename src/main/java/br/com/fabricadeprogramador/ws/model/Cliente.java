package br.com.fabricadeprogramador.ws.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String login;
	private String senha;
	private String email;
	
	public Cliente(Integer id, String login, String senha, String email) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.email = email;
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

}
