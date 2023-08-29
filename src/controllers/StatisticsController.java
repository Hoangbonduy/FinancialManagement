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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.ChangeScene;
import models.UserInce;

public class StatisticsController implements Initializable {

	@FXML
	private Label debtPayment;

	@FXML
	private Label earnPayment;

	@FXML
	private Label payPayment;

	@FXML
	private PieChart scalePaymentPieChart;

	@FXML
	private Button statistics_addPayment;

	@FXML
	private Button statistics_backtoHomePageButton;

	@FXML
	private Button statistics_deletePayment;

	@FXML
	private Button statistics_seelistofPayments;

	@FXML
	private Button statistics_statistics;

	@FXML
	private Label totalNumberPayment;

	@FXML
	private LineChart<?, ?> totalPaymentLineChart;

	@FXML
	private Label useridStatistics;

	@FXML
	private Label usernameStatistics;
	
	@FXML
	private AnchorPane statisticsAnchorPane;


	UserInce userInce = UserInce.getInstance();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*
		TranslateTransition translateTransition = new TranslateTransition();
		translateTransition.setDuration(Duration.seconds(5));
		translateTransition.setNode(totalPaymentLineChart);
		translateTransition.play();
		*/
		
		getInfo();

		try {
			LoadNumber();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void OnClickstatistics_addPayment(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/AddPayment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
        
        Stage stage = (Stage)statistics_addPayment.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
	}

	@FXML
	void OnClickstatistics_backtoHomePageButton(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/HomePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
        
        Stage stage = (Stage)statistics_backtoHomePageButton.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
	}

	@FXML
	void OnClickstatistics_deletePayment(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/DeletePayment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
        
        Stage stage = (Stage)statistics_deletePayment.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
	}

	@FXML
	void OnClickstatistics_seelistofPayments(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ListofPayments.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
        
        Stage stage = (Stage)statistics_seelistofPayments.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
	}

	public void getInfo() {
		usernameStatistics.setText(userInce.getUsername());
		useridStatistics.setText("Id: " + userInce.getUserid());
	}

	@SuppressWarnings("resource")
	public void LoadNumber() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null, preparedStatement2 = null,preparedStatement3 = null;
		ResultSet resultSet = null;
		Long payLong = null,earnLong = null,debtLong = null;
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				javaUtils.AlertUtils.AlertError("Không thể kết nối. Vui lòng thử lại sau");
			}

			String sqlPayString = "SELECT SUM(moneypayment) FROM " + userInce.getUsername() + " WHERE typepayment = ?";
			preparedStatement = connection.prepareStatement(sqlPayString);
			preparedStatement.setString(1, "Trả");
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String sum = resultSet.getString("SUM(moneypayment)");
				if (sum == null) sum = "0";
				payPayment.setText(sum);
				payLong = Long.parseLong(sum);
			}

			String sqlEarnString = "SELECT SUM(moneypayment) FROM " + userInce.getUsername() + " WHERE typepayment = ?";
			preparedStatement2 = connection.prepareStatement(sqlEarnString);
			preparedStatement2.setString(1, "Nhận");
			resultSet = preparedStatement2.executeQuery();
			if (resultSet.next()) {
				String sum = resultSet.getString("SUM(moneypayment)");
				if (sum == null) sum = "0";
				earnPayment.setText(sum);
				earnLong = Long.parseLong(sum);
			}
            
			String sqlDebtString = "SELECT SUM(moneypayment) FROM " + userInce.getUsername() + " WHERE typepayment = ?";
			preparedStatement3 = connection.prepareStatement(sqlDebtString);
			preparedStatement3.setString(1, "Nợ");
			resultSet = preparedStatement2.executeQuery();
			if (resultSet.next()) {
				String sum = resultSet.getString("SUM(moneypayment)");
				if (sum == null) sum = "0";
				debtPayment.setText(sum);
				debtLong = Long.parseLong(sum);
			}
			
			Long totalNumberPaymentLong = earnLong - payLong - debtLong;
			totalNumberPayment.setText(totalNumberPaymentLong.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (preparedStatement2 != null) {
				preparedStatement2.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement3 != null) {
				preparedStatement3.close();
			}
		}
	}
}
