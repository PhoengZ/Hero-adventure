package application;

import javafx.stage.Stage;

import SPane.StartPane;
import javafx.application.Application;
import javafx.scene.Scene;


public class Main extends Application{
	
	public void start(Stage primaryStage)  throws Exception {
		StartPane startPane = (StartPane) StartPane.getPane();
        Scene startScene = new Scene(startPane, 1280, 720);
        
        primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.setScene(startScene);
        primaryStage.show();

        startPane.requestFocus();
        
	}
	public static void main(String[] args) {
		launch(args);
	}
}
	
