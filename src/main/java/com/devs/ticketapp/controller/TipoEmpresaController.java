/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.ticketapp.controller;

import com.devs.ticketapp.dao.ColaModel;
import com.devs.ticketapp.dao.EmpresaModel;
import com.devs.ticketapp.dao.TipoEmpresaModel;
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
public class TipoEmpresaController {
     
    
    
    @RequestMapping(value="/tipoempresa/list", method=RequestMethod.GET)
    public String obtenerTotalTipoEmpresa(){
        TipoEmpresaModel model= new TipoEmpresaModel();
        String respuesta = model.obtenerTotalTipoEmpresas();
        return respuesta;
    }
    

    
    @RequestMapping(value="/tipoempresa", method=RequestMethod.POST)
    public String obtenerTipoEmpresaById(@RequestBody String jSonRequest){
        TipoEmpresaModel model= new TipoEmpresaModel();
        String respuesta = model.obtenerTipoEmpresaByID(jSonRequest);
        return respuesta;
    }
     
    @RequestMapping(value="/tipoempresa/add", method=RequestMethod.POST)
    public String insertarTipoEmpresa(@RequestBody String jSonRequest){
        TipoEmpresaModel model= new TipoEmpresaModel();
        String respuesta = model.insertarTipoEmpresa(jSonRequest);
        return respuesta;
    }
    
  
    @RequestMapping(value="/tipoempresa/edit", method=RequestMethod.PUT)
    public String modificarTipoEmpresa(@RequestBody String jSonRequest){
        TipoEmpresaModel model= new TipoEmpresaModel();
        String respuesta = model.modificarTipoEmpresa(jSonRequest);
        return respuesta;
    }
    
    
    @RequestMapping(value="/tipoempresa/delete", method=RequestMethod.DELETE)
    public String eliminarTipoEmpresa(@RequestBody String jSonRequest){
        TipoEmpresaModel model= new TipoEmpresaModel();
        String respuesta = model.eliminarTipoEmpresa(jSonRequest);
        return respuesta;
    }
}
