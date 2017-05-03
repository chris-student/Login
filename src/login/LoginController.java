package login;

import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//import java.net.URL;
import java.io.IOException;
import java.sql.SQLException;
//import java.util.ResourceBundle;

public class LoginController {
    private String name;
    private String pass;

    @FXML private GridPane grid;
    @FXML private TextField userField;
    @FXML private PasswordField passField;
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
        System.out.println("Unverified user:\n" + unverified);

        UserLogic logic = new UserLogic();
        User verified = logic.validateUser(unverified);

        System.out.println("Verified user:\n" + verified);

        if (verified != null) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Success!");
//            alert.setHeaderText("Successful Login");
//            alert.setContentText(verified.toString());
//            alert.showAndWait();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("list.fxml"));
                Stage primaryStage = (Stage) grid.getScene().getWindow();
                primaryStage.setTitle("User List");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
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
        UserLogic logic = new UserLogic();
        newUser = logic.hashPassword(newUser);

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
