package com.tsaroblivious.obliviousarchitecture.common.te;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.core.init.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
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

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		super.save(nbt);
		if (!this.getSlot().isEmpty()) {
			nbt.put("Inventory", this.getSlot().save(new CompoundNBT()));
		}
		return nbt;
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		if (nbt.contains("Inventory", 10)) {
			this.setSlot(ItemStack.of(nbt.getCompound("Inventory")));
		}
	}

	public void outputSlotItem() {
		ObliviousArchitecture.LOGGER.debug(slot.getHoverName());
	}

	public void setSlot(ItemStack item) {
		slot = item;
	}

	public void clearSlot() {
		slot = ItemStack.EMPTY;
	}

	public ItemStack getSlot() {
		return slot;
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(getBlockPos(), 1, getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return serializeNBT();
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.deserializeNBT(pkt.getTag());
	}

}
