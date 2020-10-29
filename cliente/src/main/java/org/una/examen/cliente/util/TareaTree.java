/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.una.examen.cliente.App;
import org.una.examen.cliente.dto.TareaDTO;

/**
 *
 * @author Pablo-VE
 */
public class TareaTree extends HBox{
    private TareaDTO tarea;

    public TareaTree(TareaDTO tarea) {
        this.setMinHeight(70);
        this.setMaxWidth(849);
        this.tarea = tarea;
        this.setPrefWidth(849);
        this.setPrefHeight(80);
        this.setStyle(estiloPrincipal());
        crearNombre();
        crearEstado();
        crearPrioridad();
        crearFechas();
        crearEditar();
    }
    
    public void crearNombre(){
        VBox con = new VBox();
        con.setStyle(vbEstilo1());
        con.setPrefHeight(70);
        con.setPrefWidth(234);
        con.setAlignment(Pos.CENTER);
        Label nombre = new Label();
        nombre.setText("Nombre: "+tarea.getNombre());
        con.getChildren().add(nombre);
        this.getChildren().add(con);
    }
    
    public void crearEstado(){
        VBox estado = new VBox();
        estado.setStyle(vbEstilo2());
        estado.setPrefHeight(70);
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
        con.setPrefHeight(70);
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
        con.setPrefHeight(70);
        con.setPrefWidth(255);
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
    
    public void crearEditar(){
        HBox con = new HBox();
        con.setStyle(vbEstilo1());
        con.setPrefHeight(70);
        con.setPrefWidth(60);
        con.setAlignment(Pos.CENTER);
        
        Image ima=null; 
        try {
            ima = new Image(new File("src/main/resources/org/una/examen/cliente/recursos/editarTarea.png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException ex) {
            Logger.getLogger(TareaTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageView ivEditar = new ImageView(ima);
        
        ivEditar.setOnMouseClicked(event ->{
            try{
                Stage stage = new Stage();
                AppContext.getInstance().set("ModalidadTarea", "Modificar");
                AppContext.getInstance().set("TareaEnCuestion", tarea);
                AppContext.getInstance().set("VistaProyecto", "TreeView");
                Parent root = FXMLLoader.load(App.class.getResource("TareasDetalle" + ".fxml"));
                stage.setScene(new Scene(root));
                stage.setTitle("Modificación de tarea");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
                stage.show();
            }catch(IOException ex){
                System.out.println(ex.getMessage());
                Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
            };
        });
        
        Label espacio = new Label();
        espacio.setText("  ");
        con.getChildren().add(ivEditar);
        this.getChildren().add(con);
    }
    
    
    
    public String estiloPrincipal(){
        return  "   -fx-background-color: #transparent;";
    }
    
    
    public String vbEstilo1(){
        return  "   -fx-background-color: #f5e2e1;" +
                "   -fx-spacing: 5;" ;
    }
    
    public String vbEstilo2(){
        return  "   -fx-background-color: #f0d0ce;" +
                "   -fx-spacing: 5;" ;
    }
}
