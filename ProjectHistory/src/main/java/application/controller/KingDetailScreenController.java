package application.controller;

import application.App;
import history.entity.Dynasty;
import history.entity.King;
import history.collection.Dynasties;
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

public class KingDetailScreenController {
    @FXML
    private Text nameText;

    @FXML
    public Text triviText;

    @FXML
    private Text sinhText;

    @FXML
    private Text matText;

    @FXML
    private Text dynastyText;

    @FXML
    private Text overviewText;

    @FXML
    private FlowPane momText;

    @FXML
    private Text preText;

    @FXML
    private Text postText;

    @FXML
    private FlowPane dadText;

    @FXML
    private Text antangText;

    @FXML
    private SidebarController sideBarController;

    private King king;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/main/java/application/view/KingScreen.fxml", event);
    }

    public void setKing(King king) {

        Characters characters = new Characters();

        try {
            characters.loadDataJson();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.king = king;
        nameText.setText(king.getName());
        sinhText.setText(king.getSinh());
        matText.setText(king.getMat());
        overviewText.setText(king.getDescription());
        triviText.setText(king.getTriVi());
        dynastyText.setText(king.getTrieuDai());
        preText.setText(king.getTienNhiem());
        postText.setText(king.getKeNhiem());
        if (king.getThanPhu() != null) {
            Character figure = characters.get(king.getThanPhu());
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
                dadText.getChildren().add(figureText);
            } else {
                dadText.getChildren().add(new Text(king.getThanPhu()));
            }
        } else {
            dadText.getChildren().add(new Text("Không rõ"));
        }

        if (king.getThanMau() != null) {
            Character figure = characters.get(king.getThanMau());
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
                momText.getChildren().add(figureText);
            } else {
                momText.getChildren().add(new Text(king.getThanMau()));
            }
        } else {
            momText.getChildren().add(new Text("Không rõ"));
        }
    }
}
