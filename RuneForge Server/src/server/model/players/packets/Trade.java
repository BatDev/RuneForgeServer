package server.model.players.packets;

import server.Config;
import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Trading
 */
public class Trade implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int tradeId = c.getInStream().readSignedWordBigEndian();
		c.getPA().resetFollow();
		
		if(c.arenas()) {
			c.sendMessage("You can't trade inside the arena!");
			return;
		}

		if(c.inWild()) {
			c.sendMessage("You can't trade in wild!");
			return;
		}
		if(c.playerName.equalsIgnoreCase("pro brid"))
		{
		c.sendMessage("You cannot trade!");
		return;
		}
		if(c.playerName.equalsIgnoreCase("hur dur"))
		{
		c.sendMessage("You cannot trade!");
		return;
		}
		if(c.playerName.equalsIgnoreCase("soul over"))
		{
		c.sendMessage("You cannot trade!");
		return;
		}
		if(c.playerName.equalsIgnoreCase("jews die"))
		{
		c.sendMessage("You cannot trade!");
		return;
		}
		if(c.playerName.equalsIgnoreCase("kyle"))
		{
		c.sendMessage("You cannot trade!");
		return;
		}
		if(c.playerName.equalsIgnoreCase("devon"))
		{
		c.sendMessage("You cannot trade!");
		return;
		}
		if(c.playerName.equalsIgnoreCase("vengedvanity"))
		{
		c.sendMessage("You cannot trade!");
		return;
		}
		
		if(c.playerRights == 2 && !Config.ADMIN_CAN_TRADE) {
			c.sendMessage("Trading as an admin has been disabled, U fucktard.");
			return;
		}
		if (tradeId != c.playerId)
			c.getTradeAndDuel().requestTrade(tradeId);
	}
		
}
