package br.com.lab2spring.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lab2spring.ws.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Query(value="Select u from Usuario u where u.email=:pemail")
	public Usuario buscarPorEmail(@Param("pemail") String email);

}
