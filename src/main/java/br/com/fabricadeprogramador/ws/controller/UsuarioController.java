package br.com.fabricadeprogramador.ws.controller;

import java.util.Collection;

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

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value="/usuarios", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
		
		Usuario usuarioCadastrado = usuarioService.cadastrar(usuario);
		
		return new ResponseEntity<Usuario>(usuarioCadastrado, HttpStatus.OK );
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Usuario>> buscarTodosClientes() {

		Collection<Usuario> usuariosBuscados = usuarioService.buscarTodos();

		return new ResponseEntity<>(usuariosBuscados, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> alterarCliente(@RequestBody Usuario usuario) {

		Usuario usuarioAlterado = usuarioService.alterar(usuario);
		return new ResponseEntity<>(usuarioAlterado, HttpStatus.OK);
	}
}
