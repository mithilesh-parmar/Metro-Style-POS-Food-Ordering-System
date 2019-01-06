package Models;

import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Employee implements Serializable {

	private String name,aadharNumber;
	private Dish.Restaurant restaurant;
	private double monthlySalary;
	private boolean isPresentToday;
	private Map<LocalDate,Boolean> attendance;
	private int totalPresentDays,totalAbsentDays;

	public Employee(String name, String aadharNumber, Dish.Restaurant restaurant, double monthlySalary, boolean isPresentToday) {
		this.name = name;
		this.aadharNumber = aadharNumber;
		this.restaurant = restaurant;
		this.monthlySalary = monthlySalary;
		this.isPresentToday = isPresentToday;
		attendance = new HashMap<>();
	}

	public String getName() {
		return name.toUpperCase();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public Dish.Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Dish.Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public double getMonthlySalary() {
		return monthlySalary;
	}

	public void setMonthlySalary(double monthlySalary) {
		this.monthlySalary = monthlySalary;
	}

	public boolean isPresentToday() {
		return isPresentToday;
	}

	public void setPresentToday(boolean presentToday) {
		isPresentToday = presentToday;
		attendance.put(LocalDate.now(),presentToday);
		System.out.println(attendance);
		calculateAttendance();
	}

	private void calculateAttendance(){
		totalAbsentDays =0;
		totalPresentDays=0;
		for (Map.Entry<LocalDate,Boolean> item:attendance.entrySet()){
			if (item.getValue())totalPresentDays++;
			else totalAbsentDays++;
		}
//		System.out.println("Present: "+totalPresentDays);
//		System.out.println("Absent: "+totalAbsentDays);
	}

	public int getTotalPresentDays() {
		return totalPresentDays;
	}

	public int getTotalAbsentDays() {
		return totalAbsentDays;
	}

	public Map<LocalDate, Boolean> getAttendance() {
		return attendance;
	}

	public void setAttendance(Map<LocalDate, Boolean> attendance) {
		this.attendance = attendance;
		calculateAttendance();
	}

	@Override
	public String toString() {
		return name.toUpperCase();
	}
}
