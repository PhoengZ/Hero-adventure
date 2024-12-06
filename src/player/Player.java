package player;

import base.Attackable;
import base.Unit;
import enemy.Enemy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player extends Pane implements Attackable{
	private int hp;
	private int atk; 
	private int speed;
	private int defense;
	private boolean alive;
	private Image image;
	private ImageView imageView;
	public Player(int hp , int atk , int speed , int defense , String imagePath) {
		this.setHp(hp);
		this.setAtk(atk);
		this.setSpeed(speed);
		this.setDefense(defense);
		this.setAlive(true);
		this.setImageByPath(imagePath);
		
	}
	
	public void attack(Unit other) {
	
		Enemy enemy = (Enemy) other;
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
	
	


	
}
