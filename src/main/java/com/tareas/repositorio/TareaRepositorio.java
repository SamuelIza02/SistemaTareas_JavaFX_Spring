package com.tareas.repositorio;

import com.tareas.modelo.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para operaciones CRUD de la entidad Tarea
 * Extiende JpaRepository que proporciona métodos básicos como:
 * - findAll(): obtener todas las tareas
 * - findById(): buscar por ID
 * - save(): guardar/actualizar tarea
 * - delete(): eliminar tarea
 */
public interface TareaRepositorio extends JpaRepository<Tarea, Integer> {
    // Spring Data JPA genera automáticamente la implementación
    // No necesita métodos adicionales para operaciones básicas
}
