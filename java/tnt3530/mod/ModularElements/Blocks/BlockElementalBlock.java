package tnt3530.mod.ModularElements.Blocks;

import java.util.Random;

import tnt3530.mod.ModularElements.Common.Constants;
import tnt3530.mod.ModularElements.Common.ModularElements;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockElementalBlock extends Block
{
	private int[] props;
	private int color = 0;
	
    public BlockElementalBlock(int[] props, String name)
    {
        super(Material.rock);
		this.setCreativeTab(ModularElements.tabElementalBlocks);
		this.setBlockName(Constants.MODID + "_" + "elementalBlock" + name);
		GameRegistry.registerBlock(this, "elementalBlock" + name);
		
		//4 State (Solid[1], Liquid[2], Gas[3])
		//5 Stability/Radioactivity (High[1] - Low[100])
		//6 Valence E- (1-8)
		//7 Charge for Ions
		//8 Weight (Light[1] - Heavy[10])
		//9 Hardness (Soft[1] - Hard[10]
		//10 Brittleness (Mallable[1] - Shatter[10]
		//11 Conductivity (Not[0] - Very[10])
		//12 Flamibility (Water[0] - Explosive[100])
		
		this.setStepSound(soundTypeMetal);
		this.setHardness(props[9]/2);
		this.setHarvestLevel("pickaxe", 1);
		this.setResistance(11 - props[10]);
		this.setLightLevel(props[11] / 2);
		
        this.props = props;
        color = (props[1]*2+40 & 255) << 16 | (props[2]*2+40 & 255) << 8 | props[3]*2+40 & 255;
    }
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		this.blockIcon = iconregister.registerIcon(Constants.MODID + ":elementalBlock");
	}
	public IIcon getIcon(int side, int meta) {
			return this.blockIcon;
	}
	public Item getItemDropped(int par1, Random random, int par3) {
		return Item.getItemFromBlock(this);
	}
	public Item getItem(World world, int par2, int par3, int par4) {
		return Item.getItemFromBlock(this);
	}
	
    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
    	color = (props[1]*2+40 & 255) << 16 | (props[2]*2+40 & 255) << 8 | props[3]*2+40 & 255;
        return color;
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
        return this.getBlockColor();
    }
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
    {
        return color;
    }
    
}