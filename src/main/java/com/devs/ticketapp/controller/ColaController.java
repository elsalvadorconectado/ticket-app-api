/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.ticketapp.controller;

import com.devs.ticketapp.dao.ColaModel;
import com.devs.ticketapp.dao.EmpresaModel;
import com.devs.ticketapp.dao.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author azus
 */
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ColaController {
     
    
    
    @RequestMapping(value="/cola/list", method=RequestMethod.GET)
    public String obtenerTotalColas(){
        ColaModel model= new ColaModel();
        String respuesta = model.obtenerTotalColas();
        return respuesta;
    }
    

    
    @RequestMapping(value="/cola", method=RequestMethod.POST)
    public String obtenerColaById(@RequestBody String jSonRequest){
        ColaModel model= new ColaModel();
        String respuesta = model.obtenerColaByID(jSonRequest);
        return respuesta;
    }
     
    @RequestMapping(value="/cola/add", method=RequestMethod.POST)
    public String insertarCola(@RequestBody String jSonRequest){
        ColaModel model= new ColaModel();
        String respuesta = model.insertarCola(jSonRequest);
        return respuesta;
    }
    
    
     /* Metodo que se utiliza para modificar un usuario especifico en la BD  
        @Param JsonRequest [{"idusuario" : 20000,"nombre":"WebServicePrueba","telefono":"1234567", "notificacion":"SMS"}]
    */   
    @RequestMapping(value="/cola/edit", method=RequestMethod.PUT)
    public String modificarCola(@RequestBody String jSonRequest){
        ColaModel model= new ColaModel();
        String respuesta = model.modificarCola(jSonRequest);
        return respuesta;
    }
    
    
    @RequestMapping(value="/cola/delete", method=RequestMethod.DELETE)
    public String eliminarCola(@RequestBody String jSonRequest){
        ColaModel model= new ColaModel();
        String respuesta = model.eliminarCola(jSonRequest);
        return respuesta;
    }
}
