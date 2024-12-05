package enemy;

import base.Magicable;
import player.Player;

public class FireDemon extends Enemy implements Magicable{
	private int MagicAtk;
	public FireDemon() {
		super(100,80,60); //hp atk speed
		this.setMagicAtk(40);
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
	
}
