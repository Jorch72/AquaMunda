package mcjty.aquamunda.recipes;

import mcjty.immcraft.api.helpers.InventoryHelper;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CookerRecipeRepository {

    private static Map<ResourceLocation, CookerRecipe> recipeMap = new HashMap<>();

    public static void addRecipe(CookerRecipe recipe) {
        ResourceLocation key = recipe.getInputItem().getItem().getRegistryName();
        recipeMap.put(key, recipe);
    }

    @Nullable
    public static CookerRecipe getRecipe(ItemStack stack) {
        if (ItemStackTools.isEmpty(stack)) {
            return null;
        }
        ResourceLocation key = stack.getItem().getRegistryName();
        if (recipeMap.containsKey(key)) {
            CookerRecipe recipe = recipeMap.get(key);
            if (InventoryHelper.isItemStackConsideredEqual(recipe.getInputItem(), stack)) {
                return recipe;
            }

        }
        return null;
    }
}
