package com.metaphorce.challenge.services;

import com.metaphorce.challenge.exceptions.InvalidTareaDataException;
import com.metaphorce.challenge.exceptions.TareaNotFoudException;
import com.metaphorce.challenge.models.Tarea;
import com.metaphorce.challenge.repositories.TareaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Date;
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
        if (tarea.getTitulo() == null || tarea.getTitulo().trim().isEmpty())
            throw new InvalidTareaDataException("El titulo de la tarea es requerido.");

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
    public Tarea updateTareaById(Long id, Tarea tarea) {
        Tarea existingTarea = tareaRepository.findById(id).orElse(null);
        if (existingTarea == null) {
            throw new TareaNotFoudException("No se encontró la tarea con el ID proporcionado.", "DB-404", HttpStatus.NOT_FOUND);
        }

        if (tarea.getTitulo() == null || tarea.getTitulo().trim().isEmpty())
            throw new InvalidTareaDataException("El titulo de la tarea es requerido.");

        existingTarea.setTitulo(tarea.getTitulo());
        existingTarea.setDescripcion(tarea.getDescripcion());
        existingTarea.setEstado(tarea.getEstado());
        existingTarea.setFechaVencimiento(tarea.getFechaVencimiento());
        existingTarea.setUpdatedAt(new Date());

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
