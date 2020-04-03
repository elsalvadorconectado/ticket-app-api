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
import com.devs.ticketapp.model.Ticket;
import com.devs.ticketapp.repository.TicketRepository;

@RestController
@RequestMapping("/ticket")
public class TicketController {

	@Autowired()
	private TicketRepository ticketRepository;
	
    @RequestMapping()
    public List<Ticket> obtenerTickets(){
        return ticketRepository.findAll();
    }
    
    @GetMapping(value="/{ticketId}")
    public Ticket obtenerTicketById(@PathVariable Long ticketId){
    	if(!ticketRepository.existsById(ticketId)) {
    		throw new ResourceNotFoundException("No se ha encontrado Ticket con ID: " + ticketId);
    	}
    	
    	return ticketRepository.findById(ticketId).get();
    }
     
    @PostMapping()
    public Ticket insertarTicket(@RequestBody Ticket newTicket){
        if(newTicket == null) {
        	throw new BadRequestException("Solicitud inválida");
        }
        
        return ticketRepository.save(newTicket);
    }
    
    @PutMapping(value="/{ticketId}")
    public Ticket modificarTicket(@PathVariable Long ticketId, @RequestBody Ticket newTicket){
        if(newTicket == null) {
        	throw new BadRequestException("Solicitud inválida para Ticket con ID " + ticketId);
        }
        
        if(!ticketRepository.existsById(ticketId)) {
    		throw new ResourceNotFoundException("No se ha encontrado Ticket con ID: " + ticketId);
    	}
        
        Ticket ticketToUpdate = ticketRepository.findById(ticketId).get();
        ticketToUpdate.setEstado(newTicket.getEstado());
        ticketToUpdate.setPosicion(newTicket.getPosicion());
        ticketToUpdate.setUltimaNotificacion(newTicket.getUltimaNotificacion());
        ticketToUpdate.setAtendidoEn(newTicket.getAtendidoEn());
        
        return ticketRepository.save(ticketToUpdate);        
    }
    
    @DeleteMapping(value="/{ticketId}")
    public ResponseEntity<?> eliminarTicket(@PathVariable Long ticketId){
    	if(!ticketRepository.existsById(ticketId)) {
    		throw new ResourceNotFoundException("No se ha encontrado Ticket con ID: " + ticketId);
    	}
    	
    	ticketRepository.deleteById(ticketId);
    	
    	return ResponseEntity.ok().build();
    }

}