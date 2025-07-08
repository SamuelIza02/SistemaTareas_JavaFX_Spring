package com.tareas;

import com.tareas.presentacion.SistemaTareasFx;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot
 * Punto de entrada que integra Spring Boot con JavaFX
 * 
 * @SpringBootApplication habilita:
 * - Configuración automática
 * - Escaneo de componentes
 * - Configuración de propiedades
 */
@SpringBootApplication
public class TareasApplication {

	/**
	 * Método principal que inicia la aplicación
	 * Lanza JavaFX en lugar del servidor web tradicional de Spring Boot
	 */
	public static void main(String[] args) {
		// Opción tradicional de Spring Boot (comentada)
		// SpringApplication.run(TareasApplication.class, args);
		
		// Lanzar aplicación JavaFX que internamente inicializa Spring
		Application.launch(SistemaTareasFx.class, args);
	}

}
