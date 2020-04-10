package com.devs.ticketapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RolModel extends CommonsDAO{
    /*
    *Query que retorna todos los objetos JSON de la tabla rol
    *[{"id": 1, "name":"ADMIN", "status":"Activo"}] 
    */
    public String obtenerTotalRoles() {
        String result="";
        String sql = "SELECT\n"+
                        "json_agg(\n"+
                            "json_build_object(\n"+
                                "'id', r.id,\n"+
                                "'name', r.name,\n"+
                                "'status', r.status\n"+
                            ")\n"+
                        ") as resultado\n"+
                    "FROM rol r";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
             Statement statement = conn.createStatement()) {
                System.err.println("El sql generado es : "+ sql);
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
        
     
    /*
    * Query que retornara el objeto  JSON a partir de un id
    * /*
    *Query que retorna el objeto JSON de la tabla rol
    *[{"id": 1, "name":"ADMIN", "status":"Activo"}] 
    */
     
     public String obtenerRolByID(String json) {
        String result="";
        String sql = "SELECT\n"+
                        "json_agg(\n"+
                            "json_build_object(\n"+
                                "'id', r.id,\n"+
                                "'name', r.name,\n"+
                                "'status', r.status\n"+
                            ")\n"+
                        ") as resultado\n"+
                    "FROM rol r\n"+
                    "WHERE r.id in (SELECT (json_array_elements_text('"+json+"')::jsonb->'id')::int4 as id)";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                PreparedStatement ps = conn.prepareStatement(sql)) {
            
            System.err.println("El sql generado es : "+ sql);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result= resultSet.getString("resultado");
            }
            result= "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":"+result+"}";
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
     
    /*
    * Query para insertar un registro a la tabla rol
    * query que solo recibira dos parametros del objeto json
    * [{"name":"ADMIN", "status":"Activo"}] 
    * */
     
     
     public String insertarRol(String json) {
        String result="";
        String sql = "insert into rol (name, status)\n"+
                        "select trim(name) as name,\n"+
                               "trim(status) as status\n"+
                        "from json_to_recordset('"+json+"')\n"+
                        "as x(\"name\" text, \"status\" text);";

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
     
     
    /*
    * Query para actualizar el registro de la tabla rol
    * [{"id": 1, "name":"ADMIN", "status":"Activo"}] 
    *  
    * */  	
    public String modificarRol(String json) {
        String result="";
        String sql = "update rol \n"+
                        "set\n"+
                                "name = coalesce (usRecord.name, rol.name, usRecord.name ),\n"+
                                "status = coalesce (usRecord.status, rol.status , usRecord.status)\n"+
                        "from (\n"+
                            "select * from json_to_recordset('"+json+"')\n"+
                            "as x(\"id\" text,\"name\" text, \"status\" text)\n"+
                        ") as usRecord\n"+
                        "where usRecord.id::int4 = rol.id ";

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


    /*
    * Query para eliminar el registro de la tabla rol
    * [{"id": 1, "name":"ADMIN", "status":"Activo"}] 
    *  
    * */ 
     public String eliminarRol(String json) {
        String result="";
        String sql = " delete from rol r\n"+
                        "where r.id in (\n"+
                            "select id::int4 from json_to_recordset('"+json+"')\n"+
                            "as x(\"id\" text, \"name\" text, \"status\" text)\n"+
                        ")";

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
}