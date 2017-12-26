package br.com.lab2spring.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lab2spring.ws.model.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {

}
