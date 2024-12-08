package obstacle;

public class ShotX extends Shot {

	public ShotX(int posX, int posY) {
		super(posX, posY);
	}
	public void update() {
		this.setPosX(this.getPosX()-this.getSpeed());
	}
}
