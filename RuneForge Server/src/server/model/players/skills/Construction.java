package server.model.players.skills;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

import server.model.players.Client;
import server.util.Misc;
import java.io.IOException;

/**
 * @author Aintaro.
 * Construction Class.
 */

public class Construction {

	private Client c;

	public Construction(Client c) {
		this.c = c;
	}
	
	/**
	*10 = Objectid.
	*1 = LevelReq.
	*300 = Experience add.
	*960 = item needed.
	*5 = amount of item needed.
	*/
	private int[][] ConstructionObjects = { 
			{10, 1, 300, 960, 5}, //ladder
			{155, 20, 1000, 960, 7}, //bookcase
			{189, 30, 1200, 960, 14}, //bar
			{346, 34, 1300, 960, 9}, //cabinet
			{388, 40, 1521, 960, 7}, // wardrobe
			{428, 44, 1580, 960, 12}, //bed
			{1278, 50, 1700, 960, 3}, //common tree
			{1281, 53, 1756, 960, 5}, //Oak tree
			{1308, 58, 1780, 960, 9}, //Willow tree
			{1307, 60, 1800, 960, 11}, //maple tree
			{1309, 64, 1833, 960, 12}, //yew tree
			{1306, 70, 1900, 960, 14} //magic tree
	};
	/**
	* The object we will be handling will be stored in here.
	*/
	
