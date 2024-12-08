package obstacle;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utils.GameStart;

public class Shot extends Pane {
	private boolean toRemove;
	private int posX, posY, speed = 5;
	private int initialx,initialy;
	private ImageView image;
	static final int size = 30;
	public Shot(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.initialx = posX;
		this.initialy = posY;
		try {
            String classLoaderPath = ClassLoader.getSystemResource("Fireball.png").toString();
            Image img  = new Image(classLoaderPath);
            setImage(new ImageView(img));
            this.image.setFitHeight(size/2);
            this.image.setFitWidth(size/2);
            this.getChildren().clear();
            this.getChildren().add(this.image);
        } catch (Exception e) {
            e.printStackTrace();
        }
		//Circle circle = new Circle(size / 2);
	    //circle.setFill(Color.RED);
//	    /this.getChildren().add(circle);
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

	public boolean isToRemove() {
		return toRemove;
	}

	public void setToRemove(boolean toRemove) {
		this.toRemove = toRemove;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public static int getSize() {
		return size;
	}

	public int getInitialx() {
		return initialx;
	}

	public void setInitialx(int initialx) {
		this.initialx = initialx;
	}

	public int getInitialy() {
		return initialy;
	}

	public void setInitialy(int initialy) {
		this.initialy = initialy;
	}

	public void update() {
		posY+=speed;
	}
	

	public void draw() {
	    Circle circle = new Circle(size / 2);
	    circle.setFill(Color.RED);
	    this.setTranslateX(this.posX);
	    this.setTranslateY(this.posY);
	    
	}
	
	public boolean collide(Node player) {
	    Bounds thisBounds = this.getBoundsInParent();
	    Bounds playerBounds = player.getBoundsInParent();

	    double thisCenterX = thisBounds.getMinX() + thisBounds.getWidth() / 2;
	    double thisCenterY = thisBounds.getMinY() + thisBounds.getHeight() / 2;
	    double playerCenterX = playerBounds.getMinX() + playerBounds.getWidth() / 2;
	    double playerCenterY = playerBounds.getMinY() + playerBounds.getHeight() / 2;

	    double distance = distance(thisCenterX, thisCenterY, playerCenterX, playerCenterY);
	    double thisRadius = Math.min(thisBounds.getWidth(), thisBounds.getHeight()) / 2;
	    double playerRadius = Math.min(playerBounds.getWidth(), playerBounds.getHeight()) / 2;

	    return distance < thisRadius + playerRadius;
	}

	private int distance(double x1, double y1, double x2, double y2) {
	    return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}
	
}
