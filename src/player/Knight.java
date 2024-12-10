package player;

public class Knight extends Player{ //อัศวิน
	public Knight() {
		super(750,80,80,30,"KnightRight.png","KnightLeft.png","KnightWalkRight.png",
				"KnightWalkRight2.png","KnightWalkLeft.png","KnightWalkLeft2.png","KnightLeft.png","KnightFight.png"); //hp attack speed defense dulability 
	}
	public String toString() {
		return "Knight";
	}
}
