package finalProject;
//Jake Sencenbaugh

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.text.PasswordView;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

//GUI Buttons:
//back - goes to previous scene
//home - goes to home scene
//help - calls for employee to help customer
//submit - loads info for next scene
public class HomeDisplay extends Application{
	GenInfo fakeAI = new GenInfo();
	GarageDatabase garage = new GarageDatabase();
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene;
		BorderPane homePane = new BorderPane();
		//---Home Pane--
		HBox buttonBox = new HBox();
		Button dropOff = new Button("DROP OFF");
		Button pickUp = new Button("PICK UP");
		
		dropOff.setPrefWidth(200);
		dropOff.setPrefHeight(100);
		pickUp.setPrefWidth(200);
		pickUp.setPrefHeight(100);
		
		buttonBox.getChildren().addAll(dropOff,pickUp);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(10);
		
		
		Button employee = new Button("E");
		Button help = new Button("Help");
		employee.setFont(new Font(40));
		
		homePane.setCenter(buttonBox);
		homePane.setTop(employee);
		homePane.setBottom(help);
		
		BorderPane.setAlignment(employee, Pos.CENTER);
		BorderPane.setAlignment(help, Pos.CENTER);
		
		scene = new Scene(homePane, 500,300);
		primaryStage.setTitle("Home Screen");
		primaryStage.setScene(scene);
		primaryStage.show();	
		
		dropOff.setOnAction(e -> {droppingOff(primaryStage, scene, scene);});
		pickUp.setOnAction(e -> {
			pickUp(primaryStage, scene, scene);
		});
		
