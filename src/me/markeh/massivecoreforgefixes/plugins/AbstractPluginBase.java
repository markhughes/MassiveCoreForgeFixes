package me.markeh.massivecoreforgefixes.plugins;

import java.util.ArrayList;
import java.util.List;

/*
 * With all Plugin Fixes you should be able to enable and 
 * disable the fixes - on demand.
 * 
 * When a fix is enable it should be passed to logFix
 *
 */
public abstract class AbstractPluginBase {
	
	// ------------------------------
	//  FIXES LOG 
	// ------------------------------
	
	// List of plugin fixes 
	private static List<AbstractPluginBase> fixes = new ArrayList<AbstractPluginBase>();
	
	// Log a plugin fix 
	public static void logFix(AbstractPluginBase fix) { fixes.add(fix); }
	
	// Disable all fixes 
	public static void disableAll() {
		for (AbstractPluginBase fix : fixes) fix.disable();
		fixes.clear();
	}

	// ------------------------------
	//  ABSTRACT METHODS 
	// ------------------------------
	
	public abstract void enable();
	public abstract void disable();
	
}
