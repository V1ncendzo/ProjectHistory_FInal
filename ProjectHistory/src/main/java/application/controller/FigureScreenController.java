package application.controller;

import application.App;
import history.entity.Character;
import history.collection.Characters;
// import history.relation.Pair;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class FigureScreenController {

    private Characters characters = new Characters();

    @FXML
    private TableView<Character> tblFigure;

    @FXML
    private TableColumn<Character, Integer> colFigureId;

    @FXML
    private TableColumn<Character, String> colFigureName;

    @FXML
    private SearchBarController searchBarController;

    @FXML
    public void initialize() {

        try {
            characters.loadDataJson();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        colFigureId.setCellValueFactory(
                new PropertyValueFactory<Character, Integer>("id"));
        colFigureName.setCellValueFactory(
                new PropertyValueFactory<Character, String>("name"));
        tblFigure.setItems(characters.getData());

        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        tblFigure.setItems(characters.searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            tblFigure.setItems(
                                    FXCollections.singletonObservableList(characters.get(intId)));
                        } catch (Exception e) {
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        tblFigure.setItems(characters.getData());
                    }
                });

        tblFigure.setRowFactory(tableView -> {
            TableRow<Character> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Character figure = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                App.convertToURL("/main/java/application/view/FigureDetailScreen.fxml"));
                        Parent root = loader.load();
                        FigureDetailScreenController controller = loader.getController();
                        controller.setFigure(figure);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
}