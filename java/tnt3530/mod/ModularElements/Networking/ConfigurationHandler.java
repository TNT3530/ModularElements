package tnt3530.mod.ModularElements.Networking;

import tnt3530.mod.ModularElements.Common.ModularElements;
import cpw.mods.fml.common.FMLCommonHandler;

public class ConfigurationHandler 
{
	public static int maxElements;
	public static final int MAXELEMENTS_DEFAULT = 9999;
	public static final String MAXELEMENTS_NAME= "Max Created Elements";
	public static final int MAXELEMENTS_MAX = 9999;
	public static final int MAXELEMENTS_MIN = 1;
	
	public static void syncConfig()
	{
		FMLCommonHandler.instance().bus().register(ModularElements.instance);
		
		final String MAXELEMENTS = ModularElements.config.CATEGORY_GENERAL + ModularElements.config.CATEGORY_SPLITTER + "Max Elements";	
		
		ModularElements.config.addCustomCategoryComment(MAXELEMENTS, "How many elements can be created");
		maxElements = ModularElements.config.getInt(MAXELEMENTS, MAXELEMENTS, MAXELEMENTS_DEFAULT, MAXELEMENTS_MIN, MAXELEMENTS_MAX, "Max Created Elements");
		
		if(ModularElements.config.hasChanged())
		{
			ModularElements.config.save();
		}
	}
}
