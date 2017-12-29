package br.com.lab2spring.ws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lab2spring.ws.model.Album;
import br.com.lab2spring.ws.repository.AlbumRepository;

@Service
public class AlbumService {
	
	@Autowired
	AlbumRepository albumRepository;
	
	@Autowired
	MusicaService musicaService;

	public Album cadastrar(Album album) {
		return albumRepository.save(album);
	}
	
	public Album buscarPorId(Long id) {
		return albumRepository.findOne(id);
	}
	
	public void excluir(Album album) {
		for(int i = 0; i < album.getMusicas().size(); i++) {
			musicaService.remover(album.getMusicas().get(i));
		}
		
		albumRepository.delete(album);
	}

}
