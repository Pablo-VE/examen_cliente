/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.service.ProyectoService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Formato;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class ProyectoEditarController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private JFXTextField txtResponsable;

    /**
     * Initializes the controller class.
     */
    ProyectoDTO proyecto = new ProyectoDTO();
    ProyectoService proyectoService = new ProyectoService();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Formato();
        proyecto = (ProyectoDTO) AppContext.getInstance().get("Proyecto");
        txtNombre.setText(proyecto.getNombre());
        txtResponsable.setText(proyecto.getResponsable());
        if(proyecto.getDescipcion()!=null){
            txtDescripcion.setText(proyecto.getDescipcion());
        }
        // TODO
    }   
    
    public void Formato(){
        txtNombre.setTextFormatter(Formato.getInstance().maxLengthFormat(20));
        txtResponsable.setTextFormatter(Formato.getInstance().maxLengthFormat(50));
        txtDescripcion.setTextFormatter(Formato.getInstance().maxLengthFormat(200));
    }

    @FXML
    private void actGuardar(ActionEvent event) {
        if(validar()){
            
            proyecto.setNombre(txtNombre.getText());
            proyecto.setResponsable(txtResponsable.getText());
            if(txtDescripcion.getText()!=null){
                proyecto.setDescipcion(txtDescripcion.getText());
            }else{
                 proyecto.setDescipcion("");
                
            }
            
                Respuesta res = proyectoService.modificar(proyecto.getId(), proyecto);
                if(res.getEstado()){
                    proyecto = (ProyectoDTO) res.getResultado("Proyecto");
                    ProyectosTreeController proController = (ProyectosTreeController) AppContext.getInstance().get("ControllerProyecto");
                    proController.cargarTodos();
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Modificación de proyecto", "Proyecto "+proyecto.getNombre()+" modificado con éxito");
                    Stage stage = (Stage) btnCancelar.getScene().getWindow();
                    stage.close();
                   
                }else{
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de proyecto", "Ocurrió un error al registrar su proyecto");
                }
            
        }
    }

    @FXML
    private void actCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    public boolean validar(){
        if(txtNombre.getText().isBlank()){
            Mensaje.showAndWait(Alert.AlertType.WARNING, "Registro de proyecto", "Por favor ingrese el nombre del proyecto");
            return false;
        }
        if(txtResponsable.getText().isBlank()){
            Mensaje.showAndWait(Alert.AlertType.WARNING, "Registro de proyecto", "Por favor ingrese el nombre del responsable del proyecto");
            return false;
        }
        
        return true;
    }
    
}
