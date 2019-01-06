package Test;

import HelperClasses.Database;
import Models.InventoryItem;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.time.LocalDate;

public class InventoryTest {



	public static void main(String[] args) throws IOException, ClassNotFoundException {
		 Database database = Database.getInstance();

		for (int i = 0; i <10 ; i++) {
			database.addInventoryItem(new InventoryItem(
					"Milk "+i,
					i*120.0,
					i,
					LocalDate.now()
			));
		}
		database.saveInventoryItems();
		database.loadInventory();



	}


}
