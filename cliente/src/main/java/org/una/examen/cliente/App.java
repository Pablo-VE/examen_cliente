package org.una.examen.cliente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("MenuInicio" + ".fxml"));
        stage.setTitle("Examen de Programación III - Grupo 7");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    

}