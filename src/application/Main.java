package application;

import javafx.stage.Stage;
import player.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;


public class Main extends Application{
	private boolean isJump;
	private Player player;
	
	public void start(Stage primaryStage)  throws Exception {
		StackPane st = new StackPane();
		Scene scene = new Scene(st,900,500);
		primaryStage.setTitle("Collos");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
