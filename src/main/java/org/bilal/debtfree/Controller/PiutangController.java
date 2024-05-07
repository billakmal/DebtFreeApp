package org.bilal.debtfree.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bilal.debtfree.DAO.PendapatanDAO;
import org.bilal.debtfree.DAO.PiutangDAO;
import org.bilal.debtfree.Model.Pendapatan;
import org.bilal.debtfree.Model.Piutang;
import org.bilal.debtfree.Utility.Identity;

import java.util.List;

public class PiutangController {

    private int user_id;
    private PiutangDAO piutangDAO;

    public PiutangController() {}

    public PiutangController(int user_id) {
        this.user_id = user_id;
    }

    @FXML
    public Button bt_add;
    @FXML
    public Button bt_del;
    @FXML
    public Button bt_update;
    @FXML
    public Button bt_back;
    @FXML
    public TextField tf_piutang;
    @FXML
    public TextField tf_penerima;
    @FXML
    public TableView<Piutang> tableView;
    @FXML
    public TableColumn<Piutang, Integer> idColumn;
    @FXML
    public TableColumn<Piutang, Integer> nominalColumn;
    @FXML
    public TableColumn<Piutang, String> penerimaColumn;
    @FXML
    public TableColumn<Piutang, String> waktuColumn;

    public void initialize() {
        this.user_id = Identity.getInstance().getUserId();
        piutangDAO = new PiutangDAO();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("piutang_id"));
        nominalColumn.setCellValueFactory(new PropertyValueFactory<>("jumlah_piutang"));
        penerimaColumn.setCellValueFactory(new PropertyValueFactory<>("penerima_piutang"));
        waktuColumn.setCellValueFactory(new PropertyValueFactory<>("piutang_date"));

        loadDataIntoTableView();
    }

    private void loadDataIntoTableView() {
        List<Piutang> piutangList = piutangDAO.showData(user_id);

        tableView.getItems().addAll(piutangList);
    }

    public void AddAction(ActionEvent actionEvent) {
        int user_id = Identity.getInstance().getUserId();
        int jumlah_piutang = Integer.parseInt(tf_piutang.getText());
        String penerima_piutang = tf_penerima.getText();

        Piutang newPiutang = new Piutang(user_id, jumlah_piutang, penerima_piutang, null);
        PiutangDAO piutangDAO = new PiutangDAO();
        System.out.println(newPiutang);

        boolean success = piutangDAO.addData(newPiutang);
        refreshTableView();

        if (success) {
            System.out.println("Data berhasil ditambahkan ke database.");
        } else {
            System.out.println("Gagal menambahkan data ke database.");
        }
    }

    public void DeleteAction(ActionEvent actionEvent) {
        Piutang selectedPiutang;
        selectedPiutang = (Piutang) tableView.getSelectionModel().getSelectedItem();

        if (selectedPiutang != null) {
            tableView.getItems().remove(selectedPiutang);

            int success = piutangDAO.deleteData(selectedPiutang);

            if (success != 0) {
                System.out.println("Data berhasil dihapus dari database.");
            } else {
                System.out.println("Gagal menghapus data dari database.");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText(null);
            alert.setContentText("Silakan pilih baris yang ingin dihapus.");
            alert.showAndWait();
        }
    }

    public void UpdateAction(ActionEvent actionEvent) {
    }

    public void BackAction(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    private void refreshTableView() {
        tableView.getItems().clear();
        List<Piutang> piutangList = piutangDAO.showData(user_id);
        tableView.getItems().addAll(piutangList);
    }
}
