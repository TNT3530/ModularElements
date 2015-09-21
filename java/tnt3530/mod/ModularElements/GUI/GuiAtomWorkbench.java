package tnt3530.mod.ModularElements.GUI;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.io.Charsets;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import tnt3530.mod.ModularElements.Common.*;
import tnt3530.mod.ModularElements.TileEntity.*;
import tnt3530.mod.ModularElements.Networking.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAtomWorkbench extends GuiContainer{
	private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(Constants.MODID + ":" + "textures/client/gui/guiatomworkbench.png");
	private TileEntityAtomWorkbench tileFurnace;
	
	//private GuiTextField text;
	//private boolean set = false;
	
	public GuiAtomWorkbench(InventoryPlayer invPlayer, TileEntityAtomWorkbench tile) {
		super(new ContainerAtomWorkbench(invPlayer, tile));
		this.tileFurnace = tile;
	}
	/*
	private void start()
	{
		if(!set)
		{
			text.setText("Name");
		}
	}
	*/
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places + 1, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		int basic = -1;
		int change = 10;
		int basicX = 39;
		
		String string = this.tileFurnace.hasCustomInventoryName() ? this.tileFurnace.getInventoryName() : I18n.format(Constants.MODID + "_" + "blockAtomWorkbenchGuiName", new Object[0]);
		
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) + 40, -20, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 35, this.ySize - 64, 4210752);
		
		this.fontRendererObj.drawString("Element Creation", this.xSize / 2 - 80, 35, 4210752);
		this.fontRendererObj.drawString(this.tileFurnace.elementInformation[1] + " Protons", this.xSize / 2 - 55, 51, 4210752);
		this.fontRendererObj.drawString(this.tileFurnace.elementInformation[2] + " Neutrons", this.xSize / 2 - 55, 69, 4210752);
		this.fontRendererObj.drawString(this.tileFurnace.elementInformation[3] + " Electrons", this.xSize / 2 - 55, 87, 4210752);
		
		//State (Solid[1], Liquid[2], Gas[3])
		//this.elementInformation[4] = 1;
		//Stability/Radioactivity (High[1] - Low[100])
		//this.elementInformation[5] = 80;
		//Valence E- (1-8)
		//this.elementInformation[6] = 1;
		//Charge for Ions
		//this.elementInformation[7] = 0;
		//Weight (Light[1] - Heavy[10])
		//this.elementInformation[8] = 3;
		//Hardness (Soft[1] - Hard[10]
		//this.elementInformation[9] = 1;
		//Brittleness (Mallable[1] - Shatter[10]
		//this.elementInformation[10] = 1;
		//Conductivity (Not[0] - Very[10])
		//this.elementInformation[11] = 1;
		//Flamibility (Water[0] - Explosive[100])
		//this.elementInformation[12] = 65;
		
		String state = "";
		if(this.tileFurnace.elementInformation[4] == 1) state = "Solid";
		if(this.tileFurnace.elementInformation[4] == 2) state = "Liquid";
		if(this.tileFurnace.elementInformation[4] == 3) state = "Gas";
		int mass = this.tileFurnace.elementInformation[1] + this.tileFurnace.elementInformation[2];
		this.fontRendererObj.drawString(ElementStorageManager.getDisplayName(this.tileFurnace.elementInformation[1], this.tileFurnace.elementInformation[2]), this.xSize / 2 + basicX, basic - change -1, 4210752);
		this.fontRendererObj.drawString("State: " + state, this.xSize / 2 + basicX, basic, 4210752);
		this.fontRendererObj.drawString("Stability: " + this.tileFurnace.elementInformation[5], this.xSize / 2 + basicX, basic + (1 * change), 4210752);
		this.fontRendererObj.drawString("Valence : " + this.tileFurnace.elementInformation[6], this.xSize / 2 + basicX, basic + (2 * change), 4210752);
		this.fontRendererObj.drawString("Charge: " + this.tileFurnace.elementInformation[7], this.xSize / 2 + basicX, basic + (3 * change), 4210752);
		this.fontRendererObj.drawString("Weight: " + this.tileFurnace.elementInformation[8], this.xSize / 2 + basicX, basic + (4 * change), 4210752);
		this.fontRendererObj.drawString("Hardness: " + this.tileFurnace.elementInformation[9], this.xSize / 2 + basicX, basic + (5 * change), 4210752);
		this.fontRendererObj.drawString("Brittleness: " + this.tileFurnace.elementInformation[10], this.xSize / 2 + basicX, basic + (6 * change), 4210752);
		this.fontRendererObj.drawString("Conductivity: " + this.tileFurnace.elementInformation[11], this.xSize / 2 + basicX, basic + (7 * change), 4210752);
		this.fontRendererObj.drawString("Flamibility: " + this.tileFurnace.elementInformation[12], this.xSize / 2 + basicX, basic + (8 * change), 4210752);
		this.fontRendererObj.drawString("Atomic Mass: " + mass, this.xSize / 2 + basicX, basic + (10 * change), 4210752);
		double displayedEnergy = 0;
		String label = "";
		int j = 1;
		if(this.tileFurnace.storedEnergy >= 0 && this.tileFurnace.storedEnergy < (j * 1000))
		{
			displayedEnergy = round(this.tileFurnace.storedEnergy, 2);
			label = "J";
		}
		if(this.tileFurnace.storedEnergy >= (j * 1000) && this.tileFurnace.storedEnergy < (j * 1000 * 1000))
		{
			displayedEnergy = round(this.tileFurnace.storedEnergy / (j * 1000), 2);
			label = "KJ";
		}
		if(this.tileFurnace.storedEnergy >= (j * 1000 * 1000) && this.tileFurnace.storedEnergy < (j * 1000 * 1000 * 1000))
		{
			displayedEnergy = round(this.tileFurnace.storedEnergy / (j * 1000 * 1000), 2);
			label = "MJ";
		}
		if(this.tileFurnace.storedEnergy >= (j * 1000 * 1000 * 1000) && this.tileFurnace.storedEnergy < (j * 1000 * 1000 * 1000 * 1000))
		{
			displayedEnergy = round(this.tileFurnace.storedEnergy / (j * 1000 * 1000 * 1000), 2);
			label = "GJ";
		}
		if(this.tileFurnace.storedEnergy >= (j * 1000 * 1000 * 1000 * 1000) && this.tileFurnace.storedEnergy < (j * 1000 * 1000 * 1000 * 1000 * 1000))
		{
			displayedEnergy = round(this.tileFurnace.storedEnergy / (j * 1000 * 1000 * 1000 * 1000), 2);
			label = "TJ";
		}
		this.fontRendererObj.drawString("Power- " + displayedEnergy + label, this.xSize / 2 - 63, -10, 4210752);
		//this.text.drawTextBox();
	}
	/*
	protected void keyTyped(char par1, int par2)
	{
        this.text.textboxKeyTyped(par1, par2);         
        if(!( par2== Keyboard.KEY_E  &&  this.text.isFocused())) super.keyTyped(par1, par2);
	}
	
	
	public void updateScreen()
	{
		super.updateScreen();
		this.text.updateCursorCounter();
		this.drawGuiContainerForegroundLayer(this.xSize, this.ySize);
	}
	
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
		this.text = new GuiTextField(this.fontRendererObj, this.xSize / 2 + 39 - 3 , this.ySize - 180, 100, 10);
		text.setMaxStringLength(10);
		this.text.setFocused(true);
		start();
    }
	
	protected void mouseClicked(int x, int y, int btn)
	{
		super.mouseClicked(x, y, btn);
		this.text.mouseClicked(x, y, btn);
	}
	*/
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize - 45) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize + 80, this.ySize + 52);
		int i1;
		
		//if (this.tileFurnace.isBurning())
		//{
			//i1 = this.tileFurnace.getBurnTimeRemainingScaled(12);
			//this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
		//}
		//i1 = this.tileFurnace.getCookProgressScaled(24);
		//this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
}