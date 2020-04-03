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
import com.devs.ticketapp.model.Cola;
import com.devs.ticketapp.repository.ColaRepository;

@RestController
@RequestMapping("/cola")
public class ColaController {
    
	@Autowired
    private ColaRepository colaRepository;
    
    @GetMapping()
    public List<Cola> obtenerTotalColas(){
        return colaRepository.findAll();
    }
    
    @GetMapping(value = "/{colaId}")
    public Cola obtenerColaById(@PathVariable Integer colaId){
    	
    	if(!colaRepository.existsById(colaId)) {
    		throw new ResourceNotFoundException("Cola no encontrada con ID: " + colaId);
    	} else {
    		return colaRepository.findById(colaId).get();
    	}
    }
     
    @PostMapping()
    public Cola insertarCola(@RequestBody Cola newCola){
    	if(newCola == null) {
    		throw new BadRequestException("Solicitud inválida");
    	}
    	
        return colaRepository.save(newCola);
    }
     
    @PutMapping(value = "/{colaId}")
    public Cola modificarCola(@PathVariable Integer colaId, @RequestBody Cola colaNew){
    	
    	if(colaNew == null) {
    		throw new BadRequestException("Solicitud inválida para cola ID " + colaId);
    	}
    	
        if(!colaRepository.existsById(colaId)) {
        	throw new ResourceNotFoundException("Cola no encontrada con ID " + colaId);
        }
        
        Cola colaToUpdate = colaRepository.findById(colaId).get();
    	colaToUpdate.setCupos(colaNew.getCupos());
    	colaToUpdate.setDescripcion(colaNew.getDescripcion());
    	colaToUpdate.setUltimoAtendido(colaNew.getUltimoAtendido());
    	return colaRepository.save(colaToUpdate);
    }
    
    @DeleteMapping(value = "/{colaId}")
    public ResponseEntity<?> eliminarCola(@PathVariable Integer colaId){
        
        if(!colaRepository.existsById(colaId)) {
            throw new ResourceNotFoundException("Cola no encontrada con ID " + colaId);
        }

        colaRepository.deleteById(colaId);
        return ResponseEntity.ok().build();
    }
}
