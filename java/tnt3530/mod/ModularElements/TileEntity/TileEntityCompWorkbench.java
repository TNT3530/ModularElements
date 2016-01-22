package tnt3530.mod.ModularElements.TileEntity;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;

import tnt3530.mod.ModularElements.Blocks.BlockAtomWorkbench;
import tnt3530.mod.ModularElements.Blocks.BlockCompWorkbench;
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
import tnt3530.mod.ModularElements.Networking.CompoundStorageManager;
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

public class TileEntityCompWorkbench extends TileEntity implements ISidedInventory {
	public int[] compoundInformation = new int[49]; //int[X] sets max data
	//0, p, n, e, state, stab, val, char, weig, hard, brit, cond, flame
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };
	private boolean fine = false;
	private String ones, tens, hunds, thous, sOnes, sHunds, sTens, sThous;
	private int one, ten, hund, thou;
	public int[] inputElementsForCompounding = new int[] {0, 1, 0, 1, 0, 1, 0, 1, 0, 1};

	private ItemStack[] stacks = new ItemStack[8];

	public int compworkbenchBurnTime;
	public int currentBurnTime;
	public int compworkbenchCookTime;

	//property stuffs
	private double hardness = 0, brittleness = 0, conductivity = 0, stability = 0, flamability = 0,
			state = 0, weight = 0, valence = 0, charge = 0; 

	public int storedEnergy = 0;

	private String compworkbenchName;
	public void compworkbenchName(String string){
		this.compworkbenchName = string;
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
		return this.hasCustomInventoryName() ? this.compworkbenchName : "Compound Workbench";
	}
	@Override
	public boolean hasCustomInventoryName() {
		return this.compworkbenchName != null && this.compworkbenchName.length() > 0;
	}
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

