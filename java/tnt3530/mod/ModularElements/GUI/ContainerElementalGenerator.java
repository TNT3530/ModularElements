package tnt3530.mod.ModularElements.GUI;
import tnt3530.mod.ModularElements.TileEntity.*;
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

	public class ContainerElementalGenerator extends Container {
		private TileEntityElementalGenerator tileelementalGenerator;
		private double storedEnergy;
		
		public ContainerElementalGenerator(InventoryPlayer player, TileEntityElementalGenerator tileEntityelementalGenerator){
			this.tileelementalGenerator = tileEntityelementalGenerator;
			this.addSlotToContainer(new Slot(tileEntityelementalGenerator, 0, 51, 9)); //Fuel In
			
			int i;
			for(i = 0; i < 3; ++i){
				for(int j = 0; j < 9; ++j){
					this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 62 + i * 18));
				}
			}
			for(i = 0; i < 9; ++i){
				this.addSlotToContainer(new Slot(player, i , 8 + i * 18 , 120));
			}
		}
		public void addCraftingToCrafters(ICrafting craft){
			super.addCraftingToCrafters(craft);
			craft.sendProgressBarUpdate(this, 0, (int) this.tileelementalGenerator.getEnergyStored());
		}
		public void detectAndSendChanges(){
			super.detectAndSendChanges();
			for(int i = 0; i < this.crafters.size(); ++i){
				ICrafting craft = (ICrafting) this.crafters.get(i);
				if(this.storedEnergy != this.tileelementalGenerator.getEnergyStored()){
					craft.sendProgressBarUpdate(this, 0, (int) this.tileelementalGenerator.getEnergyStored());
				}
			}
			this.storedEnergy = this.tileelementalGenerator.getEnergyStored(null);
		}
		@Override
		public boolean canInteractWith(EntityPlayer player) {
			return this.tileelementalGenerator.isUseableByPlayer(player);
		}
		@SideOnly(Side.CLIENT)
		public void updateProgressBar(int par1, int par2){
			if(par1 == 0){
				//this.tileelementalGenerator.storage.setEnergyStored(par2);
			}
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