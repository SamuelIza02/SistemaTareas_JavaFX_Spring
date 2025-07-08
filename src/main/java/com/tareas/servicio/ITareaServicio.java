package com.tareas.servicio;

import com.tareas.modelo.Tarea;

import java.util.List;

/**
 * Interfaz que define los servicios de negocio para la gestión de tareas
 * Contiene la lógica de negocio y actúa como intermediario entre
 * el controlador y el repositorio
 */
public interface ITareaServicio {
    
    /**
     * Obtiene todas las tareas del sistema
     * @return Lista de todas las tareas
     */
    public List<Tarea> listarTareas();
    
    /**
     * Busca una tarea específica por su ID
     * @param idTarea ID de la tarea a buscar
     * @return Tarea encontrada o null si no existe
     */
    public Tarea buscarTareaPorId(Integer idTarea);
    
    /**
     * Guarda una nueva tarea o actualiza una existente
     * @param tarea Tarea a guardar/actualizar
     */
    public void guardarTarea(Tarea tarea);
    
    /**
     * Elimina una tarea del sistema
     * @param tarea Tarea a eliminar
     */
    public void eliminarTarea(Tarea tarea);
}
