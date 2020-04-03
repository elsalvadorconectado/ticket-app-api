/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.ticketapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devs.ticketapp.exception.BadRequestException;
import com.devs.ticketapp.exception.ResourceNotFoundException;
import com.devs.ticketapp.model.Usuario;
import com.devs.ticketapp.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
	@Autowired()
	private UsuarioRepository usuarioRepository;
	
    @GetMapping()
    public List<Usuario> obtenerTotalUsuarios(){
        return usuarioRepository.findAll();
    }
    
    @GetMapping(value="/{idUsuario}")
    public Usuario obtenerUsuarioById(@PathVariable Integer idUsuario){
        if(!usuarioRepository.existsById(idUsuario)) {
        	throw new ResourceNotFoundException("No se ha encontrado Usuario con ID: " + idUsuario);
        }
        
        return usuarioRepository.findById(idUsuario).get();
    }
    
    @PostMapping()
    public Usuario insertarUsuario(@RequestBody Usuario newUsuario){
    	if(newUsuario == null) {
    		throw new BadRequestException("Solicitud inválida");
    	}
    	
    	return usuarioRepository.save(newUsuario);
    }
  
    @PutMapping(value="/{idUsuario}")
    public Usuario modificarUsuario(@PathVariable Integer idUsuario, @RequestBody Usuario newUsuario){
    	if(newUsuario == null) {
    		throw new BadRequestException("Solicitud inválida para Usuario con ID " + idUsuario);
    	}
    	if(!usuarioRepository.existsById(idUsuario)) {
        	throw new ResourceNotFoundException("No se ha encontrado Usuario con ID: " + idUsuario);
        }
    	
    	Usuario usuarioToUpdate = usuarioRepository.findById(idUsuario).get();
    	usuarioToUpdate.setNombre(newUsuario.getNombre());
    	usuarioToUpdate.setTelefono(newUsuario.getTelefono());
    	usuarioToUpdate.setNotificacion(newUsuario.getNotificacion());
    	
    	return usuarioRepository.save(usuarioToUpdate);
    }
  
    @DeleteMapping(value="/{idUsuario}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer idUsuario){
    	if(!usuarioRepository.existsById(idUsuario)) {
        	throw new ResourceNotFoundException("No se ha encontrado Usuario con ID: " + idUsuario);
        }
    	
    	usuarioRepository.deleteById(idUsuario);
    	
    	return ResponseEntity.ok().build();
    }
}
