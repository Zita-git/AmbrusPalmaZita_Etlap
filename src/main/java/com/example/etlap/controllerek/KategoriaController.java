package com.example.etlap.controllerek;

import com.example.etlap.EtlapDb;
import com.example.etlap.HelloController;
import com.example.etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class KategoriaController extends HelloController {

    @FXML
    public TextField nevKategoriaFxmlTextField;
    private EtlapDb db;
    private List<Kategoria> kategoriaList;

    public void onHozzaadasKategoriaFxmlBTN(ActionEvent actionEvent) {
        try {
            db = new EtlapDb();
        } catch (SQLException e) {
            hibaKiir(e);
            return;
        }

        String nev = nevKategoriaFxmlTextField.getText();
        try {
            kategoriaList = db.getKategoria();
        } catch (SQLException e) {
            hibaKiir(e);
        }
        boolean tartalmazza = false;
        for (Kategoria kategoria : kategoriaList)
        {
            if(kategoria.getNev().toLowerCase().equals(nev.toLowerCase())){
                tartalmazza = true;
                break;
            }
        }
        if(tartalmazza){
            alert("A kategória már szerepel!");
            return;
        }
        try {

            int siker = db.addKategoria(nev);
            if (siker == 1){
                alert("A kategoria hozzáadása sikeres");
            } else {
                alert("Az kategoria hozzáadása sikeretelen");
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
}
