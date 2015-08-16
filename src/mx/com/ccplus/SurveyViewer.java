package mx.com.ccplus;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.com.ccplus.controller.PreguntaDAO;
import mx.com.ccplus.model.Pregunta;

public class SurveyViewer {
    
    private static ArrayList<Pregunta> lista = new ArrayList<Pregunta>();
    
    public SurveyViewer(ArrayList<String[]> lista){
        this.lista = new PreguntaDAO().transformToPreguntas(lista);
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
    
    public static ArrayList<Pregunta> getLista(){
        return lista;
    }
    
}
