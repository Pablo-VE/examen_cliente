/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;
import org.una.examen.cliente.conexion.ConexionServidor;
import org.una.examen.cliente.dto.CobroPendienteDTO;
import org.una.examen.cliente.util.Respuesta;

/**
 *
 * @author Luis
 */
public class CobroPendienteService {
       public Respuesta crear(CobroPendienteDTO cobroPendiente){
        try{
            ConexionServidor conexion = new ConexionServidor(1,"cobrosPendientes/crear");
            conexion.post(cobroPendiente);
            if(conexion.isError()){
                System.out.println("Error creacion del cobro pendiente: "+conexion.getError());
                return new Respuesta(false, conexion.getError(), "No se pudo crear el cobro pendiente");
            }
            CobroPendienteDTO result = (CobroPendienteDTO) conexion.readEntity(CobroPendienteDTO.class);
            return new Respuesta(true, "CobroPendiente", result);
        }catch(Exception ex){
            System.out.println(ex.toString());
            System.out.println("Excepcion creacion de cobro pendiente: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta modificar(Long id, CobroPendienteDTO cobroPendiente){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(1,"cobrosPendientes/modificar", "/{id}", parametros);
            conexion.put(cobroPendiente);
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "No se pudo modificar el cobro pendiente");
            }
            CobroPendienteDTO result = (CobroPendienteDTO) conexion.readEntity(CobroPendienteDTO.class);
            return new Respuesta(true, "CobroPendiente", result);
        }catch(Exception ex){
            System.out.println("Excepcion modificacion del cobro pendiente: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getById(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(1,"cobrosPendientes", "/{id}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar el cobro pendiente por su id");
            }
            CobroPendienteDTO result = (CobroPendienteDTO) conexion.readEntity(CobroPendienteDTO.class);
            return new Respuesta(true, "CobroPendiente",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getAll(){
        try{
            ConexionServidor conexion = new ConexionServidor(1,"cobrosPendientes/");
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar todos los cobros pendientes");
            }
            List<CobroPendienteDTO> result = (List<CobroPendienteDTO>) conexion.readEntity(new GenericType<List<CobroPendienteDTO>>(){});
            return new Respuesta(true, "CobrosPendientes",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
     
}
