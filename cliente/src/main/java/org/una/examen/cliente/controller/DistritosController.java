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
import java.util.ResourceBundle;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.examen.cliente.App;
import org.una.examen.cliente.dto.CantonDTO;
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.service.CantonService;
import org.una.examen.cliente.service.DistritoService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.AreaYPoblacion;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Jeffry
 */
public class DistritosController implements Initializable {

    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXComboBox<String> cbTipoBusqueda;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private Label lblInfo;
    @FXML
    private TableView<DistritoDTO> tableView;
    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private JFXComboBox<CantonDTO> cbCanton;
    @FXML
    private JFXButton btnBuscarCanton;
    
    private DistritoService distritoService = new DistritoService();
    AreaYPoblacion ap = new AreaYPoblacion();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AppContext.getInstance().set("DistritoController", this);
        ArrayList tiposBusqueda = new ArrayList();
        tiposBusqueda.add("Por Nombre");
        tiposBusqueda.add("Por Código");
        tiposBusqueda.add("Por Área");
        tiposBusqueda.add("Por Población");
        tiposBusqueda.add("Por Cantón");
        ObservableList items = FXCollections.observableArrayList(tiposBusqueda);   
        cbTipoBusqueda.setItems(items);
        cargarTodos();
        initCantones();
    }    

    public void cargarTodos(){
        ArrayList<DistritoDTO> distritos = new ArrayList<DistritoDTO>();
        Respuesta respuesta = distritoService.getAll();
        if(respuesta.getEstado()){
            distritos = (ArrayList<DistritoDTO>) respuesta.getResultado("Distritos");
        }else{
            System.out.println(respuesta.getMensaje());
        }
        cargarTabla(distritos);
        
    }
    
    public void cargarTabla(ArrayList<DistritoDTO> distritos){
        tableView.getColumns().clear();
        if(!distritos.isEmpty()){
            ap.setAreaPoblacionDistritos(distritos);
            ObservableList items = FXCollections.observableArrayList(distritos);   
            
            TableColumn <DistritoDTO, Long>colCodigo = new TableColumn("Código");
            colCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
            TableColumn <DistritoDTO, String>colNombre = new TableColumn("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            TableColumn <DistritoDTO, String>colCanton = new TableColumn("Cantón");
            colCanton.setCellValueFactory(new PropertyValueFactory("canton"));
            
            TableColumn <DistritoDTO, String>colArea = new TableColumn("Área");
            colArea.setCellValueFactory(new PropertyValueFactory("area"));
            TableColumn <DistritoDTO, String>colPoblacion = new TableColumn("Población");
            colPoblacion.setCellValueFactory(new PropertyValueFactory("poblacion"));
            
            tableView.getColumns().addAll(colCodigo);
            tableView.getColumns().addAll(colNombre);
            tableView.getColumns().addAll(colCanton);
            tableView.getColumns().addAll(colArea);
            tableView.getColumns().addAll(colPoblacion);
            addButtonToTable();
            tableView.setItems(items);
        }else{
            tableView.setVisible(false);
            lblInfo.setText("No hay información para mostrar");
        }
    }
    
    public void ver(DistritoDTO bitacora){
        AnchorPane Contenedor = (AnchorPane) AppContext.getInstance().get("apContenedor");
        AppContext.getInstance().set("ModalidadDistrito", "Ver");
        AppContext.getInstance().set("DistritoEnCuestion", bitacora);
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("DistritoDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Ver Distrito");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
    
    public void modificar(DistritoDTO bitacora){
        AnchorPane Contenedor = (AnchorPane) AppContext.getInstance().get("apContenedor");
        AppContext.getInstance().set("ModalidadDistrito", "Modificar");
        AppContext.getInstance().set("DistritoEnCuestion", bitacora);
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("DistritoDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Distrito");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
    
    private void addButtonToTable() {
        TableColumn<DistritoDTO, Void> colBtn = new TableColumn("Acciones");

        Callback<TableColumn<DistritoDTO, Void>, TableCell<DistritoDTO, Void>> cellFactory = new Callback<TableColumn<DistritoDTO, Void>, TableCell<DistritoDTO, Void>>() {
            @Override
            public TableCell<DistritoDTO, Void> call(final TableColumn<DistritoDTO, Void> param) {
                final TableCell<DistritoDTO, Void> cell = new TableCell<DistritoDTO, Void>() {
                    //Image image = new Image(getClass().getResourceAsStream("recursos/editar.png"));    
                    private final JFXButton btnEditar = new JFXButton("Editar"/*, new ImageView(image)*/);
                    
                    {
                        btnEditar.setOnAction((ActionEvent event) -> {
                            try{
                            DistritoDTO data = getTableView().getItems().get(getIndex());
                            modificar(data);
                            }catch(Exception ex){}
                        });
                    }
                    
                    //Image image2 = new Image(getClass().getResourceAsStream("recursos/ver.png"));    
                    private final JFXButton btnVer = new JFXButton("Ver"/*, new ImageView(image)*/);
                    
                    {
                        btnVer.setOnAction((ActionEvent event) -> {
                            try{
                            DistritoDTO data = getTableView().getItems().get(getIndex());
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
        if(!txtBuscar.getText().isEmpty()){
            if(tipo.equals("Por Nombre")){
                ArrayList<DistritoDTO> provincias = new ArrayList<DistritoDTO>();
                Respuesta respuesta = distritoService.getByNombre(txtBuscar.getText());
                if(respuesta.getEstado()){
                    provincias = (ArrayList<DistritoDTO>) respuesta.getResultado("Distritos");  
                }else{
                    System.out.println(respuesta.getMensaje());
                }
                cargarTabla(provincias);
            }
            if(tipo.equals("Por Código")){
                DistritoDTO canton = new DistritoDTO();
                Respuesta respuesta = distritoService.getByCodigo(Integer.valueOf(txtBuscar.getText()));
                ArrayList<DistritoDTO> cantones = new ArrayList<DistritoDTO>();
                if(respuesta.getEstado()){
                    canton = (DistritoDTO) respuesta.getResultado("Distrito");  
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
            AppContext.getInstance().set("ModalidadDistrito", "Agregar");
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("DistritoDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Agregar Distrito");
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
       
    }

    @FXML
    private void actBuscarCanton(ActionEvent event) {
        if(cbCanton.getValue() != null){
            ArrayList<DistritoDTO> provincias = new ArrayList<DistritoDTO>();
            Respuesta respuesta = distritoService.getByCanton(cbCanton.getValue().getId());
            if(respuesta.getEstado()){
                provincias = (ArrayList<DistritoDTO>) respuesta.getResultado("Distritos");  
            }else{
                System.out.println(respuesta.getMensaje());
            }
            cargarTabla(provincias);
        }
    }
    
    public void initCantones(){
        CantonService cantonService = new CantonService();
        ArrayList<CantonDTO> cantones;
        Respuesta respuesta = cantonService.getAll();
        if(respuesta.getEstado()){
            cantones = (ArrayList<CantonDTO>) respuesta.getResultado("Cantones");
            ObservableList items = FXCollections.observableArrayList(cantones);
            cbCanton.setItems(items);
        }
    }
}
