package OrderingWindow;

import HelperClasses.Database;
import Models.Dish;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSpecialsDialogController implements Initializable {

	public RadioButton isPermanentYesButton;
	public RadioButton isPermanentNoButton;
	public RadioButton rajasthaniRasoiButton;
	public RadioButton redChilliButton;
	public TextField dishName;
	public TextField dishPrice;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ToggleGroup restaurantGroup = new ToggleGroup();
		rajasthaniRasoiButton.setToggleGroup(restaurantGroup);
		redChilliButton.setToggleGroup(restaurantGroup);

		ToggleGroup isPermanentGroup = new ToggleGroup();
		isPermanentNoButton.setToggleGroup(isPermanentGroup);
		isPermanentYesButton.setToggleGroup(isPermanentGroup);

	}

	public Dish processResult(){
		String name = dishName.getText();
		Double price = Double.parseDouble(dishPrice.getText());

		Dish.Restaurant restaurant =
				rajasthaniRasoiButton.isSelected()?Dish.Restaurant.RAJASTHANI_RASOI:Dish.Restaurant.RED_CHILLI;

		Dish dish = new Dish(name,price,restaurant);

		if (isPermanentYesButton.isSelected())saveDishToMenu(dish);
		else saveDishToSpecials(dish);

		return dish;
	}

	private void saveDishToSpecials(Dish d){
		System.out.println("Saving Dish to specials");
		Database.getInstance().addDish(d);
	}

	private void saveDishToMenu(Dish d){
		System.out.println("Saving Dish to menu");
		Database.getInstance().addDish(d);
	}
}
