package tnt3530.mod.ModularElements.TileEntity;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;

//import cofh.api.energy.*;
import tnt3530.mod.ModularElements.Blocks.BlockElementalGenerator;
import tnt3530.mod.ModularElements.Blocks.BlockElementalGenerator;
import tnt3530.mod.ModularElements.Common.Constants;
import tnt3530.mod.ModularElements.Common.ModularElements;
import tnt3530.mod.ModularElements.Items.CoreElement;
import tnt3530.mod.ModularElements.Items.itemElementalAxe;
import tnt3530.mod.ModularElements.Items.itemElementalHoe;
import tnt3530.mod.ModularElements.Items.itemElementalPickaxe;
import tnt3530.mod.ModularElements.Items.itemElementalSpade;
import tnt3530.mod.ModularElements.Items.itemElementalSword;
import tnt3530.mod.ModularElements.Networking.ConfigurationHandler;
import tnt3530.mod.ModularElements.Networking.ElementStorageManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//public class TileEntityElementalGenerator extends TileEnergyHandler implements ISidedInventory, IEnergyProvider
@Deprecated
public class TileEntityElementalGenerator extends TileEntity implements ISidedInventory
{
	//public static EnergyStorage storage;

	public TileEntityElementalGenerator() {
		super();
		//storage = new EnergyStorage(10000, 0, 1000);
	}

	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };

	private ItemStack[] stacks = new ItemStack[3];

	private String elementalgeneratorName;
	public void elementalgeneratorName(String string){
		this.elementalgeneratorName = string;
	}
	@Override
	public int getSizeInventory() {
		return this.stacks.length;
	}
	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.stacks[slot];
	}
	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.stacks[par1] != null) {
			ItemStack itemstack;
			if (this.stacks[par1].stackSize <= par2) {
				itemstack = this.stacks[par1];
				this.stacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.stacks[par1].splitStack(par2);
				if (this.stacks[par1].stackSize == 0) {
					this.stacks[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.stacks[slot] != null) {
			ItemStack itemstack = this.stacks[slot];
			this.stacks[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.stacks[slot] = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}
	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.elementalgeneratorName : "Atom Decomposer";
	}
	@Override
	public boolean hasCustomInventoryName() {
		return this.elementalgeneratorName != null && this.elementalgeneratorName.length() > 0;
	}
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public void updateEntity() {
		/*
		//boolean flag = this.storage.getEnergyStored() > 0;
		boolean flag1 = false;
		if (!this.worldObj.isRemote) 
		{
			if(this.stacks[0] != null && this.stacks[0].stackSize > 0
					&& Constants.getItemEnergy(this.stacks[0].getItem()) > 0
					&& (this.storage.getEnergyStored() + Constants.getItemEnergy(this.stacks[0].getItem()) <= this.storage.getMaxEnergyStored()))
			{
				this.storage.setEnergyStored(this.storage.getEnergyStored() + Constants.getItemEnergy(this.stacks[0].getItem()));

				--this.stacks[0].stackSize;
				if(this.stacks[0].stackSize <= 0)
				{
					this.stacks[0] = null;
				}
			}
		}*/
		
//		if (flag != this.storage.getEnergyStored() > 0) {
//			flag1 = true;
//			BlockElementalGenerator.updateBlockState(/*this.elementalgeneratorBurnTime*/ this.storage.getEnergyStored() > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
//		
//		}
//		if (flag1) {
//			this.markDirty();
//		}
//		if ((storage.getEnergyStored() > 0)) {
//			for (int i = 0; i < 6; i++){
//				TileEntity tile = worldObj.getTileEntity(xCoord + ForgeDirection.getOrientation(i).offsetX, yCoord + ForgeDirection.getOrientation(i).offsetY, zCoord + ForgeDirection.getOrientation(i).offsetZ);
//				if (tile != null && tile instanceof IEnergyReceiver) {
//					storage.extractEnergy(((IEnergyReceiver)tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
//				}
//			}
//		}
	}
	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* IEnergyHandler 
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}
	
	public int getEnergyStored()
	{
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}

	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		System.out.println("Reading from NBT");
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.stacks = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < this.stacks.length) {
				this.stacks[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}
		this.storage.setEnergyStored(tagCompound.getInteger("decomposerStoredEnergy"));
		if (tagCompound.hasKey("CustomName", 8)) {
			this.elementalgeneratorName = tagCompound.getString("CustomName");
		}
	}
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		System.out.println("Writing to NBT");
		tagCompound.setInteger("decomposerStoredEnergy", this.storage.getEnergyStored());
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < this.stacks.length; ++i) {
			if (this.stacks[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte) i);
				this.stacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}
		tagCompound.setTag("Items", tagList);
		if (this.hasCustomInventoryName()) {
			tagCompound.setString("CustomName", this.elementalgeneratorName);
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}
	@Override
	public void openInventory() {
	}
	@Override
	public void closeInventory() {
	}
	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return true;
	}
	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		return par1 == 0 ? slotsBottom : (par1 == 1 ? slotsTop : slotsSides);
	}
	@Override
	public boolean canInsertItem(int par1, ItemStack itemstack, int par3) {
		return this.isItemValidForSlot(par1, itemstack);
	}
	@Override
	public boolean canExtractItem(int par1, ItemStack itemstack, int par3) {
		return par3 != 0 || par1 != 1 || itemstack.getItem() == Items.bucket;
	}
	*/
}
