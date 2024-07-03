package com.metaphorce.challenge;

import com.metaphorce.challenge.exceptions.InvalidTareaDataException;
import com.metaphorce.challenge.exceptions.TareaNotFoudException;
import com.metaphorce.challenge.models.Tarea;
import com.metaphorce.challenge.repositories.TareaRepository;
import com.metaphorce.challenge.services.TareaService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

public class TareaServiceTest {

    private TareaService tareaService;
    private TareaRepository tareaRepositoryMock;

    @BeforeEach
    public void init() {
        tareaRepositoryMock = mock(TareaRepository.class);
        tareaService = new TareaService(tareaRepositoryMock);
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
        // Predefinimos la respuesta del método save de tareaRepositoryMock para que devuelva la misma tarea
        when(tareaRepositoryMock.save(tarea)).thenReturn(tarea);
        // Llamamos al método saveTarea del tareaService con la tarea como argumento y almacenamos el resultado en tareaCreada
        Tarea tareaCreada = tareaService.saveTarea(tarea);
        // Verificamos con Mockito que el método save del tareaRepositoryMock se ha llamado exactamente una vez con la tarea como argumento
        Mockito.verify(tareaRepositoryMock, times(1)).save(tarea);
        // Verificamos que la tareaCreada que se obtuvo de tareaService.saveTarea(tarea) sea la misma que la tarea original
        assertEquals(tarea, tareaCreada);
    }

    @Test
    public void testFindTareaById() {
        // Creamos una nueva instancia de Tarea
        Tarea tarea = new Tarea();
        // Asignamos algunas propiedades
        tarea.setId(1L);
        // Predefinimos la respuesta del método findById de tareaRepositoryMock para que devuelva la misma tarea
        when(tareaRepositoryMock.findById(1L)).thenReturn(Optional.of(tarea));
        //
        Tarea tareaServiceTareaById = tareaService.getTareaById(1L);
        //
        Mockito.verify(tareaRepositoryMock, times(1)).findById(1L);
        //
        assertEquals(tarea, tareaServiceTareaById);
    }

    @Test
    public void testUpdateTarea() {
        //
        Tarea tarea = new Tarea();
        //
        tarea.setId(1L);
        tarea.setTitulo("Test Titulo");
        //
        when(tareaRepositoryMock.findById(1L)).thenReturn(Optional.of(tarea));
        when(tareaRepositoryMock.save(any(Tarea.class))).thenReturn(tarea);
        //
        tarea.setTitulo("Actualizar Titulo");
        Tarea tareaActualizada = tareaService.updateTarea(tarea);
        //
        Mockito.verify(tareaRepositoryMock, times(1)).save(tarea);
        //
        assertEquals("Actualizar Titulo", tareaActualizada.getTitulo());
    }

    @Test
    public void testDeleteTarea() {
        //
        Tarea tarea = new Tarea();
        //
        tarea.setId(1L);
        //
        when(tareaRepositoryMock.findById(1L)).thenReturn(Optional.of(tarea));
        //
        tareaService.deleteTarea(1L);
        //
        Mockito.verify(tareaRepositoryMock, times(1)).delete(tarea);
    }

    @Test
    public void testCreateTareaWithInvalidData() {
        //
        Tarea tarea = new Tarea();
        //
        Exception exception = assertThrows(InvalidTareaDataException.class, () -> tareaService.saveTarea(tarea));
        //
        String expectedMessage = "El titulo de la tarea es requerido.";
        String actualMessage = exception.getMessage();
        //
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Caso de prueba para verificar que `TareaService.getTareaById()` devuelve nulo cuando el ID no existe.
     */
    @Test
    public void testFindTareaByNonExistentId() {
        when(tareaRepositoryMock.findById(99L)).thenReturn(Optional.empty());
        //
        Tarea tarea = tareaService.getTareaById(99L);
        //
        assertNull(tarea);
    }
}
