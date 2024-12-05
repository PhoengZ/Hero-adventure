package player;


public class Player {
	private int hp;
	private int atk; 
	private int speed;
	private int defense;
	public Player(int hp , int atk , int speed , int defense) {
		this.setHp(hp);
		this.setAtk(atk);
		this.setSpeed(speed);
		this.setDefense(defense);
	}
	
	public void attack() {
		
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


	
}
