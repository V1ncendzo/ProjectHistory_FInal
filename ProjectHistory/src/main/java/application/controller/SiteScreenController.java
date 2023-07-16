package application.controller;

import application.App;
import history.entity.Relic;
import history.collection.Relics;
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

public class SiteScreenController {

    private Relics relics = new Relics();

    @FXML
    private TableView<Relic> siteTable;

    @FXML
    private TableColumn<Relic, Integer> colSiteId;

    @FXML
    private TableColumn<Relic, String> colSiteName;

    @FXML
    private SearchBarController searchBarController;

    @FXML
    public void initialize() {

        try {
            relics.loadDataJson();
        } catch (IOException e) {
            e.printStackTrace();
        }

        colSiteId.setCellValueFactory(
                new PropertyValueFactory<Relic, Integer>("id"));
        colSiteName.setCellValueFactory(
                new PropertyValueFactory<Relic, String>("name"));
        siteTable.setItems(relics.getData());

        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        siteTable.setItems(relics.searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            siteTable.setItems(
                                    FXCollections.singletonObservableList(relics.get(intId)));
                        } catch (Exception e) {
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        siteTable.setItems(relics.getData());
                    }
                });

        siteTable.setRowFactory(tableView -> {
            TableRow<Relic> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Relic site = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                App.convertToURL("/main/java/application/view/SiteDetailScreen.fxml"));
                        Parent root = loader.load();
                        SiteDetailScreenController controller = loader.getController();
                        controller.setHistoricSite(site);
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
