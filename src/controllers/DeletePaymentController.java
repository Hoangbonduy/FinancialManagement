package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javaUtils.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.ChangeScene;
import models.UserInce;

public class DeletePaymentController implements Initializable{

    @FXML
    private Button addPayment_DeletePayment;

    @FXML
    private Button backtoHomePageButton_DeletePayment;

    @FXML
    private DatePicker datePayment;

    @FXML
    private TextField moneyPayment;

    @FXML
    private TextField namePayment;

    @FXML
    private Button seelistofPayments_DeletePayment;

    @FXML
    private Button statistics_DeletePayment;

    @FXML
    private Button submitdeletePayment;

    @FXML
    private Label useridDeletePayment;

    @FXML
    private Label usernameDeletePayment;
    
    @FXML
    private ChoiceBox<String> typePayment;
    
    @FXML
    private Label alertdeletePayment;
    
    private String typePaymetString[] = {"Trả","Nhận","Nợ"}; // pay earn debt
    
    UserInce userInce = UserInce.getInstance();

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	usernameDeletePayment.setText(userInce.getUsername());
		useridDeletePayment.setText("Id: " + userInce.getUserid());
		typePayment.getItems().addAll(typePaymetString);
	}
    
    @FXML
    void OnClickaddPayment(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/AddPayment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
       
        
        Stage stage = (Stage)addPayment_DeletePayment.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
    }

    @FXML
    void OnClickbacktoHomePageButton(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/HomePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        
        
        Stage stage = (Stage)backtoHomePageButton_DeletePayment.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
    }

    @FXML
    void OnClickseelistofPayments(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ListofPayments.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
        
      
        
        Stage stage = (Stage)seelistofPayments_DeletePayment.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
    }

    @FXML
    void OnClickstatistics(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/Statistics.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
        
        Stage stage = (Stage)statistics_DeletePayment.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
    }

    @FXML
    void OnclicksubmitdeletePayment(ActionEvent event) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				alertdeletePayment.setText("Không thể kết nối. Vui lòng thử lại sau");
			}
			if (namePayment.getText().isEmpty() && datePayment.getValue() == null && moneyPayment.getText().isEmpty() && typePayment.getValue() == null) {
				alertdeletePayment.setText("Error");
			} else {
				if (!namePayment.getText().isEmpty()) {
					String sqlString = "DELETE from " + userInce.getUsername() + " WHERE namepayment = ?, owneduser = ?";
					preparedStatement = connection.prepareStatement(sqlString);
					preparedStatement.setString(1, namePayment.getText());
					preparedStatement.setString(2, userInce.getUsername());
					preparedStatement.executeUpdate();
					preparedStatement.close();
				}
				if (datePayment.getValue() != null) {
					String sqlString = "DELETE from " + userInce.getUsername() + " datepayment = ?, owneduser = ?";
					preparedStatement = connection.prepareStatement(sqlString);
					preparedStatement.setString(1, datePayment.getValue().toString());
					preparedStatement.setString(2, userInce.getUsername());
					preparedStatement.executeUpdate();
					preparedStatement.close();
				}
				if (!moneyPayment.getText().isEmpty()) {
					String sqlString = "DELETE from " + userInce.getUsername() + " WHERE moneypayment = ?, owneduser = ?";
					preparedStatement = connection.prepareStatement(sqlString);
					preparedStatement.setLong(1, Integer.parseInt(moneyPayment.getText()));
					preparedStatement.setString(2, userInce.getUsername());
					preparedStatement.executeUpdate();
					preparedStatement.close();
				}
				if (typePayment.getValue() != null) {
					String sqlString = "DELETE from " + userInce.getUsername() + " WHERE typepayment = ?, owneduser = ?";
					preparedStatement = connection.prepareStatement(sqlString);
					preparedStatement.setString(1, typePayment.getValue());
					preparedStatement.setString(2, userInce.getUsername());
					preparedStatement.executeUpdate();
					preparedStatement.close();
				}
				alertdeletePayment.setText("Đã xóa");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
    }
    
}
