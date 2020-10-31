/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import org.una.examen.cliente.App;
import org.una.examen.cliente.dto.ProvinciaDTO;
import org.una.examen.cliente.service.CantonService;
import org.una.examen.cliente.service.DistritoService;
import org.una.examen.cliente.service.ProvinciaService;
import org.una.examen.cliente.service.UnidadService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Jeffry
 */
public class ProvinciasPrincipalController implements Initializable {

    @FXML
    private JFXButton btnInicio;
    @FXML
    private JFXButton btnProvincias;
    @FXML
    private JFXButton btnCantones;
    @FXML
    private JFXButton btnDistritos;
    @FXML
    private JFXButton btnUnidades;
    @FXML
    private AnchorPane apContenedor;
    
    private ProvinciasController provinciasController;
    @FXML
    private JFXTreeView treeView;

    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        AppContext.getInstance().set("apContenedor", this.apContenedor);
        try{
            Parent root = FXMLLoader.load(App.class.getResource("ProvinciasTreeView" + ".fxml"));
            apContenedor.getChildren().clear();
            apContenedor.getChildren().add(root);
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        }

    }    

    @FXML
    private void actInicio(ActionEvent event) {
        AppContext.getInstance().set("apContenedor", this.apContenedor);
        try{
            Parent root = FXMLLoader.load(App.class.getResource("ProvinciasTreeView" + ".fxml"));
            apContenedor.getChildren().clear();
            apContenedor.getChildren().add(root);
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        }
    }

    @FXML
    private void actProvincias(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(App.class.getResource("Provincias" + ".fxml"));
            apContenedor.getChildren().clear();
            apContenedor.getChildren().add(root);
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        }
    }

    @FXML
    private void actCantones(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(App.class.getResource("Cantones" + ".fxml"));
            apContenedor.getChildren().clear();
            apContenedor.getChildren().add(root);
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        }
    }

    @FXML
    private void actDistritos(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(App.class.getResource("Distritos" + ".fxml"));
            apContenedor.getChildren().clear();
            apContenedor.getChildren().add(root);
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        }
    }

    @FXML
    private void actUnidades(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(App.class.getResource("Unidades" + ".fxml"));
            apContenedor.getChildren().clear();
            apContenedor.getChildren().add(root);
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        }
    }
    
}
