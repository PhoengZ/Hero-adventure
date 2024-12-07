package obstacle;

import base.Slowable;
import base.Unit;
import enemy.Enemy;
import player.Player;

public class Log extends Obstacle implements Slowable{
	public Log() {
		super(50,20,10,"");
	}
	public void decreaseEnemySpeed(Unit other,int speeddown) {
		if(other instanceof Player) {
			Player player = (Player) other;
			player.setSpeed(player.getSpeed()-speeddown);
		}
	}
}
