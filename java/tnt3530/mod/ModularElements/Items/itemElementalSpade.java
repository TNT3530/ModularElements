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


public class itemElementalSpade extends ItemTool
{	
	private static final Set spadeGood = Sets.newHashSet(new Block[] {Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});

	int speed = 0;

	public itemElementalSpade(String name, ToolMaterial material, int maxdamage, int speed)
	{
		super(2.0F, material, spadeGood);
		setUnlocalizedName(Constants.MODID + "_" + name);
		this.setTextureName(Constants.MODID + ":basicShovel");
		this.setCreativeTab(ModularElements.tabElementalTools);
		GameRegistry.registerItem(this, name);
		this.efficiencyOnProperMaterial = speed;
		this.setMaxDamage(maxdamage);
	}

	public boolean func_150897_b(Block p_150897_1_)
	{
		return p_150897_1_ == Blocks.snow_layer ? true : p_150897_1_ == Blocks.snow;
	}
}
