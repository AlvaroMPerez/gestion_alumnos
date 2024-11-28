package com.Vallo.GestionAlumnos.controller;

import org.springframework.http.ResponseEntity;
import com.Vallo.GestionAlumnos.model.Alumno;
import com.Vallo.GestionAlumnos.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Map;


import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {
    @Autowired
    private AlumnoRepository alumnoRepository;

    // Obtener todos los alumnos
    @GetMapping
    public List<Alumno> getAllAlumnos(){
        return alumnoRepository.findAll();
    }

    // Crear alumno
    @PostMapping
    public Alumno createAlumno(@RequestBody Alumno alumno){
        return alumnoRepository.save(alumno);
    }

    // Obtener alumno ID
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable Long id) {
        Optional<Alumno> alumno = alumnoRepository.findById(id);
        if (alumno.isPresent()) {
            return ResponseEntity.ok(alumno.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Modificar alumno
    @PostMapping("/{id}")
    public ResponseEntity<Alumno> updateAlumno(@PathVariable Long id, @RequestBody Alumno alumnoDetails){
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(id);
        if (!alumnoOptional.isPresent()){
            return  ResponseEntity.notFound().build();
        }

        Alumno alumno = alumnoOptional.get();
        alumno.setNombre(alumnoDetails.getNombre());
        alumno.setEdad(alumnoDetails.getEdad());
        alumno.setCorreo(alumnoDetails.getCorreo());
        alumno.setVersion(alumnoDetails.getVersion());

        Alumno updatedAlumno = alumnoRepository.save(alumno);
        return ResponseEntity.ok(updatedAlumno);
    }

    // Modicicar unicamente una cosa con patch
    @PatchMapping("/{id}")
    public ResponseEntity<Alumno> updateAlumnoPartial(@PathVariable long id, @RequestBody Map<String, Object> updates){
        Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
        if (!optionalAlumno.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Alumno alumno = optionalAlumno.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "nombre":
                    alumno.setNombre((String) value);
                    break;
                case "edad":
                    alumno.setEdad((Integer) value);
                    break;
                case "correo":
                    alumno.setCorreo((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Campo no válido: " + key);

            }

        });

        Alumno updatedAlumno = alumnoRepository.save(alumno);

        return ResponseEntity.ok(updatedAlumno);
    }

    // Borrar alumno

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable long id){
        Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
        if (!optionalAlumno.isPresent()){
            return ResponseEntity.notFound().build();
        }
        alumnoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
        }
}
