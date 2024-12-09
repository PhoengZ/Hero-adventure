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
import SPane.UpgadeStatPane;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
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
	private static Pane settingRoot = new Pane();
	private static AnimationTimer time;
	private static boolean isJump;
	private static boolean isWalk;
	private static boolean isDamage;
	private static boolean isMusic = true;
	private static boolean isEsc = false;
	private static boolean isEscHeld = false;
	private static boolean isPaused = false;
	private static Node player;
	private static Node Door;
	private static int levelWidth;
	private static int Score;
	private static int Round = 0;
	private static ProgressBar H_bar;
	private static MediaPlayer media;
	private static MediaPlayer hitSoundPlayer;
	private static MediaPlayer walk;
	private static MediaPlayer jump;
	private static Text Score_on_screne;
	private static ImageView Background;
	private static ArrayList<Node> platform = new ArrayList<Node>();
	private static ArrayList<Node> coins = new ArrayList<Node>();
	private static ArrayList <Node> obstacle = new ArrayList<Node>(); 
	private static ArrayList <Node> monster = new ArrayList<Node>();
	private static ArrayList <Node> shot = new ArrayList<Node>();
	private static ArrayList <Node> setUi = new ArrayList<Node>();
	private static Point2D playerVelocity = new Point2D(0,0);
	private static String Path_Block;
	private static Font font;
	
	
	public static void mainPage() {
		clear();
		setEsc(false);
		setEscHeld(false);
		setPaused(false);
		Media md = SetMedia("Music_mainPage.mp3");
		media = new MediaPlayer(md);
		media.setCycleCount(MediaPlayer.INDEFINITE);
		media.setVolume(0.1);
		media.play();
		hitSoundPlayer = new MediaPlayer(SetMedia("hit.mp3"));
		hitSoundPlayer.setVolume(0.2);
		walk = new MediaPlayer(SetMedia("Walk_SoundEffect.mp3"));
		jump = new MediaPlayer(SetMedia("Jump_SoundEffect.mp3"));
		walk.setVolume(0.05);
		walk.setCycleCount(MediaPlayer.INDEFINITE);
		jump.setVolume(0.05);
		//hitSoundPlayer.play();
		Image st = SetImage("startButton.png");
		Image ex = SetImage("Exit.png");
		Image bg = SetImage("Background_Mainmenu_1.jpg");
		ImageView Start = new ImageView(st);
		ImageView Exit = new ImageView(ex);
		ImageView Bg = new ImageView(bg);
		ImageView Music = createButtonMusic(1200, 650, 50, 50);
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
			ImageView knight = new ImageView(SetImage("KnightRight.png"));
			ImageView warrior = new ImageView(SetImage("warriorRight.png"));
			ImageView mage = new ImageView(SetImage("MagicianRight.png"));
			knight.setFitHeight(150);
			knight.setFitWidth(100);
			warrior.setFitHeight(150);
			warrior.setFitWidth(100);
			mage.setFitHeight(150);
			mage.setFitWidth(100);
			knight.setTranslateX(275);
			knight.setTranslateY(350);
			warrior.setTranslateX(575);
			warrior.setTranslateY(350);
			mage.setTranslateX(875);
			mage.setTranslateY(350);
			Button_knight.setFitHeight(200);
			Button_knight.setFitWidth(300);
			Button_warrior.setFitHeight(200);
			Button_warrior.setFitWidth(300);
			Button_magic.setFitHeight(200);
			Button_magic.setFitWidth(300);
			Button_knight.setTranslateX(175);
			Button_knight.setTranslateY(510);
			Button_warrior.setTranslateX(475);
			Button_warrior.setTranslateY(510);
			Button_magic.setTranslateX(775);
			Button_magic.setTranslateY(510);
			Button_knight.setOnMouseClicked(event->{
				media.stop();
				GameStart.GameStart(new Knight());
			});
			Button_knight.setOnMouseEntered(event->{
				Button_knight.setFitHeight(220);
				Button_knight.setFitWidth(320);
				Button_knight.setTranslateX(165);
				Button_knight.setTranslateY(500);
				createShadowEffect(knight,Color.RED);
			});
			Button_knight.setOnMouseExited(event->{
				Button_knight.setFitHeight(200);
				Button_knight.setFitWidth(300);
				Button_knight.setTranslateX(175);
				Button_knight.setTranslateY(510);
			});
			Button_warrior.setOnMouseClicked(event->{
				media.stop();
				GameStart.GameStart(new Warrior());
			});
			Button_warrior.setOnMouseEntered(event->{
				Button_warrior.setFitHeight(220);
				Button_warrior.setFitWidth(320);
				Button_warrior.setTranslateX(465);
				Button_warrior.setTranslateY(500);
				createShadowEffect(warrior,Color.ORANGE);
			});
			Button_warrior.setOnMouseExited(event->{
				Button_warrior.setFitHeight(200);
				Button_warrior.setFitWidth(300);
				Button_warrior.setTranslateX(475);
				Button_warrior.setTranslateY(510);
			});
			Button_magic.setOnMouseClicked(event->{
				media.stop();
				GameStart.GameStart(new Magician()); 
			});
			Button_magic.setOnMouseEntered(event->{
				Button_magic.setFitHeight(220);
				Button_magic.setFitWidth(320);
				Button_magic.setTranslateX(765);
				Button_magic.setTranslateY(500);
				createShadowEffect(mage,Color.PURPLE);
			});
			Button_magic.setOnMouseExited(event->{
				Button_magic.setFitHeight(200);
				Button_magic.setFitWidth(300);
				Button_magic.setTranslateX(775);
				Button_magic.setTranslateY(510);
			});
			appRoot.getChildren().addAll(Bg,Button_knight,Button_warrior,Button_magic,knight,warrior,mage);
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
			Platform.exit();
		});
		Start.setTranslateX(565);
		Start.setTranslateY(350);
		Exit.setTranslateX(565);
		Exit.setTranslateY(500);
		setBackground(Bg);
		System.out.println("Successfull Created pane");
		appRoot.getChildren().addAll(Bg,Start,Exit,Music);
	}
	
	
	public static void GameStart(Player player) {
		String ft = "";
		try {
            String classLoaderPath = ClassLoader.getSystemResource("Pixeboy.ttf").toString();
            ft = classLoaderPath;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not fount: "+ "PixelGame.otf");
        }	
		setFont(Font.loadFont(ft, 36));
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
			if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
		        ((Player) player).stopWalking();
		        isWalk = false;
		        walk.stop();
		    }
			GameStart.keys.put(event.getCode(),false);
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
		if (media != null) {
            media.stop();
        }
		gameRoot.getChildren().clear();
		uiRoot.getChildren().clear();
		shotRoot.getChildren().clear();
		settingRoot.getChildren().clear();
		platform.clear();
		monster.clear();
		shot.clear();
		setUi.clear();
 		appRoot.getChildren().clear();
	}
	public static void update() {
		if (isPressed(KeyCode.ESCAPE) && !isEscHeld) {
			isEscHeld = true;
		    isEsc = !isEsc; // Toggle isEsc
		    if (isEsc) {
		    	settingRoot.setVisible(true);
		    	setPaused(true);
		        GaussianBlur blur = new GaussianBlur();
		        blur.setRadius(10);
		        gameRoot.setEffect(blur);
		        uiRoot.setEffect(blur);
		        Background.setEffect(blur);
		    } else {
		    	settingRoot.setVisible(false);
		    	setPaused(false);
		    	Background.setEffect(null);
		        gameRoot.setEffect(null);
		        uiRoot.setEffect(null);
		    }
		}
		if (!isPressed(KeyCode.ESCAPE)) {
		    isEscHeld = false; // Reset when key is released
		}
		if (isPaused)return ;
		if (isPressed(KeyCode.SPACE) && player.getTranslateY()  >=5) {
			jumpPlayer();
		}
		if (isPressed(KeyCode.LEFT) && player.getTranslateX() >= 5) {
			if (!isWalk) {
				((Player)player).startWalkingLeft();
				walk.play();
				isWalk = true;
			}
			movePlayerX(-4);
		}
		if (isPressed(KeyCode.RIGHT) && player.getTranslateX() + 40  <= levelWidth - 5) {
			if (!isWalk) {
				((Player)player).startWalkingRight();
				walk.play();
				isWalk = true;
			}
			movePlayerX(4);
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
		if (!moveDown && walk.getStatus() != MediaPlayer.Status.PLAYING)jump.play();
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
			player.setTranslateY(player.getTranslateY() + (moveDown ? 0.5:-1));
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
			fireShot((int) e.getTranslateX()+20, (int) e.getTranslateY()+20,e);
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
	            makehitSound();
	            shotRoot.getChildren().remove(s);
	            gameRoot.getChildren().remove(s);
	            shot.remove(s);
	            editUi(0);
	            editUi(2);
	            
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
			time.stop();
			clear();
			appRoot.getChildren().add(Background);
			GaussianBlur blur = new GaussianBlur();
	        blur.setRadius(10);
			Background.setEffect(blur);
			UpgadeStatPane up = new UpgadeStatPane((Player)player);
			appRoot.getChildren().add(up);
		}
	}
	private static void checkcollideObstacle(Node pt) {
		if (obstacle.contains(pt) && !isDamage) {
			((Player)player).setHp(((Player)player).getHp() - 10);
            editUi(0);
            editUi(2);
        	isDamage = true;
        	((Player)player).createAttackEffect(player);
        	makehitSound();
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
			jump.stop();
			jump.seek(Duration.ZERO);
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
		setBackground(Bg);
		gameRoot.setEffect(null);
		uiRoot.setEffect(null);
		Background.setEffect(null);
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
		initMusic();
		initSetting();
		gameRoot.setLayoutX(0); //reset มุมกล้อง
	    gameRoot.setLayoutY(0); //reset มุมกล้อง 
		player.setTranslateX(0);
		player.setTranslateY(500);
		System.out.println("Attack: "+((Player)player).getAtk());
		System.out.println("Attack: "+((Player)player).getDefense());
		System.out.println("Attack: "+((Player)player).getHp());
		gameRoot.getChildren().add(player);
		player.translateXProperty().addListener((obs,old,newValue)->{
			int offset = newValue.intValue();
			if (offset > 640 && offset < levelWidth - 640) {
				gameRoot.setLayoutX(-(offset-640));
			}
		});
		appRoot.getChildren().addAll(Bg,gameRoot,uiRoot,shotRoot,settingRoot);
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
		Health.setFitHeight(60);
		Health.setFitWidth(60);
		Health.setTranslateX(10);
		Health.setTranslateY(8);
		//System.out.println("H_bar initialized: " + (H_bar != null));
		uiRoot.getChildren().add(healthBar);
		uiRoot.getChildren().add(Health);
		Score_on_screne = Scoreboard();
		uiRoot.getChildren().add(Score_on_screne);
	}
	private static void initMusic() {
		media = new MediaPlayer(SetMedia("Level"+Integer.toString(getRound())+".mp3"));
		media.setCycleCount(MediaPlayer.INDEFINITE);
		media.setVolume(0.1);
		if (isMusic)media.play();
	}
	private static void initSetting() {
		settingRoot.setVisible(false);
		ImageView setting = new ImageView(SetImage("Menu.png"));
		setting.setFitWidth(600);
		setting.setFitHeight(650);
		setting.setTranslateX(340);
		setting.setTranslateY(35);
		ImageView main = new ImageView(SetImage("main_button.png"));
		ImageView att = new ImageView(SetImage("sword.png"));
		ImageView def = new ImageView(SetImage("shield.png"));
		ImageView Hp = new ImageView(SetImage("Heart.png"));
		att.setFitHeight(50);
		att.setFitWidth(50);
		att.setTranslateX(465);
		att.setTranslateY(225);
		def.setFitHeight(50);
		def.setFitWidth(50);
		def.setTranslateX(615);
		def.setTranslateY(225);
		Hp.setFitHeight(50);
		Hp.setFitWidth(50);
		Hp.setTranslateX(765);
		Hp.setTranslateY(225);
		Text dam = new Text(Integer.toString(((Player)player).getAtk()));
		Text df = new Text(Integer.toString(((Player)player).getDefense()));
		Text hp = new Text(Integer.toString(((Player)player).getHp()) +  "/" + Integer.toString(((Player)player).getMaxhp()));
		dam.setFont(font);
		df.setFont(font);
		hp.setFont(font);
		dam.setTranslateX(467);
		dam.setTranslateY(325);
		df.setTranslateX(623);
		df.setTranslateY(325);
		hp.setTranslateX(730);
		hp.setTranslateY(325);
		setUi.add(hp);
		setUi.add(df);
		setUi.add(dam);
		main.setFitHeight(150);
		main.setFitWidth(200);
		main.setTranslateX(540);
		main.setTranslateY(450);
		main.setOnMouseClicked(e->{
			time.stop();
			clear();
			GameStart.mainPage();
		});
		main.setOnMouseEntered(e->{
			main.setFitHeight(170);
			main.setFitWidth(220);
			main.setTranslateX(530);
			main.setTranslateY(440);
		});
		main.setOnMouseExited(e->{
			main.setFitHeight(150);
			main.setFitWidth(200);
			main.setTranslateX(540);
			main.setTranslateY(450);
		});
		ImageView Music = createButtonMusic(615, 400, 50, 50);
		settingRoot.getChildren().addAll(setting,main,Music,att,def,Hp,dam,df,hp);
	}
	private static void makehitSound() {
		hitSoundPlayer.stop();
		hitSoundPlayer.play();
	}
	private static ImageView createButtonMusic(int x,int y,int w,int h) {
		String path = isMusic ? "Music_open.png":"Music_Close.png" ;
		ImageView Music = new ImageView(SetImage(path));
		Music.setOnMouseClicked(e->{
			if (isMusic) {
				setMusic(false);
				media.pause();
				Music.setImage(SetImage("Music_Close.png"));
			}else {
				setMusic(true);
				Music.setImage(SetImage("Music_open.png"));
				media.play();
			}
		});
		Music.setOnMouseEntered(e->{
			Music.setFitWidth(w+20);
			Music.setFitHeight(h+20);
			Music.setTranslateX(x-10);
			Music.setTranslateY(y-10);
		});
		Music.setOnMouseExited(e->{
			Music.setFitWidth(w);
			Music.setFitHeight(h);
			Music.setTranslateX(x);
			Music.setTranslateY(y);
		});
		Music.setTranslateX(x);
		Music.setTranslateY(y);
		Music.setFitWidth(w);
		Music.setFitHeight(h);
		return Music;
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
	private static Media SetMedia(String mediaPath) {
		Media bg = null;
		try {
            String classLoaderPath = ClassLoader.getSystemResource(mediaPath).toString();
            bg = new Media(classLoaderPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not fount: "+ mediaPath);
        }
		return bg;
	}
	private static Text Scoreboard() {
		Text t = new Text("Coins: " + Integer.toString(getScore()));
		t.setFont(font);
		t.setStroke(Color.BLUE);
		t.setStrokeDashOffset(5);
		t.setFill(Color.BLACK);
		t.prefHeight(60);
		t.prefWidth(60);
		t.setTranslateX(1130);
		t.setTranslateY(50);
		return t;
	}
	private static void editUi(int idx) { // idx indicate behavior of this method
		if (idx == 0) {
			double currentHp = ((Player) player).getHp();
			double maxHp = ((Player) player).getMaxhp();
			if (currentHp > 0) {
				H_bar.setProgress(currentHp / maxHp);
			}else {
				H_bar.setProgress(0);
				appRoot.getChildren().clear();
			}
		}else if (idx == 1){
			Platform.runLater(()->{
				Score_on_screne.setText("Coins: "+Integer.toString(getScore()));
			});
		}else if (idx == 2){
			((Text)setUi.get(0)).setText(Integer.toString(((Player)player).getHp()) + "/" + Integer.toString(((Player)player).getMaxhp()));
		}else if (idx == 3) {
			((Text)setUi.get(1)).setText(Integer.toString(((Player)player).getDefense()));
		}else {
			((Text)setUi.get(2)).setText(Integer.toString(((Player)player).getAtk()));
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
	public static void createShadowEffect(ImageView targetImageView,Color color) {
	    DropShadow attackEffect = new DropShadow();
	    attackEffect.setColor(color);
	    attackEffect.setRadius(0);

	    targetImageView.setEffect(attackEffect);

	    Timeline flickerEffect = new Timeline(
	        new KeyFrame(Duration.ZERO, new KeyValue(attackEffect.radiusProperty(), 0)),
	        new KeyFrame(Duration.millis(100), new KeyValue(attackEffect.radiusProperty(), 100)),
	        new KeyFrame(Duration.millis(200), new KeyValue(attackEffect.radiusProperty(), 0))
	    );
	    flickerEffect.setCycleCount(3);
	    flickerEffect.setOnFinished(e -> targetImageView.setEffect(null));
	    flickerEffect.play();
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


	public static boolean isWalk() {
		return isWalk;
	}


	public static void setWalk(boolean isWalk) {
		GameStart.isWalk = isWalk;
	}


	public static boolean isDamage() {
		return isDamage;
	}


	public static void setDamage(boolean isDamage) {
		GameStart.isDamage = isDamage;
	}


	public static boolean isMusic() {
		return isMusic;
	}


	public static void setMusic(boolean isMusic) {
		GameStart.isMusic = isMusic;
	}


	public static boolean isEsc() {
		return isEsc;
	}


	public static void setEsc(boolean isEsc) {
		GameStart.isEsc = isEsc;
	}


	public static boolean isPaused() {
		return isPaused;
	}


	public static void setPaused(boolean isPaused) {
		GameStart.isPaused = isPaused;
	}


	public static boolean isEscHeld() {
		return isEscHeld;
	}


	public static void setEscHeld(boolean isEscHeld) {
		GameStart.isEscHeld = isEscHeld;
	}


	public static ImageView getBackground() {
		return Background;
	}


	public static void setBackground(ImageView background) {
		Background = background;
	}


	public static AnimationTimer getTime() {
		return time;
	}


	public static Font getFont() {
		return font;
	}


	public static void setFont(Font font) {
		GameStart.font = font;
	}

	
}
