package OrderingWindow;

import HelperClasses.Animations;
import HelperClasses.Database;
import HelperClasses.NavigationController;
import Models.Dish;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OrderingWindowController implements Initializable, NavigationController {


	@FXML public ListView<Dish> menuList;
	public TextField searchTextField;
	public BorderPane mainBorderPane;
	public Label tokenLabel;
	public TableColumn dishQuantity;
	public Button printButton;
	public Button cancelButton;
	public Label date;
	public Label totalAmountLabel;
	public TableView<Dish> orderTable;



	//Scenes
	public HBox orderingWindow;
	private Node employeeManagementWindow;
	private Node inventoryManagementWindow;
	private Node reportsWindow;



	private ContextMenu listContextMenu;

	private static int tokenNumber=1;
	private int totalAmount;
	private ObservableList<Dish> orderList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadWindows();

		menuList.setItems(Database.getInstance().getDishObservableList());
		menuList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		orderList = FXCollections.observableArrayList();
		orderTable.setItems(orderList);


		orderTable.setEditable(true);
		dishQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		dishQuantity.setOnEditCommit(e->onQuantityChanged(e));

		tokenLabel.setText(String.valueOf(tokenNumber));

		printButton.setOnAction(e->confirmOrder());

		cancelButton.setOnAction(e->cancelOrder());


		listContextMenu = new ContextMenu();
		MenuItem deleteItem = new MenuItem("Delete");
		listContextMenu.getItems().add(deleteItem);

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
		System.out.println(LocalDate.now().format(dateTimeFormatter));

		date.setText(LocalDate.now().format(dateTimeFormatter));

		totalAmountLabel.setText(String.valueOf(totalAmount));

		searchTextField.textProperty().addListener(
				(observable,oldValue,newValue)->performSearch(oldValue,newValue)
		);

		menuList.setCellFactory(new Callback<ListView<Dish>, ListCell<Dish>>() {
			@Override
			public ListCell<Dish> call(ListView<Dish> param) {
				ListCell<Dish> cell = new ListCell<Dish>(){
					@Override
					protected void updateItem(Dish item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) setText(null);
						else {
							setText(item.getDishName());
						}
					}

				};

				cell.emptyProperty().addListener(
						(obs,wasEmpty,isNowEmpty)->{
							if (isNowEmpty)cell.setContextMenu(null);
							else cell.setContextMenu(listContextMenu);
						}
				);

				return cell;
			}
		});



	}

	private void performSearch(String oldVal,String newVal){
		if (oldVal != null && (newVal.length() < oldVal.length())) {
			menuList.setItems(Database.getInstance().getDishObservableList());
		}

		String searchValue = newVal.toUpperCase();
		ObservableList<Dish> subentries = FXCollections.observableArrayList();

		for (Dish entry : menuList.getItems()) {

			if (entry.getDishName().toUpperCase().contains(searchValue)){
				subentries.add(entry);

			}
		}
		menuList.setItems(subentries);
	}

	private void loadWindows(){

		FXMLLoader employeeLoader = new FXMLLoader();
		FXMLLoader inventoryLoader = new FXMLLoader();
		FXMLLoader reportsLoader = new FXMLLoader();

		try {
			//Employee Management
			employeeLoader.setLocation(getClass().getResource("/EmployeeManagement/employee_management.fxml"));
			employeeManagementWindow = employeeLoader.load();

			//Inventory Management
			inventoryLoader.setLocation(getClass().getResource("/InventoryManagement/inventory_window.fxml"));
			inventoryManagementWindow = inventoryLoader.load();

			//reports
			reportsLoader.setLocation(getClass().getResource("/ReportsManagement/reports_window.fxml"));
			reportsWindow = reportsLoader.load();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void cancelOrder() {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("Delete Order");
		alert.setContentText("Press yes to clear order.");
		alert.showAndWait();

		if (alert.getResult() == ButtonType.OK){
			clearTable();
		}

	}

	private void clearTable(){
		orderTable.getItems().clear();
		totalAmountLabel.setText(String.valueOf(totalAmount =0));
	}

	private void confirmOrder() {
		// if no items than return
		if (orderTable.getItems().size()<=0) return;
		//TODO add Dialog pane for informing them to add items
		tokenNumber++;
		tokenLabel.setText(String.valueOf(tokenNumber));
		clearTable();
	}

	private void onQuantityChanged(Event e) {
		TableColumn.CellEditEvent<Dish, Integer> ce;
		ce = (TableColumn.CellEditEvent<Dish, Integer>) e;
		Dish d = ce.getRowValue();

		// Quantity Difference
		int quantityChanged = Math.abs(ce.getNewValue()-ce.getOldValue());

		d.setDishQuantity(ce.getNewValue());
		updateTotalAmount(d.getDishPrice()*quantityChanged);
	}

	public void handleOnKeyPressedInMenu(KeyEvent keyEvent) {
		Dish item =  menuList.getSelectionModel().getSelectedItem();
		if (item != null){
			if (keyEvent.getCode().equals(KeyCode.ENTER)){
				if (orderList.contains(item))
					orderList.get(orderList.indexOf(item)).incrementQuantityByOne();

				else
					orderList.add(item);

				updateTotalAmount(item.getDishPrice());

				searchTextField.setText("");
			}

		}

		if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)){
			searchTextField.requestFocus();
		}

	}

	public void handleOnMouseClickedInMenu(MouseEvent mouseEvent) {
		Dish item = menuList.getSelectionModel().getSelectedItem();
		if (item != null){
			if (mouseEvent.getClickCount() >= 2){
				if (orderList.contains(item))
					orderList.get(orderList.indexOf(item)).incrementQuantityByOne();
				else
					orderList.add(item);
				updateTotalAmount(item.getDishPrice());
			}

		}
	}

	private void updateTotalAmount(Double amount){
		totalAmount += amount;
		totalAmountLabel.setText(String.valueOf(totalAmount));
	}

	public void handleOnKeyPressedInSearch(KeyEvent keyEvent) {
			if (keyEvent.getCode().equals(KeyCode.ENTER)){
				menuList.requestFocus();
			}
	}

	public void handleOnKeyPressedInTable(KeyEvent keyEvent) {
		if (keyEvent.getCode().equals(KeyCode.LEFT)){
			menuList.requestFocus();
		}else if (keyEvent.getCode().equals(KeyCode.ENTER)){
			//TODO on pressing enter key edit quantity

		}else if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)){
			Dish d = orderTable.getSelectionModel().getSelectedItem();
			if (d == null) return;
			orderList.remove(orderTable.getSelectionModel().getSelectedItem());
			updateTotalAmount(-d.getTotalPrice());
			d.setDishQuantity(1);
		}
	}

	public void showAddSpecialItemDialog(ActionEvent actionEvent) {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Add Item");

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("add_specials_dialog.fxml"));

		try{
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Error Occured While Loading Dialog pane");
			e.printStackTrace();
		}

		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		dialog.showAndWait();

		if (dialog.getResult() == ButtonType.OK){
			AddSpecialsDialogController controller = fxmlLoader.getController();
			Dish d = controller.processResult();
			menuList.getSelectionModel().select(d);
		}
	}

	public void handleExit(ActionEvent actionEvent) {
		Platform.exit();
	}


	//TODO fix the glitch with pane
	public void handleOnMouseEntered(MouseEvent mouseEvent) {
		HBox view = (HBox)mouseEvent.getSource();
		Pane indicatorPane = (Pane) view.getChildren().get(0);
		indicatorPane.setStyle("-fx-background-color:derive(red,70%);");
		Animations.makeButtonOutAnimations(50,view);

	}

	public void handleOnMouseExited(MouseEvent mouseEvent) {
		HBox view = (HBox)mouseEvent.getSource();
		Pane indicatorPane = (Pane) view.getChildren().get(0);
		indicatorPane.setStyle("-fx-background-color:#2d3041;");
		Animations.makeButtonInAnimations(50,view);
	}

	public void showEmployeeWindow(ActionEvent actionEvent) throws IOException {
		changeSceneTo(employeeManagementWindow);
	}

	public void showInventoryWindow(ActionEvent actionEvent) throws IOException {
		changeSceneTo(inventoryManagementWindow);
	}

	public void showReportsWindow(ActionEvent actionEvent) throws IOException {
		changeSceneTo(reportsWindow);
	}

	@Override
	public void changeSceneTo(Node node) {
		Animations.makeFadeInTransition(350,node);

		//TODO select the button with current window
//		if (node == orderingWindow){
//
//		}else if (node == inventoryManagementWindow){
//
//		}else if (node == employeeManagementWindow){
//
//		}else if (node == reportsWindow){
//
//		}
		mainBorderPane.setCenter(node);
	}

	public void showOrderingWindow(ActionEvent actionEvent) {
		changeSceneTo(orderingWindow);
	}
}
