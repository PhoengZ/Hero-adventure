package SPane;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import player.Knight;
import player.Magician;
import player.Warrior;
import utils.GameStart;


public class StartPane extends Pane{
	private static Pane instance;

	private StartPane() {
		Image st = SetImage("startButton.png");
		Image ex = SetImage("Exit.png");
		Image bg = SetImage("Background_Mainmenu_1.jpg");
		
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
			this.getChildren().clear();
			Image bt_knight = SetImage("Knight_button.png");
			Image bt_warrior = SetImage("Warrior_button.png");
			Image bt_magic = SetImage("Magician_button.png");
			ImageView Button_knight = new ImageView(bt_knight);
			ImageView Button_warrior = new ImageView(bt_warrior);
			ImageView Button_magic = new ImageView(bt_magic);
			Button_knight.setFitHeight(200);
			Button_knight.setFitWidth(300);
			Button_warrior.setFitHeight(200);
			Button_warrior.setFitWidth(300);
			Button_magic.setFitHeight(200);
			Button_magic.setFitWidth(300);
			Button_knight.setTranslateX(175);
			Button_knight.setTranslateY(360);
			Button_warrior.setTranslateX(475);
			Button_warrior.setTranslateY(360);
			Button_magic.setTranslateX(775);
			Button_magic.setTranslateY(360);
			
			Button_knight.setOnMouseClicked(event->{
				GameStart.GameStart(new Knight());
			});
			Button_warrior.setOnMouseClicked(event->{
				GameStart.GameStart(new Warrior());
			});
			Button_magic.setOnMouseClicked(event->{
				GameStart.GameStart(new Magician());
			});
			this.getChildren().addAll(Bg,Button_knight,Button_warrior,Button_magic);
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
