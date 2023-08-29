module FinancialManagement {
	requires javafx.controls;
	requires java.sql;
	requires mysql.connector.java;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	opens controllers to javafx.graphics, javafx.fxml,java.sql;
	opens javaUtils to javafx.graphics, javafx.fxml,java.sql;
	opens models to javafx.graphics, javafx.fxml,java.sql,javafx.controls,javafx.base;
}
