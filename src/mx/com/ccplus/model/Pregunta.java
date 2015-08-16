/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.ccplus.model;

import java.util.Objects;

/**
 *
 * @author Acer
 */
public class Pregunta {
    private String mat_A;
    private String mat_C;
    private String mat_S;
    private String letra;
    private String numero;
    
    private String materia;
    private String maestro;
    private String pregunta;
    private String resp;

    public Pregunta(String mat_A, String mat_C, String mat_S, String letra, String numero, String materia, String maestro, String pregunta, String resp) {
        this.mat_A = mat_A;
        this.mat_C = mat_C;
        this.mat_S = mat_S;
        this.letra = letra;
        this.numero = numero;
        this.materia = materia;
        this.maestro = maestro;
        this.pregunta = pregunta;
        this.resp = resp;
    }

    public String getMat_A() {
        return mat_A;
    }

    public void setMat_A(String mat_A) {
        this.mat_A = mat_A;
    }

    public String getMat_C() {
        return mat_C;
    }

    public void setMat_C(String mat_C) {
        this.mat_C = mat_C;
    }

    public String getMat_S() {
        return mat_S;
    }

    public void setMat_S(String mat_S) {
        this.mat_S = mat_S;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Pregunta other = (Pregunta) obj;
        if (!Objects.equals(this.mat_A, other.mat_A)) {
            return false;
        }
        if (!Objects.equals(this.mat_C, other.mat_C)) {
            return false;
        }
        if (!Objects.equals(this.mat_S, other.mat_S)) {
            return false;
        }
        if (!Objects.equals(this.letra, other.letra)) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        return true;
    }
    
    
    
}
