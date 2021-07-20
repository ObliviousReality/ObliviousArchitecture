package com.tsaroblivious.obliviousarchitecture.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.core.init.ItemInit;
import com.tsaroblivious.obliviousarchitecture.core.init.RecipeInit;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SawingRecipe implements IRecipe<IInventory> {

	public static final Serializer SERIALIZER = new Serializer();

	private final Ingredient input;
	private final ItemStack output;
	private final ResourceLocation id;

	public SawingRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
		this.id = id;
		this.input = input;
		this.output = output;
	}

	@Override
	public boolean matches(IInventory inv, World world) {
		return this.input.test(inv.getItem(0));
	}

	@Override
	public ItemStack assemble(IInventory inv) {
		return this.output.copy();
	}

	@Override
	public ItemStack getResultItem() {
		return this.output;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public IRecipeType<?> getType() {
		return RecipeInit.SAWING_RECIPE;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(ItemInit.HAMMER.get());
	}

	public boolean isValid(ItemStack input) {
		return this.input.test(input);
	}
	
	@Override
	public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
		return true;
	}

	private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
			implements IRecipeSerializer<SawingRecipe> {
		public Serializer() {
			this.setRegistryName(ObliviousArchitecture.MOD_ID, "sawing_recipe");
		}

		@Override
		public SawingRecipe fromJson(ResourceLocation recipeID, JsonObject json) {
			final JsonElement inputElement = JSONUtils.isArrayNode(json, "input")
					? JSONUtils.getAsJsonArray(json, "input")
					: JSONUtils.getAsJsonObject(json, "input");
			final Ingredient input = Ingredient.fromJson(inputElement);
			final ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "output"));
			return new SawingRecipe(recipeID, input, output);
		}

		@Override
		public SawingRecipe fromNetwork(ResourceLocation recipeID, PacketBuffer buffer) {
			final Ingredient input = Ingredient.fromNetwork(buffer);
			final ItemStack stack = buffer.readItem();
			return new SawingRecipe(recipeID, input, stack);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, SawingRecipe recipe) {
			recipe.input.toNetwork(buffer);
			buffer.writeItem(recipe.output);
		}

	}
}
