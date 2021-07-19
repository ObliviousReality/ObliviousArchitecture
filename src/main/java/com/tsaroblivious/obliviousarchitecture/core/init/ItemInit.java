package com.tsaroblivious.obliviousarchitecture.core.init;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.core.itemgroup.ObliviousArchitectureItemGroup;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			ObliviousArchitecture.MOD_ID);

	public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new Item(
			new Item.Properties().rarity(Rarity.RARE).tab(ObliviousArchitectureItemGroup.OBLIVIOUS_ARCHITECTURE)));
}
