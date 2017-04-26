package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListController implements Initializable {
    private UserData data = new UserData();

    @FXML private Pane p;
    @FXML private ListView<User> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<User> obsData = null;
        try {
            List<User> users = data.retrieveAllUsers();
            obsData = FXCollections.observableList(users);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        list.setItems(obsData);

        list.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    try {
                        User user = list.getSelectionModel().getSelectedItem();
                        UserDetailsController controller = new UserDetailsController(user);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_details.fxml"));
                        loader.setController(controller);
                        Stage primaryStage = (Stage) p.getScene().getWindow();
                        primaryStage.setTitle("User Details");
                        primaryStage.setScene(new Scene(loader.load()));
                        primaryStage.show();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
