package com.tsaroblivious.obliviousarchitecture.common.te;

import com.tsaroblivious.obliviousarchitecture.core.init.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;

public class AnvilTileEntity extends TileEntity implements IClearable {

	private final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);

	public AnvilTileEntity(TileEntityType<?> p_i48289_1_) {
		super(p_i48289_1_);
	}

	public AnvilTileEntity() {
		this(TileEntityInit.ANVIL_TILE_ENTITY.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		saveMetadataAndItems(nbt);
		return nbt;
	}

	private CompoundNBT saveMetadataAndItems(CompoundNBT nbt) {
		super.save(nbt);
		ItemStackHelper.saveAllItems(nbt, this.items, true);
		return nbt;
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.items.clear();
		ItemStackHelper.loadAllItems(nbt, items);
	}

	public NonNullList<ItemStack> getItems() {
		return items;
	}

	public boolean addItem(ItemStack item) {
		for (int i = 0; i < this.items.size(); i++) {
			ItemStack itemstack = this.items.get(i);
			if (itemstack.isEmpty() && !item.isEmpty()) {
				this.items.set(i, item.split(1));
				return true;
			}
		}
		return false;
	}

	public ItemStack popItem() {
		for (int i = this.items.size() - 1; i >= 0; i--) {
			ItemStack itemstack = this.items.get(i);
			if (!itemstack.isEmpty()) {
				this.items.set(i, ItemStack.EMPTY);
				return itemstack;
			}
		}
		return ItemStack.EMPTY;
	}

	public void clearContent() {
		this.items.clear();
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(getBlockPos(), 13, getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.saveMetadataAndItems(new CompoundNBT());
	}

}
