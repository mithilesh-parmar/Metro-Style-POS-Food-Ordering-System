package ReportsManagement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsWindowController implements Initializable {


	//TODO make charts for monthly sale daily sale and total todays sale

	public LineChart monthlySaleLineChart;
	public NumberAxis saleForMonth;
	public NumberAxis monthNumber;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		XYChart.Series series = new XYChart.Series();


		for (int i = 0; i <20 ; i++) {
			series.getData().add(new XYChart.Data<>(i, i + 10*(i%2==0?0:12)));
		}

		monthlySaleLineChart.getData().add(series);


	}
}
