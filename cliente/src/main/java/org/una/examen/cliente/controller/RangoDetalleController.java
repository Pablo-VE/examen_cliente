/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.RangoColor;
import org.una.examen.cliente.util.Rangos;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class RangoDetalleController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private JFXSlider valMinimo;
    @FXML
    private JFXSlider valMaximo;
    @FXML
    private ColorPicker color;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnGuardar;

    /**
     * Initializes the controller class.
     */
    private String modalidad ="";
    
    RangoColor rangoEditar = new RangoColor();
    RangosController rC = new RangosController();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modalidad=(String) AppContext.getInstance().get("ModalidadRango");
        rC = (RangosController) AppContext.getInstance().get("RangosController");
        if(modalidad.equals("Agregar")){
            valMinimo.setValue(0);
            valMaximo.setValue(100);
            color.setValue(Color.valueOf("#f0d0ce"));
        }else{
            if(modalidad.equals("Modificar")){
                rangoEditar = (RangoColor) AppContext.getInstance().get("Rango"); 
                valMaximo.setValue(rangoEditar.getMax());
                valMinimo.setValue(rangoEditar.getMin());
                color.setValue(Color.valueOf(rangoEditar.getColor()));
            }
        }
        // TODO
    }    

    @FXML
    private void actSalir(ActionEvent event) {
    }

    @FXML
    private void actGuardar(ActionEvent event) {
        String c;
        c=String.format("#%02x%02x%02x", (int)( color.getValue().getRed()* 255 ),(int)( color.getValue().getGreen() * 255 ),(int)( color.getValue().getBlue() * 255 )); 
        RangoColor rango = new RangoColor((int)valMinimo.getValue(),(int)valMaximo.getValue(), c);
        if(modalidad.equals("Agregar")){
            boolean creado = Rangos.getInstance().addRango(rango); 
            if(creado){
                rC.cargarTabla();
                Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Configuración de rango y color", "El rango y su color ha sido agregado con éxito");
                Stage stage = (Stage) btnGuardar.getScene().getWindow();
                stage.close();
            }else{
                Mensaje.showAndWait(Alert.AlertType.ERROR, "Configuración de rango y color", "Revise los rangos, ya que ya existe un rango creado con esos valores");
            }
        }else{
            if(modalidad.equals("Modificar")){
                boolean modificado = Rangos.getInstance().modificarRango(rangoEditar, rango); 
                if(modificado){
                    rC.cargarTabla();
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Configuración de rango y color", "El color del rango ha sido modificado con éxito");
                    Stage stage = (Stage) btnGuardar.getScene().getWindow();
                    stage.close();
                }else{
                    Mensaje.showAndWait(Alert.AlertType.ERROR, "Configuración de rango y color", "Revise los rangos, ya que ya existe un rango creado con esos valores");
                }
            }
        }
    }
    
    
    
}
