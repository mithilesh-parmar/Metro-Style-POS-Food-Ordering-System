package InventoryManagement;

import HelperClasses.Database;
import HelperClasses.Dialog;
import Models.InventoryItem;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InventoryWindowController implements Initializable {

	public TableView<InventoryItem> tableView;
	public TextField inventoryItemName;
	public TextField inventoryItemQuantity;
	public TextField inventoryItemPrice;
	public DatePicker inventoryItemPurchaseDate;
	public TableColumn dateCol;
	public TableColumn nameCol;
	public TableColumn quantityCol;
	public TableColumn priceCol;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableView.setItems(Database.getInstance().getInventoryItemObservableList());

		tableView.setEditable(true);

		dateCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		dateCol.setOnEditCommit(e->onDateChanged(e));

		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setOnEditCommit(e->onNameChanged(e));

		quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		quantityCol.setOnEditCommit(e->onQuantityChanged(e));

		priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		priceCol.setOnEditCommit(e->onPriceChanged(e));



		inventoryItemPurchaseDate.setValue(LocalDate.now());




	}

	private void onNameChanged(Event e) {
		TableColumn.CellEditEvent<InventoryItem,String> event =
				(TableColumn.CellEditEvent<InventoryItem, String>) e;
		InventoryItem item = ((TableColumn.CellEditEvent<InventoryItem, String>) e).getRowValue();
		item.setItemName(((TableColumn.CellEditEvent<InventoryItem,String>) e).getNewValue());

	}


	private void onPriceChanged(Event e) {
		TableColumn.CellEditEvent<InventoryItem,Double> event =
				(TableColumn.CellEditEvent<InventoryItem, Double>) e;
		InventoryItem item = ((TableColumn.CellEditEvent<InventoryItem, Double>) e).getRowValue();
		item.setItemPrice(((TableColumn.CellEditEvent<InventoryItem, Double>) e).getNewValue());
	}

	private void onQuantityChanged(Event e) {
		TableColumn.CellEditEvent<InventoryItem,Integer> event =
				(TableColumn.CellEditEvent<InventoryItem, Integer>) e;
		InventoryItem item = ((TableColumn.CellEditEvent<InventoryItem, Integer>) e).getRowValue();
		item.setItemQuantity(((TableColumn.CellEditEvent<InventoryItem, Integer>) e).getNewValue());
	}


	private void onDateChanged(Event e) {
		TableColumn.CellEditEvent<InventoryItem,LocalDate> event =
				(TableColumn.CellEditEvent<InventoryItem, LocalDate>) e;
		InventoryItem item = ((TableColumn.CellEditEvent<InventoryItem, LocalDate>) e).getRowValue();
		item.setItemPurchaseDate(((TableColumn.CellEditEvent<InventoryItem, LocalDate>) e).getNewValue());

	}

	public void handleOnKeyPressedInTable(KeyEvent keyEvent) {
		if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)){
			InventoryItem item = tableView.getSelectionModel().getSelectedItem();
			boolean result=Dialog.showInfoDialog("Confirm","Are you sure to delete "+item.getItemName(),
					"Press yes to delete.");
			if (result)Database.getInstance().removeInventoryItem(item);
		}
	}

	public void handleAddButtonAction(ActionEvent actionEvent) {
		InventoryItem item = new InventoryItem(
				inventoryItemName.getText(),
				Double.parseDouble(inventoryItemPrice.getText()),
				Integer.parseInt(inventoryItemQuantity.getText()),
				inventoryItemPurchaseDate.getValue()
		);

		Database.getInstance().addInventoryItem(item);
	}
}
