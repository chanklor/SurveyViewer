/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.ccplus.model;

/**
 *
 * @author Acer
 */
public class Unit {
    
    private String materia;
    private String maestro;
    private String pregunta;
    private int respuesta;

    public Unit(String materia, String maestro, String pregunta, int respuesta) {
        this.materia = materia;
        this.maestro = maestro;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public Unit() {
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getMaestro() {
        return maestro;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }
    
}
