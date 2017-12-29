package br.com.lab2spring.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lab2spring.ws.model.MusicaDaPlaylist;

@Repository
public interface MusicaDaPlaylistRepository extends JpaRepository<MusicaDaPlaylist, Long> {
	
	@Query(value="Select u from MusicaDaPlaylist u where u.nome=:pnome")
	public MusicaDaPlaylist buscarPorNome(@Param("pnome") String nome);

}
