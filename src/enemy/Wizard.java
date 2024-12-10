package enemy;

import base.Breakable;
import base.Magicable;
import base.Unit;
import player.Player;

public class Wizard extends Enemy implements Magicable,Breakable{
	public Wizard() {
		super(80,30,70,10,"Wizard.png","WizardFight.png"); //hp atk speed
	}
	public void attack(Unit other) {
		Player player = (Player) other;
		this.decreaseHp(player);
		this.setDamage(this.getAtk());
		if(player.getHp() == 0) {
			player.setAlive(false);
		}
		this.decreaseEnemyDefense(other);
	}
	public void decreaseHp(Player player) {
		player.setHp(player.getHp()-this.getAtk());
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
