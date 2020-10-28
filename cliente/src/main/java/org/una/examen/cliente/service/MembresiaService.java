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
import org.una.examen.cliente.dto.MembresiaDTO;
import org.una.examen.cliente.util.Respuesta;

/**
 *
 * @author Luis
 */
public class MembresiaService {
     public Respuesta crear(MembresiaDTO membresia){
        try{
            ConexionServidor conexion = new ConexionServidor(1,"membresias/crear");
            conexion.post(membresia);
            if(conexion.isError()){
                System.out.println("Error creacion de proyecto: "+conexion.getError());
                return new Respuesta(false, conexion.getError(), "No se pudo crear la membresia");
            }
            MembresiaDTO result = (MembresiaDTO) conexion.readEntity(MembresiaDTO.class);
            return new Respuesta(true, "Membresia", result);
        }catch(Exception ex){
            System.out.println(ex.toString());
            System.out.println("Excepcion creacion de la membresia: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta modificar(Long id, MembresiaDTO membresia){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(1,"membresias/modificar", "/{id}", parametros);
            conexion.put(membresia);
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "No se pudo modificar la membresia");
            }
            MembresiaDTO result = (MembresiaDTO) conexion.readEntity(MembresiaDTO.class);
            return new Respuesta(true, "Membresia", result);
        }catch(Exception ex){
            System.out.println("Excepcion modificacion de membresia: "+ex.getMessage());
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getById(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            ConexionServidor conexion = new ConexionServidor(1,"membresias", "/{id}", parametros);
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar membresia por su id");
            }
            MembresiaDTO result = (MembresiaDTO) conexion.readEntity(MembresiaDTO.class);
            return new Respuesta(true, "Membresia",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }
    
    public Respuesta getAll(){
        try{
            ConexionServidor conexion = new ConexionServidor(1,"membresias/");
            conexion.get();
            if(conexion.isError()){
                return new Respuesta(false, conexion.getError(), "Error al buscar todos los proyectos");
            }
            List<MembresiaDTO> result = (List<MembresiaDTO>) conexion.readEntity(new GenericType<List<MembresiaDTO>>(){});
            return new Respuesta(true, "Membresias",result);
        }catch(Exception ex){
            return new Respuesta(false, ex.toString(), "No pudo establecerse conexion con el servidor");
        }
    }

}
