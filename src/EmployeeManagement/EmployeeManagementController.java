package EmployeeManagement;

import HelperClasses.Database;
import Models.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeManagementController implements Initializable {

	public BorderPane mainBorderPane;
	public ListView<Employee> employeeList;

	public Label nameLabel;
	public Label totalPresentLabel;
	public Label totalAbsentLabel;
	public Label aadharNumberLabel;
	public Label restaurantLabel;
	public ToggleButton attendanceToggleButton;
	public VBox detailView;

	private ContextMenu listContextMenu;

	private Employee employee;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		employeeList.setItems(Database.getInstance().getEmployeeObservableList());
		employeeList.getSelectionModel().selectFirst();
		mainBorderPane.setCenter(null);

		listContextMenu = new ContextMenu();
		MenuItem deteleMenuItem = new MenuItem("Delete");
		listContextMenu.getItems().add(deteleMenuItem);
		deteleMenuItem.setOnAction(e->deleteEmployee(employeeList.getSelectionModel().getSelectedItem()));

		employee = employeeList.getSelectionModel().getSelectedItem();



		employeeList.setCellFactory(new Callback<ListView<Employee>, ListCell<Employee>>() {
			@Override
			public ListCell<Employee> call(ListView<Employee> param) {
				ListCell<Employee> cell = new ListCell<Employee>(){
					@Override
					protected void updateItem(Employee item, boolean empty) {
						super.updateItem(item, empty);
						if (empty){
							//TODO add view to show empty list
							setText(null);
						}else {
							setText(item.getName());
							if (item.isPresentToday()){
								//TODO add present indicator
							}else{
								//TODO add absent indicator
							}
						}
					}
				};

				cell.emptyProperty().addListener(
						(observable,wasEmpty,isNowEmpty)->{
							if (isNowEmpty)cell.setContextMenu(null);
							else cell.setContextMenu(listContextMenu);
						}
				);

				return cell;
			}
		});

	}



	public void handleOnMouseClickedInEmployeeList(MouseEvent mouseEvent) {
		employee = employeeList.getSelectionModel().getSelectedItem();
		if (mainBorderPane.getCenter()==null)mainBorderPane.setCenter(detailView);
		showEmployeeDetails();
	}

	public void handleOnKeyPressedInEmployeeList(KeyEvent keyEvent) {
		employee = employeeList.getSelectionModel().getSelectedItem();
		if (mainBorderPane.getCenter()==null)mainBorderPane.setCenter(detailView);
		showEmployeeDetails();
	}


	public void updateAadharNumber(ActionEvent actionEvent) {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Update Aadhar Number");

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("update_dialog.fxml"));

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
			UpdateDialogController controller = fxmlLoader.getController();
//			Employee e = controller.process();
//
//			Database.getInstance().addEmployee(e);
		}
	}

	public void updateRestaurant(ActionEvent actionEvent) {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Update Restaurant");

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("update_dialog.fxml"));

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
			UpdateDialogController controller = fxmlLoader.getController();
//			Employee e = controller.process();
//
//			Database.getInstance().addEmployee(e);
		}
	}

	public void updateAttendance(ActionEvent actionEvent) {

		employee.setPresentToday(attendanceToggleButton.isSelected());

		totalPresentLabel.setText(String.valueOf(employee.getTotalPresentDays()));

		totalAbsentLabel.setText(String.valueOf(employee.getTotalAbsentDays()));

		attendanceToggleButton.setText(employee.isPresentToday()?"Present\nToday":"Absent\nToday");

	}

	private void showEmployeeDetails(){
		nameLabel.setText(employee.getName());
		aadharNumberLabel.setText(employee.getAadharNumber());
		restaurantLabel.setText(employee.getRestaurant().toString());

		//set inital sate of toggle  button
		attendanceToggleButton.setSelected(employee.isPresentToday());
		//set text of toggle button for today
		attendanceToggleButton.setText(employee.isPresentToday()?"Present\nToday":"Absent\nToday");

		totalAbsentLabel.setText(String.valueOf(employee.getTotalAbsentDays()));
		totalPresentLabel.setText(String.valueOf(employee.getTotalPresentDays()));
	}

	public void showAddEmployeeDialog(ActionEvent actionEvent) {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Add Employee");

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("add_employee.fxml"));

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
			AddEmployeeController controller = fxmlLoader.getController();
			Employee e = controller.process();
			e.setPresentToday(true);
			employeeList.getSelectionModel().select(e);
			Database.getInstance().addEmployee(e);
		}
	}

	private void deleteEmployee(Employee item) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("Delete "+ item.getName());
		alert.setContentText("Press yes to remove employee.");
		alert.showAndWait();

		if (alert.getResult() == ButtonType.OK){
			Database.getInstance().deleteEmployee(item);
		}
	}
}
