package tnt3530.mod.ModularElements.Common;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class Constants 
{
	public static final String MODID = "modularelements";
	public static final String MODNAME = "Modular Elements by TNT3530";
	public static final String VERSION = "v1 for Minecraft 1.7.10";
	public static final String CLIENT_PROXY_CLASS = "tnt3530.mod.ModularElements.Networking.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "tnt3530.mod.ModularElements.Networking.ServerProxy";
	public static final String GUIFACTORY = "tnt3530.mod.ModularElements.Networking.GuiFactory";
	
	public static int getItemEnergy(Item item)
	{
		if(item == Items.coal) return 10000;
		if(item == Items.diamond) return 100000;
		if(item == Items.golden_apple) return 100000000;
		else return 0;
	}
}
