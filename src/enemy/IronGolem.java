package enemy;

import base.Armorable;
import base.Breakable;
import base.Unit;
import player.Player;

public class IronGolem extends Enemy implements Armorable , Breakable{
	public IronGolem() {
		super(300,70,80,100,"IronGolem.png","IronGolemFight.png");
	}
	public void attack(Player other) {
		super.attack(other);
		this.increaseDefense(5);
	}
	public void increaseDefense(int adddefense) {
		this.setDefense(this.getDefense()+adddefense);
	}
	public void decreaseEnemyDefense(Unit enemy) {
		if(enemy instanceof Player) {
			Player player = (Player) enemy;
			player.setDefense(player.getDefense()-5);
		}
	}
	public String toString() {
		return "IronGolem";
	}
}
