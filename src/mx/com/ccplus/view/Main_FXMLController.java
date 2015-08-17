package mx.com.ccplus.view;

import java.net.URL;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
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
    
    @FXML
    ChoiceBox<Materia> choiceMateria;
    
    @FXML
    ChoiceBox<String> choiceMatMae;
    
    @FXML
    ChoiceBox<Maestro> choiceMaestro;
    
    @FXML
    ChoiceBox<String> choiceMaeMat;
    
    private ObservableList<Registro> listaObservable = FXCollections.observableArrayList();
    private FilteredList<Registro> listaFiltrada;
    private SortedList<Registro> listaOrdenada;
    
//    private ArrayList<Materia> listaMateria = new ArrayList<Materia>();
//    private ArrayList<Maestro> listaMaestro = new ArrayList<Maestro>();
    
    private ObservableList<Materia> listaMateria = FXCollections.observableArrayList();
    private ObservableList<String> listaMatMae = FXCollections.observableArrayList();
    private ObservableList<Maestro> listaMaestro = FXCollections.observableArrayList();
    private ObservableList<String> listaMaeMat = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColumns();
        setChoiceBoxes();
        populateData();
        tablePrefs();
    }
    
    private void setColumns(){
        colPregunta.setCellValueFactory(new PropertyValueFactory<Registro, String>("pregunta"));
        
        colPregunta.setCellFactory
        (
          column ->
           {
             return new TableCell<Registro, String>()
              {
                @Override
                protected void updateItem(String item, boolean empty)
                 {
                    super.updateItem(item, empty);
                    setText(item);
                    
                    Registro reg = (Registro) getTableRow().getItem();
                    if(reg!=null){
                        setTooltip(new Tooltip( reg.getMateria() + " / " + reg.getMaestro() ));
                    }
                 }
              };
           });
        
        colUno.setCellValueFactory(new PropertyValueFactory<Registro, String>("uno"));
        colDos.setCellValueFactory(new PropertyValueFactory<Registro, String>("dos"));
        colTres.setCellValueFactory(new PropertyValueFactory<Registro, String>("tres"));
        colCuatro.setCellValueFactory(new PropertyValueFactory<Registro, String>("cuatro"));
        colCinco.setCellValueFactory(new PropertyValueFactory<Registro, String>("cinco"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Registro, String>("total"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<Registro, String>("subtotal"));
        colPromedio.setCellValueFactory(new PropertyValueFactory<Registro, String>("promedio"));
    }
    
    private void setChoiceBoxes(){
        choiceMateria.setConverter(new MateriaConverter());
//        choiceMateria.setItems(listaMateria);
        choiceMateria.setItems(new SortedList<Materia>(listaMateria, new MateriaComparator()));
        choiceMatMae.setItems(listaMatMae);
        
        choiceMateria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Materia>() {

            @Override
            public void changed(ObservableValue<? extends Materia> observable, Materia oldValue, Materia newValue) {
                
                if(newValue != null) choiceMaestro.getSelectionModel().clearSelection();
                
                listaMatMae.clear();
                listaMatMae.addAll(newValue.getLista());
                Collections.sort(listaMatMae);
                
                
            }
        });
        
        choiceMatMae.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                
                listaFiltrada.setPredicate(registro -> {
                    
                    String materia = registro.getMateria();
                    String maestro = registro.getMaestro();
                    
                    if(newValue == null) return false;
                    
                    if(choiceMateria.getSelectionModel().getSelectedItem().getNombre().equals(materia)
                            && newValue.equals(maestro)){
                        return true;
                    }
                    
                    return false;
                });
                
            }
        });
        
        choiceMaestro.setConverter(new MaestroConverter());
