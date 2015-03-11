package tnt3530.mod.ModularElements.Networking;

import tnt3530.mod.ModularElements.Common.Constants;
import tnt3530.mod.ModularElements.Common.ModularElements;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig
{
	public ConfigGui(GuiScreen screen)
	{
		super(screen, new ConfigElement(ModularElements.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), 
				Constants.MODNAME, false, false, GuiConfig.getAbridgedConfigPath(ModularElements.config.toString()));
	}
}
