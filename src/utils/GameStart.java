package utils;


import player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Data.DataLevel;

import SPane.StartPane;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameStart {
	public static HashMap<KeyCode,Boolean> keys = new HashMap<KeyCode,Boolean>();
	private static StartPane appRoot;
	private static Pane gameRoot = new Pane();
	private static Pane uiRoot = new Pane();
	private static boolean isJump;
	private static boolean isWalk;
	private static boolean isDamage;
	private static Node player;
	private static int levelWidth;
	private static Node Door;
	private static ProgressBar H_bar;
	private static int Score;
	private static Text Score_on_screne;
	private static ArrayList<Node> platform = new ArrayList<Node>();
	private static ArrayList<Node> coins = new ArrayList<Node>();
	private static ArrayList <Node> obstacle = new ArrayList<Node>(); 
	private static Point2D playerVelocity = new Point2D(0,0);
	private static String Path_Block;
	private static int Round = 0;
	
	public static void GameStart(Player player) {
		Node py = player;
		setPlayer(py);
		GameStart.clear();
		GameStart.setScore(1);
		GameStart.initContent(1);
		GameStart.setRound(1);
		appRoot.setOnKeyPressed(event->{
			GameStart.keys.put(event.getCode(), true);
		});
		appRoot.setOnKeyReleased(event->{
			GameStart.keys.put(event.getCode(),false);
			if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
		        ((Player) player).stopWalking();
		        isWalk = false;
		    }
		});
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long now) {
				GameStart.update();
			}
		};
		timer.start();
	}
	
	public static StartPane getAppRoot() {
		return appRoot;
	}
	public static void setAppRoot(StartPane appRoot) {
		GameStart.appRoot = appRoot;
	}
	public static void clear() {
		gameRoot.getChildren().clear();
		platform.clear();
		uiRoot.getChildren().clear();
		
 		appRoot.getChildren().clear();
	}
	public static void update() {
		if (isPressed(KeyCode.SPACE) && player.getTranslateY()  >=5) {
			jumpPlayer();
		}
		if (isPressed(KeyCode.LEFT) && player.getTranslateX() >= 5) {
			if (!isWalk) {
				((Player)player).startWalkingLeft();
				isWalk = true;
			}
			movePlayerX(-4);
		}
		if (isPressed(KeyCode.RIGHT) && player.getTranslateX() + 40  <= levelWidth - 5) {
			if (!isWalk) {
				((Player)player).startWalkingRight();
				isWalk = true;
			}
			movePlayerX(4);
		}
		if (isPressed(KeyCode.Z)) {
			//Do damage
			//setScore(getScore()+1);
			//editUi(1);
		}
		if (isPressed(KeyCode.E) && player.getBoundsInParent().intersects(getDoor().getBoundsInParent())) {
			clear();
			setRound(getRound() + 1);
			if (getRound() == 2) {
				//Goto หน้าบอส
			}else {
				//((Player)player).stopWalking();
				initContent(getRound());
			}
		}
		if (playerVelocity.getY() < 10) {
			playerVelocity = playerVelocity.add(0,1);
		}
		movePlayerY((int)playerVelocity.getY());
		checkcollideCoin();
	}
	private static boolean isPressed(KeyCode key) {
		return keys.getOrDefault(key, false);
	}
	private static void doDamage(Node player,Node Enemy) {
		//change Node to class of each 
	}
	private static void movePlayerX(int value) {
		boolean moveRight = value > 0;
		for (int i = 0;i<Math.abs(value);i++) {
			for (Node pt:platform) {
				if (player.getBoundsInParent().intersects(pt.getBoundsInParent())) {
					if (moveRight) {
						if (player.getTranslateX() + 40 == pt.getTranslateX()) {
							checkcollideObstacle(pt);
							return;
						}
					}else {
						if (player.getTranslateX() == pt.getTranslateX() + 60) {
							checkcollideObstacle(pt);
							return;
						}
					}
				}
			}
			player.setTranslateX(player.getTranslateX() + (moveRight ? 1:-1)); // if moveRight set translate x to oldX + 1 or oldX -1 when moveLeft 
		}
	}
	private static void movePlayerY(int value) {
		boolean moveDown = value > 0; // - is mean up + is mean down
		for (int i = 0;i<Math.abs(value);i++) {
			for (Node pt:platform) {
				if (player.getBoundsInParent().intersects(pt.getBoundsInParent())) {
					if (moveDown) {
						if (player.getTranslateY() + 80 == pt.getTranslateY()) {
							player.setTranslateY(player.getTranslateY() - 1);
							setJump(false);
							checkcollideObstacle(pt);
							return;
						}
					}else {
						if (player.getTranslateY() == pt.getTranslateY() + 60) {
							return;
						}
					}
				}
			}
			player.setTranslateY(player.getTranslateY() + (moveDown ? 0.5:-1)); // if moveRight set translate x to oldX + 1 or oldX -1 when moveLeft 
		}
		if (player.getTranslateY() >= 720) {
			player.setTranslateX(0);
			player.setTranslateY(500);
			gameRoot.setLayoutX(0); //reset มุมกล้อง
		    gameRoot.setLayoutY(0); //reset มุมกล้อง 
		    ((Player)player).setHp(((Player)player).getHp() - 20);
		    editUi(0);
		}
	}
	private static void checkcollideCoin() {
		List<Node> collectedCoins = new ArrayList<>();
	    for (Node coin : coins) {
	        if (player.getBoundsInParent().intersects(coin.getBoundsInParent())) {
	            collectedCoins.add(coin); // Mark coin for removal
	            setScore(getScore() + 1); // Increment score
	            editUi(1); // Update UI
	        }
	    }
	    // Remove collected coins
	    for (Node coin : collectedCoins) {
	        gameRoot.getChildren().remove(coin);
	        coins.remove(coin);
	    }
	}
	
	private static void checkcollideObstacle(Node pt) {
		if (obstacle.contains(pt) && !isDamage) {
			((Player)player).setHp(((Player)player).getHp() - 10);
            editUi(0);
        	isDamage = true;
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isDamage = false;
            }).start();
		}
	}
	
	private static void jumpPlayer() {
		if (!isJump) {
			playerVelocity = playerVelocity.add(0,-30);
			setJump(true); //prohibit to double jump
		}
	}
	public static void initContent(int level) {
		Path_Block = level==0 ? "Block_01.png":"Block_02.png";
		String sp = level == 0 ? "Spike.png" : "Cactus.png";
		ImageView Bg = new ImageView(SetImage("Level"+Integer.toString(level)+ ".png"));
		Bg.setFitHeight(720);
		Bg.setFitWidth(1280);
		levelWidth = DataLevel.Level1[level].length() * 60;
		String[] arr;
		if (level == 0) {
			arr = DataLevel.Level1;
		}else {
			arr = DataLevel.Level2;
		}
 		for (int i = 0 ;i<arr.length;i++) {
			String line = arr[i];
			for (int j = 0;j<line.length();j++) {
				if (line.charAt(j) == '1') {
					Image block =  SetImage(Path_Block);
					Node pt = CreateEntity(j*60, i*60, 60, 60,gameRoot,block);
					platform.add(pt);
				}
				else if (line.charAt(j) == '2') {
					Image block =  SetImage("Portal.png");
					Node door = CreateEntity(j*60-300, i*60-200, 300,300,gameRoot,block);
					setDoor(door);
					platform.add(door);
				}else if (line.charAt(j) == '3') {
					Image coin = SetImage("Coin.png");
					Node Coin = CreateEntity(j*60, i*60, 60, 60,gameRoot,coin);
					coins.add(Coin);
				}else if (line.charAt(j) == '4') {
					Image spike = SetImage(sp);
					Node Spike = CreateEntity(j*60, i*60, 60, 60,gameRoot,spike);
					platform.add(Spike);
					obstacle.add(Spike);
				}
			}
		}
		initUi();
		gameRoot.setLayoutX(0); //reset มุมกล้อง
	    gameRoot.setLayoutY(0); //reset มุมกล้อง 
		player.setTranslateX(0);
		player.setTranslateY(500);
		gameRoot.getChildren().add(player);
		player.translateXProperty().addListener((obs,old,newValue)->{
			int offset = newValue.intValue();
			if (offset > 640 && offset < levelWidth - 640) {
				gameRoot.setLayoutX(-(offset-640));
			}
		});
		appRoot.getChildren().addAll(Bg,gameRoot,uiRoot);
	}
	private static void initUi() {
		double currentHealth =((Player)player).getHp();
		ProgressBar healthBar = new ProgressBar();
		healthBar.setPrefWidth(200);
		healthBar.setPrefHeight(30);
		healthBar.setStyle("-fx-accent: green;");
		healthBar.setTranslateX(30);
		healthBar.setTranslateY(20);
		healthBar.setProgress(currentHealth/((Player)player).getMaxhp());
		H_bar = healthBar;
		System.out.println("H_bar initialized: " + (H_bar != null));
		uiRoot.getChildren().add(healthBar);
		Score_on_screne = Scoreboard();
		uiRoot.getChildren().add(Score_on_screne);
	}
	private static Image SetImage(String imagePath) {
		Image bg = null;
		try {
            String classLoaderPath = ClassLoader.getSystemResource(imagePath).toString();
            bg = new Image(classLoaderPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not fount: "+ imagePath);
        }
		return bg;
	}
	private static Text Scoreboard() {
		Text t = new Text("Score: " + Integer.toString(getScore()));
		t.setFont(new Font(22));
		t.prefHeight(40);
		t.prefWidth(40);
		t.setTranslateX(1180);
		t.setTranslateY(30);
		return t;
	}
	private static void editUi(int idx) { // idx indicate behavior of this method
		if (idx == 0) {
			double currentHp = ((Player) player).getHp();
			double maxHp = ((Player) player).getMaxhp();
			if (currentHp > 0) {
				H_bar.setProgress(currentHp / maxHp);
				System.out.println(currentHp);
			}else {
				H_bar.setProgress(0);
				appRoot.getChildren().clear();
				//บอกว่า player ตายแล้วก็ขึ้นว่า แพ้แล้วไปหน้า start
			}
		}else {
			Platform.runLater(()->{
				Score_on_screne.setText("Score: "+Integer.toString(getScore()));
			});
		}
	}
	private static Node CreateEntity(int x,int y,int w,int h,Pane p,Image image) {
		ImageView ob = new ImageView(image);
		ob.setFitHeight(h);
		ob.setFitWidth(w);
		ob.setTranslateX(x);
		ob.setTranslateY(y);
		
		p.getChildren().add(ob);
		return ob;
	}
	
	public static Node getPlayer() {
		return player;
	}
	public static void setPlayer(Node py) {
		player = py;
	}
	public static  boolean isJump() {
		return isJump;
	}
	public static void setJump(boolean Jump) {
		isJump = Jump;
	}
	public static Node getDoor() {
		return Door;
	}
	public static void setDoor(Node door) {
		Door = door;
	}
	public static int getRound() {
		return Round;
	}
	public static void setRound(int round) {
		Round = round;
	}
	public static int getScore() {
		return Score;
	}
	public static void setScore(int score) {
		Score = score;
	}
	
	
}
