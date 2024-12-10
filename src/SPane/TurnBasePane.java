package SPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.Unit;
import enemy.Enemy;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import player.Player;
import utils.TurnBase;

public class TurnBasePane extends Pane {
    private Player player;
    private List<Enemy> enemies;
    private List<Enemy> initialenemies;
    private TurnBase turnManager;
    private Label playerHp;
    private Label playerDefense;
    private Label playerAtk;
    private Map<Enemy, Label> enemyHpLabels = new HashMap<>();
    private Map<Enemy, Label> enemyDefenseLabels = new HashMap<>();
    private Map<Enemy, ImageView> enemyImageViews = new HashMap<>();
    private Text turnStatusLabelPlayer;
    private Text turnStatusLabelEnemy;
    private Text clickEnemyToAttackLabel;
    private GridPane  enemyBox;
    private GridPane playerBox;
    private boolean isAnimationRunning;
    private String FontString;
    private ImageView playerImage; //88
    private  ArrayList<ArrayList<Integer>> enemyPositionX;
    private ArrayList<ArrayList<Integer>> enemyPositionY;
    private ImageView BuffAttackButton;
    private ImageView BuffDefenseButton;
    public TurnBasePane(Player player , List<Enemy> enemies , String BackgroundPath) {
    	//set font
    	FontString = "";
    	 try {
             String classLoaderPath = ClassLoader.getSystemResource("Pixeboy.ttf").toString();
             FontString = classLoaderPath;
         } catch (Exception e) {
             e.printStackTrace();
             System.out.println("Not fount: "+ "Pixeboy.ttf");
         }    
    	// initialize enemies and player
        this.enemies = enemies;
        this.player = player;
        initialenemies = new ArrayList<Enemy>();
        for(Enemy enemy : enemies) {
        	initialenemies.add(enemy);
        }
        // Background setup
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
        //initialize turnManager
        turnManager = new TurnBase(player, enemies, this);
        // Set Player Image Position
        playerImage = new ImageView(player.getImageStay());
        playerImage.setPreserveRatio(true);
        playerImage.setFitHeight(250);
        playerImage.setLayoutX(1260); //1000
        playerImage.setLayoutY(150);
        this.getChildren().add(playerImage);
        TranslateTransition playerMoveIn = new TranslateTransition(Duration.seconds(2), playerImage);
        playerMoveIn.setByX(-280);
        // Set Enemy Image Position when have 1 or 2 or 3 enemy
        enemyPositionX = new ArrayList<ArrayList<Integer>>();
        enemyPositionY = new ArrayList<ArrayList<Integer>>();
        enemyPositionX.add(new ArrayList<>(List.of(200)));
        enemyPositionY.add(new ArrayList<>(List.of(150)));
        enemyPositionX.add(new ArrayList<>(List.of(300, 120)));
        enemyPositionY.add(new ArrayList<>(List.of(30,200)));
        enemyPositionX.add(new ArrayList<>(List.of(120,80,380)));
        enemyPositionY.add(new ArrayList<>(List.of(20, 220, 150)));
        for (int i = 0; i < this.enemies.size(); i++) {
            Enemy enemy = this.enemies.get(i);
            // Enemy image
            ImageView enemyImage = new ImageView(enemy.getImageStay());
            enemyImage.setPreserveRatio(true);
            enemyImage.setFitHeight(250);
            enemyImage.setLayoutX(enemyPositionX.get(this.enemies.size() - 1).get(i));
            enemyImage.setLayoutY(enemyPositionY.get(this.enemies.size() - 1).get(i));
            enemyImage.setOpacity(0);
            // Add mouse events
	        enemyImage.setOnMouseEntered(e -> {
	             if (turnManager.isPlayerTurn() && player.isAlive() && !this.isAnimationRunning) {
	                 enemyImage.setScaleX(1.1); // Zoom in
	                 enemyImage.setScaleY(1.1);
	                 setEnemyFadeEffect(enemy, true); // Fade other enemies
	                 highlightEnemyRow(enemy, Color.BLUE);
	                 
	             }
	         });
            enemyImage.setOnMouseExited(e -> {
                if (turnManager.isPlayerTurn() && player.isAlive() && !this.isAnimationRunning) {
                    enemyImage.setScaleX(1.0); // Reset size
                    enemyImage.setScaleY(1.0);
                    setEnemyFadeEffect(null, false); // Reset fade effect
                    highlightEnemyRow(enemy, Color.BLACK);
                }
            });
            enemyImage.setOnMouseClicked(e -> handleEnemyClick(e, enemy)); // Handle click event
            this.getChildren().add(enemyImage);
            enemyImageViews.put(enemy, enemyImage); // Keep Enemy Imageview to map
        }
      //start with animation player walkin and enemy blink
    	isAnimationRunning = true;
        // Enemies Animation Blink
        ParallelTransition enemiesFadeIn = new ParallelTransition();
        for (ImageView enemyImage : enemyImageViews.values()) {
            FadeTransition fade = new FadeTransition(Duration.seconds(0.5), enemyImage);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setCycleCount(5);
            fade.setAutoReverse(true);
            enemiesFadeIn.getChildren().add(fade);
        }
        SequentialTransition startSequence = new SequentialTransition(
                playerMoveIn, // ผู้เล่นเดินเข้ามา
                new PauseTransition(Duration.seconds(1)), // หยุดรอ 1 วินาที
                enemiesFadeIn, // ศัตรูกระพริบ
                new PauseTransition(Duration.seconds(0.5)) // หยุดก่อนเริ่มเกม
        );

        startSequence.setOnFinished(e -> {
            // เริ่มเกมหลังจาก Animation จบ
        	isAnimationRunning = false;
            turnManager.startTurn();
            updateTurnStatus(true); // แสดง Player Turn
        });
        startSequence.play();
        // Initialize status labels and button
        playerHp = new Label();
        playerDefense = new Label();
        playerAtk = new Label();
        updatePlayerStatus();
        //Player Status Box
        playerBox = new GridPane();
        playerBox.setOpacity(0.8);
        playerBox.setHgap(20);
        playerBox.setVgap(25);
        playerBox.setPadding(new Insets(20,40,20,40));
        playerBox.setLayoutX(820);
        playerBox.setLayoutY(500);
        playerBox.setPrefHeight(200);
        playerBox.setPrefWidth(450);
        Text PlayerHeader = new Text("Player");
        PlayerHeader.setFont(Font.loadFont(FontString,45));
        PlayerHeader.setUnderline(true);
        PlayerHeader.setFill(Color.web("#c765ff"));
        playerBox.add(PlayerHeader, 0, 0,1,1);
        Text playerName = new Text("Name: " + player.toString());
        playerName.setFont(Font.loadFont(FontString,27));
        playerName.setFill(Color.BLACK);
        playerBox.add(playerName, 0, 1);
        playerHp.setFont(Font.loadFont(FontString,27));
        playerHp.setTextFill(Color.BLACK);
        playerDefense.setFont(Font.loadFont(FontString,27));
        playerDefense.setTextFill(Color.BLACK);
        playerBox.add(playerHp, 0, 2);
        playerBox.add(playerDefense, 0, 3);
        playerAtk.setFont(Font.loadFont(FontString,27));
        playerAtk.setTextFill(Color.BLACK);
        playerBox.add(playerAtk, 1, 2);
        Label playerSpeed = new Label("Speed: " + this.player.getSpeed());
        playerSpeed.setFont(Font.loadFont(FontString,27));
        playerSpeed.setTextFill(Color.BLACK);
        playerBox.add(playerSpeed, 1, 3);
        playerBox.setBackground(new Background(new BackgroundFill(
                Color.WHITE,
                new CornerRadii(10),  
                Insets.EMPTY           
            )));
        playerBox.setBorder(new Border(new BorderStroke(
        		Color.BLACK,             
        		BorderStrokeStyle.SOLID, 
                new CornerRadii(10),    
                new BorderWidths(5)     
            )));
         this.getChildren().add(playerBox);
         
         // Enemy status box
         enemyBox = new GridPane();
         enemyBox.setHgap(20);
         enemyBox.setVgap(25);
        enemyBox.setOpacity(0.8);
        enemyBox.setPadding(new Insets(20,20,20,40));
        enemyBox.setLayoutX(20);
        enemyBox.setLayoutY(500);
        enemyBox.setPrefHeight(200);
        enemyBox.setPrefWidth(780);
        Text enemyHeader = new Text("Enemy");
        enemyHeader.setFont(Font.loadFont(FontString,45));
        enemyHeader.setUnderline(true);
        enemyHeader.setFill(Color.RED);
        enemyBox.add(enemyHeader, 0, 0,1,1);
        for (int i = 0; i < this.enemies.size(); i++) {
            Enemy enemy = this.enemies.get(i);
            Label enemyName = new Label(enemy.toString());
            enemyName.setFont(Font.loadFont(FontString,23));
            enemyName.setTextFill(Color.BLACK);
            enemyBox.add(enemyName, 0, i + 1);
            Label enemyHpLabel = new Label("HP: " + enemy.getHp() + " / " + enemy.getMax_Hp());
            enemyHpLabel.setFont(Font.loadFont(FontString,23));
            enemyHpLabel.setTextFill(Color.BLACK);
            enemyBox.add(enemyHpLabel, 1, i + 1);
            enemyHpLabels.put(enemy, enemyHpLabel); // Add to HashMap
            Label enemyDefenseLabel = new Label("Defense: " + enemy.getDefense()+ " / " + enemy.getMax_Defense());
            enemyDefenseLabel.setFont(Font.loadFont(FontString,23));
            enemyDefenseLabel.setTextFill(Color.BLACK);
            enemyBox.add(enemyDefenseLabel, 2, i + 1);
            enemyDefenseLabels.put(enemy, enemyDefenseLabel); // Add to HashMap
            Label enemyAtk = new Label("Attack: " + enemy.getAtk());
            enemyAtk.setFont(Font.loadFont(FontString,23));
            enemyAtk.setTextFill(Color.BLACK);
            enemyBox.add(enemyAtk, 3, i + 1);
            Label enemySpeed = new Label("Speed: " + enemy.getSpeed());
            enemySpeed.setFont(Font.loadFont(FontString,23));
            enemySpeed.setTextFill(Color.BLACK);
            enemyBox.add(enemySpeed, 4, i + 1);
        }
        enemyBox.setBackground(new Background(new BackgroundFill(
                Color.WHITE,
                new CornerRadii(10),     
                Insets.EMPTY           
            )));
            // ตั้งค่า Border
        enemyBox.setBorder(new Border(new BorderStroke(
        		Color.BLACK,            
        		BorderStrokeStyle.SOLID, 
                new CornerRadii(10),     
                new BorderWidths(5)     
            )));
         this.getChildren().add(enemyBox);
         
        // Initialize turn manager
         turnStatusLabelPlayer = new Text("Player's Turn");
         turnStatusLabelPlayer.setFont(Font.loadFont(FontString,45));
         turnStatusLabelPlayer.setFill(Color.web("#c765ff"));
         turnStatusLabelPlayer.setLayoutX(550);
         turnStatusLabelPlayer.setLayoutY(50);
         turnStatusLabelPlayer.setVisible(false);
         this.getChildren().add(turnStatusLabelPlayer);
         clickEnemyToAttackLabel = new Text("(Click enemy to attack)");
         clickEnemyToAttackLabel.setFont(Font.loadFont(FontString,30));
         clickEnemyToAttackLabel.setFill(Color.web("#c765ff"));
         clickEnemyToAttackLabel.setLayoutX(532); 
         clickEnemyToAttackLabel.setLayoutY(100);  
         clickEnemyToAttackLabel.setVisible(false);  
         this.getChildren().add(clickEnemyToAttackLabel);
         turnStatusLabelEnemy = new Text("Enemies's Turn");
         turnStatusLabelEnemy.setFont(Font.loadFont(FontString,45));
         turnStatusLabelEnemy.setFill(Color.RED);
         turnStatusLabelEnemy.setLayoutX(545);
         turnStatusLabelEnemy.setLayoutY(50);
         turnStatusLabelEnemy.setVisible(false);
         this.getChildren().add(turnStatusLabelEnemy);
         
         //Initialize Defense Button
         Image BuffDefenseButtonImage = null;
 	    try {
 	        String classLoaderPath = ClassLoader.getSystemResource("DefenseBuff_button.png").toString();
 	       BuffDefenseButtonImage = new Image(classLoaderPath);
 	    } catch (Exception e) {
 	        e.printStackTrace();
 	        System.out.println("Not found BuffDefenseButtonImage");
 	    }
 	    BuffDefenseButton = new ImageView(BuffDefenseButtonImage);
 	    BuffDefenseButton.setFitHeight(80); 
 	    BuffDefenseButton.setFitWidth(80); 
 	    BuffDefenseButton.setLayoutX(960); 
 	    BuffDefenseButton.setLayoutY(410); 
 	    BuffDefenseButton.setOnMouseEntered(event -> {
 	    	if(turnManager.getExtraDamage() == 0 && turnManager.getExtraDefense() == 0) {
	 		   BuffDefenseButton.setFitHeight(90); 
	 		   BuffDefenseButton.setFitWidth(90); 
	 		   BuffDefenseButton.setLayoutX(960); 
	 		   BuffDefenseButton.setLayoutY(410); 
 	    	}
	        
 	    });
 	   BuffDefenseButton.setOnMouseExited(event -> {
	 		   BuffDefenseButton.setFitHeight(80); 
	 	 	   BuffDefenseButton.setFitWidth(80); 
	 	 	   BuffDefenseButton.setLayoutX(960); 
	 	 	   BuffDefenseButton.setLayoutY(410); 
	        
	    });
 	  BuffDefenseButton.setOnMouseClicked(e -> turnManager.handleBuffDefenseClick(e));
 	//Initialize Defense Button
       Image BuffAttackButtonImage = null;
	    try {
	        String classLoaderPath = ClassLoader.getSystemResource("AttackBuff_button.png").toString();
	        BuffAttackButtonImage = new Image(classLoaderPath);
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not found BuffAttackButtonImage");
	    }
	    BuffAttackButton = new ImageView(BuffAttackButtonImage);
	    BuffAttackButton.setFitHeight(80); 
	    BuffAttackButton.setFitWidth(80); 
	    BuffAttackButton.setLayoutX(1060); 
	    BuffAttackButton.setLayoutY(410); 
	    BuffAttackButton.setOnMouseEntered(event -> {
	    	if(turnManager.getExtraDamage() == 0 && turnManager.getExtraDefense() == 0) {
		    	BuffAttackButton.setFitHeight(90); 
		    	BuffAttackButton.setFitWidth(90); 
		    	BuffAttackButton.setLayoutX(1060); 
		    	BuffAttackButton.setLayoutY(410); 
	    	}
	        
	    });
	    BuffAttackButton.setOnMouseExited(event -> {
	    	BuffAttackButton.setFitHeight(80); 
	    	BuffAttackButton.setFitWidth(80); 
	    	BuffAttackButton.setLayoutX(1060); 
	    	BuffAttackButton.setLayoutY(410); 
	        
	    });
	    BuffAttackButton.setOnMouseClicked(e -> turnManager.handleBuffAttackClick(e));
        this.getChildren().addAll(BuffDefenseButton,BuffAttackButton);
        BuffAttackButton.setVisible(false);
        BuffDefenseButton.setVisible(false);
    }
    
