package application.controller;

import application.App;
import history.entity.Festival;
import history.entity.Character;
import history.collection.Characters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class FesDetailScreenController {
    @FXML
    private Text nameText;

    @FXML
    private Text dateText;

    @FXML
    private Text locationText;

    @FXML
    private Text firstTimeText;

    @FXML
    private FlowPane charText;

    @FXML
    private SidebarController sideBarController;

    private Festival fes;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/main/java/application/view/FestivalScreen.fxml", event);
    }

    public void setFestival(Festival fes) {

        Characters characters = new Characters();

        try {
            characters.loadDataJson();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.fes = fes;
        nameText.setText(fes.getName());
        dateText.setText(fes.getTime());
        locationText.setText(fes.getPlace());
        firstTimeText.setText(fes.getFirstTime());
        if (fes.getCharacter() != null) {
            Character figure = characters.get(fes.getCharacter());
            if (figure != null && figure.getName() != null) {
                Text figureText = new Text(figure.getName());
                figureText.setFill(Color.web("#3498db"));
                figureText.setOnMouseClicked(mouseEvent -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                App.convertToURL("/main/java/application/view/FigureDetailScreen.fxml"));
                        Parent root = loader.load();
                        FigureDetailScreenController controller = loader.getController();
                        controller.setFigure(figure);
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                charText.getChildren().add(figureText);
            } else {
                charText.getChildren().add(new Text(fes.getCharacter()));
            }
        } else {
            charText.getChildren().add(new Text("Không rõ"));
        }
    }
}
