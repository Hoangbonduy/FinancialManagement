package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.ChangeScene;
import models.UserInce;

public class AddPaymentController implements Initializable{

	@FXML
	private AnchorPane addpaymentAnchorPane;

	@FXML
    private Button addPayment_addPayment;

    @FXML
    private Button addPayment_deletePayment;

    @FXML
    private Button addPayment_seelistofPayments;

    @FXML
    private Button addPayment_statistics;
    
    @FXML
    private Button submitnewPayment;
    
    @FXML
    private Button backtoHomePageButton;

    @FXML
    private TextField namePayment;

    @FXML
    private TextField moneyPayment;

    @FXML
    private TextField notePayment;

    @FXML
    private ChoiceBox<String> typePayment;
    
    @FXML
    private Label useridAddPayment;

    @FXML
    private Label usernameAddPayment;
    
    @FXML
    private Label alertAddPayment;
    
    @FXML
    private DatePicker datePayment;
    
    private String typePaymetString[] = {"Trả","Nhận","Nợ"}; // pay earn debt
    
    UserInce userInce = UserInce.getInstance();
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	usernameAddPayment.setText(userInce.getUsername());
		useridAddPayment.setText("Id: " + userInce.getUserid());
    	typePayment.getItems().addAll(typePaymetString);
    	typePayment.setValue("Trả");
	}

    @FXML
    void OnClickdeletePayment(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/DeletePayment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage)addPayment_deletePayment.getScene().getWindow();
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
        
        Stage stage = (Stage)addPayment_seelistofPayments.getScene().getWindow();
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
        
        Stage stage = (Stage)addPayment_statistics.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
    }
    
    @FXML
    void OnclicksubmitnewPayment(ActionEvent event) throws SQLException {
    	Connection connection = null;
		PreparedStatement preparedStatementPaymentRegister = null;
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				alertAddPayment.setText("Không thể kết nối. Vui lòng thử lại sau");
			}
			if (namePayment.getText().isEmpty() || moneyPayment.getText().isEmpty() || datePayment.getValue() == null) {
				alertAddPayment.setText("Vui lòng nhập đầy đủ thông tin");
			} else if (Integer.parseInt(moneyPayment.getText()) <= 0){
				alertAddPayment.setText("Vui lòng nhập lại số tiền");
			} else {
				String sqlRegister = "INSERT INTO " + userInce.getUsername() + " (namepayment,moneypayment,datepayment,typepayment,notepayment) VALUES (?,?,?,?,?)";
				preparedStatementPaymentRegister = connection.prepareStatement(sqlRegister);
				preparedStatementPaymentRegister.setString(1, namePayment.getText());
				preparedStatementPaymentRegister.setLong(2, Integer.parseInt(moneyPayment.getText()));
				preparedStatementPaymentRegister.setString(3, datePayment.getValue().toString());
				preparedStatementPaymentRegister.setString(4, typePayment.getValue());
				preparedStatementPaymentRegister.setString(5, notePayment.getText());
				preparedStatementPaymentRegister.executeUpdate();
				alertAddPayment.setText("Thêm thành công");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (preparedStatementPaymentRegister != null) {
				preparedStatementPaymentRegister.close();
			}
		}
    }
    
    @FXML
    void OnClickbacktoHomePageButton(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/HomePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage)backtoHomePageButton.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
    }
}