/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.una.examen.cliente.App;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Mensaje;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class ProyectosPrincipalController implements Initializable {

    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private Button actBuscarNombre;
    @FXML
    private Button btnBuscarProyecto;
    @FXML
    private Label lbTitulo;
    @FXML
    private StackPane ContenedorProyectos;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnCrearProyecto;
    @FXML
    private Button btnVerProyectos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*TreeItem<File> archivos = new TreeItem<>();
        TreeView<File> treeView = new TreeView<>();
        treeView.setShowRoot(false);
        treeView.setRoot(archivos);

        File[] roots = File.listRoots();
        for (File disk : roots)
        archivos.getChildren().add(createNode(disk));
        
     
        vbProyectos.setPadding(new Insets(10.0));
        vbProyectos.setSpacing(10.0);
        vbProyectos.getChildren().add(new Label("Proyectos"));
        vbProyectos.getChildren().add(treeView);*/

        }    

    @FXML
    private void actBuscarNombre(ActionEvent event) {
    }

    @FXML
    private void actBuscarProyecto(ActionEvent event) {
    }

    @FXML
    private void actVolver(ActionEvent event) {
        try{
            Stage stageCerrar = (Stage) btnVolver.getScene().getWindow();
            stageCerrar.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("MenuInicio" + ".fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Examen de Programación III - Grupo 7");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Ocurrió un error, por favor intenta más tarde");
        }
    }

    @FXML
    private void actCrearProyecto(ActionEvent event) {
        try{
            AppContext.getInstance().set("ModalidadProyecto", "Agregar");
            Parent root = FXMLLoader.load(App.class.getResource("ProyectoDetalle" + ".fxml"));
            ContenedorProyectos.getChildren().clear();
            ContenedorProyectos.getChildren().add(root);
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }

    @FXML
    private void actVerProyectos(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(App.class.getResource("ProyectosTree" + ".fxml"));
            ContenedorProyectos.getChildren().clear();
            ContenedorProyectos.getChildren().add(root);
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
    
}
