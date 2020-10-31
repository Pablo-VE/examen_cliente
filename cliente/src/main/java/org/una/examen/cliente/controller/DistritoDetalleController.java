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
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.service.CantonService;
import org.una.examen.cliente.service.DistritoService;
import org.una.examen.cliente.service.ProvinciaService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.AreaYPoblacion;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Jeffry
 */
public class DistritoDetalleController implements Initializable {

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
    
    private String modalidad = "";
    private DistritoDTO distrito = new DistritoDTO();
    private DistritoService distritoService = new DistritoService();
    private DistritosController distritosController;
    
    private AreaYPoblacion ap = new AreaYPoblacion();
    @FXML
    private JFXComboBox<CantonDTO> cbCanton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        distritosController = (DistritosController) AppContext.getInstance().get("DistritoController");
        modalidad = (String) AppContext.getInstance().get("ModalidadDistrito");
        if(modalidad.equals("Modificar")){
            distrito = (DistritoDTO)AppContext.getInstance().get("DistritoEnCuestion");
            txtNombre.setText(distrito.getNombre());
            cbCanton.setValue(distrito.getCanton());
            System.out.println(distrito.getCanton());
            lblInfo.setText("Modificar Distrito");
        }
        if(modalidad.equals("Ver")){
            distrito = (DistritoDTO)AppContext.getInstance().get("DistritoEnCuestion");
            btnGuardar.setVisible(false);
            txtNombre.setText(distrito.getNombre());
            txtNombre.setDisable(true);
            txtNombre.setOpacity(100);
            cbCanton.setValue(distrito.getCanton());
            cbCanton.setDisable(true);
            cbCanton.setOpacity(100);
            btnEliminar.setVisible(false);
            lblInfo.setText("Ver Distrito");
        }
        initCantones();
    }    

    @FXML
    private void actGuardar(ActionEvent event) {
        if(txtNombre.getText().isEmpty()){
            Mensaje.showAndWait(Alert.AlertType.WARNING, "Atención", "Por favor digite el nombre del cantón");
        }else{
            
            
            if(modalidad.equals("Modificar")){
                distrito.setNombre(txtNombre.getText());
                distrito.setCodigo(distrito.getCodigo());
                distrito.setCanton(cbCanton.getValue());
                Respuesta respuesta = distritoService.modificar(distrito.getId(), distrito);
                if(respuesta.getEstado()){
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Modificación de distrito", "Se ha modificado el distrito correctamente");
                    Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                    stage2.close();
                }else{
                    Mensaje.showAndWait(Alert.AlertType.ERROR, "Modificación de distrito", respuesta.getMensaje());
                }
            }else{
                if(modalidad.equals("Agregar")){
                    distrito.setNombre(txtNombre.getText());
                    Respuesta res = distritoService.getAll();
                    ArrayList<DistritoDTO> distritos = (ArrayList<DistritoDTO>)res.getResultado("Distritos");
                    distrito.setCodigo(ap.getCodigoNuevoDistrito(distritos));
                    distrito.setCanton(cbCanton.getValue());
                    Respuesta respuesta = distritoService.crear(distrito);
                    if(respuesta.getEstado()){
                        distrito = (DistritoDTO) respuesta.getResultado("Distrito");
                        Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de distrito", "Se ha registrado el distrito correctamente");
                        Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                        stage2.close();
                    }else{
                        Mensaje.showAndWait(Alert.AlertType.ERROR, "Registro de distrito", respuesta.getMensaje());
                    }
                }
            }
        }
        distritosController.cargarTodos();
    }

    @FXML
    private void actCancelar(ActionEvent event) {
        Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
        stage2.close();
    }

    @FXML
    private void actEliminar(ActionEvent event) {
        if(Mensaje.showConfirmation("Se eliminará este distrito", btnCancelar.getScene().getWindow(), "¿Seguro que desea elminar este distrito?")){
            Respuesta respuesta = distritoService.delete(distrito.getId());
            if(respuesta.getEstado()){
                Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Cantones", "Se eliminó el cantón exitosamente");
                distritosController.cargarTodos();
                Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                stage2.close();
            }else{
                System.out.println(respuesta.getMensaje());
            }
            distritosController.cargarTodos();
        }
    }
    
    public void initCantones(){
        CantonService cantonService = new CantonService();
        ArrayList<DistritoDTO> cantones;
        Respuesta respuesta = cantonService.getAll();
        if(respuesta.getEstado()){
            cantones = (ArrayList<DistritoDTO>) respuesta.getResultado("Cantones");
            ObservableList items = FXCollections.observableArrayList(cantones);
            cbCanton.setItems(items);
        }
    }

    @FXML
    private void actProvincia(ActionEvent event) {
    }

    
}
