package mcjty.aquamunda.config;

import mcjty.aquamunda.environment.FarmlandOverhaulType;
import mcjty.aquamunda.items.ItemDish;
import mcjty.aquamunda.items.ModItems;
import mcjty.aquamunda.recipes.*;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class GeneralConfiguration {
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_RECIPES_CUTTINGBOARD = "recipescuttingboard";
    public static final String CATEGORY_RECIPES_COOKER = "recipescooker";
    public static final String CATEGORY_RECIPES_GRINDSTONE = "recipesgrindstone";

    public static float baseCookerVolume = 0.6f;     // Use 0 to turn off
    public static float baseChoppingVolume = 1.0f;   // Use 0 to turn off
    public static float baseGrindstoneVolume = 0.6f; // Use 0 to turn off

    public static int tankCatchesRain = 200;
    public static int tankEvaporation = 5;

    public static FarmlandOverhaulType farmlandOverhaulType = FarmlandOverhaulType.FRESH;

    public static void init(Configuration cfg) {
        baseCookerVolume = (float) cfg.get(CATEGORY_GENERAL, "baseCookerVolume", baseCookerVolume,
                "The volume for the cooker sound (0.0 is off)").getDouble();
        baseChoppingVolume = (float) cfg.get(CATEGORY_GENERAL, "baseChoppingVolume", baseChoppingVolume,
                "The volume for the chopping sound (0.0 is off)").getDouble();
        baseGrindstoneVolume = (float) cfg.get(CATEGORY_GENERAL, "baseGrindstoneVolume", baseGrindstoneVolume,
                "The volume for the grindstone sound (0.0 is off)").getDouble();

        tankCatchesRain = cfg.getInt("tankCatchesRain", CATEGORY_GENERAL, tankCatchesRain, 0, 100000, "By default tanks will catch this amount of rainfall every update tick. Set this to 0 if you don't want that");
        tankEvaporation = cfg.getInt("tankEvaporation", CATEGORY_GENERAL, tankEvaporation, 0, 100000, "By default water in tanks in the sun will evaporate this amount of water every update tick. Set this to 0 if you don't want that");

        String overhaul = cfg.get(CATEGORY_GENERAL, "farmlandOverhaulType", GeneralConfiguration.farmlandOverhaulType.getName(),
                "The type of overhaul for farmland: 'none' means vanilla water will work, 'vanilla' means the farmland is not replaced, 'fresh' means fresh water is required, 'harsh' means fresh water is required and the farmland must be sprinkled").getString();
        farmlandOverhaulType = FarmlandOverhaulType.getByName(overhaul);
        if (farmlandOverhaulType == null) {
            farmlandOverhaulType = FarmlandOverhaulType.FRESH;
        }
    }

    public static void initGrindstoneRecipes(Configuration cfg) {
        ConfigCategory category = cfg.getCategory(CATEGORY_RECIPES_GRINDSTONE);
        if (category.isEmpty()) {
            // Initialize with defaults
            addRecipe(cfg, "flour", new ItemStack(Items.WHEAT), new ItemStack(ModItems.flour), 100);
            addRecipe(cfg, "bonemeal", new ItemStack(Items.BONE), new ItemStack(Items.DYE, 5, EnumDyeColor.WHITE.getDyeDamage()), 100);
            addRecipe(cfg, "flint", new ItemStack(Blocks.GRAVEL), new ItemStack(Items.FLINT, 2), 100);
            addRecipe(cfg, "glowstone", new ItemStack(Blocks.GLOWSTONE), new ItemStack(Items.GLOWSTONE_DUST, 4), 300);
            addRecipe(cfg, "sugar", new ItemStack(Items.REEDS), new ItemStack(Items.SUGAR, 2), 100);
            addRecipe(cfg, "blazepowder", new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER, 3), 200);
        } else {
            for (Map.Entry<String, Property> entry : category.entrySet()) {
                String[] list = entry.getValue().getStringList();
                GrindstoneRecipeRepository.addRecipe(new GrindstoneRecipe(getItem(list, 0), getItem(list, 1), getInt(list, 2)));
            }
        }
    }

    public static void initCookerRecipes(Configuration cfg) {
        ConfigCategory category = cfg.getCategory(CATEGORY_RECIPES_COOKER);
        if (category.isEmpty()) {
            // Initialize with defaults
            addRecipe(cfg, "cookedcarrot", new ItemStack(Items.CARROT), new ItemStack(ModItems.cookedCarrot), "", 10);
            addRecipe(cfg, "cookedpotato", new ItemStack(Items.POTATO), new ItemStack(ModItems.cookedPotato), "", 10);
            addRecipe(cfg, "vegetablesoup", new ItemStack(ModItems.choppedVegetables), ItemStackTools.getEmptyStack(), ItemDish.DISH_VEGETABLE_SOUP, 10);
        } else {
            for (Map.Entry<String, Property> entry : category.entrySet()) {
                String[] list = entry.getValue().getStringList();
                CookerRecipeRepository.addRecipe(new CookerRecipe(getItem(list, 0), getItem(list, 1), getString(list, 2), getInt(list, 3)));
            }
        }
    }

    public static void initCookingBoardRecipes(Configuration cfg) {
        ConfigCategory category = cfg.getCategory(CATEGORY_RECIPES_CUTTINGBOARD);
        if (category.isEmpty()) {
            // Initialize with defaults
            addRecipe(cfg, "chopped1", new ItemStack(Items.CARROT), new ItemStack(Items.BEETROOT), new ItemStack(Item.getItemFromBlock(Blocks.BROWN_MUSHROOM)), new ItemStack(ModItems.choppedVegetables), 2, "knife");
            addRecipe(cfg, "chopped2", new ItemStack(Items.CARROT), new ItemStack(Items.BEETROOT), new ItemStack(Item.getItemFromBlock(Blocks.RED_MUSHROOM)), new ItemStack(ModItems.choppedVegetables), 2, "knife");
            addRecipe(cfg, "dough", new ItemStack(ModItems.flour), ItemStackTools.getEmptyStack(), ItemStackTools.getEmptyStack(), new ItemStack(ModItems.dough), 10, "roller");
        } else {
            for (Map.Entry<String, Property> entry : category.entrySet()) {
                String[] list = entry.getValue().getStringList();
                boolean roller = "roller".equals(getString(list, 5));
                CuttingBoardRecipeRepository.addRecipe(new CuttingBoardRecipe(getItem(list, 0), getItem(list, 1), getItem(list, 2), getItem(list, 3), getInt(list, 4), roller));
            }
        }
    }

    private static ItemStack getItem(String[] list, int index) {
        if (index >= list.length) {
            return ItemStackTools.getEmptyStack();
        }
        String reg = list[index];
        if ("-".equals(reg)) {
            return ItemStackTools.getEmptyStack();
        }
        return getItem(reg);
    }

    private static ItemStack getItem(String reg) {
        int cnt = 1;
        if (!reg.isEmpty() && Character.isDigit(reg.charAt(0))) {
            int c = 0;
            int i = 0;
            while (i < reg.length() && Character.isDigit(reg.charAt(i))) {
                c = c*10 + (reg.charAt(i)-'0');
                i++;
            }
            if (i < reg.length() && reg.charAt(i) == 'x') {
                reg = reg.substring(i+1);
                cnt = c > 0 ? c : 1;
            }
        }

        String[] split = StringUtils.split(reg, '@');
        String name = split[0];
        int meta = 0;
        if (split.length > 1) {
            meta = Integer.parseInt(split[1]);
        }
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
        if (block != null && block != Blocks.AIR) {
            return new ItemStack(block, cnt, meta);
        }
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
        if (item != null) {
            return new ItemStack(item, cnt, meta);
        }
        return ItemStackTools.getEmptyStack();
    }

    private static int getInt(String[] list, int index) {
        if (index >= list.length) {
            return 0;
        }
        return Integer.parseInt(list[index]);
    }

    private static String getString(String[] list, int index) {
        if (index >= list.length) {
            return "";
        }
        String s = list[index];
        if ("-".equals(s)) {
            return "";
        }
        return s;
    }

    private static String itemToString(ItemStack stack) {
        if (ItemStackTools.isEmpty(stack)) {
            return "-";
        } else {
            String s;
            if (ItemStackTools.getStackSize(stack) > 1) {
                s = Integer.toString(ItemStackTools.getStackSize(stack)) + "x";
            } else {
                s = "";
            }

            s += stack.getItem().getRegistryName().toString();
            if (stack.getItemDamage() != 0) {
                s += "@" + stack.getItemDamage();
            }
            return s;
        }
    }

    private static void addRecipe(Configuration cfg, String name, ItemStack it1, ItemStack it2, ItemStack it3, ItemStack outi, int chopTime, String tool) {
        cfg.get(CATEGORY_RECIPES_CUTTINGBOARD, name, new String[] {
                itemToString(it1),
                itemToString(it2),
                itemToString(it3),
                itemToString(outi),
                Integer.toString(chopTime), tool });
        CuttingBoardRecipeRepository.addRecipe(new CuttingBoardRecipe(it1, it2, it3, outi, chopTime, "roller".equals(tool)));
    }

    private static void addRecipe(Configuration cfg, String name, ItemStack in, ItemStack out, String soup, int cookTime) {
        cfg.get(CATEGORY_RECIPES_COOKER, name, new String[] {
                itemToString(in),
                itemToString(out),
                soup != null && soup.isEmpty() ? "-" : soup,
                Integer.toString(cookTime) });
        CookerRecipeRepository.addRecipe(new CookerRecipe(in, out, soup, cookTime));
    }

    private static void addRecipe(Configuration cfg, String name, ItemStack in, ItemStack out, int grindTime) {
        cfg.get(CATEGORY_RECIPES_GRINDSTONE, name, new String[] {
                itemToString(in),
                itemToString(out),
                Integer.toString(grindTime) });
        GrindstoneRecipeRepository.addRecipe(new GrindstoneRecipe(in, out, grindTime));
    }
}
