package server.model.players.packets;


import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.Player;
import server.util.Misc;

public class ReportAbuse implements PacketType {
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String name = Misc.longToPlayerName2(c.inStream.readQWord());
		int rule = c.inStream.readUnsignedByte();
		boolean muting = (c.inStream.readUnsignedByte() == 1);
		if(c.report < System.currentTimeMillis()){
		c.report = System.currentTimeMillis()+60000;
		System.out.println("****************************");
		System.out.println("*"+c.playerName+" reported:"+name+"*");
		System.out.println("*for "+c.getRule(rule)+"*");
		System.out.println("****************************");
		c.logReport(name, rule, muting);
		c.sendMessage("Thank you for your report about "+name+".");
		if(muting && c.playerRights >= 1){
			Player.mute(name);
		}
		}else{
		c.sendMessage("You have to wait 60 seconds before reporting again.");
		}
	}
}