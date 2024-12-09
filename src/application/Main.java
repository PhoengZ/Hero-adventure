package application;

import javafx.stage.Stage;
import SPane.GameWinPane;
import SPane.HowToPlayPane;
import SPane.StartPane;

import javafx.application.Application;
import javafx.scene.Scene;


public class Main extends Application{
	public void start(Stage primaryStage)  throws Exception {
		
		StartPane.setStage(primaryStage);
		StartPane startPane = (StartPane) StartPane.getPane();
        Scene startScene = new Scene(startPane, 1280, 720);
        
        primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.setScene(startScene);
        primaryStage.show();

        startPane.requestFocus();
		/*GameWinPane gameWinPane = new GameWinPane();
        Scene gameoverscene = new Scene (gameWinPane , 1280 ,720);
        primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.setScene(gameoverscene);
        primaryStage.show();
        gameWinPane.requestFocus();*/
		/*HowToPlayPane howtoplayPane = new HowToPlayPane();
        Scene howtoplayScene = new Scene (howtoplayPane , 1280 ,720);
        primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.setScene(howtoplayScene);
        primaryStage.show();
        howtoplayPane.requestFocus();*/
	}
	public static void main(String[] args) {
		launch(args);
	}
}
	
