package application.controller;

import application.App;
import history.collection.Festivals;
import history.entity.Festival;
import history.entity.Character;
import history.collection.Characters;
import history.entity.Relic;
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

public class SiteDetailScreenController {
    @FXML
    private Text nameText;

    @FXML
    private Text locationText;

    @FXML
    private Text overviewText;

    @FXML
    private Text categoryText;

    @FXML
    private FlowPane relatedCharsFlowPane;

    @FXML
    private SidebarController sideBarController;

    private Relic site;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/main/java/application/view/HistoricSiteScreen.fxml", event);
    }

    public void setHistoricSite(Relic site) {

        Characters characters = new Characters();

        try {
            characters.loadDataJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.site = site;
        nameText.setText(site.getName());
        locationText.setText(site.getProvince());
        overviewText.setText(site.getDescription());
        categoryText.setText(site.getCertifacte());
        List<Integer> relateList = site.getRelate();
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
                    relatedCharsFlowPane.getChildren().add(figureText);
                }
            }
        } else {
            relatedCharsFlowPane.getChildren().add(new Text("Không rõ"));
        }
    }
}
