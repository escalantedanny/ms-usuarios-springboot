package com.escalantedanny.usuario.service;

import java.util.List;

import com.escalantedanny.usuario.exception.CustomError;
import com.escalantedanny.usuario.models.entity.Usuario;
import com.escalantedanny.usuario.models.entity.response.ResponseUserData;

public interface IUsuarioService {
	
	public List<Usuario> findAll() throws CustomError;
	public Usuario findByUUID(String id) throws CustomError;
	public ResponseUserData save(Usuario usuario, String token) throws CustomError;
	public void deleteById(String  id) throws CustomError;
	public String findByEmail(String email);
	public void updateUsuario(Usuario usuario);
	
}
