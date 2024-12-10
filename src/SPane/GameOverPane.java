package SPane;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import utils.GameStart;

public class GameOverPane extends Pane{
	public GameOverPane() {
		Image Background = null;
	    try {
	        String classLoaderPath = ClassLoader.getSystemResource("GameOver.png").toString();
	        Background = new Image(classLoaderPath);
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not found gameover Background");
	    }
	    Media bg = null;
		try {
            String classLoaderPath = ClassLoader.getSystemResource("Gameover.mp3").toString();
            bg = new Media(classLoaderPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not found Media:");
        }
		MediaPlayer media = new MediaPlayer(bg);
		media.setCycleCount(MediaPlayer.INDEFINITE);
		media.setVolume(0.1);
		media.play();
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
	    	media.stop();
	    	GameStart.clear();
	    	System.out.println("Is clicked");
            GameStart.mainPage();
	    });
	    this.getChildren().add(restartButton);
	}
	
}
