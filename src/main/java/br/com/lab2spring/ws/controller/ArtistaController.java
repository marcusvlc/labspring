package br.com.lab2spring.ws.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lab2spring.ws.model.Artista;
import br.com.lab2spring.ws.model.Usuario;
import br.com.lab2spring.ws.services.ArtistaService;
import br.com.lab2spring.ws.services.UsuarioService;

@RestController
public class ArtistaController {
	
	@Autowired
	ArtistaService artistaService;
	@Autowired
	UsuarioService usuarioService;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/usuarios/{id}/artistas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artista> cadastrarArtista(@RequestBody Artista artista, @PathVariable Long id) {
		
		Usuario user = usuarioService.buscarPorId(id);
		
		artista.setUsuario(user);
				
		Artista artistaCadastrado = artistaService.cadastrar(artista);
		System.out.println(artistaCadastrado.getNome());

		
		return new ResponseEntity<>(artistaCadastrado, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/usuarios/{id}/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Artista>> buscarTodosArtistas() {
		
		Collection<Artista> artistasBuscados = artistaService.buscarTodos();

		return new ResponseEntity<>(artistasBuscados, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/usuarios/{id}/artistas/{id_artista}" )
	public ResponseEntity<Artista> excluirArtista(@PathVariable Long id_artista) {
		
		Artista artistaEncontrado = artistaService.buscarPorId(id_artista);
		if (artistaEncontrado==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		artistaService.excluir(artistaEncontrado);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/usuarios/{id}/artistas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artista> alterarArtista(@RequestBody Artista artista) {

		Artista artistaAlterado = artistaService.alterar(artista);
		return new ResponseEntity<>(artistaAlterado, HttpStatus.OK);
	}

	
}
