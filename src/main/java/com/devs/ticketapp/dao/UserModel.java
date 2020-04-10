/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.ticketapp.dao;

import com.devs.ticketapp.dto.HeaderMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author azus
 */
public class UserModel extends CommonsDAO{
    
    
/* Query para obtener todos los usuarios de la tabla 
 * 
 * 
 * Ejemplo de JsonResponse
 * 
 * [{"idusuario" : 20000, "nombre" : "prueba2", "telefono" : "1111111 ", "notificacion" : "cel"}, {"idusuario" : 10000, "nombre" : "prueba", "telefono" : "7777777 ", "notificacion" : null}]
 * 
 * */   
    
     public String obtenerTotalUsuarios() {
        String result="";
        String sql = "select\n" +
                "	json_agg(\n" +
                "    json_build_object(\n" +
                "        'idusuario', u.id_usuario ,\n" +
                "        'nombre', u.nombre ,\n" +
                "        'telefono', u.telefono, \n" +
                "        'notificacion', u.tipo_notif,\n" +
                "        'empresa', u.id_empresa ,\n" +
                "        'email', u.email \n" +
                "    )\n" +
                "    ) as resultado\n" +
                "FROM usuario u";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
             Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result= resultSet.getString("resultado");
            }
            if(result == null){
                result= "{\"header\":" + obtenerCabecera(CODE_NOT_RECORDS, MSG_NOT_RECORDS) + ",\"data\":"+result+"}";
            }
            else{
                result= "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":"+result+"}";
            }
            resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        } catch (Exception e) {
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        }
        return result;
    }
        
     
/* Query para obtener todos los usuarios en base al arreglo de usuarios JSON que espera como parametro 
 * 
 * Ejemplo de JsonRequest
 * [{"idusuario": 10000, "nombre":"Prueba","telefono":null},{"idusuario": 20000, "nombre":null,"telefono":null}]
 * 
 * Ejemplo de JsonResponse
 * 
 * [{"idusuario" : 20000, "nombre" : "prueba2", "telefono" : "1111111 ", "notificacion" : "cel"}, {"idusuario" : 10000, "nombre" : "prueba", "telefono" : "7777777 ", "notificacion" : ""}]
 * 
 * */    
     
     public String obtenerUsuariosByID(String json) {
        String result="";
        String sql = "select\n" +
                "	json_agg(\n" +
                "    json_build_object(\n" +
                "        'idusuario', u.id_usuario ,\n" +
                "        'nombre', u.nombre ,\n" +
                "        'telefono', u.telefono,\n" +
                "        'notificacion', u.tipo_notif,\n" +
                "        'empresa', u.id_empresa ,\n" +
                "        'email', u.email \n" +
                "    )\n" +
                "    ) as resultado\n" +
                "FROM usuario u\n" +
                "where u.id_usuario  in (select idusuario::int4 from  json_to_recordset('"+json+"')\n" +
                "		as x(\"idusuario\" text, \"nombre\" text, \"telefono\" text, \"notificacion\" text, \"pass\" text, \"rol\" text, \"empresa\" text , \"email\" text))\n" +
                "or u.\"password\"  in (select pass from  json_to_recordset('"+json+"')\n" +
                "		as x(\"idusuario\" text, \"nombre\" text, \"telefono\" text, \"notificacion\" text, \"pass\" text, \"rol\" text, \"empresa\" text , \"email\" text))\n" +
                "or u.id_rol in (select rol::int4 from  json_to_recordset('"+json+"')\n" +
                "		as x(\"idusuario\" text, \"nombre\" text, \"telefono\" text, \"notificacion\" text, \"pass\" text, \"rol\" text, \"empresa\" text , \"email\" text))\n" +
                "or u.id_empresa  in (select empresa::int4 from  json_to_recordset('"+json+"')\n" +
                "		as x(\"idusuario\" text, \"nombre\" text, \"telefono\" text, \"notificacion\" text, \"pass\" text, \"rol\" text, \"empresa\" text , \"email\" text))\n" +
                "or u.email  in (select email from  json_to_recordset('"+json+"')\n" +
                "		as x(\"idusuario\" text, \"nombre\" text, \"telefono\" text, \"notificacion\" text, \"pass\" text, \"rol\" text, \"empresa\" text , \"email\" text))";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                PreparedStatement ps = conn.prepareStatement(sql)) {
            
            System.err.println("El sql generado es : "+ sql);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result= resultSet.getString("resultado");
            }
            if(result == null){
                result= "{\"header\":" + obtenerCabecera(CODE_NOT_RECORDS, MSG_NOT_RECORDS) + ",\"data\":"+result+"}";
            }
            else{
                result= "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":"+result+"}";
            }
            resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        } catch (Exception e) {
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        }
        return result;
    }
     

     
    /* Query para insertar el usuario enviado como parametro en un arreglo Json 
     * 
     * Ejemplo de JsonRequest
     * [{"nombre":"Prueba","telefono":"11111111", "notificacion":"SMS"}]
     * 
     * */
     
     
     public String insertarUsuario(String json) {
        String result="";
        String sql = "insert into usuario (nombre , telefono , tipo_notif, \"password\", id_rol , id_empresa , email)\n" +
                    "select trim(nombre) as nombre, trim(telefono) as telefono, \n" +
                    "trim(notificacion) as notificacion, trim(pass) as pass, rol::int4, empresa::int4, trim(email) as email \n" +
                    "from   json_to_recordset('"+json+"')\n" +
                    "		as x(\"idusuario\" text, \"nombre\" text, \"telefono\" text, \"notificacion\" text, \"pass\" text, \"rol\" text, \"empresa\" text, \"email\" text)";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                PreparedStatement ps = conn.prepareStatement(sql)) {
            
            //System.err.println("El sql generado es : "+ sql);

            ps.executeUpdate();
            
            //result= "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":["+result+"]}";
            result="true";
            //resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        } catch (Exception e) {
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        }
        return result;
    }
     
     
