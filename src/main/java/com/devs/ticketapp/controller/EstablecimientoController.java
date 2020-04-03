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
import com.devs.ticketapp.model.Establecimiento;
import com.devs.ticketapp.repository.EstablecimientoRepository;

@RestController
@RequestMapping("/establecimiento")
public class EstablecimientoController {
	
	@Autowired
    private EstablecimientoRepository establecimientoRepository;
	
    @GetMapping()
    public List<Establecimiento> obtenerTotalColas(){
        return establecimientoRepository.findAll();
    }    
    
    @GetMapping(value="/{idEstablecimiento}")
    public Establecimiento obtenerColaById(@PathVariable Integer idEstablecimiento){
    	if(!establecimientoRepository.existsById(idEstablecimiento)) {
        	throw new ResourceNotFoundException("No se ha encontrado Establecimiento con ID: " + idEstablecimiento);
        }
    	
    	return establecimientoRepository.findById(idEstablecimiento).get();
    }
     
    @PostMapping()
    public Establecimiento insertarCola(@RequestBody Establecimiento establecimiento){
        if(establecimiento == null) {
        	throw new BadRequestException("Solicitud inválida");
        }
        
        return establecimientoRepository.save(establecimiento);
    }
        
    @PutMapping(value="/{idEstablecimiento}")
    public Establecimiento modificarCola(@PathVariable Integer idEstablecimiento, @RequestBody Establecimiento newEstablecmiento){
    	if(newEstablecmiento == null) {
    		throw new BadRequestException("Solicitud inválida para Establecimiento con ID " + idEstablecimiento);
    	}
    	
    	if(!establecimientoRepository.existsById(idEstablecimiento)) {
        	throw new ResourceNotFoundException("No se ha encontrado Establecimiento con ID: " + idEstablecimiento);
        }
    	
    	Establecimiento establecimientoToUpdate = establecimientoRepository.findById(idEstablecimiento).get();
    	establecimientoToUpdate.setNombre(newEstablecmiento.getNombre());
    	establecimientoToUpdate.setDireccion1(newEstablecmiento.getDireccion1());
    	establecimientoToUpdate.setDireccion2(newEstablecmiento.getDireccion2());
    	establecimientoToUpdate.setLatitud(newEstablecmiento.getLatitud());
    	establecimientoToUpdate.setLongitud(newEstablecmiento.getLongitud());
    	establecimientoToUpdate.setDuracionvenmin(newEstablecmiento.getDuracionvenmin());
    	establecimientoToUpdate.setIdEmpresa(newEstablecmiento.getIdEmpresa());
    	
    	return establecimientoRepository.save(establecimientoToUpdate);
    }
    
    
    @DeleteMapping(value="/{idEstablecimiento}")
    public ResponseEntity<?> eliminarCola(@PathVariable Integer idEstablecimiento){
    	if(!establecimientoRepository.existsById(idEstablecimiento)) {
        	throw new ResourceNotFoundException("No se ha encontrado Establecimiento con ID: " + idEstablecimiento);
        }
    	
    	establecimientoRepository.deleteById(idEstablecimiento);
    	
    	return ResponseEntity.ok().build();
    }
}