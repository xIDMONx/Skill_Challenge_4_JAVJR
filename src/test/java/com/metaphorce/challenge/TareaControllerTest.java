package com.metaphorce.challenge;

import com.metaphorce.challenge.controllers.TareaController;
import com.metaphorce.challenge.exceptions.InvalidTareaDataException;
import com.metaphorce.challenge.exceptions.TareaNotFoudException;
import com.metaphorce.challenge.models.Tarea;
import com.metaphorce.challenge.services.TareaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

public class TareaControllerTest {
    @InjectMocks
    TareaController tareaController;

    @Mock
    TareaService tareaService;

    @Mock
    BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTarea() {
        // Creamos una nueva instancia de Tarea
        Tarea tarea = new Tarea();
        // Asignamos algunas propiedades
        tarea.setTitulo("Skill challenge");
        tarea.setDescripcion("Agregar el manejo de excepciones, validación de datos y pruebas unitarias al API de gestión de tareas realizado en el challenge anterior.");
        tarea.setEstado("Pendiente");
        tarea.setFechaVencimiento(new Date());
        //
        when(bindingResult.hasErrors()).thenReturn(false);
        when(tareaService.saveTarea(any(Tarea.class))).thenReturn(tarea);
        //
        ResponseEntity<Tarea> responseEntity = tareaController.createTarea(tarea, bindingResult);
        //
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(tarea.getTitulo(), responseEntity.getBody().getTitulo());
        assertEquals(tarea.getDescripcion(), responseEntity.getBody().getDescripcion());
        assertEquals(tarea.getEstado(), responseEntity.getBody().getEstado());
    }

    @Test
    public void createTarea_ValidTarea() {
        // Creamos una nueva instancia de Tarea
        Tarea tarea = new Tarea();
        // Asignamos algunas propiedades
        tarea.setTitulo("Skill challenge");
        tarea.setDescripcion("Agregar el manejo de excepciones, validación de datos y pruebas unitarias al API de gestión de tareas realizado en el challenge anterior.");
        tarea.setEstado("Pendiente");
        tarea.setFechaVencimiento(new Date());
        //
        when(bindingResult.hasErrors()).thenReturn(false);
        when(tareaService.saveTarea(any(Tarea.class))).thenReturn(tarea);
        //
        ResponseEntity<Tarea> responseEntity = tareaController.createTarea(tarea, bindingResult);
        //
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(tarea, responseEntity.getBody());
    }

    @Test
    public void createTarea_InvalidTarea() {
        // Creamos una nueva instancia de Tarea
        Tarea tarea = new Tarea();
        //
        when(bindingResult.hasErrors()).thenReturn(true);
        //
        Exception exception = assertThrows(InvalidTareaDataException.class, () -> {
            tareaController.createTarea(tarea, bindingResult);
        });
        //
        String expectedMessage = "El titulo de la tarea es requerido.";
        String actualMessage = exception.getMessage();
        //
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetAllTareas() {
        // Creamos una nueva instancia de Tarea
        Tarea tarea1 = new Tarea();
        // Asignamos algunas propiedades
        tarea1.setTitulo("Skill challenge 1");
        // Creamos una nueva instancia de Tarea
        Tarea tarea2 = new Tarea();
        // Asignamos algunas propiedades
        tarea2.setTitulo("Skill challenge 2");
        // Agregamos las tareas a una lista de tareas
        List<Tarea> tareas = Arrays.asList(tarea1, tarea2);
        //
        when(tareaService.getAllTareas()).thenReturn(tareas);
        //
        List<Tarea> response = tareaController.getAllTareas();
        //
        assertNotNull(response);
    }

    @Test
    public void testGetTareaById() {
        // Creamos una nueva instancia de Tarea
        Tarea tarea = new Tarea();
        // Asignamos algunas propiedades
        tarea.setId(3L);
        tarea.setTitulo("Skill challenge");
        tarea.setDescripcion("Agregar el manejo de excepciones, validación de datos y pruebas unitarias al API de gestión de tareas realizado en el challenge anterior.");
        tarea.setEstado("Pendiente");
        tarea.setFechaVencimiento(new Date());
        //
        when(tareaService.getTareaById(3L)).thenReturn(tarea);
        //
        ResponseEntity<Tarea> response = tareaController.getTareaById(3L);
        //
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tarea, response.getBody());
    }

    @Test
    public void testGetTareaByNonExistentId() {
        Exception exception = assertThrows(TareaNotFoudException.class, () -> {
            tareaController.getTareaById(3L);
        });
        //
        String expectedMessage = "Tarea no encontrada. [3]";
        String actualMessage = exception.getMessage();
        //
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateTarea() {
        // Creamos una nueva instancia de Tarea
        Tarea tarea = new Tarea();
        // Asignamos algunas propiedades
        tarea.setId(1L);
        tarea.setTitulo("Skill challenge");
        // Simulamos la obtención de la tarea 1 y retornamos la tarea instanciada
        when(tareaService.getTareaById(1L)).thenReturn(tarea);
        // Simulamos la actualización
        when(tareaService.updateTarea(any(Tarea.class))).thenReturn(tarea);
        // Actualizamos el título
        tarea.setTitulo("Skill challenge 4");
        //
        ResponseEntity<Tarea> response = tareaController.updateTarea(1L, tarea);
        //
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Skill challenge 4", Objects.requireNonNull(response.getBody()).getTitulo());
    }

    @Test
    public void testDeleteTarea() {
        // Creamos una nueva instancia de Tarea
        Tarea tarea = new Tarea();
        // Asignamos algunas propiedades
        tarea.setId(1L);
        //
        when(tareaService.getTareaById(1L)).thenReturn(tarea);
        willDoNothing().given(tareaService).deleteTareaById(1L);
        //
        ResponseEntity<Void> response = tareaController.deleteTarea(1L);
        //
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteTareaByNonExistentId() {
        Exception exception = assertThrows(TareaNotFoudException.class, () -> {
            tareaController.deleteTarea(3L);
        });
        //
        String expectedMessage = "Tarea no encontrada. [3]";
        String actualMessage = exception.getMessage();
        //
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
