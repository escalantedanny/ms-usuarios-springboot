package com.escalantedanny.usuario.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.escalantedanny.usuario.exception.CustomError;
import com.escalantedanny.usuario.exception.CustomMessage;
import com.escalantedanny.usuario.models.entity.Usuario;
import com.escalantedanny.usuario.models.entity.response.ResponseUserData;
import com.escalantedanny.usuario.service.IUsuarioService;
import com.escalantedanny.usuario.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@Api(tags = "Usuarios")
@RestController
public class UsuarioController {
	
    @Value("${codigo.token}")
    String tokenValido;
    
    @Value("${cantidad.caracteres}")
    int acount;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/users")
	@ApiOperation(value = "Genera listado de usuarios", notes = "Genera listado de usuarios en formato Json")
	public List<Usuario> listarUsuarios	(@RequestHeader("Authorization") String token) throws CustomError{
		token = token.replace("Bearer ", "");
		if(token.equals(tokenValido))
			return usuarioService.findAll();
		else
			return null;
		
	}
	
	@GetMapping("/user/{id}")
	@ApiOperation(value = "busca el detalle por Id del usuario", notes = "trae el objeto del usuario que se quiere buscar por ID")
	@Transactional
	public ResponseEntity<Usuario> detalleUsuario(@PathVariable String id, @RequestHeader("Authorization") String token) throws CustomError{
		token = token.replace("Bearer ", "");
		if(token.equals(tokenValido)) {
			Usuario data = usuarioService.findByUUID(id);
			return new ResponseEntity<>(data, HttpStatus.OK);
		} else
			return null;
		
	}	
	
	@PostMapping("/save")
	@ApiOperation(value = "Guarda el usuario en la base de datos", notes = "Genera un registro del usuario que se ingresa")
	public ResponseEntity<ResponseUserData> guardarUsuario(@Validated @RequestBody Usuario usuario, @RequestHeader("Authorization") String token) throws CustomError{
		token = token.replace("Bearer ", "");
		if(token.equals(tokenValido)) {
			if (Utils.validate(usuario.getEmail())) {
		        if (usuario.getPassword().length() < acount) {
		        	throw new CustomError("contraseña minimo debe tener 8 caracteres");
		        }
		        if(usuarioService.findByEmail(usuario.getEmail()) == "1") {
		        	throw new CustomError("email ya se encuentra registrado");
		        }
				ResponseUserData data = usuarioService.save(usuario, token);
				data.setToken(token);
				return new ResponseEntity<>(data, HttpStatus.OK);
			} else {
				throw new CustomError("email Invalido");
			}
			
		} else {
			return null;
		}
	}
	
	@DeleteMapping("/user/{id}")
	@ApiOperation(value = "Elimina el detalle por Id del usuario", notes = "Elimina el objeto del usuario que se quiere por ID")
	public void deleteUsuario(@PathVariable String id, @RequestHeader("Authorization") String token) throws CustomError{
		token = token.replace("Bearer ", "");
		if(token.equals(tokenValido))
			try {
				usuarioService.deleteById(id);
			} catch (NullPointerException e) {
				throw new CustomError("usuario invalido");
			} catch (Exception e) {
				throw new CustomError("usuario invalido");
			}
			
	}
	
	@PutMapping("/user")
	@ApiOperation(value = "Actualiza el detalle por usuario", notes = "Actualiza el objeto del usuario que se quiere completo")
	public void updateUsuario(@RequestBody Usuario usuario, @RequestHeader("Authorization") String token) throws CustomError{
		token = token.replace("Bearer ", "");
		if(token.equals(tokenValido))
			try {
				usuarioService.updateUsuario(usuario);
				throw new CustomMessage("Usuario Actualizado");
			} catch (Exception e) {
				throw new CustomError("Usuario Actualizado");
			}
			
	}
	
}
