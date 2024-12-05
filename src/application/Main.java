package application;

import javafx.stage.Stage;
import player.Player;
import processgame.Platform;

import java.util.ArrayList;
import java.util.HashMap;

import Data.DataLevel;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Main extends Application{
	private HashMap<KeyCode,Boolean> keys = new HashMap<KeyCode,Boolean>();
	private Pane appRoot = new Pane();
	private Pane gameRoot = new Pane();
	private Pane uiRoot = new Pane();
	private boolean isJump;
	private Node player;
	private int levelWidth;
	private ArrayList<Node> platform = new ArrayList<Node>();
	private Point2D playerVelocity = new Point2D(0,0);

	public void start(Stage primaryStage)  throws Exception {
		this.initContent();
		Scene scene = new Scene(appRoot);
		scene.setOnKeyPressed(e->{
			keys.put(e.getCode(), true);
		});
		scene.setOnKeyReleased(e->{
			keys.put(e.getCode(),false);
		});
		primaryStage.setTitle("Collos");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long now) {
				update();
			}
		};
		timer.start();
	}
	private void update() {
		if (isPressed(KeyCode.W) && player.getTranslateY()  >=5) {
			jumpPlayer();
		}
		if (isPressed(KeyCode.A) && player.getTranslateX() >= 5) {
			movePlayerX(-5);
		}
		if (isPressed(KeyCode.D) && player.getTranslateX() + 40  <= levelWidth - 5) {
			movePlayerX(5);
		}
		if (playerVelocity.getY() < 10) {
			playerVelocity = playerVelocity.add(0,1);
		}
		movePlayerY((int)playerVelocity.getY());
	}
	private boolean isPressed(KeyCode key) {
		return keys.getOrDefault(key, false);
	}
	private void movePlayerX(int value) {
		boolean moveRight = value > 0;
		for (int i = 0;i<Math.abs(value);i++) {
			for (Node pt:platform) {
				if (player.getBoundsInParent().intersects(pt.getBoundsInParent())) {
					if (moveRight) {
						if (player.getTranslateX() + 40 == pt.getTranslateX()) {
							return;
						}
					}else {
						if (player.getTranslateX() == pt.getTranslateX() + 60) {
							return;
						}
					}
				}
			}
			player.setTranslateX(player.getTranslateX() + (moveRight ? 1:-1)); // if moveRight set translate x to oldX + 1 or oldX -1 when moveLeft 
			
		}
	}
	private void movePlayerY(int value) {
		boolean moveDown = value > 0; // - is mean up + is mean down
		for (int i = 0;i<Math.abs(value);i++) {
			for (Node pt:platform) {
				if (player.getBoundsInParent().intersects(pt.getBoundsInParent())) {
					if (moveDown) {
						if (player.getTranslateY() + 40 == pt.getTranslateY()) {
							player.setTranslateY(player.getTranslateY() - 1);
							isJump = false;
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
			
		}//777
	}
	private void jumpPlayer() {
		if (!isJump) {
			playerVelocity = playerVelocity.add(0,-30);
			isJump = true; //prohibit to double jump
		}
	}
	private void initContent() {
		Rectangle Bg = new Rectangle(1280,720);
		Bg.setFill(Color.LIGHTYELLOW);
		levelWidth = DataLevel.Level1[0].length() * 60;
		for (int i = 0 ;i<DataLevel.Level1.length;i++) {
			String line = DataLevel.Level1[i];
			for (int j = 0;j<line.length();j++) {
				if (line.charAt(j) == '1') {
					Node pt = this.CreateEntity(j*60, i*60, 60, 60, Color.BROWN);
					platform.add(pt);
					
				}
			}
		}
		player = this.CreateEntity(0, 600, 40, 40, Color.BLUE);
		player.translateXProperty().addListener((obs,old,newValue)->{
			int offset = newValue.intValue();
			if (offset > 640 && offset < levelWidth - 640) {
				gameRoot.setLayoutX(-(offset-640));
			}
		});
		appRoot.getChildren().addAll(Bg,gameRoot,uiRoot);
	}
	private Node CreateEntity(int x,int y,int w,int h,Color color) {
		Rectangle ob = new Rectangle(w,h);
		ob.setTranslateX(x);
		ob.setTranslateY(y);
		
		ob.setFill(color);
		gameRoot.getChildren().add(ob);
		return ob;
	}
	public static void main(String[] args) {
		launch(args);
	}
}
