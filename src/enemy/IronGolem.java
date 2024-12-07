package enemy;

import base.Armorable;
import base.Unit;
import player.Player;

public class IronGolem extends Enemy implements Armorable{
	public IronGolem() {
		super(300,70,10,100,"");
	}
	public void attack(Player other) {
		super.attack(other);
		this.increaseDefense(5);
	}
	public void increaseDefense(int adddefense) {
		this.setDefense(this.getDefense()+adddefense);
	}
	public String toString() {
		return "IronGolem";
	}
}
