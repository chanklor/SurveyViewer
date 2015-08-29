package mx.com.ccplus.view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import mx.com.ccplus.SurveyViewer;
import mx.com.ccplus.controller.RegistroDAO;
import mx.com.ccplus.model.Pregunta;
import mx.com.ccplus.model.Registro;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.imgscalr.Scalr;


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
    
    @FXML
    Button botonImprimir;
    
    @FXML
    ImageView imageView;
    
    @FXML
    LineChart<String, Number> lineChart;
    
    final XYChart.Series series1 = new XYChart.Series();
    final XYChart.Series series2 = new XYChart.Series();
    final XYChart.Series series3 = new XYChart.Series();
    final XYChart.Series series4 = new XYChart.Series();
    final XYChart.Series series5 = new XYChart.Series();
    
    private ObservableList<Registro> listaObservable = FXCollections.observableArrayList();
    private FilteredList<Registro> listaFiltrada;
    private SortedList<Registro> listaOrdenada;
    
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
        setChart();
        populateChart();
    }
    
    private void setColumns(){
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
    
    private void setChoiceBoxes(){
        choiceMateria.setConverter(new MateriaConverter());
//        choiceMateria.setItems(listaMateria);
        choiceMateria.setItems(new SortedList<Materia>(listaMateria, new MateriaComparator()));
        choiceMatMae.setItems(listaMatMae);
        
        choiceMateria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Materia>() {

            @Override
            public void changed(ObservableValue<? extends Materia> observable, Materia oldValue, Materia newValue) {
                
                if(newValue != null){
                    choiceMaestro.getSelectionModel().clearSelection();
                    choiceMaeMat.getSelectionModel().clearSelection();
                    assignImage(null);
                }
                
                listaMatMae.clear();
                listaMatMae.addAll(newValue.getLista());
                Collections.sort(listaMatMae);
                
                
            }
        });
        
        choiceMatMae.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if(newValue!=null){
                    String mat = extractMatricula(newValue);
                    if(mat!=null){
                        assignImage(mat);
                    }
                }
                                
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
                
                populateChart();
                
            }
        });
        
        choiceMaestro.setConverter(new MaestroConverter());
