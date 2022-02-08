package com.example.etlap.controllerek;

import com.example.etlap.HelloController;
import com.example.etlap.EtlapDb;
import com.example.etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HozzaadController extends HelloController {

    @FXML
    private TextArea leirasTextArea;
    @FXML
    private TextField nevTextField;
    @FXML
    private ComboBox kategoriaComb;
    @FXML
    private Spinner arSpinner;

    private EtlapDb db;
    private List<Kategoria> kategoriaList;

    public void onHozzaadasFxmlBTNClick(ActionEvent actionEvent){
        String leiras = leirasTextArea.getText().trim();
        if (leiras.isEmpty()){
            alert("A leírás megadása kötelező!");
            return;
        }

        String nev = nevTextField.getText().trim();
        if (nev.isEmpty()){
            alert("A név megadása kötelező!");
            return;
        }


        int ar = 0;
        try {
            ar = (int) arSpinner.getValue();
        } catch (NullPointerException ex){
            alert("Az ár megadása kötelező!");
            return;
        } catch (Exception ex){
            System.out.println(ex);
            alert("Az ár csak 1 és 50000 közötti szám lehet!");
            return;
        }
        if (ar < 1 || ar > 50000) {
            alert("Az ár csak 1 és 50000 közötti szám lehet!");
            return;
        }

        int kategoriaIndex = kategoriaComb.getSelectionModel().getSelectedIndex();
        if (kategoriaIndex == -1){
            alert("A kategória kiválasztása kötelező!");
            return;
        }
        String kategoriaString = (String) kategoriaComb.getSelectionModel().getSelectedItem();
        int kategoriaInt = -1;
        try {
            EtlapDb db = new EtlapDb();
            for (Kategoria kategoria : kategoriaList){
                if(kategoria.getNev().equals(kategoriaString)){
                    kategoriaInt = kategoria.getId();
                    break;
                }
            }
            int siker = db.addEtel(nev,leiras, kategoriaInt,ar);
            if (siker == 1){
                alert("Az étel hozzáadása sikeres");
            } else {
                alert("Az étel hozzáadása sikeretelen");
            }
        } catch (Exception e) {
            hibaKiir(e);
        }
    }

    public void initialize(){
        kategoriaList = new ArrayList<>();
        try {
            db = new EtlapDb();
        } catch (SQLException e) {
            hibaKiir(e);
        }
        try {
            kategoriaList = db.getKategoria();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Kategoria kategoria : kategoriaList){
            kategoriaComb.getItems().add(kategoria.getNev());
        }
        kategoriaComb.getSelectionModel().selectFirst();
    }
}
