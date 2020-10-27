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
import org.una.examen.cliente.dto.TareaDTO;
import org.una.examen.cliente.util.Respuesta;

/**
 *
 * @author Pablo-VE
 */
public class TareaService {
    public Respuesta crear(TareaDTO tarea){
        try{
            ConexionServidor conexion = new ConexionServidor(1,"tareas/crear");
            conexion.post(tarea);
            if(conexion.isError()){
                System.out.println("Error creacion de tarea: "+conexion.getError());
                return new Respuesta(false, conexion.getError(), "No se pudo crear la tarea");
            }
            TareaDTO result = (TareaDTO) conexion.readEntity(TareaDTO.class);
            return new Respuesta(true, "Tarea", result);
        }catch(Exception ex){
            System.out.println("Excepcion creacion de tarea: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta modificar(Long id, TareaDTO tarea){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(1,"tareas/modificar", "/{id}", parametros);
            conexion.put(tarea);
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "No se pudo modificar la tarea");
            }
            TareaDTO result = (TareaDTO) conexion.readEntity(TareaDTO.class);
            return new Respuesta(true, "Tarea", result);
        }catch(Exception ex){
            System.out.println("Excepcion modificacion de tarea: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getById(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(1,"tareas", "/{id}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar la tarea por su id");
            }
            TareaDTO result = (TareaDTO) conexion.readEntity(TareaDTO.class);
            return new Respuesta(true, "Tarea",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getAll(){
        try{
            ConexionServidor conexion = new ConexionServidor(1,"tareas/");
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar todas las tareas");
            }
            List<TareaDTO> result = (List<TareaDTO>) conexion.readEntity(new GenericType<List<TareaDTO>>(){});
            return new Respuesta(true, "Tareas",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    public Respuesta getByProyecto(Long proyecto){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", proyecto);
            ConexionServidor conexion = new ConexionServidor(1,"tareas/list/proyecto", "/{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar las tareas por el proyecto");
            }
            List<TareaDTO> result = (List<TareaDTO>) conexion.readEntity(new GenericType<List<TareaDTO>>(){});
            return new Respuesta(true, "Tareas",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
}