    private void handleEnemyClick(MouseEvent event, Enemy enemy) {
    	if (turnManager.isPlayerTurn() && player.isAlive() && !isAnimationRunning) {
    		this.BuffAttackButton.setVisible(false);
    		this.BuffDefenseButton.setVisible(false);
    		performAttack(this.getPlayerImage(), enemyImageViews.get(enemy), this.player, () -> {
    			if (Math.random() < this.turnManager.chanceToMiss(player)) { // ถ้าค่าที่สุ่มได้น้อยกว่าโอกาส miss
                    showMissText(enemyImageViews.get(enemy)); // แสดงข้อความ miss
                    if(turnManager.getExtraDamage() > 0) {
                    	player.setAtk(player.getAtk()-turnManager.getExtraDamage());
                    	turnManager.setExtraDamage(0);
                    }
                    updatePlayerStatus();
                    updateEnemyStatus();
                } else {
                    if(turnManager.getExtraDamage() > 0) {
                    	player.attack(enemy);
                    	player.setAtk(player.getAtk()-turnManager.getExtraDamage());
                    	turnManager.setExtraDamage(0);
                    }
                    else {
                    	player.attack(enemy);
                    }
                    playAttackEffect(enemyImageViews.get(enemy));
                    updatePlayerStatus();
                    updateEnemyStatus();
                    showDamageText(enemyImageViews.get(enemy), player.getDamage());
                }
                setEnemyFadeEffect(null, false);
                if (!enemy.isAlive()) {
                	playDeathEffect(enemyImageViews.get(enemy), () -> {
    	                enemyHpLabels.remove(enemy);
    	                enemyDefenseLabels.remove(enemy);
    	                this.enemies.remove(enemy);
    	                this.getChildren().remove(event.getSource());
    	                updateEnemyPositions();
                    });
                }
                highlightEnemyRow(enemy, Color.BLACK);
    		});
        }
    }
    
