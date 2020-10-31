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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.examen.cliente.App;
import org.una.examen.cliente.dto.UnidadDTO;
import org.una.examen.cliente.service.UnidadService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Jeffry
 */
public class UnidadesController implements Initializable {

    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXComboBox<String> cbTipoBusqueda;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private Label lblInfo;
    @FXML
    private TableView<UnidadDTO> tableView;
    
    private UnidadService unidadService = new UnidadService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AppContext.getInstance().set("UnidadController", this);
        ArrayList tiposBusqueda = new ArrayList();
        tiposBusqueda.add("Por Nombre");
        tiposBusqueda.add("Por Código");
        tiposBusqueda.add("Por Área");
        tiposBusqueda.add("Por Población");
        tiposBusqueda.add("Por Distrito");
        ObservableList items = FXCollections.observableArrayList(tiposBusqueda);   
        cbTipoBusqueda.setItems(items);
        cargarTodos();
    }
    
    public void cargarTodos(){
        ArrayList<UnidadDTO> unidades = new ArrayList<UnidadDTO>();
        Respuesta respuesta = unidadService.getAll();
        if(respuesta.getEstado()){
            unidades = (ArrayList<UnidadDTO>) respuesta.getResultado("Unidades");
        }else{
            System.out.println(respuesta.getMensaje());
        }
        cargarTabla(unidades);
        
    }
    
    public void cargarTabla(ArrayList<UnidadDTO> cantones){
        tableView.getColumns().clear();
        if(!cantones.isEmpty()){
            ObservableList items = FXCollections.observableArrayList(cantones);   
            
            TableColumn <UnidadDTO, Long>colCodigo = new TableColumn("Código");
            colCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
            TableColumn <UnidadDTO, String>colNombre = new TableColumn("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            
            TableColumn <UnidadDTO, String>colArea = new TableColumn("Área");
            colArea.setCellValueFactory(new PropertyValueFactory("area"));
            
            TableColumn <UnidadDTO, String>colPoblacion = new TableColumn("Población");
            colPoblacion.setCellValueFactory(new PropertyValueFactory("poblacion"));
            
            TableColumn <UnidadDTO, String>colDistrito = new TableColumn("Distrito");
            colDistrito.setCellValueFactory(new PropertyValueFactory("distrito"));
            
            TableColumn <UnidadDTO, String>colTipo = new TableColumn("Tipo");
            colTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
            
            tableView.getColumns().addAll(colCodigo);
            tableView.getColumns().addAll(colNombre);
            tableView.getColumns().addAll(colArea);
            tableView.getColumns().addAll(colPoblacion);
            tableView.getColumns().addAll(colTipo);
            tableView.getColumns().addAll(colDistrito);
            addButtonToTable();
            tableView.setItems(items);
        }else{
            tableView.setVisible(false);
            lblInfo.setText("No hay información para mostrar");
        }
    }
    
    public void ver(UnidadDTO unidad){
        AnchorPane Contenedor = (AnchorPane) AppContext.getInstance().get("apContenedor");
        AppContext.getInstance().set("ModalidadUnidad", "Ver");
        AppContext.getInstance().set("UnidadEnCuestion", unidad);
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("UnidadDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Unidad");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
    
    public void modificar(UnidadDTO canton){
        AnchorPane Contenedor = (AnchorPane) AppContext.getInstance().get("apContenedor");
        AppContext.getInstance().set("ModalidadUnidad", "Modificar");
        AppContext.getInstance().set("UnidadEnCuestion", canton);
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("UnidadDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar Unidad");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
    
    private void addButtonToTable() {
        TableColumn<UnidadDTO, Void> colBtn = new TableColumn("Acciones");

        Callback<TableColumn<UnidadDTO, Void>, TableCell<UnidadDTO, Void>> cellFactory = new Callback<TableColumn<UnidadDTO, Void>, TableCell<UnidadDTO, Void>>() {
            @Override
            public TableCell<UnidadDTO, Void> call(final TableColumn<UnidadDTO, Void> param) {
                final TableCell<UnidadDTO, Void> cell = new TableCell<UnidadDTO, Void>() {
                    //Image image = new Image(getClass().getResourceAsStream("recursos/editar.png"));    
                    private final JFXButton btnEditar = new JFXButton("Editar"/*, new ImageView(image)*/);
                    
                    {
                        btnEditar.setOnAction((ActionEvent event) -> {
                            try{
                            UnidadDTO data = getTableView().getItems().get(getIndex());
                            modificar(data);
                            }catch(Exception ex){}
                        });
                    }
                    
                    //Image image2 = new Image(getClass().getResourceAsStream("recursos/ver.png"));    
                    private final JFXButton btnVer = new JFXButton("Ver"/*, new ImageView(image)*/);
                    
                    {
                        btnVer.setOnAction((ActionEvent event) -> {
                            try{
                            UnidadDTO data = getTableView().getItems().get(getIndex());
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
                ArrayList<UnidadDTO> provincias = new ArrayList<UnidadDTO>();
                Respuesta respuesta = unidadService.getByNombre(txtBuscar.getText());
                if(respuesta.getEstado()){
                    provincias = (ArrayList<UnidadDTO>) respuesta.getResultado("Unidades");  
                }else{
                    System.out.println(respuesta.getMensaje());
                }
                cargarTabla(provincias);
            }
            if(tipo.equals("Por Código")){
                UnidadDTO canton = new UnidadDTO();
                Respuesta respuesta = unidadService.getByCodigo(Integer.valueOf(txtBuscar.getText()));
                ArrayList<UnidadDTO> cantones = new ArrayList<UnidadDTO>();
                if(respuesta.getEstado()){
                    canton = (UnidadDTO) respuesta.getResultado("Unidad");  
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
            AppContext.getInstance().set("ModalidadUnidad", "Agregar");
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("UnidadDetalle" + ".fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Agregar Unidad");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
    
}
