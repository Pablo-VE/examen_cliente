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
import javafx.scene.control.TableView;
import static javafx.scene.paint.Color.RED;
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.dto.TareaDTO;
import org.una.examen.cliente.service.ProyectoService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class ProyectoDetalleController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtResponsable;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private Button btnGuardar;

    private String modalidad="";
    @FXML
    private Label lbFechaRegistro;
    
    ProyectoService proyectoService = new ProyectoService();
    @FXML
    private Button btnAgregarTarea;
    @FXML
    private TableView<TareaDTO> tvTareas;
    @FXML
    private Label lbTituloTareas;
    
    private ProyectoDTO proyecto = new ProyectoDTO();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modalidad=(String) AppContext.getInstance().get("ModalidadProyecto");
        if(modalidad.equals("Agregar")){
            lbFechaRegistro.setVisible(false);
            btnAgregarTarea.setDisable(true);
            btnAgregarTarea.setVisible(false);
            proyecto = new ProyectoDTO();
            lbTitulo.setText("CREACIÓN DE PROYECTO");
        }
        // TODO
    }    

    
    
    @FXML
    private void actGuardar(ActionEvent event) {
        if(validar()){
            
            proyecto.setNombre(txtNombre.getText());
            proyecto.setResponsable(txtResponsable.getText());
            if(!txtDescripcion.getText().isBlank()){
                proyecto.setDescipcion(txtDescripcion.getText());
            }else{
                if(modalidad.equals("Modificar")){
                    proyecto.setDescipcion("");
                }
            }
            if(modalidad.equals("Agregar")){
                Respuesta res = proyectoService.crear(proyecto);
                if(res.getEstado()){
                    proyecto = (ProyectoDTO) res.getResultado("Proyecto");
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de proyecto", "Proyecto "+proyecto.getNombre()+" creado con éxito. Si desea puede agregar tareas");
                    btnGuardar.setDisable(true);
                    btnGuardar.setVisible(false);
                    btnAgregarTarea.setVisible(true);
                    btnAgregarTarea.setDisable(false);
                }else{
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de proyecto", "Ocurrió un error al registrar su proyecto");
                }
            }
        }
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

    @FXML
    private void actAgregarTarea(ActionEvent event) {
    }
    
}
