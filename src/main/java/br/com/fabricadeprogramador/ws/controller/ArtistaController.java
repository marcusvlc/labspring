package br.com.fabricadeprogramador.ws.controller;

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

import br.com.fabricadeprogramador.ws.model.Artista;
import br.com.fabricadeprogramador.ws.services.ArtistaService;

@RestController
public class ArtistaController {
	
	@Autowired
	ArtistaService artistaService;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/clientes/{id}/artistas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artista> cadastrarCliente(@RequestBody Artista artista) {

		Artista artistaCadastrado = artistaService.cadastrar(artista);
		return new ResponseEntity<>(artistaCadastrado, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/clientes/{id}/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Artista>> buscarTodosClientes() {

		Collection<Artista> artistasBuscados = artistaService.buscarTodos();

		return new ResponseEntity<>(artistasBuscados, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/clientes/{id}/artistas/{id}" )
	public ResponseEntity<Artista> excluirCliente(@PathVariable Long id) {
		
		Artista artistaEncontrado = artistaService.buscarPorId(id);
		if (artistaEncontrado==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		artistaService.excluir(artistaEncontrado);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/clientes/{id}/artistas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artista> alterarCliente(@RequestBody Artista artista) {

		Artista artistaAlterado = artistaService.alterar(artista);
		return new ResponseEntity<>(artistaAlterado, HttpStatus.OK);
	}

	
}
