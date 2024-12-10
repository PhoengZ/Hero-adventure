package enemy;

import base.Magicable;
import base.Unit;
import player.Player;

public class FireDemon extends Enemy implements Magicable{
	public FireDemon() {
		super(100,80,70,20,"FireDemon.png","FireDemonFight.png"); //hp atk speed
	}
	public void attack(Unit other) {
		Player player = (Player) other;
		this.decreaseHp(player);
		this.setDamage(this.getAtk());
		if(player.getHp() == 0) {
			player.setAlive(false);
		}
	}
	public void decreaseHp(Player player) {
		player.setHp(player.getHp()-this.getAtk());
	}
	public String toString() {
		return "FireDemon";
	}
}
