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
 * @author Pablo-VE
 */
public class ProyectoDTO {
    private Long id;
    private String nombre;
    private String descipcion;
    private String responsable;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date fechaRegistro;

    public ProyectoDTO() {
    }

    public ProyectoDTO(Long id, String nombre, String descipcion, String responsable, Date fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.descipcion = descipcion;
        this.responsable = responsable;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    
}
