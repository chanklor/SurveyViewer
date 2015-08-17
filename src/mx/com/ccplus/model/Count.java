/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.ccplus.model;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Acer
 */
public class Count {
    
    private String materia;
    private String maestro;
    private String pregunta;
    private int[] respuestas = new int[5];

    public Count() {
    }

    public Count(String materia, String maestro, String pregunta) {
        this.materia = materia;
        this.maestro = maestro;
        this.pregunta = pregunta;
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

    public int[] getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(int[] respuestas) {
        this.respuestas = respuestas;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.materia);
        hash = 67 * hash + Objects.hashCode(this.maestro);
        hash = 67 * hash + Objects.hashCode(this.pregunta);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Count other = (Count) obj;
        if (!Objects.equals(this.materia, other.materia)) {
            return false;
        }
        if (!Objects.equals(this.maestro, other.maestro)) {
            return false;
        }
        if (!Objects.equals(this.pregunta, other.pregunta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Count{" + "materia=" + materia + ", maestro=" + maestro + ", pregunta=" + pregunta + ", respuestas=" + Arrays.toString(respuestas) + '}';
    }

    
    
}