//        choiceMaestro.setItems(listaMaestro);
        choiceMaestro.setItems(new SortedList<Maestro>(listaMaestro, new MaestroComparator()));
        choiceMaeMat.setItems(listaMaeMat);
        
        choiceMaestro.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Maestro>() {

            @Override
            public void changed(ObservableValue<? extends Maestro> observable, Maestro oldValue, Maestro newValue) {
                
                if(newValue.getNombre()!=null){
                    String mat = extractMatricula(newValue.getNombre());
                    if(mat!=null){
                        assignImage(mat);
                    }
                }
                
                if(newValue != null){
                    choiceMateria.getSelectionModel().clearSelection();
                    choiceMatMae.getSelectionModel().clearSelection();
                }
                
                
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
                
                populateChart();
                
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
    
    private void setChart(){
        series1.setName("1");
        series2.setName("2");
        series3.setName("3");
        series4.setName("4");
        series5.setName("5");
        
        
        
        lineChart.getData().add(series1);
        lineChart.getData().add(series2);
        lineChart.getData().add(series3);
        lineChart.getData().add(series4);
        lineChart.getData().add(series5);
       
        
    }
    
    private void populateChart(){
        
        series1.getData().clear();
        series2.getData().clear();
        series3.getData().clear();
        series4.getData().clear();
        series5.getData().clear();        
        
        for(Registro reg : listaFiltrada){
            int resp1 = 0;
            int resp2 = 0;
            int resp3 = 0;
            int resp4 = 0;
            int resp5 = 0;
            try{
                resp1 = Integer.parseInt(reg.getUno());
                resp2 = Integer.parseInt(reg.getDos());
                resp3 = Integer.parseInt(reg.getTres());
                resp4 = Integer.parseInt(reg.getCuatro());
                resp5 = Integer.parseInt(reg.getCinco());
            }catch(Exception e){
                System.out.println("error al parse integer resp");
            }
            String preg = reg.getPregunta();
            if(preg.length() > 40) preg = preg.substring(0, 39);
            series1.getData().add(new XYChart.Data<>(preg, resp1));
            series2.getData().add(new XYChart.Data<>(preg, resp2));
            series3.getData().add(new XYChart.Data<>(preg, resp3));
            series4.getData().add(new XYChart.Data<>(preg, resp4));
            series5.getData().add(new XYChart.Data<>(preg, resp5));
        }
        
        lineChart.setAnimated(false);
        
    }
    
    private String extractMatricula(String catedratico){
        int i = catedratico.indexOf("(");
        int j = catedratico.indexOf(")");
        try{
            return catedratico.substring(i+1, j);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    private void assignImage(String matricula){
        if(matricula==null){
            imageView.setImage(null);
        }else{
            String ruta = SurveyViewer.getRuta() + "\\" + matricula + ".";
            String[] tipoArchivo = {"png", "jpeg", "bmp", "jpg"};
            int j = 0;
            for (int i = 1; i <= tipoArchivo.length; i++) {
                String r = ruta + tipoArchivo[i-1];
                System.out.println(r);
                if(new File(r).exists()){
                    j = i;
                }
            }
            System.out.println(j);
            if(j!=0){
                String r = ruta + tipoArchivo[j-1];
                imageView.setImage(new Image("file:" + r));
            }else{
                imageView.setImage(null);
            }
        }
    }
    
    @FXML
    private void handleButtonImprimir(ActionEvent event) throws COSVisitorException {
        WritableImage image = botonImprimir.getScene().snapshot(null);
        File fileImage = new File("chart.jpg");
        if (fileImage != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", fileImage);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        Stage stage = (Stage) botonImprimir.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDf Document", "*.pdf"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                
                // Create a new empty document
                PDDocument document = new PDDocument();

                // Create a new blank page and add it to the document
                PDPage page = new PDPage();
                document.addPage( page );

                
                
                InputStream in = new FileInputStream(fileImage);
                
                if(fileImage == null) System.out.println("image nula");
                
                PDJpeg img = new PDJpeg(document, in);
                
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.drawImage(img, 100, 700);
                contentStream.close();
                
                // Save the newly created document
                document.save(file.getAbsolutePath());

                // finally make sure that the document is properly
                // closed.
                document.close();
                
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        if(fileImage.delete()) System.out.println("delted");
        else System.out.println("error dleting");
        
    }
    
    @FXML
    private void handleButtonImprimir2(ActionEvent event) throws COSVisitorException {
        WritableImage image = botonImprimir.getScene().snapshot(null);
        File fileImage = new File("chart.png");
        if (fileImage != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", fileImage);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
    }
    
    @FXML
    private void handleButtonImprimir3(ActionEvent event) throws COSVisitorException {
//        WritableImage image = botonImprimir.getScene().snapshot(null);
        WritableImage image = botonImprimir.getScene().snapshot(null);
        BufferedImage tempImg = null;
        tempImg = SwingFXUtils.fromFXImage(image, null);
     
        BufferedImage img1 = null;
        byte[] imageInByte;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(tempImg, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            InputStream in = new ByteArrayInputStream(imageInByte);
            img1 = ImageIO.read(in);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        BufferedImage img2 = Scalr.resize(img1, Scalr.Method.ULTRA_QUALITY, 800);
        BufferedImage img3 = Scalr.rotate(img2, Scalr.Rotation.CW_90);
        
        Stage stage = (Stage) botonImprimir.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDf Document", "*.pdf"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                
                // Create a new empty document
                PDDocument document = new PDDocument();

                // Create a new blank page and add it to the document
                PDPage page = new PDPage();
                document.addPage( page );

                
                
                
                PDJpeg img = new PDJpeg(document, img3, 1.0f);
                
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.drawImage(img, 130, 0);
                contentStream.close();
                
                // Save the newly created document
                document.save(file.getAbsolutePath());

                // finally make sure that the document is properly
                // closed.
                document.close();
                
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        
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
