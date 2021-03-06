package tnt3530.mod.ModularElements.GUI;
import tnt3530.mod.ModularElements.TileEntity.TileEntityToolInfuser;
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

	public class ContainerToolInfuser extends Container {
		private TileEntityToolInfuser tiletoolInfuser;
		private int lastCookTime;
		private int lastBurnTime;
		private int lastItemBurnTime;
		private int storedEnergy;
		private int[] infoArray = new int[13];
		private int[] storedArray = new int[4];
		
		public ContainerToolInfuser(InventoryPlayer player, TileEntityToolInfuser tileEntitytoolInfuser){
			this.tiletoolInfuser = tileEntitytoolInfuser;
			this.addSlotToContainer(new Slot(tileEntitytoolInfuser, 0, 6, -16)); //Fuel In
			this.addSlotToContainer(new Slot(tileEntitytoolInfuser, 1, 6, 2)); //Atom In
			this.addSlotToContainer(new Slot(tileEntitytoolInfuser, 2, 46, 15)); //Tool In
			this.addSlotToContainer(new SlotFurnace(player.player, tileEntitytoolInfuser, 3, 108, 15));
		
			
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
			craft.sendProgressBarUpdate(this, 1, this.tiletoolInfuser.storedEnergy);
		}
		public void detectAndSendChanges(){
			super.detectAndSendChanges();
			for(int i = 0; i < this.crafters.size(); ++i){
				ICrafting craft = (ICrafting) this.crafters.get(i);
				if(this.storedEnergy != this.tiletoolInfuser.storedEnergy){
					craft.sendProgressBarUpdate(this, 1, this.tiletoolInfuser.storedEnergy);
				}
			}
			this.storedEnergy = this.tiletoolInfuser.storedEnergy;
		}
		@SideOnly(Side.CLIENT)
		public void updateProgressBar(int par1, int par2){
			if(par1 == 1)
			{
				this.tiletoolInfuser.storedEnergy = par2;
			}
		}
		@Override
		public boolean canInteractWith(EntityPlayer player) {
			return this.tiletoolInfuser.isUseableByPlayer(player);
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