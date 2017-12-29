package br.com.lab2spring.ws.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.doctype.resolution.ServletContextDocTypeResolutionEntry;

import br.com.lab2spring.ws.model.Album;
import br.com.lab2spring.ws.model.Musica;
import br.com.lab2spring.ws.services.AlbumService;
import br.com.lab2spring.ws.services.MusicaService;

@RestController
public class MusicaController {
	
	@Autowired
	AlbumService albumService;
	@Autowired
	MusicaService musicaService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/usuarios/{id}/artistas/{id_artista}/albuns/{id_album}/musicas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Musica> cadastrarMusica(@RequestBody Musica musica, @PathVariable Long id_album) throws ServletException {
		
		Album album = albumService.buscarPorId(id_album);
		if(album != null) {
			musica.setAlbum(album);
		} else {
			throw new ServletException("O album na qual a musica esta sendo cadastrada nao existe");
		}

				
		Musica musicaCadastrada = musicaService.cadastrar(musica);

		
		return new ResponseEntity<>(musicaCadastrada, HttpStatus.CREATED);
	}
	
	
}
