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
import org.una.examen.cliente.dto.TipoServicioDTO;
import org.una.examen.cliente.util.Respuesta;

/**
 *
 * @author Luis
 */
public class TipoServicioService {
        public Respuesta crear(TipoServicioDTO tipoServicio){
        try{
            ConexionServidor conexion = new ConexionServidor(1,"tipoServicio/crear");
            conexion.post(tipoServicio);
            if(conexion.isError()){
                System.out.println("Error creacion del tipo servicio: "+conexion.getError());
                return new Respuesta(false, conexion.getError(), "No se pudo crear el tipo servicio");
            }
            TipoServicioDTO result = (TipoServicioDTO) conexion.readEntity(TipoServicioDTO.class);
            return new Respuesta(true, "TipoServicio", result);
        }catch(Exception ex){
            System.out.println(ex.toString());
            System.out.println("Excepcion creacion de tipo servicio: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta modificar(Long id, TipoServicioDTO tipoServicio){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(1,"tipoServicio/modificar", "/{id}", parametros);
            conexion.put(tipoServicio);
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "No se pudo modificar el tipo servicio");
            }
            TipoServicioDTO result = (TipoServicioDTO) conexion.readEntity(TipoServicioDTO.class);
            return new Respuesta(true, "TipoServicio", result);
        }catch(Exception ex){
            System.out.println("Excepcion modificacion del tipo servicio: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getById(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(1,"tipoServicio", "/{id}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar del tipo servicio por su id");
            }
            TipoServicioDTO result = (TipoServicioDTO) conexion.readEntity(TipoServicioDTO.class);
            return new Respuesta(true, "TipoServicio",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getAll(){
        try{
            ConexionServidor conexion = new ConexionServidor(1,"tipoServicio/");
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar todos los tipos servicios");
            }
            List<TipoServicioDTO> result = (List<TipoServicioDTO>) conexion.readEntity(new GenericType<List<TipoServicioDTO>>(){});
            return new Respuesta(true, "TiposServicios",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
}
