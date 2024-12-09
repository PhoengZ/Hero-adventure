package SPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import utils.GameStart;

public class GameWinPane extends Pane{
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
	    
	    Image restartButtonImage = null;
	    try {
	        String classLoaderPath = ClassLoader.getSystemResource("RestartButton.png").toString();
	        restartButtonImage = new Image(classLoaderPath);
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not found RestartButton");
	    }
	    FontString = "";
		 try {
	        String classLoaderPath = ClassLoader.getSystemResource("Pixeboy.ttf").toString();
	        FontString = classLoaderPath;
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not fount: "+ "Pixeboy.ttf");
	    }   
	    Text youwin = new Text("You Win");
	    youwin.setFont(null);
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
	    	//StartPane startPane = new StartPane(); // สร้าง StartPane
	    	GameStart.clear();
	    	System.out.println("Is clicked");
	    	//Scene startScene = new Scene(startPane, 1280, 720); // สร้าง Scene
            //primaryStage.setScene(startScene); // เปลี่ยน Scene เป็น StartPane
            GameStart.mainPage();
	    });
	    this.getChildren().add(restartButton);
	}
}
