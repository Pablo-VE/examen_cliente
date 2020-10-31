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
public class ProvinciaDTO {
    private Long id;
    private String nombre;
    private int codigo;
    
    public ProvinciaDTO(){
    }
    
    public ProvinciaDTO(Long id, String nombre, int codigo){
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
    }
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public int getCodigo(){
        return codigo;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    public String toString(){
        return nombre;
    }
}
