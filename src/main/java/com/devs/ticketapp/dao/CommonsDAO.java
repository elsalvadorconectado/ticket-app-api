/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.ticketapp.dao;

import com.devs.ticketapp.dto.HeaderMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author azus
 */
public abstract class CommonsDAO {
    
    
    protected Integer CODE_SUCCESS =1;
    protected String  MSG_SUCESS= "Transaccion realizada exitosamente";
    protected Integer CODE_ERROR =0;
    protected String  MSG_ERROR= "Ocurrio un error en la transaccion"; 
    
    protected Integer CODE_NOT_RECORDS =-1;
    protected String  MSG_NOT_RECORDS= "No se encontraron registros";
    
    /* Se agregan todos los codigos y mensajes que se van a retornar*/
   
    private String url= "jdbc:postgresql://bd.idea4.quenecesito.org:1003/ticketapptest";
   
    private String user="ticketuser";
    
    private String password="T!3537App";
    
    
    
    public String obtenerCabecera(Integer code, String msg){
        ObjectMapper mapper = new ObjectMapper();
        String resultado="";
        HeaderMsg cabecera= new HeaderMsg();
        cabecera.setCode(code);
        cabecera.setMsg(msg);
         try {
            resultado= mapper.writeValueAsString(cabecera);
         } catch (JsonProcessingException ex) {
             resultado="{\"code\":0,\"msg\":\"Error en la conversion del objeto a Json\"}";
         }
         return resultado;
    }
    

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