//        choiceMaestro.setItems(listaMaestro);
        choiceMaestro.setItems(new SortedList<Maestro>(listaMaestro, new MaestroComparator()));
        choiceMaeMat.setItems(listaMaeMat);
        
        choiceMaestro.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Maestro>() {

            @Override
            public void changed(ObservableValue<? extends Maestro> observable, Maestro oldValue, Maestro newValue) {
                
                if(newValue != null) choiceMateria.getSelectionModel().clearSelection();
                
                listaMaeMat.clear();
                listaMaeMat.addAll(newValue.getLista());
                Collections.sort(listaMaeMat);
                
            }
        });
        
        choiceMaeMat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                
                listaFiltrada.setPredicate(registro -> {
                    
                    String materia = registro.getMateria();
                    String maestro = registro.getMaestro();
                    
                    if(newValue == null) return false;
                    
                    if(choiceMaestro.getSelectionModel().getSelectedItem().getNombre().equals(maestro)
                            && newValue.equals(materia)){
                        return true;
                    }
                    
                    return false;
                });
                
            }
        });
        
    }
    
    private void populateData(){
        for (Registro registro : new RegistroDAO().transformCountToRegistro(SurveyViewer.getListaCount())) {
            listaObservable.add(registro);
            
            if(listaMateria.contains(new Materia(registro.getMateria()))){
                Materia m = listaMateria.get(listaMateria.indexOf(new Materia(registro.getMateria())));
                if(!m.getLista().contains(registro.getMaestro())){
                    m.getLista().add(registro.getMaestro());
                }
            }else{
                Materia m = new Materia(registro.getMateria());
                m.getLista().add(registro.getMaestro());
                listaMateria.add(m);
            }
            
            if(listaMaestro.contains(new Maestro(registro.getMaestro()))){
                Maestro m = listaMaestro.get(listaMaestro.indexOf(new Maestro(registro.getMaestro())));
                if(!m.getLista().contains(registro.getMateria())){
                    m.getLista().add(registro.getMateria());
                }
            }else{
                Maestro m = new Maestro(registro.getMaestro());
                m.getLista().add(registro.getMateria());
                listaMaestro.add(m);
            }
            
        }
        
        
        
    }
    
    private void tablePrefs(){
        listaFiltrada = new FilteredList<>(listaObservable, p -> false);
        listaOrdenada = new SortedList<>(listaFiltrada);
        listaOrdenada.comparatorProperty().bind(tabla.comparatorProperty());
        tabla.setItems(listaOrdenada);
        
        
    }
    
    class Materia{
        private String nombre;
        private ArrayList<String> lista = new ArrayList<String>();

        public Materia(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        public ArrayList<String> getLista() {
            return lista;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Materia other = (Materia) obj;
            if (!Objects.equals(this.nombre, other.nombre)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Materia{" + "nombre=" + nombre + ", lista=" + lista + '}';
        }
        
        
        
    }
    
    class Maestro{
        private String nombre;
        private ArrayList<String> lista = new ArrayList<String>();

        public Maestro(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        public ArrayList<String> getLista() {
            return lista;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Maestro other = (Maestro) obj;
            if (!Objects.equals(this.nombre, other.nombre)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Maestro{" + "nombre=" + nombre + ", lista=" + lista + '}';
        }
        
        
        
    }
    
    class MateriaConverter extends StringConverter<Materia>{

        @Override
        public String toString(Materia object) {
            return object.getNombre();
        }

        @Override
        public Materia fromString(String string) {
            return new Materia(string);
        }
        
    }
    
    class MaestroConverter extends StringConverter<Maestro>{

        @Override
        public String toString(Maestro object) {
            return object.getNombre();
        }

        @Override
        public Maestro fromString(String string) {
            return new Maestro(string);
        }
        
    }
    
    class MateriaComparator implements Comparator<Materia>{

        @Override
        public int compare(Materia o1, Materia o2) {
            return o1.getNombre().compareTo(o2.getNombre());
        }
        
    }
    
    class MaestroComparator implements Comparator<Maestro>{

        @Override
        public int compare(Maestro o1, Maestro o2) {
            return o1.getNombre().compareTo(o2.getNombre());
        }
        
    }
    
}
