/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.una.examen.cliente.App;
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.service.ProyectoService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class ProyectosPrincipalController implements Initializable {

    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private Button actBuscarNombre;
    @FXML
    private Button btnBuscarProyecto;
    @FXML
    private Label lbTitulo;
    @FXML
    private StackPane ContenedorProyectos;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnCrearProyecto;
    @FXML
    private Button btnVerProyectos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("ControllerPrincipal", this);
    }    

    
    
    private ProyectoService proyectoService = new ProyectoService();
    @FXML
    private void actBuscarNombre(ActionEvent event) {
        if(txtBusqueda.getText().isBlank()){
            Mensaje.show(Alert.AlertType.WARNING, "Buscar proyectos", "Por favor escriba el nombre del responsable del proyecto en el campo de búsqueda");
        }else{
            List<ProyectoDTO> proyectos = new ArrayList<ProyectoDTO>();
            ProyectoService proyectoService = new ProyectoService();
            Respuesta res = proyectoService.getByNombre(txtBusqueda.getText());
            if(res.getEstado()){
                proyectos = (List<ProyectoDTO>) res.getResultado("Proyectos");
                System.out.println(proyectos.size());
                try{
                    AppContext.getInstance().set("ModalidadProyectosTree", "Busqueda");
                    AppContext.getInstance().set("ProyectosFiltrados", proyectos);
                    Parent root = FXMLLoader.load(App.class.getResource("ProyectosTree" + ".fxml"));
                    ContenedorProyectos.getChildren().clear();
                    ContenedorProyectos.getChildren().add(root);
                }catch(IOException ex){
                    Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
                };
            }else{
                Mensaje.showAndWait(Alert.AlertType.ERROR, "Búsqueda de proyectos", "No se encontro ningun proyecto con el responsable "+txtBusqueda.getText());
            }
                
        }
    }

    @FXML
    private void actBuscarProyecto(ActionEvent event) {
        if(txtBusqueda.getText().isBlank()){
            Mensaje.show(Alert.AlertType.WARNING, "Buscar proyectos", "Por favor escriba el nombre del proyecto en el campo de búsqueda");
        }else{
            List<ProyectoDTO> proyectos = new ArrayList<ProyectoDTO>();
            ProyectoService proyectoService = new ProyectoService();
            Respuesta res = proyectoService.getByResponsable(txtBusqueda.getText());
            if(res.getEstado()){
                proyectos = (List<ProyectoDTO>) res.getResultado("Proyectos");
                try{
                    AppContext.getInstance().set("ModalidadProyectosTree", "Busqueda");
                    AppContext.getInstance().set("ProyectosFiltrados", proyectos);
                    Parent root = FXMLLoader.load(App.class.getResource("ProyectosTree" + ".fxml"));
                    ContenedorProyectos.getChildren().clear();
                    ContenedorProyectos.getChildren().add(root);
                }catch(IOException ex){
                    Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
                };
            }else{
                Mensaje.showAndWait(Alert.AlertType.ERROR, "Búsqueda de proyectos", "No se encontro ningun proyecto con el nombre "+txtBusqueda.getText());
            }
                
        }
    }

    @FXML
    private void actVolver(ActionEvent event) {
        try{
            Stage stageCerrar = (Stage) btnVolver.getScene().getWindow();
            stageCerrar.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(App.class.getResource("MenuInicio" + ".fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Examen de Programación III - Grupo 7");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Ocurrió un error, por favor intenta más tarde");
        }
    }

    @FXML
    private void actCrearProyecto(ActionEvent event) {
        try{
            AppContext.getInstance().set("ModalidadProyecto", "Agregar");
            Parent root = FXMLLoader.load(App.class.getResource("ProyectoDetalle" + ".fxml"));
            ContenedorProyectos.getChildren().clear();
            ContenedorProyectos.getChildren().add(root);
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }

    @FXML
    private void actVerProyectos(ActionEvent event) {
        verProyectos();
    }
    
    public void verProyectos(){
        try{
            AppContext.getInstance().set("ModalidadProyectosTree", "Ver");
            Parent root = FXMLLoader.load(App.class.getResource("ProyectosTree" + ".fxml"));
            ContenedorProyectos.getChildren().clear();
            ContenedorProyectos.getChildren().add(root);
        }catch(IOException ex){
            Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :c", "Se ha producido un error inesperado en la aplicación");
        };
    }
}
