package me.markeh.massivecoreforgefixes.plugins.factions;

import java.util.ArrayList;
import java.util.List;

import me.markeh.massivecoreforgefixes.MassiveCoreForgeFixes;
import me.markeh.massivecoreforgefixes.plugins.FixBase;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.RegisteredListener;

public class FactionsFixes extends FixBase {

	private static FactionsFixes i = null;
	public static FactionsFixes get() {
		if (i == null) i = new FactionsFixes();
		return i;
	}
	
	private List<RegisteredListener> removedRegisteredListeners = new ArrayList<RegisteredListener>();
	
	@Override
	public void enable() {
		// Store this into the FixBase 
		FixBase.logFix(FactionsFixes.get());
		
		// Scan over Factions EngineMain for PlayerMoveEvent events 
		
		for (RegisteredListener registeredListener : PlayerMoveEvent.getHandlerList().getRegisteredListeners()) {
			if (registeredListener.getPlugin().getName().equalsIgnoreCase("Factions")) {
				if(registeredListener.getListener().getClass().getSimpleName().equalsIgnoreCase("EngineMain")) {
					removedRegisteredListeners.add(registeredListener);
				}
			}
		}
		
		// Unregister all the PlayerMoveEvent events from EngineMain 
		for (RegisteredListener registeredListener: removedRegisteredListeners) PlayerMoveEvent.getHandlerList().unregister(registeredListener);
		
		// Add our new events
		Bukkit.getServer().getPluginManager().registerEvents(FactionsEvents.get(), MassiveCoreForgeFixes.get());
	}

	@Override
	public void disable() {
		HandlerList.unregisterAll(FactionsEvents.get());
		
		// Add back the previous factions registered listeners 
		for (RegisteredListener registeredListener: removedRegisteredListeners) PlayerMoveEvent.getHandlerList().register(registeredListener);
	}
}
