package controllers;

import java.io.IOException;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javaUtils.DBConnection;
import models.ChangeScene;
import models.UserInce;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Label alertError;

    @FXML
    private Button forgetPassword;

    @FXML
    private Button loginButton;

    @FXML
    private TextField userName;

    @FXML
    private TextField userPassword;
    
    @FXML
    private AnchorPane loginAnchorPane;
    
    UserInce userInce = UserInce.getInstance();

	@FXML
	void OnClickLoginButton(ActionEvent event) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				alertError.setText("Không thể kết nối. Vui lòng thử lại sau");
			}
			String sql = "SELECT * from user WHERE username = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userName.getText());
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				if (userName.getText().isEmpty() || userPassword.getText().isEmpty()) {
					alertError.setText("Hãy điền đẩy đủ thông tin");
				} else {
					alertError.setText("Không tìm thấy tài khoản");
				}
			} else {
				while (resultSet.next()) {
					String retrieveduserName = resultSet.getString("username");
					String retrieveduserPassword = resultSet.getString("userpassword");
					if (retrieveduserPassword.equals(userPassword.getText())) { // Login succesfully
						String retrieveduserId = resultSet.getString("userid");
						String retrieveduserEmail = resultSet.getString("useremail");
						////////////////////////////////////
						userInce.setUsername(retrieveduserName);
						userInce.setUserid(Long.parseLong(retrieveduserId));
						userInce.setUserpassword(retrieveduserPassword);
						userInce.setUseremail(retrieveduserEmail);
						/////////////////////////////////////
						/*
						FXMLLoader loader = new FXMLLoader();
				        loader.setLocation(getClass().getResource("/views/HomePage.fxml"));
				        Parent root = loader.load();
				        Scene scene = new Scene(root);
						
						Stage stage = (Stage)userName.getScene().getWindow();
					    stage.setScene(scene);
					    stage.show();
						*/
						new ChangeScene(loginAnchorPane, "/views/HomePage.fxml");
					} else {
						alertError.setText("Mật khẩu sai");
					}
				}
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}

	@FXML
	void OnClickForgetPasswordButton(ActionEvent event) throws IOException {
        new ChangeScene(loginAnchorPane, "/views/ForgetPasswordFXML.fxml");
	}
	
	@FXML
    void OnClicksignupButton(ActionEvent event) throws IOException {
        new ChangeScene(loginAnchorPane, "/views/SignUpFXML.fxml");
    }
}
