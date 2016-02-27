package me.markeh.massivecoreforgefixes.plugins;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.massivecraft.factions.engine.EngineMain;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.VisualizeUtil;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;

public class FactionsEvents implements Listener {
	
	// ------------------------------
	// SINGLETON 
	// ------------------------------
	
	private static FactionsEvents i = null;
	public static Listener get() {
		if (i == null) i = new FactionsEvents();
		return i;
	}

	// ------------------------------
	// DUPLICATE MSG FIX 
	// ------------------------------
	// In Forge based servers a duplicate message is sometimes shown in-between Factions
	//
	// By DraksterAU - see: https://github.com/StargateMC/Factions/blob/a082bc494072362edcd04d4dd9959688513ff421/src/com/massivecraft/factions/engine/EngineMain.java#L671-L710
	//
	// This event should only be enable after removing EngineMain#chunkChangeDetect from listeners 

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void chunkChangeDetect(PlayerMoveEvent event)
	{
		// If the player is moving from one chunk to another ...
		if (MUtil.isSameChunk(event)) return;
		Player player = event.getPlayer();
		if (MUtil.isntPlayer(player)) return;
		
		// ... gather info on the player and the move ...
		MPlayer mplayer = MPlayer.get(player);
		
		PS chunkFrom = PS.valueOf(event.getFrom()).getChunk(true);
		PS chunkTo = PS.valueOf(event.getTo()).getChunk(true);
				
		Faction factionFrom = BoardColl.get().getFactionAt(chunkFrom);
		Faction factionTo = BoardColl.get().getFactionAt(chunkTo);
				
		if ( ! isChunkCoord(player)) return;
		
		// ... and send info onwards.
		EngineMain.get().chunkChangeTerritoryInfo(mplayer, player, chunkFrom, chunkTo, factionFrom, factionTo);
		EngineMain.get().chunkChangeAutoClaim(mplayer, chunkTo);
		
	}
	
	// -------------------------------------------- //
	// CAULDRON/FORGE HACK: This detects chunk borders to ensure messages only throw once.
	// -------------------------------------------- //
	
	public boolean isChunkCoord(Player player) {
		Location loc = player.getLocation();
		if (loc.getBlockX() % 2 == 0 && loc.getBlockZ() %2 == 0) {
			return true;
		} else {
			if (loc.getBlockX() % 2 != 0 && loc.getBlockZ() %2 != 0) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	// This is the only other PlayerMoveEvent we need to replace (EngineMain.javaL1086-L1092) 
	// at StargateMC/Factions/blob/a082bc494072362edcd04d4dd9959688513ff421
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerMoveClearVisualizations(PlayerMoveEvent event)
	{
		if (MUtil.isSameBlock(event)) return;
		
		VisualizeUtil.clear(event.getPlayer());
	}
}
