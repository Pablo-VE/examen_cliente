/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXTreeView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import org.una.examen.cliente.dto.CantonDTO;
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.dto.ProvinciaDTO;
import org.una.examen.cliente.dto.UnidadDTO;
import org.una.examen.cliente.service.CantonService;
import org.una.examen.cliente.service.DistritoService;
import org.una.examen.cliente.service.ProvinciaService;
import org.una.examen.cliente.service.UnidadService;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Jeffry
 */
public class ProvinciasTreeViewController implements Initializable {

    @FXML
    private JFXTreeView treeView;

    
    private ProvinciaService provinciaService = new ProvinciaService();
    private CantonService cantonService = new CantonService();
    private DistritoService distritoService = new DistritoService();
    private UnidadService unidadService = new UnidadService();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }
//        TreeItem root = new TreeItem<>("Root");
//        treeView.setRoot(root);
//        treeView.setShowRoot(false);
//        ArrayList<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();
//        Respuesta respuesta = provinciaService.getAll();
//        if(respuesta.getEstado()){
//            provincias = (ArrayList<ProvinciaDTO>)respuesta.getResultado("Provincias");
//        }
//        if(!provincias.isEmpty()){
//            for(int i=0; i<provincias.size(); i++){
//                ProvinciaDTO p = provincias.get(i);
//                TreeItem item = new TreeItem("Provincia: "+p.getNombre());
//                root.getChildren().add(item);
//                
//                ArrayList<CantonDTO> cantones = new ArrayList<CantonDTO>();
//                Respuesta respuesta2 = cantonService.getByProvincia(p.getId());
//                if(respuesta2.getEstado()){
//                    cantones = (ArrayList<CantonDTO>)respuesta.getResultado("Cantones");
//                }
//                if(!cantones.isEmpty()){
//                    for(int j=0; j<cantones.size(); j++){
//                        CantonDTO c = cantones.get(j);
//                        TreeItem item2 = new TreeItem("Cantón: "+c.getNombre());
//                        item.getChildren().add(item);
//                        
////                        ArrayList<DistritoDTO> distritos = new ArrayList<DistritoDTO>();
////                        Respuesta respuesta3 = distritoService.getByCanton(c.getId());
////                        if(respuesta3.getEstado()){
////                            distritos = (ArrayList<DistritoDTO>)respuesta.getResultado("Distritos");
////                        }
////                        if(!distritos.isEmpty()){
////                            for(int k=0; k<distritos.size(); k++){
////                                DistritoDTO d = distritos.get(k);
////                                TreeItem item3 = new TreeItem("Distrito: "+d.getNombre());
////                                item2.getChildren().add(item);
//                                
////                                ArrayList<UnidadDTO> unidades = new ArrayList<UnidadDTO>();
////                                Respuesta respuesta4 = unidadService.getByDistrito(d.getId());
////                                if(respuesta4.getEstado()){
////                                    unidades = (ArrayList<UnidadDTO>)respuesta.getResultado("Unidades");
////                                }
////                                if(!provincias.isEmpty()){
////                                    for(int l=0; l<provincias.size(); l++){
////                                        UnidadDTO u = unidades.get(l);
////                                        TreeItem item4 = new TreeItem("Unidad: "+u.getNombre());
////                                        item3.getChildren().add(item);
////                                    } 
////                                }
//                            } 
//                        }
//                    } 
//                }
//            } 
//        }
    //}


