package com.tareas.servicio;

import com.tareas.modelo.Tarea;
import com.tareas.repositorio.TareaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de tareas
 * Contiene la lógica de negocio y coordina las operaciones
 * entre el controlador y el repositorio
 */
@Service // Marca esta clase como un servicio de Spring
public class TareaServicio implements ITareaServicio{

    // Inyección de dependencia del repositorio
    @Autowired
    private TareaRepositorio tareaRepositorio;

    /**
     * Obtiene todas las tareas de la base de datos
     */
    @Override
    public List<Tarea> listarTareas() {
        return tareaRepositorio.findAll();
    }

    /**
     * Busca una tarea por su ID
     * Utiliza Optional para manejar casos donde la tarea no existe
     */
    @Override
    public Tarea buscarTareaPorId(Integer idTarea) {
        // orElse(null) devuelve null si no encuentra la tarea
        Tarea tarea = tareaRepositorio.findById(idTarea).orElse(null);
        return tarea;
    }

    /**
     * Guarda una tarea nueva o actualiza una existente
     * JPA determina automáticamente si es INSERT o UPDATE
     */
    @Override
    public void guardarTarea(Tarea tarea) {
        tareaRepositorio.save(tarea);
    }

    /**
     * Elimina una tarea de la base de datos
     */
    @Override
    public void eliminarTarea(Tarea tarea) {
        tareaRepositorio.delete(tarea);
    }
}