		help.setOnAction(e ->{
			try {
				help.setText("Please Wait To Be Helped");
				garage.addHelp(fakeAI.getLocation());
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		employee.setOnAction(e ->{
			try {
				employeeLogin(primaryStage, scene, scene);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});

	}
	//verifies employee login info with database
	public void employeeLogin(Stage aStage, Scene aScene, Scene homeScene) throws ClassNotFoundException, SQLException
	{
		Button submit = new Button("Login");
		Button back = new Button("Back");
		Label error = new Label();
		
		TextField id = new TextField();
		PasswordField pw = new PasswordField();
		
		Label idP = new Label("ID: ");
		Label pwP = new Label("Password: ");
		
		GridPane pane = new GridPane();
		pane.add(idP, 0, 0);
		pane.add(id, 1, 0);
		pane.add(pwP, 0, 1);
		pane.add(pw, 1, 1);
		pane.add(back, 0, 2);
		pane.add(submit, 1, 2);
		pane.add(error, 1, 4);
		pane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(pane, 500, 300);
		
		aStage.setScene(scene);
		
		submit.setOnAction(e ->{
			try {
				if(garage.hasEmployee(id.getText(), pw.getText()))
				{
					displayEmployee(aStage, scene, homeScene);
				}else {
					error.setText("*Incorrect password or ID");
				}
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		back.setOnAction(e -> {
			aStage.setScene(homeScene);
		});
	}
	
	//Displays employee menu page
	//able to see all parking garage info
	public void displayEmployee(Stage aStage, Scene aScene, Scene homeScene) throws ClassNotFoundException, SQLException
	{
		ScrollPane sPane = new ScrollPane();
		VBox pane = new VBox();
		GridPane helpPane = garage.viewHelp();
		helpPane.setPadding(new Insets(3, 5, 3, 5));
		helpPane.setVisible(false);
		helpPane.setAlignment(Pos.CENTER);
		helpPane.setHgap(6);
		helpPane.setVgap(3);
		GridPane custPane = garage.viewCust();
		custPane.setAlignment(Pos.CENTER);
		custPane.setPadding(new Insets(3, 5, 3, 5));
		custPane.setVisible(false);
		custPane.setHgap(6);
		custPane.setVgap(3);
		Button viewH = new Button("View Help");
		Button viewC = new Button("View Customers");
		Button hide = new Button("Hide Table");

		hide.setVisible(false);
		Button home = new Button("HOME");
		
		pane.getChildren().addAll(viewH, viewC, home,hide, helpPane, custPane);
		pane.setAlignment(Pos.CENTER);
		pane.setAlignment(Pos.CENTER);
		sPane.setContent(pane);
		Scene scene = new Scene(sPane, 500, 300);
		aStage.setScene(scene);
		
		viewH.setOnAction(e -> {
			helpPane.setVisible(true);
			custPane.setVisible(false);
			hide.setVisible(true);
		});
		
		viewC.setOnAction(e -> {
			helpPane.setVisible(false);
			custPane.setVisible(true);
			hide.setVisible(true);
		});
		
		hide.setOnAction(e -> {
			helpPane.setVisible(false);
			custPane.setVisible(false);
		});
		
		home.setOnAction(e -> {
			aStage.setScene(homeScene);
		});
		
		
		
		
		
	}
	
	
	//starting pickup --------------------------------------------------------------------------
	public void pickUp(Stage aStage, Scene aScene, Scene homeScene)
	{
		Button login = new Button("Login");
		Button back = new Button("Go Back");
		
		PasswordField pwd = new PasswordField();
		TextField license = new TextField();
		Label lPrompt = new Label("Enter License Plate Number: ");
		Label pPrompt = new Label("Enter Password: ");
		Label error = new Label();
		
		GridPane pane = new GridPane();
		
		pane.setAlignment(Pos.CENTER);
		pane.add(lPrompt, 0, 0);
		pane.add(license, 1, 0);
		pane.add(pPrompt, 0, 1);
		pane.add(pwd, 1,	1);
		pane.add(login, 0, 2);
		pane.add(back, 1, 2);
		pane.add(error, 1, 4);
		
		Scene scene = new Scene(pane, 500, 300);
		aStage.setScene(scene);
		
		back.setOnAction(e -> {
			aStage.setScene(aScene);
		});
		
		login.setOnAction(e -> {
			try {
				if(garage.hasCustomer(license.getText(), pwd.getText()))
				{
					seeFees(aStage, scene, homeScene, fakeAI.getFees(license.getText(), pwd.getText(), garage), license.getText(), pwd.getText());
				}else {
					error.setText("* Vehicle Not Found | Please Try Again");
				}
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	//Displays fees to user
	public void seeFees(Stage aStage, Scene aScene, Scene homeScene, ArrayList<Object> list, String plate, String pwd)
	{
		Label cPrompt = new Label("Total Cost: $" +list.get(2)); // calculate total cost;
		Label fees = new Label("FEES");
		Label wPrompt = new Label("Weight: " + list.get(0) + " lbs"); // Calculate weight fee
		Label tfPrompt = new Label("Time: " + list.get(1) + " hrs"); //Calculate time fee
		Button back = new Button("Go Back");
		
		Button help = new Button("Help");
		Button pay = new Button("Pay/Pickup Vehicle");
		
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(10));
		pane.add(cPrompt, 0, 0);
		pane.add(fees, 0, 2);
		pane.add(wPrompt, 0, 3);
		pane.add(tfPrompt, 0, 4);
		pane.add(back, 0, 5);
		pane.add(help, 1, 2);
		pane.add(pay, 1, 5);
		pane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(pane, 500, 300);
		aStage.setScene(scene);
		
		back.setOnAction(e -> {
			aStage.setScene(aScene);
		});
		
		pay.setOnAction(e -> {
			payment(aStage, scene, homeScene, plate, pwd);
		});
		
		help.setOnAction(e ->{
			try {
				help.setText("Please Wait To Be Helped");
				garage.addHelp(fakeAI.getLocation());
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	//Allows user to pay with the use of a credit card and phone number
	public void payment(Stage aStage, Scene aScene, Scene homeScene, String plate, String pwd)
	{
		Button back = new Button("Go Back");
		Button pay = new Button("Pay");
		
		Label creditPrompt = new Label("Creditcard Number: ");
		TextField credit = new TextField();
		credit.setPromptText("1234567890123456");
		
		Label phonePrompt = new Label("Phone Number: ");
		TextField phone = new TextField();
		phone.setPromptText("1234567890");
		
		Label warning = new Label();
		
		GridPane pane = new GridPane();
		pane.add(creditPrompt, 0, 0);
		pane.add(credit, 1, 0);
		pane.add(phonePrompt, 0, 1);
		pane.add(phone, 1, 1);
		pane.add(pay, 0, 2);
		pane.add(back, 1, 2);
		pane.add(warning, 1, 5);
		pane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(pane, 500,300);
		aStage.setScene(scene);
		
		pay.setOnAction(e -> {
			if((phone.getText().matches("[0-9]+") && credit.getText().matches("[0-9]+")))
			{
				if((credit.getText().length() == 16) && (phone.getText().length() == 10))
				{
					//Upload payment to database
					try {
						garage.removeCustomer(plate, pwd);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					aStage.setScene(homeScene);
				}else {
					warning.setText("* Creditcard must be 16 digits and phone number must be 10");
				}
				
			}else {
				warning.setText("* Creditcard and phone number must be numbers only.");
			}
		});
		
		back.setOnAction(e -> {
			aStage.setScene(aScene);
		});
		
	}
	
	// dropping off start----------------------------------------------------------------------
	public void droppingOff(Stage aStage, Scene aScene, Scene homeScene)
	{
		//---DropOffPane---
		VBox dropPane = new VBox();
		HBox buttons = new HBox();
		HBox nextButton = new HBox();
		Button yesButton = new Button("YES");
		Button noButton = new Button("NO");
		Button gButton = new Button("Go Back");
		Text question = new Text("Is this your license plate #?");
		Text license = new Text(fakeAI.getPlate());
		Label question2 = new Label("Enter Manually:");
		TextField field = new TextField();
		field.setMaxWidth(80);
		Button submitButton = new Button("Submit");
		license.setFont(new Font(50));
		buttons.getChildren().addAll(yesButton,noButton);
		buttons.setSpacing(10);
		nextButton.getChildren().addAll(submitButton, gButton);
		nextButton.setAlignment(Pos.CENTER);
		nextButton.setSpacing(10);
		dropPane.getChildren().addAll(question,license, buttons,question2, field, nextButton);
		dropPane.setSpacing(20);
		dropPane.setAlignment(Pos.CENTER);
		buttons.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(dropPane, 500,300);
		aStage.setScene(scene);
		noButton.setOnAction(e -> {
			droppingOff(aStage, aScene, homeScene);
		});
		
		yesButton.setOnAction(e -> {
			correctLicense(aStage, scene ,license.getText(), homeScene);
		});
		
		submitButton.setOnAction(e -> {
			if(field.getLength() != 0) {
				correctLicense(aStage, scene ,field.getText(), homeScene);
			}else {
				// replay current scene
			}
		});
		gButton.setOnAction(e -> {
			aStage.setScene(aScene);
		});
		

	}
	
	
	// Uses fakeAI to scan license plate or user can enter manually
	public void correctLicense(Stage aStage, Scene aScene,String aLicense, Scene homeScene)
	{
		PasswordField pwd1 = new PasswordField();
		PasswordField pwd2 = new PasswordField();
		Label pwd1Text = new Label("Create your password: ");
		Label pwd2Text = new Label("Enter password again: ");
		Label errorText = new Label();

		pwd2.setMaxWidth(100);
		pwd1.setMaxWidth(100);
		
		Button cButton = new Button("Go Back");
		Button sButton = new Button("Submit");
		
		VBox mainPane = new VBox();
		mainPane.setSpacing(10);
		HBox others = new HBox();
		HBox pwd1Pane = new HBox();
		HBox pwd2Pane = new HBox();
		
		pwd1Pane.getChildren().addAll(pwd1Text, pwd1);
		pwd2Pane.getChildren().addAll(pwd2Text, pwd2);
		pwd1Pane.setAlignment(Pos.CENTER);
		pwd2Pane.setAlignment(Pos.CENTER);

		others.setAlignment(Pos.CENTER);
		others.setSpacing(10);
		others.getChildren().addAll(cButton, sButton);
		mainPane.getChildren().addAll(pwd1Pane,pwd2Pane,others,errorText);
		mainPane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(mainPane, 500,300);
		aStage.setScene(scene);
		
		sButton.setOnAction(e -> {
			if((pwd1.getText().equals(pwd2.getText())) && (pwd1.getLength() > 6))
			{
				passwordsMatch(aStage,scene,aLicense,pwd1.getText(), homeScene);
			}else {
				errorText.setText("*Passwords must match and be greater than 6 characters");
			}
		});
		cButton.setOnAction(e -> {
			aStage.setScene(aScene);
		});
		
	}
	
	//Checks to see if users passwords match 
	public void passwordsMatch(Stage aStage, Scene aScene, String aLicense, String aPassword, Scene homeScene) 
	{
		Date theDate = new Date();
		long timeMilli = theDate.getTime();
		GridPane pane = new GridPane();
		Button sButton = new Button("Submit");
		Button cButton = new Button("Go Back");
		Button help = new Button("Help");
		Label info = new Label("FINAL INFO");
		Label password = new Label("Password: ");
		Label license = new Label("License: ");
		Label date = new Label("Date/Time: ");
		Label licenseInfo = new Label(aLicense);
		Label passwordInfo = new Label(aPassword.substring(0,1) + "***********");
		Label dateInfo = new Label(Long.toString(timeMilli));
		
		pane.add(info, 1, 0);
		pane.add(license, 0, 1);
		pane.add(licenseInfo, 2, 1);
		pane.add(password, 0, 2);
		pane.add(passwordInfo, 2, 2);
		pane.add(date, 0, 3);
		pane.add(dateInfo, 2, 3);
		pane.add(sButton, 0, 4);
		pane.add(help, 1, 4);
		pane.add(cButton, 2, 4);
		pane.setAlignment(Pos.CENTER);
		GridPane.setHalignment(help, HPos.CENTER);
		GridPane.setHalignment(info, HPos.CENTER);
		GridPane.setHalignment(sButton, HPos.CENTER);
		
		Scene scene = new Scene(pane, 500, 300);
		aStage.setScene(scene);
		
		sButton.setOnAction(e -> {
			Customer customer = new Customer(aLicense, aPassword, "customer", fakeAI.getWeight(), Long.toString(timeMilli), fakeAI.getLocation(), false);
			try {
				garage.addCustomer(customer);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			carSafe(aStage, scene, homeScene);
		});
		
		cButton.setOnAction(e -> {
			aStage.setScene(aScene);
		});
		
		help.setOnAction(e ->{
			try {
				help.setText("Please Wait To Be Helped");
				garage.addHelp(fakeAI.getLocation());
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	// uses fake ai to see if car is safe to be taken away by autonomous platform
	public void carSafe(Stage aStage, Scene aScene, Scene homeScene)
	{
		Text isSafe = new Text("Please make vehicle safe and exit. Press 'Safe' when ready.");
		Button safe = new Button("Safe");
		Text warning = new Text();
		safe.setPrefHeight(80);
		safe.setPrefWidth(120);
		safe.maxHeight(80);
		safe.maxWidth(120);
		Button help = new Button("Help");
		VBox pane = new VBox();
		pane.getChildren().addAll(isSafe, safe, help, warning);
		pane.setAlignment(Pos.CENTER);
		pane.setSpacing(10);
		Scene scene = new Scene(pane,500,300);
		aStage.setScene(scene);
		help.setOnAction(e -> {
			//Help Me
		});
		
		safe.setOnAction( e -> {
			if(fakeAI.isSafe())
				aStage.setScene(homeScene);
			else
				warning.setText("* Please Make Vehicle Safe");
		});
		
		help.setOnAction(e ->{
			try {
				help.setText("Please Wait To Be Helped");
				garage.addHelp(fakeAI.getLocation());
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		
	}

}
