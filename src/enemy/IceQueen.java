package enemy;

import base.Magicable;
import player.Player;

public class IceQueen extends Enemy implements Magicable{
	private int MagicAtk;
	public IceQueen() {
		super(120,60,40,10,"WarriorRight.png");
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
	public String toString() {
		return "IceQueen";
	}
}
