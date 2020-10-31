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
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.dto.UnidadDTO;
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
public class UnidadDetalleController implements Initializable {

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
    @FXML
    private JFXComboBox<DistritoDTO> cbDistrito;
    @FXML
    private JFXTextField txtTipo;
    @FXML
    private JFXTextField txtArea;
    @FXML
    private JFXTextField txtPoblacion;
    
    
    private String modalidad = "";
    private UnidadDTO unidad = new UnidadDTO();
    private UnidadService unidadService = new UnidadService();
    private UnidadesController unidadesController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        unidadesController = (UnidadesController) AppContext.getInstance().get("UnidadController");
        modalidad = (String) AppContext.getInstance().get("ModalidadUnidad");
        if(modalidad.equals("Modificar")){
            unidad = (UnidadDTO)AppContext.getInstance().get("UnidadEnCuestion");
            txtNombre.setText(unidad.getNombre());
            cbDistrito.setValue(unidad.getDistrito());
            txtArea.setText(unidad.getArea().toString());
            txtPoblacion.setText(unidad.getPoblacion().toString());
            txtTipo.setText(unidad.getTipo());
            lblInfo.setText("Modificar Unidad");
        }
        if(modalidad.equals("Ver")){
            unidad = (UnidadDTO)AppContext.getInstance().get("UnidadEnCuestion");
            btnGuardar.setVisible(false);
            txtNombre.setText(unidad.getNombre());
            txtNombre.setDisable(true);
            txtArea.setText(unidad.getArea().toString());
            txtArea.setOpacity(100);
            txtArea.setDisable(true);
            txtPoblacion.setText(unidad.getPoblacion().toString());
            txtPoblacion.setOpacity(100);
            txtPoblacion.setDisable(true);
            txtNombre.setOpacity(100);
            cbDistrito.setValue(unidad.getDistrito());
            cbDistrito.setDisable(true);
            cbDistrito.setOpacity(100);
            txtTipo.setText(unidad.getTipo());
            txtTipo.setDisable(true);
            txtTipo.setOpacity(100);
            btnEliminar.setVisible(false);
            lblInfo.setText("Ver Unidad");
        }
        initDistritos();
    }    

    @FXML
    private void actGuardar(ActionEvent event) {
        if(txtNombre.getText().isEmpty()){
            Mensaje.showAndWait(Alert.AlertType.WARNING, "Atención", "Por favor digite el nombre del cantón");
        }else{
            
            
            if(modalidad.equals("Modificar")){
                unidad.setNombre(txtNombre.getText());
                unidad.setCodigo(3);
                unidad.setDistrito(cbDistrito.getValue());
                unidad.setTipo(txtTipo.getText());
                unidad.setArea(Long.valueOf(txtArea.getText()));
                unidad.setPoblacion(Long.valueOf(txtPoblacion.getText()));
                Respuesta respuesta = unidadService.modificar(unidad.getId(), unidad);
                if(respuesta.getEstado()){
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Modificación de unidad", "Se ha modificado la unidad correctamente");
                    Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                    stage2.close();
                }else{
                    Mensaje.showAndWait(Alert.AlertType.ERROR, "Modificación de unidad", respuesta.getMensaje());
                }
            }else{
                if(modalidad.equals("Agregar")){
                    unidad.setNombre(txtNombre.getText());
                    unidad.setCodigo(1);
                    unidad.setDistrito(cbDistrito.getValue());
                    unidad.setArea(Long.valueOf(txtArea.getText()));
                    unidad.setTipo(txtTipo.getText());
                    unidad.setPoblacion(Long.valueOf(txtPoblacion.getText()));
                    
                    
                    
                    Respuesta respuesta = unidadService.crear(unidad);
                    if(respuesta.getEstado()){
                        unidad = (UnidadDTO) respuesta.getResultado("Unidad");
                        Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de unidad", "Se ha registrado la unidad correctamente");
                        Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                        stage2.close();
                    }else{
                        Mensaje.showAndWait(Alert.AlertType.ERROR, "Registro de unidad", respuesta.getMensaje());
                    }
                }
            }
        }
        unidadesController.cargarTodos();
    }

    @FXML
    private void actCancelar(ActionEvent event) {
        Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
        stage2.close();
    }

    @FXML
    private void actEliminar(ActionEvent event) {
        if(Mensaje.showConfirmation("Se eliminará este unidad", btnCancelar.getScene().getWindow(), "¿Seguro que desea elminar esta unidad?")){
            Respuesta respuesta = unidadService.delete(unidad.getId());
            if(respuesta.getEstado()){
                Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Unidades", "Se eliminó la unidad exitosamente");
                unidadesController.cargarTodos();
                Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
                stage2.close();
            }else{
                System.out.println(respuesta.getMensaje());
            }
            unidadesController.cargarTodos();
        }
    }
    
    public void initDistritos(){
        DistritoService distritoService = new DistritoService();
        ArrayList<DistritoDTO> distritos;
        Respuesta respuesta = distritoService.getAll();
        if(respuesta.getEstado()){
            distritos = (ArrayList<DistritoDTO>) respuesta.getResultado("Distritos");
            ObservableList items = FXCollections.observableArrayList(distritos);
            cbDistrito.setItems(items);
        }
    }
}
