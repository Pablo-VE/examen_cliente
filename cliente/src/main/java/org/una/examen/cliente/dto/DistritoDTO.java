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
public class DistritoDTO {
    private Long id;
    private String nombre;
    private int codigo;
    private CantonDTO canton;
    private Long area;
    private Long poblacion;
    
    public DistritoDTO(){
    }
    
    public DistritoDTO(Long id, String nombre, int codigo, CantonDTO canton){
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.canton = canton;
    }
    
    public Long getId(){
        return id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public Long getArea(){
        return area;
    }
    public void setArea(Long area){
        this.area = area;
    }
    public Long getPoblacion(){
        return poblacion;
    }
    public void setPoblacion(Long poblacion){
        this.poblacion = poblacion;
    }
    
    public int getCodigo(){
        return codigo;
    }
    
    public CantonDTO getCanton(){
        return canton;
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
    
    public void setCanton(CantonDTO canton){
        this.canton = canton;
    }
    public String toString(){
        return nombre;
    }
}
