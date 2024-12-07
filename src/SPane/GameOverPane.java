package SPane;

import application.Main;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameOverPane extends Pane{
	public GameOverPane(Stage primaryStage) {
		Image Background = null;
	    try {
	        String classLoaderPath = ClassLoader.getSystemResource("GameOver.png").toString();
	        Background = new Image(classLoaderPath);
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not found gameover Background");
	    }
	    ImageView backgroundView = new ImageView(Background);
	    backgroundView.setFitHeight(720);
	    backgroundView.setFitWidth(1280);
	    this.getChildren().add(backgroundView);
	    
	    Image restartButtonImage = null;
	    try {
	        String classLoaderPath = ClassLoader.getSystemResource("RestartButton.png").toString();
	        restartButtonImage = new Image(classLoaderPath);
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not found RestartButton");
	    }
	    ImageView restartButton = new ImageView(restartButtonImage);
	    restartButton.setFitHeight(100); 
        restartButton.setFitWidth(200); 
        restartButton.setLayoutX(540); 
        restartButton.setLayoutY(580); 
        restartButton.setOnMouseEntered(event -> {
        	restartButton.setFitHeight(120); 
            restartButton.setFitWidth(220); 
            restartButton.setLayoutX(530); 
            restartButton.setLayoutY(570); 
	    });
        restartButton.setOnMouseExited(event -> {
        	restartButton.setFitHeight(100); 
            restartButton.setFitWidth(200); 
            restartButton.setLayoutX(540); 
            restartButton.setLayoutY(580); 
	    });
	    restartButton.setOnMouseClicked(event -> {
	    	//StartPane startPane = new StartPane(); // สร้าง StartPane
	    	StartPane startPane = (StartPane) StartPane.getPane();
	    	Scene startScene = new Scene(startPane, 1280, 720); // สร้าง Scene
            primaryStage.setScene(startScene); // เปลี่ยน Scene เป็น StartPane
            startPane.requestFocus();
	    });
	    this.getChildren().add(restartButton);
	}
	
}
