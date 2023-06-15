package application;

import java.net.URL;
import java.sql.*;

//


import java.util.Properties;
import java.util.Random;



import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

//
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class SigninController implements Initializable {

    @FXML
    private Button login_createAccountbtn;
    
    @FXML
    private ComboBox<?> login_choose;

    @FXML
    private Hyperlink login_forgetPassword;

    @FXML
    private AnchorPane login_form;
    
    @FXML
    private FontAwesomeIcon recover1_close;
    
    @FXML
    private FontAwesomeIcon recover2_close;
    
    @FXML
    private FontAwesomeIcon signup_close;


    @FXML
    private Button login_loginbtn;

    @FXML
    private PasswordField login_password;

    @FXML
    private CheckBox login_showPasswordbtn;

    @FXML
    private TextField login_textField;

    @FXML
    private TextField login_username;

    @FXML
    private TextField recover1_answer;
    
    @FXML
    private Button recover1_proceed1;

    @FXML
    private Button recover1_backbtn;

    @FXML
    private ComboBox<?> recover1_chooseQuestion;

    @FXML
    private TextField recover1_email;

    @FXML
    private Button recover1_proceedEmail;

    @FXML
    private Button recover1_proceedQuestionbtn;

    @FXML
    private TextField recover1_recoverycode;
    
    @FXML
    private TextField recover1_username2;

    @FXML
    private TextField recover1_username;

    @FXML
    private PasswordField recover2_confirmPassword;

    @FXML
    private Button recover2_loginbtn;

    @FXML
    private PasswordField recover2_newPassword;

    @FXML
    private Button recover2_submitbtn;

    @FXML
    private AnchorPane recoverPoneform;

    @FXML
    private AnchorPane recoverPtwoform;

    @FXML
    private TextField signup_Answer;
    
    @FXML
    private TextField signup_passwordtxt;

    @FXML
    private ComboBox<?> signup_chooseQuestion;

    @FXML
    private PasswordField signup_confirmPassword;

    @FXML
    private TextField signup_email;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private Button signup_loginbtn;
    
    @FXML
    private TextField signup_passwordtxtconfirm;

    @FXML
    private PasswordField signup_password;

    @FXML
    private CheckBox signup_showPassword;

    @FXML
    private Button signup_signupbtn;

    @FXML
    private TextField signup_username;
    
    
    // Assigning JDBC parameters, DB_DRIVER DB_URL DB_USER DB_PASS
    
    static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/employee";
	static final String DB_USER = "root";
	static final String DB_PASS = "";
    
	
	// JDBC connection parameters
	
	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	private java.sql.Statement statement;
	private String verificationCode;
	
	/*
	  emp_info table 
       user_id ------ INT
       email ------ VARCHAR (String)
       username ------ VARCHAR (String)
       password ------ VARCHAR (String)
       question ------ VARCHAR (String)
       answer ------ VARCHAR (String)
       date ------ TimeStamp
       update_date ------ Date
     */
	
	
	public Connection connectDB(){
		
		try {
			
			Class.forName(DB_DRIVER);
			Connection connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			return connect;
		}catch(Exception e){
			e.addSuppressed(e);
			
			
		}
		return null;
	} 
    
	
	
	
	public void login() {
	    alertMessage alert = new alertMessage();
	    if (login_choose.getSelectionModel().isEmpty()) {
	        alert.errorMessage("Please select user type");
	        login_username.setDisable(true);
	        login_password.setDisable(true);
	    } else if (login_choose.getValue().equals("Employee")) {
	        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
	            alert.errorMessage("Please fill your username and password field!");
	        } else {
	            String selectData = "SELECT * FROM emp_info WHERE username = ? AND password = ?";
	            connect = connectDB();
	            try {
	                prepare = connect.prepareStatement(selectData);
	                prepare.setString(1, login_username.getText());
	                prepare.setString(2, login_password.getText());
	                result = prepare.executeQuery();
	                if (result.next()) {
//	                  
	                	getData.empUsername = login_username.getText(); 
	                	changeScene("employee.fxml");
//	                    changeScene2("employee.fxml",login_username.getText());
	                    
	                } else {
	                    alert.errorMessage("Incorrect username or password");
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    } else if (login_choose.getValue().equals("Admin")) {
	        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
	            alert.errorMessage("Please fill all blank fields");
	        } else {
	            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
	            connect = connectDB();
	            try {
	                prepare = connect.prepareStatement(sql);
	                prepare.setString(1, login_username.getText());
	                prepare.setString(2, login_password.getText());
	                result = prepare.executeQuery();
	                if (result.next()) {
	                    getData.username = login_username.getText();
	                    alert.successMessage("Successfully Login");
	                    changeScene("dashboard.fxml");
	                } else {
	                    alert.errorMessage("Wrong Username/Password");
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	};
	

public void changeScene2(String fxml, String name) {
	    try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource(fxml));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        employeeController employeeController = loader.getController();
	        employeeController.username = name;
	        Stage stage = (Stage) login_loginbtn.getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}



public void changeScene(String fxmlFileName) {
	    try {
	        Parent root = FXMLLoader.load(getClass().getResource(fxmlFileName));
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) login_loginbtn.getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
};
	

	
	
	
	public void showPassword(){
		
		if(login_showPasswordbtn.isSelected()) {
			login_textField.setVisible(true);
			login_textField.setText(login_password.getText());
			
			login_password.setVisible(false);
			
		}else {
			login_textField.setVisible(false);
			login_password.setVisible(true);
			
		}
	}
	
public void showPassword2(){
		
		if(signup_showPassword.isSelected()) {
			signup_passwordtxt.setVisible(true);
			signup_passwordtxt.setText(signup_password.getText());
			signup_password.setVisible(false);
			
			signup_passwordtxtconfirm.setVisible(true);
			signup_passwordtxtconfirm.setText(signup_confirmPassword.getText());
			signup_confirmPassword.setVisible(false);
			
		}else {
			signup_passwordtxt.setVisible(false);
			signup_password.setVisible(true);
			signup_confirmPassword.setVisible(true);
			signup_passwordtxtconfirm.setVisible(false);
			
			
		}
	}
	
public void forgetPassword() {
    alertMessage alert = new alertMessage();
    String username = recover1_username.getText();
    String question = (String) recover1_chooseQuestion.getSelectionModel().getSelectedItem();
    String answer = recover1_answer.getText();

    if (username.isEmpty() || question == null || answer.isEmpty()) {
        alert.errorMessage("Please fill all the necessary fields!");
    } else {
        connect = connectDB();
        try {
            statement = connect.createStatement();

            // Check if username and answer match in database
            String checkUsername = "SELECT * FROM emp_info WHERE username = '"+ username +"' AND question = '"+ question +"' AND answer = '"+ answer +"'";
            result  = statement.executeQuery(checkUsername);

            if (result.next()) {
                // Show recoverPtwoform and hide others
            	alert.successMessage("Successful,proceed to the final stage!");
            	login_form.setVisible(false);
         		signup_form.setVisible(false);
         		recoverPoneform.setVisible(false);
         		recoverPtwoform.setVisible(true);
            } else {
                alert.errorMessage("Invalid username, question, or answer!");
            }
        } catch(Exception e) {
            e.printStackTrace();
            alert.errorMessage("Unable to recover password");
        }
    }
}

public void forgetoneList() {
	
	List<String> ListQ = new ArrayList<>();
	
	for(String data: questionList) {
		ListQ.add(data);
	}
	ObservableList ListData = FXCollections.observableArrayList(ListQ);
	recover1_chooseQuestion.setItems(ListData);
	
}
	
public void register() {
    alertMessage alert = new alertMessage();
    
    String username = signup_username.getText();
    String email = signup_email.getText();
    String password = signup_password.getText();
    String confirmPassword = signup_confirmPassword.getText();
    String question = (String) signup_chooseQuestion.getSelectionModel().getSelectedItem();
    String answer = signup_Answer.getText();

    if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
            question == null || answer.isEmpty()) {
        alert.errorMessage("Please fill all the necessary fields!");
    } else if (!password.equals(confirmPassword)) {
        alert.errorMessage("The password you entered doesn't match");
    } else if (password.length() < 8) {
        alert.errorMessage("Password must contain more than 7 characters");
    } else {
        connect = connectDB();
        try {
            statement = connect.createStatement();

            // Check if username already exists
            String checkUsername = "SELECT * FROM emp_info WHERE username = '"+ username +"'";
            result  = statement.executeQuery(checkUsername);

            if (result.next()) {
                // Update employee information
                String updateData = "UPDATE emp_info SET email=?, password=?, question=?, answer=?, date=? WHERE username=?";
                prepare = connect.prepareStatement(updateData);
                prepare.setString(1, email);
                prepare.setString(2, password);
                prepare.setString(3, question);
                prepare.setString(4, answer);

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(5, String.valueOf(sqlDate));
                prepare.setString(6, username);
                prepare.executeUpdate();
                alert.successMessage("Successfully updated employee information");
                login_form.setVisible(true);
        		signup_form.setVisible(false);
        		recoverPoneform.setVisible(false);
        		recoverPtwoform.setVisible(false);
                clear();
            } else {
                alert.errorMessage("No employee with such username is registered!");
            }
        } catch(Exception e) {
            e.printStackTrace();
            alert.errorMessage("Unable to update employee information");
        }
    }
}
	
	public void changePassword() {
	
		alertMessage alert = new alertMessage();
		
		if(recover2_newPassword.getText().isEmpty() || recover2_confirmPassword.getText().isEmpty()){
			
			alert.errorMessage("Please fill the required fields!");
		}else if(!recover2_newPassword.getText().equals(recover2_confirmPassword.getText())) {
			alert.errorMessage("The input passwords dosn't match!");
		}else if(recover2_newPassword.getText().length() < 8) {
			alert.errorMessage("Invalid Password length");
		}else {
			
			String updateData = "UPDATE emp_info SET password = ?, update_date = ? WHERE username = ?";
			connect  = connectDB();
			
			try {
				
				prepare = connect.prepareStatement(updateData);
				prepare.setString(1, recover2_newPassword.getText());
				Date date = new Date();
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				prepare.setString(2, String.valueOf(sqlDate));
				prepare.setString(3, recover1_username.getText());
				prepare.executeUpdate();
				
				alert.successMessage("Password is Sucessfuly reseted!");
				login_form.setVisible(true);
				signup_form.setVisible(false);
				recoverPoneform.setVisible(false);
				recoverPtwoform.setVisible(false);
				
				
				
			}catch(Exception e) {
				e.addSuppressed(e);
			}
		}
	
	}
	
	
	public void clear() {
		signup_username.setText(" ");
		signup_email.setText(" ");
		signup_password.setText(" ");
		signup_confirmPassword.setText(" ");
		signup_Answer.setText(" ");
		signup_chooseQuestion.getSelectionModel().clearSelection();
	}
	
	
	public void switchForm(ActionEvent event) {
		
	if(event.getSource() == login_createAccountbtn) {
		login_form.setVisible(false);
		signup_form.setVisible(true);
		recoverPoneform.setVisible(false);
		recoverPtwoform.setVisible(false);
	}else if(event.getSource() == login_forgetPassword) {
		login_form.setVisible(false);
		signup_form.setVisible(false);
		recoverPoneform.setVisible(true);
		recoverPtwoform.setVisible(false);
		forgetoneList();
	}else if(event.getSource() == signup_loginbtn) {
		login_form.setVisible(true);
		signup_form.setVisible(false);
		recoverPoneform.setVisible(false);
		recoverPtwoform.setVisible(false);
	}else if(event.getSource() == recover1_backbtn){
		login_form.setVisible(true);
		signup_form.setVisible(false);
		recoverPoneform.setVisible(false);
		recoverPtwoform.setVisible(false);
	}else if(event.getSource() == recover2_loginbtn){
		login_form.setVisible(true);
		signup_form.setVisible(false);
		recoverPoneform.setVisible(false);
		recoverPtwoform.setVisible(false);
	}
	}
	
	
	 public void close(){
	        System.exit(0);
	    }
	
	public void sendVerificationCode() throws SendFailedException {
	    alertMessage alert = new alertMessage();
	    String username = recover1_username2.getText();
	    String email = recover1_email.getText();

	    if (username.isEmpty() || email.isEmpty()) {
	        alert.errorMessage("Please fill all the necessary fields!");
	    } else {
	        connect = connectDB();
	        try {
	            statement = connect.createStatement();

	            // Check if username exists and get email address
	            String checkUsername = "SELECT email FROM emp_info WHERE username = '"+ username +"'";
	            result  = statement.executeQuery(checkUsername);

	            if (result.next()) {
	                String registeredEmail = result.getString("email");

	                if (registeredEmail.equals(email)) {
	                    // Generate and send verification code
	                    String code = generateVerificationCode();
	                    sendEmail(email, code);

						// Display success message and enable recovery code field
						alert.successMessage("Verification code sent to email");
						recover1_recoverycode.setDisable(false);

						// Save code for later comparison
						verificationCode = code;
	                } else {
	                    alert.errorMessage("The email you entered doesn't match the one registered for this username!");
	                }
	            } else {
	                alert.errorMessage("No employee with such username is registered!");
	            }
	        } catch(Exception e) {
	            e.printStackTrace();
	            alert.errorMessage("Unable to send verification code");
	        }
	    }
	}

	private String generateVerificationCode() {
	    Random random = new Random();
	    int code = random.nextInt(900000) + 100000; // Generate a random 6-digit number
	    return String.valueOf(code);
	}

	private void sendEmail(String email, String code) {
	    final String FROM_EMAIL = "yakobgirma779@gmail.com"; // Replace with your email address
	    final String FROM_PASSWORD = "jakob123go"; // Replace with your email password

	    Properties properties = new Properties();
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your email provider's SMTP server
	    properties.put("mail.smtp.port", "465"); // Replace with your email provider's SMTP port

	    Session session = Session.getInstance(properties, new Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
	        }
	    });

	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(FROM_EMAIL));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
	        message.setSubject("Password Recovery Verification Code");
	        message.setText("Your verification code is: " + code);

	        Transport.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}

	public void verifyCode() {
	    alertMessage alert = new alertMessage();
	    String code = recover1_recoverycode.getText();

	    if (code.isEmpty()) {
	        alert.errorMessage("Please enter the verification code!");
	    } else if (code.equals(verificationCode)) {
	        alert.successMessage("Verification code matched!");
	        // Show next page or perform other actions
	    } else {
	        alert.errorMessage("Verification code doesn't match!");
	    }
	}
	
	
	
	private String[] questionList = {"What is your Favourite Food?","What is your favourite color","What is the name of your pet?"};
	
	private String[] chooseType = {"Admin","Employee"};
	
	public void questionWho() {
		List<String> ListQ  =new ArrayList<>();
		for(String data:chooseType) {
			
			ListQ.add(data);
			
		}
		
		ObservableList ListData = FXCollections.observableArrayList(ListQ);
		login_choose.setItems(ListData);
		
	}
	
	
	public void questions(){
     		
    List<String> ListQ  =new ArrayList<>();
    
    for(String data :questionList) {
    	ListQ.add(data);
    }
    
    ObservableList ListData = FXCollections.observableArrayList(ListQ);
    signup_chooseQuestion.setItems(ListData);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		questions();
		forgetoneList();
		questionWho();
		recover1_recoverycode.setDisable(true);
		
	}
   









}
