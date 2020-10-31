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
import org.una.examen.cliente.dto.CantonDTO;
import org.una.examen.cliente.util.Request;
import org.una.examen.cliente.util.Respuesta;

/**
 *
 * @author Jeffry
 */
public class CantonService {
    public Respuesta crear(CantonDTO canton){
        try{
            ConexionServidor conexion = new ConexionServidor(2,"cantones/crear");
            conexion.post(canton);
            if(conexion.isError()){
                System.out.println("Error creacion de cantón: "+conexion.getError());
                return new Respuesta(false, conexion.getError(), "No se pudo crear el cantón");
            }
            CantonDTO result = (CantonDTO) conexion.readEntity(CantonDTO.class);
            return new Respuesta(true, "Canton", result);
        }catch(Exception ex){
            System.out.println("Excepcion creacion de cantón: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta modificar(Long id, CantonDTO canton){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(2,"cantones/modificar", "/{id}", parametros);
            conexion.put(canton);
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "No se pudo modificar el cantón");
            }
            CantonDTO result = (CantonDTO) conexion.readEntity(CantonDTO.class);
            return new Respuesta(true, "Canton", result);
        }catch(Exception ex){
            System.out.println("Excepcion modificacion de canton: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getById(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(2,"cantones", "/{id}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar el cantón por su id");
            }
            CantonDTO result = (CantonDTO) conexion.readEntity(CantonDTO.class);
            return new Respuesta(true, "Canton",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByCodigo(int codigo){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", codigo);
            ConexionServidor conexion = new ConexionServidor(2,"cantones", "/{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar el cantón por su codigo");
            }
            CantonDTO result = (CantonDTO) conexion.readEntity(CantonDTO.class);
            return new Respuesta(true, "Canton",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByNombre(String nombre){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", nombre);
            ConexionServidor conexion = new ConexionServidor(2,"cantones/list/nombre", "{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar el cantón por nombre");
            }
            List<CantonDTO> result = (List<CantonDTO>) conexion.readEntity(new GenericType<List<CantonDTO>>(){});
            return new Respuesta(true, "Cantones",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByProvincia(Long provincia){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", provincia);
            ConexionServidor conexion = new ConexionServidor(2,"cantones/list/provincia/", "{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar el cantón por provincia");
            }
            List<CantonDTO> result = (List<CantonDTO>) conexion.readEntity(new GenericType<List<CantonDTO>>(){});
            return new Respuesta(true, "Cantones",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getAll(){
        try{
            ConexionServidor conexion = new ConexionServidor(2,"cantones/");
            conexion.get();
            if(conexion.isError()){
                System.out.println("coneccion error");
                return new Respuesta(false, conexion.getError(), "Error al buscar todos los cantones");
            }
            List<CantonDTO> result = (List<CantonDTO>) conexion.readEntity(new GenericType<List<CantonDTO>>(){});
            return new Respuesta(true, "Cantones",result);
        }catch(Exception ex){
            System.out.println("coneccion error 2");
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta delete(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("cantones", "/{id}", parametros);
            request.delete();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "No se pudo eliminar el cantón");
            }
            return new Respuesta(true, "El cantón fue eliminado exitosamente");
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No puedo establecerce conexion con el servidor");
        }
    }
}
