package br.com.lab2spring.ws.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lab2spring.ws.model.Artista;
import br.com.lab2spring.ws.repository.AlbumRepository;
import br.com.lab2spring.ws.repository.ArtistaRepository;

@Service
public class ArtistaService {
	
	@Autowired
	ArtistaRepository artistaRepository;
	
	@Autowired
	AlbumService albumService;
	
	
	
	public Artista cadastrar(Artista artista) {

		return artistaRepository.save(artista);

	}

	public Collection<Artista> buscarTodos() {
		return artistaRepository.findAll();
	}
	
	public void excluir (Artista artista){
		
		for(int i = 0; i < artista.getAlbuns().size(); i++) {
			albumService.excluir(artista.getAlbuns().get(i));
		}
		
		artistaRepository.delete(artista);
	}
	
	public Artista buscarPorId(Long id) {
		return artistaRepository.findOne(id);
	}
	
	public Artista alterar(Artista artista){
		return artistaRepository.save(artista);

	}

}

