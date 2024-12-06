package obstacle;

import base.Unit;
import enemy.Enemy;
import player.Player;

public class Log {
	public void attack(Unit other) {
		if(!IsSameTeam(other)) {
			Player player = (Player) other;
			player.setHp(player.getHp()-atk);
			if(player.getHp() == 0) {
				player.setAlive(false);
			}
		}
	}
	public boolean IsSameTeam(Unit other) {
		if(other instanceof Enemy) {
			return true;
		}
		return false;
	}
}
