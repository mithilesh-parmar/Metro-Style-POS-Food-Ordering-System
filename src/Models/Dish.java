package Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Dish {

	public enum Restaurant{
		RED_CHILLI("Red Chilli"),RAJASTHANI_RASOI("Rajasthani Rasoi");

		String name;

		Restaurant(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}

	}

	private SimpleStringProperty dishName = new SimpleStringProperty("");
	private SimpleDoubleProperty dishPrice = new SimpleDoubleProperty();
	private SimpleIntegerProperty dishQuantity = new SimpleIntegerProperty();
	private SimpleDoubleProperty totalPrice = new SimpleDoubleProperty();
	private Restaurant restaurant;

	public Dish(String dishName,Double dishPrice, Restaurant restaurant) {
		this.restaurant = restaurant;
		this.dishName.set(dishName);
		this.dishPrice.set(dishPrice);
		this.dishQuantity.set(1);
		this.totalPrice.set(dishPrice);
	}

	public int getDishQuantity() {
		return dishQuantity.get();
	}

	public SimpleIntegerProperty dishQuantityProperty() {
		return dishQuantity;
	}

	public double getTotalPrice() {
		return totalPrice.get();
	}

	public void setDishQuantity(int dishQuantity) {
		this.dishQuantity.set(dishQuantity);
		this.totalPrice.set(dishQuantity*this.dishPrice.getValue());
	}

	public SimpleDoubleProperty totalPriceProperty() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice.set(totalPrice);
	}

	public void incrementQuantityByOne(){
		this.dishQuantity.set(this.dishQuantity.getValue()+1);
		this.totalPrice.set(this.totalPrice.getValue()+this.dishPrice.getValue());
	}

	public void decrementQuantityByOne(){
		this.dishQuantity.set(this.dishQuantity.getValue()-1);
		this.totalPrice.set(this.totalPrice.getValue()-this.dishPrice.getValue());
	}

	public String getDishName() {
		return dishName.get();
	}

	public SimpleStringProperty dishNameProperty() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName.set(dishName);
	}

	public double getDishPrice() {
		return dishPrice.get();
	}

	public SimpleDoubleProperty dishPriceProperty() {
		return dishPrice;
	}

	public void setDishPrice(double dishPrice) {
		this.dishPrice.set(dishPrice);
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public String toString() {
		return getDishName();
	}
}
