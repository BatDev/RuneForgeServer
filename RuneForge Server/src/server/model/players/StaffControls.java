package server.model.players;

import server.Config;

/**
	* made by nathan and fritz
	 **/

public class StaffControls {

	public boolean clickButtons(int id) {
		if(System.currentTimeMillis() - clickDelay < 1000) {
			return false;
		}
		clickDelay = System.currentTimeMillis();
		if (id == 89227) {
			openInput(1);
			return true;
		}
		return false;
	}

	public void acceptInput(String msg) {
	 String playerCommand = c.getInStream().readString();
		msg = msg.trim();
			if (inputType == 1) {
			otherName = msg;
			if (otherOnline()) {
			PlayerHandler.getPlayerByName(msg);
				try {	
					String playerToBan = playerCommand.substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
		}
	}

	public void openInput(int id) {
		inputType = id;
		c.getOutStream().createFrame(2);
	}

	public boolean otherOnline() {
		if (getPlayer() == null) {
			c.sendMessage("The player is not online");
		}
		return getPlayer() != null;
	}

	public Client getPlayer() {
		return PlayerHandler.getPlayerByName(otherName);
	}

	public StaffControls(Client c) {
		this.c = c;
	}

	private Client c;
	public int inputType = 0;
	public String otherName = "";	
	public long clickDelay = 0;
}
