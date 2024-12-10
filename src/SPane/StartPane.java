package SPane;


import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.GameStart;


public class StartPane extends Pane{
	private static Pane instance;

	private StartPane() {
		GameStart.setAppRoot(this);
		GameStart.mainPage();
	}

	public static Pane getPane() {
        if (instance == null)
            instance = new StartPane();
        return instance;
    }
	
}
