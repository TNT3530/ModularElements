package tnt3530.mod.ModularElements.Networking;

import tnt3530.mod.ModularElements.TileEntity.*;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy 
{
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityAtomWorkbench.class, "tileentityAtomWorkbench");
		GameRegistry.registerTileEntity(TileEntityToolInfuser.class, "tileentityToolInfuser");
		GameRegistry.registerTileEntity(TileEntityAtomCompressor.class, "tileentityAtomCompressor");
		GameRegistry.registerTileEntity(TileEntityAtomDecomposer.class, "tileentityAtomDecomposer");
	}
}
