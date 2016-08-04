package com.anime.basic.network;

import com.anime.basic.MainModReference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSendingHelper {
	
	/**
	 * Goes to the MainModReference of SimpleNetworkWrapper and sends a Packet to all players online.
	 * @param message The Packet that needs to be sent.
	 */
	public static void sendToAll(IMessage message) {
		MainModReference.WRAPPER.sendToAll(message);
	}
	
	/**
	 * Goes to the MainModReference of SimpleNetworkWrapper and sends a Packet to the specified dimension.
	 * @param message The Packet that needs to be sent.
	 * @param dimID The ID of the dimension where this packet will be sent to.
	 */
	public static void sendToDimension(IMessage message, int dimID) {
		MainModReference.WRAPPER.sendToDimension(message, dimID);
	}
	
	/**
	 * Goes to the MainModReference of SimpleNetworkWrapper and sends a Packet to all players with in the specified dimension and area.
	 * @param message The Packet that needs to be sent.
	 * @param dimID The ID of the dimension where this packet will be sent to.
	 * @param x Center x position.
	 * @param y Center y position.
	 * @param z Center z position.
	 * @param range Distance from the center point to the outside bounding.
	 */
	public static void sendToAllAround(IMessage message, int dimID, int x, int y, int z, int range) {
		MainModReference.WRAPPER.sendToAllAround(message, new TargetPoint(dimID, x, y, z, range));
	}
	
	/**
	 * Goes to the MainModReference of SimpleNetworkWrapper and sends a Packet to the specified player.
	 * @param message The Packet that needs to be sent.
	 * @param player The player to send this packet to.
	 */
	public static void sendToSpecificPlayer(IMessage message, EntityPlayer player) {
		MainModReference.WRAPPER.sendTo(message, (EntityPlayerMP) player);
	}
	
	/**
	 * Goes to the MainModReference of SimpleNetworkWrapper and sends a Packet to the server.
	 * @param message The Packet that needs to be sent.
	 */
	public static void sendToServer(IMessage message) {
		MainModReference.WRAPPER.sendToServer(message);
	}
	
}
