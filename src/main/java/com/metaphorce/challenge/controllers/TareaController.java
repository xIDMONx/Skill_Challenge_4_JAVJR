package com.metaphorce.challenge.controllers;

import com.metaphorce.challenge.exceptions.InvalidTareaDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.metaphorce.challenge.models.Tarea;
import com.metaphorce.challenge.services.TareaService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarea")
public class TareaController {

    private final TareaService tareaService;

    /**
     * La clase TareaController es responsable de manejar solicitudes HTTP relacionadas con objetos Tarea.
     */
    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    /**
     * Crea una nueva Tarea.
     *
     * @param tarea el objeto Tarea que se va a crear
     * @return una ResponseEntity que contiene la Tarea creada y un código de estado de 201 (CREADO)
     */
    @PostMapping
    public ResponseEntity<Tarea> createTarea(@RequestBody Tarea tarea) {
        try {
            if (tarea.getTitulo() == null || tarea.getTitulo().isEmpty()) {
                throw new InvalidTareaDataException("El titulo de la tarea no puede estar vacío.", "INVALID_TAREA_TITULO", HttpStatus.BAD_REQUEST, null);
            }
            if (tarea.getDescripcion() == null || tarea.getDescripcion().isEmpty()) {
                throw new InvalidTareaDataException("La descripción de la tarea no puede estar vacío.", "INVALID_TAREA_DESCRIPCION", HttpStatus.BAD_REQUEST, null);
            }

            return new ResponseEntity<>(tareaService.saveTarea(tarea), HttpStatus.CREATED);

        } catch (InvalidTareaDataException e) {
            return new ResponseEntity<>(e.getHttpStatus());
        }
    }

    /**
     * Recupera todas las tareas.
     *
     * @return una lista de todas las tareas.
     */
    @GetMapping
    public List<Tarea> getAllTareas() {
        return tareaService.getAllTareas();
    }

    /**
     * Recupera una Tarea por su ID.
     *
     * @param id el ID de la Tarea a recuperar
     * @return una ResponseEntity que contiene la Tarea con el ID proporcionado si existe o una ResponseEntity con un
     * estado HTTP 404 NOT FOUND si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> getTareaById(@PathVariable Long id) {
        Optional<Tarea> optionalTarea = tareaService.getTareaById(id);

        return optionalTarea.map(tarea -> new ResponseEntity<>(tarea, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza una Tarea por el ID dado.
     *
     * @param id el ID de la Tarea a actualizar
     * @param updateTarea el objeto Tarea que contiene los valores actualizados
     * @return a ResponseEntity que contiene la Tarea actualizada si existe, en caso contrario se devolverá una
     * respuesta HTTP 404 NOT FOUND
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> updateTarea(@PathVariable Long id, @RequestBody Tarea updateTarea) {
        return tareaService.getTareaById(id)
                .map(existingTarea -> {
                    if (updateTarea.getTitulo() != null && !updateTarea.getTitulo().isEmpty()) {
                        existingTarea.setTitulo(updateTarea.getTitulo());
                    }

                    if (updateTarea.getDescripcion() != null && !updateTarea.getDescripcion().isEmpty()) {
                        existingTarea.setDescripcion(updateTarea.getDescripcion());
                    }

                    if (updateTarea.getEstado() != null) {
                        existingTarea.setEstado(updateTarea.getEstado());
                    }

                    if (updateTarea.getFechaVencimiento() != null) {
                        existingTarea.setFechaVencimiento(updateTarea.getFechaVencimiento());
                    }

                    existingTarea.setUpdatedAt(new Date());

                    return new ResponseEntity<>(tareaService.updateTarea(existingTarea), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Elimina una Tarea por su ID.
     *
     * @param id el ID de la Tarea a eliminar
     * @return una ResponseEntity con un estado de 204 (NO_CONTENT) si la Tarea se elimina exitosamente, o una
     * ResponseEntity con un estado de 404 (NOT_FOUND) si la Tarea no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable Long id) {
        if (tareaService.getTareaById(id).isPresent()) {
            tareaService.deleteTarea(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
