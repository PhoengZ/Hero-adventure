package enemy;

import base.Unit;
import player.Player;

public class BeastMaster extends Enemy{ //นักรบสัตว์ป่า
	public BeastMaster() {
		super(120,80,70,20,"BeastMaster.png","BeastMasterFight.png");
	}
	public void attack(Unit other) {
		if(other instanceof Player) {
			Player player = (Player) other;
			player.setHp(player.getHp()-Math.max(0,(this.getAtk()-player.getDefense())));
			this.setDamage(Math.max(0,(this.getAtk()-player.getDefense())));
			if(player.getHp() == 0) {
				player.setAlive(false);
			}
		}
	}
	public String toString() {
		return "BeastMaster";
	}
}
