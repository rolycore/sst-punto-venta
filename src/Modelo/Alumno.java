/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Rolando Salinas
 */
public class Alumno {
    
    private String  cedula ;
    private String nombre;
    private char sexo;
    private String grado;
   
    
    public Alumno(){
    }

    public Alumno(String cedula, String nombre, char sexo, String grado) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.sexo = sexo;
        this.grado = grado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }
    
    
    
}
