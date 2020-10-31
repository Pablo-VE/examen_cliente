/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.examen.cliente.App;
import org.una.examen.cliente.dto.CantonDTO;
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.dto.ProvinciaDTO;
import org.una.examen.cliente.dto.UnidadDTO;
import org.una.examen.cliente.service.CantonService;
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
public class CantonesController implements Initializable {

    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXComboBox<String> cbTipoBusqueda;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private Label lblInfo;
    @FXML
    private TableView<CantonDTO> tableView;
    
    private CantonService cantonService = new CantonService();
    @FXML
    private JFXComboBox<Object> cbBusqueda;
    @FXML
    private JFXComboBox<ProvinciaDTO> cbProvincia;
    @FXML
    private JFXButton btnBuscarProvincia;
    
    AreaYPoblacion ap = new AreaYPoblacion();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AppContext.getInstance().set("CantonController", this);
        ArrayList tiposBusqueda = new ArrayList();
        tiposBusqueda.add("Por Nombre");
        tiposBusqueda.add("Por Código");
        tiposBusqueda.add("Por Área");
        tiposBusqueda.add("Por Población");
        tiposBusqueda.add("Por Provincia");
        ObservableList items = FXCollections.observableArrayList(tiposBusqueda);   
        cbTipoBusqueda.setItems(items);
        cargarTodos();
        initProvincias();
    }    

    public void cargarTodos(){
        ArrayList<CantonDTO> cantones = new ArrayList<CantonDTO>();
        Respuesta respuesta = cantonService.getAll();
        if(respuesta.getEstado()){
            cantones = (ArrayList<CantonDTO>) respuesta.getResultado("Cantones");
        }else{
            System.out.println(respuesta.getMensaje());
        }
        cargarTabla(cantones);
        
    }
    
    public void cargarTabla(ArrayList<CantonDTO> cantones){
        tableView.getColumns().clear();
        if(!cantones.isEmpty()){
            ap.setAreaPoblacionCantones(cantones);
            
            
            ObservableList items = FXCollections.observableArrayList(cantones);   
            
            TableColumn <CantonDTO, Long>colCodigo = new TableColumn("Código");
            colCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
            TableColumn <CantonDTO, String>colNombre = new TableColumn("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            
            TableColumn <CantonDTO, String>colArea = new TableColumn("Área");
            colArea.setCellValueFactory(new PropertyValueFactory("area"));
            TableColumn <CantonDTO, String>colPoblacion = new TableColumn("Población");
            colPoblacion.setCellValueFactory(new PropertyValueFactory("poblacion"));
            TableColumn <CantonDTO, String>colProvincia = new TableColumn("Provincia");
            colProvincia.setCellValueFactory(new PropertyValueFactory("provincia"));
            
            tableView.getColumns().addAll(colCodigo);
            tableView.getColumns().addAll(colNombre);
            tableView.getColumns().addAll(colArea);
            tableView.getColumns().addAll(colPoblacion);
            tableView.getColumns().addAll(colProvincia);
            addButtonToTable();
            tableView.setItems(items);
        }else{
            tableView.setVisible(false);
            lblInfo.setText("No hay información para mostrar");
        }
    }
    
    public void ver(CantonDTO canton){
        AnchorPane Contenedor = (AnchorPane) AppContext.getInstance().get("apContenedor");
        AppContext.getInstance().set("ModalidadCanton", "Ver");
        AppContext.getInstance().set("CantonEnCuestion", canton);
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("CantonDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Cantón");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
    
    public void modificar(CantonDTO canton){
        AnchorPane Contenedor = (AnchorPane) AppContext.getInstance().get("apContenedor");
        AppContext.getInstance().set("ModalidadCanton", "Modificar");
        AppContext.getInstance().set("CantonEnCuestion", canton);
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("CantonDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar Cantón");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
    
    private void addButtonToTable() {
        TableColumn<CantonDTO, Void> colBtn = new TableColumn("Acciones");

        Callback<TableColumn<CantonDTO, Void>, TableCell<CantonDTO, Void>> cellFactory = new Callback<TableColumn<CantonDTO, Void>, TableCell<CantonDTO, Void>>() {
            @Override
            public TableCell<CantonDTO, Void> call(final TableColumn<CantonDTO, Void> param) {
                final TableCell<CantonDTO, Void> cell = new TableCell<CantonDTO, Void>() {
                    //Image image = new Image(getClass().getResourceAsStream("recursos/editar.png"));    
                    private final JFXButton btnEditar = new JFXButton("Editar"/*, new ImageView(image)*/);
                    
                    {
                        btnEditar.setOnAction((ActionEvent event) -> {
                            try{
                            CantonDTO data = getTableView().getItems().get(getIndex());
                            modificar(data);
                            }catch(Exception ex){}
                        });
                    }
                    
                    //Image image2 = new Image(getClass().getResourceAsStream("recursos/ver.png"));    
                    private final JFXButton btnVer = new JFXButton("Ver"/*, new ImageView(image)*/);
                    
                    {
                        btnVer.setOnAction((ActionEvent event) -> {
                            try{
                            CantonDTO data = getTableView().getItems().get(getIndex());
                            ver(data);
                            }catch(Exception ex){}
                        });
                    }
                    
                    HBox pane = new HBox(btnEditar, btnVer);

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                            
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableView.getColumns().add(colBtn);
    }
    @FXML
    private void actBuscar(ActionEvent event) {
        String tipo = cbTipoBusqueda.getValue();
        if(cbBusqueda.getValue() != null){
            if(tipo.equals("Por Nombre")){
                ArrayList<CantonDTO> provincias = new ArrayList<CantonDTO>();
                Respuesta respuesta = cantonService.getByNombre(cbBusqueda.getValue().toString());
                if(respuesta.getEstado()){
                    provincias = (ArrayList<CantonDTO>) respuesta.getResultado("Cantones");  
                }else{
                    System.out.println(respuesta.getMensaje());
                }
                cargarTabla(provincias);
            }
            if(tipo.equals("Por Código")){
                CantonDTO canton = new CantonDTO();
                Respuesta respuesta = cantonService.getByCodigo(Integer.valueOf(cbBusqueda.getValue().toString()));
                ArrayList<CantonDTO> cantones = new ArrayList<CantonDTO>();
                if(respuesta.getEstado()){
                    canton = (CantonDTO) respuesta.getResultado("Canton");  
                    cantones.add(canton);
                }else{
                    System.out.println(respuesta.getMensaje());
                }
                cargarTabla(cantones);
            }
            if(tipo.equals("Por Provincia")){
                CantonDTO canton = new CantonDTO();
                ProvinciaDTO p = (ProvinciaDTO)cbBusqueda.getValue();
                System.out.println(p.getNombre());
                Respuesta respuesta = cantonService.getByProvincia(p.getId());
                ArrayList<CantonDTO> cantones = new ArrayList<CantonDTO>();
                if(respuesta.getEstado()){
                    canton = (CantonDTO) respuesta.getResultado("Cantones");  
                    cantones.add(canton);
                }else{
                    System.out.println(respuesta.getMensaje());
                }
                cargarTabla(cantones);
            }
            if(tipo.equals("Por Área")){
//                ArrayList<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();
//                Respuesta respuesta = provinciaService.getByCodigo(Integer.valueOf(txtBuscar.getText()));
//                if(respuesta.getEstado()){
//                    provincias = (ArrayList<ProvinciaDTO>) respuesta.getResultado("Provincias");  
//                }else{
//                    System.out.println(respuesta.getMensaje());
//                }
//                cargarTabla(provincias);
            }
            if(tipo.equals("Por Población")){
//                ArrayList<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();
//                Respuesta respuesta = provinciaService.getByCodigo(Integer.valueOf(txtBuscar.getText()));
//                if(respuesta.getEstado()){
//                    provincias = (ArrayList<ProvinciaDTO>) respuesta.getResultado("Provincias");  
//                }else{
//                    System.out.println(respuesta.getMensaje());
//                }
//                cargarTabla(provincias);
            }
        }
    }

    @FXML
    private void actAgregar(ActionEvent event) {
        try{
            AppContext.getInstance().set("ModalidadCanton", "Agregar");
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("CantonDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Agregar Cantón");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }

    @FXML
    private void actTipo(ActionEvent event) {
        if(cbTipoBusqueda.getValue().equals("Por Provincia")){
            ProvinciaService provinciaService = new ProvinciaService();
            ArrayList<ProvinciaDTO> provincias;
            Respuesta respuesta = provinciaService.getAll();
            if(respuesta.getEstado()){
                provincias = (ArrayList<ProvinciaDTO>) respuesta.getResultado("Provincias");
                ObservableList items = FXCollections.observableArrayList(provincias);
                cbBusqueda.setItems(items);
            }
        }
    }

    @FXML
    private void actBuscarProvincia(ActionEvent event) {
    if(cbProvincia.getValue() != null){
            ArrayList<CantonDTO> provincias = new ArrayList<CantonDTO>();
            Respuesta respuesta = cantonService.getByProvincia(cbProvincia.getValue().getId());
            if(respuesta.getEstado()){
                provincias = (ArrayList<CantonDTO>) respuesta.getResultado("Cantones");  
            }else{
                System.out.println(respuesta.getMensaje());
            }
            cargarTabla(provincias);
        }
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
