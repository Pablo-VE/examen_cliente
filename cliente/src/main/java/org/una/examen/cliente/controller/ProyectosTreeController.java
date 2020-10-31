/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.una.examen.cliente.dto.ProyectoDTO;
import org.una.examen.cliente.service.ProyectoService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.ProyectoTreeBig;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class ProyectosTreeController implements Initializable {

    @FXML
    private VBox vbPadre;
    @FXML
    private Label lbTitulo;
    @FXML
    private ScrollPane scPane;

    /**
     * Initializes the controller class.
     */
    private String modalidad="";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vbPadre.setStyle(estilosPrincipal());
        AppContext.getInstance().set("ControllerProyecto", this);
        modalidad=(String) AppContext.getInstance().get("ModalidadProyectosTree");
        if(!modalidad.equals("Ver")){
            List<ProyectoDTO> proyectos = new ArrayList<ProyectoDTO>();
            proyectos = (List<ProyectoDTO>) AppContext.getInstance().get("ProyectosFiltrados");
            System.out.println(proyectos.size());
            cargarVista(proyectos);
        }else{
            cargarTodos();
        }
        // TODO
    } 
    
    public void cargarVista(List<ProyectoDTO> proyectos){
        vbPadre.getChildren().clear();
        if(!proyectos.isEmpty()){
                for(int i=0; i<proyectos.size(); i++){
                    ProyectoTreeBig pro = new ProyectoTreeBig(proyectos.get(i));
                    vbPadre.getChildren().add(pro);
            }
        }
    }
    
    public void cargarTodos(){
        List<ProyectoDTO> proyectos = new ArrayList<ProyectoDTO>();
        ProyectoService proyectoService = new ProyectoService();
        Respuesta res = proyectoService.getAll();
        if(res.getEstado()){
            proyectos = (List<ProyectoDTO>) res.getResultado("Proyectos");
            cargarVista(proyectos);
        }
    }
    
    public String estilosPrincipal(){
        return  "   -fx-background-color: transparent;" +
                "   -fx-spacing: 25;" ;
    }
    
    
}
