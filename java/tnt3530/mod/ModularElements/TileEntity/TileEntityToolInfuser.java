package tnt3530.mod.ModularElements.TileEntity;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;

import tnt3530.mod.ModularElements.Blocks.BlockToolInfuser;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityToolInfuser extends TileEntity implements ISidedInventory {
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };

	private int[] realprops = new int[20];
	private Item basicSword2, basicPick2, basicAxe2, basicHoe2, basicShovel2;
	private boolean full = false;
	
	private ItemStack[] stacks = new ItemStack[4];

	public int storedEnergy = 0;

	private String toolinfuserName;
	public void toolinfuserName(String string){
		this.toolinfuserName = string;
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
		return this.hasCustomInventoryName() ? this.toolinfuserName : "Tool Infuser";
	}
	@Override
	public boolean hasCustomInventoryName() {
		return this.toolinfuserName != null && this.toolinfuserName.length() > 0;
	}
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public void updateEntity() {
		boolean flag = this.storedEnergy > 0;
		boolean flag1 = false;
		if (!this.worldObj.isRemote) 
		{
			if(this.stacks[0] != null && this.stacks[0].stackSize > 0
					&& Constants.getItemEnergy(this.stacks[0].getItem()) > 0
					&& (this.storedEnergy + Constants.getItemEnergy(this.stacks[0].getItem()) < 1000000000))
			{
				storedEnergy = storedEnergy + Constants.getItemEnergy(this.stacks[0].getItem());

				--this.stacks[0].stackSize;
				if(this.stacks[0].stackSize <= 0)
				{
					this.stacks[0] = null;
				}
			}
			String elementName = "";

			if(this.stacks[1] != null && this.stacks[1].stackSize >= 32 && !full)
			{		
				elementName = this.stacks[1].getItem().getUnlocalizedName();
				int info[] = ElementStorageManager.getProtonsFromName(elementName);
				int id = ElementStorageManager.getIdFromProtonsAndMass(info[0], info[1]);
				String elementfound[] = ElementStorageManager.getElementAndInfo(id);
				int pro = Integer.parseInt(elementfound[1]);
				int neu = Integer.parseInt(elementfound[2]);
				int ele = Integer.parseInt(elementfound[3]);
				int group = ElementStorageManager.getElementGroup(Integer.parseInt(elementfound[1]));
				realprops = ElementStorageManager.getElementProperties(group, pro, neu, ele);
				
				this.stacks[1].stackSize -= 32;

				if(this.stacks[1].stackSize <= 0)
				{
					this.stacks[1] = null;
				}
				
				full = true;
			}

			if(this.stacks[2] != null && this.storedEnergy > 10000 && this.stacks[3] == null)
			{
				//System.out.println("YEA!");
				String type = "";
				boolean no = true;
				if(this.stacks[2].getItem() == ModularElements.basicAxe)
				{
					System.out.println("Axe Detected");
					type = "Axe";
					no = false;
				}
				if(this.stacks[2].getItem() == ModularElements.basicPick)
				{
					System.out.println("Pickaxe Detected");
					type = "Pickaxe";
					no = false;
				}
				if(this.stacks[2].getItem() == ModularElements.basicHoe)
				{
					System.out.println("Hoe Detected");
					type = "Hoe";
					no = false;
				}
				if(this.stacks[2].getItem() == ModularElements.basicShovel) 
				{
					System.out.println("Shovel Detected");
					type = "Spade";
					no = false;
				}
				if(this.stacks[2].getItem() == ModularElements.basicSword)
				{
					System.out.println("Sword Detected");
					type = "Sword";
					no = false;
				}
				
				if(!no)
				{
					System.out.println("SHIT NIG");
					for(int i = 0; i < realprops.length; i++)
					{
						System.out.println("?= " + realprops[i] + " I= " + i);
					}
					String displayName = ElementStorageManager.getElementName(realprops[1]);
					//4 State (Solid[1], Liquid[2], Gas[3])
					//5 Stability/Radioactivity (High[1] - Low[100])
					//6 Valence E- (1-8)
					//7 Charge for Ions
					//8 Weight (Light[1] - Heavy[10])
					//9 Hardness (Soft[1] - Hard[10]
					//10 Brittleness (Mallable[1] - Shatter[10]
					//11 Conductivity (Not[0] - Very[10])
					//12 Flamibility (Water[0] - Explosive[100])

					int id = ElementStorageManager.getIdFromProtonsAndMass(realprops[1], (realprops[1] + realprops[2]));
					Item returnedTool = null;
					
					if(type == "Sword")
					{
						returnedTool = ElementStorageManager.swords[id];
					}
					if(type == "Pickaxe")
					{
						returnedTool = ElementStorageManager.picks[id];
					}
					if(type == "Spade")
					{
						returnedTool = ElementStorageManager.spades[id];
					}
					if(type == "Axe")
					{
						returnedTool = ElementStorageManager.axes[id];
					}
					if(type == "Hoe")
					{
						returnedTool = ElementStorageManager.hoes[id];
					}
					
					--this.stacks[2].stackSize;
					if(this.stacks[2].stackSize <= 0)
					{
						this.stacks[2] = null;
					}
					this.storedEnergy -= 10000;
					
					this.stacks[3] = new ItemStack(returnedTool, 1);
					this.stacks[3].setStackDisplayName(displayName + "ium " + type);
					
					full = false;
				}
			}
			

		}
		if (flag != this.storedEnergy > 0) {
			flag1 = true;
			BlockToolInfuser.updateBlockState(/*this.toolinfuserBurnTime*/ this.storedEnergy > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
		if (flag1) {
			this.markDirty();
		}
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
		this.storedEnergy = tagCompound.getInteger("infuserStoredEnergy");
		this.realprops = tagCompound.getIntArray("infuserStoredElementInfo");
		if (tagCompound.hasKey("CustomName", 8)) {
			this.toolinfuserName = tagCompound.getString("CustomName");
		}
	}
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		System.out.println("Writing to NBT");
		tagCompound.setInteger("infuserStoredEnergy", this.storedEnergy);
		tagCompound.setIntArray("infuserStoredElementInfo", this.realprops);
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
			tagCompound.setString("CustomName", this.toolinfuserName);
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
}