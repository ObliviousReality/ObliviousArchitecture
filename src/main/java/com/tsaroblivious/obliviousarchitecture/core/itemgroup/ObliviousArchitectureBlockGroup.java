package com.tsaroblivious.obliviousarchitecture.core.itemgroup;

import com.tsaroblivious.obliviousarchitecture.core.init.WoodBlockInit;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ObliviousArchitectureBlockGroup extends ItemGroup {
	public static final ObliviousArchitectureBlockGroup OBLIVIOUS_ARCHITECTURE_BLOCKS = new ObliviousArchitectureBlockGroup(
			ItemGroup.TABS.length, "obliviousarchitectureblocks");

	public ObliviousArchitectureBlockGroup(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(WoodBlockInit.OAK_VERTICAL_STAIRS.get());
	}
}
