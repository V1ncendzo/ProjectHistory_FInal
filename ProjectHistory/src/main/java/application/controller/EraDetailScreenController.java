package application.controller;

import application.App;
import history.entity.Dynasty;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow;

public class EraDetailScreenController {

    @FXML
    public FlowPane kingsFlowPane;

    @FXML
    private Text nameText;

    @FXML
    private Text kingdomText;

    @FXML
    private Text capLocateText;

    @FXML
    private Text overviewText;

    @FXML
    private SidebarController sideBarController;

    private Dynasty dynasty;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/main/java/application/view/EraScreen.fxml", event);
    }

    public void setEra(Dynasty dynasty) {

        Characters characters = new Characters();

        try {
            characters.loadDataJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dynasty = dynasty;
        nameText.setText(dynasty.getName());
        kingdomText.setText(dynasty.getKingdom());
        capLocateText.setText(dynasty.getCapital());
        overviewText.setText(dynasty.getDescription());
        List<Integer> relateList = dynasty.getRelate();
        if (relateList != null) {
            for (Integer id : relateList) {
                Character figure = characters.get(id);
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
                    kingsFlowPane.getChildren().add(figureText);
                }
            }
        } else {
            kingsFlowPane.getChildren().add(new Text("Không rõ"));
        }
    }
}
