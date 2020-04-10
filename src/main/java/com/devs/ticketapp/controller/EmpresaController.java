/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.ticketapp.controller;

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
public class EmpresaController {
     
    
    
    @RequestMapping(value="/empresa/list", method=RequestMethod.GET)
    public String obtenerTotalEmpresas(){
        EmpresaModel model= new EmpresaModel();
        String respuesta = model.obtenerTotalEmpresas();
        return respuesta;
    }
    

    
    @RequestMapping(value="/empresa", method=RequestMethod.POST)
    public String obtenerEmpresaById(@RequestBody String jSonRequest){
        EmpresaModel model= new EmpresaModel();
        String respuesta = model.obtenerEmpresaByID(jSonRequest);
        return respuesta;
    }
     
    @RequestMapping(value="/empresa/add", method=RequestMethod.POST)
    public String insertarEmpresa(@RequestBody String jSonRequest){
        EmpresaModel model= new EmpresaModel();
        String respuesta = model.insertarEmpresa(jSonRequest);
        return respuesta;
    }
    
    
     /* Metodo que se utiliza para modificar un usuario especifico en la BD  
        @Param JsonRequest [{"idusuario" : 20000,"nombre":"WebServicePrueba","telefono":"1234567", "notificacion":"SMS"}]
    */   
    @RequestMapping(value="/empresa/edit", method=RequestMethod.PUT)
    public String modificarEmpresa(@RequestBody String jSonRequest){
        EmpresaModel model= new EmpresaModel();
        String respuesta = model.modificarEmpresa(jSonRequest);
        return respuesta;
    }
    
    
    @RequestMapping(value="/empresa/delete", method=RequestMethod.DELETE)
    public String eliminarEmpresa(@RequestBody String jSonRequest){
        EmpresaModel model= new EmpresaModel();
        String respuesta = model.eliminarEmpresa(jSonRequest);
        return respuesta;
    }
}
