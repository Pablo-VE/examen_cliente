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
import org.una.examen.cliente.dto.ProvinciaDTO;
import org.una.examen.cliente.util.Request;
import org.una.examen.cliente.util.Respuesta;

/**
 *
 * @author Jeffry
 */
public class ProvinciaService {
    public Respuesta crear(ProvinciaDTO tarea){
        try{
            ConexionServidor conexion = new ConexionServidor(2 ,"provincias/crear");
            conexion.post(tarea);
            if(conexion.isError()){
                System.out.println("Error creacion de provincia: "+conexion.getError());
                return new Respuesta(false, conexion.getError(), "No se pudo crear la provincia");
            }
            ProvinciaDTO result = (ProvinciaDTO) conexion.readEntity(ProvinciaDTO.class);
            return new Respuesta(true, "Provincia", result);
        }catch(Exception ex){
            System.out.println("Excepcion creacion de provincia: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta modificar(Long id, ProvinciaDTO provincia){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(2 ,"provincias/modificar", "/{id}", parametros);
            conexion.put(provincia);
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "No se pudo modificar la provincia");
            }
            ProvinciaDTO result = (ProvinciaDTO) conexion.readEntity(ProvinciaDTO.class);
            return new Respuesta(true, "Provincia", result);
        }catch(Exception ex){
            System.out.println("Excepcion modificacion de provincia: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getById(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(2 ,"provincias", "/{id}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar la provincia por su id");
            }
            ProvinciaDTO result = (ProvinciaDTO) conexion.readEntity(ProvinciaDTO.class);
            return new Respuesta(true, "Provincia",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByCodigo(int codigo){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", codigo);
            ConexionServidor conexion = new ConexionServidor(2 ,"provincias/", "{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar la provincia por su codigo");
            }
            ProvinciaDTO result = (ProvinciaDTO) conexion.readEntity(ProvinciaDTO.class);
            return new Respuesta(true, "Provincia",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByNombre(String nombre){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", nombre);
            ConexionServidor conexion = new ConexionServidor(2 ,"provincias/list/nombre", "{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar las provincias por nombre");
            }
            List<ProvinciaDTO> result = (List<ProvinciaDTO>) conexion.readEntity(new GenericType<List<ProvinciaDTO>>(){});
            return new Respuesta(true, "Provincias",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getAll(){
        try{
            ConexionServidor conexion = new ConexionServidor(2, "provincias/");
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar todas las provincias");
            }
            List<ProvinciaDTO> result = (List<ProvinciaDTO>) conexion.readEntity(new GenericType<List<ProvinciaDTO>>(){});
            return new Respuesta(true, "Provincias",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta delete(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("provincias", "/{id}", parametros);
            request.delete();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "No se pudo eliminar la provincia");
            }
            return new Respuesta(true, "La provincia fue eliminada exitosamente");
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerse conexion con el servidor");
        }
    }
}
