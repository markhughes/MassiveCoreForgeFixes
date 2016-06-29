package me.markeh.massivecoreforgefixes;

import me.markeh.massivecoreforgefixes.plugins.AbstractPluginBase;
import me.markeh.massivecoreforgefixes.plugins.factions.FactionsFixes;

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class MassiveCoreForgeFixes extends JavaPlugin {

	// ----------------------------------------
	//  SINGLETON
	// ----------------------------------------
	
	private static MassiveCoreForgeFixes i;
	public static MassiveCoreForgeFixes get() { return i; }
	public MassiveCoreForgeFixes() { i = this; }
	
	// ----------------------------------------
	//  FIELDS
	// ----------------------------------------
	
	private String massivecorePluginName = null;
	private String factionsVersion;
	private String massivecoreVersion;
	
	// ----------------------------------------
	//  PLUGIN ENABLE
	// ----------------------------------------
	
	@Override
	public final void onEnable() {
		try {
			Class.forName("cpw.mods.fml.common.FMLCommonHandler");
		} catch (ClassNotFoundException e) {
			log("We couldn't actually find FMLCommonHandler, which usually means this isn't a forge-based server..");
			log("We will still log the events anyway, but please ensure this is only running on a forge-based server.");
		}	
		
		// Attempt to find MassiveCore plugin name
		if (this.getServer().getPluginManager().isPluginEnabled("MassiveCore")) {
			this.massivecorePluginName = "MassiveCore";
		}
		
		if (this.getServer().getPluginManager().isPluginEnabled("MCore")) {
			this.massivecorePluginName = "MCore";
		}
		
		// Check for MassiveCore
		if (this.massivecorePluginName == null) {
			log("We couldn't find MassiveCore ...");
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		// Store plugin versions
		this.factionsVersion = this.getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion();
		this.massivecoreVersion = this.getServer().getPluginManager().getPlugin(this.massivecorePluginName).getDescription().getVersion();
		
		// Enable fixes
		this.enableFixes();
		
		log("Fixes enabled!");
	}
	
	// ----------------------------------------
	//  PLUGIN DISABLE
	// ----------------------------------------

	@Override
	public final void onDisable() {
		this.disableFixes();
	}
	
	// ----------------------------------------
	//  METHODS
	// ----------------------------------------
	
	// Simply adds our events 
	public final void enableFixes() {
		this.getServer().getPluginManager().registerEvents(MCFFEvents.get(), this);
		
		if (this.getServer().getPluginManager().isPluginEnabled("Factions")) {
			FactionsFixes.get().enable();
		}
	}
	
	// Simply disables our events and plugin fixes
	public final void disableFixes() {
		HandlerList.unregisterAll(MCFFEvents.get());
		
		AbstractPluginBase.disableAll();
	}
	
	// Log util
	public final MassiveCoreForgeFixes log(String... msg) {
		for (String m : msg) {
			this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[MCFF] " + ChatColor.WHITE + m);
		}
		
		return this;
	}
	
	// Print the exception with detailed information
	public final void detailedPrint(Exception e) {
		e.printStackTrace();
		System.out.println("With: Factions " + this.factionsVersion + ", " + this.massivecorePluginName + " " + this.massivecoreVersion + ", " + this.getServer().getName() + " " + this.getServer().getVersion());
	}
	
}
