package utils;

import enemy.Enemy;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import player.Player;

import java.util.List;
import java.util.Random;

import SPane.TurnBasePane;

public class TurnBase {
    private Player player;
    private List<Enemy> enemies;
    private TurnBasePane gamePane;
    private boolean isPlayerTurn;
    private static double chanceToMiss;

    public TurnBase(Player player, List<Enemy> enemies, TurnBasePane gamePane) {
        this.player = player;
        this.enemies = enemies;
        this.gamePane = gamePane;
        this.isPlayerTurn = true; 
        this.chanceToMiss = 0.4;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void startTurn() {
        if (player.isAlive() && enemies.stream().anyMatch(Enemy::isAlive)) { 
        	//stream anymatch for check if any enemies is alive
            gamePane.updateTurnStatus(isPlayerTurn);
            if (isPlayerTurn) {
                playerTurn();
            } else {
                enemyTurn();
            }
        } else {
            endGame();
        }
    }

    public void endPlayerTurn() {
        isPlayerTurn = false;
        startTurn();
    }

    private void playerTurn() {
        System.out.println("Player's Turn");
     
    }

    private void enemyTurn() {
    	isPlayerTurn = false;
        System.out.println("Enemy's Turn");
        gamePane.setEnemyFadeEffect(null, false);
        // Choose Enemy To attack Player by Random
        Enemy attackingEnemy = getRandomAliveEnemy();
        if (attackingEnemy != null) {
            // get Image
            ImageView enemyImageView = gamePane.getEnemyImageView(attackingEnemy);
            if (enemyImageView != null) {
                gamePane.performAttack(enemyImageView, gamePane.getPlayerImage(), attackingEnemy, () -> {
                    // โจมตีผู้เล่น
                    int damage = attackingEnemy.getAtk();
                    if (Math.random() < TurnBase.getChanceToMiss()) {
                        gamePane.showMissText(gamePane.getPlayerImage());
                    } else {
                        attackingEnemy.attack(player);
                        gamePane.playAttackEffect(gamePane.getPlayerImage());
                        gamePane.showDamageText(gamePane.getPlayerImage(), damage);
                    }
                    gamePane.updatePlayerStatus();
                });
            } else {
                // ถ้าไม่มี ImageView ของศัตรู
                endGame();
            }
        }
    }
    private Enemy getRandomAliveEnemy() {
        Random random = new Random();
        List<Enemy> aliveEnemies = enemies.stream().filter(Enemy::isAlive).toList();
        if (!aliveEnemies.isEmpty()) {
            return aliveEnemies.get(random.nextInt(aliveEnemies.size()));
        }
        return null;
    }
    private void endGame() {
        if (player.isAlive()) {
            System.out.println("Player Wins!");
        } else {
            System.out.println("Enemies Win!");
            //go to GameoverPane
        }
    }
	public static double getChanceToMiss() {
		return chanceToMiss;
	}
	public static void setChanceToMiss(double chanceToMiss) {
		TurnBase.chanceToMiss = chanceToMiss;
	}

	public void setPlayerTurn(boolean isPlayerTurn) {
		this.isPlayerTurn = isPlayerTurn;
	}
	
    
}
