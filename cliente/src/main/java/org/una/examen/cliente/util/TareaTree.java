/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.util;

import java.text.SimpleDateFormat;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.una.examen.cliente.dto.TareaDTO;

/**
 *
 * @author Pablo-VE
 */
public class TareaTree extends HBox{
    private TareaDTO tarea;

    public TareaTree(TareaDTO tarea) {
        this.tarea = tarea;
        
        this.setStyle(estiloPrincipal());
        crearNombre();
        crearEstado();
        crearPrioridad();
        crearFechas();
    }
    
    public void crearNombre(){
        VBox con = new VBox();
        con.setStyle(vbEstilo1());
        con.setPrefHeight(50);
        con.setPrefWidth(200);
        con.setAlignment(Pos.CENTER);
        Label nombre = new Label();
        nombre.setText("Nombre: "+tarea.getNombre());
        con.getChildren().add(nombre);
        this.getChildren().add(con);
    }
    
    public void crearEstado(){
        VBox estado = new VBox();
        estado.setStyle(vbEstilo2());
        estado.setPrefHeight(50);
        estado.setPrefWidth(200);
        estado.setAlignment(Pos.CENTER);
        //setear color
        Label titulo = new Label();
        titulo.setText("Avance");
        Label avance = new Label();
        avance.setText(String.valueOf(tarea.getPorcentajeAvance())+"%");
        estado.getChildren().add(titulo);
        estado.getChildren().add(avance);
        this.getChildren().add(estado);
    }
    
    public void crearPrioridad(){
        VBox con = new VBox();
        con.setStyle(vbEstilo1());
        con.setPrefHeight(50);
        con.setPrefWidth(100);
        con.setAlignment(Pos.CENTER);
        Label nombre = new Label();
        nombre.setText("Prioridad: "+String.valueOf(tarea.getImportancia()*tarea.getUrgencia()));
        con.getChildren().add(nombre);
        this.getChildren().add(con);
    }
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    public void crearFechas(){
        VBox con = new VBox();
        con.setStyle(vbEstilo2());
        con.setPrefHeight(50);
        con.setPrefWidth(225);
        con.setAlignment(Pos.CENTER);
        Label fechaI = new Label();
        fechaI.setText("Fecha de inicio: "+formatter.format(tarea.getFechaInicio()));
        fechaI.setPrefWidth(183);
        Label fechaF = new Label();
        fechaF.setText("Fecha de finalización: "+formatter.format(tarea.getFechaFinalizacion()));
        fechaF.setPrefWidth(183);
        con.getChildren().add(fechaI);
        con.getChildren().add(fechaF);
        this.getChildren().add(con);
    }
    
    public String estiloPrincipal(){
        return  "   -fx-background-color: #ffffff;" +
                "   -fx-padding: 10;" +
                "   -fx-spacing: 15;" ;
    }
    
    
    public String vbEstilo1(){
        return  "   -fx-background-color: #f5e2e1;" +
                "   -fx-spacing: 15;" ;
    }
    
    public String vbEstilo2(){
        return  "   -fx-background-color: #f0d0ce;" +
                "   -fx-spacing: 5;" ;
    }
}
