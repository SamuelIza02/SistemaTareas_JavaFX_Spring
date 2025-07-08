package com.tareas.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad que representa una tarea en el sistema
 * Mapea a la tabla 'tarea' en la base de datos
 */
@Entity
@Data // Genera getters, setters, equals, hashCode automáticamente
@NoArgsConstructor // Constructor sin parámetros
@AllArgsConstructor // Constructor con todos los parámetros
@ToString // Método toString automático
public class Tarea {
    
    // Clave primaria con generación automática
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTarea;
    
    // Nombre descriptivo de la tarea
    private String nombreTarea;
    
    // Persona responsable de la tarea
    private String responsable;
    
    // Estado actual de la tarea (pendiente, en progreso, completada)
    private String estatus;
}
