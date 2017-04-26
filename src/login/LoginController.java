package login;

import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

//import java.net.URL;
import java.sql.SQLException;
//import java.util.ResourceBundle;

public class LoginController {
    private String name;
    private String pass;

    @FXML private TextField userField;
    @FXML private TextField passField;
    @FXML private Button submitBtn;
    @FXML private Button addBtn;


//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//    }

    @FXML
    private void submit() {
        name = userField.getText();
        pass = passField.getText();

        User unverified = new User(name, pass);
        System.out.println(unverified);

        User verified = null;

        try {
            UserData data = new UserData();
            verified = data.retrieveUser(unverified.getName());
        }
        catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Unsuccessful!");
            alert.setHeaderText("Invalid credentials");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }

        System.out.println(verified);

        if (unverified.getPass().equals(verified.getPass())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("Successful Login");
            alert.setContentText(verified.toString());
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Unsuccessful!");
            alert.setHeaderText("Invalid credentials");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    private void add() {
        name = userField.getText();
        pass = passField.getText();

        User newUser = new User(name, pass);

        try {
            UserData data = new UserData();
            data.createUser(newUser);
        }
        catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Invalid credentials");
            alert.setContentText("Cannot add this user.");
            alert.showAndWait();
        }
    }
}
