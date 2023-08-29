package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javaUtils.AlertUtils;
import javaUtils.CovertUtils;
import javaUtils.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Payment;
import models.UserInce;

public class ModifyPaymentController implements Initializable {

	@FXML
	private DatePicker datePayment_modifyPayment;

	@FXML
	private AnchorPane modifyPaymentAnchorPane;

	@FXML
	private TextField moneyPayment_modifyPayment;

	@FXML
	private TextField namePayment_modifyPayment;

	@FXML
	private TextField notePayment_modifyPayment;

	@FXML
	private Button modifyPayment;

	@FXML
	private ChoiceBox<String> typePayment_modifyPayment;

	@FXML
	private Label modifyPaymentTitle;

	@FXML
	private Button backtoListPayment;

	private String typePaymetString[] = { "Trả", "Nhận", "Nợ" }; // pay earn debt
	
	UserInce userInce = UserInce.getInstance();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		typePayment_modifyPayment.getItems().addAll(typePaymetString);
	}

	@FXML
	void OnclickmodifyPayment(ActionEvent event) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				AlertUtils.AlertError("Không thể kêt nối");
			} else if (namePayment_modifyPayment.getText().isEmpty() || moneyPayment_modifyPayment.getText().isEmpty() || datePayment_modifyPayment.getValue() == null) {
				AlertUtils.AlertError("Vui lòng xem lại các giá trị được nhập");
				System.out.println("Er");
			} else {
				String sqlString = "UPDATE " + userInce.getUsername() + " SET namepayment = ?, moneypayment = ?, datepayment = ?, typepayment = ?, notepayment = ? WHERE idpayment = ?";
				preparedStatement = connection.prepareStatement(sqlString);
				preparedStatement.setString(1, namePayment_modifyPayment.getText());
				preparedStatement.setLong(2, Integer.parseInt(moneyPayment_modifyPayment.getText()));
				preparedStatement.setString(3, datePayment_modifyPayment.getValue().toString());
				preparedStatement.setString(4, typePayment_modifyPayment.getValue());
				preparedStatement.setString(5, notePayment_modifyPayment.getText());
				preparedStatement.setLong(6, payment.getIdpayment());
				preparedStatement.executeUpdate();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Chỉnh sửa thành công");
				alert.setContentText("Bạn có muốn tiếp tục?");

				ButtonType yesButtonType = new ButtonType("Có", ButtonBar.ButtonData.YES);
				ButtonType noButtonType = new ButtonType("Không", ButtonBar.ButtonData.NO);
				alert.getButtonTypes().setAll(yesButtonType, noButtonType);

				Optional<ButtonType> resultOptional = alert.showAndWait();
				if (resultOptional.get() == yesButtonType) {
					OnclickbacktoListPayment(event);
				}
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

	@FXML
	void OnclickbacktoListPayment(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/ListofPayments.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());


		Stage stage = (Stage) backtoListPayment.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	// Send data
	Payment payment = new Payment(null, null, null, null, null, null);

	public void SendDataPayment(Payment payment) {
		this.payment = payment;
	}

	public void DisplayData(String namePayment, Long moneyPayment, String datePayment, String notePayment,
			String typePayment) {
		namePayment_modifyPayment.setText(namePayment);
		moneyPayment_modifyPayment.setText(moneyPayment.toString());
		LocalDate date = CovertUtils.ConvertStringtoDate(datePayment);
		datePayment_modifyPayment.setValue(date);
		notePayment_modifyPayment.setText(notePayment);
		typePayment_modifyPayment.setValue(typePayment);
	}
}
