package org.bilal.debtfree.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bilal.debtfree.DAO.PendapatanDAO;
import org.bilal.debtfree.Model.Pendapatan;
import org.bilal.debtfree.Utility.Identity;

import java.util.List;

public class PendapatanController {

    private int user_id;
    private PendapatanDAO pendapatanDAO;

    public PendapatanController() {}

    public PendapatanController(int user_id) {
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
    public TextField tf_pendapatan;
    @FXML
    public TextField tf_sumber;
    @FXML
    private TableView<Pendapatan> tableView;
    @FXML
    public TableColumn<Pendapatan, Integer> idColumn;
    @FXML
    private TableColumn<Pendapatan, Integer> nominalColumn;
    @FXML
    private TableColumn<Pendapatan, String> sumberColumn;
    @FXML
    private TableColumn<Pendapatan, String> waktuColumn;

    public void initialize() {
        this.user_id = Identity.getInstance().getUserId();
        pendapatanDAO = new PendapatanDAO();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("pendapatan_id"));
        nominalColumn.setCellValueFactory(new PropertyValueFactory<>("jumlah_pendapatan"));
        sumberColumn.setCellValueFactory(new PropertyValueFactory<>("sumber_pendapatan"));
        waktuColumn.setCellValueFactory(new PropertyValueFactory<>("pendapatan_date"));

        loadDataIntoTableView();
    }

    private void loadDataIntoTableView() {
        List<Pendapatan> pendapatanList = pendapatanDAO.showData(user_id);

        tableView.getItems().addAll(pendapatanList);
    }

    public void AddAction(ActionEvent actionEvent) {
        int user_id = Identity.getInstance().getUserId();
        int jumlah_pendapatan = Integer.parseInt(tf_pendapatan.getText());
        String sumber_pendapatan = tf_sumber.getText();

        Pendapatan newPendapatan = new Pendapatan(user_id, jumlah_pendapatan, sumber_pendapatan, null);
        PendapatanDAO pendapatanDAO = new PendapatanDAO();
        System.out.println(newPendapatan);

        boolean success = pendapatanDAO.addData(newPendapatan);
        refreshTableView();

        if (success) {
            System.out.println("Data berhasil ditambahkan ke database.");
        } else {
            System.out.println("Gagal menambahkan data ke database.");
        }
    }

    public void DeleteAction(ActionEvent actionEvent) {
        Pendapatan selectedPendapatan;
        selectedPendapatan = (Pendapatan) tableView.getSelectionModel().getSelectedItem();

        if (selectedPendapatan != null) {
            tableView.getItems().remove(selectedPendapatan);

            int success = pendapatanDAO.deleteData(selectedPendapatan);

            if (success != 0) {
                System.out.println("Data berhasil dihapus dari database.");
            } else {
                System.out.println("Gagal menghapus data dari database.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "PERINGATAN", "Silahkan pilih baris yang ingin dihapus");
        }
    }

    public void UpdateAction(ActionEvent actionEvent) {
        Pendapatan selectedPendapatan = tableView.getSelectionModel().getSelectedItem();

        if (selectedPendapatan != null) {
            int user_id = selectedPendapatan.getUser_id();
            int pendapatan_id = selectedPendapatan.getPendapatan_id();
            int jumlah_pendapatan = Integer.parseInt(tf_pendapatan.getText());
            String sumber_pendapatan = tf_sumber.getText();

            Pendapatan updatedPendapatan = new Pendapatan(pendapatan_id, user_id, jumlah_pendapatan, sumber_pendapatan, null);
            int success = pendapatanDAO.updateData(updatedPendapatan);

            if (success != 0) {
                System.out.println("Data berhasil diperbarui di database.");
                selectedPendapatan.setJumlah_pendapatan(jumlah_pendapatan);
                selectedPendapatan.setSumber_pendapatan(sumber_pendapatan);
                tableView.refresh();
            } else {
                System.out.println("Gagal memperbarui data di database.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "PERINGATAN", "Silahkan pilih baris yang ingin diupdate.");
        }
    }

    public void BackAction(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    private void refreshTableView() {
        tableView.getItems().clear();
        List<Pendapatan> pendapatanList = pendapatanDAO.showData(user_id);
        tableView.getItems().addAll(pendapatanList);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
