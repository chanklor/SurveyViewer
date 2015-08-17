package mx.com.ccplus;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.com.ccplus.controller.CountDAO;
import mx.com.ccplus.controller.PreguntaDAO;
import mx.com.ccplus.controller.RegistroDAO;
import mx.com.ccplus.model.Count;
import mx.com.ccplus.model.Pregunta;
import mx.com.ccplus.model.Registro;
import mx.com.ccplus.model.Unit;

public class SurveyViewer {
    
    private static ArrayList<Count> listaCount = new ArrayList<Count>();
    
    public SurveyViewer(ResultSet rs){
        try {
            
            int total = rs.getMetaData().getColumnCount();
            if(total != 4) throw new IllegalArgumentException("ResultSet Incorrecto");
            
            ArrayList<Unit> listaUnit = new ArrayList<Unit>();
            
            while (rs.next()) {
                
                String materia = rs.getString("Materia");
                String maestro = rs.getString("Maestro");
                String pregunta = rs.getString("Pregunta");
                int respuesta = rs.getInt("RespN");                
                
                if(materia != null && maestro != null && pregunta != null && respuesta > 0 && respuesta < 6 )
                    listaUnit.add(new Unit(materia, maestro, pregunta, respuesta));
                          
            }
            
            listaCount = new CountDAO().countUnits(listaUnit);
            
        } catch (SQLException ex) {
            System.out.println("error rs");
        }
    }
    
    public void mostrarTabla(){
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("view/Main_FXML.fxml"));
            Parent root = (Parent) fXMLLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("error al abrir tabla");
            e.printStackTrace();
        }
    }
    
    public static ArrayList<Count> getListaCount(){
        return listaCount;
    }
    
}
