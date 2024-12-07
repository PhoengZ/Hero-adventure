package base;

import javafx.scene.layout.Pane;
import player.Player;

public abstract class Unit extends Pane{
	public abstract void attack(Unit other);
}
