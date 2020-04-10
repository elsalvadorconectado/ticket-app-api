package com.devs.ticketapp.controller;

import org.springframework.web.bind.annotation.RestController;


import com.devs.ticketapp.dao.TIcketModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TicketController {

    @RequestMapping(value="/ticket/list", method=RequestMethod.GET)
    public String obtenerTotalColas(){
        TIcketModel model= new TIcketModel();
        String respuesta = model.obtenerTotalTickets();
        return respuesta;
    }
    

    
    @RequestMapping(value="/ticket", method=RequestMethod.POST)
    public String obtenerColaById(@RequestBody String jSonRequest){
        TIcketModel model= new TIcketModel();
        String respuesta = model.obtenerTicketByID(jSonRequest);
        return respuesta;
    }
     
    @RequestMapping(value="/ticket/add", method=RequestMethod.POST)
    public String insertarCola(@RequestBody String jSonRequest){
        TIcketModel model= new TIcketModel();
        String respuesta = model.insertarTicket(jSonRequest);
        return respuesta;
    }
    
    
     /* Metodo que se utiliza para modificar un ticket especifico en la BD  
        @Param JsonRequest [{"idticket": 3,"idusuario": null,"idcola": null, "posicion": 7, "estado": 6, "generaren":"2020/04/02","inicioturno":"2020/04/02","vencimiento":"2020/04/02","atendidoen":"2020/04/02","ultimanotif":"2020/04/02"}]
    */   
    @RequestMapping(value="/ticket/edit", method=RequestMethod.PUT)
    public String modificarCola(@RequestBody String jSonRequest){
        TIcketModel model= new TIcketModel();
        String respuesta = model.modificarTicket(jSonRequest);
        return respuesta;
    }
    
    
    @RequestMapping(value="/ticket/delete", method=RequestMethod.DELETE)
    public String eliminarCola(@RequestBody String jSonRequest){
        TIcketModel model= new TIcketModel();
        String respuesta = model.eliminarTicket(jSonRequest);
        return respuesta;
    }

}