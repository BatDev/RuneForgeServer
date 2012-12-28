package server.model.players;

/**
 * @author I'm A Jerk
 */

public class DonatorControlPanel {
	
	
	private Client c;
	public DonatorControlPanel (Client c) {
		this.c = c;	
	}
	
	public void restoreSpec() {
		if (c.specAmount >= 10) {
			c.sendMessage("Your special is already full!");
			return;
		}
		if (c.inWild()) {
			c.sendMessage("You cannot do this in Wild.");
			return;
		}
		if (c.underAttackBy > 0){
			c.sendMessage("You cannot do this in Combat.");
			return;
		}
		c.nextChat = 1337;
		c.getPA().sendFrame126("Would you like to restore your special for 1 RuneForge point?", 2460);
		c.getPA().sendFrame126("Yes", 2461);
		c.getPA().sendFrame126("No", 2462);
		c.getPA().sendFrame164(2459);
	}
	

}