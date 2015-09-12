package event;

import rule.holdem.PlayerMove;
import entity.Player;

public interface PlayerMoveListener
{
	void playerMovePerformed(Player player, PlayerMove playerMove, double value);
}
