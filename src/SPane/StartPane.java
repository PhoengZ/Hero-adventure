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
		Image st = null,ex = null;
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
		ImageView Start = new ImageView(st);
		ImageView Exit = new ImageView(ex);
		Start.setFitWidth(30);
		Start.setFitHeight(30);
		Exit.setFitHeight(30);
		Exit.setFitWidth(30);
		Rectangle Bg = new Rectangle(1280,720);
		Bg.setFill(Color.LIGHTYELLOW);
		GameStart.setAppRoot(this);
		Start.setOnMouseClicked(e->{
			GameStart.GameStart();
		});
		Exit.setOnMouseClicked(e->{
			//Exit the game
			Platform.exit();
		});
		Start.setTranslateX(640);
		Start.setTranslateY(300);
		Exit.setTranslateX(640);
		Exit.setTranslateY(400);
		System.out.println("Successfull Created pane");
		this.getChildren().addAll(Bg,Start,Exit);
	}

	public static Pane getPane() {
        if (instance == null)
            instance = new StartPane();
        return instance;
    }
	
}
