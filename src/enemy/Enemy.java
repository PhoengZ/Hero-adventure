package enemy;

public class Enemy {
	private int hp;
	private int atk; 
	private int speed;
	public Enemy(int hp ,int atk , int speed) {
		this.setHp(hp);
		this.setAtk(atk);
		this.setSpeed(speed);
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
	
	
	
}
