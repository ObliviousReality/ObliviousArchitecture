package com.tsaroblivious.obliviousarchitecture.common.jei;

import java.util.Collection;
import java.util.stream.Collectors;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.core.init.RecipeInit;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

	private static final ResourceLocation PLUGIN_ID = new ResourceLocation(ObliviousArchitecture.MOD_ID, "jei_plugin");

	@Override
	public ResourceLocation getPluginUid() {
		return PLUGIN_ID;
	}

	@SuppressWarnings("resource")
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		net.minecraft.item.crafting.RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
		registration.addRecipes(getRecipes(manager, RecipeInit.SAWING_RECIPE), SawingRecipeCategory.ID);
		registration.addRecipes(getRecipes(manager, RecipeInit.HAMMERING_RECIPE), HammeringRecipeCategory.ID);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(new SawingRecipeCategory(helper));
		registration.addRecipeCategories(new HammeringRecipeCategory(helper));
	}

	private static Collection<?> getRecipes(net.minecraft.item.crafting.RecipeManager manager, IRecipeType<?> type) {
		return manager.getRecipes().parallelStream().filter(recipe -> recipe.getType() == type)
				.collect(Collectors.toList());
	}

}
