/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package RegistroAcademico.codeup.academico.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class UsuarioService {
    
  
    private static final Map<String, char[]> USUARIOS = new HashMap<>();
    
    static {
       
        USUARIOS.put("admin", "admin123".toCharArray());
        USUARIOS.put("profesor", "prof2024".toCharArray());
        USUARIOS.put("usuario", "12345".toCharArray());
    }
    
    
    public boolean autenticar(String usuario, char[] password) {
        if (usuario == null || password == null) {
            return false;
        }
        
        
        char[] passwordAlmacenada = USUARIOS.get(usuario.toLowerCase());
        
        if (passwordAlmacenada == null) {
            return false; // Usuario no existe
        }
        
      
        boolean esValida = Arrays.equals(password, passwordAlmacenada);
        
        return esValida;
    }
    
   
    public boolean existeUsuario(String usuario) {
        return USUARIOS.containsKey(usuario.toLowerCase());
    }
    
    
    public String[] getUsuariosDisponibles() {
        return USUARIOS.keySet().toArray(new String[0]);
    }
    
 
    public boolean cambiarPassword(String usuario, char[] passwordActual, char[] passwordNueva) {
        if (autenticar(usuario, passwordActual)) {
            USUARIOS.put(usuario.toLowerCase(), Arrays.copyOf(passwordNueva, passwordNueva.length));
            return true;
        }
        return false;
    }
}