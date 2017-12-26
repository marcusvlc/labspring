package br.com.lab2spring.ws.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lab2spring.ws.model.Usuario;
import br.com.lab2spring.ws.services.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class LoginController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value="/autenticar", consumes=MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.POST )
	public ResponseEntity<Usuario> autenticar(@RequestBody Usuario usuario) throws ServletException {
		
		if(usuario.getLogin() == null || usuario.getSenha() == null) {
			throw new ServletException("Nome e senha são obrigatórios!");
		}
		
		Usuario usuarioAutenticado = usuarioService.buscarPorLogin(usuario.getLogin());
		
		if(usuarioAutenticado == null) {
			throw new ServletException("Usuário não cadastrado!");
		}
		
		if(!usuarioAutenticado.getSenha().equals(usuario.getSenha())) {
			throw new ServletException("Usuário ou senha inválidos!");
		}
		
		return new ResponseEntity<Usuario>(usuarioAutenticado, HttpStatus.OK);
		
//		String token = Jwts.builder()
//				.setSubject(usuarioAutenticado.getLogin())
//				.signWith(SignatureAlgorithm.HS512, "livuska")
//				.setExpiration(new Date(System.currentTimeMillis() + 60000)) // 1 MINUTO ATUALMENTE.
//				.compact();
		
//		return new LoginResponse(token);
	}
	
	private class LoginResponse {
		public String token;
		
		public LoginResponse(String token) {
			this.token = token;
		}
		
	}

}
