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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<ProyectoDTO> proyectos = new ArrayList<ProyectoDTO>();
        ProyectoService proyectoService = new ProyectoService();
        Respuesta res = proyectoService.getAll();
        if(res.getEstado()){
            proyectos = (List<ProyectoDTO>) res.getResultado("Proyectos");
            if(!proyectos.isEmpty()){
                for(int i=0; i<proyectos.size(); i++){
                    ProyectoTreeBig pro = new ProyectoTreeBig(proyectos.get(i));
                    vbPadre.getChildren().add(pro);
                }
            }
        }
        // TODO
    }    
    
}
