package tnt3530.mod.ModularElements.Items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tnt3530.mod.ModularElements.Common.*;

public class CoreElement extends Item
{
	private String formulaDisplayed;
	private int protonAmount, neutronAmount, electronAmount;

    public CoreElement(int protonAmount, 
    		int neutronAmount, int electronAmount, String formula, String name)
    {
    	setUnlocalizedName(Constants.MODID + "_" + name);
    	GameRegistry.registerItem(this, name);
    	setCreativeTab(ModularElements.tabElements);
    	this.protonAmount = protonAmount;
    	this.neutronAmount = neutronAmount;
    	this.electronAmount = electronAmount;
    	this.formulaDisplayed = formula;
    	this.canRepair = false;
    	this.setTextureName(Constants.MODID + ":coreElement");
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
    	int mass = this.protonAmount + this.neutronAmount;
        par3List.add(this.formulaDisplayed);
        par3List.add("Atomic Number: " + this.protonAmount);
        par3List.add("Atomic Mass: " + mass);
    }
}

