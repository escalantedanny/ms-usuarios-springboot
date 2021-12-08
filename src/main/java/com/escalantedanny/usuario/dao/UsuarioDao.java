package com.escalantedanny.usuario.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.escalantedanny.usuario.models.entity.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, UUID> {
	
	@Query(value = "SELECT * FROM usuarios u WHERE u.id = :id", nativeQuery = true)
	public Usuario findByUUID( @Param("id") UUID id);
	
	@Query(value = "SELECT * FROM usuarios u WHERE u.email = :email", nativeQuery = true)
	public String findByEmail( @Param("email") String email);

}
