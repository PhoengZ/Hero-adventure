package enemy;

import base.Armorable;
import base.Attackable;
import base.Unit;

public class IronGolem extends Enemy implements Armorable,Attackable{
	public IronGolem() {
		super(300,70,10,100,"");
	}
	public void attack(Unit other) {
		super.attack(other);
		this.increaseDefense(5);
	}
	public void increaseDefense(int adddefense) {
		this.setDefense(this.getDefense()+adddefense);
	}
}
