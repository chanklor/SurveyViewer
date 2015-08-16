package mx.com.ccplus.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.com.ccplus.SurveyViewer;
import mx.com.ccplus.controller.RegistroDAO;
import mx.com.ccplus.model.Pregunta;
import mx.com.ccplus.model.Registro;


public class Main_FXMLController implements Initializable {
    
    @FXML
    TableView<Registro> tabla;
    
    @FXML
    TableColumn<Registro, String> colPregunta;
    
    @FXML
    TableColumn<Registro, String> colUno;
    
    @FXML
    TableColumn<Registro, String> colDos;
    
    @FXML
    TableColumn<Registro, String> colTres;
    
    @FXML
    TableColumn<Registro, String> colCuatro;
    
    @FXML
    TableColumn<Registro, String> colCinco;
    
    @FXML
    TableColumn<Registro, String> colTotal;
    
    @FXML
    TableColumn<Registro, String> colSubtotal;
    
    @FXML
    TableColumn<Registro, String> colPromedio;
    
    private ObservableList<Registro> listaObservable = FXCollections.observableArrayList();
    private FilteredList<Registro> listaFiltrada;
    private SortedList<Registro> listaOrdenada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setPregunta();
        populateData();
        tablePrefs();
    }
    
    private void setPregunta(){
        colPregunta.setCellValueFactory(new PropertyValueFactory<Registro, String>("pregunta"));
        colUno.setCellValueFactory(new PropertyValueFactory<Registro, String>("uno"));
        colDos.setCellValueFactory(new PropertyValueFactory<Registro, String>("dos"));
        colTres.setCellValueFactory(new PropertyValueFactory<Registro, String>("tres"));
        colCuatro.setCellValueFactory(new PropertyValueFactory<Registro, String>("cuatro"));
        colCinco.setCellValueFactory(new PropertyValueFactory<Registro, String>("cinco"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Registro, String>("total"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<Registro, String>("subtotal"));
        colPromedio.setCellValueFactory(new PropertyValueFactory<Registro, String>("promedio"));
    }
    
    private void populateData(){
        for (Registro registro : new RegistroDAO().transformToRegistro(SurveyViewer.getLista())) {
            //if(registro.getMat_A().equals("LD") && registro.getMat_C().equals("1") && registro.getMat_S().equals("1") && registro.getLetra().equals("A")) 
                listaObservable.add(registro);
            
        }
    }
    
    private void tablePrefs(){
        listaFiltrada = new FilteredList<>(listaObservable, p -> true);
        listaOrdenada = new SortedList<>(listaFiltrada);
        listaOrdenada.comparatorProperty().bind(tabla.comparatorProperty());
        tabla.setItems(listaOrdenada);
    }
    
    
    
}
