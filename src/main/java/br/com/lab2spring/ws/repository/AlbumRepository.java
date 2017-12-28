package br.com.lab2spring.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lab2spring.ws.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
