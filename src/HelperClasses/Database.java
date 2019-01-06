package HelperClasses;

import Models.Dish;
import Models.Employee;
import Models.InventoryItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Database {

	private final static String MENU_DISH_FILE= "menu.txt";
	private final static String INVENTORY_FILE = "inventory.txt";
//	private final static String REPORTS_FILE = "reports.txt";
	private final static String EMPLOYEE_FILE= "employee.txt";
	private final static Database instance = new Database();
	private ObservableList<Dish> dishObservableList;
	private ObservableList<Employee> employeeObservableList;
	private ObservableList<InventoryItem> inventoryItemObservableList;


	private Database(){
		dishObservableList = FXCollections.observableArrayList();
		employeeObservableList  =FXCollections.observableArrayList();
		inventoryItemObservableList = FXCollections.observableArrayList();
	}

	public  void start() throws IOException, ClassNotFoundException {
		loadDishes();
		loadEmployees();
		loadInventory();
	}

	public void stop() throws IOException {
		saveDishes();
		saveEmployees();
		saveInventoryItems();
	}

	public void loadDishes() throws IOException {
		File file = new File(MENU_DISH_FILE);
		if (!file.exists())file.createNewFile();
		Path path = Paths.get(MENU_DISH_FILE);
		BufferedReader reader = Files.newBufferedReader(path);

		String input;

		try{
			while((input = reader.readLine())!=null){
				String[] lines= input.split("\t");
				String dishName = lines[0],
						dishPrice = lines[1],
						dishRestaurant = lines[2];
				Dish.Restaurant restaurant = dishRestaurant
						.matches(Dish.Restaurant.RAJASTHANI_RASOI.toString())?
						Dish.Restaurant.RAJASTHANI_RASOI:Dish.Restaurant.RED_CHILLI;
				dishObservableList.add(new Dish(dishName,Double.parseDouble(dishPrice),restaurant));
			}
		}finally {
			if (reader != null)reader.close();
		}


	}

	public void loadEmployees() throws IOException, ClassNotFoundException {
		File file = new File(EMPLOYEE_FILE);
		if (!file.exists())file.createNewFile();

		if (file.length()>0)
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))){
			this.employeeObservableList.setAll((List<Employee>)reader.readObject());
		}
	}

	public void loadInventory()throws IOException, ClassNotFoundException{
		File file = new File(INVENTORY_FILE);
		if (!file.exists())file.createNewFile();

		if (file.length()>0)
			try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))){
				this.inventoryItemObservableList.setAll((List<InventoryItem>)reader.readObject());
//				System.out.println((List<InventoryItem>)reader.readObject());
			}
	}

	public void saveEmployees()throws IOException{
		File file = new File(EMPLOYEE_FILE);
		if (!file.exists())file.createNewFile();

		try(
				ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file));
		){
			List<Employee> employees = new ArrayList<>();
			employees.addAll(this.employeeObservableList);
			writer.writeObject(employees);
			writer.flush();
		}

	}

	public void saveInventoryItems()throws IOException{
		File file = new File(INVENTORY_FILE);
		if (!file.exists())file.createNewFile();

		try(
				ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file));
		){
			List<InventoryItem> inventoryItems = new ArrayList<>();
			inventoryItems.addAll(this.inventoryItemObservableList);
			writer.writeObject(inventoryItems);
			writer.flush();
		}
	}

	public void saveDishes() throws IOException {
		File file = new File(MENU_DISH_FILE);
		if (!file.exists())file.createNewFile();
		Path path = Paths.get(MENU_DISH_FILE);
		BufferedWriter writer = Files.newBufferedWriter(path);
		try{
			Iterator<Dish> dishIterator = dishObservableList.iterator();
			while (dishIterator.hasNext()){
				Dish item = dishIterator.next();
				writer.write(String.format("%s\t%s\t%s",
						item.getDishName(),
						item.getDishPrice(),
						item.getRestaurant().toString()));
				writer.newLine();
				writer.flush();
			}
		}finally {
			if (writer != null) writer.close();
		}

	}

	public ObservableList<Dish> getDishObservableList() {
		return dishObservableList;
	}

	public static Database getInstance() {
		return instance;
	}

	public void addDish(Dish d){
		dishObservableList.add(d);
	}

	public void removeDishFromMenu(Dish d){
		dishObservableList.remove(d);
	}

	public void addEmployee(Employee e){
		employeeObservableList.add(e);
	}

	public void removeEmployee(Employee e){
		employeeObservableList.remove(e);
	}

	public ObservableList<Employee> getEmployeeObservableList() {
		return employeeObservableList;
	}

	public ObservableList<InventoryItem> getInventoryItemObservableList() {
		return inventoryItemObservableList;
	}

	public void addInventoryItem(InventoryItem i){
		inventoryItemObservableList.add(i);
	}

	public void deleteInventoryItem(InventoryItem i){
		inventoryItemObservableList.remove(i);
	}

	public void deleteEmployee(Employee item) {
		employeeObservableList.remove(item);
	}

	public void removeInventoryItem(InventoryItem item) {
		inventoryItemObservableList.remove(item);
	}
}