	private int objectID;
	private long buildDelay;
	//private static final String errorMessage = "You have to be in the construction area to start building!";
	//private String objectLine = "";
	/**
	* Creates the file to save the objects in.
	*/
	public void createObjectsFile() {
		String filePath = "./Data/CFG/construction/" + c.playerName + ".cfg";
		BufferedWriter bw = null;
		
		try 
		{				
			bw = new BufferedWriter(new FileWriter(filePath, true));
			//bw.newLine();
			//bw.newLine();
			//bw.write("[ENDOFSPAWNLIST]", 0, 16);
			//object.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " " + checkTimeOfDay() + "]: " + "[" + c.connectedFrom + "]: " + "" + data + " ");
			
			
			//bw.flush();
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		} 
		finally 
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				
				}
			}
		}
	}
	public void writeObjects(int objectId, int X, int Y, int face, int objectType) {
		String filePath = "./Data/CFG/construction/" + c.playerName + ".cfg";
		BufferedWriter bw = null;
		
		try 
		{				
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("object = " + objectId + "\t" + X + "\t" + Y + "\t" + face + "\t" + objectType);
			bw.newLine();
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		} 
		finally 
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				
				}
			}
		}
		
	}
	
	public void deleteObjects(int X, int Y) {
		String filePath = "./Data/CFG/delete/" + c.playerName + ".cfg";
		BufferedWriter bw = null;
		
		try 
		{				
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("object = " + -1 + "\t" + X + "\t" + Y + "\t" + 0 + "\t" + 10);
			c.getPA().checkObjectSpawn(-1, X, Y, 0, 10);
			bw.newLine();
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		} 
		finally 
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				
				}
			}
		}
	}
	
	public boolean loadDeleteObjects(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[5];
		boolean EndOfFile = false;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./"+FileName));
		} catch(FileNotFoundException fileex) {
			Misc.println(FileName+": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch(IOException ioexception) {
			Misc.println(FileName+": error loading file.");
			return false;
		}
		while(EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("object")) {
					//newNPC(Integer.parseInt(token3[0]), Integer.parseInt(token3[1]), Integer.parseInt(token3[2]), Integer.parseInt(token3[3]), Integer.parseInt(token3[4]), getNpcListHP(Integer.parseInt(token3[0])), Integer.parseInt(token3[5]), Integer.parseInt(token3[6]), Integer.parseInt(token3[7]));
					c.getPA().checkObjectSpawn(Integer.parseInt(token3[0]), Integer.parseInt(token3[1]), Integer.parseInt(token3[2]), Integer.parseInt(token3[3]), Integer.parseInt(token3[4]));
				}
			} else {
			/*
				if (line.equals("[ENDOFSPAWNLIST]")) {
					try { characterfile.close(); } catch(IOException ioexception) { }
					return true;
				}
				*/
			}
			try {
				line = characterfile.readLine();
			} catch(IOException ioexception1) { EndOfFile = true; }
		}
		try { characterfile.close(); } catch(IOException ioexception) { }
		return false;	
	}
	
	/**
	* Loads the saved objects.
	*/
	public boolean loadObjects(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[5];
		boolean EndOfFile = false;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./"+FileName));
		} catch(FileNotFoundException fileex) {
			Misc.println(FileName+": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch(IOException ioexception) {
			Misc.println(FileName+": error loading file.");
			return false;
		}
		while(EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("object")) {
					//newNPC(Integer.parseInt(token3[0]), Integer.parseInt(token3[1]), Integer.parseInt(token3[2]), Integer.parseInt(token3[3]), Integer.parseInt(token3[4]), getNpcListHP(Integer.parseInt(token3[0])), Integer.parseInt(token3[5]), Integer.parseInt(token3[6]), Integer.parseInt(token3[7]));
					c.getPA().checkObjectSpawn(Integer.parseInt(token3[0]), Integer.parseInt(token3[1]), Integer.parseInt(token3[2]), Integer.parseInt(token3[3]), Integer.parseInt(token3[4]));
				}
			} else {
			/*
				if (line.equals("[ENDOFSPAWNLIST]")) {
					try { characterfile.close(); } catch(IOException ioexception) { }
					return true;
				}
				*/
			}
			try {
				line = characterfile.readLine();
			} catch(IOException ioexception1) { EndOfFile = true; }
		}
		try { characterfile.close(); } catch(IOException ioexception) { }
		return false;	
	}
	
	/**
	* Area for construction.
	*/
	public boolean constructionArea() {
		if((c.absX > 1900 && c.absX < 1983 && c.absY > 4992 && c.absY < 5027)) {
			return true;
		}
		return false;
	}
	
	private void sendText(String text, int frameId) {
		c.getPA().sendFrame126(text, frameId);
	}
	
	/**
	*This makes the text show green or red based on their construction level.
	**/
	private void colorConstructor(String text, int frameId, int objectId) {
		for (int[] OBJECTS : ConstructionObjects) {
			if (objectId == OBJECTS[0]) {
				if (c.playerLevel[21] >= OBJECTS[1]) {
					c.getPA().sendFrame126("@gre@" + text, frameId);
					break;
				} else if (c.playerLevel[21] < OBJECTS[1]) {
					c.getPA().sendFrame126("@red@" + text, frameId);
					break;
				} 
			} else {
					c.getPA().sendFrame126("" + text, frameId);
			}
		}
	}
	/**
	* This displays the amount of planks needed for an object.
	*/
	private void displayAmount(int objectId) {
	if (!constructionArea()) {
		//c.sendMessage(errorMessage);
		return;
	}
			for (int[] OBJECTS : ConstructionObjects) {
				if (objectId == OBJECTS[0]) {
					c.getPA().sendFrame126("Construction Level : " + c.playerLevel[21] + " || Construction Xp : " + c.playerXP[21], 21006);
					c.getPA().sendFrame126("Next level at : " + c.getPA().getXPForLevel(c.getPA().getLevelForXP(c.playerXP[21]) + 1), 21007);
					c.getPA().sendFrame126("Level Required : " + OBJECTS[1], 21008);
					c.getPA().sendFrame126("Planks Needed : " + OBJECTS[4], 21009);
					c.getPA().walkableInterface(21005);
					objectID = objectId;
				}
			}
		}
	/**
	* Builds the object
	* Checks for planks, levels...
	*/
	public void buildObject(int X, int Y, int heightLevel, int objectId) {
		objectID = objectId;
		for (int[] OBJECTS : ConstructionObjects) {
			if (objectID == OBJECTS[0]) {
				if (c.getItems().playerHasItem(OBJECTS[3], OBJECTS[4])) {
					if (constructionArea()) {
						if (c.playerLevel[21] >= OBJECTS[1]) {
							c.getItems().DeleteItem(OBJECTS[3], OBJECTS[4]);
							c.getPA().object(objectID, X, Y, heightLevel, 10);
							c.getPA().addSkillXP(OBJECTS[2], 21);
							c.startAnimation(898);
							writeObjects(OBJECTS[0], c.absX, c.absY, 0, 10);
						} else {
							c.sendMessage("You need atleast " + OBJECTS[1] + " Construction to build This.");
							break;
						}
					} else {
						//c.sendMessage(errorMessage);
						break;
					}
				} else {
					c.sendMessage("You need atleast " + OBJECTS[4] + " Planks before you can build!");
					break;
				}
			}
		}
	}
	
	/**
	*This makes the text print on their screen.
	**/
	public void textConstructor() {
			sendText("@mag@Construction Level : " + c.playerLevel[21], 3206);
			sendText("Build", 6272);
			sendText("TELE", 6271);
			sendText("SAVE", 9926);
			sendText("@red@objectSave: " + c.objectSave, 5449);
			colorConstructor("Ladder" , 11941, 10);
			colorConstructor("Bookcase " , 4287, 155);
			colorConstructor("Bar " , 4288, 189);
			colorConstructor("Cabinet" , 4289, 346);
			colorConstructor("Wardrobe " , 4290, 388);
			colorConstructor("Bed " , 11134, 428);
			colorConstructor("Common Tree " , 4291, 1278);
			colorConstructor("Oak Tree " , 4292, 1281);
			colorConstructor("Willow Tree " , 4293, 1308);
			colorConstructor("Maple Tree " , 4294, 1307);
			colorConstructor("Yew Tree " , 4295, 1309);
			colorConstructor("Magic Tree" , 4296, 1306);
			colorConstructor("Ladder12 " , 8935, -1);
			colorConstructor("Ladder13 " , 4297, -1);
			colorConstructor("Ladder14 " , 4298, -1);
			colorConstructor("Ladder15 " , 4299, -1);
			colorConstructor("Ladder16 " , 4300, -1);
			colorConstructor("Ladder17 " , 4301, -1);
			colorConstructor("Ladder18 " , 4302, -1);
			colorConstructor("Ladder19 " , 4303, -1);
			colorConstructor("Ladder20 " , 4304, -1);
	}
	
	public void constructionButtons(Client c, int actionButtonId) {
		if (c.playerRights != 3)
		return;
		switch(actionButtonId) {
			/**
			* this is our build button.
			*/
			case 113234:
				if (System.currentTimeMillis() - buildDelay >= 2000) {
					buildObject(c.absX, c.absY + 1, c.heightLevel, objectID);
					c.getPA().sendFrame126("Construction Level : " + c.playerLevel[21] + " || Construction Xp : " + c.playerXP[21], 21006);
					c.getPA().sendFrame126("Next level at : " + c.getPA().getXPForLevel(c.getPA().getLevelForXP(c.playerXP[21]) + 1), 21007);
					buildDelay = System.currentTimeMillis();
				}
			break;
			/**
			*Teleport to the construction area.
			*/
			case 24126:
				c.getPA().startTeleport(2097, 3206, 0, "modern");
			break;
			/**
			* List of all the objects that can be build.
			*/
			case 113228:
				displayAmount(10);	
			break;
			
			case 16191:
				displayAmount(155);
			break;
			
			case 16192:
				displayAmount(189);
			break;
			
			case 16193:
				displayAmount(346);
			break;
			
			case 16194:
				displayAmount(388);
			break;
			
			case 16195:
				displayAmount(1278);
			break;
			
			case 43126:
				displayAmount(428);
			break;
			
			case 16196:
				displayAmount(1281);
			break;
			
			case 16197:
				displayAmount(1308);
			break;
			
			case 16198:
				displayAmount(1307);
			break;
			
			case 16199:
				displayAmount(1309);
			break;
			
			case 16200:
				displayAmount(1306);
			break;
		}
	}
	
	public void bookCase() {
		c.sendMessage("You found nothing interesting in the bookcase.");
	}
	public void wardRobe() {
		c.getPA().showInterface(3559);
		c.canChangeAppearance = true;
	}
	
	
	
}