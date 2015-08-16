/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.ccplus.controller;

import java.util.ArrayList;
import mx.com.ccplus.model.Pregunta;
import mx.com.ccplus.model.Registro;

/**
 *
 * @author Acer
 */
public class RegistroDAO {
    
    public ArrayList<Registro> transformToRegistro(ArrayList<Pregunta> listaPreguntas){
        ArrayList<Registro> listaRegistros = new ArrayList<Registro>();
        
        for(Pregunta pregunta : listaPreguntas){
            
            Registro registroSample = createSampleReg(pregunta);
            int respuesta = new PreguntaDAO().parseRespuesta(pregunta.getResp());
            
            if(listaRegistros.contains(registroSample)){
                increaseResp(listaRegistros.get(listaRegistros.indexOf(registroSample)), respuesta);
            }else{
                Registro r = new Registro(pregunta.getPregunta(), "0", "0", "0", "0", "0", "0", "0", "0", pregunta.getMat_A(), pregunta.getMat_C(), pregunta.getMat_S(), pregunta.getLetra(), pregunta.getNumero());
                increaseResp(r, respuesta);
                listaRegistros.add(r);                
            }
        }
        
        return listaRegistros;
    }
    
    public Registro createSampleReg(Pregunta pregunta){
        return new Registro(pregunta.getMat_A(), pregunta.getMat_C(), pregunta.getMat_S(), pregunta.getLetra(), pregunta.getNumero());
    }
    
    private void increaseResp(Registro registro, int i){
        //if(respuesta == null || respuesta.equals("0") || respuesta.equals(""))
//        int i = 0;
//        boolean flag = false;
//        try{
//            i = Integer.parseInt(respuesta);
//            flag = true;
//        }catch(Exception e){
//            System.out.println("Error parsing respuesta");
//            return false;
//        }
        
//        if(flag != false && i != 0){
            if(i==1){
                registro.setUno(Integer.toString(Integer.parseInt(registro.getUno()) + 1));
                //return true;
            }else if(i==2){
                registro.setDos(Integer.toString(Integer.parseInt(registro.getDos()) + 1));
                //return true;
            }else if(i==3){
                registro.setTres(Integer.toString(Integer.parseInt(registro.getTres()) + 1));
                //return true;
            }else if(i==4){
                registro.setCuatro(Integer.toString(Integer.parseInt(registro.getCuatro()) + 1));
                //return true;
            }else if(i==5){
                registro.setCinco(Integer.toString(Integer.parseInt(registro.getCinco()) + 1));
                //return true;
            }//else{
//                System.out.println("respuesta mayor a 5");
//                return false;
//            }
//        }else{
//            System.out.println("sigue siendo cero");
//        }
//        return false;
            if(i > 0 && i < 6)
            registro.setTotal(Integer.toString(Integer.parseInt(registro.getTotal()) + 1));
        
    }
    
}
