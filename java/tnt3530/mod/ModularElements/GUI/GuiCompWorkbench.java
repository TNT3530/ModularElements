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
public class GuiCompWorkbench extends GuiContainer{
	private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(Constants.MODID + ":" + "textures/client/gui/guicompworkbench.png");
	private TileEntityCompWorkbench tileFurnace;
	
	//private GuiTextField text;
	//private boolean set = false;
	
	public GuiCompWorkbench(InventoryPlayer invPlayer, TileEntityCompWorkbench tile) {
		super(new ContainerCompWorkbench(invPlayer, tile));
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
		
		String string = this.tileFurnace.hasCustomInventoryName() ? this.tileFurnace.getInventoryName() : I18n.format(Constants.MODID + "_" + "blockCompWorkbenchGuiName", new Object[0]);
		
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) + 40, -20, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 35, this.ySize - 64, 4210752);
		
		this.fontRendererObj.drawString("Compound Fusion", this.xSize / 2 - 54, 2, 4210752);
		
		String state = "";
		if(this.tileFurnace.compoundInformation[4] == 1) state = "Solid";
		if(this.tileFurnace.compoundInformation[4] == 2) state = "Liquid";
		if(this.tileFurnace.compoundInformation[4] == 3) state = "Gas";
		int mass = this.tileFurnace.compoundInformation[1] + this.tileFurnace.compoundInformation[2];
		this.fontRendererObj.drawString(ElementStorageManager.getDisplayName(this.tileFurnace.compoundInformation[1], this.tileFurnace.compoundInformation[2]), this.xSize / 2 + 39, basic - change -1, 4210752);
		this.fontRendererObj.drawString("State: " + state, this.xSize / 2 + 39, basic, 4210752);
		this.fontRendererObj.drawString("Stability: " + this.tileFurnace.compoundInformation[5], this.xSize / 2 + 39, basic + 10, 4210752);
		this.fontRendererObj.drawString("Valence : " + this.tileFurnace.compoundInformation[6], this.xSize / 2 + 39, basic + 20, 4210752);
		this.fontRendererObj.drawString("Charge: " + this.tileFurnace.compoundInformation[7], this.xSize / 2 + 39, basic + (3 * change), 4210752);
		this.fontRendererObj.drawString("Weight: " + this.tileFurnace.compoundInformation[8], this.xSize / 2 + 39, basic + (4 * change), 4210752);
		this.fontRendererObj.drawString("Hardness: " + this.tileFurnace.compoundInformation[9], this.xSize / 2 + 39, basic + (5 * change), 4210752);
		this.fontRendererObj.drawString("Brittleness: " + this.tileFurnace.compoundInformation[10], this.xSize / 2 + 39, basic + (6 * change), 4210752);
		this.fontRendererObj.drawString("Conductivity: " + this.tileFurnace.compoundInformation[11], this.xSize / 2 + 39, basic + (7 * change), 4210752);
		this.fontRendererObj.drawString("Flamibility: " + this.tileFurnace.compoundInformation[12], this.xSize / 2 + 39, basic + (8 * change), 4210752);
		this.fontRendererObj.drawString("Compic Mass: " + mass, this.xSize / 2 + 39, basic + (10 * change), 4210752);
		
		this.fontRendererObj.drawString("" + this.tileFurnace.inputElementsForCompounding[0] + "," + this.tileFurnace.inputElementsForCompounding[1], this.xSize / 2 + 39, basic + 20, 4210752);
		this.fontRendererObj.drawString("" + this.tileFurnace.inputElementsForCompounding[2] + "," + this.tileFurnace.inputElementsForCompounding[3], this.xSize / 2 + 39, basic + 20, 4210752);
		this.fontRendererObj.drawString("" + this.tileFurnace.inputElementsForCompounding[4] + "," + this.tileFurnace.inputElementsForCompounding[5], this.xSize / 2 + 39, basic + 20, 4210752);
		this.fontRendererObj.drawString("" + this.tileFurnace.inputElementsForCompounding[6] + "," + this.tileFurnace.inputElementsForCompounding[7], this.xSize / 2 + 39, basic + 20, 4210752);
		this.fontRendererObj.drawString("" + this.tileFurnace.inputElementsForCompounding[8] + "," + this.tileFurnace.inputElementsForCompounding[9], this.xSize / 2 + 39, basic + 20, 4210752);
		
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