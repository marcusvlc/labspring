package br.com.lab2spring.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lab2spring.ws.model.Artista;
import br.com.lab2spring.ws.model.PlayList;
import br.com.lab2spring.ws.model.Usuario;
import br.com.lab2spring.ws.services.PlayListService;
import br.com.lab2spring.ws.services.UsuarioService;

@CrossOrigin
@RestController
public class PlayListController {
	
	@Autowired
	PlayListService playListService;
	@Autowired
	UsuarioService usuarioService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/usuarios/{id}/playlists", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlayList> cadastrarPlaylist(@RequestBody PlayList playlist, @PathVariable Long id) {
		
		Usuario user = usuarioService.buscarPorId(id);
		
		playlist.setUsuario(user);
				
		PlayList playlistCadastrada = playListService.cadastrar(playlist);

		
		return new ResponseEntity<>(playlistCadastrada, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/usuarios/{id}/playlists/{id_playlist}" )
	public ResponseEntity<PlayList> excluirPlaylist(@PathVariable Long id_playlist) {
		
		PlayList playListEncontrada = playListService.buscarPorId(id_playlist);
		if (playListEncontrada==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		playListService.excluir(playListEncontrada);
		return new ResponseEntity<>( HttpStatus.OK);
	}

}
