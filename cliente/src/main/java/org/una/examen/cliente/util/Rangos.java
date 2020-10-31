/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import org.una.examen.cliente.dto.TareaDTO;

/**
 *
 * @author Pablo-VE
 */
public class Rangos {
    private static Rangos instance = null;
    
    public ArrayList<RangoColor> rangos;
    
    private Rangos() {
        this.rangos = new ArrayList<RangoColor>();
    } 
    
    public static Rangos getInstance(){
        if(instance == null){
            instance = new Rangos();
        }
        return instance;
    }
    
    public boolean addRango(RangoColor rango){
        if(buscarRangoCrear(rango)){
            Rangos.getInstance().rangos.add(rango);
            return true;
        }else{
            return false;
        }
        
        
    }
    
    public boolean modificarRango(RangoColor rangoEditar, RangoColor rangoNuevo){
        
        if(buscarRangoModificar(rangoEditar, rangoNuevo)){
            int index=-1;
            for(int i=0; i<Rangos.getInstance().rangos.size();i++){
                System.out.println(rangos.get(i)+"\n");
                if(rangos.get(i).getMin()==rangoEditar.getMin()&&rangos.get(i).getMax()==rangoEditar.getMax()&&rangos.get(i).getColor().equals(rangoEditar.getColor())){
                    index=i;
                }
            }
            
            System.out.println("Rango editar: "+rangoEditar);
            
           
            System.out.println(index);
            Rangos.getInstance().rangos.get(index).setMax(rangoNuevo.getMax());
            Rangos.getInstance().rangos.get(index).setMin(rangoNuevo.getMin());
            Rangos.getInstance().rangos.get(index).setColor(rangoNuevo.getColor());
            Rangos.getInstance().rangos.get(index).setCol(rangoNuevo.getCol());
            return true;
        }else{
            return false;
        }
        
    }
    
    public boolean buscarRangoCrear(RangoColor rango){
        if(rangos.size()>0){
            ArrayList<RangoColor> rangosAux = rangos;
            if(rangosAux.size()>0){
                for(int i=0; i<rangosAux.size(); i++){
                    if((rango.getMin()>=rangosAux.get(i).getMin()&&rango.getMin()<=rangosAux.get(i).getMax())||(rango.getMax()>=rangosAux.get(i).getMin()&&rango.getMax()<=rangosAux.get(i).getMax())){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public boolean buscarRangoModificar(RangoColor rangoViejo, RangoColor rangoNuevo){
        if(rangos.size()>0){
            ArrayList<RangoColor> rangosAux = rangos;
            int in = rangos.indexOf(rangoViejo);
            if(rangosAux.size()>0){
                for(int i=0; i<rangosAux.size(); i++){
                    if(i!=in){
                        if((rangoNuevo.getMin()>=rangosAux.get(i).getMin()&&rangoNuevo.getMin()<=rangosAux.get(i).getMax())||(rangoNuevo.getMax()>=rangosAux.get(i).getMin()&&rangoNuevo.getMax()<=rangosAux.get(i).getMax())){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<RangoColor> getRangos() {
        return rangos;
    }

    public void setRangos(ArrayList<RangoColor> rangos) {
        this.rangos = rangos;
    }
    
    public void eliminarRango(RangoColor rango){
        Rangos.getInstance().rangos.remove(rango);
    }
    
    public void guardar(){
        String textoGuardar="";
        if(rangos.size()>0){
            for(int i=0; i<rangos.size(); i++){
                textoGuardar+=rangos.get(i);
                if(i<(rangos.size()-1)){
                    textoGuardar+=",";
                }
            }
        }
        try {
            String ruta = "proyectosRangos\\Rangos.txt";


            File file = new File(ruta);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(textoGuardar);
            bw.close();

            
            Mensaje.showAndWait(Alert.AlertType.INFORMATION, "CONFIGURACIÓN DE RANGOS Y COLORES", "Rangos y colores registrados correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
            
    }
    
    public void cargar(){
        rangos.clear();
        File file = new File("proyectosRangos\\Rangos.txt"); 
  
        try{
            BufferedReader br = new BufferedReader(new FileReader(file)); 

            String archivoLeido=null; 
            while ((archivoLeido = br.readLine()) != null) 

            if(archivoLeido!=null){

                String todosRangos[] = archivoLeido.split(",");
                if(todosRangos!=null){
                    if(todosRangos.length>0){
                        for(int i=0; i<todosRangos.length; i++){
                            String rangoLeido[]=todosRangos[i].split("-");
                            RangoColor rango = new RangoColor(Integer.valueOf(rangoLeido[0]), Integer.valueOf(rangoLeido[1]), rangoLeido[2]);
                            Rangos.getInstance().addRango(rango);
                        }
                    }
                }

            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
         
    }
    
    public String colorTarea(TareaDTO tarea){
        if(rangos.size()>0){
            for(int i=0; i<rangos.size();i++){
                if(tarea.getPorcentajeAvance()>=rangos.get(i).getMin()&&tarea.getPorcentajeAvance()<=rangos.get(i).getMax()){
                    return "   -fx-background-color: "+rangos.get(i).getColor()+";" +
                           "   -fx-spacing: 5;" ;
                }
            }
        }
        
        
        
        return "   -fx-background-color: #f0d0ce;" +
                "   -fx-spacing: 5;" ;
    }
}
