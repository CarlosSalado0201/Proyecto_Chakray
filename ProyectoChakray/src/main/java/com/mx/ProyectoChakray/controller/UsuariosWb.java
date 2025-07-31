package com.mx.ProyectoChakray.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.ProyectoChakray.dto.InicioSesionDTO;
import com.mx.ProyectoChakray.model.AccesoSesion;
import com.mx.ProyectoChakray.model.Usuario;
import com.mx.ProyectoChakray.service.UsuarioServiceImp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("UsuariosWb")
public class UsuariosWb {
	@Autowired
	private UsuarioServiceImp usuariosService;
	
	@GetMapping("mostrar")
	public List<Usuario> mostrarUsuarios(){
		return usuariosService.mostrarUsuarios();
	}
	
	@PostMapping("annadirUsuario")
	public ResponseEntity<String> annadirUsuario(@RequestBody Usuario nuevoUsuario){
		boolean exito = usuariosService.annadirUsuario(nuevoUsuario);
		if(exito == false) return new ResponseEntity<String>("tex_id ya existe o telefono  no valido o rfc no valido", HttpStatus.CREATED);
		else return new ResponseEntity<String> ("Agregado correctamente", HttpStatus.CREATED);
	}
	
	@DeleteMapping("eliminar/{id}")
	public ResponseEntity<String> borrarUsuarioXid (@PathVariable("id") UUID id){
		boolean eliminado = usuariosService.borrarUsuarioXid(id);
		if(eliminado == true)
			return new ResponseEntity<String>("Eliminado correctamente", HttpStatus.CREATED);
		else 
			return new ResponseEntity<String>("Error al eliminar",HttpStatus.CREATED);
	}
	@PatchMapping("editar/{id}")
	public ResponseEntity<String> editarUsuario (@PathVariable("id") UUID id, @RequestBody Usuario editable){
		boolean actualizado = usuariosService.editarUsuario(id,editable);
		if(actualizado == true)
			return new ResponseEntity<String>("Usuario actualizado", HttpStatus.CREATED);
		else 
			return new ResponseEntity<String>("Error al actualizado",HttpStatus.CREATED);
	}
	@PostMapping("iniciarSesion")
	public ResponseEntity<?> iniciarSesion(@RequestBody AccesoSesion login) {
	    InicioSesionDTO verifica = usuariosService.inicioSesion(login.getTax_id(), login.getContrasenna());
	    if(verifica != null) {
	        return ResponseEntity.ok(verifica);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
	    }
	}
	
	

	@GetMapping("")
	public ResponseEntity<List<Usuario>> filtrarYOrdenar(
	        @RequestParam(name = "ordenarPor", required = false) String ordenarPor,
	        @RequestParam(name = "filtro", required = false) String filtro
	) {
	    List<Usuario> resultado = usuariosService.obtenerUsuariosFiltradosYOrdenados(ordenarPor, filtro);
	    return ResponseEntity.ok(resultado);
	}
	
	

}
