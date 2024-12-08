package SPane;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import player.Knight;
import player.Magician;
import player.Warrior;
import utils.GameStart;


public class StartPane extends Pane{
	private static Pane instance;
	private static Stage stage;

	private StartPane() {
		GameStart.setAppRoot(this);
		GameStart.mainPage();
	}

	public static Pane getPane() {
        if (instance == null)
            instance = new StartPane();
        return instance;
    }
	public static void setStage(Stage primaryStage) {
        stage = primaryStage; // Set the stage reference
    }
	private Image SetImage(String imagePath) {
		Image bg = null;
		try {
            String classLoaderPath = ClassLoader.getSystemResource(imagePath).toString();
            bg = new Image(classLoaderPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not fount Background_Mainmenu");
        }
		return bg;
	}
	
}
