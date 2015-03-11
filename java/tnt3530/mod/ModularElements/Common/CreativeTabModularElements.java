package tnt3530.mod.ModularElements.Common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabModularElements extends CreativeTabs
{
	private String name;
	public CreativeTabModularElements(int par1, String name, Item display) {
		super(par1, name);
		this.name = name;
	}

	public String getTranslatedTabLabel()
	{
		return Constants.MODID + "_" + "tab" + name;
	}

	@Override
	public Item getTabIconItem() {
		System.out.println(name);
		if(name == "modularelementstabElements") return ModularElements.coreModularElement;
		if(name == "modularelementstabElementalBlocks") return Item.getItemFromBlock(ModularElements.atomCompressor);
		if(name == "modularelementstabElementalMaterials") return ModularElements.itemProton;
		if(name == "modularelementstabElementalTools") return ModularElements.basicPick;
		else return Items.apple;
	}
}
