package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;

public class ItemOnGroundItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.getInStream().readSignedWordBigEndian();
		int itemUsed = c.getInStream().readSignedWordA();
		int groundItem = c.getInStream().readUnsignedWord();
		c.getInStream().readSignedWordA();
		c.getInStream().readSignedWordBigEndianA();
		c.getInStream().readUnsignedWord();
		
		switch(itemUsed) {
		
		default:
			if(c.playerRights == 3)
				Misc.println("ItemUsed "+itemUsed+" on Ground Item "+groundItem);
			break;
		}
	}

}
