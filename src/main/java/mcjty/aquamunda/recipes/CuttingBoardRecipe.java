package mcjty.aquamunda.recipes;

import mcjty.lib.tools.ItemStackTools;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public class CuttingBoardRecipe {

    private final ItemStack[] inputItems;
    private final ItemStack outputItem;
    private final int chopTime;
    private final boolean useRoller;

    public CuttingBoardRecipe(ItemStack inputItem1, ItemStack inputItem2, ItemStack inputItem3, ItemStack outputItem, int chopTime, boolean useRoller) {
        inputItems = new ItemStack[3];
        inputItems[0] = inputItem1;
        inputItems[1] = inputItem2;
        inputItems[2] = inputItem3;
        sortItems(inputItems);

        this.outputItem = outputItem;
        this.chopTime = chopTime;
        this.useRoller = useRoller;
    }

    private static String getRegNameSafe(ItemStack s) {
        if (s.getItem() == null) {
            return "";
        }
        ResourceLocation r = s.getItem().getRegistryName();
        if (r == null) {
            return "";
        }
        return r.toString();
    }

    public static void sortItems(ItemStack[] stacks) {
        Arrays.sort(stacks, (i1, i2) -> {
            if (ItemStackTools.isValid(i1) && ItemStackTools.isValid(i2)) {
                return getRegNameSafe(i1).compareTo(getRegNameSafe(i2));
            }
            if (ItemStackTools.isValid(i1)) {
                return 1;
            }
            if (ItemStackTools.isValid(i2)) {
                return -1;
            }
            return 0;
        });
    }

    public ItemStack[] getInputItems() {
        return inputItems;
    }

    public ItemStack getOutputItem() {
        return outputItem;
    }

    public int getChopTime() {
        return chopTime;
    }

    public boolean isUseRoller() {
        return useRoller;
    }
}