/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import org.una.examen.cliente.dto.ClienteDTO;
import org.una.examen.cliente.service.ClienteService;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class PagosVerController implements Initializable {

    @FXML
    private TreeView<ClienteDTO> TreeClientesCobros;

    private ClienteService clienteService = new ClienteService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void cargarTodos(){
        ArrayList<ClienteDTO> clientes = new ArrayList<ClienteDTO>();
        Respuesta respuesta = clienteService.getAll();
        if(respuesta.getEstado().equals(true)){
            clientes = (ArrayList<ClienteDTO>) respuesta.getResultado("Clientes");
        }
        cargarTabla(clientes);
    }


    public void cargarTabla(ArrayList<ClienteDTO> clientes){
        

            
    }


    
    
}