/* Query para modificar el usuario enviado como parametro en un arreglo Json 
 * 
 * Ejemplo de JsonRequest
 * [{"idusuario" : 20000,"nombre":"Prueba","telefono":null, "notificacion":"SMS"}]
 * 
 * */
     
     
     public String modificarUsuario(String json) {
        String result="";
        String sql = "UPDATE usuario\n" +
                "SET nombre = coalesce (usRecord.nombre, usuario.nombre, usRecord.nombre), \n" +
                "    telefono = coalesce (usRecord.telefono, usuario.telefono, usRecord.telefono),\n" +
                "    tipo_notif =coalesce (usRecord.notificacion, usuario.tipo_notif, usRecord.notificacion),\n" +
                "    \"password\" =coalesce (usRecord.pass, usuario.\"password\" , usRecord.pass),\n" +
                "    id_rol =coalesce (usRecord.rol::int4, usuario.id_rol , usRecord.rol::int4),\n" +
                "    id_empresa =coalesce (usRecord.empresa::int4, usuario.id_empresa , usRecord.empresa::int4),\n" +
                "    email =coalesce (usRecord.email, usuario.email , usRecord.email)\n" +
                "FROM (\n" +
                "select * from   json_to_recordset('"+json+"')\n" +
                "		as x(\"idusuario\" text, \"nombre\" text, \"telefono\" text, \"notificacion\" text, \"pass\" text, \"rol\" text, \"empresa\" text, \"email\" text)\n" +
                ") AS usRecord\n" +
                "WHERE \n" +
                "    usRecord.idusuario::int4 = usuario.id_usuario";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                PreparedStatement ps = conn.prepareStatement(sql)) {
            
            //System.err.println("El sql generado es : "+ sql);

            ps.executeUpdate();
            
            result= "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":["+result+"]}";
            //resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        } catch (Exception e) {
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        }
        return result;
    }
  
/* Query para eliminar el usuario enviado como parametro en un arreglo Json 
 * 
 * Ejemplo de JsonRequest
 * [{"idusuario" : 20000,"nombre":"Prueba","telefono":null, "notificacion":"SMS"}]
 * 
 * */
     
     
     public String eliminarUsuario(String json) {
        String result="";
        String sql = "   delete from usuario u \n" +
                "   where u.id_usuario in (\n" +
                "	select idusuario::int4 from   json_to_recordset('"+json+"')\n" +
                "			as x(\"idusuario\" text, \"nombre\" text, \"telefono\" text, \"notificacion\" text)\n" +
                "	)";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                PreparedStatement ps = conn.prepareStatement(sql)) {
            
            System.err.println("El sql generado es : "+ sql);

            ps.executeUpdate();
            
            result= "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":["+result+"]}";
            //resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        } catch (Exception e) {
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        }
        return result;
    }     

    public String loginUsuario(String json) {
        String result="";
        String sql = "select \n" +
            "	json_agg(\n" +
            "    json_build_object(\n" +
            "        'idusuario', u.id_usuario ,\n" +
            "        'nombre', u.nombre ,\n" +
            "        'telefono', u.telefono,\n" +
            "        'notificacion', u.tipo_notif,\n" +
            "        'empresa', u.id_empresa ,\n" +
            "        'rol', r.\"name\", \n" +
            "        'email', u.email \n" +
            "    )\n" +
            "    ) as resultado\n" +
            "from usuario u, empresa e ,rol r,\n" +
            "(select username as email, \"password\" as pass from  json_to_recordset('"+json+"')\n" +
            "		as x(\"password\" text, \"username\" text)) as usrecord\n" +
            "where u.email = usrecord.email\n" +
            "and u.\"password\" = usrecord.pass\n" +
            "and u.id_rol = r.id \n" +
            "and u.id_empresa = e.id_empresa";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                PreparedStatement ps = conn.prepareStatement(sql)) {
            
            System.err.println("El sql generado es : "+ sql);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result= resultSet.getString("resultado");
            }
            if(result == null){
                result= "false";
            }
            else{
                result= "true{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":"+result+"}";
            }
            resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        } catch (Exception e) {
            result= "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": "+e.getMessage()) + ",\"data\":["+result+"]}";
        }
        return result;
    }
     
     
}
