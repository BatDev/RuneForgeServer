package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import server.event.EventManager;
import server.model.npcs.NPCHandler;
import server.model.npcs.NPCDrops;
import server.clip.region.Region;
import server.clip.region.ObjectDef;
import server.model.players.PlayerHandler;
import server.model.players.Player;
import server.model.players.Client;
import server.model.players.PlayerSave;
import server.model.minigames.*;
import server.net.ConnectionHandler;
import server.net.ConnectionThrottleFilter;
import server.util.SimpleTimer;
import server.util.log.Logger;
import server.world.ItemHandler;
import server.world.ObjectHandler;
import server.world.ObjectManager;
//import server.util.MadTurnipConnection;
import server.world.ShopHandler;
import server.world.ClanChatHandler;


/**
 * Server.java
 *
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30
 *
 */

public class Server {
	
		private static WarriorsGuild warriorsGuild = new WarriorsGuild();
	public static WarriorsGuild getWarriorsGuild() {
		return warriorsGuild;
	}
	public static boolean sleeping;
	public static int cycleRate;
	public static boolean UpdateServer = false;
	public static long lastMassSave = System.currentTimeMillis();
	private static IoAcceptor acceptor;
	private static ConnectionHandler connectionHandler;
	private static ConnectionThrottleFilter throttleFilter;
	private static SimpleTimer engineTimer, debugTimer;
	private static long cycleTime, cycles, totalCycleTime, sleepTime;
	private static DecimalFormat debugPercentFormat;
	public static boolean shutdownServer = false;		
	public static boolean shutdownClientHandler;			
	public static int serverlistenerPort; 
	public static ItemHandler itemHandler = new ItemHandler();
	public static PlayerHandler playerHandler = new PlayerHandler();
	public static NPCHandler npcHandler = new NPCHandler();
	public static ShopHandler shopHandler = new ShopHandler();
	public static ObjectHandler objectHandler = new ObjectHandler();
	public static ObjectManager objectManager = new ObjectManager();
	public static CastleWars castleWars = new CastleWars();
	public static FightPits fightPits = new FightPits();
	public static PestControl pestControl = new PestControl();
	public static NPCDrops npcDrops = new NPCDrops();
	public static ClanChatHandler clanChat = new ClanChatHandler();
	public static FightCaves fightCaves = new FightCaves();
			public static int days, hours, minutes, seconds;
	public static RFD rfd = new RFD();

	//public static WorldMap worldMap = new WorldMap();
	public static long[] TIMES = new long[5];
	//public static DatabaseFunctions database = null;
	//private static final WorkerThread engine = new WorkerThread();
	
