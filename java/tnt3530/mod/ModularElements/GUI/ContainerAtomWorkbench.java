package tnt3530.mod.ModularElements.GUI;
import tnt3530.mod.ModularElements.TileEntity.TileEntityAtomWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

	public class ContainerAtomWorkbench extends Container {
		private TileEntityAtomWorkbench tileatomWorkbench;
		private int lastCookTime;
		private int lastBurnTime;
		private int lastItemBurnTime;
		private int storedEnergy;
		private int[] infoArray = new int[13];
		private int[] storedArray = new int[4];
		
		public ContainerAtomWorkbench(InventoryPlayer player, TileEntityAtomWorkbench tileEntityatomWorkbench){
			this.tileatomWorkbench = tileEntityatomWorkbench;
			this.addSlotToContainer(new Slot(tileEntityatomWorkbench, 0, 6, -16)); //Fuel In
			this.addSlotToContainer(new SlotFurnace(player.player, tileEntityatomWorkbench, 1, 6, 2)); //Fuel Out
			this.addSlotToContainer(new Slot(tileEntityatomWorkbench, 2, 39, 14)); //Processer
			this.addSlotToContainer(new SlotFurnace(player.player, tileEntityatomWorkbench, 3, 92, 14));
			
			this.addSlotToContainer(new Slot(tileEntityatomWorkbench, 4, 11, 47)); //P
			this.addSlotToContainer(new Slot(tileEntityatomWorkbench, 5, 11, 65)); //N
			this.addSlotToContainer(new Slot(tileEntityatomWorkbench, 6, 11, 65 + (65-47))); //E
			
			int i;
			for(i = 0; i < 3; ++i){
				for(int j = 0; j < 9; ++j){
					this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 36 + j * 18, 115 + i * 18));
				}
			}
			for(i = 0; i < 9; ++i){
				this.addSlotToContainer(new Slot(player, i , 36 + i * 18 , 173));
			}
		}
		public void addCraftingToCrafters(ICrafting craft){
			super.addCraftingToCrafters(craft);
			craft.sendProgressBarUpdate(this, 0, this.tileatomWorkbench.atomworkbenchCookTime);
			craft.sendProgressBarUpdate(this, 1, this.tileatomWorkbench.atomworkbenchBurnTime);
			craft.sendProgressBarUpdate(this, 2, this.tileatomWorkbench.currentBurnTime);
			craft.sendProgressBarUpdate(this, 3, this.tileatomWorkbench.storedEnergy);
			
			for(int i = 1; i < 13; i++)
			{
				craft.sendProgressBarUpdate(this, 3 + i, this.tileatomWorkbench.elementInformation[i]);
			}
		}
		public void detectAndSendChanges(){
			super.detectAndSendChanges();
			for(int i = 0; i < this.crafters.size(); ++i){
				ICrafting craft = (ICrafting) this.crafters.get(i);
				if(this.lastCookTime != this.tileatomWorkbench.atomworkbenchCookTime){
					craft.sendProgressBarUpdate(this, 0, this.tileatomWorkbench.atomworkbenchCookTime);
				}
				if(this.lastBurnTime != this.tileatomWorkbench.atomworkbenchBurnTime){
					craft.sendProgressBarUpdate(this, 1, this.tileatomWorkbench.atomworkbenchBurnTime);
				}
				if(this.lastItemBurnTime != this.tileatomWorkbench.currentBurnTime){
					craft.sendProgressBarUpdate(this, 2, this.tileatomWorkbench.currentBurnTime);
				}
				if(this.storedEnergy != this.tileatomWorkbench.storedEnergy){
					craft.sendProgressBarUpdate(this, 3, this.tileatomWorkbench.storedEnergy);
				}
				for(int i1 = 1; i1 < 13; i1++)
				{
					if(this.infoArray[i1] != tileatomWorkbench.elementInformation[i1])
					{
						craft.sendProgressBarUpdate(this, 3 + i1, this.tileatomWorkbench.elementInformation[i1]);
					}
				}
			}
			this.lastBurnTime = this.tileatomWorkbench.atomworkbenchBurnTime;
			this.lastCookTime = this.tileatomWorkbench.atomworkbenchCookTime;
			this.lastItemBurnTime = this.tileatomWorkbench.currentBurnTime;
			this.storedEnergy = this.tileatomWorkbench.storedEnergy;
			this.infoArray = this.tileatomWorkbench.elementInformation;
		}
		@SideOnly(Side.CLIENT)
		public void updateProgressBar(int par1, int par2){
			if(par1 == 0){
				this.tileatomWorkbench.atomworkbenchCookTime = par2;
			}
			if(par1 == 1){
				this.tileatomWorkbench.atomworkbenchBurnTime = par2;
			}
			if(par1 == 2){
				this.tileatomWorkbench.currentBurnTime = par2;
			}
			if(par1 == 3)
			{
				this.tileatomWorkbench.storedEnergy = par2;
			}
			for(int i = 4; i < 16; i++)
			{
				if(par1 == i)
				{
					this.tileatomWorkbench.elementInformation[i-3] = par2;
				}
			}
		}
		@Override
		public boolean canInteractWith(EntityPlayer player) {
			return this.tileatomWorkbench.isUseableByPlayer(player);
		}
		public ItemStack transferStackInSlot(EntityPlayer player, int par2){
			ItemStack itemstack = null;
			Slot slot = (Slot) this.inventorySlots.get(par2);
			if(slot != null && slot.getHasStack()){
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
				if(par2 == 2){
					if(!this.mergeItemStack(itemstack1, 3, 39, true)){
						return null;
					}
					slot.onSlotChange(itemstack1, itemstack);
				}else if(par2 != 1 && par2 != 0){
					if(FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null){
						if(!this.mergeItemStack(itemstack1, 0, 1, false)){
							return null;
						}
					}else if(par2 >=3 && par2 < 30){
						if(!this.mergeItemStack(itemstack1, 30, 39, false)){
							return null;
						}
					}else if(par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)){
						return null;
					}
				}else if(!this.mergeItemStack(itemstack1, 3, 39, false)){
					return null;
				}
				if(itemstack1.stackSize == 0){
					slot.putStack((ItemStack)null);
				}else{
					slot.onSlotChanged();
				}
				if(itemstack1.stackSize == itemstack.stackSize){
					return null;
				}
				slot.onPickupFromSlot(player, itemstack1);
			}
			return itemstack;
		}
	}