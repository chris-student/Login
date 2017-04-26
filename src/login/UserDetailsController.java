package login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;


public class UserDetailsController implements Initializable {
    private User user;

    @FXML private GridPane gp;
    @FXML private Label nameLbl;
    @FXML private Label passLbl;

    public UserDetailsController(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameLbl.setText(user.getName());
        passLbl.setText(user.getPass());
    }
}
