package org.bilal.debtfree.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bilal.debtfree.Utility.JDBCConnection;
import org.bilal.debtfree.Utility.Identity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    private int user_id;

    @FXML
    private TextField tf_login;

    @FXML
    private PasswordField pf_password;

    @FXML
    private Button bt_signup;

    @FXML
    private Button bt_login;

    @FXML
    void SignupAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/bilal/debtfree/Controller/signup-view.fxml"));

            Parent root = fxmlLoader.load();

            Stage stage = (Stage) bt_signup.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load signup-view.fxml");
        }
    }

    @FXML
    void LoginAction() {
        String login = tf_login.getText();
        String password = pf_password.getText();

        try (Connection conn = JDBCConnection.getConnection()) {

            String query = "SELECT * FROM users WHERE (username = ? OR email = ?) AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, login);
            statement.setString(3, password);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int databaseUser_id = rs.getInt("user_id");
                String databaseUsername = rs.getString("username");
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + databaseUsername +"!");
                Identity.getInstance().setUserId(databaseUser_id);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
                Parent root = fxmlLoader.load();
                DashboardController dashboardController = fxmlLoader.getController();
                dashboardController.setUser_id(databaseUser_id);

                Scene scene = new Scene(root);
                Stage stage = (Stage) bt_login.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username/email or password!");
            }

            statement.close();
            rs.close();
        } catch (SQLException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Failed to execute SQL query!", e);
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to the database!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}