/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistroAcademico.codeup.academico.domain;

import RegistroAcademico.codeup.academico.service.CalculoService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author anonimo
 */
public class Estudiante {
    private final String id = UUID.randomUUID().toString();
    private String name;
    private int age;
    private List<Nota> notas;

    public Estudiante(String name, int age) {
        this.name = name;
        this.age = age;
        this.notas = new ArrayList<>();
      
    }
    
    public String getName() {
        return name;
    }
    
    
    public int getAge() {
        return age;
    }
 
    public String getId(){ return id ;}
    
    
    public void agregarNota(Nota nota){
        if(notas.size() >= 3){
            throw new IllegalStateException("El estudiante no puede tener mas de 3 notas");
        }
        notas.add(nota);
    }
    
    
    
   
    public List<Nota> getNotas(){ return notas; }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    public double getGrade1() {
        return grade1;
    }

    public void setGrade1(double grade1) {
        this.grade1 = grade1;
    }

    public double getGrade2() {
        return grade2;
    }

    public void setGrade2(double grade2) {
        this.grade2 = grade2;
    }

    public double getGrade3() {
        return grade3;
    }

    public void setGrade3(double grade3) {
        this.grade3 = grade3;
    }
    
    
    
    public double calculateAverage(){
        return (grade1 + grade2 + grade3) /3.0;
    }
    
    public double gradeMax(){
        return Math.max(grade1, Math.max(grade2, grade3));
    }
    
    public boolean approved(){
        return calculateAverage() >= 3.0;
    }
    
   */

  
   
}
