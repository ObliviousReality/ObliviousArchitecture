package com.tsaroblivious.obliviousarchitecture.common.recipe;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;

import net.minecraft.item.crafting.IRecipeType;

public class SawingRecipeType implements IRecipeType<SawingRecipe> {
	@Override
	public String toString() {
		return ObliviousArchitecture.MOD_ID + ":sawing_recipe";
	}
}
