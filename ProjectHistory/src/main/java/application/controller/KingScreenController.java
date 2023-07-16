package application.controller;

import application.App;
import history.entity.King;
import history.collection.Kings;
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

public class KingScreenController {

    private Kings kings = new Kings();

    @FXML
    private TableView<King> kingTable;

    @FXML
    private TableColumn<King, Integer> colKingId;

    @FXML
    private TableColumn<King, String> colKingName;

    @FXML
    private SearchBarController searchBarController;

    @FXML
    public void initialize() {

        try {
            kings.loadDataJson();
        } catch (IOException e) {
            e.printStackTrace();
        }

        colKingId.setCellValueFactory(
                new PropertyValueFactory<King, Integer>("id"));
        colKingName.setCellValueFactory(
                new PropertyValueFactory<King, String>("name"));
        kingTable.setItems(kings.getData());

        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        kingTable.setItems(kings.searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            kingTable.setItems(
                                    FXCollections.singletonObservableList(kings.get(intId)));
                        } catch (Exception e) {
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        kingTable.setItems(kings.getData());
                    }
                });

        kingTable.setRowFactory(tableView -> {
            TableRow<King> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    King king = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                App.convertToURL("/main/java/application/view/KingDetailScreen.fxml"));
                        Parent root = loader.load();
                        KingDetailScreenController controller = loader.getController();
                        controller.setKing(king);
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
