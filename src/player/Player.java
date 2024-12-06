package player;

import base.Unit;
import enemy.Enemy;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Player extends Unit{

	private int hp;
	private int atk; 
	private int speed;
	private int defense;
	private boolean alive;
	private Image image;
	private ImageView imageView;
	private static Timeline walkRightAnimation;
	private static Timeline walkLeftAnimation;
	private static final int SPRITE_WIDTH = 80; // Width of one frame
    private static final int SPRITE_HEIGHT = 80; // Height of one frame
    private static final int SPRITE_COUNT = 6; // Total number of frames
    private static final int COLUMNS = 6; // Number of columns in the sprite sheet
    private static final int ANIMATION_DURATION = 500;
	public Player(int hp , int atk , int speed , int defense , String imagePath) {
		this.setHp(hp);
		this.setAtk(atk);
		this.setSpeed(speed);
		this.setDefense(defense);
		this.setAlive(true);
		this.setImageByPath(imagePath);
		createWalkRightAnimation(); 
		
	}
	
	public void attack(Enemy enemy) {
		enemy.setHp(enemy.getHp()-atk);
		if(enemy.getHp() == 0) {
			enemy.setAlive(false);
		}
		
	}
	
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = Math.max(0, hp);
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = Math.max(0, atk);
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = Math.max(0,speed);
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = Math.max(0,defense);
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Image getImage() {
		return image;
	}

	public void setImageByPath(String imagePath) {
    	try {
            String classLoaderPath = ClassLoader.getSystemResource(imagePath).toString();
            this.image=new Image(classLoaderPath);
            setImageView(new ImageView(getImage()));
            imageView.setViewport(new Rectangle2D(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
            this.getChildren().clear();
            this.getChildren().add(this.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		imageView.setFitHeight(80);
		imageView.setFitWidth(80);
		this.imageView = imageView;
	}


	
	private void createWalkRightAnimation() {
        walkRightAnimation = new Timeline();
        for (int i = 0; i < SPRITE_COUNT; i++) {
            int frameIndex = i;
            KeyFrame frame = new KeyFrame(
                    Duration.millis(i * ANIMATION_DURATION / SPRITE_COUNT),
                    e -> {
                        int x = (frameIndex % COLUMNS) * SPRITE_WIDTH;
                        int y = (frameIndex / COLUMNS) * SPRITE_HEIGHT;
                        imageView.setViewport(new Rectangle2D(x, y, SPRITE_WIDTH, SPRITE_HEIGHT));
                    }
            );
            walkRightAnimation.getKeyFrames().add(frame);
        }
        walkRightAnimation.setCycleCount(Timeline.INDEFINITE);  // Loop the animation indefinitely
    }

    // Method to start the walking animation when moving to the right
    public void startWalkingRight() {
        walkRightAnimation.play();
    }

    // Method to stop the walking animation when not moving
    public void stopWalking() {
        walkRightAnimation.stop();
        // Optionally, set back to the idle state (first frame)
        imageView.setViewport(new Rectangle2D(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
    }

	@Override
	public void attack(Player player) {
		// TODO Auto-generated method stub
		
	}
	


	
}
