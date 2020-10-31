/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.dto;

import java.util.Date;
import javafx.scene.chart.PieChart.Data;
import javax.json.bind.annotation.JsonbDateFormat;

/**
 *
 * @author Luis
 */
public class CobroPendienteDTO {
    private Long id;
    private int ano;
    private int periodo;
    private float monto;
    private boolean estado;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date fechaGenerado;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date fechaVencimiento;
    private MembresiaDTO membresia;
    
    
    public CobroPendienteDTO(){  
    } 
    
    public CobroPendienteDTO(Long id, int ano, int periodo, float  monto, boolean estado, Date fechaVencimiento, MembresiaDTO membresia){
        this.id=id;
        this.ano=ano;
        this.periodo=periodo;
        this.monto=monto;
        this.estado=estado;
        this.fechaVencimiento=this.fechaVencimiento;
        this.membresia=membresia;
    } 
    
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    
    public int getAno(){
        return ano;
    }
    public void setAno(int ano){
        this.ano=ano;
    }
    
    public int getPeriodo(){
        return periodo;
    }
    public void setPeriodo(int periodo){
        this.periodo=periodo;
    }
    
    public double getMonto(){
        return monto;
    }
    public void setMonto(float  monto){
        this.monto=monto;
    }
    
    public boolean getEstado(){
        return estado;
    }
    public void setEstado(boolean estado){
        this.estado=estado;
    }
    
    public Date getFechaGenerada(){
        return fechaGenerado;
    }
    public void setFechaGenerada(Date fechaGenerada){
        this.fechaGenerado=fechaGenerada;
    }
    
    public Date getFechaVencimiento(){
        return fechaVencimiento;
    }   
    public void setFechaVencimiento(Date fechaVencimiento){
        this.fechaVencimiento=this.fechaVencimiento;
    }
    
    public MembresiaDTO getMembresia(){
        return membresia;
    }
    public void setMembresia(MembresiaDTO membresia){
        this.membresia=membresia;
    }
    
    
   
    
}
