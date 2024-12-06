package SPane;
import enemy.Enemy;
import javafx.scene.layout.Pane;
import player.Player;
import utils.TurnBase;
public class TurnBasePane extends Pane{
	    private Player player;
	    private Enemy enemy;
	    private TurnBase turnManager;

	    public TurnBasePane() {
	        // Initialize player and enemy
	        player = new Player(100, 20, 10, 5, "player.png");
	        enemy = new Enemy(80, 15, 8, 3, "enemy.png");

	        // Set positions
	        player.setLayoutX(200);
	        player.setLayoutY(300);

	        enemy.setLayoutX(600);
	        enemy.setLayoutY(300);

	        // Add to the screen
	        this.getChildren().addAll(player, enemy);

	        // Initialize turn manager
	        turnManager = new TurnBase(player, enemy, this);
	        turnManager.startTurn();
	    }
	

}
