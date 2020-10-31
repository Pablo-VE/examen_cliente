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
public class UnidadDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private int codigo;
    private Long area;
    private Long poblacion;
    private DistritoDTO distrito;
    
    public UnidadDTO(){
    }
    
    public UnidadDTO(Long id, String nombre, int codigo, DistritoDTO distrito, String tipo, Long area, Long poblacion){
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.distrito = distrito;
        this.tipo = tipo;
        this.area = area;
        this.poblacion = poblacion;
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
    
    public DistritoDTO getDistrito(){
        return distrito;
    }
    
    public Long getArea(){
        return area;
    }
    
    public Long getPoblacion(){
        return poblacion;
    }
    
    public String getTipo(){
        return tipo;
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
    
    public void setArea(Long area){
        this.area = area;
    }
    
    public void setPoblacion(Long poblacion){
        this.poblacion = poblacion;
    }
    
    public void setDistrito(DistritoDTO distrito){
        this.distrito = distrito;
    }
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    
    public String toString(){
        return nombre;
    }
}
