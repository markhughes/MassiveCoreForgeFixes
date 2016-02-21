package me.markeh.massivecoreforgefixes;

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class MassiveCoreForgeFixes extends JavaPlugin {

	// ------------------------------
	//  SINGLETON
	// ------------------------------
	
	private static MassiveCoreForgeFixes i;
	public static MassiveCoreForgeFixes get() { return i; }
	public MassiveCoreForgeFixes() { i = this; }
	
	// ------------------------------
	//  PLUGIN ENABLE
	// ------------------------------
	
	@Override
	public final void onEnable() {
		try
		{
			Class.forName("cpw.mods.fml.common.FMLCommonHandler");
		} catch (ClassNotFoundException e) {
			log("We couldn't actually find FMLCommonHandler, which usually means this isn't a forge-based server..");
			log("We will still log the events anyway, but please ensure this is only running on a forge-based server.");
		}	
		
		if (this.getServer().getPluginManager().isPluginEnabled("MassiveCore") || this.getServer().getPluginManager().isPluginEnabled("MCore")) {
			this.enableFixes();
		} else {
			log("We couldn't find MassiveCore ...");
			this.getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	// ------------------------------
	//  PLUGIN DISABLE
	// ------------------------------

	@Override
	public final void onDisable() {
		this.disableFixes();
	}
	
	// ------------------------------
	//  METHODS
	// ------------------------------
	
	// Simply adds our events 
	public final void enableFixes() {
		this.getServer().getPluginManager().registerEvents(MCFFEvents.get(), this);
	}
	
	// Simply disables our events
	public final void disableFixes() {
		HandlerList.unregisterAll(MCFFEvents.get());
	}
	
	// Log util
	public final MassiveCoreForgeFixes log(String... msg) {
		for (String m : msg) {
			this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[MCFF] " + ChatColor.WHITE + m);
		}
		
		return this;
	}
}