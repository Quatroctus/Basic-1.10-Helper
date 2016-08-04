package com.anime.basic;

import com.anime.basic.network.GuiHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class MainModReference {
	
	/**
	 * Mod Identiifier String
	 */
	public static final String MODID = "modid";
	/**
	 * Mod In Game Name
	 */
	public static final String NAME = "Mod ID";
	/**
	 * Version of the Mod
	 **/
	public static final String VERSION = "1.0";
	/**
	 * What versions of Minecraft this Mod will load on.
	 */
	public static final String APPLICABLE_MINECRAFT_VERSIONS = "[1.8],[1.8.9]";
	/**
	 * The mods Dependencies(if it has any).
	 */
	public static final String MOD_DEPENDENCIES = "";
	
	
	/**
	 * Server Proxy Path.
	 */
	public static final String SERVER_PROXY_PATH = "";
	/**
	 * Client Proxy Path
	 */
	public static final String CLIENT_PROXY_PATH = "";
	
	/**
	 * A GuiHandler instance, register to handle GUIs.
	 */
	public static final GuiHandler GUIHANDLERS = new GuiHandler();
	
	/**
	 * A SimpleNetworkWrapper instance, register to use packets.
	 */
	public static final SimpleNetworkWrapper WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
	
	public static final boolean SHOULD_LOG_ITEM_REGISTRY = true;
	
	public static final boolean SHOULD_LOG_BLOCK_REGISTRY = true;
	
	public static final boolean SHOULD_LOG_OREDICT_REGISTRY = true;
	
}
