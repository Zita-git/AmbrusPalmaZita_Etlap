package com.example.etlap.controllerek;

import com.example.etlap.Etlap;
import com.example.etlap.EtlapDb;
import com.example.etlap.HelloController;
import com.example.etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class MainController extends HelloController {

    @FXML
    public Spinner<Integer> ftSpinner,szSpinner;
    @FXML
    public TableView<Etlap> etlapTableView;
    @FXML
    public TableColumn<Etlap, String> nevCol,ketgoraCol;
    @FXML
    public TableColumn<Etlap, Integer> arCol;
    @FXML
    public TextArea textAreaEtlap;
    @FXML
    public ChoiceBox szuresChoiceBox;
    @FXML
    public TableColumn<Kategoria, String> katgoriaKategoriaCol;
    @FXML
    public TableView<Kategoria> kategoriaTableView;

    private EtlapDb db;
    private List<Kategoria> kategoriaLista;

    @FXML
    public void onEmelesBTNft(ActionEvent actionEvent) {
        int emeles = 0;

        try {
            emeles = ftSpinner.getValue();
        } catch (NullPointerException e) {
            alert("Az emeléshez az ár megadása kötelező!");
            return;
        } catch (Exception e) {
            alert("Az ár 50 és 3000 közötti szám lehet");
            return;
        }
        if (emeles < 50 || emeles > 3000) {
            alert("Az ár 50 és 3000 közötti szám lehet");
            return;
        }

        int selectedIndex = etlapTableView.getSelectionModel().getSelectedIndex();

        Etlap emelesEtel = (Etlap) etlapTableView.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos szeretné emelni az összes étel árát?")) {
                return;
            }
            try {
                if (db.mindFt(emeles)) {
                    alertWait("Sikeres emelés");
                    etlapUjratoltese();
                } else {
                    alert("Sikertelen emelés");
                }
            } catch (SQLException e) {
                hibaKiir(e);
            }
        } else {
            if (!confirm("Biztos szeretné emelni a(z) " + emelesEtel.getNev() + " árát?")) {
                return;
            }
            try {
                if (db.egyFt(emelesEtel.getId(), emeles)) {
                    alertWait("Sikeres emelés");
                    etlapUjratoltese();
                    textAreaEtlap.setText("");
                } else {
                    alert("Sikertelen emelés");
                }

            } catch (SQLException e) {
                hibaKiir(e);
            }
        }
    }

    @FXML
    public void onEmelesBTNszazalek(ActionEvent actionEvent) {
        int emeles = 0;

        try {
            emeles = szSpinner.getValue();
        } catch (NullPointerException e) {
            alert("Az emeléshez a százalék megadása kötelező!");
            return;
        } catch (Exception e) {
            alert("Az ár 5 és 50 közötti szám lehet");
            return;
        }
        if (emeles < 5 || emeles > 50) {
            alert("Az ár 5 és 50 közötti szám lehet");
            return;
        }

        int selectedIndex = etlapTableView.getSelectionModel().getSelectedIndex();

        Etlap emelesEtel = (Etlap) etlapTableView.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos szeretné emelni az összes étel árát?")) {
                return;
            }
            try {
                if (db.mindSz(emeles)){
                    alertWait("Sikeres emelés");
                    etlapUjratoltese();
                } else {
                    alert("Sikertelen emelés");
                }
            } catch (SQLException e) {
                hibaKiir(e);
            }
        } else {
            if (!confirm("Biztos szeretné emelni a(z) " + emelesEtel.getNev() + " árát?")) {
                return;
            }
            try {
                if (db.egySz(emelesEtel.getId(), emeles)) {
                    alertWait("Sikeres emelés");
                    etlapUjratoltese();
                    textAreaEtlap.setText("");
                } else {
                    alert("Sikertelen emelés");
                }
            } catch (SQLException e) {
                hibaKiir(e);
            }
        }
    }

    @FXML
    public void onUjEtelFelveteleBTN(ActionEvent actionEvent) {
        try {
            HelloController hozzadas = ujAblak("hozzaad.fxml", "Étel hozzáadása", 277, 248);
            hozzadas.getStage().setOnCloseRequest(event -> etlapUjratoltese());
            hozzadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onTorlesEtlapBTN(ActionEvent actionEvent) {
        int selectedIndex = etlapTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez előbb válasszon ki egy ételt a táblázatból!");
            return;
        }
        Etlap torlendoEtel = etlapTableView.getSelectionModel().getSelectedItem();
        if (!confirm("Biztosan törölni szeretnéd ezt az ételt? " + torlendoEtel.getNev())) {
            return;
        }
        try {
            db.deleteEtel(torlendoEtel.getId());
            alert("Sikeres törlés");
            etlapUjratoltese();
            textAreaEtlap.setText("");
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onEtelClick(MouseEvent mouseEvent) {
        int selectedIndex = etlapTableView.getSelectionModel().getSelectedIndex();
        if (!(selectedIndex == -1)) {
            Etlap szoveg = etlapTableView.getSelectionModel().getSelectedItem();
            textAreaEtlap.setText(szoveg.getLeiras());
        }
    }

    @FXML
    public void onHozzadasBTN(ActionEvent actionEvent) {
        try {
            HelloController kategoriaHozzadas = ujAblak("kategoria.fxml", "Kategória hozzáadása", 310, 277);
            kategoriaHozzadas.getStage().setOnCloseRequest(event -> kategoriaClick());
            kategoriaHozzadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onTorlesKaegoriaBTN(ActionEvent actionEvent) {
        int selectedIndex = kategoriaTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("Válasszon ki egy kategóriát!");
            return;
        }
        Kategoria torlendoKategoria = kategoriaTableView.getSelectionModel().getSelectedItem();
        if (!confirm("Biztosan törölni szeretnéd ezt a kategóriá? " + torlendoKategoria.getNev())) {
            return;
        }
        try {
            db.delKategoria(torlendoKategoria.getId());
            alert("Sikeres törlés");
            kategoriaClick();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }


    public void setSzuresChoiceBox() {
        szuresChoiceBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            try {
                if (newValue.equals("Összes")) {
                    etlapUjratoltese();
                } else {
                    List<Etlap> szurtEtlapLista = db.getSzurtEtlap((String) newValue);
                    etlapTableView.getItems().clear();
                    for (Etlap etlap : szurtEtlapLista) {
                        etlapTableView.getItems().add(etlap);
                    }
                }
            } catch (SQLException e) {
                hibaKiir(e);
            }
        });
    }

    private void etlapUjratoltese() {
        try {
            List<Etlap> etlapLista = db.getEtlap();
            etlapTableView.getItems().clear();
            for (Etlap etlap : etlapLista) {
                etlapTableView.getItems().add(etlap);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }


    private void kategoriaClick() {
        try {
            List<Kategoria> list = db.getKategoria();
            kategoriaTableView.getItems().clear();
            for (Kategoria kategoria : list) {
                kategoriaTableView.getItems().add(kategoria);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void initialize(){
        szuresChoiceBox.getItems().add("Összes");
        try {
            db = new EtlapDb();
            kategoriaLista = db.getKategoria();
            for (Kategoria kategoria : kategoriaLista) {
                szuresChoiceBox.getItems().add(kategoria.getNev());
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }

        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        ketgoraCol.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        arCol.setCellValueFactory(new PropertyValueFactory<>("ar"));

        katgoriaKategoriaCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        try {
            db = new EtlapDb();
            etlapUjratoltese();
            kategoriaClick();
        } catch (SQLException e) {
            hibaKiir(e);
        }

        setSzuresChoiceBox();
    }

}
