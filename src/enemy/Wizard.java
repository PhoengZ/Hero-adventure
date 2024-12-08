package enemy;

import base.Breakable;
import base.Magicable;
import base.Unit;
import player.Player;

public class Wizard extends Enemy implements Magicable,Breakable{
	private int MagicAtk;
	public Wizard() {
		super(80,20,20,10,"Wizard.png","WizardFight.png"); //hp atk speed
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
	public void decreaseEnemyDefense(Unit enemy) {
		if(enemy instanceof Player) {
			Player player = (Player) enemy;
			player.setDefense(player.getDefense()-5);
		}
	}
	public String toString() {
		return "Wizard";
	}
	
}