    public void updatePlayerStatus() {
    	if(!player.isAlive()) {
    		playDeathEffect(this.getPlayerImage(), () -> {});
    	}
        playerHp.setText("HP: " + player.getHp() + " / " + player.getMaxhp());
        playerDefense.setText("Defense: " + player.getDefense() + " / "+ player.getMaxdefense());
        playerAtk.setText("Attack: " + this.player.getAtk());
    }
    public void updateEnemyStatus() {
    	for (Enemy enemy : new ArrayList<>(this.enemies)) {
            if (!enemy.isAlive()) {
                // อัปเดต HP ของศัตรูให้เป็น 0
                Label hpLabel = enemyHpLabels.get(enemy);
                if (hpLabel != null) {
                    hpLabel.setText("HP: 0" + " / " + enemy.getMax_Hp());
                    this.highlightEnemyRow(enemy, Color.GRAY);
                }
                this.playAttackEffect(enemyImageViews.get(enemy));
            } else {
                Label hpLabel = enemyHpLabels.get(enemy);
                Label defenseLabel = enemyDefenseLabels.get(enemy);
                if (hpLabel != null) {
                    hpLabel.setText("HP: " + enemy.getHp() + " / " + enemy.getMax_Hp());
                }
                if (defenseLabel != null) {
                    defenseLabel.setText("Defense: " + enemy.getDefense()+ " / " + enemy.getMax_Defense());
                }
            }
        }
    	
    }
    public void updateTurnStatus(boolean isPlayerTurn) {
        if (isPlayerTurn && player.isAlive()) {
        	turnStatusLabelEnemy.setVisible(false);
        	turnStatusLabelPlayer.setVisible(true);
            playerBox.setBackground(new Background(new BackgroundFill(
                    Color.YELLOW,
                    new CornerRadii(10),  
                    Insets.EMPTY           
                )));
            enemyBox.setBackground(new Background(new BackgroundFill(
                    Color.WHITE,
                    new CornerRadii(10),  
                    Insets.EMPTY           
                )));
            clickEnemyToAttackLabel.setVisible(true);
        } 
        
        else {
        	turnStatusLabelPlayer.setVisible(false);
        	turnStatusLabelEnemy.setVisible(true);
        	enemyBox.setBackground(new Background(new BackgroundFill(
                    Color.YELLOW,
                    new CornerRadii(10),  
                    Insets.EMPTY           
                )));
            playerBox.setBackground(new Background(new BackgroundFill(
                    Color.WHITE,
                    new CornerRadii(10),  
                    Insets.EMPTY           
                )));
            clickEnemyToAttackLabel.setVisible(false);
        }
    }
    public void showDamageText(Node target, int damage) {
        Text damageLabel = new Text("Damage " + damage);
        damageLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        damageLabel.setFill(Color.RED);
        damageLabel.setStroke(Color.WHITE);
        damageLabel.setStrokeWidth(1.5);

        // ใช้ตำแหน่ง localToScene
        Bounds bounds = target.localToScene(target.getBoundsInLocal());
        damageLabel.setLayoutX(bounds.getMinX()-30);
        damageLabel.setLayoutY(bounds.getMinY()+10);

        Platform.runLater(() -> {
            this.getChildren().add(damageLabel);
            turnManager.playSound("sound/OUCH_SoundEffect.mp3", 0.5);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> this.getChildren().remove(damageLabel));
            pause.play();
        });
    }

    public void showMissText(Node target) {
        Text missLabel = new Text("Miss");
        missLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        missLabel.setFill(Color.BLACK);
        missLabel.setStroke(Color.WHITE);
        missLabel.setStrokeWidth(1.5);

        Bounds bounds = target.localToScene(target.getBoundsInLocal());
        missLabel.setLayoutX(bounds.getMinX()-10);
        missLabel.setLayoutY(bounds.getMinY()+ 10);

        Platform.runLater(() -> {
            this.getChildren().add(missLabel);
            turnManager.playSound("sound/Miss2_SoundEffect.mp3", 0.7);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> this.getChildren().remove(missLabel));
            pause.play();
        });
    }
    public void updateEnemyPositions() {
		for (int i = 0; i < enemies.size(); i++) {
	        Enemy enemy = enemies.get(i);
	        ImageView enemyImage = enemyImageViews.get(enemy);

	        if (enemyImage != null) {
	            // รีเซ็ต TranslateX และ TranslateY ก่อน
	            enemyImage.setTranslateX(0);
	            enemyImage.setTranslateY(0);

	            // สร้าง Animation สำหรับการย้ายตำแหน่ง
	            TranslateTransition moveAnimation = new TranslateTransition(Duration.seconds(1), enemyImage);
	            double newX = enemyPositionX.get(enemies.size() - 1).get(i);
	            double newY = enemyPositionY.get(enemies.size() - 1).get(i);
	            moveAnimation.setToX(newX - enemyImage.getLayoutX());
	            moveAnimation.setToY(newY - enemyImage.getLayoutY());
	            moveAnimation.play();
	        }
	    }
	}
	public void highlightEnemyRow(Enemy enemy, Color color) {
	    int rowIndex = initialenemies.indexOf(enemy) + 1; // หาตำแหน่งแถว (บวก 1 เพราะแถวที่ 0 คือ header)
	    //int rowIndex = 
	    for (Node node : enemyBox.getChildren()) {
	        Integer row = GridPane.getRowIndex(node); // ตรวจสอบ row index ของ Node
	        if (row != null && row == rowIndex && node instanceof Label) {
	            ((Label) node).setTextFill(color); // เปลี่ยนสีข้อความ
	        }
	    }
	}
	 public void setEnemyFadeEffect(Enemy  highlightedEnemy, boolean fadeOthers) {
	    	for (Map.Entry<Enemy, ImageView> entry : enemyImageViews.entrySet()) {
	            ImageView enemyImage = entry.getValue();
	            if (fadeOthers && !entry.getKey().equals(highlightedEnemy)) {
	                enemyImage.setOpacity(0.3); // Fade to gray
	            } else {
	                enemyImage.setOpacity(1.0); // Reset to normal
	            }
	        }
	    }
	public void playAttackEffect(ImageView enemyImage) {
	    // การเคลื่อนที่ของเอฟเฟกต์
	    TranslateTransition attackMove = new TranslateTransition(Duration.millis(200), enemyImage);
	    attackMove.setByX(10); // ขยับไปด้านข้างเล็กน้อย
	    attackMove.setAutoReverse(true);
	    attackMove.setCycleCount(2);
	    // เอฟเฟกต์กระพริบ
	    FadeTransition fadeEffect = new FadeTransition(Duration.millis(300), enemyImage);
	    fadeEffect.setFromValue(1.0);
	    fadeEffect.setToValue(0.5);
	    fadeEffect.setAutoReverse(true);
	    fadeEffect.setCycleCount(2);
	    // เล่นอนิเมชัน
	    attackMove.play();
	    fadeEffect.play();
	}
	public void performAttack(ImageView attackerImageView, ImageView targetImageView, Unit attacker, Runnable onAttackComplete) {
		if (attackerImageView != null && targetImageView != null) {
	        // สไลด์ไปข้างหน้า
			this.setAnimationRunning(true);
	        TranslateTransition slideForward = new TranslateTransition(Duration.seconds(0.5), attackerImageView);
	        if(attacker instanceof Enemy) {
	        	slideForward.setByX(100);
        	}
        	else {
        		slideForward.setByX(-100);
        	}

	        slideForward.setOnFinished(event -> {
	            // เปลี่ยนเป็นรูปต่อสู้
	        	if(attacker instanceof Enemy) {
	        		Enemy Attacker = (Enemy) attacker;
	        		attackerImageView.setImage(Attacker.getImageFight());
	        	}
	        	else {
	        		Player Attacker = (Player) attacker;
	        		attackerImageView.setImage(Attacker.getImageFight());
	        	}
	            attackerImageView.setScaleX(1.2);
	            attackerImageView.setScaleY(1.2);
	            // สร้างเอฟเฟกต์แสงฟันที่เป้าหมาย
	            createShadowEffect(targetImageView,Color.RED);
	            onAttackComplete.run();
	            // Pause เพื่อแสดงการโจมตี
	            PauseTransition pause = new PauseTransition(Duration.seconds(2));
	            pause.setOnFinished(e -> {
	                // กลับมาที่ตำแหน่งเดิม
	                TranslateTransition slideBack = new TranslateTransition(Duration.seconds(0.5), attackerImageView);
	                if(attacker instanceof Enemy) {
	                	slideBack.setByX(-100);
	            	}
	            	else {
	            		slideBack.setByX(100);
	            	}

	                slideBack.setOnFinished(e2 -> {
	                    // รีเซ็ตขนาดภาพ
	                	if(attacker instanceof Enemy) {
	    	        		Enemy Attacker = (Enemy) attacker;
	    	        		attackerImageView.setImage(Attacker.getImageStay());
	    	        	}
	    	        	else {
	    	        		Player Attacker = (Player) attacker;
	    	        		attackerImageView.setImage(Attacker.getImageStay());
	    	        	}
	                    attackerImageView.setScaleX(1.0);
	                    attackerImageView.setScaleY(1.0);
	                    //onAttackComplete.run(); // ดำเนินการหลังโจมตีเสร็จ
	                });
	                slideBack.play();
	                PauseTransition pause2 = new PauseTransition(Duration.seconds(1.2));
		            pause2.setOnFinished(e2 -> {
			            if(attacker instanceof Enemy) {
			                turnManager.setPlayerTurn(true);
			                this.turnManager.startTurn();
			                this.setAnimationRunning(false);
		            	}
		            	else {
		            		turnManager.endPlayerTurn();
		            		this.setAnimationRunning(false);
		            	}
		            });
		            pause2.play();
	            });
	            pause.play();
	        });
	        slideForward.play();
	    }
	}
	public void createShadowEffect(ImageView targetImageView,Color color) {
		this.setAnimationRunning(true);
	    DropShadow attackEffect = new DropShadow();
	    attackEffect.setColor(color);
	    attackEffect.setRadius(0);

	    targetImageView.setEffect(attackEffect);

	    Timeline flickerEffect = new Timeline(
	        new KeyFrame(Duration.ZERO, new KeyValue(attackEffect.radiusProperty(), 0)),
	        new KeyFrame(Duration.millis(100), new KeyValue(attackEffect.radiusProperty(), 20)),
	        new KeyFrame(Duration.millis(200), new KeyValue(attackEffect.radiusProperty(), 0))
	    );
	    flickerEffect.setCycleCount(6);
	    flickerEffect.setOnFinished(e -> {
	    	targetImageView.setEffect(null);
	    	this.setAnimationRunning(false);
	    });
	    flickerEffect.play();
	}
	
	public void playDeathEffect(ImageView playerImage, Runnable onFinished) {
	    // Fade out effect
	    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), playerImage);
	    fadeOut.setFromValue(1.0);
	    fadeOut.setToValue(0.0);

	    // Rotate effect (falling down)
	    RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), playerImage);
	    rotate.setByAngle(90); // Rotate 90 degrees to simulate falling

	    // Scale down effect
	    ScaleTransition scaleDown = new ScaleTransition(Duration.seconds(1.5), playerImage);
	    scaleDown.setToX(0.5);
	    scaleDown.setToY(0.5);

	    // Combine animations
	    ParallelTransition deathEffect = new ParallelTransition(fadeOut, rotate, scaleDown);

	    deathEffect.setOnFinished(e -> {
	        if (onFinished != null) {
	            onFinished.run(); // Execute additional logic after death animation
	        }
	    });

	    deathEffect.play();
	}
	public boolean isAnimationRunning() {
		return isAnimationRunning;
	}
	public void setAnimationRunning(boolean isAnimationRunning) {
		this.isAnimationRunning = isAnimationRunning;
	}
	public ImageView getBuffAttackButton() {
		return BuffAttackButton;
	}
	public void setBuffAttackButton(ImageView buffAttackButton) {
		BuffAttackButton = buffAttackButton;
	}
	public ImageView getBuffDefenseButton() {
		return BuffDefenseButton;
	}
	public void setBuffDefenseButton(ImageView buffDefenseButton) {
		BuffDefenseButton = buffDefenseButton;
	}
	public Map<Enemy, Label> getEnemyHpLabels() {
		return enemyHpLabels;
	}
	public void setEnemyHpLabels(Map<Enemy, Label> enemyHpLabels) {
		this.enemyHpLabels = enemyHpLabels;
	}
	public Map<Enemy, Label> getEnemyDefenseLabels() {
		return enemyDefenseLabels;
	}
	public void setEnemyDefenseLabels(Map<Enemy, Label> enemyDefenseLabels) {
		this.enemyDefenseLabels = enemyDefenseLabels;
	}
	public ImageView getEnemyImageView(Enemy enemy) {
	    return enemyImageViews.get(enemy);
	}
	
	public ImageView getPlayerImage() {
		return playerImage;
	}
	
	public Text getClickEnemyToAttackLabel() {
		return clickEnemyToAttackLabel;
	}

	public Text getTurnStatusLabelPlayer() {
		return turnStatusLabelPlayer;
	}

	public void setTurnStatusLabelPlayer(Text turnStatusLabelPlayer) {
		this.turnStatusLabelPlayer = turnStatusLabelPlayer;
	}

	public Text getTurnStatusLabelEnemy() {
		return turnStatusLabelEnemy;
	}

	public void setTurnStatusLabelEnemy(Text turnStatusLabelEnemy) {
		this.turnStatusLabelEnemy = turnStatusLabelEnemy;
	}
	
	

	
	
}
