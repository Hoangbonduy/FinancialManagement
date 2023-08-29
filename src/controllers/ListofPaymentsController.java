package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Predicate;

import javaUtils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Payment;
import models.UserInce;

public class ListofPaymentsController implements Initializable {

	@FXML
	private Button addPayment_ListofPayments;

	@FXML
	private Label alertListofPayments;

	@FXML
	private Button backtoHomePage_ListofPayments;

	@FXML
	private TableColumn<Payment, String> dateColumn;

	@FXML
	private Button deletePayment_ListofPayments;

	@FXML
	private TableView<Payment> listofPaymentsTable;

	@FXML
	private TableColumn<Payment, String> editColumn;

	@FXML
	private TableColumn<Payment, Long> idColumn;

	@FXML
	private AnchorPane listofpaymentsAnchorPane;

	@FXML
	private TableColumn<Payment, Long> moneyColumn;

	@FXML
	private TableColumn<Payment, String> nameColumn;

	@FXML
	private TableColumn<Payment, String> noteColumn;

	@FXML
	private Button statistics_ListofPayments;

	@FXML
	private TableColumn<Payment, String> typeColumn;
	
	@FXML
	private TextField keywordSearch;

	private ObservableList<Payment> list;
	
	UserInce userInce = UserInce.getInstance();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
    	try {
			LoadTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void OnClickaddPayment_ListofPayments(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/AddPayment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
        
        Stage stage = (Stage)addPayment_ListofPayments.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
	}

	@FXML
	void OnClickbacktoHomePage_ListofPayments(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/HomePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
        
        Stage stage = (Stage)addPayment_ListofPayments.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
	}

	@FXML
	void OnClickdeletePayment_ListofPayments(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/DeletePayment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
        
        Stage stage = (Stage)addPayment_ListofPayments.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
	}

	@FXML
	void OnClickstatistics_ListofPayments(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/Statistics.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
        
        Stage stage = (Stage)statistics_ListofPayments.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
	}
    
	private void LoadTable() throws SQLException {
		Connection connection = null;
    	PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
    	try {
    		list = FXCollections.observableArrayList();
    		String sql = "SELECT * FROM " + userInce.getUsername();
    		connection = DBConnection.getConnection();
    		preparedStatement = connection.prepareStatement(sql);
    		resultSet = preparedStatement.executeQuery();
    		
    		while (resultSet.next()) {
    			Long idpaymentLong = resultSet.getLong("idpayment");
    			String namepaymentString = resultSet.getString("namepayment");
    			Long moneypaymentLong = resultSet.getLong("moneypayment");
    			String datepaymentString = resultSet.getString("datepayment");
    			String typepaymentString = resultSet.getString("typepayment");
    			String notepaymentString = resultSet.getString("notepayment");
    			Payment payment = new Payment(idpaymentLong,namepaymentString, moneypaymentLong, datepaymentString, typepaymentString, notepaymentString);
    			list.add(payment);
    		}
    		
    		idColumn.setCellValueFactory(new PropertyValueFactory<>("idpayment"));
    		nameColumn.setCellValueFactory(new PropertyValueFactory<>("namepayment"));
    		moneyColumn.setCellValueFactory(new PropertyValueFactory<>("moneypayment"));
    		dateColumn.setCellValueFactory(new PropertyValueFactory<>("datepayment"));
    		typeColumn.setCellValueFactory(new PropertyValueFactory<>("typepayment"));
    		noteColumn.setCellValueFactory(new PropertyValueFactory<>("notepayment"));
    		
    		Callback<TableColumn<Payment, String>, TableCell<Payment, String>> cellFactory = (param) -> {
    			final TableCell<Payment, String> cell = new TableCell<Payment, String>() {
    				@Override
    				public void updateItem(String item,boolean empty) {
    					super.updateItem(sql, empty);
    					
    					if (empty) {
    						setGraphic(null);
    						setText(null);
    					} else {
    						Button editButton = new Button("Sửa");
    						Button deleteButton = new Button("Xóa");
    						
    						editButton.setOnAction(event -> {
    							try {
									Payment payment = getTableView().getItems().get(getIndex());
									FXMLLoader loader = new FXMLLoader();
									loader.setLocation(getClass().getResource("/views/ModifyPayment.fxml"));
									Parent root = loader.load();
									Scene scene = new Scene(root);
									
									ModifyPaymentController modifyPaymentController = loader.getController();
									modifyPaymentController.SendDataPayment(payment);
									modifyPaymentController.DisplayData(payment.getNamepayment(), payment.getMoneypayment(), payment.getDatepayment(), payment.getNotepayment(), payment.getTypepayment());
									
									Stage stage = (Stage)editButton.getScene().getWindow();
									stage.setScene(scene);
								    stage.show();
								} catch (IOException e) {
									e.printStackTrace();
								}
    						});
    						
    						deleteButton.setOnAction(event -> {
    							try {
    								Payment payment = getTableView().getItems().get(getIndex());
    								String sqlString = "DELETE from " + userInce.getUsername() + " where idpayment = ?";
    								Connection connection = DBConnection.getConnection();
    								PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
    								preparedStatement.setLong(1, payment.getIdpayment());
    								preparedStatement.executeUpdate();
    								LoadTable();
								} catch (Exception e) {
									e.printStackTrace();
								}
    						});
    						
    						HBox manageButtonHBox = new HBox(editButton,deleteButton);
    						HBox.setMargin(deleteButton, new Insets(2, 2, 0, 3));
                            HBox.setMargin(editButton, new Insets(2, 3, 0, 2));
    						
                            setGraphic(manageButtonHBox);
    						setText(null);
    					}
    				}
    			};
    			
    			return cell;
    		};
    		
    		editColumn.setCellFactory(cellFactory);
    		
    		listofPaymentsTable.setItems(list);
    		
    		FilteredList<Payment> filteredList = new FilteredList<>(list, b -> true);
    		
    		keywordSearch.textProperty().addListener((observable,oldvalue,newvalue) -> {
    			filteredList.setPredicate(payment -> {
    				// No changes
    				if (newvalue.isEmpty() || newvalue.isBlank() || newvalue == null) {
    					return true;
    				}
    				
    				String searchKeywordString = newvalue.toLowerCase();
    				if (payment.getIdpayment().toString().indexOf(searchKeywordString) > -1) {
    					return true; // found
    				} else if (payment.getNamepayment().indexOf(searchKeywordString) > -1) {
    					return true;
    				} else if (payment.getMoneypayment().toString().indexOf(searchKeywordString) > -1) {
    					return true;
    				} else if (payment.getDatepayment().indexOf(searchKeywordString) > -1) {
						return true;
					} else if (payment.getTypepayment().indexOf(searchKeywordString) > -1) {
						return true;
					} else if (payment.getNotepayment().indexOf(searchKeywordString) > -1) {
						return true;
					} else {
						return false; // not found
					}
    			});
    		});
    		
    		SortedList<Payment> sortedList = new SortedList<>(filteredList);
    		
    		sortedList.comparatorProperty().bind(listofPaymentsTable.comparatorProperty());
    		
    		//sorted
    		listofPaymentsTable.setItems(sortedList);
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
