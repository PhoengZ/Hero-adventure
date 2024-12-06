package SPane;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utils.GameStart;


public class StartPane extends Pane{
	private static Pane instance;

	private StartPane() {
		Image st = null,ex = null,bg = null;
		try {
            String classLoaderPath = ClassLoader.getSystemResource("startButton.png").toString();
            st = new Image(classLoaderPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not fount StartButton");
        }
		try {
            String classLoaderPath = ClassLoader.getSystemResource("Exit.png").toString();
            ex = new Image(classLoaderPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not fount ExitButton");
        }
		try {
            String classLoaderPath = ClassLoader.getSystemResource("Background_Mainmenu.jpg").toString();
            bg = new Image(classLoaderPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not fount Background_Mainmenu");
        }
		ImageView Start = new ImageView(st);
		ImageView Exit = new ImageView(ex);
		ImageView Bg = new ImageView(bg);
		Start.setFitWidth(150);
		Start.setFitHeight(150);
		Exit.setFitHeight(150);
		Exit.setFitWidth(150);
		Bg.setFitHeight(720);
		Bg.setFitWidth(1280);
		GameStart.setAppRoot(this);
		Start.setOnMouseClicked(e->{
			GameStart.GameStart();
		});
		Exit.setOnMouseClicked(e->{
			//Exit the game
			Platform.exit();
		});
		Start.setTranslateX(565);
		Start.setTranslateY(350);
		Exit.setTranslateX(565);
		Exit.setTranslateY(500);
		System.out.println("Successfull Created pane");
		this.getChildren().addAll(Bg,Start,Exit);
	}

	public static Pane getPane() {
        if (instance == null)
            instance = new StartPane();
        return instance;
    }
	
}
