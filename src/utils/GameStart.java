package utils;


 import player.Knight;
import player.Magician;
import player.Player;
import player.Warrior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Data.DataLevel;
import SPane.GameOverPane;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import obstacle.Shot;
import obstacle.ShotX;
import obstacle.SlimeFire;
import obstacle.Dragon;
import obstacle.Monster;

public class GameStart {
	public static HashMap<KeyCode,Boolean> keys = new HashMap<KeyCode,Boolean>();
	private static StartPane appRoot;
	private static Pane gameRoot = new Pane();
	private static Pane uiRoot = new Pane();
	private static Pane shotRoot = new Pane();
	private static AnimationTimer time;
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
	private static ArrayList <Node> monster = new ArrayList<Node>();
	private static ArrayList <Node> shot = new ArrayList<Node>();
	private static Point2D playerVelocity = new Point2D(0,0);
	private static String Path_Block;
	private static int Round = 0;
	private static Font font;
	
	
	public static void mainPage() {
		clear();
		Image st = SetImage("startButton.png");
		Image ex = SetImage("Exit.png");
		Image bg = SetImage("Background_Mainmenu_1.jpg");
		
		ImageView Start = new ImageView(st);
		ImageView Exit = new ImageView(ex);
		ImageView Bg = new ImageView(bg);
		
		Start.setFitWidth(150);
		Start.setFitHeight(150);
		Exit.setFitHeight(150);
		Exit.setFitWidth(150);
		
		Bg.setFitHeight(720);
		Bg.setFitWidth(1280);
		Start.setOnMouseEntered(e->{
			Start.setFitHeight(170);
			Start.setFitWidth(170);
			Start.setTranslateX(555);
			Start.setTranslateY(340);
		});
		Start.setOnMouseExited(e->{
			Start.setFitWidth(150);
			Start.setFitHeight(150);
			Start.setTranslateX(565);
			Start.setTranslateY(350);
		});
		Start.setOnMouseClicked(e->{
			appRoot.getChildren().clear();
			Image bt_knight = SetImage("Knight_button.png");
			Image bt_warrior = SetImage("Warrior_button.png");
			Image bt_magic = SetImage("Magician_button.png");
			ImageView Button_knight = new ImageView(bt_knight);
			ImageView Button_warrior = new ImageView(bt_warrior);
			ImageView Button_magic = new ImageView(bt_magic);
			Button_knight.setFitHeight(200);
			Button_knight.setFitWidth(300);
			Button_warrior.setFitHeight(200);
			Button_warrior.setFitWidth(300);
			Button_magic.setFitHeight(200);
			Button_magic.setFitWidth(300);
			Button_knight.setTranslateX(175);
			Button_knight.setTranslateY(360);
			Button_warrior.setTranslateX(475);
			Button_warrior.setTranslateY(360);
			Button_magic.setTranslateX(775);
			Button_magic.setTranslateY(360);
			
			Button_knight.setOnMouseClicked(event->{
				GameStart.GameStart(new Knight());
			});
			Button_knight.setOnMouseEntered(event->{
				Button_knight.setFitHeight(220);
				Button_knight.setFitWidth(320);
				Button_knight.setTranslateX(165);
				Button_knight.setTranslateY(350);
			});
			Button_knight.setOnMouseExited(event->{
				Button_knight.setFitHeight(200);
				Button_knight.setFitWidth(300);
				Button_knight.setTranslateX(175);
				Button_knight.setTranslateY(360);
			});
			Button_warrior.setOnMouseClicked(event->{
				GameStart.GameStart(new Warrior());
			});
			Button_warrior.setOnMouseEntered(event->{
				Button_warrior.setFitHeight(220);
				Button_warrior.setFitWidth(320);
				Button_warrior.setTranslateX(465);
				Button_warrior.setTranslateY(350);
			});
			Button_warrior.setOnMouseExited(event->{
				Button_warrior.setFitHeight(200);
				Button_warrior.setFitWidth(300);
				Button_warrior.setTranslateX(475);
				Button_warrior.setTranslateY(360);
			});
			Button_magic.setOnMouseClicked(event->{
				GameStart.GameStart(new Magician()); 
			});
			Button_magic.setOnMouseEntered(event->{
				Button_magic.setFitHeight(220);
				Button_magic.setFitWidth(320);
				Button_magic.setTranslateX(765);
				Button_magic.setTranslateY(350);
			});
			Button_magic.setOnMouseExited(event->{
				Button_magic.setFitHeight(200);
				Button_magic.setFitWidth(300);
				Button_magic.setTranslateX(775);
				Button_magic.setTranslateY(360);
			});
			appRoot.getChildren().addAll(Bg,Button_knight,Button_warrior,Button_magic);
		});
		Exit.setOnMouseEntered(e->{
			Exit.setFitHeight(170);
			Exit.setFitWidth(170);
			Exit.setTranslateX(555);
			Exit.setTranslateY(490);
		});
		Exit.setOnMouseExited(e->{
			Exit.setFitHeight(150);
			Exit.setFitWidth(150);
			Exit.setTranslateX(565);
			Exit.setTranslateY(500);
		});
		Exit.setOnMouseClicked(e->{
			//Exit the game
			Platform.exit();
		});
		Start.setTranslateX(565);
		Start.setTranslateY(350);
		Exit.setTranslateX(565);
		Exit.setTranslateY(500);
		
		System.out.println("Successfull Created pane");
		appRoot.getChildren().addAll(Bg,Start,Exit);
	}
	
	
	public static void GameStart(Player player) {
		String ft = "";
		try {
            String classLoaderPath = ClassLoader.getSystemResource("PixelGame.otf").toString();
            ft = classLoaderPath;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not fount: "+ "PixelGame.otf");
        }	
		font = Font.loadFont(ft, 30);
		Node py = player;
		setPlayer(py);
		GameStart.clear();
		GameStart.setScore(0);
		GameStart.setRound(0);
		GameStart.initContent(0);
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
		time = timer;
		time.start();
	}
	
