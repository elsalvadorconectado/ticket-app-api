package com.devs.ticketapp.controller;
import org.springframework.web.bind.annotation.RestController;

import com.devs.ticketapp.dao.RolModel;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class RolController {

    @RequestMapping(value="/rol/list", method=RequestMethod.GET)
    public String obtenerTotalColas(){
        RolModel model= new RolModel();
        String respuesta = model.obtenerTotalRoles();
        return respuesta;
    }
    

    
    @RequestMapping(value="/rol", method=RequestMethod.POST)
    public String obtenerColaById(@RequestBody String jSonRequest){
        RolModel model= new RolModel();
        String respuesta = model.obtenerRolByID(jSonRequest);
        return respuesta;
    }
     
    @RequestMapping(value="/rol/add", method=RequestMethod.POST)
    public String insertarRol(@RequestBody String jSonRequest){
        RolModel model= new RolModel();
        String respuesta = model.insertarRol(jSonRequest);
        return respuesta;
    }
    
    
    @RequestMapping(value="/rol/edit", method=RequestMethod.PUT)
    public String modificarRol(@RequestBody String jSonRequest){
        RolModel model= new RolModel();
        String respuesta = model.modificarRol(jSonRequest);
        return respuesta;
    }
    
    
    @RequestMapping(value="/rol/delete", method=RequestMethod.DELETE)
    public String eliminarRol(@RequestBody String jSonRequest){
        RolModel model= new RolModel();
        String respuesta = model.eliminarRol(jSonRequest);
        return respuesta;
    }
}