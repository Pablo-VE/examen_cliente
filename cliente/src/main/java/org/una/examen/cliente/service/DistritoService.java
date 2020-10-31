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
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.util.Request;
import org.una.examen.cliente.util.Respuesta;

/**
 *
 * @author Jeffry
 */
public class DistritoService {
    public Respuesta crear(DistritoDTO tarea){
        try{
            ConexionServidor conexion = new ConexionServidor(2,"distritos/crear");
            conexion.post(tarea);
            if(conexion.isError()){
                System.out.println("Error creacion de cantón: "+conexion.getError());
                return new Respuesta(false, conexion.getError(), "No se pudo crear el distrito");
            }
            DistritoDTO result = (DistritoDTO) conexion.readEntity(DistritoDTO.class);
            return new Respuesta(true, "Distrito", result);
        }catch(Exception ex){
            System.out.println("Excepcion creacion de distrito: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta modificar(Long id, DistritoDTO tarea){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(2,"distritos/modificar", "/{id}", parametros);
            conexion.put(tarea);
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "No se pudo modificar el distrito");
            }
            DistritoDTO result = (DistritoDTO) conexion.readEntity(DistritoDTO.class);
            return new Respuesta(true, "Distrito", result);
        }catch(Exception ex){
            System.out.println("Excepcion modificacion de distrito: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getById(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(2,"distritos", "/{id}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar el distrito por su id");
            }
            DistritoDTO result = (DistritoDTO) conexion.readEntity(DistritoDTO.class);
            return new Respuesta(true, "Distrito",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByCodigo(int codigo){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", codigo);
            ConexionServidor conexion = new ConexionServidor(2,"distritos", "/{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar el distrito por su codigo");
            }
            DistritoDTO result = (DistritoDTO) conexion.readEntity(DistritoDTO.class);
            return new Respuesta(true, "Distrito",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByNombre(String nombre){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", nombre);
            ConexionServidor conexion = new ConexionServidor(2,"distritos/", "{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar el distrito por nombre");
            }
            List<DistritoDTO> result = (List<DistritoDTO>) conexion.readEntity(new GenericType<List<DistritoDTO>>(){});
            return new Respuesta(true, "Distritos",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByCanton(Long canton){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", canton);
            ConexionServidor conexion = new ConexionServidor(2,"distritos/", "{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar los distrito por cantón");
            }
            List<DistritoDTO> result = (List<DistritoDTO>) conexion.readEntity(new GenericType<List<DistritoDTO>>(){});
            return new Respuesta(true, "Distritos",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getAll(){
        try{
            ConexionServidor conexion = new ConexionServidor(2,"distritos/");
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar todos los distritos");
            }
            List<DistritoDTO> result = (List<DistritoDTO>) conexion.readEntity(new GenericType<List<DistritoDTO>>(){});
            return new Respuesta(true, "Distritos",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta delete(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("distritos", "/{id}", parametros);
            request.delete();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "No se pudo eliminar el distrito");
            }
            return new Respuesta(true, "El distrito fue eliminado exitosamente");
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
}
