package com.anime.basic.NBT;

import com.anime.basic.logger.ModLogger;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTUtils {
	
	/**
	 * The Identifier to use for Main Slots in an inventory.
	 */
	public static final String MAIN_SLOTS_NAME = "Main_Items";
	
	/**
	 * Writes the specified ItemStack[] to the passed NBTTagCompound.
	 * @param tag The NBTTagCompound to write the ItemStack[] too.
	 * @param stacks The ItemStack[] it will write.
	 * @param identifier The Identifier for the NBTTagList.
	 * @return The passed tag.
	 */
	public static NBTTagCompound writeItemStacksToNBT(NBTTagCompound tag, ItemStack[] stacks, String identifier) {
		if (stacks != null && tag != null && identifier != null && !identifier.isEmpty()) {
			NBTTagList mainItems = new NBTTagList();

			for (int i = 0; i < stacks.length; ++i) {
				if (stacks[i] != null) {
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte)i);
					stacks[i].writeToNBT(nbttagcompound1);
					mainItems.appendTag(nbttagcompound1);
				}
			}

			tag.setTag(identifier, mainItems);
		} else {
			ModLogger.logWarningMessage("Couldn't write ItemStacks to NBT this could be an error please report to the Mod Author. " + (tag !=null) + " " + stacks + identifier + ".");
		}
        return tag;
	}
	
	/**
	 * Reads the specified Identifier from the NBTTagCompound.
	 * @param tag The NBTTagCompound to read the identifier from.
	 * @param size The size of the ItemStack[] it will return.
	 * @param identifier The identifier for the NBTTagList.
	 * @return The ItemStack[] that was read from the tag.
	 */
	public static ItemStack[] readItemStacksFromNBT(NBTTagCompound tag, int size, String identifier) {
		if (size <= 0 && tag != null && identifier != null && !identifier.isEmpty()) {
		NBTTagList mainItems = tag.getTagList(identifier, 10);
        ItemStack[] stacks = new ItemStack[size];

        for (int i = 0; i < mainItems.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = mainItems.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < size) {
                stacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        return stacks;
        } else {
        	ModLogger.logWarningMessage("Couldn't read ItemStack from NBT this could be an error please report to the Mod Author." + (tag !=null) + " " + size + identifier + ".");
        }
        return null;
	}
	
}
