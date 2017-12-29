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
import br.com.lab2spring.ws.model.MusicaDaPlaylist;
import br.com.lab2spring.ws.model.PlayList;
import br.com.lab2spring.ws.services.MusicaDaPlaylistService;
import br.com.lab2spring.ws.services.PlayListService;

@RestController
public class MusicaDaPlaylistController {
	
	@Autowired
	PlayListService playListService;
	
	@Autowired
	MusicaDaPlaylistService musicaDaPlaylistService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/usuarios/{id}/playlists/{id_playlist}/musicadaplaylist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MusicaDaPlaylist> cadastrarAlbum(@RequestBody MusicaDaPlaylist musicaDaPlaylist, @PathVariable Long id_playlist) {
		
		PlayList playlist = playListService.buscarPorId(id_playlist);
		musicaDaPlaylist.setPlaylist(playlist);
		
		MusicaDaPlaylist musicaCadastrada = musicaDaPlaylistService.cadastrar(musicaDaPlaylist);

		
		return new ResponseEntity<>(musicaCadastrada, HttpStatus.CREATED);
	}

}
