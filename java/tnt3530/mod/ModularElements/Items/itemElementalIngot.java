package tnt3530.mod.ModularElements.Items;
import java.util.List;

import tnt3530.mod.ModularElements.Common.Constants;
import tnt3530.mod.ModularElements.Common.ModularElements;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;


public class itemElementalIngot extends Item
{	
	private int[] props;
	
    public itemElementalIngot(String name, int[] props)
    {
        super();
        setUnlocalizedName(Constants.MODID + "_" + name);
        this.setTextureName(Constants.MODID + ":elementalIngot");
        this.setCreativeTab(ModularElements.tabElementalMaterials);
    	GameRegistry.registerItem(this, name);
    	this.props = props;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int parColorType)
    {
    	int color = (props[1]*2+40 & 255) << 16 | (props[2]*2+40 & 255) << 8 | props[3]*2+40 & 255;
        return (parColorType == 0) ? color : 0;
    }
}
