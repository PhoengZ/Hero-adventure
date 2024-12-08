package application;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import SPane.GameOverPane;
import SPane.StartPane;
import SPane.TurnBasePane;
import javafx.application.Application;
import javafx.scene.Scene;
import player.Knight;
import player.Player;
import enemy.BeastMaster;
import enemy.Enemy;
import enemy.FireDemon;
import enemy.IceQueen;


public class Main extends Application{
	public void start(Stage primaryStage)  throws Exception {
		/*
		StartPane startPane = (StartPane) StartPane.getPane();
        Scene startScene = new Scene(startPane, 1280, 720);
        
        primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.setScene(startScene);
        primaryStage.show();

        // Request focus for the StartPane
        startPane.requestFocus();
        */
		
		Player player = new Knight();
		List<Enemy> enemies = new ArrayList<Enemy>();
		Enemy BeastMaster = new BeastMaster();
		enemies.add(BeastMaster);
		Enemy FireDemon = new FireDemon();
		enemies.add(FireDemon);
		Enemy IceQueen = new IceQueen();
		enemies.add(IceQueen);
		TurnBasePane turnbasePane = new TurnBasePane(player,enemies,"DesertTurnBaseBackground.png");
		Scene turnbaseScene = new Scene (turnbasePane , 1280 ,720);
		primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.setScene(turnbaseScene);
        primaryStage.show();
        turnbasePane.requestFocus();
        
		/*
		GameOverPane gameoverPane = new GameOverPane();
		Scene gameoverscene = new Scene (gameoverPane , 1280 ,720);
		primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.setScene(gameoverscene);
        primaryStage.show();
        gameoverPane.requestFocus();
        */
		/*
        GameOverPane gameOverPane = new GameOverPane(primaryStage);
        Scene gameOverScene = new Scene(gameOverPane, 1280, 720);
        primaryStage.setScene(gameOverScene); // แสดง GameOverPane
        primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.show();
       // startPane.requestFocus();
        */
	}
	public static void main(String[] args) {
		launch(args);
	}
}
	
