package com.escalantedanny.usuario.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.escalantedanny.usuario.dao.UsuarioDao;
import com.escalantedanny.usuario.exception.CustomError;
import com.escalantedanny.usuario.models.entity.Usuario;
import com.escalantedanny.usuario.models.entity.response.ResponseUserData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceUsuarioImpl implements IUsuarioService {

	@Autowired
	private UsuarioDao usuariosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() throws CustomError {
		try {			
			return (List<Usuario>) usuariosDao.findAll();
		} catch (NullPointerException e) {
			throw new CustomError("Servidor caido");
		} catch (Exception e) {
			throw new CustomError("Servidor caido");
		}
	}

	@Override
	public Usuario findByUUID(String id) throws CustomError {
		try {			
			log.info("id {}", id);
			UUID sameUuid = UUID.fromString(id);
			if( usuariosDao.findByUUID(sameUuid) == null ) {
				throw new CustomError("User invalid");
			} else {				
				return usuariosDao.findByUUID(sameUuid);
			}
		} catch (NullPointerException e) {
			throw new CustomError("usuario invalido");
		} catch (Exception e) {
			throw new CustomError("usuario invalido");
		}
	}

	@Override
	@Transactional(readOnly = false)
	public ResponseUserData save(Usuario usuario, String token) throws CustomError {
		
		ResponseUserData responseUser = new ResponseUserData();
		try {	
			usuario.setToken(token);
			Usuario user = usuariosDao.save(usuario);
			responseUser.setId( user.getId() );
			responseUser.setCreated( user.getCreated() );
			responseUser.setModified( user.getModified() );
			responseUser.setLastLogin( user.getLastLogin() );
			responseUser.setIsActive(true);
			return responseUser;
		} catch (NullPointerException e) {
			throw new CustomError("usuario invalido");
		} catch (Exception e) {
			throw new CustomError("usuario no se encuentra registrado");
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(String  id) throws CustomError {
		try {			
			UUID sameUuid = UUID.fromString(id);
			usuariosDao.deleteById(sameUuid);
		} catch (NullPointerException e) {
			throw new CustomError("usuario invalido");
		} catch (Exception e) {
			throw new CustomError("usuario invalido");
		}
	}

	@Override
	public String findByEmail(String email) {
		String resp = usuariosDao.findByEmail(email);
		System.out.println(resp);
		if(resp != null)
			return "1";
		else
			return "0";
	}

	@Override
	public void updateUsuario(Usuario usuario){
		usuariosDao.deleteById(usuario.getId());
		usuariosDao.save(usuario);		
	}

}
