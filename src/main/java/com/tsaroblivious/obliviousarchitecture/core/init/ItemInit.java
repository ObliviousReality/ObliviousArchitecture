package com.tsaroblivious.obliviousarchitecture.core.init;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.common.material.ArchitectureToolMaterial;
import com.tsaroblivious.obliviousarchitecture.core.itemgroup.ObliviousArchitectureItemGroup;

import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			ObliviousArchitecture.MOD_ID);

	public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new PickaxeItem(
			ArchitectureToolMaterial.ARCHITECT_TOOL, 0, -2f, new Item.Properties().tab(ObliviousArchitectureItemGroup.OBLIVIOUS_ARCHITECTURE)));

	public static final RegistryObject<Item> SAW = ITEMS.register("saw", () -> new AxeItem(
			ArchitectureToolMaterial.ARCHITECT_TOOL, 0, -2f, new Item.Properties().tab(ObliviousArchitectureItemGroup.OBLIVIOUS_ARCHITECTURE)));
}
