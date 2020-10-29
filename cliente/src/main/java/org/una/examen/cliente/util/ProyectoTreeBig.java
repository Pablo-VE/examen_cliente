/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.util;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.dto.TareaDTO;
import org.una.examen.cliente.service.TareaService;

/**
 *
 * @author Pablo-VE
 */
public class ProyectoTreeBig extends VBox {
    private ProyectoDTO proyecto;
    private List<TareaDTO> tareas;

    private TareaService tareaService = new TareaService();
    
    public ProyectoTreeBig(ProyectoDTO proyecto) {
        this.proyecto = proyecto;
        
        tareas = new ArrayList<TareaDTO>();
        Respuesta res = tareaService.getByProyecto(proyecto.getId());
        if(res.getEstado()){
            tareas = (List<TareaDTO>) res.getResultado("Tareas");
        }
        crearTitulo();
        this.getChildren().add(titulo);
        
        
    }
    
    HBox titulo = new HBox();
    
    public void crearTitulo(){
        titulo = new HBox();
        titulo.setAlignment(Pos.CENTER_LEFT);
        titulo.prefHeight(45);
        titulo.prefWidth(849);
        titulo.setStyle(estilosTitulo());
        
        Label nombre = new Label();
        nombre.setText(String.valueOf(proyecto.getId())+". "+proyecto.getNombre());
        nombre.setPrefWidth(585);
        //set estilo al label
        titulo.getChildren().add(nombre);  
        crearBotones();
    }
    
    public void crearBotones(){
        Button btnAgregarTarea = new Button();
        btnAgregarTarea.setText("Agregar tarea");
        btnAgregarTarea.setOnMouseClicked(event ->{
            try{
                actAgregarTarea();
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        Button btnModificarProyecto = new Button();
        btnModificarProyecto.setText("Modificar proyecto");
        btnModificarProyecto.setOnMouseClicked(event ->{
            try{
                actModificarProyecto();
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        
        //set estilo a los botones
        
        titulo.getChildren().add(btnModificarProyecto);
        /*Label espacio = new Label();
        espacio.setText("");
        espacio.setPrefWidth(24);*/
        titulo.getChildren().add(btnAgregarTarea);
    }
    
    public void actAgregarTarea(){
        
    }
    
    public void actModificarProyecto(){
        
    }
    
    
    
    
    public String estilosTitulo(){
        return  "   -fx-background-color: #ffffff;" +
                "   -fx-padding: 10;" +
                "   -fx-spacing: 15;" ;
    }
    
    
    
    
    
}
