package br.com.lab2spring.ws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lab2spring.ws.model.PlayList;
import br.com.lab2spring.ws.repository.PlayListRepository;

@Service
public class PlayListService {
	
	@Autowired
	PlayListRepository playListRepository;
	
	@Autowired
	MusicaDaPlaylistService musicaDaPlaylistService;

	public PlayList cadastrar(PlayList playlist) {
		return playListRepository.save(playlist);
	}
	
	public PlayList buscarPorId(Long id) {
		return playListRepository.findOne(id);
	}
	
	public void excluir(PlayList playlist) {
		
		for(int i = 0; i < playlist.getMusicas().size(); i++) {
			musicaDaPlaylistService.remover(playlist.getMusicas().get(i));
		}
		
		playListRepository.delete(playlist);
	}

}
