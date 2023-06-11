package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double x = 0;
    private double y = 0;
    
    static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/employee";
    static final String DB_USER = "root";
    static final String DB_PASS = "";

    @Override
    public void start(Stage stage) throws Exception {
        // Establish database connection
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "SELECT * FROM admin";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                // No admin registered in database, redirect to Sample.fxml
                Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Connection Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.exit(1);
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        
        // Admin registered in database, show login screen
        Parent root = FXMLLoader.load(getClass().getResource("Signin.fxml"));

        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX(); 
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

            stage.setOpacity(.8);
        });

        root.setOnMouseReleased((MouseEvent event) ->{
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
