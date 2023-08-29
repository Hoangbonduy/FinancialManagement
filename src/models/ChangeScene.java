package models;

import java.io.IOException;
import java.util.Objects;

import application.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ChangeScene {
  public ChangeScene(AnchorPane currentAnchorPane,String fxmlFile) throws IOException {
	  AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(fxmlFile)));
	  currentAnchorPane.getChildren().removeAll();
	  currentAnchorPane.getChildren().setAll(nextAnchorPane);
  }
}
