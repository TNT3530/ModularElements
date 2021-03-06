package tnt3530.mod.ModularElements.Networking;

import tnt3530.mod.ModularElements.GUI.*;
import tnt3530.mod.ModularElements.TileEntity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == 0)
		{
			TileEntityAtomWorkbench tileentity = (TileEntityAtomWorkbench) world.getTileEntity(x, y, z);
			return new ContainerAtomWorkbench(player.inventory, tileentity);
		}
		if(ID == 1)
		{
			TileEntityToolInfuser tileentity1 = (TileEntityToolInfuser) world.getTileEntity(x, y, z);
			return new ContainerToolInfuser(player.inventory, tileentity1);
		}
		if(ID == 2)
		{
			TileEntityAtomCompressor tileentity1 = (TileEntityAtomCompressor) world.getTileEntity(x, y, z);
			return new ContainerAtomCompressor(player.inventory, tileentity1);
		}
		if(ID == 3)
		{
			TileEntityAtomDecomposer tileentity1 = (TileEntityAtomDecomposer) world.getTileEntity(x, y, z);
			return new ContainerAtomDecomposer(player.inventory, tileentity1);
		}
		if(ID == 4)
		{
			TileEntityElementalGenerator tileentity1 = (TileEntityElementalGenerator) world.getTileEntity(x, y, z);
			return new ContainerElementalGenerator(player.inventory, tileentity1);
		}
		if(ID == 5)
		{
			TileEntityCompWorkbench te = (TileEntityCompWorkbench) world.getTileEntity(x, y, z);
			return new ContainerCompWorkbench(player.inventory, te);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == 0)
		{
			TileEntityAtomWorkbench tileentity = (TileEntityAtomWorkbench) world.getTileEntity(x, y, z);
			return new GuiAtomWorkbench(player.inventory, tileentity);
		}
		if(ID == 1)
		{
			TileEntityToolInfuser tileentity1 = (TileEntityToolInfuser) world.getTileEntity(x, y, z);
			return new GuiToolInfuser(player.inventory, tileentity1);
		}
		if(ID == 2)
		{
			TileEntityAtomCompressor tileentity1 = (TileEntityAtomCompressor) world.getTileEntity(x, y, z);
			return new GuiAtomCompressor(player.inventory, tileentity1);
		}
		if(ID == 3)
		{
			TileEntityAtomDecomposer tileentity1 = (TileEntityAtomDecomposer) world.getTileEntity(x, y, z);
			return new GuiAtomDecomposer(player.inventory, tileentity1);
		}
		if(ID == 4)
		{
			TileEntityElementalGenerator tileentity1 = (TileEntityElementalGenerator) world.getTileEntity(x, y, z);
			return new GuiElementalGenerator(player.inventory, tileentity1);
		}
		if(ID == 5)
		{
			TileEntityCompWorkbench tileentity1 = (TileEntityCompWorkbench) world.getTileEntity(x, y, z);
			return new GuiCompWorkbench(player.inventory, tileentity1);
		}
		else return null;
	}
}
