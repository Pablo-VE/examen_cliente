/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javax.json.bind.annotation.JsonbDateFormat;
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.dto.TareaDTO;
import org.una.examen.cliente.service.TareaService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Formato;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class TareasDetalleController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFinalizacion;
    @FXML
    private JFXSlider nivelAvance;
    @FXML
    private JFXComboBox<Integer> cbNivelImportancia;
    @FXML
    private Label lbTituloModificacion;
    @FXML
    private Label lbFechaRegistro;
    @FXML
    private Label lbFechaModificacion;
    @FXML
    private Button btnGuardar;

    /**
     * Initializes the controller class.
     */
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    String modalidad="";
    private ProyectoDTO proyecto = new ProyectoDTO();
    private TareaDTO tarea = new TareaDTO();
    private TareaService tareaService = new TareaService();
    
    @FXML
    private Label lbTituloRegistro;
    @FXML
    private JFXComboBox<Integer> cbNivelUrgencia;
    @FXML
    private Label lbPrioridad;
    
    
    private String modalidadVista = "";
    @FXML
    private Button btnCancelar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Formato();
        initCBX();
        modalidadVista = (String) AppContext.getInstance().get("VistaProyecto");
        modalidad = (String) AppContext.getInstance().get("ModalidadTarea");
        proyecto = (ProyectoDTO) AppContext.getInstance().get("Proyecto");
        if(modalidad.equals("Agregar")){
            nivelAvance.setValue(0);
            lbFechaModificacion.setVisible(false);
            lbFechaRegistro.setVisible(false);
            lbTituloModificacion.setVisible(false);
            lbTituloRegistro.setVisible(false);  
            cbNivelImportancia.setValue(1);
            cbNivelUrgencia.setValue(1);
            establecerPrioridad();
            lbTitulo.setText("Registro Tarea - Proyecto "+proyecto.getNombre());
        }else{
            tarea = (TareaDTO) AppContext.getInstance().get("TareaEnCuestion");
            proyecto=tarea.getProyecto();
            lbTitulo.setText("Tarea "+tarea.getNombre()+" - Proyecto "+proyecto.getNombre());
            txtNombre.setText(tarea.getNombre());
            if(tarea.getDescipcion()!=null){
                txtDescripcion.setText(tarea.getDescipcion());
            }
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Instant instant = tarea.getFechaInicio().toInstant();
            dpFechaInicio.setValue(instant.atZone(defaultZoneId).toLocalDate());
            instant = tarea.getFechaFinalizacion().toInstant();
            dpFechaFinalizacion.setValue(instant.atZone(defaultZoneId).toLocalDate());
            
            setFechaInicio();
            setFechaFinalizacion();
            
            lbFechaRegistro.setText(formatter.format(tarea.getFechaRegistro()));
            lbFechaModificacion.setText(formatter.format(tarea.getFechaModificacion()));
            
            cbNivelImportancia.setValue(tarea.getImportancia());
            cbNivelUrgencia.setValue(tarea.getUrgencia());
            establecerPrioridad();
            nivelAvance.setValue(Double.valueOf(tarea.getPorcentajeAvance()));
            
        }
    }   
    
    public void Formato(){
        txtNombre.setTextFormatter(Formato.getInstance().maxLengthFormat(50));
        txtDescripcion.setTextFormatter(Formato.getInstance().maxLengthFormat(200));
    }
    
    public void establecerPrioridad(){
        int urgencia = cbNivelImportancia.getValue()*cbNivelUrgencia.getValue();
        lbPrioridad.setText(String.valueOf(urgencia));
    }
    
    public void initCBX(){
        ArrayList<Integer> niveles = new ArrayList<Integer>();
        niveles.add(1);
        niveles.add(2);
        niveles.add(3);
        ObservableList items = FXCollections.observableArrayList(niveles);   
        cbNivelImportancia.setItems(items); 
        cbNivelUrgencia.setItems(items); 
    }

    @FXML
    private void actGuardar(ActionEvent event) {
        if(validar()){
            tarea.setNombre(txtNombre.getText());
            if(txtDescripcion.getText().isBlank()){
                if(modalidad.equals("Modificar")){
                    tarea.setDescipcion("");
                }
            }else{
                tarea.setDescipcion(txtDescripcion.getText());
            }
            tarea.setPorcentajeAvance((float)((int)nivelAvance.getValue()));
            tarea.setImportancia(cbNivelImportancia.getValue());
            tarea.setUrgencia(cbNivelUrgencia.getValue());
            tarea.setFechaInicio(fechaInicioGuardar);
            tarea.setFechaFinalizacion(fechaFinalizacionGuardar);
            tarea.setProyecto(proyecto);
            
            if(modalidad.equals("Modificar")){
                Respuesta res= tareaService.modificar(tarea.getId(), tarea);
                if(res.getEstado()){
                    tarea = (TareaDTO) res.getResultado("Tarea");
                    if(modalidadVista.equals("TreeView")){
                        ProyectosTreeController proController = (ProyectosTreeController) AppContext.getInstance().get("ControllerProyecto");
                        proController.cargarTodos();
                    }
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Modificación de tarea", "La tarea fue modificada con éxito");
                    Stage stage = (Stage) btnGuardar.getScene().getWindow();
                    stage.close();
                }else{
                    Mensaje.showAndWait(Alert.AlertType.ERROR, "Modificación de tarea", "Hubo un error al modificar la tarea");
                }
            }else{
                if(modalidad.equals("Agregar")){
                    Respuesta res = tareaService.crear(tarea);
                    if(res.getEstado()){
                        tarea = (TareaDTO) res.getResultado("Tarea");
                        if(modalidadVista.equals("Creacion")){
                            ProyectoDetalleController proController = (ProyectoDetalleController) AppContext.getInstance().get("ControllerProyecto");
                            proController.cargarTabla();
                        }else{
                            ProyectosTreeController proController = (ProyectosTreeController) AppContext.getInstance().get("ControllerProyecto");
                            proController.cargarTodos();
                        }
                        Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de tarea", "La tarea fue registrada con éxito");
                        Stage stage = (Stage) btnGuardar.getScene().getWindow();
                        stage.close();
                    }else{
                        Mensaje.showAndWait(Alert.AlertType.ERROR, "Registro de tarea", "Hubo un error al registrar la tarea");
                    }
                }
            }
            
        }
    }

    @FXML
    private void actNivelImportancia(ActionEvent event) {
        establecerPrioridad();
    }

    @FXML
    private void actNivelUrgencia(ActionEvent event) {
        establecerPrioridad();
    }
    
    public boolean validar(){
        String titulo="";
        if(modalidad.equals("Agregar")){
            titulo = "Registro de tarea";
        }else{
            titulo = "Modificación de tarea";
        }
        
        if(txtNombre.getText().isBlank()){
            Mensaje.showAndWait(Alert.AlertType.WARNING, titulo, "Faltan datos por ingresar: nombre");
            return false;
        }
        if(dpFechaInicio.getValue()==null){
            Mensaje.showAndWait(Alert.AlertType.WARNING, titulo, "Faltan datos por ingresar: fecha de inicio");
            return false;
        }
        if(dpFechaFinalizacion.getValue()==null){
            Mensaje.showAndWait(Alert.AlertType.WARNING, titulo, "Faltan datos por ingresar: fecha de finalizacion");
            return false;
        }
        if(cbNivelImportancia.getValue()==null){
            Mensaje.showAndWait(Alert.AlertType.WARNING, titulo, "Faltan datos por ingresar: nivel de importancia");
            return false;
        }
        if(cbNivelUrgencia.getValue()==null){
            Mensaje.showAndWait(Alert.AlertType.WARNING, titulo, "Faltan datos por ingresar: nivel de urgencia");
            return false;
        }
        return true;
    }
    
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date fechaInicioGuardar;
    
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date fechaFinalizacionGuardar;

    @FXML
    private void actSelFechaInicio(ActionEvent event) {
        setFechaInicio();
    }

    public void setFechaInicio(){
        Calendar dateCalendar = Calendar.getInstance();
        String fecha[]=dpFechaInicio.getValue().toString().split("-");
        dateCalendar.set(Calendar.YEAR,Integer.valueOf(fecha[0]));
        dateCalendar.set(Calendar.MONTH, Integer.valueOf(fecha[1])-1);
        dateCalendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(fecha[2]));
        fechaInicioGuardar=dateCalendar.getTime();
    }
    
    public void setFechaFinalizacion(){
        Calendar dateCalendar = Calendar.getInstance();
        String fecha[]=dpFechaFinalizacion.getValue().toString().split("-");
        dateCalendar.set(Calendar.YEAR,Integer.valueOf(fecha[0]));
        dateCalendar.set(Calendar.MONTH, Integer.valueOf(fecha[1])-1);
        dateCalendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(fecha[2]));
        fechaFinalizacionGuardar=dateCalendar.getTime();
    }
    
    @FXML
    private void actSelFechaFinalizacion(ActionEvent event) {
        setFechaFinalizacion();
    }

    @FXML
    private void actCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
}
