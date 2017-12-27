package br.com.lab2spring.ws.services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lab2spring.ws.model.Usuario;
import br.com.lab2spring.ws.repository.ArtistaRepository;
import br.com.lab2spring.ws.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ArtistaService artistaService;

	public Usuario cadastrar(Usuario usuario) {
		return usuarioRepository.save(usuario);
		
	}
	
	public Usuario buscarPorLogin(String login) {
		return usuarioRepository.buscarPorLogin(login);
	}

	public Collection<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}
	
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findOne(id);
	}

	public Usuario alterar(Usuario usuario) {
		
		for(int i = 0; i < usuario.getArtistas().size(); i++) {
			artistaService.cadastrar(usuario.getArtistas().get(i));
		}
		
		return usuarioRepository.save(usuario);
	}

}
