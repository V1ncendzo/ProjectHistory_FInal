package history.collection;

import history.entity.BaseEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.Collection;
import java.util.Comparator;

public class EntityCollection<T extends BaseEntity> {

    private ObservableList<T> data = FXCollections.observableArrayList();

    public void setData(Collection<T> data) {
        this.data = FXCollections.observableArrayList(data);
    }

    public <V extends BaseEntity> ObservableList<V> getData() {
        return (ObservableList<V>) data;
    }

    // public boolean exists(T entity) {
    // return data.contains(entity);
    // }

    // public void add(T entity) {
    // if (!exists(entity))
    // data.add(entity);
    // }

    public T get(Integer id) {
        if (id == null) {
            return null;
        }

        for (T entity : this.data) {
            if (entity != null && entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    public T get(String name) {
        for (T entity : data) {
            if (entity != null && entity.hasName(name))
                return entity;
        }
        return null;
    }

    public FilteredList<T> searchByName(String name) {
        ObservableList<T> filteredData = FXCollections.observableArrayList(
                data.filtered(entity -> entity != null && entity.isMatch(name)));
        return new FilteredList<>(filteredData);

    }

    public void sortById() {
        Comparator<T> comparator = Comparator.comparingInt(BaseEntity::getId);

        data.sort(comparator);
    }
}