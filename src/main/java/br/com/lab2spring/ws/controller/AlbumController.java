package br.com.lab2spring.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lab2spring.ws.model.Album;
import br.com.lab2spring.ws.model.Artista;
import br.com.lab2spring.ws.services.AlbumService;
import br.com.lab2spring.ws.services.ArtistaService;

@RestController
public class AlbumController {
	
	@Autowired
	AlbumService albumService;
	
	@Autowired
	ArtistaService artistaService;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/usuarios/{id}/artistas/{id_artista}/albuns", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Album> cadastrarAlbum(@RequestBody Album album, @PathVariable Long id_artista) {
		
		Artista artista = artistaService.buscarPorId(id_artista);
		album.setArtista(artista);
				
		Album albumCadastrado = albumService.cadastrar(album);

		
		return new ResponseEntity<>(albumCadastrado, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/usuarios/{id}/artistas/{id_artista}/albuns/{id_album}" )
	public ResponseEntity<Album> excluirAlbum(@PathVariable Long id_album, @PathVariable Long id_artista) {
		
		Album albumEncontrado = albumService.buscarPorId(id_album);
		if (albumEncontrado==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		albumService.excluir(albumEncontrado);
		return new ResponseEntity<>( HttpStatus.OK);
	}

}
