package org.bilal.debtfree.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bilal.debtfree.DAO.PendapatanDAO;
import org.bilal.debtfree.DAO.PengeluaranDAO;
import org.bilal.debtfree.Model.Pendapatan;
import org.bilal.debtfree.Model.Pengeluaran;
import org.bilal.debtfree.Utility.Identity;

import java.util.List;

public class PengeluaranController {

    private int user_id;
    private PengeluaranDAO pengeluaranDAO;

    public PengeluaranController() {}

    public PengeluaranController(int user_id) {
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
    public TextField tf_pengeluaran;
    @FXML
    public TextField tf_keterangan;
    @FXML
    public TableView<Pengeluaran> tableView;
    @FXML
    public TableColumn<Pengeluaran, Integer> idColumn;
    @FXML
    private TableColumn<Pengeluaran, Integer> nominalColumn;
    @FXML
    private TableColumn<Pengeluaran, String> keteranganColumn;
    @FXML
    private TableColumn<Pengeluaran, String> waktuColumn;

    public void initialize() {
        this.user_id = Identity.getInstance().getUserId();
        pengeluaranDAO = new PengeluaranDAO();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("pengeluaran_id"));
        nominalColumn.setCellValueFactory(new PropertyValueFactory<>("jumlah_pengeluaran"));
        keteranganColumn.setCellValueFactory(new PropertyValueFactory<>("keterangan_pengeluaran"));
        waktuColumn.setCellValueFactory(new PropertyValueFactory<>("pengeluaran_date"));

        loadDataIntoTableView();
    }

    private void loadDataIntoTableView() {
        List<Pengeluaran> pengeluaranList = pengeluaranDAO.showData(user_id);

        tableView.getItems().addAll(pengeluaranList);
    }

    public void AddAction(ActionEvent actionEvent) {
        int user_id = Identity.getInstance().getUserId();
        int jumlah_pengeluaran= Integer.parseInt(tf_pengeluaran.getText());
        String keterangan_pengeluaran = tf_keterangan.getText();

        Pengeluaran newPengeluaran = new Pengeluaran(user_id, jumlah_pengeluaran, keterangan_pengeluaran, null);
        PengeluaranDAO pengeluaranDAO = new PengeluaranDAO();
        System.out.println(newPengeluaran);

        boolean success = pengeluaranDAO.addData(newPengeluaran);
        refreshTableView();

        if (success) {
            System.out.println("Data berhasil ditambahkan ke database.");
        } else {
            System.out.println("Gagal menambahkan data ke database.");
        }
    }

    public void DeleteAction(ActionEvent actionEvent) {
        Pengeluaran selectedPengeluaran;
        selectedPengeluaran = (Pengeluaran) tableView.getSelectionModel().getSelectedItem();

        if (selectedPengeluaran != null) {
            tableView.getItems().remove(selectedPengeluaran);

            int success = pengeluaranDAO.deleteData(selectedPengeluaran);

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
        Pengeluaran selectedPengeluaran = (Pengeluaran) tableView.getSelectionModel().getSelectedItem();

        if (selectedPengeluaran != null) {
            int user_id = selectedPengeluaran.getUser_id();
            int pengeluaran_id = selectedPengeluaran.getPengeluaran_id();
            int jumlah_pengeluaran= Integer.parseInt(tf_pengeluaran.getText());
            String keterangan_pengeluaran = tf_keterangan.getText();

            Pengeluaran updatePengeluaran = new Pengeluaran(pengeluaran_id, user_id, jumlah_pengeluaran, keterangan_pengeluaran, null);
            int success = pengeluaranDAO.updateData(updatePengeluaran);

            if (success != 0) {
                System.out.println("Data berhasil diperbarui di database.");
                selectedPengeluaran.setJumlah_pengeluaran(jumlah_pengeluaran);
                selectedPengeluaran.setKeterangan_pengeluaran(keterangan_pengeluaran);
                tableView.refresh();
            } else {
                System.out.println("Gagal memperbarui data di database.");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText(null);
            alert.setContentText("Silakan pilih baris yang ingin diperbarui.");
            alert.showAndWait();
        }
    }

    public void BackAction(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    private void refreshTableView() {
        tableView.getItems().clear();
        List<Pengeluaran> pengeluaranList = pengeluaranDAO.showData(user_id);
        tableView.getItems().addAll(pengeluaranList);
    }
}
