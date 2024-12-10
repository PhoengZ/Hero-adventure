package enemy;

import base.Armorable;
import base.Unit;
import player.Player;

public class ShadowWarrior extends Enemy implements Armorable{
	public ShadowWarrior() {
		super(100,60,75,20,"ShadowWarrior.png","ShadowWarriorFight.png");
	}
	public void attack(Unit other) {
		if(other instanceof Player) {
			Player player = (Player) other;
			player.setHp(player.getHp()-Math.max(0,(this.getAtk()-player.getDefense())));
			this.setDamage(Math.max(0,(this.getAtk()-player.getDefense())));
			if(player.getHp() == 0) {
				player.setAlive(false);
			}
			this.increaseDefense(5);
		}
	}
	public void increaseDefense(int adddefense) {
		this.setDefense(this.getDefense()+adddefense);
	}
	public String toString() {
		return "ShadowWarrior";
	}
}
