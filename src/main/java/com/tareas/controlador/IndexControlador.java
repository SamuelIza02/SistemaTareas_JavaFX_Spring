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

@Component
public class IndexControlador implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @Autowired
    private TareaServicio tareaServicio;

    @FXML
    private TableView<Tarea> tareaTabla;

    @FXML
    private TableColumn<Tarea, Integer> idTareaColumna;

    @FXML
    private TableColumn<Tarea, String> nombreTareaColumna;

    @FXML
    private TableColumn<Tarea, String> responsableColumna;

    @FXML
    private TableColumn<Tarea, String> estatusColumna;

    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();

    @FXML
    private TextField nombreTareaTexto;

    @FXML
    private TextField responsableTexto;

    @FXML
    private TextField estatusTexto;

    private Integer idTareaInterno;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTareas();
    }

    private void configurarColumnas(){
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        nombreTareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableColumna.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        estatusColumna.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }

    private void listarTareas(){
        logger.info("ejecutando listarTareas");
        tareaList.clear();
        tareaList.addAll(tareaServicio.listarTareas());
        tareaTabla.setItems(tareaList);
    }

    public void agregarTarea(){
        if (nombreTareaTexto.getText().isEmpty()){
            mostrarMensaje("Error Validacion", "Debe proporcionar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }
        else {
            var tarea = new Tarea();
            recolectarDatosFormulario(tarea);
            tarea.setIdTarea(null);
            tareaServicio.guardarTarea(tarea);
            mostrarMensaje("Informacion", "Tarea agregada");
            limpiarFormulario();
            listarTareas();
        }
    }

    public void cargarTareaFormulario(){
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if (tarea != null){
            idTareaInterno = tarea.getIdTarea();
            nombreTareaTexto.setText(tarea.getNombreTarea());
            responsableTexto.setText(tarea.getResponsable());
            estatusTexto.setText(tarea.getEstatus());
        }
        else {
            mostrarMensaje("Error Tarea", "Seleccione una tarea");
        }
    }

    private void recolectarDatosFormulario(Tarea tarea){
        if (idTareaInterno != null){
            tarea.setIdTarea(idTareaInterno);
        }
        tarea.setNombreTarea(nombreTareaTexto.getText());
        tarea.setResponsable(responsableTexto.getText());
        tarea.setEstatus(estatusTexto.getText());
    }

    public void modificarTarea(){
        if (idTareaInterno == null){
            mostrarMensaje("Informacion", "Debe seleccionar una tarea");
            return;
        }
        if (nombreTareaTexto.getText().isEmpty()) {
            mostrarMensaje("Error Validacion", "Debe proporcionar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }
        var  tarea = new Tarea();
        recolectarDatosFormulario(tarea);
        tareaServicio.guardarTarea(tarea);
        mostrarMensaje("Informacion", "Tarea modificada");
        limpiarFormulario();
        listarTareas();
    }

    public void eliminarTarea(){
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if (tarea != null){
            logger.info("Eliminando tarea: " + tarea.toString());
            tareaServicio.eliminarTarea(tarea);
            mostrarMensaje("Informacion", "Tarea eliminada");
            limpiarFormulario();
            listarTareas();
        }
        else {
            mostrarMensaje("Informacion", "Selecciona una tarea a eliminar");
        }
    }

    public void limpiarFormulario(){
        idTareaInterno = null;
        nombreTareaTexto.clear();
        responsableTexto.clear();
        estatusTexto.clear();
    }


    public void mostrarMensaje(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
