package com.metaphorce.challenge.repositories;

import com.metaphorce.challenge.models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.transaction.Transactional;

@Transactional
public interface TareaRepository extends JpaRepository<Tarea, Long> {
}
