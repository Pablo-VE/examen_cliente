/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.una.examen.cliente.dto.CantonDTO;
import org.una.examen.cliente.dto.ProvinciaDTO;
import org.una.examen.cliente.service.CantonService;
import org.una.examen.cliente.service.ProvinciaService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Jeffry
 */
public class CantonDetalleController implements Initializable {

    @FXML
    private Label lblInfo;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnEliminar;
    
    
    private CantonesController cantonController;
    private String modalidad = "";
    private CantonDTO canton = new CantonDTO();
    private CantonService cantonService = new CantonService();
    @FXML
    private JFXComboBox<ProvinciaDTO> cbProvincia;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cantonController = (CantonesController) AppContext.getInstance().get("CantonController");
        modalidad = (String) AppContext.getInstance().get("ModalidadCanton");
        if(modalidad.equals("Modificar")){
            canton = (CantonDTO)AppContext.getInstance().get("CantonEnCuestion");
            txtNombre.setText(canton.getNombre());
            cbProvincia.setValue(canton.getProvincia());
            lblInfo.setText("Modificar Cantón");
        }
        if(modalidad.equals("Ver")){
            canton = (CantonDTO)AppContext.getInstance().get("CantonEnCuestion");
            btnGuardar.setVisible(false);
            txtNombre.setText(canton.getNombre());
            txtNombre.setDisable(true);
            txtNombre.setOpacity(100);
            cbProvincia.setValue(canton.getProvincia());
            cbProvincia.setDisable(true);
            cbProvincia.setOpacity(100);
            btnEliminar.setVisible(false);
            lblInfo.setText("Ver Cantón");
        }
        initProvincias();
    }    

    @FXML
    private void actGuardar(ActionEvent event) {
        if(txtNombre.getText().isEmpty()){
            Mensaje.showAndWait(Alert.AlertType.WARNING, "Atención", "Por favor digite el nombre del cantón");
        }else{
            if(modalidad.equals("Modificar")){
                canton.setNombre(txtNombre.getText());
                canton.setCodigo(3);
                canton.setProvincia(cbProvincia.getValue());
                Respuesta respuesta = cantonService.modificar(canton.getId(), canton);
                if(respuesta.getEstado()){
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Modificación de cantón", "Se ha modificado el cantón correctamente");
                    Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                    stage2.close();
                }else{
                    Mensaje.showAndWait(Alert.AlertType.ERROR, "Modificación de cantón", respuesta.getMensaje());
                }
            }else{
                if(modalidad.equals("Agregar")){
                    canton.setNombre(txtNombre.getText());
                    canton.setCodigo(1);
                    canton.setProvincia(cbProvincia.getValue());
                    Respuesta respuesta = cantonService.crear(canton);
                    if(respuesta.getEstado()){
                        canton = (CantonDTO) respuesta.getResultado("Canton");
                        Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de cantón", "Se ha registrado el cantón correctamente");
                        Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                        stage2.close();
                    }else{
                        Mensaje.showAndWait(Alert.AlertType.ERROR, "Registro de cantón", respuesta.getMensaje());
                    }
                }
            }
        }
        cantonController.cargarTodos();
    }

    @FXML
    private void actCancelar(ActionEvent event) {
        Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
        stage2.close();
    }

    @FXML
    private void actEliminar(ActionEvent event) {
        if(Mensaje.showConfirmation("Se eliminará este cantón", btnCancelar.getScene().getWindow(), "¿Seguro que desea elminar este cantón?")){
            Respuesta respuesta = cantonService.delete(canton.getId());
            if(respuesta.getEstado()){
                Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Cantones", "Se eliminó el cantón exitosamente");
                cantonController.cargarTodos();
                Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                stage2.close();
            }else{
                System.out.println(respuesta.getMensaje());
            }
            cantonController.cargarTodos();
        }
        
        
    }

    @FXML
    private void actProvincia(ActionEvent event) {
    }
    
    public void initProvincias(){
        ProvinciaService provinciaService = new ProvinciaService();
        ArrayList<ProvinciaDTO> provincias;
        Respuesta respuesta = provinciaService.getAll();
        if(respuesta.getEstado()){
            provincias = (ArrayList<ProvinciaDTO>) respuesta.getResultado("Provincias");
            ObservableList items = FXCollections.observableArrayList(provincias);
            cbProvincia.setItems(items);
        }
    }
}
