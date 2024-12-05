package enemy;

import base.Armorable;
import base.Unit;

public class ShadowWarrior extends Enemy implements Armorable{
	public ShadowWarrior() {
		super(100,60,100,20,"");
	}
	public void attack(Unit other) {
		super.attack(other);
		this.increaseDefense(5);
	}
	public void increaseDefense(int adddefense) {
		this.setDefense(this.getDefense()+adddefense);
	}
}
