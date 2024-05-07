package org.bilal.debtfree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bilal.debtfree.Utility.JDBCConnection;

import java.io.IOException;
import java.sql.Connection;

public class DebtFreeApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Connection conn = JDBCConnection.getConnection();

        if (conn != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/bilal/debtfree/controller/login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 520, 440);

            stage.setTitle("Debt Free App");
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Gagal terhubung ke database.");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}