package player;

public class Warrior extends Player{ //นักรบ
	public Warrior() {
		super(800,80,70,50,"warriorRight.png","warriorLeft.png","warriorWalkright.png","WarriorWalkRight2.png",
				"warriorWalkleft.png","WarriorWalkleft2.png","WarriorStay.png","WarriorLeft.png");
	}
	public String toString() {
		return "Warrior";
	}
}
