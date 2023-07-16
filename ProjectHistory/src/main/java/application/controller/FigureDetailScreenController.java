package application.controller;

import application.App;
import history.entity.Dynasty;
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

public class FigureDetailScreenController {
    @FXML
    private Text nameText;

    @FXML
    private Text timeText;

    @FXML
    private Text otherNameText;

    @FXML
    private Text sinhText;

    @FXML
    private Text matText;

    @FXML
    private Text overviewText;

    @FXML
    private Text jobText;

    @FXML
    private SidebarController sideBarController;

    private Character figure;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/main/java/application/view/HistoricalFiguresScreen.fxml", event);
    }

    public void setFigure(Character figure) {
        this.figure = figure;
        nameText.setText(figure.getName());
        sinhText.setText(figure.getSinh());
        matText.setText(figure.getMat());
        overviewText.setText(figure.getDescription());
        jobText.setText(figure.getNgheNghiep());
        otherNameText.setText(figure.getotherName());
        timeText.setText(figure.getTime());
    }
}