	static {
		if(!Config.SERVER_DEBUG) {
			serverlistenerPort = 43594;
		} else {
			serverlistenerPort = 43594;
		}
		cycleRate = 575;
		shutdownServer = false;
		engineTimer = new SimpleTimer();
		debugTimer = new SimpleTimer();
		sleepTime = 0;
		debugPercentFormat = new DecimalFormat("0.0#%");
	}
	//height,absX,absY,toAbsX,toAbsY,type
    /*public static final boolean checkPos(int height,int absX,int absY,int toAbsX,int toAbsY,int type)
    {
        return I.I(height,absX,absY,toAbsX,toAbsY,type);
    }*/
	public static void main(java.lang.String args[]) throws NullPointerException, IOException {
		/**
		 * Starting Up Server
		 */
		 
		System.setOut(new Logger(System.out));
		System.setErr(new Logger(System.err));
		//database = new DatabaseFunctions();
		System.out.println("Launching RuneForge");
		//MadTurnipConnection md = new MadTurnipConnection();
	      //md.start();
		//try {
	//Database.init();
	//} catch (Exception e) {
	//e.printStackTrace();
	//return;
	//}
		/**
		 * World Map Loader
		 */
		//if(!Config.SERVER_DEBUG)
			//VirtualWorld.init();
		//WorldMap.loadWorldMap();	
				ObjectDef.loadConfig();
		Region.load();

		/**
		 * Script Loader
		 */
		//ScriptManager.loadScripts();
		
		/**
		 * Accepting Connections
		 */
		acceptor = new SocketAcceptor();
		connectionHandler = new ConnectionHandler();
		
		SocketAcceptorConfig sac = new SocketAcceptorConfig();
		sac.getSessionConfig().setTcpNoDelay(false);
		sac.setReuseAddress(true);
		sac.setBacklog(100);
		
		throttleFilter = new ConnectionThrottleFilter(Config.CONNECTION_DELAY);
		sac.getFilterChain().addFirst("throttleFilter", throttleFilter);
		acceptor.bind(new InetSocketAddress(serverlistenerPort), connectionHandler, sac);

		/**
		 * Initialise Handlers
		 */
		//VoteForCash.createConnection();
		EventManager.initialize();
		Connection.initialize();
		//PlayerSaving.initialize();
		//MysqlManager.createConnection();	
		/**
		 * Server Successfully Loaded 
		 */
		System.out.println("Server listening on port 0.0.0.0:" + serverlistenerPort);
		/**
		 * Main Server Tick
		 */
		try {
			while (!Server.shutdownServer) {
				if (sleepTime > 0)
					Thread.sleep(sleepTime);
				engineTimer.reset();
				itemHandler.process();
				playerHandler.process();	
	                        npcHandler.process();
				shopHandler.process();
				objectManager.process();
				fightPits.process();
				pestControl.process();				
				if(cycleTime < 575)
					sleepTime = cycleRate - cycleTime;
				else
					sleepTime = 0;
				cycleTime = engineTimer.elapsed();				
				sleepTime = cycleRate - cycleTime;
				totalCycleTime += cycleTime;
				cycles++;
				seconds++;
				if(seconds == 60){
					minutes++;
					seconds = 0;
				}
				if(minutes == 60){
					hours++;
					minutes = 0;
				}
				if(hours == 24){
					days++;
					hours = 0;
				}
				debug();
			if (System.currentTimeMillis() - lastMassSave > 1800000) {//1800000
					for(Player p : PlayerHandler.players) {
						if(p == null)
							continue;						
						PlayerSave.saveGame((Client)p);
						System.out.println("Saved game for " + p.playerName + ".");
						
						for (int i = 0; i < Config.MAX_PLAYERS; i++) {
							if (PlayerHandler.players[i] != null) {
							}
						}
						lastMassSave = System.currentTimeMillis();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A fatal exception has been thrown!");
			for(Player p : PlayerHandler.players) {
				if(p == null)
					continue;						
				PlayerSave.saveGame((Client)p);
				System.out.println("Saved game for " + p.playerName + ".");
			}
		}
		acceptor = null;
		connectionHandler = null;
		sac = null;
		System.exit(0);
	}
	
	public static void processAllPackets() {
		synchronized (playerHandler) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				while(PlayerHandler.players[j].processQueuedPackets());			
				}	
			}
		}
	}
	
	public static boolean playerExecuted = false;
	private static void debug() {
		if (debugTimer.elapsed() > 360*1000 || playerExecuted) {
			long averageCycleTime = totalCycleTime / cycles;
			System.out.println("Average Cycle Time: " + averageCycleTime + "ms");
			double engineLoad = ((double) averageCycleTime / (double) cycleRate);
			System.out.println("Players online: " + PlayerHandler.playerCount+ ", engine load: "+ debugPercentFormat.format(engineLoad));
			totalCycleTime = 0;
			cycles = 0;
			System.gc();
			System.runFinalization();
			debugTimer.reset();
			playerExecuted = false;
		}
	}

	public static boolean checkVotes(String playerName) {
		try {
			URL url = new URL("http://www.area-51.net46.net/vote.php?type=checkvote&username="+playerName.replace(" ", "%20"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String results = reader.readLine();
			if(results.length() > 0) {
				if(results.equals("user needs reward..."))
					return true;
				else 
					return false;
			}
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL Exception in checkVotes(String playerName)");
		} catch (IOException e) {
		System.out.println("IO Exception in checkVotes(String playerName)");
		}
		return false;
	}
		public static boolean checkVotes2(String playerName) {
		try {
			URL url = new URL("http://www.area-51.net46.net/vote.php?type=checkvote&username="+playerName.replace(" ", "%20"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String results = reader.readLine();
			if(results.length() > 0) {
				if(results.equals("user needs reward...") || (results.equals("user been given reward...")))
					return true;
				else 
					return false;
			}
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL Exception in checkVotes(String playerName)");
		} catch (IOException e) {
		System.out.println("IO Exception in checkVotes(String playerName)");
		}
		return false;
	}
	

	
	public static long getSleepTimer() {
		return sleepTime;
	}
	
}
