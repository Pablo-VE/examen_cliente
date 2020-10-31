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
 * @author Luis
 */
public class MenuCobroController implements Initializable {

    @FXML
    private JFXButton btnMantenimiento;
    @FXML
    private JFXButton btnVerCobros;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actMantenimiento(ActionEvent event) {
          try{
            Stage stageCerrar = (Stage) btnMantenimiento.getScene().getWindow();
            stageCerrar.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("MantenimientoCobro" + ".fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Gestión de cobros");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Ocurrió un error, por favor intenta más tarde");
        }
    }

    @FXML
    private void actVerCobros(ActionEvent event) {
          try{
            Stage stageCerrar = (Stage) btnVerCobros.getScene().getWindow();
            stageCerrar.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("VerCobro" + ".fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Gestión de cobros");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Ocurrió un error, por favor intenta más tarde");
        }
    }
    
}
