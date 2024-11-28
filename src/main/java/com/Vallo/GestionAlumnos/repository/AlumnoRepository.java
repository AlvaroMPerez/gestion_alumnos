package com.Vallo.GestionAlumnos.repository;

import com.Vallo.GestionAlumnos.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    // Métodos personalizados si los necesitas
}
