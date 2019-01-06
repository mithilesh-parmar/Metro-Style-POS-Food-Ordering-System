package Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

public class InventoryItem implements Serializable {

	private SimpleStringProperty itemName = new SimpleStringProperty("");
	private SimpleDoubleProperty itemPrice = new SimpleDoubleProperty();
	private SimpleIntegerProperty itemQuantity = new SimpleIntegerProperty();
	private SimpleObjectProperty<LocalDate> itemPurchaseDate = new SimpleObjectProperty<>();

	public InventoryItem(String itemName,double itemPrice,int itemQuantity,LocalDate itemPurchaseDate) {
		this.itemName.set(itemName);
		this.itemPrice.set(itemPrice);
		this.itemQuantity.set(itemQuantity);
		this.itemPurchaseDate.set(itemPurchaseDate);
	}


	private void writeObject(ObjectOutputStream writer) throws IOException {
		writer.writeUTF(itemName.getValue());
		writer.writeDouble(itemPrice.getValue());
		writer.writeInt(itemQuantity.getValue());
		writer.writeObject(itemPurchaseDate.getValue());
		writer.flush();

	}

	private void readObject(ObjectInputStream reader) throws IOException, ClassNotFoundException {

		itemName = new SimpleStringProperty("");
		itemPrice = new SimpleDoubleProperty();
		itemQuantity = new SimpleIntegerProperty();
		itemPurchaseDate = new SimpleObjectProperty<>();

		itemName.set(reader.readUTF());
		itemPrice.set(reader.readDouble());
		itemQuantity.set(reader.readInt());
		itemPurchaseDate.set((LocalDate) reader.readObject());


	}

	public SimpleStringProperty itemNameProperty() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName.set(itemName);
	}

	public SimpleDoubleProperty itemPriceProperty() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice.set(itemPrice);
	}

	public SimpleIntegerProperty itemQuantityProperty() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity.set(itemQuantity);
	}

	public SimpleObjectProperty<LocalDate> itemPurchaseDateProperty() {
		return itemPurchaseDate;
	}

	public void setItemPurchaseDate(LocalDate itemPurchaseDate) {
		this.itemPurchaseDate.set(itemPurchaseDate);
	}

	public String getItemName() {
		return itemName.get();
	}

	public double getItemPrice() {
		return itemPrice.get();
	}

	public int getItemQuantity() {
		return itemQuantity.get();
	}

	public LocalDate getItemPurchaseDate() {
		return itemPurchaseDate.get();
	}

	@Override
	public String toString() {
		return "Name: "+getItemName()+" Quantity "+getItemQuantity()+" price "+getItemPrice()+" date "+getItemPurchaseDate();
	}
}
