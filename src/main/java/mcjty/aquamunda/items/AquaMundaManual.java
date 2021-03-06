package mcjty.aquamunda.items;

import mcjty.aquamunda.AquaMunda;
import mcjty.aquamunda.immcraft.ImmersiveCraftHandler;
import mcjty.immcraft.api.book.IBook;
import mcjty.lib.compat.CompatItem;
import mcjty.lib.tools.ChatTools;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AquaMundaManual extends CompatItem implements IBook {

    public AquaMundaManual() {
        setMaxStackSize(1);
        setRegistryName("manual");
        setUnlocalizedName(AquaMunda.MODID + ".manual");
        setCreativeTab(AquaMunda.creativeTab);
        GameRegistry.register(this);
    }

    @Override
    public String getTitle() {
        return "The Aqua Munda Manual";
    }

    @Override
    public ResourceLocation getJson() {
        return new ResourceLocation(AquaMunda.MODID, "text/manual.json");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    protected EnumActionResult clOnItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ChatTools.addChatMessage(player, new TextComponentString("Use this book on a book stand"));
        return EnumActionResult.PASS;
    }

    @Override
    protected ActionResult<ItemStack> clOnItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote) {
            ImmersiveCraftHandler.immersiveCraft.openManual(player);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }


}
