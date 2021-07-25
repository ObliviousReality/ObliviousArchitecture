package com.tsaroblivious.obliviousarchitecture.common.recipe;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;

import net.minecraft.item.crafting.IRecipeType;

public class HammeringRecipeType implements IRecipeType<HammeringRecipe> {
	@Override
	public String toString() {
		return ObliviousArchitecture.MOD_ID + ":hammering_recipe";
	}
}
