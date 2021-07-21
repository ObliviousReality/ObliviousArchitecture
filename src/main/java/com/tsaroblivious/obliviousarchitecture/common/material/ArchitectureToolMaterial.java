package com.tsaroblivious.obliviousarchitecture.common.material;

import com.google.common.base.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public enum ArchitectureToolMaterial implements IItemTier {

	// Efficiencies: Hand = 1, Wood = 2, Stone = 4, Iron = 6, Diamond = 8,
	// Netherite = 9, Gold = 12.

	// All swords are 1.5

	// EXAMPLE_TOOL(harvestLevel, maxUses, efficiency(F), attackDamage - 1,
	// enchantability, () -> Ingredient.of(ItemInit.EXAMPLE_ITEM.get())),

	ARCHITECT_TOOL(2, 200, 4f, 2, 10, () -> Ingredient.of(Items.IRON_INGOT));

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final Ingredient repairMaterial;

	ArchitectureToolMaterial(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability,
			Supplier<Ingredient> repairMaterial) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairMaterial = repairMaterial.get();
	}

	@Override
	public int getUses() {
		return this.maxUses;
	}

	@Override
	public float getSpeed() {
		return this.efficiency;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.attackDamage;
	}

	@Override
	public int getLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial;
	}

}
