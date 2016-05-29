package me.markeh.massivecoreforgefixes;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.massivecraft.massivecore.engine.EngineMassiveCoreDatabase;

public class MCFFEvents implements Listener {
	
	private static MCFFEvents i;
	public static MCFFEvents get() {
		if (i == null) i = new MCFFEvents();
		
		return i;
	}

	// -------------------------------------------- //
	// setSenderReferencesLater
	// -------------------------------------------- //
	// setSenderReferencesLater solves an issue where initiateSender is
	// always called too early for Cauldron/KCauldron. This is due to
	// the mod load delay when logging in. This enables 3 major events
	// so we can run setSenderReferencesSoon later on. 
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		MassiveCoreForgeFixes.get().getServer().getPluginManager().registerEvents(this.generateInitiateSenderListener(event.getPlayer()), MassiveCoreForgeFixes.get());
	}
	
	private Listener generateInitiateSenderListener(final Player player) {
		return new Listener() {
			@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
			public void executeInitiateSender(PlayerMoveEvent event) { this.executeInitiateSender(); }
			
			@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
			public void executeInitiateSender(PlayerCommandPreprocessEvent event) { this.executeInitiateSender(); }
			
			@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
			public void executeInitiateSender(AsyncPlayerChatEvent event) { this.executeInitiateSender(); }

			public void executeInitiateSender() {				
				// Now run setSenderReferencesSoon
				try {
					EngineMassiveCoreDatabase.setSenderReferencesSoon(player, null);
				} catch (Exception e) {
					
					// Probably using an older version
					try {
						Class.forName("com.massivecraft.massivecore.MassiveCoreEngineMain")
							.getMethod("setSenderReferencesSoon", Player.class)
							.invoke(this, player);
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				// Unregister this listener
				HandlerList.unregisterAll(this);
			}
		};
	}
}
