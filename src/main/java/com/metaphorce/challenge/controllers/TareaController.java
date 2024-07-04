package com.metaphorce.challenge.controllers;

import com.metaphorce.challenge.exceptions.TareaNotFoudException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.metaphorce.challenge.models.Tarea;
import com.metaphorce.challenge.services.TareaService;
import com.metaphorce.challenge.exceptions.InvalidTareaDataException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/tarea")
public class TareaController {

    @Autowired
    TareaService tareaService;

    /**
     * Crea una nueva Tarea.
     *
     * @param tarea el objeto Tarea que se va a crear
     * @return una ResponseEntity que contiene la Tarea creada y un código de estado de 201 (CREADO)
     */
    @PostMapping
    public ResponseEntity<Tarea> createTarea(@Valid @RequestBody Tarea tarea, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidTareaDataException(
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(),
                    "INVALID_TAREA_DATA",
                    HttpStatus.BAD_REQUEST,
                    bindingResult
            );
        }

        return new ResponseEntity<>(tareaService.saveTarea(tarea), HttpStatus.CREATED);
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
        Tarea tarea = tareaService.getTareaById(id);
        if (tarea == null) {
            throw new TareaNotFoudException("Tarea no encontrada. [" + id + "]", "DB-404", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tarea);
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
        Tarea tarea = tareaService.getTareaById(id);
        if (tarea == null) {
            throw new TareaNotFoudException("Tarea no encontrada. [" + id + "]", "DB-404", HttpStatus.NOT_FOUND);
        }
        //
        if (updateTarea.getTitulo() != null && !updateTarea.getTitulo().isEmpty()) {
            tarea.setTitulo(updateTarea.getTitulo());
        }
        //
        if (updateTarea.getDescripcion() != null && !updateTarea.getDescripcion().isEmpty()) {
            tarea.setDescripcion(updateTarea.getDescripcion());
        }
        //
        if (updateTarea.getEstado() != null) {
            tarea.setEstado(updateTarea.getEstado());
        }
        //
        if (updateTarea.getFechaVencimiento() != null) {
            tarea.setFechaVencimiento(updateTarea.getFechaVencimiento());
        }
        //
        tarea.setUpdatedAt(new Date());
        //
        return new ResponseEntity<>(tareaService.updateTarea(tarea), HttpStatus.OK);
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
        if (tareaService.getTareaById(id) == null) {
            throw new TareaNotFoudException("Tarea no encontrada. [" + id + "]", "DB-404", HttpStatus.NOT_FOUND);
        }
        //
        tareaService.deleteTareaById(id);
        //
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
