package br.com.lab2spring.ws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lab2spring.ws.model.MusicaDaPlaylist;
import br.com.lab2spring.ws.repository.MusicaDaPlaylistRepository;

@Service
public class MusicaDaPlaylistService {
	
	@Autowired
	MusicaDaPlaylistRepository musicaDaPlaylistRepository;
	
	public MusicaDaPlaylist cadastrar(MusicaDaPlaylist musica) {
		return musicaDaPlaylistRepository.save(musica);
	}
	
	public void remover(MusicaDaPlaylist musica) {
		musicaDaPlaylistRepository.delete(musica);
	}
	
	public MusicaDaPlaylist buscarPorId(Long id) {
		return musicaDaPlaylistRepository.findOne(id);
	}
	
	public MusicaDaPlaylist buscarPorNome(String nome) {
		return musicaDaPlaylistRepository.buscarPorNome(nome);
	}

}
