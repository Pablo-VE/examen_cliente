/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.una.examen.cliente.App;
import org.una.examen.cliente.util.Mensaje;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class MenuInicioController implements Initializable {

    @FXML
    private JFXButton btnProyectos;
    @FXML
    private JFXButton btnProvincias;
    @FXML
    private JFXButton btnCobros;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actProyectos(ActionEvent event) {
        try{
            Stage stageCerrar = (Stage) btnProyectos.getScene().getWindow();
            stageCerrar.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("ProyectosPrincipal" + ".fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Gestión de proyectos");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Ocurrió un error, por favor intenta más tarde");
        }
    }

    @FXML
    private void actProvincias(ActionEvent event) {
        try{
            Stage stageCerrar = (Stage) btnProvincias.getScene().getWindow();
            stageCerrar.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("ProvinciasPrincipal" + ".fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Gestión de provincias");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Ocurrió un error, por favor intenta más tarde");
        }
    }

    @FXML
    private void actCobros(ActionEvent event) {
         try{
            Stage stageCerrar = (Stage) btnProyectos.getScene().getWindow();
            stageCerrar.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("MenuCobro" + ".fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Gestión de cobros");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Ocurrió un error, por favor intenta más tarde");
        }
    }
    
}
