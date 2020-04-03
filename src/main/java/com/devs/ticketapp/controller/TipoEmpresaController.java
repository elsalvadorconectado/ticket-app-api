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
import com.devs.ticketapp.model.TipoEmpresa;
import com.devs.ticketapp.repository.TipoEmpresaRepository;

@RestController
@RequestMapping("/tipoempresa")
public class TipoEmpresaController {
     
	@Autowired
    private TipoEmpresaRepository tipoEmpresaRepository;
    
    @GetMapping()
    public List<TipoEmpresa> obtenerTotalTipoEmpresa(){
        return tipoEmpresaRepository.findAll();
    }
    
    @GetMapping(value="/{idTipoEmpresa}")
    public TipoEmpresa obtenerTipoEmpresaById(@PathVariable Integer idTipoEmpresa){
        if(!tipoEmpresaRepository.existsById(idTipoEmpresa)) {
        	throw new ResourceNotFoundException("No se ha encontrado Tipo empresa con ID: " + idTipoEmpresa);
        }
        
        return tipoEmpresaRepository.findById(idTipoEmpresa).get();
    }
     
    @PostMapping()
    public TipoEmpresa insertarTipoEmpresa(@RequestBody TipoEmpresa newTipoEmpresa){
    	
    	if(newTipoEmpresa == null) {
    		throw new BadRequestException("Solicitud inválida");
    	}
    	
    	return tipoEmpresaRepository.save(newTipoEmpresa);
    } 
  
    @PutMapping(value = "/{idTipoEmpresa}")
    public TipoEmpresa modificarTipoEmpresa(@PathVariable Integer idTipoEmpresa, @RequestBody TipoEmpresa tipoEmpresa){
    	if(tipoEmpresa == null) {
    		throw new BadRequestException("Solicitud inválida para Tipo empresa con ID " + idTipoEmpresa);
    	}
    	
        if(!tipoEmpresaRepository.existsById(idTipoEmpresa)) {
        	throw new ResourceNotFoundException("TipoEmpresa no encontrada con ID " + idTipoEmpresa);
        }
        
        TipoEmpresa tipoEmpresaToUpdate = tipoEmpresaRepository.findById(idTipoEmpresa).get();
        tipoEmpresaToUpdate.setTipo(tipoEmpresa.getTipo());
        tipoEmpresaToUpdate.setDescripcion(tipoEmpresa.getDescripcion());
        tipoEmpresaToUpdate.setImagen(tipoEmpresa.getImagen());
        return tipoEmpresaRepository.save(tipoEmpresaToUpdate);
    }
    
    @DeleteMapping(value = "/{idTipoEmpresa}")
    public ResponseEntity<?> eliminarTipoEmpresa(@PathVariable Integer idTipoEmpresa){
    	if(!tipoEmpresaRepository.existsById(idTipoEmpresa)) {
            throw new ResourceNotFoundException("Tipo Empresa no encontrada con ID " + idTipoEmpresa);
        }

    	tipoEmpresaRepository.deleteById(idTipoEmpresa);
        return ResponseEntity.ok().build();
    }
}
