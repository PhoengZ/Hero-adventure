package enemy;

import base.Armorable;
import base.Breakable;
import base.Unit;
import player.Player;

public class IronGolem extends Enemy implements Armorable , Breakable{
	public IronGolem() {
		super(300,100,60,60,"IronGolem.png","IronGolemFight.png");
	}
	public void attack(Unit other) {
		if(other instanceof Player) {
			Player player = (Player) other;
			player.setHp(player.getHp()-Math.max(0,(this.getAtk()-player.getDefense())));
			this.setDamage(Math.max(0,(this.getAtk()-player.getDefense())));
			if(player.getHp() == 0) {
				player.setAlive(false);
			}
			this.increaseDefense(2);
			this.decreaseEnemyDefense(other);
		}
	}
	public void increaseDefense(int adddefense) {
		this.setDefense(this.getDefense()+adddefense);
	}
	public void decreaseEnemyDefense(Unit enemy) {
		if(enemy instanceof Player) {
			Player player = (Player) enemy;
			player.setDefense(player.getDefense()-2);
		}
	}
	public String toString() {
		return "IronGolem";
	}
}
