package tnt3530.mod.ModularElements.TileEntity;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;

import tnt3530.mod.ModularElements.Blocks.BlockAtomWorkbench;
import tnt3530.mod.ModularElements.Blocks.BlockElementalBlock;
import tnt3530.mod.ModularElements.Common.Constants;
import tnt3530.mod.ModularElements.Common.ModularElements;
import tnt3530.mod.ModularElements.Items.CoreElement;
import tnt3530.mod.ModularElements.Items.itemElementalAxe;
import tnt3530.mod.ModularElements.Items.itemElementalHoe;
import tnt3530.mod.ModularElements.Items.itemElementalIngot;
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

public class TileEntityAtomWorkbench extends TileEntity implements ISidedInventory {
	public int[] elementInformation = new int[13]; //int[X] sets max data
	//0, p, n, e, state, stab, val, char, weig, hard, brit, cond, flame

	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };

	private String ones, tens, hunds, thous, sOnes, sHunds, sTens, sThous;
	private int one, ten, hund, thou;
	private boolean fine = false;

	private ItemStack[] stacks = new ItemStack[7];

	public int atomworkbenchBurnTime;
	public int currentBurnTime;
	public int atomworkbenchCookTime;

	//property stuffs
	private double hardness = 0, brittleness = 0, conductivity = 0, stability = 0, flamability = 0,
			state = 0, weight = 0, valence = 0, charge = 0; 

	public int storedEnergy = 0;

	private String atomworkbenchName;
	public void atomworkbenchName(String string){
		this.atomworkbenchName = string;
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
		return this.hasCustomInventoryName() ? this.atomworkbenchName : "Atomic Workbench";
	}
	@Override
	public boolean hasCustomInventoryName() {
		return this.atomworkbenchName != null && this.atomworkbenchName.length() > 0;
	}
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public void updateEntity() {
		boolean flag = this.storedEnergy > 0;
		boolean flag1 = false;
		fine = this.elementInformation[1] > 0;
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

			if(this.stacks[4] != null && this.stacks[4].stackSize > 0
					&& this.stacks[4].getItem() == ModularElements.itemProton)
			{
				this.elementInformation[1]++;
				this.stacks[4].stackSize--;

				if(this.stacks[4].stackSize <= 0)
				{
					this.stacks[4] = null;
				}
			}

			if(this.stacks[5] != null && this.stacks[5].stackSize > 0
					&& this.stacks[5].getItem() == ModularElements.itemNeutron)
			{
				this.elementInformation[2]++;

				this.stacks[5].stackSize--;
				if(this.stacks[5].stackSize <= 0)
				{
					this.stacks[5] = null;
				}
			}
			if(this.stacks[6] != null && this.stacks[6].stackSize > 0
					&& this.stacks[6].getItem() == ModularElements.itemElectron)
			{
				this.elementInformation[3]++;
				this.stacks[6].stackSize--;

				if(this.stacks[6].stackSize <= 0)
				{
					this.stacks[6] = null;
				}
			}

			if(this.stacks[2] != null && this.stacks[2].getItem().getUnlocalizedName().contains("element") && this.storedEnergy > 250 &&
					this.stacks[2].getItem() != ModularElements.basicElement)
			{		
				int[] protons = ElementStorageManager.getProtonsFromName((this.stacks[2].getItem().getUnlocalizedName()));
				int id = ElementStorageManager.getIdFromProtonsAndMass(protons[0], protons[1]);

				//System.out.println("Protons- " + protons[0] + " Mass- " + protons[1] + " ID- " + id);

				String[] data = ElementStorageManager.getElementAndInfo(id);
				int[] data1 = new int[data.length];
				boolean broke = true;

				if(id > 0)
				{
					for(int x = 1; x < 4; x++)
					{
						data1[x] = Integer.parseInt(data[x]);
						System.out.println("X= " + x + " Data1- " + data1[x] + " Info- " + elementInformation[x]);
					}
					boolean t1 = false;
					boolean t2 = false;
					boolean t3 = false;
					
					if(data1[1] <= this.elementInformation[1]) t1 = true;
					if(data1[2] <= this.elementInformation[2]) t2 = true;
					if(data1[3] <= this.elementInformation[3]) t3 = true;
					
					if(t1 && t2 && t3) broke = false;
				}

				if(!broke)
				{
					System.out.println("Elements Matched!");
					this.storedEnergy -= 250;

					this.stacks[2].stackSize++;

					this.elementInformation[1] -= data1[1];
					this.elementInformation[2] -= data1[2];
					this.elementInformation[3] -= data1[3];
				}

			}

			if(fine)
			{
				int check;
				check = ElementStorageManager.getElementGroup(this.elementInformation[1]);
				this.elementInformation = ElementStorageManager.getElementProperties(check, this.elementInformation[1], this.elementInformation[2], this.elementInformation[3]);
			}

			String elementName = "element_" + ElementStorageManager.getElementName(this.elementInformation[1]) + "ium " + "§§(§§" + (this.elementInformation[1] + this.elementInformation[2]) + "§§)";

			if (this.stacks[2] != null && this.stacks[2].stackSize > 0 && 
					this.stacks[2].getItem() == ModularElements.basicElement
					&& this.elementInformation[1] > 0 && this.stacks[3] == null
					&& ElementStorageManager.canCreateElement(elementName) 
					&& this.storedEnergy >= 5000) 
			{		
				boolean pro = true;
				boolean neu = true;
				boolean darn = false;

				for(int i = 1; i < (ElementStorageManager.loadedElementsAndInfo.length); i++)
				{
					String[] data = ElementStorageManager.getElementAndInfo(i);

					if(this.elementInformation[1] == Integer.parseInt(data[1])) 
					{
						pro = false;
					}
					
					if(this.elementInformation[2] == Integer.parseInt(data[2]))
					{
						neu = false;
					}
					
					if(this.elementInformation[1] != Integer.parseInt(data[1])) 
					{
						pro = true;
					}
					
					if(this.elementInformation[2] != Integer.parseInt(data[2]))
					{
						neu = true;
					}
					
					System.out.println("ID- " + i);
					System.out.println(this.elementInformation[1] + " vs " + data[1]);
					System.out.println("Pass- " + pro);
					System.out.println(this.elementInformation[2] + " vs " + data[2]);
					System.out.println("Pass- " + neu);
					System.out.println("");
					
					if(!pro && !neu)
					{
						darn = true;
					}
				}

				if(!darn)
				{
					--this.stacks[2].stackSize;
					this.storedEnergy -= 5000;

					if(this.stacks[2].stackSize < 1)
					{
						this.stacks[2] = null;
					}
					this.createElement(elementName);
				}
				
				if(darn)
				{
					int id = ElementStorageManager.getIdFromProtonsAndMass(this.elementInformation[1], (this.elementInformation[1] + this.elementInformation[2]));
					--this.stacks[2].stackSize;
					this.storedEnergy -=5000;
					
					if(this.stacks[2].stackSize < 1)
					{
						this.stacks[2] = null;
					}
					
					this.stacks[3] = new ItemStack(ElementStorageManager.elements[id]);
					this.stacks[3].setStackDisplayName(ElementStorageManager.getElementName(this.elementInformation[1]) + "ium " + "(" + (this.elementInformation[1] + this.elementInformation[2]) + ")");
					
					for(int i = 1; i < 13; i++)
					{
						this.elementInformation[i] = 0;
					}

					ElementStorageManager.readElements();
				}
			}
		}
		if (flag != this.storedEnergy > 0) {
			flag1 = true;
			BlockAtomWorkbench.updateBlockState(/*this.atomworkbenchBurnTime*/ this.storedEnergy > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
		if (flag1) {
			this.markDirty();
		}
	}

	private void createElement(String ele)
	{
		Item element = new CoreElement(this.elementInformation[1], 
				this.elementInformation[2], this.elementInformation[3], ElementStorageManager.getElementSymbol(this.elementInformation[1]), ele);
		
		//ElementStorageManager.getElementProperties(ElementStorageManager.getElementGroup(this.elementInformation[1]), this.elementInformation[1], this.elementInformation[2], this.elementInformation[3]);
		String name = ElementStorageManager.getElementName(elementInformation[1]) + "ium " + "§§(§§" + (elementInformation[1] + elementInformation[2]) + "§§)";
		
		this.stacks[3] = new ItemStack(element, 1);
		this.stacks[3].setStackDisplayName(name);
		
		ElementStorageManager.storeElement(ele, this.elementInformation[1], this.elementInformation[2], this.elementInformation[3]);

		int hard = elementInformation[9];
		int brit = elementInformation[10];
		
		int dura = (hard * 1024) - (brit * 256);
		int speed = (hard + 14) - (brit);
		
		int x = ElementStorageManager.nextStore;
		
		//Adding Tools
		String nameSimp = ElementStorageManager.getElementName(elementInformation[1]) + "ium(" + (elementInformation[1] + elementInformation[2]) + ")";
		Item basicSword2 = new itemElementalSword("elementalSword_" + nameSimp, ToolMaterial.EMERALD, dura, speed);
		Item basicAxe2 = new itemElementalAxe("elementalAxe_" + nameSimp, ToolMaterial.EMERALD, dura, speed);
		Item basicShovel2 = new itemElementalSpade("elementalShovel_" + nameSimp, ToolMaterial.EMERALD, dura, speed);
		Item basicPick2 = new itemElementalPickaxe("elementalPick_" + nameSimp, ToolMaterial.EMERALD, dura, 5, speed);
		Item basicHoe2 = new itemElementalHoe("elementalHoe_" + nameSimp);
		
		Item basicIngot = new itemElementalIngot("elementalIngot_element_" + name, elementInformation);
		
		Block basicBlock = new BlockElementalBlock(elementInformation, nameSimp);
		
		ElementStorageManager.blocks[x] = basicBlock;
		ElementStorageManager.ingots[x] = basicIngot;	
		ElementStorageManager.swords[x] = basicSword2;
		ElementStorageManager.hoes[x] = basicHoe2;
		ElementStorageManager.axes[x] = basicAxe2;
		ElementStorageManager.spades[x] = basicShovel2;
		ElementStorageManager.picks[x] = basicPick2;
		ElementStorageManager.elements[x] = element;
		
		for(int i = 1; i < 13; i++)
		{
			this.elementInformation[i] = 0;
		}

		ElementStorageManager.readElements();
		ElementStorageManager.nextStore++;
		System.out.println("Sucessfully added element and family.");
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
		this.atomworkbenchBurnTime = tagCompound.getShort("BurnTime");
		this.atomworkbenchCookTime = tagCompound.getShort("CookTime");
		this.elementInformation = tagCompound.getIntArray("atomicStoredElementInfo");
		this.storedEnergy = tagCompound.getInteger("atomicStoredEnergy");

		if (tagCompound.hasKey("CustomName", 8)) {
			this.atomworkbenchName = tagCompound.getString("CustomName");
		}
	}
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		System.out.println("Writing to NBT");
		tagCompound.setInteger("atomicStoredEnergy", this.storedEnergy);
		tagCompound.setIntArray("atomicStoredElementInfo", this.elementInformation);
		tagCompound.setShort("BurnTime", (short) this.atomworkbenchBurnTime);
		tagCompound.setShort("CookTime", (short) this.atomworkbenchBurnTime);	

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
			tagCompound.setString("CustomName", this.atomworkbenchName);
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