package br.com.fabricadeprogramador.ws.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabricadeprogramador.ws.model.Usuario;
import br.com.fabricadeprogramador.ws.services.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class LoginController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value="/autenticar", consumes=MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.POST )
	public LoginResponse autenticar(@RequestBody Usuario usuario) {
		System.out.println(usuario.getLogin() +  " " + usuario.getSenha());
		
		Usuario usuarioAutenticado = usuarioService.buscarPorLogin(usuario.getLogin());
		
		//retornar um token e nao um usuario
		
		String token = Jwts.builder()
				.setSubject(usuarioAutenticado.getLogin())
				.signWith(SignatureAlgorithm.HS512, "livuska")
				.setExpiration(new Date(System.currentTimeMillis() + 60000)) // 1 MINUTO ATUALMENTE.
				.compact();
		
		return new LoginResponse(token);
	}
	
	private class LoginResponse {
		public String token;
		
		public LoginResponse(String token) {
			this.token = token;
		}
		
	}

}
