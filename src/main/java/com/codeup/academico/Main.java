/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeup.academico;

/**
 *
 * @author anonimo
 */
public class Main {
    public static void main(String[] args) {
        Academico academico = new Academico();
        
        academico.setName("Alvaro");
        academico.setCouser("programacion");
        academico.setEmail("juan@gmial.com");
        academico.setIdStudent(123344545);
        
        System.out.println(academico.getName());
        System.out.println(academico.getCourse());
        System.out.println(academico.getEmail());
        System.out.println(academico.getIdStudent());
    }
}
