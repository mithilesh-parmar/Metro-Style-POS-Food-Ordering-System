package EmployeeManagement;

import Models.Dish;
import Models.Employee;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

	public DialogPane dialogRoot;
	public TextField employeeName;
	public TextField employeeAadharNumber;
	public TextField employeeAge;
	public TextField employeeSalary;

	public RadioButton redChilliButton;
	public RadioButton rajasthaniRasoiButton;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ToggleGroup restaurantGroup = new ToggleGroup();
		redChilliButton.setToggleGroup(restaurantGroup);
		rajasthaniRasoiButton.setToggleGroup(restaurantGroup);

	}

	 Employee process(){
		Dish.Restaurant restaurant = redChilliButton.isSelected()?
				Dish.Restaurant.RED_CHILLI:Dish.Restaurant.RAJASTHANI_RASOI;

		return new Employee(
				employeeName.getText(),
				employeeAadharNumber.getText(),
				restaurant,
				Double.parseDouble(employeeSalary.getText()),
				true
		);
	}
}
