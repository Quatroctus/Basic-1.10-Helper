package com.anime.basic.recipies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.anime.basic.logger.ModLogger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeHelper {

	/** The standard shape of the sword crafting recipe **/
	public static final String[] STANDARD_SWORD_RECIPE = new String[]{"M", "M", "S"};
	/** The standard shape of the pickaxe crafting recipe **/
	public static final String[] STANDARD_PICKAXE_RECIPE = new String[]{"MMM", " S ", " S "};
	/** The standard shape of the shovel crafting recipe **/
	public static final String[] STANDARD_SHOVEL_RECIPE = new String[]{"M", "S", "S"};
	/** The standard shape of the axe crafting recipe **/
	public static final String[] STANDARD_AXE_RECIPE = new String[]{"MM", "MS", " S"};
	/** The standard shape of the hoe crafting recipe **/
	public static final String[] STANDARD_HOE_RECIPE = new String[]{"MM", " S", " S"};
	
	/** The standard shape of the helmet crafting recipe **/
	public static final String[] STANDARD_HELMET_RECIPE = new String[]{"MMM", "M M"};
	/** The standard shape of the chest plate crafting recipe **/
	public static final String[] STANDARD_CHEST_RECIPE = new String[]{"M M", "MMM", "MMM"};
	/** The standard shape of the legs crafting recipe **/
	public static final String[] STANDARD_LEGS_RECIPE = new String[]{"MMM", "M M", "M M"};
	/** The standard shape of the boots crafting recipe **/
	public static final String[] STANDARD_BOOTS_RECIPE = new String[]{"M M", "M M"};
 	
	/** The standard chars of the tool crafting recipes **/
	public static final char[] STANDARD_WEAPONS_TOOLS_CHARS = new char[]{'M', 'S'};
	
	/** The standard chars of the armor crafting recipes **/
	public static final char[] STANDARD_ARMOR_CHARS = new char[]{'M'};
	
	/**
	 * @param craftingSlots The "shape" of the crafting recipe.
	 * @param chars Characters representing craftingObject.
	 * @param craftingObjects An item or block used in crafting.
	 * @return Object[] to be used for a shaped crafting recipe.
	 */
	public static Object[] getShapedCrafting(String[] craftingSlots, char[] chars, Object[] craftingObjects) {
		ArrayList<Object> crafting = new ArrayList<Object>();
			if (chars.length == craftingObjects.length) {
				int i;
				for (i = 0; i < craftingSlots.length; i++) {
					crafting.add(craftingSlots[i]);
				}
				for (i = 0; i < chars.length; i++) {
					crafting.add(craftingObjects[i]);
					crafting.add(chars[i]);
				}
				return crafting.toArray();
			} else ModLogger.logErrorMessage("Trying to add Crafting Recipe chars and craftingObjects lengths not equal." + craftingObjects[craftingObjects.length-1]);
		return new Object[]{};
	}
	
	/**
	 * Adds a crafting recipe to the vanilla crafting.
	 * @param result The Item or Block you will receive from this crafting operation.
	 * @param resultAmount The amount of the result you will receive from this crafting operation.
	 * @param metaResult The metadata of the result.
	 * @param crafting This stores the shape, characters that represent the item or block used in crafting. Characters and shape should not be added when using shapeless.
	 * @param shapeless If this is a shapeless recipe.
	 */
	public static void addCraftingRecipe(Object result, int resultAmount, int metaResult, Object[] crafting, boolean shapeless) {
		if (result != null) {
			ItemStack stack;
			if (result instanceof Block) {
				stack = new ItemStack(Item.getItemFromBlock((Block)result), resultAmount, metaResult);
			} else stack = new ItemStack((Item)result, resultAmount, metaResult);
			if (!shapeless) {
				GameRegistry.addShapedRecipe(stack, crafting);
			} else {
				GameRegistry.addShapelessRecipe(stack, crafting);
			}
		} else ModLogger.logErrorMessage("Failed trying to add crafting result is null. " + crafting != null ? (String) crafting[0] + crafting[1] + crafting[2] : ". Crafting is also null.");
	}
	
	/**
	 * Adds a vanilla furnace recipe to FurnaceRecipes
	 * @param inputObject The Item or Block that will be smelted.
	 * @param metaInput The metadata of the inputObject.
	 * @param resultObject The Item or Block that will be returned once inputObject is smelted.
	 * @param resultAmount The amount of the result you will receive from this smelting operation.
	 * @param metaResult The metadata of the result.
	 * @param xp The amount of experience the player will get from one smelting operation.
	 */
	public static void addSmeltingRecipe(Object inputObject, int metaInput, Object resultObject, int resultAmount, int metaResult, float xp) {
		if (inputObject != null) {
			if (resultObject != null) {
				ItemStack input;
				ItemStack result;
				if (inputObject instanceof Block) {
					input = new ItemStack(Item.getItemFromBlock((Block)inputObject), 1, metaInput);
				} else input = new ItemStack((Item)inputObject, 1, metaInput);
				if (resultObject instanceof Block) {
					result = new ItemStack(Item.getItemFromBlock((Block)resultObject), resultAmount, metaResult);
				} else result = new ItemStack((Item)resultObject, resultAmount, metaResult);
				GameRegistry.addSmelting(input, result, xp);
			} else ModLogger.logErrorMessage("Furnace recipe adding failed result is null. " + inputObject != null ? inputObject.toString() + "." : "");
		} else ModLogger.logErrorMessage("Furnace recipe adding failed input is null. " + resultObject != null ? resultObject.toString() : ". Result also null.");
	}
	
	/**
	 * Will remove all smelting recipes having to deal with the resultItem.
	 * @param resultItem The ItemStack you want to remove all smelting for
	 */
	public static void removeItemStackResultFromFurnaceRecipes(ItemStack resultItem) {
		Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
		for (Iterator<Map.Entry<ItemStack,ItemStack>> entries = recipes.entrySet().iterator(); entries.hasNext(); ){
			Map.Entry<ItemStack,ItemStack> entry = entries.next();
			ItemStack result = entry.getValue();
			if (ItemStack.areItemStacksEqual(result, resultItem)) {
				entries.remove();
			}
		}
	}
	
	/**
	 * Removes a smelting recipe based on what is smelted.
	 * @param inputItem The ItemStack that is smelted.
	 */
	public static void removeItemStackKeyFromFurnaceRecipes(ItemStack inputItem) {
		Set<ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList().keySet();
		for (Iterator<ItemStack> entries = recipes.iterator(); entries.hasNext(); ){
			ItemStack result = entries.next();
			if (ItemStack.areItemStacksEqual(result, inputItem)) {
				entries.remove();
			}
		}
	}
	
	/**
	 * Will remove all crafting recipes based on the result.
	 * @param resultItem The ItemStack you want to remove all crafting for.
	 */
	public static void removeVanillaCrafting(ItemStack resultItem) {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		Iterator<IRecipe> Leash = recipes.iterator();
			while (Leash.hasNext()) {
				ItemStack is = Leash.next().getRecipeOutput();
				if (is != null && is == resultItem) {
					Leash.remove();
			}
		}
	}
	
}
