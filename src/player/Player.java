package player;

public class Player {
	private int hp;
	private int attack; 
	private int speed;
	private int defense;
	public Player(int hp , int attack , int speed , int defense) {
		this.setHp(hp);
		this.setAttack(attack);
		this.setSpeed(speed);
		this.setDefense(defense);
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = Math.max(0, hp);
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = Math.max(0, attack);
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
