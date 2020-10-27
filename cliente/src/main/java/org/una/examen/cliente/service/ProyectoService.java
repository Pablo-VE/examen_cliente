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
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.util.Respuesta;

/**
 *
 * @author Pablo-VE
 */
public class ProyectoService {
    public Respuesta crear(ProyectoDTO proyecto){
        try{
            ConexionServidor conexion = new ConexionServidor(1,"proyectos/crear");
            conexion.post(proyecto);
            if(conexion.isError()){
                System.out.println("Error creacion de proyecto: "+conexion.getError());
                return new Respuesta(false, conexion.getError(), "No se pudo crear el proyecto");
            }
            ProyectoDTO result = (ProyectoDTO) conexion.readEntity(ProyectoDTO.class);
            return new Respuesta(true, "Proyecto", result);
        }catch(Exception ex){
            System.out.println("Excepcion creacion de proyecto: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta modificar(Long id, ProyectoDTO proyecto){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(1,"proyectos/modificar", "/{id}", parametros);
            conexion.put(proyecto);
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "No se pudo modificar el proyecto");
            }
            ProyectoDTO result = (ProyectoDTO) conexion.readEntity(ProyectoDTO.class);
            return new Respuesta(true, "Proyecto", result);
        }catch(Exception ex){
            System.out.println("Excepcion modificacion de proyecto: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getById(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(1,"proyectos", "/{id}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar el proyecto por su id");
            }
            ProyectoDTO result = (ProyectoDTO) conexion.readEntity(ProyectoDTO.class);
            return new Respuesta(true, "Proyecto",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getAll(){
        try{
            ConexionServidor conexion = new ConexionServidor(1,"proyectos/");
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar todos los proyectos");
            }
            List<ProyectoDTO> result = (List<ProyectoDTO>) conexion.readEntity(new GenericType<List<ProyectoDTO>>(){});
            return new Respuesta(true, "Proyectos",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    public Respuesta getByNombre(String nombre){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", nombre);
            ConexionServidor conexion = new ConexionServidor(1,"proyectos/list/nombre", "/{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar los proyectos por el nombre");
            }
            List<ProyectoDTO> result = (List<ProyectoDTO>) conexion.readEntity(new GenericType<List<ProyectoDTO>>(){});
            return new Respuesta(true, "Proyectos",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByResponsable(String responsable){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", responsable);
            ConexionServidor conexion = new ConexionServidor(1,"proyectos/list/responsable", "/{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar los proyectos por el responsable");
            }
            List<ProyectoDTO> result = (List<ProyectoDTO>) conexion.readEntity(new GenericType<List<ProyectoDTO>>(){});
            return new Respuesta(true, "Proyectos",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
}
