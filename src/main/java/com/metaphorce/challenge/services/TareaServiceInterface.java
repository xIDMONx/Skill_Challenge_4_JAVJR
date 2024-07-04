package com.metaphorce.challenge.services;

import com.metaphorce.challenge.models.Tarea;

import java.util.List;

public interface TareaServiceInterface {
    /**
     * Guarda un objeto Tarea en el repositorio.
     *
     * @param tarea el objeto Tarea a guardar
     * @return el objeto Tarea guardado
     */
    Tarea saveTarea(Tarea tarea);

    /**
     * Recupera una Tarea por su ID.
     *
     * @param id el ID de la Tarea a recuperar.
     * @return el objeto Tarea con el ID proporcionado si existe, o nulo si no existe.
     */
    Tarea getTareaById(Long id);

    /**
     * Recupera todas las tareas del repositorio de tareas.
     *
     * @return una lista de objetos Tarea que representan todas las tareas almacenadas en el repositorio.
     */
    List<Tarea> getAllTareas();

    /**
     * Actualiza una Tarea en el repositorio por su ID.
     *
     * @param id el ID de la Tarea a actualizar
     * @param tarea el objeto Tarea actualizado
     * @return el objeto Tarea actualizado
     */
    Tarea updateTareaById(Long id, Tarea tarea);

    /**
     * Elimina una tarea del repositorio por su ID.
     *
     * @param id el ID de la tarea a eliminar
     */
    void deleteTareaById(Long id);
}
