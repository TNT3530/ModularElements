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
public class GuiAtomDecomposer extends GuiContainer{
	private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(Constants.MODID + ":" + "textures/client/gui/guiatomdecomposer.png");
	private TileEntityAtomDecomposer tileFurnace;
	
	//private GuiTextField text;
	//private boolean set = false;
	
	public GuiAtomDecomposer(InventoryPlayer invPlayer, TileEntityAtomDecomposer tile) {
		super(new ContainerAtomDecomposer(invPlayer, tile));
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
		
		String string = this.tileFurnace.hasCustomInventoryName() ? this.tileFurnace.getInventoryName() : I18n.format(Constants.MODID + "_" + "blockAtomDecomposerGuiName", new Object[0]);
		
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) + 52, -17, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), this.xSize / 2 - 80, this.ySize /2 - 35, 4210752);
		
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
		

		double displayedEnergy = 0;
		double norm = this.tileFurnace.getEnergyStored();
		String label = "";
		if(norm >= 0 && norm < 1000)
		{
			label = "J";
			displayedEnergy = norm;
		}
		if(norm >= 1000 && norm < (1000*1000))
		{
			label = "KJ";
			displayedEnergy = norm/1000;
		}
		if(norm >= (1000*1000) && norm < (1000*1000*1000))
		{
			label = "MJ";
			displayedEnergy = norm/(1000*1000);
		}
		if(norm >= (1000*1000*1000) && norm < (1000*1000*1000*1000))
		{
			label = "GJ";
			displayedEnergy = norm/(1000*1000*1000);
		}
		if(norm >= (1000*1000*1000*1000) && norm < (1000*1000*1000*1000*1000))
		{
			label = "TJ";
			displayedEnergy = norm/(1000*1000*1000*1000);
		}
		this.fontRendererObj.drawString("Power- " + displayedEnergy + label, this.xSize / 2 - 43, -9, 4210752);
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