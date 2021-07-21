package com.tsaroblivious.obliviousarchitecture.common.te;

import com.tsaroblivious.obliviousarchitecture.core.init.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class AnvilTileEntity extends TileEntity {

	private ItemStack slot1 = new ItemStack(Items.AIR, 1);
	private ItemStack slot2 = new ItemStack(Items.AIR, 1);

	public AnvilTileEntity(TileEntityType<?> p_i48289_1_) {
		super(p_i48289_1_);
	}

	public AnvilTileEntity() {
		this(TileEntityInit.ANVIL_TILE_ENTITY.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		super.save(nbt);
		if (!this.getSlot1().isEmpty()) {
			nbt.put("One", this.getSlot1().save(new CompoundNBT()));
		}
		if (!this.getSlot2().isEmpty()) {
			nbt.put("Two", this.getSlot1().save(new CompoundNBT()));
		}
		return nbt;
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		if (nbt.contains("One", 10)) {
			this.setSlot1(ItemStack.of(nbt.getCompound("One")));
		}
		if (nbt.contains("Two", 10)) {
			this.setSlot2(ItemStack.of(nbt.getCompound("Two")));
		}
	}

	public ItemStack getSlot1() {
		return slot1;
	}

	public boolean isSlot1Full() {
		return slot1 == ItemStack.EMPTY ? false : true;
	}

	public boolean isSlot2Full() {
		return slot2 == ItemStack.EMPTY ? false : true;
	}

	public ItemStack getSlot2() {
		return slot2;
	}

	public void setSlot1(ItemStack item) {
		this.slot1 = item;
	}

	public void setSlot2(ItemStack item) {
		this.slot2 = item;

	}

	public void clearSlot1() {
		slot1 = new ItemStack(Items.AIR, 1);
	}

	public void clearSlot2() {
		slot2 = new ItemStack(Items.AIR, 1);
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
