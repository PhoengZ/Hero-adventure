package SPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enemy.Enemy;
import enemy.FireDemon;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import player.Knight;
import player.Player;
import utils.TurnBase;

public class TurnBasePane extends Pane {
    private Player player;
    private List<Enemy> enemies;
    private TurnBase turnManager;
    private Label playerHp;
    private Label playerDefense;
    private Map<Enemy, Label> enemyHpLabels = new HashMap<>();
    private Map<Enemy, Label> enemyDefenseLabels = new HashMap<>();
    private Map<Enemy, ImageView> enemyImageViews = new HashMap<>();
    private int initialHpPlayer;
    private Label turnStatusLabel;
    private GridPane  EnemyBox;
    private GridPane playerBox;

    
    public TurnBasePane(Player player , List<Enemy> enemies , String BackgroundPath) {
        // Background setup
    	this.initialHpPlayer = player.getHp();
        Image Background = null;
        try {
            String classLoaderPath = ClassLoader.getSystemResource(BackgroundPath).toString();
            Background = new Image(classLoaderPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not found turnbase Background");
        }
        ImageView backgroundView = new ImageView(Background);
        backgroundView.setFitHeight(720);
        backgroundView.setFitWidth(1280);
        this.getChildren().add(backgroundView);

      
        // Set Player Image Position
        ImageView playerImage = new ImageView(player.getImage());
        playerImage.setPreserveRatio(true);
        playerImage.setFitHeight(250);
        playerImage.setLayoutX(1000);
        playerImage.setLayoutY(150);
        this.getChildren().add(playerImage);
        // Set Enemy Image Position when have 1 or 2 or 3 enemy
        ArrayList<ArrayList<Integer>> EnemyPositionX = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> EnemyPositionY = new ArrayList<ArrayList<Integer>>();
        EnemyPositionX.add(new ArrayList<>(List.of(200)));
        EnemyPositionY.add(new ArrayList<>(List.of(150)));
        EnemyPositionX.add(new ArrayList<>(List.of(300, 120)));
        EnemyPositionY.add(new ArrayList<>(List.of(30,200)));
        EnemyPositionX.add(new ArrayList<>(List.of(200,80,380)));
        EnemyPositionY.add(new ArrayList<>(List.of(20, 220, 150)));
        
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);

            // Enemy image
            ImageView enemyImage = new ImageView(enemy.getImage());
            enemyImage.setPreserveRatio(true);
            enemyImage.setFitHeight(250);
            enemyImage.setLayoutX(EnemyPositionX.get(enemies.size() - 1).get(i));
            enemyImage.setLayoutY(EnemyPositionY.get(enemies.size() - 1).get(i));

            // Add mouse events
            enemyImage.setOnMouseEntered(e -> {
                if (turnManager.isPlayerTurn() && player.isAlive()) {
                    enemyImage.setScaleX(1.1); // Zoom in
                    enemyImage.setScaleY(1.1);
                    setEnemyFadeEffect(enemy, true); // Fade other enemies
                }
            });

            enemyImage.setOnMouseExited(e -> {
                if (turnManager.isPlayerTurn() && player.isAlive()) {
                    enemyImage.setScaleX(1.0); // Reset size
                    enemyImage.setScaleY(1.0);
                    setEnemyFadeEffect(null, false); // Reset fade effect
                }
            });

            enemyImage.setOnMouseClicked(e -> handleEnemyClick(e, enemy)); // Handle click event

            this.getChildren().add(enemyImage);
            enemyImageViews.put(enemy, enemyImage); // เก็บ ImageView ของศัตรูใน Map
        }
        // initialize enemies and player
        this.enemies = enemies;
        this.player = player;
        // Initialize status labels and button
        playerHp = new Label();
        playerDefense = new Label();
        updatePlayerStatus();
        
