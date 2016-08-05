package com.anime.basic.registry;

import com.anime.basic.MainModReference;
import com.anime.basic.logger.ModLogger;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class RegistryHelper {
	
	private static int NETWORK_DISCRIMINATOR = -1;
	
	/**
	 * Registers the Item to the registry.
	 * @param item The Item to be registered.
	 * @param registryName The name for the Item to be registered to.
	 */
	public static void registerItem(Item item, String registryName) {
		if (item != null && registryName != null && !registryName.isEmpty()) {
			GameRegistry.register(item.setRegistryName(registryName));
			if (MainModReference.SHOULD_LOG_ITEM_REGISTRY) ModLogger.logInfoMessage("Successfully added " + registryName + " to Item Registry.");
		} else ModLogger.logErrorMessage("Something was null or empty. " + "Block: " + item + " Name: " + registryName);
	}
	
	/**
	 * Registers the Block and ItemBlock to the registry.
	 * @param block The Block to be registered.
	 * @param registryName The name for the block to be registered to.
	 */
	public static void registerBlock(Block block, String registryName) {
		if (block != null && registryName != null && !registryName.isEmpty()) {
			GameRegistry.register(block.setRegistryName(registryName));
			registerItem(new ItemBlock(block), registryName);
			if (MainModReference.SHOULD_LOG_BLOCK_REGISTRY) ModLogger.logInfoMessage("Successfully added " + registryName + " to Block Registry.");
		} else ModLogger.logErrorMessage("Something was null or empty. " + "Block: " + block + " Name: " + registryName);
	}
	
	public static void registerCustomItemBlockAndBlock(Block block, ItemBlock itemBlock, String registryName) {
		if (block != null && itemBlock != null && registryName != null && !registryName.isEmpty()) {
			GameRegistry.register(block.setRegistryName(registryName));
			if (itemBlock.getRegistryName() == null || !itemBlock.getRegistryName().getResourcePath().isEmpty()) {
				registerItem(itemBlock, registryName);
			} else ModLogger.logErrorMessage("ItemBlock already has an registered name. " + itemBlock.getRegistryName());
			if (MainModReference.SHOULD_LOG_BLOCK_REGISTRY) ModLogger.logInfoMessage("Successfully added " + registryName + " to Block Registry.");
		} else ModLogger.logErrorMessage("Something was null or empty. " + "Block: " + block + " ItemBlock: " + itemBlock + " Name: " + registryName);
	}
	
	/**
	 * Adds the specified object to Forge's OreDictionary.
	 * @param object The Item, Block, or ItemStack to be added to the OreDictionary.
	 * @param oreDictName The name it will be registered to can be registered to multiple names.
	 */
	public static void registerObjectToOreDict(Object object, String oreDictName) {
		if (object != null) {
			if (object instanceof Item) {
				OreDictionary.registerOre(oreDictName, (Item) object);
			} else if (object instanceof Block) {
				OreDictionary.registerOre(oreDictName, (Block) object);
			} else if (object instanceof ItemStack) {
				OreDictionary.registerOre(oreDictName, (ItemStack) object);
			} else {
				ModLogger.logErrorMessage("Not a valid object to be added. Valid objects Item, Block, ItemStack. " + object.toString() + " " + oreDictName + ".");
				return;
			}
		} else {
			ModLogger.logErrorMessage("Trying to add null object to OreDictionary. " + oreDictName + ".");
			return;
		}
		if (MainModReference.SHOULD_LOG_OREDICT_REGISTRY) ModLogger.logInfoMessage("Successfully added " + oreDictName + " to OreDictionary.");
	}
	
	/**
	 * Wraps to registerObjectToOreDict, but adds ore as a prefix to oreMaterial.
	 * @param block The ore Block to be added to Forge's OreDictionary.
	 * @param oreMaterial The material name(example: "Copper").
	 */
	public static void addOreToOreDict(Block block, String oreMaterial) {
		registerObjectToOreDict(block, "ore" + oreMaterial);
	}
	
	/**
	 * Wraps to registerObjectToOreDict, but adds ingot as a prefix to oreMaterial.
	 * @param block The ingot Item to be added to Forge's OreDictionary.
	 * @param oreMaterial The material name(example: "Copper").
	 */
	public static void addIngotToOreDict(Item item, String ingotMaterial) {
		registerObjectToOreDict(item, "ingot" + ingotMaterial);
	}
	
	/**
	 * Registers the model from the items unlocalized name and MainModReference.MODID
	 * @param item The item that will be bound to that model
	 * @param meta The metadata of the item.
	 */
	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item, int meta) {
		if (item != null) {
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(MainModReference.MODID + ":" + item.getUnlocalizedName().substring(5)));
		} else ModLogger.logErrorMessage("Trying to register Item model error item is null.");
	}
	
	/**
	 * Registers the model from the itemblocks unlocalized name and MainModReference.MODID
	 * @param block The block that will be bound to that model.
	 * @param meta The metadata of the block.
	 */
	@SideOnly(Side.CLIENT)
	public static void registerBlockModel(Block block, int meta) {
		if (block != null) {
			registerItemModel(Item.getItemFromBlock(block), meta);
		} else ModLogger.logErrorMessage("Trying to register Block model error block is null.");
	}
	
	/**
	 * Registers the specified TileEntity using the MainModReference.MODID
	 * @param tileEntityClass The TileEntity Class what will be registered
	 */
	public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass) {
		GameRegistry.registerTileEntity(tileEntityClass, MainModReference.MODID + ":" + tileEntityClass.getName().substring(11));
	}
	
	/**
	 * Registers the specified TileEntity to MainModReference.MODID and binds a TESR to it.
	 * @param tileEntityClass The TileEntity Class that will be registered/binded.
	 * @param specialRenderer 
	 */
	public static void registerTileEntityWithTESR(Class<? extends TileEntity> tileEntityClass, TileEntitySpecialRenderer<? super TileEntity> specialRenderer) {
		registerTileEntity(tileEntityClass);
		ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, specialRenderer);
	}
	
	/**
	 * Registers the Entity and renderer.
	 * @param renderEntity The RednerFactory of the specified entity.
	 * @param entityClass The Entities class that will be registered.
	 * @param id The mod specific ID of the entity.
	 * @param modInstance Either the modid or the @Instance variable
	 * @param trackingRange The range in which the client will render.
	 * @param updateFreq How fast the entity will send updates to the client.
	 * @param hexColorPrimary Primary color of the egg using hexadecimal.
	 * @param hexColorSecondary Secondary color of the egg using hexadecimal.
	 */
	public static void registerEntity(IRenderFactory<? super Entity> renderEntity, Class<Entity> entityClass, int id, Object modInstance, int trackingRange, int updateFreq, int hexColorPrimary, int hexColorSecondary) {
		EntityRegistry.registerModEntity(entityClass, MainModReference.MODID + ":" + entityClass.getName().substring(6), id, modInstance, trackingRange, updateFreq, true, hexColorPrimary, hexColorSecondary);
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderEntity);
	}
	
	/**
	 * Registers a Keybinding to Minecrafts Game Settings Screen.
	 * @param key The Keybinding to be added to the GameSettings.
	 */
	public static void registerKeyBinding(KeyBinding key) {
		ClientRegistry.registerKeyBinding(key);
	}
	
	/**
	 * Registers the said packet to the MainModReference SimpleNetworkWrapper.
	 * @param messageHandler The handler for the packet.
	 * @param requestMessageType The packet itself.
	 * @param side What side the packet will be sent too.
	 */
	public static <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
		MainModReference.WRAPPER.registerMessage(messageHandler, requestMessageType, ++NETWORK_DISCRIMINATOR, side);
	}
	
}
