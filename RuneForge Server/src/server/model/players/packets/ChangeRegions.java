package server.model.players.packets;

import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.getPA().castleWarsObjects();
		c.getPA().removeObjects();
		//Server.objectHandler.updateObjects(c);
		Server.itemHandler.reloadItems(c);
		Server.objectManager.loadObjects(c);
		
		c.saveFile = true;
		
		if(c.skullTimer > 0) {
			c.isSkulled = true;	
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}

	}
		
}
