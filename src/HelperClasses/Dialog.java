package HelperClasses;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Dialog {

	public static boolean showInfoDialog(String title,String header,String msg){
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setContentText(msg);
		alert.showAndWait();
		alert.setHeaderText(header);
		return alert.getResult() == ButtonType.OK;
	}
}
