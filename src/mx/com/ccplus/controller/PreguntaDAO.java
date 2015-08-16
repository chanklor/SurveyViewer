package mx.com.ccplus.controller;

import java.util.ArrayList;
import mx.com.ccplus.model.Pregunta;

public class PreguntaDAO {
    
    public ArrayList<Pregunta> transformToPreguntas(ArrayList<String[]> lista){
        ArrayList<Pregunta> listaPreguntas = new ArrayList<>();
        
        for(String[] string : lista){
            if(string[10].equals("\"Gómez Coronel Yslas, Lic. Xavier Federico\""))
            listaPreguntas.add(new Pregunta(string[3], string[4], string[6], string[13], string[14], string[9], string[10], string[15], string[16]));
            //System.out.println(string[10]);
        }
        
        return  listaPreguntas;
    }
    
    public int parseRespuesta(String respuesta){
        try{
            int i = Integer.parseInt(respuesta);
            if(i > 0 && i < 6) return i;
            else return 0;
        }catch(Exception e){
            return 0;
        }
    }
    
}
