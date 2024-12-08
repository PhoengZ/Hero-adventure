package SPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.Unit;
import enemy.Enemy;
import enemy.FireDemon;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
    private Label turnStatusLabel;
    private GridPane  EnemyBox;
    private GridPane playerBox;
    private boolean isAnimationRunning;
    private Label clickEnemyToAttackLabel;
    private static String FontString;
    private ImageView playerImage; //88
    private  ArrayList<ArrayList<Integer>> EnemyPositionX;
    private ArrayList<ArrayList<Integer>> EnemyPositionY;
    public TurnBasePane(Player player , List<Enemy> enemies , String BackgroundPath) {
    	//set font
    	String FontString = "";
    	 try {
             String classLoaderPath = ClassLoader.getSystemResource("Pixeboy.ttf").toString();
             FontString = classLoaderPath;
         } catch (Exception e) {
             e.printStackTrace();
             System.out.println("Not fount: "+ "Pixeboy.ttf");
         }    
    	//start with animation player walkin and enemy blink
    	isAnimationRunning = true;
    	// initialize enemies and player
        this.enemies = enemies;
        this.player = player;
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
        playerImage = new ImageView(player.getImageLeft());
        playerImage.setPreserveRatio(true);
        playerImage.setFitHeight(250);
        playerImage.setLayoutX(1280); //1000
        playerImage.setLayoutY(150);
        this.getChildren().add(playerImage);
        TranslateTransition playerMoveIn = new TranslateTransition(Duration.seconds(2), playerImage);
        playerMoveIn.setByX(-280);
        // Set Enemy Image Position when have 1 or 2 or 3 enemy
        EnemyPositionX = new ArrayList<ArrayList<Integer>>();
        EnemyPositionY = new ArrayList<ArrayList<Integer>>();
        EnemyPositionX.add(new ArrayList<>(List.of(200)));
        EnemyPositionY.add(new ArrayList<>(List.of(150)));
        EnemyPositionX.add(new ArrayList<>(List.of(300, 120)));
        EnemyPositionY.add(new ArrayList<>(List.of(30,200)));
        EnemyPositionX.add(new ArrayList<>(List.of(80,80,380)));
        EnemyPositionY.add(new ArrayList<>(List.of(20, 220, 150)));
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            // Enemy image
            ImageView enemyImage = new ImageView(enemy.getImageStay());
            enemyImage.setPreserveRatio(true);
            enemyImage.setFitHeight(250);
            enemyImage.setLayoutX(EnemyPositionX.get(enemies.size() - 1).get(i));
            enemyImage.setLayoutY(EnemyPositionY.get(enemies.size() - 1).get(i));
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
        updatePlayerStatus();
        //Player Status Box
        playerBox = new GridPane();
        playerBox.setHgap(20);
        playerBox.setVgap(25);
        playerBox.setPadding(new Insets(20,40,20,40));
        playerBox.setLayoutX(820);
        playerBox.setLayoutY(500);
        playerBox.setPrefHeight(200);
        playerBox.setPrefWidth(410);
        Text PlayerHeader = new Text("Player");
        PlayerHeader.setFont(Font.loadFont(FontString,45));
        PlayerHeader.setUnderline(true);
        PlayerHeader.setFill(Color.BLUE);
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
        Label playerAtk = new Label("Attack: " + this.player.getAtk());
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
        		Color.WHITE,             
        		BorderStrokeStyle.SOLID, 
                new CornerRadii(10),    
                new BorderWidths(2)     
            )));
         this.getChildren().add(playerBox);
         
         // Enemy status box
        EnemyBox = new GridPane();
        EnemyBox.setHgap(20);
        EnemyBox.setVgap(25);
        EnemyBox.setPadding(new Insets(20,20,20,40));
        EnemyBox.setLayoutX(70);
        EnemyBox.setLayoutY(500);
        EnemyBox.setPrefHeight(200);
        EnemyBox.setPrefWidth(700);
        Text enemyHeader = new Text("Enemy");
        enemyHeader.setFont(Font.loadFont(FontString,45));
        enemyHeader.setUnderline(true);
        enemyHeader.setFill(Color.RED);
        EnemyBox.add(enemyHeader, 0, 0, 1, 1);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            Label enemyName = new Label(enemy.toString());
            enemyName.setFont(Font.loadFont(FontString,27));
            enemyName.setTextFill(Color.BLACK);
            EnemyBox.add(enemyName, 0, i + 1);
            Label enemyHpLabel = new Label("HP: " + enemy.getHp() + " / " + enemy.getMax_Hp());
            enemyHpLabel.setFont(Font.loadFont(FontString,27));
            enemyHpLabel.setTextFill(Color.BLACK);
            EnemyBox.add(enemyHpLabel, 1, i + 1);
            enemyHpLabels.put(enemy, enemyHpLabel); // Add to HashMap
            Label enemyDefenseLabel = new Label("Defense: " + enemy.getDefense()+ " / " + enemy.getMax_Defense());
            enemyDefenseLabel.setFont(Font.loadFont(FontString,27));
            enemyDefenseLabel.setTextFill(Color.BLACK);
            EnemyBox.add(enemyDefenseLabel, 2, i + 1);
            enemyDefenseLabels.put(enemy, enemyDefenseLabel); // Add to HashMap
            Label enemyAtk = new Label("Attack: " + enemy.getAtk());
            enemyAtk.setFont(Font.loadFont(FontString,27));
            enemyAtk.setTextFill(Color.BLACK);
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
         turnStatusLabel = new Label("");
         turnStatusLabel.setFont(Font.loadFont(FontString,45));
         turnStatusLabel.setTextFill(Color.BLUE);
         turnStatusLabel.setLayoutX(550);
         turnStatusLabel.setLayoutY(50);
         this.getChildren().add(turnStatusLabel);
         clickEnemyToAttackLabel = new Label("(Click enemy to attack)");
         clickEnemyToAttackLabel.setFont(Font.loadFont(FontString,30));
         clickEnemyToAttackLabel.setTextFill(Color.BLUE);
         clickEnemyToAttackLabel.setLayoutX(532); 
         clickEnemyToAttackLabel.setLayoutY(100);  
         clickEnemyToAttackLabel.setVisible(false);  
         this.getChildren().add(clickEnemyToAttackLabel);
    }
    private void handleEnemyClick(MouseEvent event, Enemy enemy) {
    	if (turnManager.isPlayerTurn() && player.isAlive() && !isAnimationRunning) {
    		performAttack(this.getPlayerImage(), enemyImageViews.get(enemy), this.player, () -> {
    			if (Math.random() < this.turnManager.getChanceToMiss()) { // ถ้าค่าที่สุ่มได้น้อยกว่าโอกาส miss
                    showMissText(enemyImageViews.get(enemy)); // แสดงข้อความ miss
                } else {
                    int damage = player.getAtk();
                    player.attack(enemy); // Player attacks the specific enemy
                    playAttackEffect(enemyImageViews.get(enemy));
                    updatePlayerStatus();
                    updateEnemyStatus();
                    showDamageText(enemyImageViews.get(enemy), damage);
                }
                setEnemyFadeEffect(null, false);
                if (!enemy.isAlive()) {
                    this.getChildren().remove(event.getSource());
                }
                highlightEnemyRow(enemy, Color.BLACK);
                //turnManager.endPlayerTurn();
    		});
        }
    }
    public void updatePlayerStatus() {
        playerHp.setText("HP: " + player.getHp() + " / " + player.getMaxhp());
        playerDefense.setText("Defense: " + player.getDefense() + " / "+ player.getMaxdefense());
    }
    public void updateEnemyStatus() {
    	for (Enemy enemy : new ArrayList<>(enemies)) {
            if (!enemy.isAlive()) {
                // อัปเดต HP ของศัตรูให้เป็น 0
                Label hpLabel = enemyHpLabels.get(enemy);
                if (hpLabel != null) {
                    hpLabel.setText("HP: 0" + " / " + enemy.getMax_Hp());
                    this.highlightEnemyRow(enemy, Color.GRAY);
                }
                this.playAttackEffect(enemyImageViews.get(enemy));
                enemyHpLabels.remove(enemy);
                enemyDefenseLabels.remove(enemy);
                enemies.remove(enemy);
                updateEnemyPositions();
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
    public void updateTurnStatus(boolean isPlayerTurn) {
        if (isPlayerTurn && player.isAlive()) {
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
            clickEnemyToAttackLabel.setVisible(true);
        } 
        
        else {
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
            clickEnemyToAttackLabel.setVisible(false);
        }
    }
    public void showDamageText(Node target, int damage) {
    	// สร้าง Label ที่แสดงข้อความการโจมตี
    	 System.out.println("Damage");
        Label damageLabel = new Label("-" + damage);
        damageLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        damageLabel.setTextFill(Color.RED);
        
        // กำหนดตำแหน่งของ Label ตามตำแหน่งของ target (ศัตรูหรือผู้เล่น)
        damageLabel.setLayoutX(target.getLayoutX());
        damageLabel.setLayoutY(target.getLayoutY() - 20); // ให้แสดงเล็กน้อยข้างๆ target
        
        // เพิ่มข้อความการโจมตีลงใน Pane
        this.getChildren().add(damageLabel);
        
        // ใช้ PauseTransition เพื่อให้ข้อความแสดงสักพักแล้วหายไป
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> this.getChildren().remove(damageLabel));
        pause.play();
    }
    public void showMissText(Node target) {
        // สร้าง Label ที่แสดงข้อความ Miss
    	System.out.println("Miss");
        Label missLabel = new Label("Miss");
        missLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        missLabel.setTextFill(Color.BLACK);
        
        // กำหนดตำแหน่งของ Label ตามตำแหน่งของ target (ศัตรูหรือผู้เล่น)
        missLabel.setLayoutX(target.getLayoutX());
        missLabel.setLayoutY(target.getLayoutY() - 20); // ให้แสดงเล็กน้อยข้างๆ target
        
        // เพิ่มข้อความ Miss ลงใน Pane
        this.getChildren().add(missLabel);
        
        // ใช้ PauseTransition เพื่อให้ข้อความแสดงสักพักแล้วหายไป
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> this.getChildren().remove(missLabel));
        pause.play();
    }
	public Label getTurnStatusLabel() {
		return turnStatusLabel;
	}
	public void setTurnStatusLabel(Label turnStatusLabel) {
		this.turnStatusLabel = turnStatusLabel;
	}
	private void highlightEnemyRow(Enemy enemy, Color color) {
	    int rowIndex = enemies.indexOf(enemy) + 1; // หาตำแหน่งแถว (บวก 1 เพราะแถวที่ 0 คือ header)
	    for (Node node : EnemyBox.getChildren()) {
	        Integer row = GridPane.getRowIndex(node); // ตรวจสอบ row index ของ Node
	        if (row != null && row == rowIndex && node instanceof Label) {
	            ((Label) node).setTextFill(color); // เปลี่ยนสีข้อความ
	        }
	    }
	}
	public ImageView getEnemyImageView(Enemy enemy) {
	    return enemyImageViews.get(enemy);
	}
	
	public ImageView getPlayerImage() {
		return playerImage;
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
	        		attackerImageView.setImage(Attacker.getImageRight());
	        	}
	            attackerImageView.setScaleX(1.2);
	            attackerImageView.setScaleY(1.2);
	            // สร้างเอฟเฟกต์แสงฟันที่เป้าหมาย
	            createAttackEffect(targetImageView);
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
	    	        		attackerImageView.setImage(Attacker.getImageLeft());
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
	private void createAttackEffect(ImageView targetImageView) {
	    DropShadow attackEffect = new DropShadow();
	    attackEffect.setColor(Color.RED);
	    attackEffect.setRadius(0);

	    targetImageView.setEffect(attackEffect);

	    Timeline flickerEffect = new Timeline(
	        new KeyFrame(Duration.ZERO, new KeyValue(attackEffect.radiusProperty(), 0)),
	        new KeyFrame(Duration.millis(100), new KeyValue(attackEffect.radiusProperty(), 20)),
	        new KeyFrame(Duration.millis(200), new KeyValue(attackEffect.radiusProperty(), 0))
	    );
	    flickerEffect.setCycleCount(6);
	    flickerEffect.setOnFinished(e -> targetImageView.setEffect(null));
	    flickerEffect.play();
	}
	public boolean isAnimationRunning() {
		return isAnimationRunning;
	}
	public void setAnimationRunning(boolean isAnimationRunning) {
		this.isAnimationRunning = isAnimationRunning;
	}
	private void updateEnemyPositions() {
		for (int i = 0; i < enemies.size(); i++) {
	        Enemy enemy = enemies.get(i);
	        ImageView enemyImage = enemyImageViews.get(enemy);

	        if (enemyImage != null) {
	            // รีเซ็ต TranslateX และ TranslateY ก่อน
	            enemyImage.setTranslateX(0);
	            enemyImage.setTranslateY(0);

	            // สร้าง Animation สำหรับการย้ายตำแหน่ง
	            TranslateTransition moveAnimation = new TranslateTransition(Duration.seconds(1), enemyImage);
	            double newX = EnemyPositionX.get(enemies.size() - 1).get(i);
	            double newY = EnemyPositionY.get(enemies.size() - 1).get(i);
	            moveAnimation.setToX(newX - enemyImage.getLayoutX());
	            moveAnimation.setToY(newY - enemyImage.getLayoutY());
	            /*
	            enemyImage.setLayoutX(EnemyPositionX.get(enemies.size() - 1).get(i));
	            enemyImage.setLayoutY(EnemyPositionY.get(enemies.size() - 1).get(i));
	            */
	            moveAnimation.play();
	        }
	    }
	}
	private void playDeathEffect(ImageView enemyImage) {
		// Fade Out Effect
	    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), enemyImage);
	    fadeOut.setFromValue(1.0); // เริ่มต้นด้วยความโปร่งใสเต็ม
	    fadeOut.setToValue(0.0);   // หายไปจนมองไม่เห็น

	    // Scale Down Effect
	    ScaleTransition scaleDown = new ScaleTransition(Duration.seconds(1), enemyImage);
	    scaleDown.setFromX(1.0); // เริ่มต้นด้วยขนาดปกติ
	    scaleDown.setFromY(1.0);
	    scaleDown.setToX(0.0);   // ลดขนาดลงจนเหลือ 0
	    scaleDown.setToY(0.0);

	    // Combine Both Effects
	    ParallelTransition deathEffect = new ParallelTransition(fadeOut, scaleDown);

	    // PauseTransition (หน่วงเวลาก่อนลบ)
	    PauseTransition delay = new PauseTransition(Duration.seconds(3)); // หน่วงเวลา 0.5 วินาที

	    // เมื่อ Animation เสร็จสิ้นให้ลบ Enemy ออกจาก Parent
	    deathEffect.setOnFinished(event -> {
	        // เล่น PauseTransition ก่อนลบ
	        delay.setOnFinished(e -> {
	            ((Pane) enemyImage.getParent()).getChildren().remove(enemyImage);
	        });
	        delay.play();
	    });

	    // เล่น Animation
	    deathEffect.play();
	}

	
	
}
