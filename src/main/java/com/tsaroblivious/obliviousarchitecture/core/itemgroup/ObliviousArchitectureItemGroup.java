package com.tsaroblivious.obliviousarchitecture.core.itemgroup;

import com.tsaroblivious.obliviousarchitecture.core.init.ItemInit;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ObliviousArchitectureItemGroup extends ItemGroup {
	public static final ObliviousArchitectureItemGroup OBLIVIOUS_ARCHITECTURE = new ObliviousArchitectureItemGroup(
			ItemGroup.TABS.length, "obliviousarchitecture");

	public ObliviousArchitectureItemGroup(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemInit.HAMMER.get());
	}
}
