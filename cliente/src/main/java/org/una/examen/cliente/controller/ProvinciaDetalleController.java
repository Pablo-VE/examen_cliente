/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.una.examen.cliente.App;
import org.una.examen.cliente.dto.ProvinciaDTO;
import org.una.examen.cliente.service.ProvinciaService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Jeffry
 */
public class ProvinciaDetalleController implements Initializable {

    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    
    private String modalidad = "";
    private ProvinciaDTO provincia = new ProvinciaDTO();
    private ProvinciaService provinciaService = new ProvinciaService();
    private ProvinciasController provinciasController;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private Label lblInfo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        provinciasController = (ProvinciasController) AppContext.getInstance().get("ProvinciaController");
        modalidad = (String) AppContext.getInstance().get("ModalidadProvincia");
        if(modalidad.equals("Modificar")){
            provincia = (ProvinciaDTO)AppContext.getInstance().get("ProvinciaEnCuestion");
            txtNombre.setText(provincia.getNombre());
            lblInfo.setText("Modificar Provincia");
        }
        if(modalidad.equals("Ver")){
            provincia = (ProvinciaDTO)AppContext.getInstance().get("ProvinciaEnCuestion");
            btnGuardar.setVisible(false);
            txtNombre.setText(provincia.getNombre());
            txtNombre.setDisable(true);
            txtNombre.setOpacity(100);
            btnEliminar.setVisible(false);
            lblInfo.setText("Ver Provincia");
        }
    }    

    @FXML
    private void actGuardar(ActionEvent event) {
        if(txtNombre.getText().isEmpty()){
            Mensaje.showAndWait(Alert.AlertType.WARNING, "Atención", "Por favor digite el nombre de la provincia");
        }else{
            
            
            if(modalidad.equals("Modificar")){
                provincia.setNombre(txtNombre.getText());
                provincia.setCodigo(3
                
                
                );
                Respuesta respuesta = provinciaService.modificar(provincia.getId(), provincia);
                if(respuesta.getEstado()){
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Modificación de provincia", "Se ha modificado la provincia correctamente");
                    Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                    stage2.close();
                }else{
                    Mensaje.showAndWait(Alert.AlertType.ERROR, "Modificación de provincia", respuesta.getMensaje());
                }
            }else{
                if(modalidad.equals("Agregar")){
                    provincia.setNombre(txtNombre.getText());
                    provincia.setCodigo(1);
                    Respuesta respuesta = provinciaService.crear(provincia);
                    if(respuesta.getEstado()){
                        provincia = (ProvinciaDTO) respuesta.getResultado("Provincia");
                        Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de provincia", "Se ha registrado la provincia correctamente");
                        Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                        stage2.close();
                    }else{
                        Mensaje.showAndWait(Alert.AlertType.ERROR, "Registro de provincia", respuesta.getMensaje());
                    }
                }
            }
        }
        provinciasController.cargarTodos();
    }

    @FXML
    private void actCancelar(ActionEvent event) {
        Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
        stage2.close();
    }

    @FXML
    private void actEliminar(ActionEvent event) {
        if(Mensaje.showConfirmation("Se eliminará esta provincia", btnCancelar.getScene().getWindow(), "¿Seguro que desea elminar esta provincia?")){
           Respuesta respuesta = provinciaService.delete(provincia.getId());
            if(respuesta.getEstado()){
                Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Provincias", "Se eliminó la provincia exitosamente");
                provinciasController.cargarTodos();
                Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                stage2.close();
            }else{
                System.out.println(respuesta.getMensaje());
            } 
        }
        
        
    }
    
    
    
}
