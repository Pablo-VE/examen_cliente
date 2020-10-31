/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.dto;

/**
 *
 * @author Jeffry
 */
public class CantonDTO {
    private Long id;
    private String nombre;
    private int codigo;
    private ProvinciaDTO provincia;
    
    public CantonDTO(){
    }
    
    public CantonDTO(Long id, String nombre, int codigo, ProvinciaDTO provincia){
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.provincia = provincia;
    }
    
    public Long getId(){
        return id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public int getCodigo(){
        return codigo;
    }
    
    public ProvinciaDTO getProvincia(){
        return provincia;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    
    public void setProvincia(ProvinciaDTO provincia){
        this.provincia = provincia;
    }
    
    public String toString(){
        return nombre;
    }
}
