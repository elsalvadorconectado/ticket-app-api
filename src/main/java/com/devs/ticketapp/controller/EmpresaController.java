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
import com.devs.ticketapp.model.Empresa;
import com.devs.ticketapp.repository.EmpresaRepository;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
     
	@Autowired
    private EmpresaRepository empresaRepository;
    
    @GetMapping()
    public List<Empresa> obtenerTotalEmpresas(){
        return empresaRepository.findAll();
    }
        
    @GetMapping(value="/{idEmpresa}")
    public Empresa obtenerEmpresaById(@PathVariable Integer idEmpresa){
        if(!empresaRepository.existsById(idEmpresa)) {
        	throw new ResourceNotFoundException("No se ha encontrado Empresa con ID: " + idEmpresa);
        }
        
        return empresaRepository.findById(idEmpresa).get();
    }
     
    @PostMapping()
    public Empresa insertarEmpresa(@RequestBody Empresa empresa){
        if(empresa == null) {
        	throw new BadRequestException("Solicitud inválida");
        }
        
        return empresaRepository.save(empresa);
    }
  
    @PutMapping(value="/{idEmpresa}")
    public Empresa modificarEmpresa(@PathVariable Integer idEmpresa, @RequestBody Empresa newEmpresa){
    	if(newEmpresa == null) {
    		throw new BadRequestException("Solicitud inválida para Empresa con ID " + idEmpresa);
    	}
    	
        if(!empresaRepository.existsById(idEmpresa)) {
        	throw new ResourceNotFoundException("Empresa no encontrada con ID " + idEmpresa);
        }
        
        Empresa empresaToUpdate = empresaRepository.findById(idEmpresa).get();
        empresaToUpdate.setIdTipoEmpresa(newEmpresa.getIdTipoEmpresa());
        empresaToUpdate.setNombre(newEmpresa.getNombre());
        empresaToUpdate.setNombreCorto(newEmpresa.getNombreCorto());
        empresaToUpdate.setNit(newEmpresa.getNit());
        
        return empresaRepository.save(empresaToUpdate);
    }
    
    
    @DeleteMapping(value="/{idEmpresa}")
    public ResponseEntity<?> eliminarEmpresa(@PathVariable Integer idEmpresa){
    	if(!empresaRepository.existsById(idEmpresa)) {
        	throw new ResourceNotFoundException("No se ha encontrado Empresa con ID: " + idEmpresa);
        }
    	
    	empresaRepository.deleteById(idEmpresa);
    	
    	return ResponseEntity.ok().build();
    }
}
