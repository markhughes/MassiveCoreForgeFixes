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
public abstract class FixBase {
	
	// ------------------------------
	//  FIXES LOG 
	// ------------------------------
	
	// List of plugin fixes 
	private static List<FixBase> fixes = new ArrayList<FixBase>();
	
	// Log a plugin fix 
	public static void logFix(FixBase fix) { fixes.add(fix); }
	
	// Disable all fixes 
	public static void disableAll() {
		for (FixBase fix : fixes) fix.disable();
		fixes.clear();
	}

	// ------------------------------
	//  ABSTRACT METHODS 
	// ------------------------------
	
	public abstract void enable();
	public abstract void disable();
	
}
