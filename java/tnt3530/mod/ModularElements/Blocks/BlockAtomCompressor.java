package tnt3530.mod.ModularElements.Blocks;
import java.util.Random;

import tnt3530.mod.ModularElements.Common.Constants;
import tnt3530.mod.ModularElements.Common.ModularElements;
import tnt3530.mod.ModularElements.TileEntity.TileEntityAtomCompressor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAtomCompressor extends BlockContainer {
	@SideOnly(Side.CLIENT)
	private IIcon top;
	@SideOnly(Side.CLIENT)
	private IIcon front;
	private final Random random = new Random();

	public BlockAtomCompressor() {
		super(Material.rock);
		this.setCreativeTab(ModularElements.tabElementalBlocks);
		this.setBlockName(Constants.MODID + "_" + "blockAtomCompressor");
		GameRegistry.registerBlock(this, "blockAtomCompressor");
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		this.blockIcon = iconregister.registerIcon(Constants.MODID + ":atomworkbench_side");
		this.front = iconregister.registerIcon(Constants.MODID + ":atomworkbench_front_inactive");
		this.top = iconregister.registerIcon(Constants.MODID + ":atomworkbench_top");
	}
	public IIcon getIcon(int side, int meta) {
		if (side == 1) {
			return top;
		} else {
			return this.blockIcon;
		}
	}
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		player.openGui(ModularElements.instance, 2, world, x, y, z);
		return true;
	}
	public Item getItemDropped(int par1, Random random, int par3) {
		return Item.getItemFromBlock(this);
	}
	public Item getItem(World world, int par2, int par3, int par4) {
		return Item.getItemFromBlock(this);
	}
	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */	
	public TileEntity createNewTileEntity(World world, int par2)
	{
		return new TileEntityAtomCompressor();
	}
	@SideOnly(Side.CLIENT)
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.direction(world, x, y, z);
	}
	private void direction(World world, int x, int y, int z) {
		if (!world.isRemote) {
			Block direction = world.getBlock(x, y, z - 1);
			Block direction1 = world.getBlock(x, y, z + 1);
			Block direction2 = world.getBlock(x - 1, y, z);
			Block direction3 = world.getBlock(x + 1, y, z);
			byte byte0 = 3;
			if (direction.func_149730_j() && direction.func_149730_j()) {
				byte0 = 3;
			}
			if (direction1.func_149730_j() && direction1.func_149730_j()) {
				byte0 = 2;
			}
			if (	direction2.func_149730_j() && direction2.func_149730_j()) {
				byte0 = 5;
			}
			if (direction3.func_149730_j() && direction3.func_149730_j()) {
				byte0 = 4;
			}
			world.setBlockMetadataWithNotify(x, y, z, byte0, 2);
		}
	}
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
		int direction = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if (direction == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if (direction == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if (direction == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if (direction == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if (itemstack.hasDisplayName()) {
			((TileEntityAtomCompressor) world.getTileEntity(x, y, z)).atomcompressorName(itemstack.getDisplayName());
		}
	}
	public static void updateBlockState(boolean burning, World world, int x, int y, int z) {
		int direction = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (burning) {
			world.setBlock(x, y, z, ModularElements.atomCompressor);
		} else {
			world.setBlock(x, y, z, ModularElements.atomCompressor);
		}
		world.setBlockMetadataWithNotify(x, y, z, direction, 2);
		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
	}
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntityAtomCompressor tileentitytutatomcompressor = (TileEntityAtomCompressor) world.getTileEntity(x, y, z);
		if (tileentitytutatomcompressor != null) {
			for (int i = 0; i < tileentitytutatomcompressor.getSizeInventory(); ++i) {
				ItemStack itemstack = tileentitytutatomcompressor.getStackInSlot(i);
				if (itemstack != null) {
					float f = this.random.nextFloat() * 0.6F + 0.1F;
					float f1 = this.random.nextFloat() * 0.6F + 0.1F;
					float f2 = this.random.nextFloat() * 0.6F + 0.1F;
					while (itemstack.stackSize > 0) {
						int j = this.random.nextInt(21) + 10;
						if (j > itemstack.stackSize) {
							j = itemstack.stackSize;
						}
						itemstack.stackSize -= j;
						EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound(((NBTTagCompound) itemstack.getTagCompound().copy()));
						}
						float f3 = 0.025F;
						entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + 0.1F);
						entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			world.func_147453_f(x, y, z, block);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
}