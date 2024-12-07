package utils;

import enemy.Enemy;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
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
        this.chanceToMiss = 0.6;
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
        // สุ่มเลือกศัตรูที่ยังมีชีวิต
        Enemy attackingEnemy = getRandomAliveEnemy();
        if (attackingEnemy != null) {
        	 int damage = attackingEnemy.getAtk();
        	 double chanceToMiss = this.getChanceToMiss();
             if (Math.random() < chanceToMiss) { // ถ้าค่าที่สุ่มได้น้อยกว่าโอกาส miss
                 gamePane.showMissText(this.player); // แสดงข้อความ miss
             }
             else {
            	 attackingEnemy.attack(player); // ศัตรูโจมตีผู้เล่น
            	 gamePane.showDamageText(this.player, attackingEnemy.getAtk());
             }
            if (gamePane instanceof TurnBasePane) {
               gamePane.updatePlayerStatus(); // อัปเดตสถานะผู้เล่น
            }
            //showDamageText(playerImage, damage);
        }

        // Pause เพื่อให้เห็นการเปลี่ยนสถานะ
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            isPlayerTurn = true; // เปลี่ยนกลับเป็นเทิร์นของผู้เล่น
            startTurn();
        });
        pause.play();
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
        }
    }

	public static double getChanceToMiss() {
		return chanceToMiss;
	}

	public static void setChanceToMiss(double chanceToMiss) {
		TurnBase.chanceToMiss = chanceToMiss;
	}
    
}
