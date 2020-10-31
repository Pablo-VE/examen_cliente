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
import org.una.examen.cliente.dto.UnidadDTO;
import org.una.examen.cliente.util.Request;
import org.una.examen.cliente.util.Respuesta;

/**
 *
 * @author Jeffry
 */
public class UnidadService {
    public Respuesta crear(UnidadDTO tarea){
        try{
            ConexionServidor conexion = new ConexionServidor(2,"unidades/crear");
            conexion.post(tarea);
            if(conexion.isError()){
                System.out.println("Error creacion de cantón: "+conexion.getError());
                return new Respuesta(false, conexion.getError(), "No se pudo crear la unidad");
            }
            UnidadDTO result = (UnidadDTO) conexion.readEntity(UnidadDTO.class);
            return new Respuesta(true, "Unidad", result);
        }catch(Exception ex){
            System.out.println("Excepcion creacion de unidad: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta modificar(Long id, UnidadDTO tarea){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(2,"unidades/modificar", "/{id}", parametros);
            conexion.put(tarea);
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "No se pudo modificar la unidad");
            }
            UnidadDTO result = (UnidadDTO) conexion.readEntity(UnidadDTO.class);
            return new Respuesta(true, "Unidad", result);
        }catch(Exception ex){
            System.out.println("Excepcion modificacion de unidad: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getById(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(2,"unidades", "/{id}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar la unidad por su id");
            }
            UnidadDTO result = (UnidadDTO) conexion.readEntity(UnidadDTO.class);
            return new Respuesta(true, "Unidad",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByCodigo(int codigo){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", codigo);
            ConexionServidor conexion = new ConexionServidor(2,"unidades", "/{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar la unidad por su codigo");
            }
            UnidadDTO result = (UnidadDTO) conexion.readEntity(UnidadDTO.class);
            return new Respuesta(true, "Unidad",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByNombre(String nombre){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", nombre);
            ConexionServidor conexion = new ConexionServidor(2,"unidades/", "{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar la unidad por nombre");
            }
            List<UnidadDTO> result = (List<UnidadDTO>) conexion.readEntity(new GenericType<List<UnidadDTO>>(){});
            return new Respuesta(true, "Unidades",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByDistrito(Long distrito){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", distrito);
            ConexionServidor conexion = new ConexionServidor(2,"unidades/list/distrito", "/{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar las unidades por distrito");
            }
            List<UnidadDTO> result = (List<UnidadDTO>) conexion.readEntity(new GenericType<List<UnidadDTO>>(){});
            return new Respuesta(true, "Unidades",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getAll(){
        try{
            ConexionServidor conexion = new ConexionServidor(2,"unidades/");
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar todas las unidades");
            }
            List<UnidadDTO> result = (List<UnidadDTO>) conexion.readEntity(new GenericType<List<UnidadDTO>>(){});
            return new Respuesta(true, "Unidades",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta delete(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("unidades", "/{id}", parametros);
            request.delete();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "No se pudo eliminar la unidad");
            }
            return new Respuesta(true, "La unidad fue eliminada exitosamente");
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
}
