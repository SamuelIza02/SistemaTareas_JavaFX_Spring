package com.tareas.presentacion;

import com.tareas.TareasApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * Clase principal de JavaFX que integra Spring Boot con JavaFX
 * Extiende Application para crear la interfaz gráfica
 * Maneja el ciclo de vida de la aplicación JavaFX y Spring
 */
public class SistemaTareasFx extends Application {

    // Contexto de Spring para inyección de dependencias
    private ConfigurableApplicationContext applicationContext;

    // Método main comentado porque se ejecuta desde TareasApplication
//    public static void main(String[] args) {
//        launch(args);
//    }

    /**
     * Método que se ejecuta antes de start()
     * Inicializa el contexto de Spring Boot
     */
    @Override
    public void init(){
        // Crear contexto de Spring sin servidor web
        this.applicationContext = new SpringApplicationBuilder(TareasApplication.class).run();
    }

    /**
     * Método principal de JavaFX que crea y muestra la ventana
     * Carga el archivo FXML y configura la escena
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Cargar archivo FXML de la interfaz
        FXMLLoader loader = new FXMLLoader(TareasApplication.class.getResource("/templates/index.fxml"));
        // Configurar factory para que Spring maneje los controladores
        loader.setControllerFactory(applicationContext::getBean);
        // Crear escena con el contenido cargado
        Scene escena = new Scene(loader.load());
        stage.setScene(escena);
        stage.show(); // Mostrar ventana
    }

    /**
     * Método que se ejecuta al cerrar la aplicación
     * Limpia recursos de Spring y JavaFX
     */
    @Override
    public void stop(){
        applicationContext.close(); // Cerrar contexto de Spring
        Platform.exit(); // Salir de JavaFX
    }
}
