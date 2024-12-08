package enemy;

import base.Armorable;
import base.Unit;
import player.Player;

public class ShadowWarrior extends Enemy implements Armorable{
	public ShadowWarrior() {
		super(100,60,100,20,"ShadowWarrior.png","ShadowWarriorFight.png");
	}
	public void attack(Player other) {
		super.attack(other);
		this.increaseDefense(5);
	}
	public void increaseDefense(int adddefense) {
		this.setDefense(this.getDefense()+adddefense);
	}
	public String toString() {
		return "ShadowWarrior";
	}
}
