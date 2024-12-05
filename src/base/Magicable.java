package base;

import player.Player;
public interface Magicable { //โจมตีทะลุเกราะ
	void decreaseHp(Player player);
	void setMagicAtk(int MagicAtk);
	int getMagicAtk();
}
