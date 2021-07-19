package com.tsaroblivious.obliviousarchitecture.common.te;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.core.init.TileEntityInit;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class SawingBenchTileEntity extends TileEntity {

	private ItemStack slot = ItemStack.EMPTY;

	public SawingBenchTileEntity(TileEntityType<?> p_i48289_1_) {
		super(p_i48289_1_);
	}

	public SawingBenchTileEntity() {
		this(TileEntityInit.SAWINGBENCH_TILE_ENTITY.get());
	}

	public void outputSlotItem() {
		ObliviousArchitecture.LOGGER.debug(slot.getHoverName());
	}

	public void setSlot(ItemStack item) {
		slot = item;
	}

	public void clearItem() {
		slot = ItemStack.EMPTY;
	}
	
	public ItemStack getItem() {
		return slot;
	}

}
