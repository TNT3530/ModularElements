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
		private int storedEnergy;
		private int[] infoArray;
		
		public ContainerAtomWorkbench(InventoryPlayer player, TileEntityAtomWorkbench tileEntityatomWorkbench){
			tileatomWorkbench = tileEntityatomWorkbench;
			
			this.addSlotToContainer(new Slot(tileEntityatomWorkbench, 0, 6, -16)); //Fuel In
			this.addSlotToContainer(new SlotFurnace(player.player, tileEntityatomWorkbench, 1, 6, 2)); //Fuel Out
			this.addSlotToContainer(new Slot(tileEntityatomWorkbench, 2, 39, 14)); //Processer
			this.addSlotToContainer(new SlotFurnace(player.player, tileEntityatomWorkbench, 3, 92, 14));
			
			this.addSlotToContainer(new Slot(tileEntityatomWorkbench, 4, 11, 47)); //P
			this.addSlotToContainer(new Slot(tileEntityatomWorkbench, 5, 11, 65)); //N
			this.addSlotToContainer(new Slot(tileEntityatomWorkbench, 6, 11, 65 + (65-47))); //E
			infoArray = tileEntityatomWorkbench.elementInformation;
			
			for(int i = 0; i < 3; ++i){
				for(int j = 0; j < 9; ++j){
					this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 36 + j * 18, 115 + i * 18));
				}
			}
			for(int i = 0; i < 9; ++i){
				this.addSlotToContainer(new Slot(player, i , 36 + i * 18 , 173));
			}
		}
		public void addCraftingToCrafters(ICrafting craft){
			super.addCraftingToCrafters(craft);
			//craft.sendProgressBarUpdate(this, 0, this.tileatomWorkbench.atomworkbenchCookTime);
			//craft.sendProgressBarUpdate(this, 1, this.tileatomWorkbench.atomworkbenchBurnTime);
			//craft.sendProgressBarUpdate(this, 2, this.tileatomWorkbench.currentBurnTime);
			craft.sendProgressBarUpdate(this, 3, this.tileatomWorkbench.storedEnergy);
			craft.sendProgressBarUpdate(this, 4, tileatomWorkbench.elementInformation[1]);
			craft.sendProgressBarUpdate(this, 5, tileatomWorkbench.elementInformation[2]);
			craft.sendProgressBarUpdate(this, 6, tileatomWorkbench.elementInformation[3]);
			craft.sendProgressBarUpdate(this, 7, tileatomWorkbench.elementInformation[4]);
			craft.sendProgressBarUpdate(this, 8, tileatomWorkbench.elementInformation[5]);
			craft.sendProgressBarUpdate(this, 9, tileatomWorkbench.elementInformation[6]);
			craft.sendProgressBarUpdate(this, 10, tileatomWorkbench.elementInformation[7]);
			craft.sendProgressBarUpdate(this, 11, tileatomWorkbench.elementInformation[8]);
			craft.sendProgressBarUpdate(this, 12, tileatomWorkbench.elementInformation[9]);
			craft.sendProgressBarUpdate(this, 13, tileatomWorkbench.elementInformation[10]);
			craft.sendProgressBarUpdate(this, 14, tileatomWorkbench.elementInformation[11]);
			craft.sendProgressBarUpdate(this, 15, tileatomWorkbench.elementInformation[12]);
			
			//for(int i = 2; i < 13; i++)
		//	{
		//		craft.sendProgressBarUpdate(this, 3 + i, this.tileatomWorkbench.elementInformation[i]);
		//	}
		}
		public void detectAndSendChanges(){
			super.detectAndSendChanges();
			for(int i = 0; i < this.crafters.size(); ++i){
				ICrafting craft = (ICrafting) this.crafters.get(i);
				if(this.storedEnergy != this.tileatomWorkbench.storedEnergy){
					craft.sendProgressBarUpdate(this, 3, this.tileatomWorkbench.storedEnergy);
				}
				if(infoArray[1] != tileatomWorkbench.elementInformation[1])
				{
					craft.sendProgressBarUpdate(this, 4, tileatomWorkbench.elementInformation[1]);
				}
				if(infoArray[2] != tileatomWorkbench.elementInformation[2])
				{
					craft.sendProgressBarUpdate(this, 5, tileatomWorkbench.elementInformation[2]);
				}
				if(infoArray[3] != tileatomWorkbench.elementInformation[3])
				{
					craft.sendProgressBarUpdate(this, 6, tileatomWorkbench.elementInformation[3]);
				}
				if(infoArray[4] != tileatomWorkbench.elementInformation[4])
				{
					craft.sendProgressBarUpdate(this, 7, tileatomWorkbench.elementInformation[4]);
				}
				if(infoArray[5] != tileatomWorkbench.elementInformation[5])
				{
					craft.sendProgressBarUpdate(this, 8, tileatomWorkbench.elementInformation[5]);
				}
				if(infoArray[6] != tileatomWorkbench.elementInformation[6])
				{
					craft.sendProgressBarUpdate(this, 9, tileatomWorkbench.elementInformation[6]);
				}
				if(infoArray[7] != tileatomWorkbench.elementInformation[7])
				{
					craft.sendProgressBarUpdate(this, 10, tileatomWorkbench.elementInformation[7]);
				}
				if(infoArray[8] != tileatomWorkbench.elementInformation[8])
				{
					craft.sendProgressBarUpdate(this, 11, tileatomWorkbench.elementInformation[8]);
				}
				if(infoArray[9] != tileatomWorkbench.elementInformation[9])
				{
					craft.sendProgressBarUpdate(this, 12, tileatomWorkbench.elementInformation[9]);
				}
				if(infoArray[10] != tileatomWorkbench.elementInformation[10])
				{
					craft.sendProgressBarUpdate(this, 13, tileatomWorkbench.elementInformation[10]);
				}
				if(infoArray[11] != tileatomWorkbench.elementInformation[11])
				{
					craft.sendProgressBarUpdate(this, 14, tileatomWorkbench.elementInformation[11]);
				}
				if(infoArray[12] != tileatomWorkbench.elementInformation[12])
				{
					craft.sendProgressBarUpdate(this, 15, tileatomWorkbench.elementInformation[12]);
				}
			//	for(int i1 = 0; i1 < 12; i1++)
			//	{
			//		if(this.infoArray[i1] != tileatomWorkbench.elementInformation[i1])
			//		{
			//			craft.sendProgressBarUpdate(this, 4 + i1, this.tileatomWorkbench.elementInformation[i1]);
			//		}
			//	}
			}
			this.storedEnergy = this.tileatomWorkbench.storedEnergy;
			//this.infoArray = this.tileatomWorkbench.elementInformation;
			infoArray[1] = tileatomWorkbench.elementInformation[1];
			infoArray[2] = tileatomWorkbench.elementInformation[2];
			infoArray[3] = tileatomWorkbench.elementInformation[3];
			infoArray[4] = tileatomWorkbench.elementInformation[4];
			infoArray[5] = tileatomWorkbench.elementInformation[5];
			infoArray[6] = tileatomWorkbench.elementInformation[6];
			infoArray[7] = tileatomWorkbench.elementInformation[7];
			infoArray[8] = tileatomWorkbench.elementInformation[8];
			infoArray[9] = tileatomWorkbench.elementInformation[9];
			infoArray[10] = tileatomWorkbench.elementInformation[10];
			infoArray[11] = tileatomWorkbench.elementInformation[11];
			infoArray[12] = tileatomWorkbench.elementInformation[12];
			
		}
		@SideOnly(Side.CLIENT)
		public void updateProgressBar(int par1, int par2){
			if(par1 == 3)
			{
				this.tileatomWorkbench.storedEnergy = par2;
			}
			if(par1 == 4)
			{
				this.tileatomWorkbench.elementInformation[1] = par2;
			}
			if(par1 == 5)
			{
				this.tileatomWorkbench.elementInformation[2] = par2;
			}
			if(par1 == 6)
			{
				this.tileatomWorkbench.elementInformation[3] = par2;
			}
			if(par1 == 7)
			{
				this.tileatomWorkbench.elementInformation[4] = par2;
			}
			if(par1 == 8)
			{
				this.tileatomWorkbench.elementInformation[5] = par2;
			}
			if(par1 == 9)
			{
				this.tileatomWorkbench.elementInformation[6] = par2;
			}
			if(par1 == 10)
			{
				this.tileatomWorkbench.elementInformation[7] = par2;
			}
			if(par1 == 11)
			{
				this.tileatomWorkbench.elementInformation[8] = par2;
			}
			if(par1 == 12)
			{
				this.tileatomWorkbench.elementInformation[9] = par2;
			}
			if(par1 == 13)
			{
				this.tileatomWorkbench.elementInformation[10] = par2;
			}
			if(par1 == 14)
			{
				this.tileatomWorkbench.elementInformation[11] = par2;
			}
			if(par1 == 15)
			{
				this.tileatomWorkbench.elementInformation[12] = par2;
			}
		//	for(int i = 5; i < 16; i++)
		//	{
		//		if(par1 == i)
		//		{
		//			this.tileatomWorkbench.elementInformation[i-3] = par2;
		//		}
		//	}
		}
		@Override
		public boolean canInteractWith(EntityPlayer player) {
			return this.tileatomWorkbench.isUseableByPlayer(player);
		}
	}