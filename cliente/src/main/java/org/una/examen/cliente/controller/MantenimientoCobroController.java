/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.converter.LocalDateStringConverter;
import javax.json.bind.annotation.JsonbDateFormat;
import org.una.examen.cliente.App;
import org.una.examen.cliente.dto.ClienteDTO;
import org.una.examen.cliente.dto.CobroPendienteDTO;
import org.una.examen.cliente.dto.MembresiaDTO;
import org.una.examen.cliente.dto.TipoServicioDTO;
import org.una.examen.cliente.service.ClienteService;
import org.una.examen.cliente.service.CobroPendienteService;
import org.una.examen.cliente.service.MembresiaService;
import org.una.examen.cliente.service.TipoServicioService;
import org.una.examen.cliente.util.AppContext;
import org.una.examen.cliente.util.Mensaje;
import org.una.examen.cliente.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class MantenimientoCobroController implements Initializable {

    @FXML
    private JFXComboBox<String> cbxMantenimiento;
    @FXML
    private AnchorPane anchorCliente;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private JFXTextField txtDireccion;
    @FXML
    private JFXTextField txtTelefono;
    @FXML
    private JFXRadioButton btnActivo;
    @FXML
    private JFXRadioButton btnInactivo;
    @FXML
    private JFXButton btnAtras;
    @FXML
    private JFXTextField txtMonto;
    @FXML
    private JFXRadioButton btnActivoCobro;
    @FXML
    private JFXRadioButton btnInactivoCobro;
    @FXML
    private JFXButton btnGuardar1;
    @FXML
    private JFXComboBox<ClienteDTO> cbxCliente;
    @FXML
    private AnchorPane anchorCobro;
    @FXML
    private ImageView imaMantenimiento;
    @FXML
    private JFXTextField txtDescripcion;
    @FXML
    private JFXComboBox<String> cbxPerioridad;
    @FXML
    private JFXComboBox<TipoServicioDTO> cbxTipoServicio;
    @FXML
    private JFXButton btnGuardarMembresia;
    @FXML
    private DatePicker DateFechaVenceMembresia;
    private Boolean estado;
    public int perioridad;
    public int cantidadCobros=0;
    
    private Date fechaVenceAux;
    

    private Calendar fechaActual = Calendar.getInstance();
    
    private ClienteDTO clienteEnCuestion = new ClienteDTO();
    private MembresiaDTO membresiaEnCuestion = new MembresiaDTO();
    private CobroPendienteDTO CobroPendienteEnCuestion = new CobroPendienteDTO();
        
    private TipoServicioService tipoServicioService = new TipoServicioService();
    private MembresiaService membresiaService = new MembresiaService();
    private ClienteService clienteService = new ClienteService();
    private CobroPendienteService cobroPendienteService = new CobroPendienteService();
    private int contadorMontoPagos=0;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList opn = new ArrayList();
        opn.add("Cliente");
        opn.add("Membresia");
        ObservableList items = FXCollections.observableArrayList(opn);   
        cbxMantenimiento.setItems(items);
    }    


    @FXML
    private void actAtras(ActionEvent event) {
        try{
            StackPane Contenedor = (StackPane) AppContext.getInstance().get("Contenedor");
            Parent root = FXMLLoader.load(App.class.getResource("MenuCobro" + ".fxml"));
            Contenedor.getChildren().clear();
            Contenedor.getChildren().add(root);
        }catch(IOException ex){
            
        }
    }

    @FXML
    private void cbxActMantenimiento(ActionEvent event) {
        
        if(cbxMantenimiento.getValue()=="Cliente"){
            anchorCobro.setVisible(false);
            imaMantenimiento.setVisible(false);
            anchorCliente.setVisible(true);
        }else{
            anchorCliente.setVisible(false);
            anchorCobro.setVisible(true);
            imaMantenimiento.setVisible(false);
            
            cargarPerioridad();
            cargarClientesCbx();
            cargarMembresiasCbx();      
        }
    }
    void cargarPerioridad(){
        ArrayList opn = new ArrayList();
            opn.add("Mensual");
            opn.add("Bimensual");
            opn.add("Trimensual");
            opn.add("Cuatrimensual");
            opn.add("Semestral");
            opn.add("Anual");
            ObservableList items = FXCollections.observableArrayList(opn);   
            cbxPerioridad.setItems(items);
    }
    void cargarClientesCbx(){
        ArrayList<ClienteDTO> clientes = new ArrayList<ClienteDTO>();
            Respuesta respuesta = clienteService.getAll();
            if(respuesta.getEstado()==true){
                clientes = (ArrayList<ClienteDTO>) respuesta.getResultado("Clientes");
            }
            ObservableList items2 = FXCollections.observableArrayList(clientes);   
            cbxCliente.setItems(items2);
    }
    void cargarMembresiasCbx(){
        ArrayList<TipoServicioDTO> tipoServicio = new ArrayList<TipoServicioDTO>();
            Respuesta respuesta2 = tipoServicioService.getAll();
            if(respuesta2.getEstado()==true){
                tipoServicio = (ArrayList<TipoServicioDTO>) respuesta2.getResultado("TiposServicios");
            }
            ObservableList items3 = FXCollections.observableArrayList(tipoServicio);   
            cbxTipoServicio.setItems(items3);
    }
    
    @FXML
    private void cbxTipoServicio(ActionEvent event) {
    }

    
    @FXML
    private void cbxSelPerioridad(ActionEvent event) {
        if(cbxPerioridad.getValue()=="Mensual"){
            perioridad=1;
            System.out.println(perioridad);
        }
        if(cbxPerioridad.getValue()=="Bimensual"){
            perioridad=2;
            System.out.println(perioridad);
        }
        if(cbxPerioridad.getValue()=="Trimensual"){
            perioridad=3;
            System.out.println(perioridad);
        }
        if(cbxPerioridad.getValue()=="Cuatrimensual"){
            perioridad=4;
            System.out.println(perioridad);
        }
        if(cbxPerioridad.getValue()=="Semestral"){
            perioridad=5;
            System.out.println(perioridad);
        }
        if(cbxPerioridad.getValue()=="Anual"){
            perioridad=6;
            System.out.println(perioridad);
        }
    }

    
    @FXML
    private void btnActGuardarMembresia(ActionEvent event) {
        if(cbxMantenimiento.getValue()=="Membresia"){
            contadorMontoPagos=0;
            membresiaEnCuestion.setCliente(cbxCliente.getValue());
            membresiaEnCuestion.setTipoServicio(cbxTipoServicio.getValue());
            membresiaEnCuestion.setPeriodicidad(perioridad);
            membresiaEnCuestion.setMonto(Float.valueOf(txtMonto.getText()));
            membresiaEnCuestion.setDescripcion(txtDescripcion.getText());
            membresiaEnCuestion.setFechaVenceMembresia(fechaVence);
            membresiaEnCuestion.setEstado(estado);
            Respuesta respuesta=membresiaService.crear(membresiaEnCuestion);
            if(respuesta.getEstado()){
                membresiaEnCuestion = (MembresiaDTO)respuesta.getResultado("Membresia");
                Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro una membresia", "Se ha registrado una membresia correctamente");
                int cant=calcularCantidadCobros();

                Date d = new Date();
                for(int i=0; i<cant; i++){
                    CobroPendienteDTO cobroPendiente = new CobroPendienteDTO();
                    cobroPendiente.setMembresia(membresiaEnCuestion);
                    cobroPendiente.setEstado(true);
                    cobroPendiente.setAno(2020);
                    cobroPendiente.setFechaVencimiento(getFechaF());
                    cobroPendiente.setMonto(calcularMontoCobro(cant));
                    cobroPendiente.setPeriodo(perioridad);

                    Respuesta respuesta1=cobroPendienteService.crear(cobroPendiente);
                    if(respuesta1.getEstado()){
                        CobroPendienteEnCuestion=(CobroPendienteDTO) respuesta.getResultado("CobroPendiente");
                    }
                }     
            }else{
                Mensaje.showAndWait(Alert.AlertType.ERROR, "No se registro de una membresia", respuesta.getMensaje());
            }
        limpiarMembresia();
        }
    }
    public static Date getFechaF() {
        Calendar calendar = Calendar.getInstance();
        Date fechaSum = calendar.getTime();
        return fechaSum;
    }
    
    public float calcularMonto(float montoMembresia){
        float montoCalculado = montoMembresia/contadorMontoPagos;
        return montoCalculado;
    }
    
    public static Calendar toCalendar(Date date){ 
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    

    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date fechaVence;
    @FXML
    private void actDateFechaVenceMembresia(ActionEvent event) {
        contadorMontoPagos=0;
        DateFechaVenceMembresia.setConverter(new LocalDateStringConverter(FormatStyle.FULL));
        Calendar dateCalendar = Calendar.getInstance();
        String fecha[]=DateFechaVenceMembresia.getValue().toString().split("-");
        dateCalendar.set(Calendar.YEAR,Integer.valueOf(fecha[0]));
        dateCalendar.set(Calendar.MONTH, Integer.valueOf(fecha[1])-1);
        dateCalendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(fecha[2]));
        fechaVence=dateCalendar.getTime();
        fechaVenceAux=fechaVence;  
    }

    @FXML
    private void btnSelActivoMembresia(ActionEvent event) {
        btnActivo.setSelected(true);
        btnInactivo.setSelected(false);
        estado=true;
    }

    @FXML
    private void btnSelInactivoMembresia(ActionEvent event) {
        btnActivo.setSelected(false);
        btnInactivo.setSelected(true);
        estado=false;
    }

    
     @FXML
    private void btnActGuardarCliente(ActionEvent event) {
         if(cbxMantenimiento.getValue()=="Cliente"){
            clienteEnCuestion.setNombre(txtNombre.getText());
            clienteEnCuestion.setCedula(txtCedula.getText());
            clienteEnCuestion.setDireccion(txtDireccion.getText());
            clienteEnCuestion.setTelefono(txtTelefono.getText());
            clienteEnCuestion.setEstado(estado);

            Respuesta respuesta=clienteService.crear(clienteEnCuestion);
            if(respuesta.getEstado()){
                clienteEnCuestion=(ClienteDTO) respuesta.getResultado("Cliente");
                Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Guardar", "Se ha guardado un cliente correctamente");
                limpiarCliente();
            }else{
                Mensaje.showAndWait(Alert.AlertType.ERROR, "Se produjo un error, intentelo más tarde", respuesta.getMensaje());
            }  
        }
    }
       
    public void limpiarCliente(){
        clienteEnCuestion = new ClienteDTO();
        txtNombre.setText("");
        txtCedula.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        btnActivo.setSelected(false);
        btnInactivo.setSelected(false);  
    }
    
    public void limpiarMembresia(){
        membresiaEnCuestion = new MembresiaDTO();
        txtMonto.setText("");
        txtDescripcion.setText("");
        btnActivoCobro.setSelected(false);
        btnInactivoCobro.setSelected(false); 
        cargarPerioridad();
        cargarMembresiasCbx();
        cargarClientesCbx();
    }
    
    
    
     @FXML
    private void actSecActivo(ActionEvent event) {
        btnActivo.setSelected(true);
        btnInactivo.setSelected(false);
        estado=true;
    }

    @FXML
    private void actSecInactivo(ActionEvent event) {
        btnActivo.setSelected(false);
        btnInactivo.setSelected(true);
        estado=false;
    }
    
    float calcularMontoCobro(float cant){
        float montoCobro=0;
        float monto = Float.valueOf(txtMonto.getText());
        montoCobro = monto/cant;
        return montoCobro;
    }
    
    int calcularCantidadCobros(){
        boolean bandera=true;
        Calendar calActual = Calendar.getInstance();
        Calendar cal = toCalendar(fechaVenceAux);//tope
        
        if(perioridad == 1){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.MONTH, 1);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        if(perioridad == 2){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.MONTH, 2);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        if(perioridad == 3){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.MONTH, 3);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        if(perioridad == 4){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.MONTH, 4);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        if(perioridad == 5){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.MONTH, 4);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        if(perioridad == 6){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.MONTH, 4);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        if(perioridad == 7){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.MONTH, 4);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        if(perioridad == 8){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.MONTH, 6);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        if(perioridad == 9){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.YEAR, 1);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        if(perioridad == 10){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.MONTH, 4);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        if(perioridad == 11){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.MONTH, 4);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        if(perioridad == 12){
            while (bandera) {
                if(calActual.before(cal)){
                    calActual.add(Calendar.MONTH, 4);
                    contadorMontoPagos++;
                }else{
                    bandera=false;
                }
            }
        }
        return contadorMontoPagos;
    }
   
    
    @FXML
    private void actGuardar(ActionEvent event) {   
    }

    @FXML
    private void cbxCliente(ActionEvent event) {
    }
}
