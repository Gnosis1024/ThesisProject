package com.pierre.view;

import com.pierre.util.AttributeUtil;
import javafx.collections.FXCollections;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class DataTable<T> {

    private TableView<T> table;

    public DataTable() {

        table = new TableView<>();
    }

    public void setData(Set<T> dataset, int columnWidth) {

        if (table.getItems().isEmpty()) {

            List<String> attributesList = AttributeUtil.getAttributesList(dataset.iterator().next());

            for (String attribute : attributesList) {

                table.getColumns().add(createColumn(attribute, columnWidth));
            }

            table.setItems(FXCollections.observableArrayList(dataset));

            table.setSelectionModel(null);
        }
    }

    private <S> TableColumn<T, S> createColumn(String attribute, int columnWidth) {

        TableColumn<T, S> column = new TableColumn<>(attribute.toUpperCase());

        column.setMinWidth(columnWidth);
        column.setCellValueFactory(new PropertyValueFactory<>(attribute));

        column.setCellFactory(new Callback<TableColumn<T, S>, TableCell<T, S>>() {

            @Override
            public TableCell<T, S> call(TableColumn<T, S> param) {

                return new TableCell<T, S>() {

                    @Override
                    public void updateItem(S item, boolean empty) {

                        super.updateItem(item, empty);

                        if (!isEmpty()) {

                            setTextFill(Color.BLACK);
                            setText(item.toString());
                        }
                    }
                };
            }
        });

        return column;
    }

    public void stylizeTable(String targetAttribute) {

        table.getStylesheets().add("styles/table.css");

        table.setRowFactory(new Callback<TableView<T>, TableRow<T>>() {

            @Override
            public TableRow<T> call(TableView<T> tableView) {

                return new TableRow<T>() {

                    @Override
                    protected void updateItem(T item, boolean b) {

                        super.updateItem(item, b);

                        Object targetAttributeObject = AttributeUtil.getAttribute(item, targetAttribute);

                        if (targetAttributeObject instanceof Boolean) {

                            if ((Boolean) targetAttributeObject) {

                                getStyleClass().add("yes-trust");
                                setTextFill(Color.BLACK);

                            } else {

                                getStyleClass().add("no-trust");
                                setTextFill(Color.WHITE);
                            }
                        }
                    }
                };
            }
        });
    }
}
