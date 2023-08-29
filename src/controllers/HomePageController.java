package controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.security.PublicKey;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.ChangeScene;
import models.UserInce;

public class HomePageController implements Initializable{

    @FXML
    private Button addPayment;

    @FXML
    private Button deletePayment;

    @FXML
    private AnchorPane homepageAnchorPane;

    @FXML
    private Button seelistofPayments;

    @FXML
    private Button statistics;

    @FXML
    private Label useridHomePage;

    @FXML
    private Label usernameHomePage; 
    
    @FXML
    private Button logOut;
    
    UserInce userInce = UserInce.getInstance();
    
    @FXML
    void OnClickaddPayment(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/AddPayment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage)addPayment.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
    }

    @FXML
    void OnClickdeletePayment(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/DeletePayment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
        
        Stage stage = (Stage)addPayment.getScene().getWindow();
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
       
        Stage stage = (Stage)usernameHomePage.getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
    }

    @FXML
    void OnClickstatistics(ActionEvent event) throws IOException {
        new ChangeScene(homepageAnchorPane, "/views/Statistics.fxml");
    }
    
    @FXML
    void OnClicklogOut(ActionEvent event) throws IOException {
        new ChangeScene(homepageAnchorPane, "/views/Login.fxml");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameHomePage.setText(userInce.getUsername());
		useridHomePage.setText("Id: " + userInce.getUserid());
	}
}
