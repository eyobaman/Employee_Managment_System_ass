package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class employeeController implements Initializable {

    @FXML
    private FontAwesomeIcon employeeHeaderClose;

    @FXML
    private Button employeeHomebtn;

    @FXML
    private FontAwesomeIcon employeeHomelogoutbtn;

    @FXML
    private TextField employeeHometxt;

    @FXML
    private Button employeeInformationbtn;
    
  
    
    @FXML
    private TextField lastNametxt;
    
    @FXML
    private TextField Usernametxt;
    
    @FXML
    private TextField positiontxt;

    @FXML
    private Button homeAbsentLabel;

    @FXML
    private Label homeCountlabel;
    
    @FXML
    private Button close;
    
    @FXML
    private TextField firstNametxt;

    @FXML
    private AnchorPane homeForm;

    @FXML
    private Button homePresentbtn;
    
    @FXML
    private Label label_welcome;

    @FXML
    private TextField informaitonHome_Question;

    @FXML
    private AnchorPane informationForm;

    @FXML
    private TextField informationHome_answer;

    @FXML
    private ComboBox<?> informationHome_choose;

    @FXML
    private Button informationHome_databtn;

    @FXML
    private TextField informationHome_email;

    @FXML
    private TextField informationHome_questionAnswer;
    
    
    static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
   	static final String DB_URL = "jdbc:mysql://localhost/employee";
   	static final String DB_USER = "root";
   	static final String DB_PASS = "";
   	
   	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	private java.sql.Statement statement;
	
	
public Connection connectDB(){
		
		try {
			
			Class.forName(DB_DRIVER);
			Connection connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			return connect;
		}catch(Exception e){
			e.addSuppressed(e);
			
			
		}
		return null;
	};
	
	
	public String username() {
     String data = getData.empUsername;
     return data;
		
	}
	
		
		
		
	
	public void fetchData(String username) {
	    try {
	        Connection connection = connectDB();

	        // Get the question and answer data from the emp_info table for the given username
	        String query = "SELECT username, email, question, answer FROM emp_info WHERE username = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, username);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        // Assign the fetched data to the private string variables
	        if (resultSet.next()) {
	            this.username = resultSet.getString("username");
	            email = resultSet.getString("email");
	            question = resultSet.getString("question");
	            answer = resultSet.getString("answer");
	        }

	        preparedStatement.close();
	        connection.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
    private String email;
	
	private String question;
	private String answer;
	
	public void assignValue() {
		
		Usernametxt.setText(username);
		informationHome_email.setText(email);
		informaitonHome_Question.setText(question);
		informationHome_questionAnswer.setText(answer);
		
		
	}

public void switchForm(ActionEvent event) {
	
	if(event.getSource() == employeeHomebtn) {
		homeForm.setVisible(true);
		informationForm.setVisible(false);
		
	}else if(event.getSource() == employeeInformationbtn) {
		homeForm.setVisible(false);
		informationForm.setVisible(true);
	}
};

public String username;






public void present() {
    LocalDate currentDate = LocalDate.now();
    try {
        Connection connection = connectDB();
        String selectSql = "SELECT COUNT(*) as present_count FROM attendance WHERE username = ?";
        prepare = connection.prepareStatement(selectSql);
        prepare.setString(1, username);
        result = prepare.executeQuery();
        if (!result.next() || result.getInt("present_count") == 0) {
            String insertSql = "INSERT INTO attendance (username, date, present) VALUES (?, ?, ?)";
            prepare = connection.prepareStatement(insertSql);
            prepare.setString(1, username);
            prepare.setDate(2, Date.valueOf(currentDate));
            prepare.setString(3, "true");
            prepare.executeUpdate();
        }
        String countSql = "SELECT COUNT(*) as present_count FROM attendance WHERE username = ? AND date = ? AND present = ?";
        prepare = connection.prepareStatement(countSql);
        prepare.setString(1, username);
        prepare.setDate(2, Date.valueOf(currentDate));
        prepare.setString(3, "true");
        result = prepare.executeQuery();
        if (result.next()) {
        	String present;
            present = result.getString("present_count");
            homeCountlabel.setText(present);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (result != null) {
                result.close();
            }
            if (prepare != null) {
                prepare.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
};



public void absent() {
    LocalDate currentDate = LocalDate.now();
    try {
        Connection connection = connectDB();
        String selectSql = "SELECT COUNT(*) as absent_count FROM attendance WHERE username = ?";
        prepare = connection.prepareStatement(selectSql);
        prepare.setString(1, username);
        result = prepare.executeQuery();
        if (!result.next() || result.getInt("absent_count") == 0) {
            String insertSql = "INSERT INTO attendance (username, date, present) VALUES (?, ?, ?)";
            prepare = connection.prepareStatement(insertSql);
            prepare.setString(1, username);
            prepare.setDate(2, Date.valueOf(currentDate));
            prepare.setString(3, "false");
            prepare.executeUpdate();
        }
        String countSql = "SELECT COUNT(*) as absent_count FROM attendance WHERE username = ? AND date = ? AND present = ?";
        prepare = connection.prepareStatement(countSql);
        prepare.setString(1, username);
        prepare.setDate(2, Date.valueOf(currentDate));
        prepare.setString(3, "false");
        result = prepare.executeQuery();
        if (result.next()) {
        	String absent;
            absent = result.getString("absent_count");
            homeCountlabel.setText(absent);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (result != null) {
                result.close();
            }
            if (prepare != null) {
                prepare.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
};

public void back() {
	changeScene("Signin.fxml");
};

public void changeScene(String fxmlFileName) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFileName));
        Scene scene = new Scene(root);
        Stage stage = (Stage) employeeHomelogoutbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
};

public void close(){
    System.exit(0);
};


public void update() {
	alertMessage alert = new alertMessage();
    try {
        Connection connection = connectDB();

        // Get the selected question and entered answer
        String selectedQuestion = (String) informationHome_choose.getSelectionModel().getSelectedItem();
        String enteredAnswer = informationHome_answer.getText();

        // Disable the button if either the question or answer is empty
        if (selectedQuestion == null || selectedQuestion.isEmpty() || enteredAnswer.isEmpty()) {
            informationHome_databtn.setDisable(true);
            return;
        } else {
            informationHome_databtn.setDisable(false);
        }

        // Update the question and answer columns of the emp_info table for the current username
        String query = "UPDATE emp_info SET question = ?, answer = ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, selectedQuestion);
        preparedStatement.setString(2, enteredAnswer);
        preparedStatement.setString(3, username);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
        assignValue();
        alert.successMessage("Data updated Successfully!");
       
        assignValue();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void changLabel() {
	label_welcome.setText("Welcome " + username);
}
private String[] questionList = {"What is your Favourite Food?","What is your favourite color","What is the name of your pet?"};
public void questions(){
		
    List<String> ListQ  =new ArrayList<>();
    
    for(String data :questionList) {
    	ListQ.add(data);
    }
    
    ObservableList ListData = FXCollections.observableArrayList(ListQ);
    informationHome_choose.setItems(ListData);
	}
    
    @Override
	public void initialize(URL url, ResourceBundle rb) {
		
//    	setName();
    	username = username();
    	fetchData(username);
    	assignValue();
    	questions();
    	
    	changLabel();
    	
    	
	}

}
