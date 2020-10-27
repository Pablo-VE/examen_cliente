/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
    }

    @FXML
    private void actProvincias(ActionEvent event) {
    }

    @FXML
    private void actCobros(ActionEvent event) {
    }
    
}
