package org.bilal.debtfree.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bilal.debtfree.DAO.HutangDAO;
import org.bilal.debtfree.DAO.PendapatanDAO;
import org.bilal.debtfree.Model.Hutang;
import org.bilal.debtfree.Model.Pendapatan;
import org.bilal.debtfree.Utility.Identity;

import java.util.List;

public class HutangController {

    private int user_id;
    private HutangDAO hutangDAO;

    public HutangController() {}

    public HutangController(int user_id) {
        this.user_id = user_id;
    }

    @FXML
    public Button bt_add;
    @FXML
    public Button bt_update;
    @FXML
    public Button bt_del;
    @FXML
    public Button bt_back;
    @FXML
    public TextField tf_hutang;
    @FXML
    public TextField tf_pemberi;
    @FXML
    public TableView<Hutang> tableView;
    @FXML
    public TableColumn<Hutang, Integer> idColumn;
    @FXML
    public TableColumn<Hutang, Integer> nominalColumn;
    @FXML
    public TableColumn<Hutang, String> pemberiColumn;
    @FXML
    public TableColumn<Hutang, String> waktuColumn;

    public void initialize() {
        this.user_id = Identity.getInstance().getUserId();
        hutangDAO = new HutangDAO();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("hutang_id"));
        nominalColumn.setCellValueFactory(new PropertyValueFactory<>("jumlah_hutang"));
        pemberiColumn.setCellValueFactory(new PropertyValueFactory<>("pemberi_hutang"));
        waktuColumn.setCellValueFactory(new PropertyValueFactory<>("hutang_date"));

        loadDataIntoTableView();
    }

    private void loadDataIntoTableView() {
        List<Hutang> hutangList = hutangDAO.showData(user_id);

        tableView.getItems().addAll(hutangList);
    }

    public void AddAction(ActionEvent actionEvent) {
        int user_id = Identity.getInstance().getUserId();
        int jumlah_hutang = Integer.parseInt(tf_hutang.getText());
        String pemberi_hutang = tf_pemberi.getText();

        Hutang newHutang = new Hutang(user_id, jumlah_hutang, pemberi_hutang, null);
        HutangDAO hutangDAO = new HutangDAO();
        System.out.println(newHutang);

        boolean success = hutangDAO.addData(newHutang);
        refreshTableView();

        if (success) {
            System.out.println("Data berhasil ditambahkan ke database.");
        } else {
            System.out.println("Gagal menambahkan data ke database.");
        }
    }

    public void DeleteAction(ActionEvent actionEvent) {
        Hutang selectedHutang;
        selectedHutang = (Hutang) tableView.getSelectionModel().getSelectedItem();

        if (selectedHutang != null) {
            tableView.getItems().remove(selectedHutang);

            int success = hutangDAO.deleteData(selectedHutang);

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
        Hutang selectedHutang = (Hutang) tableView.getSelectionModel().getSelectedItem();

        if (selectedHutang != null) {
            int user_id = selectedHutang.getUser_id();
            int hutang_id = selectedHutang.getHutang_id();
            int jumlah_hutang = Integer.parseInt(tf_hutang.getText());
            String pemberi_hutang = tf_pemberi.getText();

            Hutang updatedhutang = new Hutang(hutang_id, user_id, jumlah_hutang, pemberi_hutang, null);
            int success = hutangDAO.updateData(updatedhutang);

            if (success != 0) {
                System.out.println("Data berhasil diperbarui di database.");
                selectedHutang.setJumlah_hutang(jumlah_hutang);
                selectedHutang.setPemberi_hutang(pemberi_hutang);
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
        List<Hutang> hutangList = hutangDAO.showData(user_id);
        tableView.getItems().addAll(hutangList);
    }
}
