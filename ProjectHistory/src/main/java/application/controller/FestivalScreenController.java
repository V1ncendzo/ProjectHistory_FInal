package application.controller;

import application.App;
import history.entity.Festival;
import history.collection.Events;
import history.collection.Festivals;
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

public class FestivalScreenController {

    private Festivals festivals = new Festivals();

    @FXML
    private TableView<Festival> fesTable;

    @FXML
    private TableColumn<Festivals, Integer> colFesId;

    @FXML
    private TableColumn<Festivals, String> colFesName;

    @FXML
    private SearchBarController searchBarController;

    @FXML
    public void initialize() {

        try {
            festivals.loadDataJson();
        } catch (IOException e) {
            e.printStackTrace();
        }

        colFesId.setCellValueFactory(
                new PropertyValueFactory<Festivals, Integer>("id"));
        colFesName.setCellValueFactory(
                new PropertyValueFactory<Festivals, String>("name"));

        fesTable.setItems(festivals.getData());

        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        fesTable.setItems(festivals.searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            fesTable.setItems(
                                    FXCollections.singletonObservableList(festivals.get(intId)));
                        } catch (Exception e) {
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        fesTable.setItems(festivals.getData());
                    }
                });

        fesTable.setRowFactory(tableView -> {
            TableRow<Festival> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Festival fes = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                App.convertToURL("/main/java/application/view/FesDetailScreen.fxml"));
                        Parent root = loader.load();
                        FesDetailScreenController controller = loader.getController();
                        controller.setFestival(fes);
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
