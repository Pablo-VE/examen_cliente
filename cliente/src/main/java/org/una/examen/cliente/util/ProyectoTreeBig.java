/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.una.examen.cliente.App;
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
        this.setStyle(estilosPrincipal());
        this.proyecto = proyecto;
        
        
        crearTitulo();
        this.getChildren().add(titulo);
        tareas = new ArrayList<TareaDTO>();
        Respuesta res = tareaService.getByProyecto(proyecto.getId());
        if(res.getEstado()){
            tareas = (List<TareaDTO>) res.getResultado("Tareas");
            if(!tareas.isEmpty()){
                for(int i=0; i<tareas.size(); i++){
                    TareaTree tarea = new TareaTree(tareas.get(i));
                    this.getChildren().add(tarea);
                }
            }
        }
        
        
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
                Stage stage = new Stage();
                AppContext.getInstance().set("ModalidadTarea", "Agregar");
                AppContext.getInstance().set("Proyecto", proyecto);
                AppContext.getInstance().set("VistaProyecto", "TreeView");
                Parent root = FXMLLoader.load(App.class.getResource("TareasDetalle" + ".fxml"));
                stage.setScene(new Scene(root));
                stage.setTitle("Registro de tarea");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
                stage.show();
            }catch(IOException ex){
                System.out.println(ex.getMessage());
                Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
            };
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
        titulo.getChildren().add(btnAgregarTarea);
    }
    
   
    
    public void actModificarProyecto(){
        
    }
    
    
    public String estilosPrincipal(){
        return  "   -fx-background-color: transparent;" +
                "   -fx-spacing: 1;" ;
    }
    
    public String estilosTitulo(){
        return  "   -fx-background-color: #ffffff;" +
                "   -fx-padding: 10;" +
                "   -fx-spacing: 15;" ;
    }
    
    
    
    
    
}
