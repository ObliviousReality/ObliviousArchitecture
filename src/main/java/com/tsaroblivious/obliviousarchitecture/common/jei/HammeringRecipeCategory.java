package com.tsaroblivious.obliviousarchitecture.common.jei;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.common.recipe.HammeringRecipe;
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

public class HammeringRecipeCategory implements IRecipeCategory<HammeringRecipe> {

	public static final ResourceLocation ID = new ResourceLocation(ObliviousArchitecture.MOD_ID,
			".architect_anvil_recipe_category");

	private final IDrawable back;
	private final IDrawable icon;

	public HammeringRecipeCategory(IGuiHelper helper) {
		this.back = helper.drawableBuilder(
				new ResourceLocation(ObliviousArchitecture.MOD_ID, "textures/gui/hammering_recipe_back.png"), 0, 0, 152, 38)
				.addPadding(0, 0, 0, 0).build();
		this.icon = helper.createDrawableIngredient(new ItemStack(RegularBlockInit.ANVIL.get()));
	}

	@Override
	public ResourceLocation getUid() {
		return ID;
	}

	@Override
	public Class<? extends HammeringRecipe> getRecipeClass() {
		return HammeringRecipe.class;
	}

	@Override
	public String getTitle() {
		return new TranslationTextComponent("category." + ObliviousArchitecture.MOD_ID + ".architect_anvil_recipe").getString();
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
	public void setIngredients(HammeringRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());

	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, HammeringRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();

		itemStackGroup.init(0, true, 0, 0); // Input
		itemStackGroup.init(1, true, 27, 0); // Input 2
		itemStackGroup.init(2, true, 76, 0); // Hammer
		itemStackGroup.init(3, true, 13, 20); // Block
		itemStackGroup.init(4, false, 134, 0); // Output
		itemStackGroup.set(ingredients);
	}

}
