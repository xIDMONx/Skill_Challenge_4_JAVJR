package com.metaphorce.challenge.services;

import com.metaphorce.challenge.models.Tarea;
import com.metaphorce.challenge.repositories.TareaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class TareaService implements TareaServiceInterface {

    @Autowired
    private TareaRepository tareaRepository;

    /**
     * Guarda un objeto Tarea en el repositorio.
     *
     * @param tarea el objeto Tarea a guardar
     * @return el objeto Tarea guardado
     */
    @Transactional
    @Override
    public Tarea saveTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public Tarea getTareaById(Long id) {
        return tareaRepository.findById(id).orElse(null);
    }

    /**
     * Recupera todas las tareas del repositorio de tareas.
     *
     * @return una lista de objetos Tarea que representan todas las tareas almacenadas en el repositorio.
     */
    @Override
    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }

    /**
     * Actualiza una Tarea.
     *
     * @param tarea el objeto Tarea a actualizar
     * @return el objeto Tarea actualizado
     */
    @Transactional
    @Override
    public Tarea updateTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    /**
     * Elimina una Tarea del repositorio por su ID.
     *
     * @param id el ID de la Tarea a eliminar
     */
    @Transactional
    @Override
    public void deleteTareaById(Long id) {
        tareaRepository.deleteById(id);
    }
}
