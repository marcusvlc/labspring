package br.com.lab2spring.ws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lab2spring.ws.model.Musica;
import br.com.lab2spring.ws.repository.MusicaRepository;

@Service
public class MusicaService {
	
	@Autowired
	MusicaRepository musicaRepository;
	
	public Musica cadastrar(Musica musica) {
		return musicaRepository.save(musica);
	}
	
	public void remover(Musica musica) {
		
		musicaRepository.delete(musica);
	}
	
	

}
