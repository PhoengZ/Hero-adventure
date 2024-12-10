package application;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import SPane.GameWinPane;
import SPane.HowToPlayPane;
import SPane.StartPane;
import SPane.TurnBasePane;
import player.*;
import enemy.*;
import javafx.application.Application;
import javafx.scene.Scene;


public class Main extends Application{
	public void start(Stage primaryStage)  throws Exception {
	/*
		StartPane.setStage(primaryStage);
		StartPane startPane = (StartPane) StartPane.getPane();
        Scene startScene = new Scene(startPane, 1280, 720);
        
        primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.setScene(startScene);
        primaryStage.show();

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
		TurnBasePane turnbasePane = new TurnBasePane(player,enemies,"ColossiumTurnBaseBackground2.png");
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
        GameWinPane gameWinPane = new GameWinPane();
		Scene gameoverscene = new Scene (gameWinPane , 1280 ,720);
		primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.setScene(gameoverscene);
        primaryStage.show();
        gameWinPane.requestFocus();
        */
		/*
        GameOverPane gameOverPane = new GameOverPane(primaryStage);
        Scene gameOverScene = new Scene(gameOverPane, 1280, 720);
        primaryStage.setScene(gameOverScene); // แสดง GameOverPane
||||||| 101e336
        */
		/*
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
        */
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
        GameWinPane gameWinPane = new GameWinPane();
		Scene gameoverscene = new Scene (gameWinPane , 1280 ,720);
		primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.setScene(gameoverscene);
        primaryStage.show();
        gameWinPane.requestFocus();
        */
		/*
        GameOverPane gameOverPane = new GameOverPane(primaryStage);
        Scene gameOverScene = new Scene(gameOverPane, 1280, 720);
        primaryStage.setScene(gameOverScene); // แสดง GameOverPane
=======
		/*GameWinPane gameWinPane = new GameWinPane();
        Scene gameoverscene = new Scene (gameWinPane , 1280 ,720);
>>>>>>> 80009f92362e3542b1b1031064180ab7c2217af3
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
	
