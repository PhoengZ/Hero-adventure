package enemy;


import base.Unit;
import javafx.scene.image.Image;
import player.Player;

public class Enemy  extends Unit {
	private int hp;
	private int atk; 
	private int speed;
	private int defense;
	private Image image;
	private boolean alive;
	public Enemy(int hp ,int atk , int speed ,int defense,String imagePath ) {
		this.setHp(hp);
		this.setAtk(atk);
		this.setSpeed(speed);
		this.setDefense(defense);
		this.setImageByPath(imagePath);
		this.setAlive(true);
	}
	public void attack(Unit other) {
		if(other instanceof Player) {
			Player player = (Player) other;
			player.setHp(player.getHp()-atk);
			if(player.getHp() == 0) {
				player.setAlive(false);
			}
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
		this.speed = Math.max(0, speed);
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = Math.max(0,defense);
	}
	public Image getImage() {
		return image;
	}

	public void setImageByPath(String imagePath) {
    	try {
            String classLoaderPath = ClassLoader.getSystemResource(imagePath).toString();
            this.image=new Image(classLoaderPath);
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
	
	
	
}
