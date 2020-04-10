/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.ticketapp.controller;

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
public class UsuarioController {
     
    /* Metodo que se utiliza para  consultar todos los usuarios ingresados en la bd  */
    
    
    @RequestMapping(value="/usuario/list", method=RequestMethod.GET)
    public String obtenerTotalUsuarios(){
        UserModel model= new UserModel();
        String respuesta = model.obtenerTotalUsuarios();
        return respuesta;
    }
    
    /* Metodo que se utiliza para  consultar un usuario especifico en la BD  
        @Param JsonRequest [{"idUsuario": 10000, "nombre":"","telefono":""},{"idUsuario": 20000, "nombre":"","telefono":""}]
    */
    
    @RequestMapping(value="/usuario", method=RequestMethod.POST)
    public String obtenerUsuarioById(@RequestBody String jSonRequest){
        UserModel model= new UserModel();
        String respuesta = model.obtenerUsuariosByID(jSonRequest);
        return respuesta;
    }
    
     /* Metodo que se utiliza para crear un usuario especifico en la BD  
        @Param JsonRequest [{"nombre":"WebServicePrueba","telefono":"1234567", "notificacion":"SMS"}]
    */   
    @RequestMapping(value="/usuario/add", method=RequestMethod.POST)
    public String insertarUsuario(@RequestBody String jSonRequest){
        UserModel model= new UserModel();
        String respuesta = model.insertarUsuario(jSonRequest);
        if(respuesta.equalsIgnoreCase("true"))
            respuesta= model.obtenerUsuariosByID(jSonRequest);
        return respuesta;
    }
    
    
     /* Metodo que se utiliza para modificar un usuario especifico en la BD  
        @Param JsonRequest [{"idusuario" : 20000,"nombre":"WebServicePrueba","telefono":"1234567", "notificacion":"SMS"}]
    */   
    @RequestMapping(value="/usuario/edit", method=RequestMethod.PUT)
    public String modificarUsuario(@RequestBody String jSonRequest){
        UserModel model= new UserModel();
        String respuesta = model.modificarUsuario(jSonRequest);
        return respuesta;
    }
    
    
     /* Metodo que se utiliza para eliminar un usuario especifico en la BD  
        @Param JsonRequest [{"idusuario" : 20000, "nombre":null,"telefono":null, "notificacion":null}]
    */   
    @RequestMapping(value="/usuario/delete", method=RequestMethod.DELETE)
    public String eliminarUsuario(@RequestBody String jSonRequest){
        UserModel model= new UserModel();
        String respuesta = model.eliminarUsuario(jSonRequest);
        return respuesta;
    }
}
