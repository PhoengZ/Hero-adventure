package player;

public class Knight extends Player{ //อัศวิน
	public Knight() {
		super(240,200,30,100,"KnightRight.png","KnightLeft.png","KnightWalkRight.png",
				"KnightWalkRight2.png","KnightWalkLeft.png","KnightWalkLeft2.png"); //hp attack speed defense dulability 
	}
	public String toString() {
		return "Knight";
	}
}
