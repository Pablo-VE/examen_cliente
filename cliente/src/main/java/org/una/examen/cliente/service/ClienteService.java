/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.service;

import java.util.List;
import javax.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.Map;
import org.una.examen.cliente.conexion.ConexionServidor;
import org.una.examen.cliente.dto.ClienteDTO;
import org.una.examen.cliente.util.Respuesta;

/**
 *
 * @author Luis
 */
public class ClienteService {
    public Respuesta crear(ClienteDTO cliente){
        try{
            ConexionServidor conexion = new ConexionServidor(3,"clientes/crear");
            conexion.post(cliente);
            if(conexion.isError()){
                System.out.println("Error creacion del cliente: "+conexion.getError());
                return new Respuesta(false, conexion.getError(), "No se pudo crear el cliente");
            }
            ClienteDTO result = (ClienteDTO) conexion.readEntity(ClienteDTO.class);
            return new Respuesta(true, "Cliente", result);
        }catch(Exception ex){
            System.out.println(ex.toString());
            System.out.println("Excepcion creacion del cliente: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta modificar(Long id, ClienteDTO cliente){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(3,"clientes/modificar", "/{id}", parametros);
            conexion.put(cliente);
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "No se pudo modificar el cliente");
            }
            ClienteDTO result = (ClienteDTO) conexion.readEntity(ClienteDTO.class);
            return new Respuesta(true, "Cliente", result);
        }catch(Exception ex){
            System.out.println("Excepcion modificacion del cliente: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
     public Respuesta getById(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(3,"clientes", "/{id}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar el cliente por su id");
            }
            ClienteDTO result = (ClienteDTO) conexion.readEntity(ClienteDTO.class);
            return new Respuesta(true, "Cliente",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getAll(){
        try{
            ConexionServidor conexion = new ConexionServidor(3,"clientes/");
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar todos los proyectos");
            }
            List<ClienteDTO> result = (List<ClienteDTO>) conexion.readEntity(new GenericType<List<ClienteDTO>>(){});
            return new Respuesta(true, "Clientes",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getByNombre(String nombre){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("term", nombre);
            ConexionServidor conexion = new ConexionServidor(3,"clientes/list/nombre", "/{term}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar los clientes por el nombre");
            }
            List<ClienteDTO> result = (List<ClienteDTO>) conexion.readEntity(new GenericType<List<ClienteDTO>>(){});
            return new Respuesta(true, "Clientes",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
   
}
