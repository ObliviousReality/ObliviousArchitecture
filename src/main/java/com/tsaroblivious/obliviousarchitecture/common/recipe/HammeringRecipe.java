package com.tsaroblivious.obliviousarchitecture.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.core.init.ItemInit;
import com.tsaroblivious.obliviousarchitecture.core.init.RecipeInit;
import com.tsaroblivious.obliviousarchitecture.core.init.RegularBlockInit;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class HammeringRecipe implements IRecipe<IInventory> {

	public static final Serializer SERIALIZER = new Serializer();

	private final Ingredient input;
	private final Ingredient input2;
	private final ItemStack output;
	private final ResourceLocation id;

	public HammeringRecipe(ResourceLocation id, Ingredient input, Ingredient input2, ItemStack output) {
		this.id = id;
		this.input = input;
		this.input2 = input2;
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
		return RecipeInit.HAMMERING_RECIPE;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(ItemInit.HAMMER.get());
	}

	public boolean isValid(ItemStack input, ItemStack input2) {
		return this.input.test(input) && this.input2.test(input2);
	}

	@Override
	public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
		return true;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> i = NonNullList.create();
		i.add(input);
		i.add(input2);
		i.add(Ingredient.of(new ItemStack(ItemInit.HAMMER.get())));
		i.add(Ingredient.of(new ItemStack(RegularBlockInit.ANVIL.get())));
		return i;
	}

	private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
			implements IRecipeSerializer<HammeringRecipe> {
		public Serializer() {
			this.setRegistryName(ObliviousArchitecture.MOD_ID, "hammering_recipe");
		}

		@Override
		public HammeringRecipe fromJson(ResourceLocation recipeID, JsonObject json) {
			final JsonElement inputElement = JSONUtils.isArrayNode(json, "input")
					? JSONUtils.getAsJsonArray(json, "input")
					: JSONUtils.getAsJsonObject(json, "input");
			final JsonElement inputElement2 = JSONUtils.isArrayNode(json, "input2")
					? JSONUtils.getAsJsonArray(json, "input2")
					: JSONUtils.getAsJsonObject(json, "input2");
			final Ingredient input = Ingredient.fromJson(inputElement);
			final Ingredient input2 = Ingredient.fromJson(inputElement2);
			final ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "output"));
			return new HammeringRecipe(recipeID, input, input2, output);
		}

		@Override
		public HammeringRecipe fromNetwork(ResourceLocation recipeID, PacketBuffer buffer) {
			final Ingredient input = Ingredient.fromNetwork(buffer);
			final Ingredient input2 = Ingredient.fromNetwork(buffer);
			final ItemStack stack = buffer.readItem();
			return new HammeringRecipe(recipeID, input, input2, stack);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, HammeringRecipe recipe) {
			recipe.input.toNetwork(buffer);
			recipe.input2.toNetwork(buffer);
			buffer.writeItem(recipe.output);
		}

	}

}
