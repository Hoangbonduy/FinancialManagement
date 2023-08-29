package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javaUtils.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.ChangeScene;

public class SignUpController {

    @FXML
    private Button backtoLoginButton;

    @FXML
    private Label registerAlert;

    @FXML
    private Button registerButton;

    @FXML
    private TextField registerUserEmail;

    @FXML
    private TextField registerUserName;

    @FXML
    private TextField registerUserPassword;
    
    @FXML
    private AnchorPane signupAnchorPane;

    @FXML
    void OnClickbacktoLoginButton(ActionEvent event) throws IOException {
    	 new ChangeScene(signupAnchorPane,"/views/Login.fxml");
    }

    @FXML
    void OnClickregisterButton(ActionEvent event) throws SQLException {
    	Connection connection = null;
		PreparedStatement preparedStatementUserCheck = null;
		PreparedStatement preparedStatementUserRegister = null;
		PreparedStatement preparedStatementDBRegister = null;
		ResultSet resultSet = null;
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				registerAlert.setText("Không thể kết nối. Vui lòng thử lại sau");
			}
			String sqlCheck = "SELECT * from user WHERE username = ?";
			preparedStatementUserCheck = connection.prepareStatement(sqlCheck);
			preparedStatementUserCheck.setString(1, registerUserName.getText());
			resultSet = preparedStatementUserCheck.executeQuery();
			
			if (resultSet.isBeforeFirst()) {
				if (registerUserName.getText().isEmpty() || registerUserPassword.getText().isEmpty() || registerUserEmail.getText().isEmpty()) {
					registerAlert.setText("Vui lòng nhập đầy đủ thông tin");
				}
				else registerAlert.setText("Người dùng đã tồn tại");
			} else if (!javaUtils.LoginUtils.isValidEmail(registerUserEmail.getText())) {
				registerAlert.setText("Email không hợp lệ");
			} else if (javaUtils.LoginUtils.isContainSpace(registerUserName.getText()) || javaUtils.LoginUtils.isContainSpace(registerUserPassword.getText())) {
				registerAlert.setText("Tên hoặc mật khẩu không được chứa khoảng trắng");
			} else {
				String sqlRegisterString = "CREATE TABLE " + registerUserName.getText() + " (`idpayment` MEDIUMINT NOT NULL AUTO_INCREMENT , `namepayment` VARCHAR(50) NOT NULL , `moneypayment` MEDIUMINT NOT NULL , `datepayment` VARCHAR(10) NOT NULL , `typepayment` VARCHAR(10) NOT NULL , `notepayment` VARCHAR(50) NOT NULL , PRIMARY KEY (`idpayment`)) ENGINE = InnoDB CHARSET=utf8 COLLATE utf8_general_ci";
				preparedStatementDBRegister = connection.prepareStatement(sqlRegisterString);
				preparedStatementDBRegister.executeUpdate();
				
				String sqlRegister = "INSERT INTO user (username,userpassword,useremail) VALUES (?,?,?)";
				preparedStatementUserRegister = connection.prepareStatement(sqlRegister);
				preparedStatementUserRegister.setString(1, registerUserName.getText());
				preparedStatementUserRegister.setString(2, registerUserPassword.getText());
				preparedStatementUserRegister.setString(3, registerUserEmail.getText());
				preparedStatementUserRegister.executeUpdate();
				registerAlert.setText("Đăng ký thành công");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
			if (preparedStatementUserCheck != null) {
				preparedStatementUserCheck.close();
			}
			if (preparedStatementUserRegister != null) {
				preparedStatementUserRegister.close();
			}
			if (preparedStatementDBRegister != null) {
				preparedStatementDBRegister.close();
			}
		}
    }

}
