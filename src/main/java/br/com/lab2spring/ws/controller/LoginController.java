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
		
		if(usuario.getSenha() == null || usuario.getEmail() == null) {
			throw new ServletException("Email e senha são obrigatórios para autenticação!");
		}
		
		Usuario usuarioAutenticado = usuarioService.buscarPorEmail(usuario.getEmail());
		
		if(usuarioAutenticado == null) {
			throw new ServletException("Usuário não cadastrado!");
		}
		
		if(!usuarioAutenticado.getSenha().equals(usuario.getSenha())) {
			throw new ServletException("Usuário ou senha inválidos!");
		}
		
		return new ResponseEntity<Usuario>(usuarioAutenticado, HttpStatus.OK);
		

		
	}

}
