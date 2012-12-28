package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.Connection;
import server.util.Misc;

/**
 * Chat 
 **/
public class Chat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.setChatTextEffects(c.getInStream().readUnsignedByteS());
		c.setChatTextColor(c.getInStream().readUnsignedByteS());
        c.setChatTextSize((byte)(c.packetSize - 2));
        c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
			String[] stuffz = {".net" , ".com" , ".info", ".c 0 m", ".org" , ".tk" , ".9hz",".c0m",".scape" , ".biz", "c o m", "c 0 m", "c0m"}; //add more!
					String term = Misc.textUnpack(c.getChatText(), c.packetSize - 2).toLowerCase();
			if(c.cantSay >= 5){
				c.disconnected = true;
			}
			if (c.said >= 15) {
				c.teleportToX = 3102;
                c.teleportToY = 9516;
				c.sendMessage("You have been muted!");
				Connection.addNameToMuteList(c.playerName);
			}
			for(int i = 0; i < stuffz.length; i++) {
				if(term.contains(stuffz[i])) {
					if (!c.isOwner()) {
						c.cantSay++;
						c.said++;
						c.sendMessage("Please, do not use that term, you will be muted after 10 times!");
						return;
					}
				}
			}
		if(c.muted) {
			if(c.mute > System.currentTimeMillis()){
				double minutes = (c.mute-System.currentTimeMillis())/1000/60;
				c.sendMessage("You're still muted for "+minutes+" minutes...");
				c.sendMessage("No one is going to see what you speak.");
				return;
			} else {
				c.muted = false;
				c.setChatTextUpdateRequired(true);
			}
		}
		c.lastMessage("PUBLIC MESSAGE: "+Misc.textUnpack(c.getChatText(), c.getChatTextSize()));
		if (!Connection.isMuted(c))
			c.setChatTextUpdateRequired(true);
	}	
}