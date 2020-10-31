/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.util;

import java.io.Serializable;
import javafx.scene.layout.HBox;

/**
 *
 * @author Pablo-VE
 */
public class RangoColor implements Serializable  {
    int min;
    int max;
    String color;
    HBox col;

    public RangoColor(int min, int max, String color) {
        this.min = min;
        this.max = max;
        this.color = color;
        
        col = new HBox();
        col.setStyle("-fx-background-color: "+color+";");
    }

    public RangoColor() {
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public HBox getCol() {
        return col;
    }

    public void setCol(HBox col) {
        this.col = col;
    }
    
    public String toString(){
        return min+"-"+max+"-"+color;
    }
    
}
