/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistroAcademico.codeup.academico.service;

import RegistroAcademico.codeup.academico.domain.Estudiante;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author hi
 */

public class RegistroEstudiantesService {

    private final List<Estudiante> estudiantes = new ArrayList<>();

    CalculoService operaciones = new CalculoService();

    public void agregarEstudiante(Estudiante e) {
        estudiantes.add(e);
    }

    public List<Estudiante> listarEstudiantes() {

        for (int i = 0; i < estudiantes.size(); i++) {
            System.out.println(i);
        }
        return estudiantes;
    }

    public double calcularPromedioGeneral() {
        double suma = 0;
        int totalNotas = 0;

        for (Estudiante e : estudiantes) {
            suma += operaciones.promedio(e.getNotas());
            totalNotas++;
        }

        return suma / totalNotas;

    }

    public Optional<Estudiante> mejorEstudiante() {
        return estudiantes.stream()
                .max(Comparator.comparingDouble(
                        e -> operaciones.promedio(e.getNotas())
                ));

    }

    public long contarAprovados() {
        return estudiantes.stream()
                .filter(e -> operaciones.promedio(e.getNotas()) >= 3.0)
                .count();
    }

    public long contarReprobado() {
        return estudiantes.stream()
                .filter(e -> operaciones.promedio(e.getNotas()) < 3.0)
                .count();
    }
    
    public void reemplazar(List<Estudiante> nuevosEstudiantes) {
        estudiantes.clear();
        estudiantes.addAll(nuevosEstudiantes);
    }

    public void fusionar(List<Estudiante> nuevosEstudiantes) {
        Map<String, Estudiante> estudiantesMap = new HashMap<>();

        // Agregar estudiantes existentes al map
        for (Estudiante estudiante : estudiantes) {
            estudiantesMap.put(estudiante.getId(), estudiante);
        }

      
        for (Estudiante nuevoEstudiante : nuevosEstudiantes) {
            estudiantesMap.put(nuevoEstudiante.getId(), nuevoEstudiante);
        }

        // Actualizar la lista
        estudiantes.clear();
        estudiantes.addAll(estudiantesMap.values());
    }

    public List<Estudiante> obtenerTodos() {
        return new ArrayList<>(estudiantes);
    }
}
