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
public class MembresiaDTO {
    private Long id;
    private int periotidad;  
    private float monto;
    private String descripcion;
    private boolean estado;
    private ClienteDTO cliente; 
    
    
public MembresiaDTO() {
}

public MembresiaDTO(Long id, int perioridad, float monto, String descripcion, boolean estado, ClienteDTO cliente) {
    this.id=id;
    this.periotidad=perioridad;
    this.monto=monto;
    this.descripcion=descripcion;
    this.estado=estado;
    this.cliente=cliente;
}

public Long getId(){
    return id;
}
public void setId(Long id){
    this.id=id;
} 

public int getPerioridad(){
    return periotidad;
}
public void setPerioridad(int perioridad){
    this.periotidad=perioridad;
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
    
 
}
