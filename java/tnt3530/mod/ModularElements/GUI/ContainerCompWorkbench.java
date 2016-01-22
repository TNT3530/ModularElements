package tnt3530.mod.ModularElements.GUI;
import tnt3530.mod.ModularElements.TileEntity.TileEntityCompWorkbench;
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

	public class ContainerCompWorkbench extends Container {
		private TileEntityCompWorkbench tilecompWorkbench;
		private int storedEnergy;
		private int[] infoArray;
		private int[] compoundingArray;
		
		public ContainerCompWorkbench(InventoryPlayer player, TileEntityCompWorkbench tileEntitycompWorkbench){
			tilecompWorkbench = tileEntitycompWorkbench;
			
			this.addSlotToContainer(new Slot(tileEntitycompWorkbench, 0, 6, -17)); //Fuel In
			this.addSlotToContainer(new Slot(tileEntitycompWorkbench, 1, 6, 1)); //Fuel Out
			
			this.addSlotToContainer(new Slot(tileEntitycompWorkbench, 2, 25, 52)); //Input 1
			this.addSlotToContainer(new Slot(tileEntitycompWorkbench, 3, 44, 64)); //Input 2
			this.addSlotToContainer(new Slot(tileEntitycompWorkbench, 4, 63, 71)); //Input 3
			this.addSlotToContainer(new Slot(tileEntitycompWorkbench, 5, 82, 64)); //Input 4
			this.addSlotToContainer(new Slot(tileEntitycompWorkbench, 6, 101, 52)); //Input 5
			
	
			this.addSlotToContainer(new SlotFurnace(player.player, tileEntitycompWorkbench, 7, 62, 13)); //Output
			infoArray = tileEntitycompWorkbench.compoundInformation;
			
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
			//Energy
			craft.sendProgressBarUpdate(this, 3, this.tilecompWorkbench.storedEnergy);
			//Compound Info
			craft.sendProgressBarUpdate(this, 4, tilecompWorkbench.compoundInformation[1]);
			craft.sendProgressBarUpdate(this, 5, tilecompWorkbench.compoundInformation[2]);
			craft.sendProgressBarUpdate(this, 6, tilecompWorkbench.compoundInformation[3]);
			craft.sendProgressBarUpdate(this, 7, tilecompWorkbench.compoundInformation[4]);
			craft.sendProgressBarUpdate(this, 8, tilecompWorkbench.compoundInformation[5]);
			craft.sendProgressBarUpdate(this, 9, tilecompWorkbench.compoundInformation[6]);
			craft.sendProgressBarUpdate(this, 10, tilecompWorkbench.compoundInformation[7]);
			craft.sendProgressBarUpdate(this, 11, tilecompWorkbench.compoundInformation[8]);
			craft.sendProgressBarUpdate(this, 12, tilecompWorkbench.compoundInformation[9]);
			craft.sendProgressBarUpdate(this, 13, tilecompWorkbench.compoundInformation[10]);
			craft.sendProgressBarUpdate(this, 14, tilecompWorkbench.compoundInformation[11]);
			craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.compoundInformation[12]);
			//Info for Elements to Compound
			/*
			craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.inputElementsForCompounding[0]);
			craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.inputElementsForCompounding[1]);
			craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.inputElementsForCompounding[2]);
			craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.inputElementsForCompounding[3]);
			craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.inputElementsForCompounding[4]);
			craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.inputElementsForCompounding[5]);
			craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.inputElementsForCompounding[6]);
			craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.inputElementsForCompounding[7]);
			craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.inputElementsForCompounding[8]);
			craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.inputElementsForCompounding[9]);
			*/
			
			//for(int i = 2; i < 13; i++)
		//	{
		//		craft.sendProgressBarUpdate(this, 3 + i, this.tilecompWorkbench.compoundInformation[i]);
		//	}
		}
		public void detectAndSendChanges(){
			super.detectAndSendChanges();
			for(int i = 0; i < this.crafters.size(); ++i){
				ICrafting craft = (ICrafting) this.crafters.get(i);
				//Energy
				if(this.storedEnergy != this.tilecompWorkbench.storedEnergy){
					craft.sendProgressBarUpdate(this, 3, this.tilecompWorkbench.storedEnergy);
				}
				//Compound Info
				if(infoArray[1] != tilecompWorkbench.compoundInformation[1])
				{
					craft.sendProgressBarUpdate(this, 4, tilecompWorkbench.compoundInformation[1]);
				}
				if(infoArray[2] != tilecompWorkbench.compoundInformation[2])
				{
					craft.sendProgressBarUpdate(this, 5, tilecompWorkbench.compoundInformation[2]);
				}
				if(infoArray[3] != tilecompWorkbench.compoundInformation[3])
				{
					craft.sendProgressBarUpdate(this, 6, tilecompWorkbench.compoundInformation[3]);
				}
				if(infoArray[4] != tilecompWorkbench.compoundInformation[4])
				{
					craft.sendProgressBarUpdate(this, 7, tilecompWorkbench.compoundInformation[4]);
				}
				if(infoArray[5] != tilecompWorkbench.compoundInformation[5])
				{
					craft.sendProgressBarUpdate(this, 8, tilecompWorkbench.compoundInformation[5]);
				}
				if(infoArray[6] != tilecompWorkbench.compoundInformation[6])
				{
					craft.sendProgressBarUpdate(this, 9, tilecompWorkbench.compoundInformation[6]);
				}
				if(infoArray[7] != tilecompWorkbench.compoundInformation[7])
				{
					craft.sendProgressBarUpdate(this, 10, tilecompWorkbench.compoundInformation[7]);
				}
				if(infoArray[8] != tilecompWorkbench.compoundInformation[8])
				{
					craft.sendProgressBarUpdate(this, 11, tilecompWorkbench.compoundInformation[8]);
				}
				if(infoArray[9] != tilecompWorkbench.compoundInformation[9])
				{
					craft.sendProgressBarUpdate(this, 12, tilecompWorkbench.compoundInformation[9]);
				}
				if(infoArray[10] != tilecompWorkbench.compoundInformation[10])
				{
					craft.sendProgressBarUpdate(this, 13, tilecompWorkbench.compoundInformation[10]);
				}
				if(infoArray[11] != tilecompWorkbench.compoundInformation[11])
				{
					craft.sendProgressBarUpdate(this, 14, tilecompWorkbench.compoundInformation[11]);
				}
				if(infoArray[12] != tilecompWorkbench.compoundInformation[12])
				{
					craft.sendProgressBarUpdate(this, 15, tilecompWorkbench.compoundInformation[12]);
				}
				/*
				//Input Elements for Compounding
				if(compoundingArray[0] != tilecompWorkbench.inputElementsForCompounding[0])
				{
					craft.sendProgressBarUpdate(this, 16, tilecompWorkbench.inputElementsForCompounding[0]);
				}
				if(compoundingArray[1] != tilecompWorkbench.inputElementsForCompounding[1])
				{
					craft.sendProgressBarUpdate(this, 17, tilecompWorkbench.inputElementsForCompounding[1]);
				}
				if(compoundingArray[2] != tilecompWorkbench.inputElementsForCompounding[2])
				{
					craft.sendProgressBarUpdate(this, 18, tilecompWorkbench.inputElementsForCompounding[2]);
				}
				if(compoundingArray[3] != tilecompWorkbench.inputElementsForCompounding[3])
				{
					craft.sendProgressBarUpdate(this, 19, tilecompWorkbench.inputElementsForCompounding[3]);
				}
				if(compoundingArray[4] != tilecompWorkbench.inputElementsForCompounding[4])
				{
					craft.sendProgressBarUpdate(this, 20, tilecompWorkbench.inputElementsForCompounding[4]);
				}
				if(compoundingArray[5] != tilecompWorkbench.inputElementsForCompounding[5])
				{
					craft.sendProgressBarUpdate(this, 21, tilecompWorkbench.inputElementsForCompounding[5]);
				}
				if(compoundingArray[6] != tilecompWorkbench.inputElementsForCompounding[6])
				{
					craft.sendProgressBarUpdate(this, 22, tilecompWorkbench.inputElementsForCompounding[6]);
				}
				if(compoundingArray[7] != tilecompWorkbench.inputElementsForCompounding[7])
				{
					craft.sendProgressBarUpdate(this, 23, tilecompWorkbench.inputElementsForCompounding[7]);
				}
				if(compoundingArray[8] != tilecompWorkbench.inputElementsForCompounding[8])
				{
					craft.sendProgressBarUpdate(this, 24, tilecompWorkbench.inputElementsForCompounding[8]);
				}
				if(compoundingArray[9] != tilecompWorkbench.inputElementsForCompounding[9])
				{
					craft.sendProgressBarUpdate(this, 25, tilecompWorkbench.inputElementsForCompounding[9]);
				}
				*/
			}
			//Energy
			this.storedEnergy = this.tilecompWorkbench.storedEnergy;
			//Compound Info
			infoArray[1] = tilecompWorkbench.compoundInformation[1];
			infoArray[2] = tilecompWorkbench.compoundInformation[2];
			infoArray[3] = tilecompWorkbench.compoundInformation[3];
			infoArray[4] = tilecompWorkbench.compoundInformation[4];
			infoArray[5] = tilecompWorkbench.compoundInformation[5];
			infoArray[6] = tilecompWorkbench.compoundInformation[6];
			infoArray[7] = tilecompWorkbench.compoundInformation[7];
			infoArray[8] = tilecompWorkbench.compoundInformation[8];
			infoArray[9] = tilecompWorkbench.compoundInformation[9];
			infoArray[10] = tilecompWorkbench.compoundInformation[10];
			infoArray[11] = tilecompWorkbench.compoundInformation[11];
			infoArray[12] = tilecompWorkbench.compoundInformation[12];
			//Compound inputs
			/*
			compoundingArray[0] = tilecompWorkbench.inputElementsForCompounding[0];
			compoundingArray[1] = tilecompWorkbench.inputElementsForCompounding[1];
			compoundingArray[2] = tilecompWorkbench.inputElementsForCompounding[2];
			compoundingArray[3] = tilecompWorkbench.inputElementsForCompounding[3];
			compoundingArray[4] = tilecompWorkbench.inputElementsForCompounding[4];
			compoundingArray[5] = tilecompWorkbench.inputElementsForCompounding[5];
			compoundingArray[6] = tilecompWorkbench.inputElementsForCompounding[6];
			compoundingArray[7] = tilecompWorkbench.inputElementsForCompounding[7];
			compoundingArray[8] = tilecompWorkbench.inputElementsForCompounding[8];
			compoundingArray[9] = tilecompWorkbench.inputElementsForCompounding[9];
			*/
		}
		@SideOnly(Side.CLIENT)
		public void updateProgressBar(int par1, int par2){
			if(par1 == 3)
			{
				this.tilecompWorkbench.storedEnergy = par2;
			}
			if(par1 == 4)
			{
				this.tilecompWorkbench.compoundInformation[1] = par2;
			}
			if(par1 == 5)
			{
				this.tilecompWorkbench.compoundInformation[2] = par2;
			}
			if(par1 == 6)
			{
				this.tilecompWorkbench.compoundInformation[3] = par2;
			}
			if(par1 == 7)
			{
				this.tilecompWorkbench.compoundInformation[4] = par2;
			}
			if(par1 == 8)
			{
				this.tilecompWorkbench.compoundInformation[5] = par2;
			}
			if(par1 == 9)
			{
				this.tilecompWorkbench.compoundInformation[6] = par2;
			}
			if(par1 == 10)
			{
				this.tilecompWorkbench.compoundInformation[7] = par2;
			}
			if(par1 == 11)
			{
				this.tilecompWorkbench.compoundInformation[8] = par2;
			}
			if(par1 == 12)
			{
				this.tilecompWorkbench.compoundInformation[9] = par2;
			}
			if(par1 == 13)
			{
				this.tilecompWorkbench.compoundInformation[10] = par2;
			}
			if(par1 == 14)
			{
				this.tilecompWorkbench.compoundInformation[11] = par2;
			}
			if(par1 == 15)
			{
				this.tilecompWorkbench.compoundInformation[12] = par2;
			}
			/*
			//Compound Inputs
			if(par1 == 16)
			{
				this.tilecompWorkbench.inputElementsForCompounding[0] = par2;
			}
			if(par1 == 17)
			{
				this.tilecompWorkbench.inputElementsForCompounding[1] = par2;
			}
			if(par1 == 18)
			{
				this.tilecompWorkbench.inputElementsForCompounding[2] = par2;
			}
			if(par1 == 19)
			{
				this.tilecompWorkbench.inputElementsForCompounding[3] = par2;
			}
			if(par1 == 20)
			{
				this.tilecompWorkbench.inputElementsForCompounding[4] = par2;
			}
			if(par1 == 21)
			{
				this.tilecompWorkbench.inputElementsForCompounding[5] = par2;
			}
			if(par1 == 22)
			{
				this.tilecompWorkbench.inputElementsForCompounding[6] = par2;
			}
			if(par1 == 23)
			{
				this.tilecompWorkbench.inputElementsForCompounding[7] = par2;
			}
			if(par1 == 24)
			{
				this.tilecompWorkbench.inputElementsForCompounding[8] = par2;
			}
			if(par1 == 25)
			{
				this.tilecompWorkbench.inputElementsForCompounding[9] = par2;
			}
*/
		}
		@Override
		public boolean canInteractWith(EntityPlayer player) {
			return this.tilecompWorkbench.isUseableByPlayer(player);
		}
	}