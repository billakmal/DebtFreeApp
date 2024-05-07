package org.bilal.debtfree.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bilal.debtfree.DAO.UsersDAO;
import org.bilal.debtfree.Model.Users;
import org.bilal.debtfree.Utility.JDBCConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignupController {

    @FXML
    public TextField tf_AddUsername;

    @FXML
    public TextField tf_AddEmail;

    @FXML
    public TextField tf_AddPassword;

    @FXML
    public Button bt_signup;

    @FXML
    public Button bt_login;

    public void SignupAction(ActionEvent actionEvent) {
        String username = tf_AddUsername.getText();
        String email = tf_AddEmail.getText();
        String password = tf_AddPassword.getText();

        Users newUser = new Users(username, email, password);
        UsersDAO usersDAO = new UsersDAO();

        boolean success = usersDAO.addData(newUser);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Sign Up Successful", "User registered successfully!");
            LoginAndNavigateToDashboard(newUser);
        } else {
            showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Failed to register user. Please try again.");
        }
    }

    public void LoginAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/bilal/debtfree/Controller/login-view.fxml"));

            Parent root = fxmlLoader.load();

            Stage stage = (Stage) bt_login.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Failed to execute SQL query!", e);
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to the database!");
        }
    }

    private void LoginAndNavigateToDashboard(Users user) {
        try (Connection conn = JDBCConnection.getConnection()) {

            String query = "SELECT * FROM users WHERE (username = ? OR email = ?) AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int user_id = rs.getInt("user_id");
                String databaseUsername = rs.getString("username");
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + databaseUsername +"!");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
                Parent root = fxmlLoader.load();
                DashboardController dashboardController = fxmlLoader.getController();
                dashboardController.setUser_id(user_id);
                Scene scene = new Scene(root);
                Stage stage = (Stage) bt_signup.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Peringatan");
                alert.setHeaderText(null);
                alert.setContentText("Silakan pilih baris yang ingin diperbarui.");
                alert.showAndWait();
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

    private void showAlert(Alert.AlertType Type, String databaseError, String message) {
        Alert alert = new Alert(Type);
        alert.setTitle(databaseError);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
