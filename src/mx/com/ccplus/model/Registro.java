/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.ccplus.model;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Acer
 */
public class Registro{
        
        private final StringProperty pregunta;
        private final StringProperty uno;
        private final StringProperty dos;
        private final StringProperty tres;
        private final StringProperty cuatro;
        private final StringProperty cinco;
        private final StringProperty total;
        private final StringProperty subtotal;
        private final StringProperty promedio;
        private String mat_A;
        private String mat_C;
        private String mat_S;
        private String letra;
        private String numero;
        private String materia;
        private String maestro;
        
        public Registro(){
            this.pregunta = null;
            this.uno = null;
            this.dos = null;
            this.tres = null;
            this.cuatro = null;
            this.cinco = null;
            this.total = null;
            this.subtotal = null;
            this.promedio = null;
        }

        public Registro(String mat_A, String mat_C, String mat_S, String letra, String numero) {
            this.pregunta = null;
            this.uno = null;
            this.dos = null;
            this.tres = null;
            this.cuatro = null;
            this.cinco = null;
            this.total = null;
            this.subtotal = null;
            this.promedio = null;
            
            this.mat_A = mat_A;
            this.mat_C = mat_C;
            this.mat_S = mat_S;
            this.letra = letra;
            this.numero = numero;
            
            
        }

        

        public Registro(String pregunta, String uno, String dos, String tres, String cuatro, String cinco, String total, String subtotal, String promedio){
            this.pregunta = new SimpleStringProperty(pregunta);
            this.uno = new SimpleStringProperty(uno);
            this.dos = new SimpleStringProperty(dos);
            this.tres = new SimpleStringProperty(tres);
            this.cuatro = new SimpleStringProperty(cuatro);
            this.cinco = new SimpleStringProperty(cinco);
            this.total = new SimpleStringProperty(total);
            this.subtotal = new SimpleStringProperty(subtotal);
            this.promedio = new SimpleStringProperty(promedio);
        }
        
        public Registro(String pregunta, String uno, String dos, String tres, String cuatro, String cinco, String total, String subtotal, String promedio, String materia, String maestro){
            this.pregunta = new SimpleStringProperty(pregunta);
            this.uno = new SimpleStringProperty(uno);
            this.dos = new SimpleStringProperty(dos);
            this.tres = new SimpleStringProperty(tres);
            this.cuatro = new SimpleStringProperty(cuatro);
            this.cinco = new SimpleStringProperty(cinco);
            this.total = new SimpleStringProperty(total);
            this.subtotal = new SimpleStringProperty(subtotal);
            this.promedio = new SimpleStringProperty(promedio);
            this.materia = materia;
            this.maestro = maestro;
        }
        
        
        public Registro(String pregunta, String uno, String dos, String tres, String cuatro, String cinco, String total, String subtotal, String promedio, String mat_A, String mat_C, String mat_S, String letra, String numero){
            this.pregunta = new SimpleStringProperty(pregunta);
            this.uno = new SimpleStringProperty(uno);
            this.dos = new SimpleStringProperty(dos);
            this.tres = new SimpleStringProperty(tres);
            this.cuatro = new SimpleStringProperty(cuatro);
            this.cinco = new SimpleStringProperty(cinco);
            this.total = new SimpleStringProperty(total);
            this.subtotal = new SimpleStringProperty(subtotal);
            this.promedio = new SimpleStringProperty(promedio);
            this.mat_A = mat_A;
            this.mat_C = mat_C;
            this.mat_S = mat_S;
            this.letra = letra;
            this.numero = numero;
        }       

        public String getPregunta() {
            return pregunta.get();
        }

        public String getUno() {
            return uno.get();
        }

        public String getDos() {
            return dos.get();
        }

        public String getTres() {
            return tres.get();
        }

        public String getCuatro() {
            return cuatro.get();
        }

        public String getCinco() {
            return cinco.get();
        }

        public String getTotal() {
            return total.get();
        }

        public String getSubtotal() {
            return subtotal.get();
        }

        public String getPromedio() {
            return promedio.get();
        }

        public void setPregunta(String pregunta){
            this.pregunta.set(pregunta);
        }

        public void setUno(String uno) {
            this.uno.set(uno);
        }

        public void setDos(String dos) {
            this.dos.set(dos);
        }

        public void setTres(String tres) {
            this.tres.set(tres);
        }

        public void setCuatro(String cuatro) {
            this.cuatro.set(cuatro);
        }

        public void setCinco(String cinco) {
            this.cinco.set(cinco);
        }

        public void setTotal(String total) {
            this.total.set(total);
        }

        public void setSubtotal(String subtotal) {
            this.subtotal.set(subtotal);
        }

        public void setPromedio(String promedio) {
            this.promedio.set(promedio);
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
        final Registro other = (Registro) obj;
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

    @Override
    public String toString() {
        return "Registro{" + " materia=" + materia + ", maestro=" + maestro + ", pregunta=" + pregunta.get() + ", uno=" + uno.get() + ", dos=" + dos.get() + ", tres=" + tres.get() + ", cuatro=" + cuatro.get() + ", cinco=" + cinco.get() + ", total=" + total.get() + ", subtotal=" + subtotal.get() + ", promedio=" + promedio.get() + '}';
    }

    
        
    
        
}
