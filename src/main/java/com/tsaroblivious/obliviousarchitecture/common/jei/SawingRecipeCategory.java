package com.tsaroblivious.obliviousarchitecture.common.jei;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.common.recipe.SawingRecipe;
import com.tsaroblivious.obliviousarchitecture.core.init.RegularBlockInit;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class SawingRecipeCategory implements IRecipeCategory<SawingRecipe> {

	public static final ResourceLocation ID = new ResourceLocation(ObliviousArchitecture.MOD_ID,
			".sawing_recipe_category");

	private final IDrawable back;
	private final IDrawable icon;

	public SawingRecipeCategory(IGuiHelper helper) {
		this.back = helper.drawableBuilder(
				new ResourceLocation(ObliviousArchitecture.MOD_ID, "textures/gui/sawing_recipe_back.png"), 0, 0, 125,
				38).addPadding(0, 0, 0, 0).build();
		this.icon = helper.createDrawableIngredient(new ItemStack(RegularBlockInit.SAWING_BENCH.get()));
	}

	@Override
	public ResourceLocation getUid() {
		return ID;
	}

	@Override
	public Class<? extends SawingRecipe> getRecipeClass() {
		return SawingRecipe.class;
	}

	@Override
	public String getTitle() {
		return new TranslationTextComponent("category." + ObliviousArchitecture.MOD_ID + ".sawing_recipe").getString();
	}

	@Override
	public IDrawable getBackground() {
		return back;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setIngredients(SawingRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());

	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SawingRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();

		itemStackGroup.init(0, true, 0, 0); // Input
		itemStackGroup.init(1, true, 49, 0); // Saw
		itemStackGroup.init(2, true, 0, 20); // Block
		itemStackGroup.init(3, false, 107, 0); // Output
		itemStackGroup.set(ingredients);
	}

}
