package enemy;

import base.Magicable;
import player.Player;

public class Wizard extends Enemy implements Magicable{
	private int MagicAtk;
	public Wizard() {
		super(80,20,20,10,""); //hp atk speed
		this.setMagicAtk(60);
	}
	public void decreaseHp(Player player) {
		player.setHp(player.getHp()-this.getMagicAtk());
	}
	public void setMagicAtk(int MagicAtk) {
		this.MagicAtk = Math.max(0, MagicAtk);
	}
	public int getMagicAtk() {
		return this.MagicAtk;
	}
	public String toString() {
		return "Wizard";
	}
	
}
