/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.dto;

import java.util.Date;
import javax.json.bind.annotation.JsonbDateFormat;

/**
 *
 * @author Luis
 */
public class MembresiaDTO {
    private Long id;
    private int periodicidad;  
    private float monto;
    private String descripcion;
    private boolean estado;
    private ClienteDTO cliente; 
    private TipoServicioDTO tipoServicio;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date fechaVenceMembresia;
    
    
    public MembresiaDTO() {
    }

    public MembresiaDTO(Long id, int periodicidad, float monto, String descripcion, boolean estado, ClienteDTO cliente,
            TipoServicioDTO tipoServicio, Date fecheVenceMembresia) {
        this.id=id;
        this.periodicidad=periodicidad;
        this.monto=monto;
        this.descripcion=descripcion;
        this.estado=estado;
        this.cliente=cliente;
        this.tipoServicio=tipoServicio;
        this.fechaVenceMembresia=fecheVenceMembresia;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    } 

    public int getPeriodicidad(){
        return periodicidad;
    }
    public void setPeriodicidad(int periodicidad){
        this.periodicidad=periodicidad;
    }

    public float getMonto(){
        return monto;
    }
    public void setMonto(float monto){
        this.monto=monto;
    }

    public String getDescripcion(){
        return descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }

    public boolean getEstado(){
        return estado;
    }
    public void setEstado(boolean estado){
        this.estado=estado;
    }

    public ClienteDTO getCliente(){
        return cliente;
    }
    public void setCliente(ClienteDTO cliente){
        this.cliente=cliente;
    }    

    public TipoServicioDTO getTipoServicio(){
        return tipoServicio;
    }
    public void setTipoServicio(TipoServicioDTO tipoServicio){
        this.tipoServicio=tipoServicio;
    }
    
    public Date getFechaVenceMembresia(){
        return fechaVenceMembresia;
    }
    public void setFechaVenceMembresia(Date fechaVenceMembresia){
        this.fechaVenceMembresia=fechaVenceMembresia;
    }

}
