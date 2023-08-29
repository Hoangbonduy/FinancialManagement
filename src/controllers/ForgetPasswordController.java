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

public class ForgetPasswordController {
	@FXML
    private Button passwordResetButton;

    @FXML
    private TextField passwordResetEmail;

    @FXML
    private Label passwordResetError;
    
    @FXML
    private Button backtoLoginButton;
    
    @FXML
    private AnchorPane forgetPasswordAnchorPane;


    @FXML
    void OnClickpasswordResetButton(ActionEvent event) throws SQLException {
    	Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				passwordResetError.setText("Không thể kết nối. Vui lòng thử lại sau");
			}
			String sql = "SELECT * from user WHERE useremail = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, passwordResetEmail.getText());
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				if (passwordResetEmail.getText().isEmpty()) {
					passwordResetError.setText("Hãy điền đẩy đủ thông tin");
				} else {
					passwordResetError.setText("Không tìm thấy tài khoản");
				}
			} else {
				while (resultSet.next()) {
					String retirieveduserEmail = resultSet.getString("useremail");
					if (retirieveduserEmail.equals(passwordResetEmail.getText())) {
						String retirieveduserPassword = resultSet.getString("userpassword");
						passwordResetError.setText("Mật khẩu của bạn là: " + retirieveduserPassword + ". Hãy về trang đăng nhập để tiếp tục");
					}
					else {
						passwordResetError.setText("Sai mật khẩu");
					}
				}
			}
		} catch (Exception e) {
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
    void OnClickbacktoLoginButton(ActionEvent event) throws IOException {
        new ChangeScene(forgetPasswordAnchorPane,"/views/Login.fxml");
    }
}
