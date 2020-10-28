/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.dto;

/**
 *
 * @author Luis
 */
public class TipoServicioDTO {
    private Long id;
    private String nombre;
    private String responsable;
    private boolean estado;
    private ClienteDTO cliente;
    
    public TipoServicioDTO() {
    }
    
    public TipoServicioDTO(Long id, String nombre, String responsable, boolean estado, ClienteDTO cliente) {
        this.id=id;
        this.nombre=nombre;
        this.responsable=responsable;
        this.estado=estado;
        this.cliente=cliente;
    }
    
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    public String getResponsable(){
        return responsable;
    }
    public void setResponsable(String responsable){
        this.responsable=responsable;
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
