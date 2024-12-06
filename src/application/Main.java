package application;

import javafx.stage.Stage;
import SPane.StartPane;
import SPane.TurnBasePane;
import javafx.application.Application;
import javafx.scene.Scene;


public class Main extends Application{
	/*private HashMap<KeyCode,Boolean> keys = new HashMap<KeyCode,Boolean>();
	private Pane appRoot = new Pane();
	private Pane gameRoot = new Pane();
	private Pane uiRoot = new Pane();
	private boolean isJump;
	private Node player;
	private int levelWidth;
	private Node Door;
	private int Score;
	private Text Score_on_screne;
	private ArrayList<Node> platform = new ArrayList<Node>();
	private ArrayList<ArrayList<Node>> UI = new ArrayList<ArrayList<Node>>();
	private Point2D playerVelocity = new Point2D(0,0);
	private int Round = 0;
	private int temp_hp = 3;//*/

	public void start(Stage primaryStage)  throws Exception {
		StartPane startPane = (StartPane) StartPane.getPane();
        Scene startScene = new Scene(startPane, 1280, 720);
        
        primaryStage.setTitle("Colosseum");
        primaryStage.setResizable(false);
        primaryStage.setScene(startScene);
        primaryStage.show();

        // Request focus for the StartPane
        startPane.requestFocus();
	}
	public static void main(String[] args) {
		launch(args);
	}
	/*private void clear() {
		gameRoot.getChildren().clear();
		platform.clear();
		uiRoot.getChildren().clear();
		UI.clear();
 		appRoot.getChildren().clear();
	}
	private void update() {
		if (isPressed(KeyCode.SPACE) && player.getTranslateY()  >=5) {
			jumpPlayer();
		}
		if (isPressed(KeyCode.LEFT) && player.getTranslateX() >= 5) {
			movePlayerX(-5);
		}
		if (isPressed(KeyCode.RIGHT) && player.getTranslateX() + 40  <= levelWidth - 5) {
			movePlayerX(5);
		}
		if (isPressed(KeyCode.Z)) {
			//Do damage
			setScore(getScore()+1);
			editUi(1);
		}
		if (isPressed(KeyCode.E) && player.getBoundsInParent().intersects(getDoor().getBoundsInParent())) {
			clear();
			setRound(getRound() + 1);
			if (getRound() == 2) {
				//Goto หน้าบอส
			}else {
				initContent(getRound());
			}
		}
		if (playerVelocity.getY() < 10) {
			playerVelocity = playerVelocity.add(0,1);
		}
		movePlayerY((int)playerVelocity.getY());
	}
	private boolean isPressed(KeyCode key) {
		return keys.getOrDefault(key, false);
	}
	private void doDamage(Node player,Node Enemy) {
		//change Node to class of each 
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
							setJump(false);
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
			setJump(true); //prohibit to double jump
		}
	}
	private void initContent(int level) {
		Rectangle Bg = new Rectangle(1280,720);
		Bg.setFill(Color.LIGHTYELLOW);
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
					Node pt = this.CreateEntity(j*60, i*60, 60, 60, Color.BROWN,gameRoot);
					platform.add(pt);
				}
				else if (line.charAt(j) == '2') {
					Node door = CreateEntity(j*60, i*60, 60, 60, Color.BLACK,gameRoot);
					setDoor(door);
					platform.add(door);
				}
			}
		}
		initUi();
		gameRoot.setLayoutX(0); //reset มุมกล้อง
	    gameRoot.setLayoutY(0); //reset มุมกล้อง 
		player = this.CreateEntity(0, 600, 40, 40, Color.BLUE,gameRoot);
		player.translateXProperty().addListener((obs,old,newValue)->{
			int offset = newValue.intValue();
			if (offset > 640 && offset < levelWidth - 640) {
				gameRoot.setLayoutX(-(offset-640));
			}
		});
		appRoot.getChildren().addAll(Bg,gameRoot,uiRoot);
	}
	private void initUi() {
		ArrayList<Node> health = new ArrayList<Node>();
		int hp = (getRound() == 0 ? 2:3);// real is hp = player.gethp;
		for (int i = 0;i<hp;i++) {
			Node bar = CreateEntity(20 + (10*i*5),10,30,30,Color.RED,uiRoot);
			health.add(bar);
		}
		UI.add(health);
		Score_on_screne = Scoreboard();
		uiRoot.getChildren().add(Score_on_screne);
	}
	private Text Scoreboard() {
		Text t = new Text("Score: " + Integer.toString(getScore()));
		t.setFont(new Font(22));
		t.prefHeight(40);
		t.prefWidth(40);
		t.setTranslateX(1180);
		t.setTranslateY(30);
		return t;
	}
	private void editUi(int idx) { // idx indicate behavior of this method
		if (idx == 0) {
			if (temp_hp != 0) {
				UI.get(idx).removeLast();
				uiRoot.getChildren().removeFirst();
			}else {
				//บอกว่า player ตายแล้วก็ขึ้นว่า แพ้แล้วไปหน้า start
			}
		}else {
			Score_on_screne.setText("Score: "+Integer.toString(getScore()));
		}
	}
	private Node CreateEntity(int x,int y,int w,int h,Color color,Pane p) {
		Rectangle ob = new Rectangle(w,h);
		ob.setTranslateX(x);
		ob.setTranslateY(y);
		
		ob.setFill(color);
		p.getChildren().add(ob);
		return ob;
	}
	
	public Node getPlayer() {
		return player;
	}
	public void setPlayer(Node player) {
		this.player = player;
	}
	public boolean isJump() {
		return isJump;
	}
	public void setJump(boolean isJump) {
		this.isJump = isJump;
	}
	public Node getDoor() {
		return Door;
	}
	public void setDoor(Node door) {
		Door = door;
	}
	public int getRound() {
		return Round;
	}
	public void setRound(int round) {
		Round = round;
	}
	public int getScore() {
		return Score;
	}
	public void setScore(int score) {
		Score = score;
	}
	*/
}
