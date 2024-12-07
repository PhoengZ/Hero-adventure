package SPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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
	    restartButton.setOnMouseClicked(event -> {
	    	//goto main pain
	    });
	    this.getChildren().add(restartButton);
	}
	
}
