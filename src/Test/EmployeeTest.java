package Test;

import HelperClasses.Database;
import Models.Dish;
import Models.Employee;
import sun.util.resources.LocaleData;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class EmployeeTest {


	public static void main(String[] args) throws IOException {


		Database database = Database.getInstance();
		Map<LocalDate,Boolean> attendance = new HashMap<>();
		attendance.put(LocalDate.now(),true);
		for (int i = 1; i <30 ; i++) {
			if (i%2==0){
				attendance.put(LocalDate.of(2018,12,i),true);

			}
			else attendance.put(LocalDate.of(2018,12,i),false);

		}




		for (int i=0;i<15;i++){
			Employee e =new Employee("Employee "+i,
					"1212",Dish.Restaurant.RAJASTHANI_RASOI,
					12000.0*i,true);
			e.setAttendance(attendance);
			database.addEmployee(e);
		}


		System.out.println("Writing To file");

		database.saveEmployees();


	}


}
