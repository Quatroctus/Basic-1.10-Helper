package com.anime.basic.logger;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anime.basic.MainModReference;

public class ModLogger {
	
	/**
	 * The logger of this Mod as Specified by MainModReference.MODID
	 */
	private static final Logger instance = LogManager.getLogger(MainModReference.MODID);
	
	/**
	 * Will log a Error Message
	 * @param error The message it will print.
	 */
	public static void logErrorMessage(String error) {
		instance.log(Level.ERROR, error);
	}
	
	/**
	 * Will log a Warning Message.
	 * @param warning The message it will print.
	 */
	public static void logWarningMessage(String warning) {
		instance.log(Level.WARN, warning);
	}
	
	/**
	 * Will log a Info Message.
	 * @param info The message it will print.
	 */
	public static void logInfoMessage(String info) {
		instance.log(Level.INFO, info);
	}
	
	/**
	 * Will log a Debug Message
	 * @param debug The message it will print.
	 */
	public static void logDebugMessage(String debug) {
		instance.log(Level.DEBUG, debug);
	}
	
	/**
	 * Will log a Fatal Message
	 * @param fatal The message it will print.
	 */
	public static void logFatalMessage(String fatal) {
		instance.log(Level.FATAL, fatal);
	}
	
}
