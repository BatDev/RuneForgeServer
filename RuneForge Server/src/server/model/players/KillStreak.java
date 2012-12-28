package server.model.players;

import server.model.players.Client;

/**
 * Displays the current Killstreak
 * 
 * @author animeking1120
 * 
 */

public class KillStreak {

	/**
	 * The current titles available
	 */

	public static String[] killstreak = { "Noob Pker", "Average Pker",
			"Awesome Pker", "Pro Pker", "Deadly Pker", "Elite Pker",
			"Hero Pker", "Champion Pker", "Godlike Pker", "Unstoppable Pker" };

	/**
	 * Sends the Current Killstreak
	 */

	public static String sendKillStreak(Client c) {
		if (c.deathStreak == 0) {
			switch (c.killStreak) {
			case 5:
				return killstreak[0];
			case 10:
				return killstreak[1];
			case 15:
				return killstreak[2];
			case 20:
				return killstreak[3];
			case 25:
				return killstreak[4];
			case 30:
				return killstreak[5];
			case 35:
				return killstreak[6];
			case 40:
				return killstreak[7];
			case 45:
				return killstreak[8];
			case 50:
				return killstreak[9];
			}
		}
		return "Failed Pker";
	}
}