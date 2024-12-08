package SPane;


import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
	
}
