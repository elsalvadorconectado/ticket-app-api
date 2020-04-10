package com.devs.ticketapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EstablecimientoModel extends CommonsDAO {

    /*
 *	Query para obtener todos los establecimientos para la tabla empresa.
 *
 * 
 * 	Query de JsonResponse de todos los establecimientos
 * 
 *  estructura de JsonResponse
 * 
 * 	[{'idestablecimiento': 1,'idempresa': null, 'nomestablecimiento': Super Selecto Soyapango, 'latitud': 10, 'longitud': 10}]
 **/

    public String obtenerTotalEstablecimientos() {
        String result = "";
        String sql = "select \n"
                + "json_agg(\n"
                + "json_build_object(\n"
                + "'idestablecimiento', e2.id_establecimiento,\n"
                + "'idempresa', e2.id_empresa,\n"
                + "'nomestablecimiento', e2.nombre_establecimiento, \n"
                + "'direccion1', e2.direccion1,\n"
                + "'direccion2', e2.direccion2,\n"
                + "'latitud', e2.latitud,\n"
                + "'longitud', e2.longitud\n"
                + ")\n"
                + ") as resultado\n"
                + "FROM establecimiento e2 ";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result = resultSet.getString("resultado");
            }
            if (result == null) {
                result = "{\"header\":" + obtenerCabecera(CODE_NOT_RECORDS, MSG_NOT_RECORDS) + ",\"data\":" + result + "}";
            } else {
                result = "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":" + result + "}";
            }
            resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        } catch (Exception e) {
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        }
        return result;
    }

    /* Query para obtener todos los establecimientos en base al arreglo de usuarios JSON que espera como parametro 
 * El valor de un id de establecimiento 
 * 
 *[{"idestablecimiento":2,"idempresa":null,"nomestablecimiento":"Super Selecto Soyapanfo","direccion1":"Soyapango","latitud":10,"longitud":10}]
 * 
 * */
    public String obtenerEstablecimientoID(String json) {
        String result = "";
        String sql = "select\n"
                + "json_agg(\n"
                + "json_build_object(\n"
                + "'idestablecimiento', e2.id_establecimiento,\n"
                + "'idempresa', e2.id_empresa,\n"
                + "'nomestablecimiento', e2.nombre_establecimiento, \n"
                + "'direccion1', e2.direccion1,\n"
                + "'direccion2', e2.direccion2,\n"
                + "'latitud', e2.latitud,\n"
                + "'longitud', e2.longitud\n"
                + ")\n"
                + ") as resultado\n"
                + "FROM establecimiento e2 \n"
                + "where e2.id_establecimiento  in (select  (json_array_elements_text('" + json + "')::jsonb->'idestablecimiento')::int4 as id)";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                PreparedStatement ps = conn.prepareStatement(sql)) {

            //System.err.println("El sql generado es : "+ sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("resultado");
            }
            result = "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":" + result + "}";
            resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        } catch (Exception e) {
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        }
        return result;
    }

    public String obtenerColaEstablecimientoID(String json) {
        String result = "";
        String sql = "select\n"
                + "	json_agg(\n"
                + "    json_build_object(\n"
                + "        'idcola', c.id_cola ,\n"
                + "        'descripcion', c.descripcion ,\n"
                + "        'idestablecimiento', c.id_establecimiento ,\n"
                + "        'cupos', c.cupos ,\n"
                + "        'ultimoatendido', c.ultimo_atendido \n"
                + "    )\n"
                + "    ) as resultado\n"
                + "FROM cola c\n"
                + "where c.id_establecimiento  in (select  (json_array_elements_text('" + json + "')::jsonb->'idestablecimiento')::int4 as id)";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                PreparedStatement ps = conn.prepareStatement(sql)) {

            //System.err.println("El sql generado es : "+ sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("resultado");
            }
            result = "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":" + result + "}";
            resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        } catch (Exception e) {
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        }
        return result;
    }

    /* Query para insertar el establecimiento enviado como parametro en un arreglo Json 
 * 
 * Ejemplo de JsonRequest
 * 
 * [{"idempresa":null,"nomestablecimiento":"Super Selecto Soyapanfo","direccion1":"Soyapango","direccion2":"Plaza Mundo","latitud":10,"longitud":10}]
 * */
    public String insertarEstablecimiento(String json) {
        String result = "";
        String sql = "insert into establecimiento (id_empresa , nombre_establecimiento , direccion1 , direccion2, latitud ,longitud )\n"
                + "select idempresa::int4 , \n"
                + "trim(nomestablecimiento ) as nombre_establecimiento , \n"
                + "trim(direccion1) as direccion1, \n"
                + "trim(direccion2)as direccion2,\n"
                + "latitud::numeric,\n"
                + "longitud::numeric from   json_to_recordset('" + json + "')\n"
                + "as x(\"idempresa\" text,\"nomestablecimiento\" text,\"direccion1\" text,\"direccion2\" text,\"latitud\" text,\"longitud\" text);";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                PreparedStatement ps = conn.prepareStatement(sql)) {

            //System.err.println("El sql generado es : "+ sql);
            ps.executeUpdate();

            result = "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":[" + result + "]}";
            //resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        } catch (Exception e) {
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        }
        return result;
    }

    /* Query para modificar el establecimiento enviado como parametro en un arreglo Json 
 * 
 * Ejemplo de JsonRequest
 * [{"idestablecimiento":2,"idempresa":null,"nomestablecimiento":"Super Selecto Soyapanfo","direccion1":"Soyapango","direccion2":"Plaza Mundo","latitud":10,"longitud":10}]
 * 
 * */
    public String modificarEstablecimiento(String json) {
        String result = "";
        String sql = "UPDATE establecimiento \n"
                + "SET id_empresa = coalesce (usRecord.idempresa::int4, establecimiento.id_empresa , usRecord.idempresa::int4), \n"
                + "nombre_establecimiento = coalesce (usRecord.nomestablecimiento , establecimiento.nombre_establecimiento , usRecord.nomestablecimiento),\n"
                + "direccion1 = coalesce (usRecord.direccion1,establecimiento.direccion1, usRecord.direccion1),\n"
                + "direccion2 = coalesce (usRecord.direccion2,establecimiento.direccion2, usRecord.direccion2),\n"
                + "latitud = coalesce (usRecord.latitud::numeric, establecimiento.latitud , usRecord.latitud::numeric),\n"
                + "longitud = coalesce (usRecord.longitud::numeric, establecimiento.longitud , usRecord.longitud::numeric)\n"
                + "FROM (\n"
                + "select * from   json_to_recordset('" + json + "')\n"
                + "as x(\"idestablecimiento\" text,\"idempresa\" text,\"nomestablecimiento\" text,\"direccion1\" text,\"direccion2\" text,\"latitud\" text,\"longitud\" text)\n"
                + ") AS usRecord\n"
                + "WHERE usRecord.idestablecimiento::int4 = establecimiento.id_establecimiento ";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                PreparedStatement ps = conn.prepareStatement(sql)) {

            System.err.println("El sql generado es : " + sql);

            ps.executeUpdate();

            result = "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":[" + result + "]}";
            //resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        } catch (Exception e) {
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        }
        return result;
    }

    /* Query para eliminar el establecimiento enviado como parametro en un arreglo Json 
 * 
 * Ejemplo de JsonRequest
 * [{"idestablecimiento":"1","idempresa":"null","nomestablecimiento":"Super Selecto Soyapanfo","direccion1":"Soyapango","direccion2":"Plaza Mundo","latitud":"10.10","longitud":"10.10"}]
 * 
 * */
    public String eliminarEstablecimiento(String json) {
        String result = "";
        String sql = "   delete from establecimiento e \n"
                + "where e.id_establecimiento in (\n"
                + "select idestablecimiento::int4 from json_to_recordset('" + json + "')\n"
                + "as x(\"idestablecimiento\" text,\"idempresa\" text,\"nomestablecimiento\" text,\"direccion1\" text,\"direccion2\" text,\"latitud\" text,\"longitud\" text)\n"
                + ") ";

        try (Connection conn = DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
                PreparedStatement ps = conn.prepareStatement(sql)) {

            System.err.println("El sql generado es : " + sql);

            ps.executeUpdate();

            result = "{\"header\":" + obtenerCabecera(CODE_SUCCESS, MSG_SUCESS) + ",\"data\":[" + result + "]}";
            //resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.err.format("Ups, ocurrio un error SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        } catch (Exception e) {
            result = "{\"header\":" + obtenerCabecera(CODE_ERROR, MSG_ERROR + ": " + e.getMessage()) + ",\"data\":[" + result + "]}";
        }
        return result;
    }
}
