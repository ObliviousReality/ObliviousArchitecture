package com.tsaroblivious.obliviousarchitecture.common.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tsaroblivious.obliviousarchitecture.common.te.AnvilTileEntity;
import com.tsaroblivious.obliviousarchitecture.core.blocks.ArchitectAnvil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class AnvilTileEntityRenderer extends TileEntityRenderer<AnvilTileEntity> {

	public AnvilTileEntityRenderer(TileEntityRendererDispatcher rd) {
		super(rd);
	}

	@Override
	public void render(AnvilTileEntity te, float ticks, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			int overlay) {
		Direction direction = te.getBlockState().getValue(ArchitectAnvil.FACING);
		ItemStack item = te.getSlot1();
		ItemStack item2 = te.getSlot2();
		if (item != ItemStack.EMPTY) {
			matrix.pushPose();
			matrix.translate(0.5D, 1.1875D, 0.5D);
			switch (direction) {
			case NORTH:
				matrix.mulPose(Vector3f.YP.rotationDegrees(270));
				break;
			case EAST:
				matrix.mulPose(Vector3f.YP.rotationDegrees(180));
				break;
			case SOUTH:
				matrix.mulPose(Vector3f.YP.rotationDegrees(90));
				break;
			case WEST:
				break;
			default:
				break;
			}
			matrix.mulPose(Vector3f.XP.rotationDegrees(90.0F));
			Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemCameraTransforms.TransformType.FIXED,
					light, overlay, matrix, buffer);
			if (item2 != ItemStack.EMPTY) {
				matrix.translate(0.0D, 1.1875D, 0.0D);
				Minecraft.getInstance().getItemRenderer().renderStatic(item2, ItemCameraTransforms.TransformType.FIXED,
						light, overlay, matrix, buffer);
			}
			matrix.popPose();
		}

	}

}
