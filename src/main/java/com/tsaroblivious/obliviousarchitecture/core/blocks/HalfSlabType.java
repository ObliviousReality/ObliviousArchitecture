package com.tsaroblivious.obliviousarchitecture.core.blocks;

import net.minecraft.util.IStringSerializable;

public enum HalfSlabType implements IStringSerializable {
   BOTTOM("bottom"),
   DOUBLEBOTTOM("doublebottom"),
   DOUBLETOP("doubletop"),
   TOP("top");

   private final String name;

   private HalfSlabType(String p_i49332_3_) {
      this.name = p_i49332_3_;
   }

   public String toString() {
      return this.name;
   }

   public String getSerializedName() {
      return this.name;
   }
}