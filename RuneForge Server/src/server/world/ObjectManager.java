package server.world;

import java.util.ArrayList;

import server.model.objects.GameObject;
import server.util.Misc;
import server.model.players.Client;
import server.model.players.PlayerHandler;

/**
 * @author Sanity
 */

public class ObjectManager {

	public ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
	public void process() {
		for (GameObject o : objects) {
			if (o.tick > 0)
				o.tick--;
			else {
				updateObject(o);
				toRemove.add(o);
			}		
		}
		for (GameObject o : toRemove) {
			if (isObelisk(o.newId)) {
				int index = getObeliskIndex(o.newId);
				if (activated[index]) {
					activated[index] = false;
					teleportObelisk(index);
				}
			}
			objects.remove(o);	
		}
		toRemove.clear();
	}
	
	public void removeObject(int x, int y) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client)PlayerHandler.players[j];
				c.getPA().object(-1, x, y, 0, 10);			
			}	
		}	
	}
	
	public void updateObject(GameObject o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client)PlayerHandler.players[j];
				c.getPA().object(o.newId, o.objectX, o.objectY, o.face, o.type);			
			}	
		}	
	}
	
	public void placeObject(GameObject o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client)PlayerHandler.players[j];
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60)
					c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
			}	
		}
	}
	
	public GameObject getObject(int x, int y, int height) {
		for (GameObject o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height)
				return o;
		}	
		return null;
	}
	
	public void loadObjects(Client c) {
		if (c == null)
			return;
		for (GameObject o : objects) {
			if (loadForPlayer(o,c))
				c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
		}
		loadCustomSpawns(c);
		if (c.distanceToPoint(2813, 3463) <= 60) {
			c.getFarming().updateHerbPatch();
		}
	}
	
	public void loadCustomSpawns(Client c) {
	
		/*
		* Start of Home
		*/
//dzone altars
c.getPA().checkObjectSpawn(6552, 2613, 3087, 0, 10);
c.getPA().checkObjectSpawn(409, 2611, 3087, 0, 10);
c.getPA().checkObjectSpawn(411, 2609, 3087, 0, 10); //Curse Prayers
//dzone mining
c.getPA().checkObjectSpawn(2091, 2349, 9818, -1, 10);
c.getPA().checkObjectSpawn(2094, 2350, 9818, -1, 10);
c.getPA().checkObjectSpawn(2092, 2351, 9818, -1, 10);
c.getPA().checkObjectSpawn(2097, 2352, 9818, -1, 10);
c.getPA().checkObjectSpawn(2103, 2353, 9818, -1, 10);
c.getPA().checkObjectSpawn(2105, 2354, 9818, -1, 10);
c.getPA().checkObjectSpawn(14859, 2355, 9818, -1, 10);
//dzone smithing
c.getPA().checkObjectSpawn(3044, 2358, 9816, -1, 10);
c.getPA().checkObjectSpawn(2782, 2358, 9814, 0, 10);
c.getPA().checkObjectSpawn(2782, 2360, 9814, 0, 10);
c.getPA().checkObjectSpawn(2782, 2357, 9816, -1, 10);
//dzone farming
c.getPA().checkObjectSpawn(8389, 2363, 9814, 0, 10);
c.getPA().checkObjectSpawn(8143, 2365, 9813, 0, 10);
c.getPA().checkObjectSpawn(8143, 2362, 9816, 0, 10);
//dzone wcing
c.getPA().checkObjectSpawn(1276, 2363, 9810, 0, 10);
c.getPA().checkObjectSpawn(1281, 2363, 9807, 0, 10);
c.getPA().checkObjectSpawn(1307, 2363, 9805, 0, 10);
c.getPA().checkObjectSpawn(1309, 2359, 9805, 0, 10);
c.getPA().checkObjectSpawn(1306, 2356, 9806, 0, 10);
//dzone fishing
c.getPA().checkObjectSpawn(2728, 2350, 9806, 1, 10);
c.getPA().checkObjectSpawn(2728, 2589, 3415, 1, 10);
/*dzone theifting
c.getPA().checkObjectSpawn(4878, 2347, 9815, 0, 10);
c.getPA().checkObjectSpawn(4874, 2347, 9814, 0, 10);
c.getPA().checkObjectSpawn(4877, 2347, 9813, 1, 10);
c.getPA().checkObjectSpawn(4875, 2347, 9811, 0, 10);
c.getPA().checkObjectSpawn(4876, 2347, 9812, 0, 10);
*/
//rift
c.getPA().checkObjectSpawn(2878, 2343, 9800, 0, 10);
//ENDING ALL DONATOR ZONE


		//Remove Objects
		c.getPA().checkObjectSpawn(-1, 2965, 3381, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3107, 3274, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3253, 3267, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3079, 3421, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2532, 4713, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2531, 4713, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2532, 4714, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2531, 4714, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3081, 3422, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3420, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3081, 3420, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3082, 3421, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3083, 3421, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3083, 3420, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3082, 3420, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3081, 3419, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3418, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3076, 3445, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3445, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3081, 3445, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3082, 3445, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3082, 3444, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3443, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3442, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3441, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3440, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3439, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3438, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3443, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3337, 3334, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3339, 3340, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3353, 3336, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3353, 3334, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3354, 3334, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3351, 3334, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3350, 3334, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3350, 3336, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3353, 3336, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3350, 3335, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3353, 3335, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3362, 3343, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3362, 3344, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3361, 3344, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3363, 3345, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3366, 3347, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3366, 3345, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3365, 3346, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3362, 3343, 0, 10);
		/*c.getPA().checkObjectSpawn(4874, 3264, 3168, 0, 10);//bread stall[1]
		c.getPA().checkObjectSpawn(4875, 3264, 3167, 0, 10);//silk stall[25]
		c.getPA().checkObjectSpawn(4876, 3264, 3166, 0, 10);//fur stall[50]
		c.getPA().checkObjectSpawn(4877, 3264, 3165, 0, 10);//spice stall[75]
		c.getPA().checkObjectSpawn(4878, 3264, 3164, 0, 10);//silver stall[90]
		*/
		c.getPA().checkObjectSpawn(-1, 3077, 3442, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3441, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3440, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3439, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3438, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3076, 3439, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3076, 3442, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3078, 3443, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3078, 3441, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3081, 3442, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3081, 3439, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3079, 3439, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3081, 3437, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3081, 3436, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3079, 3445, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3075, 3424, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3074, 3421, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3074, 3422, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3075, 3421, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3075, 3422, 0, 10);
		//Add Objects
		c.getPA().checkObjectSpawn(2213, 3359, 3345, 0, 10);
		c.getPA().checkObjectSpawn(2213, 3361, 3345, 0, 10);
		c.getPA().checkObjectSpawn(2213, 3362, 3345, 0, 10);
		c.getPA().checkObjectSpawn(2213, 3363, 3345, 0, 10);
		c.getPA().checkObjectSpawn(2213, 3364, 3345, 0, 10);
		c.getPA().checkObjectSpawn(2213, 3360, 3345, 0, 10);
		c.getPA().checkObjectSpawn(6552, 3379, 3283, 1, 10);//Ancient Altar
		c.getPA().checkObjectSpawn(14002, 3384, 3266, 3, 10);//Signs
		c.getPA().checkObjectSpawn(14002, 3384, 3265, 3, 10);//Signs
		c.getPA().checkObjectSpawn(14002, 3384, 3264, 3, 10);//Signs
		c.getPA().checkObjectSpawn(14002, 3355, 3266, 3, 10);//Signs
		c.getPA().checkObjectSpawn(14002, 3355, 3265, 3, 10);//Signs
		c.getPA().checkObjectSpawn(14002, 3355, 3264, 3, 10);//Signs
		c.getPA().checkObjectSpawn(14002, 3355, 3263, 3, 10);//Signs
		c.getPA().checkObjectSpawn(14002, 3330, 3262, 0, 10);//Signs
		c.getPA().checkObjectSpawn(14002, 3329, 3262, 0, 10);//Signs
		c.getPA().checkObjectSpawn(14002, 3328, 3260, 3, 10);//Signs
	    c.getPA().checkObjectSpawn(14002, 3328, 3261, 3, 10);//Signs
		c.getPA().checkObjectSpawn(14002, 3328, 3204, 3, 10);//Signs
	    c.getPA().checkObjectSpawn(14002, 3328, 3203, 3, 10);//Signs
		/*
		* End of Home
		*/

		//c.getPA().checkObjectSpawn(13615, 2002, 4430, 0, 10);//portals
		//c.getPA().checkObjectSpawn(13620, 2007, 4430, 0, 10);
		//c.getPA().checkObjectSpawn(13619, 2014, 4430, 0, 10);
		c.getPA().checkObjectSpawn(2470, 2871, 5279, 1, 10); // Prayers
						//c.getPA().checkObjectSpawn(2996, 3375, 3267, 0, 10); //Donatorchest
		c.getPA().checkObjectSpawn(1596, 3008, 3850, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3008, 3849, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10307, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10308, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10311, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10312, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10341, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10342, 1, 0);
		c.getPA().checkObjectSpawn(2213, 3047, 9779, 1, 10);
		c.getPA().checkObjectSpawn(2213, 3080, 9502, 1, 10);
		c.getPA().checkObjectSpawn(1530, 3093, 3487, 1, 10);
                c.getPA().checkObjectSpawn(1306, 2012, 4437, 1, 10);
                c.getPA().checkObjectSpawn(1306, 2003, 4437, 1, 10);
                c.getPA().checkObjectSpawn(1306, 3110, 3508, 1, 10);
                c.getPA().checkObjectSpawn(14859, 3046, 9776, 0, 10);//Rune Rock
		c.getPA().checkObjectSpawn(14859, 3045, 9776, 0, 10);//Rune Rock
                c.getPA().checkObjectSpawn(14859, 3044, 9776, 0, 10);//Rune Rock
		c.getPA().checkObjectSpawn(4161, 3291, 9178, 0, 10);//dung portal



		//c.getPA().checkObjectSpawn(12356, 3272, 3169, 0, 10);
		//c.getPA().checkObjectSpawn(2403, 3272, 3168, 1, 10);
                                          //X     Y     ID -> ID X Y
		c.getPA().checkObjectSpawn(2213, 2855, 3439, -1, 10);
		c.getPA().checkObjectSpawn(2090, 2839, 3440, -1, 10);
		c.getPA().checkObjectSpawn(2094, 2839, 3441, -1, 10);
		c.getPA().checkObjectSpawn(2092, 2839, 3442, -1, 10);
		c.getPA().checkObjectSpawn(2096, 2839, 3443, -1, 10);
		c.getPA().checkObjectSpawn(2102, 2839, 3444, -1, 10);
		c.getPA().checkObjectSpawn(2105, 2839, 3445, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2530, 4712, 0, 10);
		c.getPA().checkObjectSpawn(1276, 2843, 3442, 0, 10);
		c.getPA().checkObjectSpawn(1281, 2844, 3499, 0, 10);
		//c.getPA().checkObjectSpawn(4156, 3083, 3440, 0, 10);
		c.getPA().checkObjectSpawn(1308, 2846, 3436, 0, 10);
		c.getPA().checkObjectSpawn(1309, 2846, 3439, -1, 10);
		c.getPA().checkObjectSpawn(1306, 2850, 3439, -1, 10);
		c.getPA().checkObjectSpawn(2783, 2841, 3436, 0, 10);
		c.getPA().checkObjectSpawn(2728, 2861, 3429, 0, 10);
		c.getPA().checkObjectSpawn(3044, 2857, 3427, -1, 10);
		c.getPA().checkObjectSpawn(320, 3048, 10342, 0, 10);
		c.getPA().checkObjectSpawn(409, 2533, 4711, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2844, 3440, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2846, 3437, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2840, 3439, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2841, 3443, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2851, 3438, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3496, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3091, 3495, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3494, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3095, 3498, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3095, 3499, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3098, 3496, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3096, 3498, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3093, 3488, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3092, 3488, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3092, 3496, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2538, 4713, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2535, 4714, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2543, 4712, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2539, 4702, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2537, 4720, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2536, 4719, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2536, 4718, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2534, 4717, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2534, 4718, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2536, 4720, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2535, 4721, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2535, 4720, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2534, 4720, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2533, 4720, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2533, 4719, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2539, 4720, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2545, 4718, -1, 10);
		
		
		/**
		 *
		 * Start Of Home
		 *
		**/
		/*c.getPA().checkObjectSpawn(-1, 2463, 3494, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2463, 3495, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2463, 3496, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2465, 3498, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2466, 3498, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2467, 3498, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2468, 3494, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2468, 3495, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2468, 3496, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2468, 3497, 0, 10);*/

		
	 if (c.heightLevel == 0) {
			c.getPA().checkObjectSpawn(2492, 2911, 3614, 1, 10);
		 }else{
			c.getPA().checkObjectSpawn(-1, 2911, 3614, 1, 10);
	}
	}
	
	public final int IN_USE_ID = 14825;
	public boolean isObelisk(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return true;
		}
		return false;
	}
	public int[] obeliskIds = {14829,14830,14827,14828,14826,14831};
	public int[][] obeliskCoords = {{3154,3618},{3225,3665},{3033,3730},{3104,3792},{2978,3864},{3305,3914}};
	public boolean[] activated = {false,false,false,false,false,false};
	
	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				addObject(new GameObject(14825, obeliskCoords[index][0], obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new GameObject(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new GameObject(14825, obeliskCoords[index][0], obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
				addObject(new GameObject(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
			}
		}	
	}
	
	public int getObeliskIndex(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return j;
		}
		return -1;
	}
	
	public void teleportObelisk(int port) {
		int random = Misc.random(5);
		while (random == port) {
			random = Misc.random(5);
		}
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client)PlayerHandler.players[j];
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if (c.goodDistance(c.getX(), c.getY(), obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2, 1)) {
					c.getPA().startTeleport2(obeliskCoords[random][0] + xOffset, obeliskCoords[random][1] + yOffset, 0);
				}
			}		
		}
	}
	
	public boolean loadForPlayer(GameObject o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60 && c.heightLevel == o.height;
	}
	
	public void addObject(GameObject object) {
		if (getObject(object.objectX, object.objectY, object.height) == null) {
			objects.add(object);
			placeObject(object);
		}	
	}




}