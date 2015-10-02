package tnt3530.mod.ModularElements.Items;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import tnt3530.mod.ModularElements.Common.Constants;
import tnt3530.mod.ModularElements.Common.ModularElements;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;


public class itemElementalPickaxe extends ItemTool
{	
    private static final Set pickGood = Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});
    private int[] props;
    int level = 0, speed = 0;
    
    public itemElementalPickaxe(String name, ToolMaterial material, int maxdamage, int level, int speed, int[] props)
    {
    	super(2.0F, material, pickGood);
        setUnlocalizedName(Constants.MODID + "_" + name);
        this.setTextureName(Constants.MODID + ":basicPick");
        this.setCreativeTab(ModularElements.tabElementalTools);
    	GameRegistry.registerItem(this, name);
    	this.efficiencyOnProperMaterial = speed;
    	this.setMaxDamage(maxdamage);
    	this.props = props;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int parColorType)
    {
    	int color = (props[1]*2+40 & 255) << 16 | (props[2]*2+40 & 255) << 8 | props[3]*2+40 & 255;
        return (parColorType == 0) ? color : 0;
    }
    
    public boolean func_150897_b(Block p_150897_1_)
    {
    	return p_150897_1_ == Blocks.obsidian ? level == 3 : (p_150897_1_ != Blocks.diamond_block && p_150897_1_ != Blocks.diamond_ore ? (p_150897_1_ != Blocks.emerald_ore && p_150897_1_ != Blocks.emerald_block ? (p_150897_1_ != Blocks.gold_block && p_150897_1_ != Blocks.gold_ore ? (p_150897_1_ != Blocks.iron_block && p_150897_1_ != Blocks.iron_ore ? (p_150897_1_ != Blocks.lapis_block && p_150897_1_ != Blocks.lapis_ore ? (p_150897_1_ != Blocks.redstone_ore && p_150897_1_ != Blocks.lit_redstone_ore ? (p_150897_1_.getMaterial() == Material.rock ? true : (p_150897_1_.getMaterial() == Material.iron ? true : p_150897_1_.getMaterial() == Material.anvil)) : level >= 2) : level >= 1) : level >= 1) : level >= 2) : level >= 2) : level >= 2);
    }
    
    public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
    {
   		return p_150893_2_.getMaterial() != Material.iron && p_150893_2_.getMaterial() != Material.anvil && p_150893_2_.getMaterial() != Material.rock ? super.func_150893_a(p_150893_1_, p_150893_2_) : this.efficiencyOnProperMaterial;
    }  
}