	public static StartPane getAppRoot() {
		return appRoot;
	}
	public static void setAppRoot(StartPane appRoot) {
		GameStart.appRoot = appRoot;
	}
	public static void clear() {
		gameRoot.getChildren().clear();
		uiRoot.getChildren().clear();
		platform.clear();
		shotRoot.getChildren().clear();
		monster.clear();
		shot.clear();
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
			movePlayerX(-5);
		}
		if (isPressed(KeyCode.RIGHT) && player.getTranslateX() + 40  <= levelWidth - 5) {
			if (!isWalk) {
				((Player)player).startWalkingRight();
				isWalk = true;
			}
			movePlayerX(5);
		}
		if (playerVelocity.getY() < 10) {
			playerVelocity = playerVelocity.add(0,1);
		}
		movePlayerY((int)playerVelocity.getY());
		if (((Player)player).getHp() == 0) {
			((Player)player).setHp(0);
			((Player)player).setAlive(false);
		}
		if (!((Player)player).isAlive()) {
			clear();
			time.stop();
			GameOverPane gameOverPane = new GameOverPane();
			appRoot.getChildren().add(gameOverPane);
			//return ;
		}
		monsterFire();
		checkcollideShot();
		checkcollideCoin();
		checkcollideDoor();
	}
	private static boolean isPressed(KeyCode key) {
		return keys.getOrDefault(key, false);
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
			player.setTranslateY(player.getTranslateY() + (moveDown ? 1:-1)); // if moveRight set translate x to oldX + 1 or oldX -1 when moveLeft 
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
	private static void monsterFire() {
		for (Node e:monster) {
			fireShot((int) e.getTranslateX()+ 20, (int) e.getTranslateY(),e);
		}
	}
	private static void fireShot(int startX, int startY,Node mn) {
		if (!((Monster)mn).getCanshot())return;
		int adjustedStartX = startX ;
	    int adjustedStartY = startY ;
	    if (mn instanceof Dragon) {
	    	Shot newShot = new Shot(adjustedStartX, adjustedStartY);
		    Platform.runLater(() -> {
		        newShot.draw();
		        shotRoot.getChildren().add(newShot);
		        shot.add(newShot);
		        gameRoot.getChildren().add(newShot);
		    });
	    }else if (mn instanceof SlimeFire) {
	    	Shot newShot = new ShotX(adjustedStartX, adjustedStartY);
		    Platform.runLater(() -> {
		        newShot.draw();
		        shotRoot.getChildren().add(newShot);
		        shot.add(newShot);
		        gameRoot.getChildren().add(newShot);
		    });
	    }
	    ((Monster)mn).setCanshot(false);
	    new Thread(() -> {
	        try {
	            Thread.sleep(2000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        ((Monster)mn).setCanshot(true);
	    }).start();
	}
	private static void checkcollideShot() {
		for (int i = shot.size() - 1; i >=0 ; i--) {
			Shot s = (Shot)shot.get(i);
			if(s.getPosY() >= 720 || s.getPosX() <=0 || Math.abs(s.getPosX()-s.getInitialx()) > 720)  { 
				shot.remove(s);
				shotRoot.getChildren().remove(s);
				gameRoot.getChildren().remove(s);
				continue;
			}
			s.update();
			s.draw();
			if (s.collide(player)) {
				((Player)player).createAttackEffect(player);
	            ((Player) player).setHp(((Player) player).getHp() - 10); // Decrease player HP by 10
	            shotRoot.getChildren().remove(s);
	            gameRoot.getChildren().remove(s);
	            shot.remove(s);
	            editUi(0);
	        }
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
	private static void checkcollideDoor() {
		if (player.getBoundsInParent().intersects(Door.getBoundsInParent())) {
			clear();
			setRound(getRound() + 1);
			if (getRound() == 4) {
				//Goto หน้าบอส
			}else {
				initContent(getRound());
				System.out.println("Round: "+getRound());
			}
		}
	}
	private static void checkcollideObstacle(Node pt) {
		if (obstacle.contains(pt) && !isDamage) {
			((Player)player).setHp(((Player)player).getHp() - 10);
            editUi(0);
        	isDamage = true;
            new Thread(() -> {
            	((Player)player).createAttackEffect(player);
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
		String sp = "";
		String[] arr;
		ImageView Bg = null;
		if (level == 0) {
			Path_Block = "Block_01.png";
			arr = DataLevel.Level1;
			sp = "Spike.png";
			Bg = new ImageView(SetImage("Level0.png"));
		}else if (level == 1) {
			Path_Block = "Block_02.png";
			arr = DataLevel.Level2;
			sp = "Cactus.png";
			Bg = new ImageView(SetImage("Level1.png"));
		}else {
			Path_Block = "Block_03.png";
			arr = DataLevel.Level3;
			sp = "Cactus.png";
			Bg = new ImageView(SetImage("Level2.png"));
		}
		
		Bg.setFitHeight(720);
		Bg.setFitWidth(1280);
		levelWidth = arr[0].length() * 60;
 		for (int i = 0 ;i<arr.length;i++) {
			String line = arr[i];
			for (int j = 0;j<line.length();j++) {
				if (line.charAt(j) == '1') {
					Image block =  SetImage(Path_Block);
					Node pt = CreateEntity(j*60, i*60, 60, 60,gameRoot,block);
					platform.add(pt);
				}
				else if (line.charAt(j) == '2') {
					Image block =  SetImage(getRound() == 2 ? "Rome_Door.png":"Portal.png");
					Node door = CreateEntity(j*60-300, i*60-200, 300,300,gameRoot,block);
					setDoor(door);
				}else if (line.charAt(j) == '3') {
					Image coin = SetImage("Coin.png");
					Node Coin = CreateEntity(j*60, i*60, 60, 60,gameRoot,coin);
					coins.add(Coin);
				}else if (line.charAt(j) == '4') {
					Image spike = SetImage(sp);
					Node Spike = CreateEntity(j*60, i*60, 60, 60,gameRoot,spike);
					platform.add(Spike);
					obstacle.add(Spike);
				}else if (line.charAt(j) == '5') {
					Monster mn = new SlimeFire(j*60,i*60,60,"SlimeFire.png");
					Node mon = mn;
					mon.setTranslateX(mn.getPosX());
					mon.setTranslateY(mn.getPosY());
					gameRoot.getChildren().add(mon);
					platform.add(mon);
					obstacle.add(mon);
					monster.add(mon);
				}else if (line.charAt(j) == '6') {
					Monster mn = new Dragon(j*60,i*60,60,"Dragon.png");
					Node mon = mn;
					mon.setTranslateX(mn.getPosX());
					mon.setTranslateY(mn.getPosY());
					gameRoot.getChildren().add(mon);
					platform.add(mon);
					obstacle.add(mon);
					monster.add(mon);
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
		appRoot.getChildren().addAll(Bg,gameRoot,uiRoot,shotRoot);
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
		Image Heart = SetImage("Heart.png");
		ImageView Health = new ImageView(Heart);
		Health.setFitHeight(40);
		Health.setFitWidth(40);
		Health.setTranslateX(15);
		Health.setTranslateY(15);
		//System.out.println("H_bar initialized: " + (H_bar != null));
		uiRoot.getChildren().add(healthBar);
		uiRoot.getChildren().add(Health);
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
		t.setFont(font);
		t.setFill(Color.BLACK);
		t.prefHeight(60);
		t.prefWidth(60);
		t.setTranslateX(1160);
		t.setTranslateY(50);
		return t;
	}
	private static void editUi(int idx) { // idx indicate behavior of this method
		if (idx == 0) {
			double currentHp = ((Player) player).getHp();
			double maxHp = ((Player) player).getMaxhp();
			if (currentHp > 0) {
				H_bar.setProgress(currentHp / maxHp);
				//System.out.println(currentHp);
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


	public static Pane getGameRoot() {
		return gameRoot;
	}


	public static void setGameRoot(Pane gameRoot) {
		GameStart.gameRoot = gameRoot;
	}
	
}
