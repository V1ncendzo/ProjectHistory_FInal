package application.controller;

import application.App;
import history.entity.Dynasty;
import history.collection.Dynasties;
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

public class EraScreenController {

    private Dynasties dynasties = new Dynasties();

    @FXML
    private TableView<Dynasty> eraTable;

    @FXML
    private TableColumn<Dynasty, Integer> colEraId;

    @FXML
    private TableColumn<Dynasty, String> colEraName;

    @FXML
    private SearchBarController searchBarController;

    @FXML
    public void initialize() {

        try {
            dynasties.loadDataJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        colEraId.setCellValueFactory(
                new PropertyValueFactory<Dynasty, Integer>("id"));
        colEraName.setCellValueFactory(
                new PropertyValueFactory<Dynasty, String>("name"));
        eraTable.setItems(dynasties.getData());

        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        eraTable.setItems(dynasties.searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            eraTable.setItems(
                                    FXCollections.singletonObservableList(dynasties.get(intId)));
                        } catch (Exception e) {
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        eraTable.setItems(dynasties.getData());
                    }
                });

        // Tao listener khi click vao trieu dai trong table
        eraTable.setRowFactory(tableView -> {
            TableRow<Dynasty> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Dynasty era = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                App.convertToURL("/main/java/application/view/EraDetailScreen.fxml"));
                        Parent root = loader.load();
                        EraDetailScreenController controller = loader.getController();
                        controller.setEra(era);
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
