package utils;
import enemy.Enemy;
import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import player.Player;
public class TurnBase {
	    private Player player;
	    private Enemy enemy;
	    private Pane gamePane;
	    private boolean isPlayerTurn;

	    public TurnBase(Player player, Enemy enemy, Pane gamePane) {
	        this.player = player;
	        this.enemy = enemy;
	        this.gamePane = gamePane;
	        this.isPlayerTurn = true; // Player starts first
	    }

	    public void startTurn() {
	        if (player.isAlive() && enemy.isAlive()) {
	            if (isPlayerTurn) {
	                playerTurn();
	            } else {
	                enemyTurn();
	            }
	        } else {
	            endGame();
	        }
	    }
	    private void playerTurn() {
	        System.out.println("Player's Turn");
	        // Example: Player attacks enemy
	        player.attack(enemy);
	        isPlayerTurn = false;

	        // Add delay before enemy's turn
	        PauseTransition pause = new PauseTransition(Duration.seconds(1));
	        pause.setOnFinished(e -> startTurn());
	        pause.play();
	    }

	    private void enemyTurn() {
	        System.out.println("Enemy's Turn");
	        // Example: Enemy attacks player
	        enemy.attack(player);
	        isPlayerTurn = true;

	        // Add delay before player's turn
	        PauseTransition pause = new PauseTransition(Duration.seconds(1));
	        pause.setOnFinished(e -> startTurn());
	        pause.play();
	    }

	    private void endGame() {
	        if (player.isAlive()) {
	            System.out.println("Player Wins!");
	        } else {
	            System.out.println("Enemy Wins!");
	        }
	    }
	
}
