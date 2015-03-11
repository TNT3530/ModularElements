package tnt3530.mod.ModularElements.GUI;
import tnt3530.mod.ModularElements.TileEntity.TileEntityAtomDecomposer;
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

	public class ContainerAtomDecomposer extends Container {
		private TileEntityAtomDecomposer tileatomDecomposer;
		private int storedEnergy;
		
		public ContainerAtomDecomposer(InventoryPlayer player, TileEntityAtomDecomposer tileEntityatomDecomposer){
			this.tileatomDecomposer = tileEntityatomDecomposer;
			this.addSlotToContainer(new Slot(tileEntityatomDecomposer, 0, 6, -16)); //Fuel In
			this.addSlotToContainer(new Slot(tileEntityatomDecomposer, 1, 35, 14)); //Element In
			this.addSlotToContainer(new SlotFurnace(player.player, tileEntityatomDecomposer, 2, 92, 14)); //Output
			this.addSlotToContainer(new SlotFurnace(player.player, tileEntityatomDecomposer, 3, 110, 14)); //Output
			this.addSlotToContainer(new SlotFurnace(player.player, tileEntityatomDecomposer, 4, 128, 14)); //Output
		
			
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
			craft.sendProgressBarUpdate(this, 0, this.tileatomDecomposer.storedEnergy);
		}
		public void detectAndSendChanges(){
			super.detectAndSendChanges();
			for(int i = 0; i < this.crafters.size(); ++i){
				ICrafting craft = (ICrafting) this.crafters.get(i);
				if(this.storedEnergy != this.tileatomDecomposer.storedEnergy){
					craft.sendProgressBarUpdate(this, 0, this.tileatomDecomposer.storedEnergy);
				}
			}
			this.storedEnergy = this.tileatomDecomposer.storedEnergy;
		}
		@Override
		public boolean canInteractWith(EntityPlayer player) {
			return this.tileatomDecomposer.isUseableByPlayer(player);
		}
		@SideOnly(Side.CLIENT)
		public void updateProgressBar(int par1, int par2){
			if(par1 == 0){
				this.tileatomDecomposer.storedEnergy = par2;
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