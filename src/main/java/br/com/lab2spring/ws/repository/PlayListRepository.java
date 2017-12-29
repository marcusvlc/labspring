package br.com.lab2spring.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lab2spring.ws.model.PlayList;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long> {

}
