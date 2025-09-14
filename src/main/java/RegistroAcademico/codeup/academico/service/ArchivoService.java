/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package RegistroAcademico.codeup.academico.service;

import RegistroAcademico.codeup.academico.domain.Estudiante;
import RegistroAcademico.codeup.academico.domain.Nota;
import java.io.*;
import java.util.*;

public class ArchivoService {
    
    private static final String CSV_HEADER = "id,nombre,edad,nota1,nota2,nota3";
    private static final String CSV_SEPARATOR = ",";
    
    
    public void guardarCSV(File archivo, List<Estudiante> estudiantes) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
          
            writer.println(CSV_HEADER);
            
            
            for (Estudiante estudiante : estudiantes) {
                StringBuilder linea = new StringBuilder();
                linea.append(escaparCSV(estudiante.getId())).append(CSV_SEPARATOR)
                     .append(escaparCSV(estudiante.getName())).append(CSV_SEPARATOR)
                     .append(estudiante.getAge()).append(CSV_SEPARATOR);
                
               
                List<Nota> notas = estudiante.getNotas();
                for (int i = 0; i < 3; i++) {
                    if (i < notas.size()) {
                        linea.append(notas.get(i).getValor());
                    } else {
                        linea.append("0.0"); // Valor por defecto si no hay nota
                    }
                    if (i < 2) linea.append(CSV_SEPARATOR);
                }
                
                writer.println(linea.toString());
            }
        }
    }
    
    
    public List<Estudiante> cargarCSV(File archivo) throws IOException, IllegalArgumentException {
        List<Estudiante> estudiantes = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea = reader.readLine();
            
            
            if (linea == null || !linea.equals(CSV_HEADER)) {
                throw new IllegalArgumentException("Formato de archivo inválido. Se esperaba: " + CSV_HEADER);
            }
            
            int numeroLinea = 1;
            while ((linea = reader.readLine()) != null) {
                numeroLinea++;
                
                if (linea.trim().isEmpty()) {
                    continue; 
                }
                
                try {
                    Estudiante estudiante = parsearLineaCSV(linea, numeroLinea);
                    estudiantes.add(estudiante);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Error en línea " + numeroLinea + ": " + e.getMessage());
                }
            }
        }
        
        if (estudiantes.isEmpty()) {
            throw new IllegalArgumentException("El archivo no contiene datos válidos de estudiantes.");
        }
        
        return estudiantes;
    }
    
    /**
     * Parsea una línea CSV y crea un objeto Estudiante
     */
    private Estudiante parsearLineaCSV(String linea, int numeroLinea) {
        String[] campos = linea.split(CSV_SEPARATOR, -1); 
        
        if (campos.length != 6) {
            throw new IllegalArgumentException("Se esperaban 6 columnas, se encontraron " + campos.length);
        }
        
        try {
           
            String nombre = campos[1].trim().replace("\"", ""); 
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío");
            }
            
            
            int edad = Integer.parseInt(campos[2].trim());
            if (edad <= 0 || edad > 150) {
                throw new IllegalArgumentException("Edad debe estar entre 1 y 150");
            }
            
           
            Estudiante estudiante = new Estudiante(nombre, edad);
            
            
            for (int i = 3; i < 6; i++) {
                double valorNota = Double.parseDouble(campos[i].trim());
                if (valorNota < 0.0 || valorNota > 5.0) {
                    throw new IllegalArgumentException("Las notas deben estar entre 0.0 y 5.0");
                }
                
                try {
                    Nota nota = new Nota(valorNota);
                    estudiante.agregarNota(nota);
                } catch (IllegalStateException e) {
                    throw new IllegalArgumentException("Error al agregar nota: " + e.getMessage());
                }
            }
            
            return estudiante;
            
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato numérico inválido: " + e.getMessage());
        }
    }
    
    
    private String escaparCSV(String valor) {
        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }
    
    
    
    public static boolean esArchivoCSV(File archivo) {
        return archivo.getName().toLowerCase().endsWith(".csv");
    }
    
}