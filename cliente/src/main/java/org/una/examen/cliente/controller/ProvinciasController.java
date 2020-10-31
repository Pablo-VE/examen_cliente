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
public class ProvinciasController implements Initializable {

    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXComboBox<String> cbTipoBusqueda;
    @FXML
    private TableView<ProvinciaDTO> tableView;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private Label lblInfo;
    @FXML
    private final JFXButton btnEditar = new JFXButton();
    @FXML
    private final JFXButton btnVer = new JFXButton();
    
    
    private ProvinciaService provinciaService = new ProvinciaService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AppContext.getInstance().set("ProvinciaController", this);
        ArrayList tiposBusqueda = new ArrayList();
        tiposBusqueda.add("Por Nombre");
        tiposBusqueda.add("Por Código");
        tiposBusqueda.add("Por Área");
        tiposBusqueda.add("Por Población");
        ObservableList items = FXCollections.observableArrayList(tiposBusqueda);   
        cbTipoBusqueda.setItems(items);
        cargarTodos();
        
    }    
    
    public void cargarTodos(){
        ArrayList<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();
        Respuesta respuesta = provinciaService.getAll();
        if(respuesta.getEstado()){
            provincias = (ArrayList<ProvinciaDTO>) respuesta.getResultado("Provincias");  
        }else{
            System.out.println(respuesta.getMensaje());
        }
        
        cargarTabla(provincias);
        
    }
    
    public void cargarTabla(ArrayList<ProvinciaDTO> aerolineas){
        tableView.getColumns().clear();
        if(!aerolineas.isEmpty()){
            ObservableList items = FXCollections.observableArrayList(aerolineas);   
            
            TableColumn <ProvinciaDTO, Long>colId = new TableColumn("ID");
            colId.setCellValueFactory(new PropertyValueFactory("id"));
            TableColumn <ProvinciaDTO, Long>colCodigo = new TableColumn("Código");
            colCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
            TableColumn <ProvinciaDTO, String>colNombre = new TableColumn("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            //TableColumn <ProvinciaDTO, String>colArea = new TableColumn("Área");
            //colResponsable.setCellValueFactory(new PropertyValueFactory("responsable"));
            //TableColumn <ProvinciaDTO, String>colPoblacion = new TableColumn("Población");
            
            tableView.getColumns().addAll(colId);
            tableView.getColumns().addAll(colCodigo);
            tableView.getColumns().addAll(colNombre);
            //tableView.getColumns().addAll(colArea);
            //tableView.getColumns().addAll(colPoblacion);
            addButtonToTable();
            tableView.setItems(items);
        }else{
            tableView.setVisible(false);
            lblInfo.setText("No hay información para mostrar");
        }
    }
    
    public void ver(ProvinciaDTO provincia){

        AnchorPane Contenedor = (AnchorPane) AppContext.getInstance().get("apContenedor");
        AppContext.getInstance().set("ModalidadProvincia", "Ver");
        AppContext.getInstance().set("ProvinciaEnCuestion", provincia);
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("ProvinciaDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Provincia");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
    
    public void modificar(ProvinciaDTO provincia){
        AnchorPane Contenedor = (AnchorPane) AppContext.getInstance().get("apContenedor");
        AppContext.getInstance().set("ModalidadProvincia", "Modificar");
        AppContext.getInstance().set("ProvinciaEnCuestion", provincia);
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("ProvinciaDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar Provincia");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
    
    private void addButtonToTable() {
        TableColumn<ProvinciaDTO, Void> colBtn = new TableColumn("Acciones");

        Callback<TableColumn<ProvinciaDTO, Void>, TableCell<ProvinciaDTO, Void>> cellFactory = new Callback<TableColumn<ProvinciaDTO, Void>, TableCell<ProvinciaDTO, Void>>() {
            @Override
            public TableCell<ProvinciaDTO, Void> call(final TableColumn<ProvinciaDTO, Void> param) {
                final TableCell<ProvinciaDTO, Void> cell = new TableCell<ProvinciaDTO, Void>() {
                    //Image image = new Image(getClass().getResourceAsStream("editar.png"));    
                    private final JFXButton btnEditar = new JFXButton("Editar"/*, new ImageView(image)*/);
                    
                    {
                        btnEditar.setOnAction((ActionEvent event) -> {
                            try{
                            ProvinciaDTO data = getTableView().getItems().get(getIndex());
                            modificar(data);
                            }catch(Exception ex){}
                        });
                    }
                    
                    //Image image2 = new Image(getClass().getResourceAsStream("ver.png"));    
                    private final JFXButton btnVer = new JFXButton("Ver"/*, new ImageView(image)*/);
                    
                    {
                        btnVer.setOnAction((ActionEvent event) -> {
                            try{
                            ProvinciaDTO data = getTableView().getItems().get(getIndex());
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
                ArrayList<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();
                Respuesta respuesta = provinciaService.getByNombre(txtBuscar.getText());
                if(respuesta.getEstado()){
                    provincias = (ArrayList<ProvinciaDTO>) respuesta.getResultado("Provincias");  
                }else{
                    System.out.println(respuesta.getMensaje());
                }
                cargarTabla(provincias);
            }
            if(tipo.equals("Por Código")){
                ArrayList<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();
                Respuesta respuesta = provinciaService.getByCodigo(Integer.valueOf(txtBuscar.getText()));
                if(respuesta.getEstado()){
                    ProvinciaDTO provincia = (ProvinciaDTO) respuesta.getResultado("Provincia");
                    
                    provincias.add(provincia);
                }else{
                    System.out.println(respuesta.getMensaje());
                }
                cargarTabla(provincias);
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
            AppContext.getInstance().set("ModalidadProvincia", "Agregar");
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("ProvinciaDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Agregar Provincia");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
    
}
