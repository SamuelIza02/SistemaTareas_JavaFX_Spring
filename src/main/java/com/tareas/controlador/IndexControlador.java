package com.tareas.controlador;

import com.tareas.modelo.Tarea;
import com.tareas.servicio.TareaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador principal de la interfaz JavaFX
 * Maneja la interacción entre la vista y los servicios de negocio
 * Implementa Initializable para configuración inicial de la vista
 */
@Component // Marca como componente de Spring para inyección de dependencias
public class IndexControlador implements Initializable {
    // Logger para registrar eventos y errores
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    // Inyección del servicio de tareas
    @Autowired
    private TareaServicio tareaServicio;

    // Componentes de la tabla de tareas
    @FXML
    private TableView<Tarea> tareaTabla; // Tabla principal que muestra las tareas

    @FXML
    private TableColumn<Tarea, Integer> idTareaColumna; // Columna para ID

    @FXML
    private TableColumn<Tarea, String> nombreTareaColumna; // Columna para nombre

    @FXML
    private TableColumn<Tarea, String> responsableColumna; // Columna para responsable

    @FXML
    private TableColumn<Tarea, String> estatusColumna; // Columna para estatus

    // Lista observable que se sincroniza automáticamente con la tabla
    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();

    // Campos de texto del formulario
    @FXML
    private TextField nombreTareaTexto; // Campo para nombre de tarea

    @FXML
    private TextField responsableTexto; // Campo para responsable

    @FXML
    private TextField estatusTexto; // Campo para estatus

    // Variable para almacenar el ID de la tarea seleccionada
    private Integer idTareaInterno;

    /**
     * Método que se ejecuta automáticamente al cargar la vista
     * Configura la tabla y carga los datos iniciales
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar selección única en la tabla
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTareas();
    }

    /**
     * Configura las columnas de la tabla para mostrar los datos correctos
     * PropertyValueFactory vincula cada columna con el atributo correspondiente
     */
    private void configurarColumnas(){
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        nombreTareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableColumna.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        estatusColumna.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }

    /**
     * Carga todas las tareas desde la base de datos y actualiza la tabla
     */
    private void listarTareas(){
        logger.info("ejecutando listarTareas");
        tareaList.clear(); // Limpiar lista actual
        tareaList.addAll(tareaServicio.listarTareas()); // Cargar desde BD
        tareaTabla.setItems(tareaList); // Actualizar tabla
    }

    /**
     * Agrega una nueva tarea al sistema
     * Valida que el nombre no esté vacío antes de guardar
     */
    public void agregarTarea(){
        // Validación básica del nombre de tarea
        if (nombreTareaTexto.getText().isEmpty()){
            mostrarMensaje("Error Validacion", "Debe proporcionar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }
        else {
            var tarea = new Tarea();
            recolectarDatosFormulario(tarea); // Llenar datos del formulario
            tarea.setIdTarea(null); // Asegurar que es una nueva tarea
            tareaServicio.guardarTarea(tarea);
            mostrarMensaje("Informacion", "Tarea agregada");
            limpiarFormulario();
            listarTareas(); // Refrescar la tabla
        }
    }

    /**
     * Carga los datos de la tarea seleccionada en el formulario
     * Permite editar una tarea existente
     */
    public void cargarTareaFormulario(){
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if (tarea != null){
            // Guardar ID para futuras operaciones
            idTareaInterno = tarea.getIdTarea();
            // Llenar campos del formulario
            nombreTareaTexto.setText(tarea.getNombreTarea());
            responsableTexto.setText(tarea.getResponsable());
            estatusTexto.setText(tarea.getEstatus());
        }
        else {
            mostrarMensaje("Error Tarea", "Seleccione una tarea");
        }
    }

    /**
     * Recolecta los datos del formulario y los asigna a una tarea
     * Método auxiliar para evitar duplicación de código
     */
    private void recolectarDatosFormulario(Tarea tarea){
        // Si hay un ID interno, es una actualización
        if (idTareaInterno != null){
            tarea.setIdTarea(idTareaInterno);
        }
        // Asignar valores de los campos de texto
        tarea.setNombreTarea(nombreTareaTexto.getText());
        tarea.setResponsable(responsableTexto.getText());
        tarea.setEstatus(estatusTexto.getText());
    }

    /**
     * Modifica una tarea existente
     * Requiere que se haya seleccionado una tarea previamente
     */
    public void modificarTarea(){
        // Validar que hay una tarea seleccionada
        if (idTareaInterno == null){
            mostrarMensaje("Informacion", "Debe seleccionar una tarea");
            return;
        }
        // Validar que el nombre no esté vacío
        if (nombreTareaTexto.getText().isEmpty()) {
            mostrarMensaje("Error Validacion", "Debe proporcionar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }
        var  tarea = new Tarea();
        recolectarDatosFormulario(tarea); // Llenar con datos del formulario
        tareaServicio.guardarTarea(tarea); // JPA detecta que es UPDATE por el ID
        mostrarMensaje("Informacion", "Tarea modificada");
        limpiarFormulario();
        listarTareas(); // Refrescar tabla
    }

    /**
     * Elimina la tarea seleccionada de la tabla
     */
    public void eliminarTarea(){
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if (tarea != null){
            logger.info("Eliminando tarea: " + tarea.toString());
            tareaServicio.eliminarTarea(tarea);
            mostrarMensaje("Informacion", "Tarea eliminada");
            limpiarFormulario();
            listarTareas(); // Refrescar tabla
        }
        else {
            mostrarMensaje("Informacion", "Selecciona una tarea a eliminar");
        }
    }

    /**
     * Limpia todos los campos del formulario
     * Resetea el estado para una nueva operación
     */
    public void limpiarFormulario(){
        idTareaInterno = null; // Resetear ID interno
        nombreTareaTexto.clear();
        responsableTexto.clear();
        estatusTexto.clear();
    }

    /**
     * Muestra un mensaje informativo al usuario
     * Método auxiliar para mostrar alertas de JavaFX
     */
    public void mostrarMensaje(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null); // Sin encabezado adicional
        alert.setContentText(mensaje);
        alert.showAndWait(); // Mostrar y esperar respuesta del usuario
    }
}
