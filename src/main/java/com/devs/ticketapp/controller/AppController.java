/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.ticketapp.controller;

import com.devs.ticketapp.dao.AppModel;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author azus
 */
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AppController {
     
    
    
    @RequestMapping(value="/app/tipoempresa", method=RequestMethod.GET)
    public String obtenerTotalTipoEmpresas(){
        AppModel model= new AppModel();
        String respuesta = model.obtenerTotalTipoEmpresas();
        return respuesta;
    }
    
    @RequestMapping(value="/app/empresa", method=RequestMethod.GET)
    public String obtenerTotalEmpresasByIdTipo(@RequestParam String tipoempresa){
        AppModel model= new AppModel();
        String respuesta = model.obtenerTotalEmpresasByIdTipo(tipoempresa);
        return respuesta;
    }

    @RequestMapping(value = "/app/sucursales", method = RequestMethod.GET)
    public String obtenerTotalSucursalesByIdEmpresa(@RequestParam String empresa){
        AppModel model = new AppModel();
        String respuesta = model.obtenerTotalSucursalesByIdTipo(empresa);
        return respuesta;
    }
}
