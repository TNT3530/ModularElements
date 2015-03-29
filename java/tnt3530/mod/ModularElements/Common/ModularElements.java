package tnt3530.mod.ModularElements.Common;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import tnt3530.mod.ModularElements.Blocks.*;
import tnt3530.mod.ModularElements.Items.*;
import tnt3530.mod.ModularElements.Networking.*;
import tnt3530.mod.ModularElements.TileEntity.*;

@Mod(modid = Constants.MODID, name = Constants.MODNAME, version = Constants.VERSION)


public class ModularElements 
{
	public static SimpleNetworkWrapper network;
	public static Item coreModularElement, basicElement, itemProton, itemElectron, itemNeutron;
	public static Item basicSword, basicPick, basicAxe, basicShovel, basicHoe;
	public static Item basicSword2, basicPick2, basicAxe2, basicShovel2, basicHoe2;
	public static Item basicIngot;
	public static Block atomWorkbench, toolInfuser, atomCompressor, atomDecomposer;
	public static Block basicBlock;
	//public static Item basicHelmet, basicChest, basicLegs, basicFeet;

	@SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	@Instance(Constants.MODID)
	public static ModularElements instance;

	public static Configuration config;
	
	public static CreativeTabs tabElements = new CreativeTabModularElements(CreativeTabs.getNextID(), Constants.MODID + "tabElements", basicElement);
	public static CreativeTabs tabElementalBlocks = new CreativeTabModularElements(CreativeTabs.getNextID(), Constants.MODID + "tabElementalBlocks", basicIngot);
	public static CreativeTabs tabElementalMaterials = new CreativeTabModularElements(CreativeTabs.getNextID(), Constants.MODID + "tabElementalMaterials", itemProton);
	public static CreativeTabs tabElementalTools = new CreativeTabModularElements(CreativeTabs.getNextID(), Constants.MODID + "tabElementalTools", itemProton);
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equals(Constants.MODID))
		{
			ConfigurationHandler.syncConfig();
		}
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		System.out.println("Modular Elements PreInit started.");

		config = new Configuration(event.getSuggestedConfigurationFile());
		ConfigurationHandler.syncConfig();

		if(ConfigurationHandler.maxElements > 0)
		{
			System.out.println("YEA IT READS");
			System.out.println(ConfigurationHandler.maxElements);
		}

		//For adding Blocks, Items, WorldGen, Etc
		//coreModularElement = new CoreElement(0, 0, 0, null, "coreElement");
		basicElement = new itemsMain("basicElement");
		
		basicSword = new itemElementalSword("basicSword", ToolMaterial.EMERALD, 1024, 7.0F);
		basicPick = new itemElementalPickaxe("basicPick", ToolMaterial.EMERALD, 1024, 4, 14);
		basicAxe = new itemElementalAxe("basicAxe", ToolMaterial.EMERALD, 1024, 14);
		basicHoe = new itemElementalHoe("basicHoe");
		basicShovel = new itemElementalSpade("basicShovel", ToolMaterial.EMERALD, 1024, 14);
		
		itemProton = new itemsMain("itemProton");
		itemElectron = new itemsMain("itemElectron");
		itemNeutron = new itemsMain("itemNeutron");
		
		atomWorkbench = new BlockAtomWorkbench();
		toolInfuser = new BlockToolInfuser();
		atomCompressor = new BlockAtomCompressor();
		atomDecomposer = new BlockAtomDecomposer();
		
		ElementStorageManager.readElements();

		if(ElementStorageManager.loadedElementsAndInfo != null)
		{		
			System.out.println("Element Read Start");
			for(int x = 0; x < ElementStorageManager.loadedElementsAndInfo.length; x++)
			{
				System.out.println("Adding Element" + x);
				String[] data = ElementStorageManager.getElementAndInfo(x);		
				
				int pro = Integer.parseInt(data[1]);
				int neu = Integer.parseInt(data[2]);
				int ele = Integer.parseInt(data[3]);
				
				int group = ElementStorageManager.getElementGroup(Integer.parseInt(data[1]));
				int[] props = ElementStorageManager.getElementProperties(group,pro, neu, ele);
				
				int hard = props[9];
				int brit = props[10];
				
				int dura = (hard * 1024) - (brit * 256);
				int speed = (hard + 14) - (brit);
				
				String name = ElementStorageManager.getFullName(pro, neu);
				
				//Adding Tools
				String nameSimp = ElementStorageManager.getElementName(pro) + "ium (" + (neu + pro) + ")";
				basicSword2 = new itemElementalSword("elementalSword_" + nameSimp, ToolMaterial.EMERALD, dura, speed);
				basicAxe2 = new itemElementalAxe("elementalAxe_" + nameSimp, ToolMaterial.EMERALD, dura, speed);
				basicShovel2 = new itemElementalSpade("elementalShovel_" + nameSimp, ToolMaterial.EMERALD, dura, speed);
				basicPick2 = new itemElementalPickaxe("elementalPick_" + nameSimp, ToolMaterial.EMERALD, dura, 4, speed);
				basicHoe2 = new itemElementalHoe("elementalHoe_" + nameSimp);
				ElementStorageManager.swords[x] = basicSword2;
				ElementStorageManager.hoes[x] = basicHoe2;
				ElementStorageManager.axes[x] = basicAxe2;
				ElementStorageManager.spades[x] = basicShovel2;
				ElementStorageManager.picks[x] = basicPick2;
				
				//Adding Elements
				coreModularElement = new CoreElement(pro, neu, ele, ElementStorageManager.getElementSymbol(pro), name);
				coreModularElement.setTextureName(Constants.MODID + ":coreElement");
				coreModularElement.setUnlocalizedName(Constants.MODID + "_" + name);
				System.out.println("Element " + name + " has been added.");
				ElementStorageManager.elements[x] = coreModularElement;
				
				//Adding Ingots
				basicIngot = new itemElementalIngot("elementalIngot_" + name, props);
				ElementStorageManager.ingots[x] = basicIngot;
				
				//Adding Blocks
				basicBlock = new BlockElementalBlock(props, nameSimp);
				ElementStorageManager.blocks[x] = basicBlock;
				
				ElementStorageManager.nextStore = (x + 1);
				
				GameRegistry.addRecipe(new ItemStack(this.atomWorkbench), new Object[] {"SSS", "ONO", "ORO", 'S', Blocks.stone, 'N', Items.nether_star, 'R', Items.redstone, 'O', Blocks.obsidian});
				GameRegistry.addRecipe(new ItemStack(this.atomDecomposer), new Object[] {"SSS", "ODO", "ORO", 'S', Blocks.stone, 'D', Items.diamond, 'R', Items.redstone, 'O', Blocks.obsidian});
				GameRegistry.addRecipe(new ItemStack(this.atomCompressor), new Object[] {"SPS", "ONO", "ORO", 'S', Blocks.stone, 'N', Items.diamond, 'R', Items.redstone, 'O', Blocks.obsidian, 'P', Blocks.piston});
				GameRegistry.addRecipe(new ItemStack(this.toolInfuser), new Object[] {"SPS", "PXP", "ORO", 'S', Blocks.stone, 'R', Items.redstone, 'O', Blocks.obsidian, 'P', Blocks.piston});
				GameRegistry.addRecipe(new ItemStack(this.basicElement, 16), new Object[] {"GXG", "GPG", "XGX", 'G', Blocks.glass, 'P', Items.water_bucket});
				
				GameRegistry.addRecipe(new ItemStack(this.basicSword), new Object[] {"XXI", "OIX", "SOX", 'I', Blocks.iron_block, 'O', Blocks.obsidian, 'S', Items.stick});
				GameRegistry.addRecipe(new ItemStack(this.basicAxe), new Object[] {"XII", "XOI", "XSX", 'I', Blocks.iron_block, 'O', Blocks.obsidian, 'S', Items.stick});
				GameRegistry.addRecipe(new ItemStack(this.basicShovel), new Object[] {"XIX", "XOX", "XSX", 'I', Blocks.iron_block, 'O', Blocks.obsidian, 'S', Items.stick});
				GameRegistry.addRecipe(new ItemStack(this.basicPick), new Object[] {"III", "XOX", "XSX", 'I', Blocks.iron_block, 'O', Blocks.obsidian, 'S', Items.stick});
				GameRegistry.addRecipe(new ItemStack(this.basicHoe), new Object[] {"XII", "XOX", "XSX", 'I', Blocks.iron_block, 'O', Blocks.obsidian, 'S', Items.stick});
			}	
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		System.out.println("Modular Elements Init started.");
		//For adding TileEntites, Events, Renderers
		proxy.registerTileEntities();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		FMLCommonHandler.instance().bus().register(instance);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		//For Addons for other mods
	}
}
