/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static javafx.scene.paint.Color.RED;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.una.examen.cliente.App;
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.dto.TareaDTO;
import org.una.examen.cliente.service.ProyectoService;
import org.una.examen.cliente.service.TareaService;
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
    
    
    boolean creado=false;
    @FXML
    private Button btnListo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modalidad=(String) AppContext.getInstance().get("ModalidadProyecto");
        cargarTabla();
        if(modalidad.equals("Agregar")){
            lbFechaRegistro.setVisible(false);
            btnAgregarTarea.setDisable(true);
            btnAgregarTarea.setVisible(false);
            proyecto = new ProyectoDTO();
            lbTitulo.setText("CREACIÓN DE PROYECTO");
        }else{
            creado=true;
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
                    creado=true;
                    txtNombre.setDisable(true);
                    txtDescripcion.setDisable(true);
                    txtResponsable.setDisable(true);
                   
                }else{
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de proyecto", "Ocurrió un error al registrar su proyecto");
                }
            }else{
                
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
        if(creado){
            try{
                Stage stage = new Stage();
                AppContext.getInstance().set("ModalidadTarea", "Agregar");
                AppContext.getInstance().set("Proyecto", proyecto);
                AppContext.getInstance().set("VistaProyecto", "Creacion");
                AppContext.getInstance().set("ControllerProyecto", this);
                Parent root = FXMLLoader.load(App.class.getResource("TareasDetalle" + ".fxml"));
                stage.setScene(new Scene(root));
                stage.setTitle("Registro de tarea");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
                stage.show();
            }catch(IOException ex){
                System.out.println(ex.getMessage());
                Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
            };
        }
    }
    
    public void cargarTabla(){
        TareaService tareaService = new TareaService();
        ArrayList<TareaDTO> tareas = new ArrayList<TareaDTO>();
        Respuesta respuesta = tareaService.getByProyecto(proyecto.getId());
        if(respuesta.getEstado().equals(true)){
            tareas = (ArrayList<TareaDTO>) respuesta.getResultado("Tareas");
            
        }
        
        tvTareas.getColumns().clear();
        if(!tareas.isEmpty()){
            ObservableList items = FXCollections.observableArrayList(tareas);   
            
            TableColumn <TareaDTO, Long>colId = new TableColumn("ID");
            colId.setCellValueFactory(new PropertyValueFactory("id"));
            
            TableColumn <TareaDTO, String>colNombre = new TableColumn("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            
            TableColumn <TareaDTO, String>colPrioridad = new TableColumn("Prioridad");
            colPrioridad.setCellValueFactory(ta -> {
                String prioridad = String.valueOf(ta.getValue().getImportancia()*ta.getValue().getUrgencia());
                return new ReadOnlyStringWrapper(prioridad);
            });
            
            TableColumn <TareaDTO, String>colFechaInicio = new TableColumn("Fecha de inicio");
            colFechaInicio.setCellValueFactory(ta -> {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = formatter.format(ta.getValue().getFechaInicio());
                return new ReadOnlyStringWrapper(fecha);
            });
            
            TableColumn <TareaDTO, String>colFechaFinalizacion = new TableColumn("Fecha de finalización");
            colFechaFinalizacion.setCellValueFactory(ta -> {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = formatter.format(ta.getValue().getFechaFinalizacion());
                return new ReadOnlyStringWrapper(fecha);
            });
            
            TableColumn<TareaDTO, String> colAvance = new TableColumn("Porcentaje de avance");
            colAvance.setCellValueFactory(ta -> {
                String avance = String.valueOf(ta.getValue().getPorcentajeAvance()+"%");
                return new ReadOnlyStringWrapper(avance);
            });
            
            
            tvTareas.getColumns().addAll(colId);
            tvTareas.getColumns().addAll(colNombre);
            tvTareas.getColumns().addAll(colPrioridad);
            tvTareas.getColumns().addAll(colAvance);
            tvTareas.getColumns().addAll(colFechaInicio);
            tvTareas.getColumns().addAll(colFechaFinalizacion);
            tvTareas.setItems(items);
        }else{
            TableColumn <TareaDTO, Long>colId = new TableColumn("ID");
            TableColumn <TareaDTO, String>colNombre = new TableColumn("Nombre");
            TableColumn <TareaDTO, String>colPrioridad = new TableColumn("Prioridad");
            TableColumn <TareaDTO, String>colFechaInicio = new TableColumn("Fecha de inicio");
            TableColumn <TareaDTO, String>colFechaFinalizacion = new TableColumn("Fecha de finalización");
            TableColumn<TareaDTO, String> colAvance = new TableColumn("Porcentaje de avance");
            
            
            tvTareas.getColumns().add(colId);
            tvTareas.getColumns().add(colNombre);
            tvTareas.getColumns().add(colPrioridad);
            tvTareas.getColumns().add(colAvance);
            tvTareas.getColumns().add(colFechaInicio);
            tvTareas.getColumns().add(colFechaFinalizacion);
        }
    }

    @FXML
    private void actListo(ActionEvent event) {
    }
    
}
