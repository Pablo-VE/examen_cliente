/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import java.awt.Rectangle;
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
import javafx.geometry.Pos;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.examen.cliente.App;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.RangoColor;
import org.una.examen.cliente.util.Rangos;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class RangosController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<RangoColor> tvRangos;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("RangosController", this);
        cargarTabla();
    }    

    @FXML
    private void actAgregar(ActionEvent event) {
        try{
                Stage stage = new Stage();
                AppContext.getInstance().set("ModalidadRango", "Agregar");
                Parent root = FXMLLoader.load(App.class.getResource("RangoDetalle" + ".fxml"));
                stage.setScene(new Scene(root));
                stage.setTitle("Configuración de rango y color");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
                stage.show();
            }catch(IOException ex){
                System.out.println(ex.getMessage());
                Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
            };
    }

    @FXML
    private void actGuardar(ActionEvent event) {
        Rangos.getInstance().guardar();
        Rangos.getInstance().cargar();
        cargarTabla();
    }
    
    public void cargarTabla(){
        ArrayList<RangoColor> rangos = Rangos.getInstance().getRangos();
        tvRangos.getColumns().clear();
        if(!rangos.isEmpty()){
            ObservableList items = FXCollections.observableArrayList(rangos);   
            
            TableColumn <RangoColor, Integer>colMin = new TableColumn("Valor Mínimo");
            colMin.setCellValueFactory(new PropertyValueFactory("min"));
            
            TableColumn <RangoColor, Integer>colMax = new TableColumn("Valor Máximo");
            colMax.setCellValueFactory(new PropertyValueFactory("max"));
            
            TableColumn <RangoColor, String>colColor = new TableColumn("Color");
            colColor.setCellValueFactory(new PropertyValueFactory("col"));
            
            tvRangos.getColumns().addAll(colMin);
            tvRangos.getColumns().addAll(colMax);
            tvRangos.getColumns().addAll(colColor);
            addButtonsToTable();
            
            tvRangos.setItems(items);
        }else{
            TableColumn <RangoColor, Integer>colMin = new TableColumn("Valor Mínimo");
            TableColumn <RangoColor, Integer>colMax = new TableColumn("Valor Máximo");
            TableColumn <RangoColor, Integer>colColor = new TableColumn("Color");
            
            
            tvRangos.getColumns().add(colMin);
            tvRangos.getColumns().add(colMax);
            tvRangos.getColumns().add(colColor);
        }
    }
    
    private void addButtonsToTable() {
        TableColumn<RangoColor, Void> colBtn = new TableColumn("Color");

        Callback<TableColumn<RangoColor, Void>, TableCell<RangoColor, Void>> cellFactory = new Callback<TableColumn<RangoColor, Void>, TableCell<RangoColor, Void>>() {
            @Override
            public TableCell<RangoColor, Void> call(final TableColumn<RangoColor, Void> param) {
                final TableCell<RangoColor, Void> cell = new TableCell<RangoColor, Void>() {
                    
                    
                    private final Button modificar = new Button("Modificar");

                    {
                       modificar.setOnAction((ActionEvent event) -> {
                            try{
                                RangoColor data1 = getTableView().getItems().get(getIndex());
                                try{
                                    Stage stage = new Stage();
                                    AppContext.getInstance().set("ModalidadRango", "Modificar");
                                    AppContext.getInstance().set("Rango", data1);
                                    Parent root = FXMLLoader.load(App.class.getResource("RangoDetalle" + ".fxml"));
                                    stage.setScene(new Scene(root));
                                    stage.setTitle("Configuración de rango y color");
                                    stage.initModality(Modality.WINDOW_MODAL);
                                    stage.initOwner(
                                        ((Node)event.getSource()).getScene().getWindow() );
                                    stage.show();
                                }catch(IOException ex){
                                    System.out.println(ex.getMessage());
                                    Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
                                };
                            }catch(Exception ex){}
                        });
                    }
                    
                    private final Button eliminar = new Button("Eliminar");

                    {
                       eliminar.setOnAction((ActionEvent event) -> {
                            
                                RangoColor data1 = getTableView().getItems().get(getIndex());
                                Rangos.getInstance().eliminarRango(data1);
                                cargarTabla();
                                
                        });
                    }
                    
                    
                    
                    HBox pane = new HBox(modificar, eliminar); 
                    
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

        tvRangos.getColumns().add(colBtn);

    }
    
}