public void updateEntity() {
		boolean flag = this.storedEnergy > 0;
		boolean flag1 = false;
		fine = this.compoundInformation[1] > 0;
		if (!this.worldObj.isRemote) 
		{
			if(this.stacks[0] != null && this.stacks[0].stackSize > 0
					&& Constants.getItemEnergy(this.stacks[0].getItem()) > 0
					&& (this.storedEnergy + Constants.getItemEnergy(this.stacks[0].getItem()) <= 1000000000))
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
				this.compoundInformation[1]++;
				this.stacks[4].stackSize--;

				if(this.stacks[4].stackSize <= 0)
				{
					this.stacks[4] = null;
				}
			}

			if(this.stacks[5] != null && this.stacks[5].stackSize > 0
					&& this.stacks[5].getItem() == ModularElements.itemNeutron)
			{
				this.compoundInformation[2]++;

				this.stacks[5].stackSize--;
				if(this.stacks[5].stackSize <= 0)
				{
					this.stacks[5] = null;
				}
			}
			if(this.stacks[6] != null && this.stacks[6].stackSize > 0
					&& this.stacks[6].getItem() == ModularElements.itemElectron)
			{
				this.compoundInformation[3]++;
				this.stacks[6].stackSize--;

				if(this.stacks[6].stackSize <= 0)
				{
					this.stacks[6] = null;
				}
			}

			if(this.stacks[2] != null && this.stacks[2].getItem().getUnlocalizedName().contains("compound") && this.storedEnergy > 250 &&
					this.stacks[2].getItem() != ModularElements.basicElement)
			{		
				int[] protons = CompoundStorageManager.getProtonsFromName((this.stacks[2].getItem().getUnlocalizedName()));
				int id = CompoundStorageManager.getIdFromProtonsAndMass(protons[0], protons[1]);

				//System.out.println("Protons- " + protons[0] + " Mass- " + protons[1] + " ID- " + id);

				String[] data = CompoundStorageManager.getCompoundAndInfo(id);
				int[] data1 = new int[data.length];
				boolean broke = true;

				if(id > 0)
				{
					for(int x = 1; x < 4; x++)
					{
						data1[x] = Integer.parseInt(data[x]);
						System.out.println("X= " + x + " Data1- " + data1[x] + " Info- " + compoundInformation[x]);
					}
					boolean t1 = false;
					boolean t2 = false;
					boolean t3 = false;
					
					if(data1[1] <= this.compoundInformation[1]) t1 = true;
					if(data1[2] <= this.compoundInformation[2]) t2 = true;
					if(data1[3] <= this.compoundInformation[3]) t3 = true;
					
					if(t1 && t2 && t3) broke = false;
				}

				if(!broke)
				{
					System.out.println("Compounds Matched!");
					this.storedEnergy -= 250;

					this.stacks[2].stackSize++;

					this.compoundInformation[1] -= data1[1];
					this.compoundInformation[2] -= data1[2];
					this.compoundInformation[3] -= data1[3];
				}

			}

			if(fine)
			{
				int check;
				check = CompoundStorageManager.getCompoundGroup(this.compoundInformation[1]);
				this.compoundInformation = CompoundStorageManager.calculateAdvProps(check, this.compoundInformation[1], this.compoundInformation[2], this.compoundInformation[3]);
			}

			//String compoundName = CompoundStorageManager.getFullName(this.compoundInformation[1], this.compoundInformation[2]);
			String compoundName = CompoundStorageManager.getDisplayName(this.compoundInformation[1], this.compoundInformation[2]);
			
			if (this.stacks[2] != null && this.stacks[2].stackSize > 0 && 
					this.stacks[2].getItem() == ModularElements.basicElement
					&& this.compoundInformation[1] > 0 && this.stacks[3] == null
					&& CompoundStorageManager.canCreateCompound(compoundName) 
					&& this.storedEnergy >= 5000) 
			{		
				boolean pro = true;
				boolean neu = true;
				boolean darn = false;

				for(int i = 1; i < (CompoundStorageManager.loadedCompoundsAndInfo.length); i++)
				{
					String[] data = CompoundStorageManager.getCompoundAndInfo(i);

					if(this.compoundInformation[1] == Integer.parseInt(data[1])) 
					{
						pro = false;
					}
					
					if(this.compoundInformation[2] == Integer.parseInt(data[2]))
					{
						neu = false;
					}
					
					if(this.compoundInformation[1] != Integer.parseInt(data[1])) 
					{
						pro = true;
					}
					
					if(this.compoundInformation[2] != Integer.parseInt(data[2]))
					{
						neu = true;
					}
					
					System.out.println("ID- " + i);
					System.out.println(this.compoundInformation[1] + " vs " + data[1]);
					System.out.println("Pass- " + pro);
					System.out.println(this.compoundInformation[2] + " vs " + data[2]);
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
					this.createCompound(compoundName);
				}
				
				if(darn)
				{
					int id = CompoundStorageManager.getIdFromProtonsAndMass(this.compoundInformation[1], (this.compoundInformation[1] + this.compoundInformation[2]));
					--this.stacks[2].stackSize;
					this.storedEnergy -=5000;
					
					if(this.stacks[2].stackSize < 1)
					{
						this.stacks[2] = null;
					}
					
					this.stacks[3] = new ItemStack(CompoundStorageManager.compounds[id]);
					this.stacks[3].setStackDisplayName(CompoundStorageManager.getCompoundName(this.compoundInformation[1]) + "ium " + "(" + (this.compoundInformation[1] + this.compoundInformation[2]) + ")");
					
					for(int i = 1; i < 13; i++)
					{
						this.compoundInformation[i] = 0;
					}

					CompoundStorageManager.readCompounds();
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

	private void createCompound(String ele)
	{
		Item compound = new CoreElement(this.compoundInformation[1], 
				this.compoundInformation[2], this.compoundInformation[3], CompoundStorageManager.getCompoundSymbol(this.compoundInformation[1]), ele);
		
		//CompoundStorageManager.getCompoundProperties(CompoundStorageManager.getCompoundGroup(this.compoundInformation[1]), this.compoundInformation[1], this.compoundInformation[2], this.compoundInformation[3]);
		String name = CompoundStorageManager.getDisplayName(this.compoundInformation[1], this.compoundInformation[2]);
		
		this.stacks[3] = new ItemStack(compound, 1);
		this.stacks[3].setStackDisplayName(name);
		
		CompoundStorageManager.storeCompound(ele, this.compoundInformation[1], this.compoundInformation[2], this.compoundInformation[3]);

		int hard = compoundInformation[9];
		int brit = compoundInformation[10];
		
		int dura = (hard * 1024) - (brit * 256);
		int speed = (hard + 8) - (brit);
		
		int x = CompoundStorageManager.nextStore;
		
		//Adding Tools
		String nameSimp = CompoundStorageManager.getCompoundName(compoundInformation[1]) + "ium(" + (compoundInformation[1] + compoundInformation[2]) + ")";
		Item basicSword2 = new itemElementalSword("compoundalSword_" + nameSimp, ToolMaterial.EMERALD, dura, speed, compoundInformation);
		Item basicAxe2 = new itemElementalAxe("compoundalAxe_" + nameSimp, ToolMaterial.EMERALD, dura, speed, compoundInformation);
		Item basicShovel2 = new itemElementalSpade("compoundalShovel_" + nameSimp, ToolMaterial.EMERALD, dura, speed, compoundInformation);
		Item basicPick2 = new itemElementalPickaxe("compoundalPick_" + nameSimp, ToolMaterial.EMERALD, dura, 5, speed, compoundInformation);
		Item basicHoe2 = new itemElementalHoe("compoundalHoe_" + nameSimp, compoundInformation);
		
		Item basicIngot = new itemElementalIngot("compoundalIngot_" + name, compoundInformation);
		
		Block basicBlock = new BlockElementalBlock(compoundInformation, nameSimp);
		
		CompoundStorageManager.blocks[x] = basicBlock;
		CompoundStorageManager.ingots[x] = basicIngot;	
		CompoundStorageManager.swords[x] = basicSword2;
		CompoundStorageManager.hoes[x] = basicHoe2;
		CompoundStorageManager.axes[x] = basicAxe2;
		CompoundStorageManager.spades[x] = basicShovel2;
		CompoundStorageManager.picks[x] = basicPick2;
		CompoundStorageManager.compounds[x] = compound;
		
		for(int i = 1; i < 13; i++)
		{
			this.compoundInformation[i] = 0;
		}

		CompoundStorageManager.readCompounds();
		CompoundStorageManager.nextStore++;
		System.out.println("Sucessfully added compound and family.");
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
		this.compworkbenchBurnTime = tagCompound.getShort("BurnTime");
		this.compworkbenchCookTime = tagCompound.getShort("CookTime");
		this.compoundInformation = tagCompound.getIntArray("compicStoredCompoundInfo");
		this.storedEnergy = tagCompound.getInteger("compicStoredEnergy");

		if (tagCompound.hasKey("CustomName", 8)) {
			this.compworkbenchName = tagCompound.getString("CustomName");
		}
	}
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		System.out.println("Writing to NBT");
		tagCompound.setInteger("compicStoredEnergy", this.storedEnergy);
		tagCompound.setIntArray("compicStoredCompoundInfo", this.compoundInformation);
		tagCompound.setShort("BurnTime", (short) this.compworkbenchBurnTime);
		tagCompound.setShort("CookTime", (short) this.compworkbenchBurnTime);	

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
			tagCompound.setString("CustomName", this.compworkbenchName);
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