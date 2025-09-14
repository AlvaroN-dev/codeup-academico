/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistroAcademico.codeup.academico.service;

import RegistroAcademico.codeup.academico.domain.Estudiante;
import RegistroAcademico.codeup.academico.domain.Nota;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author anonimo
 */
public class CalculoService {
    
    // para calcular el promedio
    public double promedio(List<Nota> notas){
        return  notas.stream()
                .mapToDouble(Nota::getValor) //DoubleStream
                .average()  //optionalDouble
                .orElse(0.0); 
    
    }
    
    
    public Nota notaMaxima(List<Nota> notas){
        return notas.stream()
                .max(Comparator.comparingDouble(Nota::getValor)) // funcion lambda 
                .orElse(null);
        
    }
    
    
    public  boolean aprovado(double promedio){
        
        return promedio >= 3.0;
    }
    
    
    public static void main(String[] args) {
        Nota nota1 = new Nota(3);
        Nota nota2 = new Nota(3);
        Nota nota3 = new Nota(3);
        
        
        String name = "hola";
        Estudiante est = new Estudiante(name, 0);
        
       est.agregarNota(nota1);
       est.agregarNota(nota2);
       est.agregarNota(nota3);
       
       
       
        CalculoService prom = new CalculoService();
        double pr= prom.promedio(est.getNotas());
     
        System.out.println(pr);
        
    }
}
