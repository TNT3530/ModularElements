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


public class itemElementalAxe extends ItemTool
{	
	private static final Set axeGood = Sets.newHashSet(new Block[] {Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});

	int speed = 0;

	public itemElementalAxe(String name, ToolMaterial material, int maxdamage, int speed)
	{
		super(2.0F, material, axeGood);
		setUnlocalizedName(Constants.MODID + "_" + name);
		this.setTextureName(Constants.MODID + ":basicAxe");
		this.setCreativeTab(ModularElements.tabElementalTools);
		GameRegistry.registerItem(this, name);
		this.efficiencyOnProperMaterial = speed;
		this.setMaxDamage(maxdamage);
	}

	public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
	{
		return p_150893_2_.getMaterial() != Material.wood && p_150893_2_.getMaterial() != Material.plants && p_150893_2_.getMaterial() != Material.vine ? super.func_150893_a(p_150893_1_, p_150893_2_) : this.efficiencyOnProperMaterial;
	}  
}
