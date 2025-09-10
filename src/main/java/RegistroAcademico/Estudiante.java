/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistroAcademico;

/**
 *
 * @author anonimo
 */
public class Estudiante {
    private String name;
    private int age;
    private double grade1, grade2, grade3;

    public Estudiante(String name, int age, double grade1, double grade2, double grade3) {
        this.name = name;
        this.age = age;
        this.grade1 = grade1;
        this.grade2 = grade2;
        this.grade3 = grade3;
    }

    public String getName() {
        return name;
    }
    

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

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
    
    void hola(){
        System.out.println("hola");
    }
    
   
   
}
