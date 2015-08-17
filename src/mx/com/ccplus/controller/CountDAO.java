package mx.com.ccplus.controller;

import java.util.ArrayList;
import mx.com.ccplus.model.Count;
import mx.com.ccplus.model.Unit;

public class CountDAO {
    
    public ArrayList<Count> countUnits(ArrayList<Unit> listaUnit){
        ArrayList<Count> listaCount = new ArrayList<Count>();
        
        for(Unit u : listaUnit){
            if(listaCount.contains(createSampleCount(u))){
                listaCount.get(listaCount.indexOf(createSampleCount(u))).getRespuestas()[u.getRespuesta()-1]++;
            }else{
                Count c = new Count(u.getMateria(), u.getMaestro(), u.getPregunta());
                c.getRespuestas()[u.getRespuesta()-1]++;
                listaCount.add(c);
            }
        }
        
        return listaCount;
    }
    
    private Count createSampleCount(Unit u){
        return new Count(u.getMateria(), u.getMaestro(), u.getPregunta());
    }
    
}
