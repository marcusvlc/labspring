package br.com.lab2spring.ws.controller;

import java.util.Collection;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lab2spring.ws.model.Usuario;
import br.com.lab2spring.ws.services.UsuarioService;

@CrossOrigin
@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value="/usuarios", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) throws ServletException {
		
		if(usuario.getLogin() == null || usuario.getSenha() == null || usuario.getEmail() == null) {
			throw new ServletException("Nome, email e senha são obrigatórios para registro!");
		}
		
		Usuario usuarioCadastrado = usuarioService.cadastrar(usuario);
		
		return new ResponseEntity<Usuario>(usuarioCadastrado, HttpStatus.OK );
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Usuario>> buscarTodosClientes() {

		Collection<Usuario> usuariosBuscados = usuarioService.buscarTodos();

		return new ResponseEntity<>(usuariosBuscados, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> alterarUsuario(@RequestBody Usuario usuario) {

		Usuario usuarioAlterado = usuarioService.alterar(usuario);
		return new ResponseEntity<>(usuarioAlterado, HttpStatus.OK);
	}
}
