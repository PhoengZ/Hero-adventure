package enemy;

import base.Magicable;
import base.Unit;
import player.Player;

public class IceQueen extends Enemy implements Magicable{
	public IceQueen() {
		super(120,60,65,10,"IceQueen.png","IceQueenFight.png");
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
		return "IceQueen";
	}
}
