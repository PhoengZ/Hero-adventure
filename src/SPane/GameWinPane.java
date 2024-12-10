package SPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.GameStart;

public class GameWinPane extends Pane{
	private static String fontString;
	public GameWinPane() {
		Image Background = null;
	    try {
	        String classLoaderPath = ClassLoader.getSystemResource("GameWin.png").toString();
	        Background = new Image(classLoaderPath);
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not found gameover Background");
	    }
	    ImageView backgroundView = new ImageView(Background);
	    backgroundView.setFitHeight(720);
	    backgroundView.setFitWidth(1280);
	    this.getChildren().add(backgroundView);
	    Media bg = null;
		try {
            String classLoaderPath = ClassLoader.getSystemResource("Gamewin.mp3").toString();
            bg = new Media(classLoaderPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not found Media:");
        }
		MediaPlayer media = new MediaPlayer(bg);
		media.setCycleCount(MediaPlayer.INDEFINITE);
		media.setVolume(0.2);
		media.play();
	    Image restartButtonImage = null;
	    try {
	        String classLoaderPath = ClassLoader.getSystemResource("RestartButton.png").toString();
	        restartButtonImage = new Image(classLoaderPath);
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not found RestartButton");
	    }
	    
	    fontString = "";
		 try {
	        String classLoaderPath = ClassLoader.getSystemResource("Pixeboy.ttf").toString();
	        fontString = classLoaderPath;
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not fount: "+ "Pixeboy.ttf");
	    }   
	    Text youwin = new Text("You Win");
	    youwin.setStroke(Color.WHITE);
	    youwin.setStrokeWidth(2);
	    youwin.setFont(Font.loadFont(fontString, 80));
	    youwin.setTranslateX(530);
	    youwin.setTranslateY(100);
	    this.getChildren().add(youwin);
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