        //Player Status Box
        playerBox = new GridPane();
        playerBox.setVgap(15);
        playerBox.setPadding(new Insets(20,40,20,40));
        playerBox.setLayoutX(820);
        playerBox.setLayoutY(500);
        playerBox.setPrefHeight(200);
        playerBox.setPrefWidth(410);
        Text PlayerHeader = new Text("Player");
        PlayerHeader.setFont(Font.font("Tahoma",FontWeight.BOLD,30));
        playerBox.add(PlayerHeader, 0, 0,1,1);
        Text playerName = new Text("Name: " + player.toString());
        playerName.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));
        playerBox.add(playerName, 0, 1);
        playerHp.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));
        playerDefense.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));
        playerBox.add(playerHp, 0, 2);
        playerBox.add(playerDefense, 0, 3);
        Label playerAtk = new Label("Attack: " + this.player.getAtk());
        playerAtk.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));
        playerBox.add(playerAtk, 1, 2);
        Label playerSpeed = new Label("Speed: " + this.player.getSpeed());
        playerSpeed.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));
        playerBox.add(playerSpeed, 1, 3);
        playerBox.setBackground(new Background(new BackgroundFill(
                Color.WHITE,
                new CornerRadii(10),  
                Insets.EMPTY           
            )));
        playerBox.setBorder(new Border(new BorderStroke(
        		Color.WHITE,             
        		BorderStrokeStyle.SOLID, 
                new CornerRadii(10),    
                new BorderWidths(2)     
            )));
        
         this.getChildren().add(playerBox);
         
         
         // Enemy status box
        EnemyBox = new GridPane();
        EnemyBox.setHgap(40);
        EnemyBox.setVgap(15);
        EnemyBox.setPadding(new Insets(20,20,20,40));
        EnemyBox.setLayoutX(70);
        EnemyBox.setLayoutY(500);
        EnemyBox.setPrefHeight(200);
        EnemyBox.setPrefWidth(700);
        Text enemyHeader = new Text("Enemy");
        enemyHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        EnemyBox.add(enemyHeader, 0, 0, 1, 1);

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            
            Label enemyName = new Label(enemy.toString());
            enemyName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            EnemyBox.add(enemyName, 0, i + 1);
            Label enemyHpLabel = new Label("HP: " + enemy.getHp());
            enemyHpLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            EnemyBox.add(enemyHpLabel, 1, i + 1);
            enemyHpLabels.put(enemy, enemyHpLabel); // Add to HashMap
            Label enemyDefenseLabel = new Label("Defense: " + enemy.getDefense());
            enemyDefenseLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            EnemyBox.add(enemyDefenseLabel, 2, i + 1);
            enemyDefenseLabels.put(enemy, enemyDefenseLabel); // Add to HashMap
            Label enemyAtk = new Label("Attack: " + enemy.getAtk());
            enemyAtk.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            EnemyBox.add(enemyAtk, 3, i + 1);
            
        }

        EnemyBox.setBackground(new Background(new BackgroundFill(
                Color.WHITE,
                new CornerRadii(10),     
                Insets.EMPTY           
            )));

            // ตั้งค่า Border
        EnemyBox.setBorder(new Border(new BorderStroke(
        		Color.WHITE,            
        		BorderStrokeStyle.SOLID, 
                new CornerRadii(10),     
                new BorderWidths(2)     
            )));
        
         this.getChildren().add(EnemyBox);
 
        
        // Initialize turn manager
         turnStatusLabel = new Label("Player's Turn");
         turnStatusLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
         turnStatusLabel.setTextFill(Color.BLUE);
         turnStatusLabel.setLayoutX(550);
         turnStatusLabel.setLayoutY(50);
         this.getChildren().add(turnStatusLabel);

        turnManager = new TurnBase(player, enemies, this);
        turnManager.startTurn();
    }
    private void handleEnemyClick(MouseEvent event, Enemy enemy) {
        if (turnManager.isPlayerTurn() && player.isAlive()) {
        	int damage = player.getAtk();
            player.attack(enemy); // Player attacks the specific enemy
            updatePlayerStatus();
            updateEnemyStatus();
            showDamageText(enemyImageViews.get(enemy), damage);
            setEnemyFadeEffect(null, false);
            if (!enemy.isAlive()) {
            	this.getChildren().remove(event.getSource());
            }
            turnManager.endPlayerTurn();
        }
    }
    public void updatePlayerStatus() {
        playerHp.setText("HP: " + player.getHp());
        playerDefense.setText("Defense: " + player.getDefense());
        if(player.getHp() != this.initialHpPlayer) {
	        playerHp.setTextFill(Color.RED);
	        PauseTransition pause = new PauseTransition(Duration.seconds(1));
	        pause.setOnFinished(event -> {
	            playerHp.setTextFill(Color.BLACK);
	            playerDefense.setTextFill(Color.BLACK);
	        });
	        pause.play();
        }
    }

    public void updateEnemyStatus() {
    	for (Enemy enemy : new ArrayList<>(enemies)) {
            if (!enemy.isAlive()) {
                // อัปเดต HP ของศัตรูให้เป็น 0
                Label hpLabel = enemyHpLabels.get(enemy);
                if (hpLabel != null) {
                    hpLabel.setText("HP: 0");
                }
                enemyHpLabels.remove(enemy);
                enemyDefenseLabels.remove(enemy);
                enemies.remove(enemy);
            } else {
                Label hpLabel = enemyHpLabels.get(enemy);
                Label defenseLabel = enemyDefenseLabels.get(enemy);
                if (hpLabel != null) {
                    hpLabel.setText("HP: " + enemy.getHp());
                }
                if (defenseLabel != null) {
                    defenseLabel.setText("Defense: " + enemy.getDefense());
                }
            }
        }
    }
    private void setEnemyFadeEffect(Enemy  highlightedEnemy, boolean fadeOthers) {
    	for (Map.Entry<Enemy, ImageView> entry : enemyImageViews.entrySet()) {
            ImageView enemyImage = entry.getValue();
            if (fadeOthers && !entry.getKey().equals(highlightedEnemy)) {
                enemyImage.setOpacity(0.3); // Fade to gray
            } else {
                enemyImage.setOpacity(1.0); // Reset to normal
            }
        }
    }
    public void updateTurnStatus(boolean isPlayerTurn) {
        if (isPlayerTurn) {
            turnStatusLabel.setText("Player's Turn");
            turnStatusLabel.setTextFill(Color.BLUE);
            playerBox.setBackground(new Background(new BackgroundFill(
                    Color.YELLOW,
                    new CornerRadii(10),  
                    Insets.EMPTY           
                )));
            EnemyBox.setBackground(new Background(new BackgroundFill(
                    Color.WHITE,
                    new CornerRadii(10),  
                    Insets.EMPTY           
                )));
           
        } else {
            turnStatusLabel.setText("Enemy's Turn");
            turnStatusLabel.setTextFill(Color.RED);
            EnemyBox.setBackground(new Background(new BackgroundFill(
                    Color.YELLOW,
                    new CornerRadii(10),  
                    Insets.EMPTY           
                )));
            playerBox.setBackground(new Background(new BackgroundFill(
                    Color.WHITE,
                    new CornerRadii(10),  
                    Insets.EMPTY           
                )));
        }
    }
    private void showDamageText(Node target, int damage) {
    	// สร้าง Label ที่แสดงข้อความการโจมตี
        Label damageLabel = new Label("-" + damage);
        damageLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        damageLabel.setTextFill(Color.RED);
        
        // กำหนดตำแหน่งของ Label ตามตำแหน่งของ target (ศัตรูหรือผู้เล่น)
        damageLabel.setLayoutX(target.getLayoutX());
        damageLabel.setLayoutY(target.getLayoutY() - 20); // ให้แสดงเล็กน้อยข้างๆ target
        
        // เพิ่มข้อความการโจมตีลงใน Pane
        this.getChildren().add(damageLabel);
        
        // ใช้ PauseTransition เพื่อให้ข้อความแสดงสักพักแล้วหายไป
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> this.getChildren().remove(damageLabel));
        pause.play();
    }


}
