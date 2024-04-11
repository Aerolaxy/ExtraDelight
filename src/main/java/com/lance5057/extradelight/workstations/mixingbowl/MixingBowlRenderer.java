package com.lance5057.extradelight.workstations.mixingbowl;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

public class MixingBowlRenderer implements BlockEntityRenderer<MixingBowlBlockEntity> {

	int timer = 0;

	public MixingBowlRenderer(BlockEntityRendererProvider.Context cxt) {

	}

	@Override
	public void render(MixingBowlBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
			MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		LazyOptional<IItemHandler> itemInteractionHandler = pBlockEntity
				.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);

		itemInteractionHandler.ifPresent(inv -> {

			renderCircle(pBlockEntity, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, itemRenderer, inv, 0,
					35, 0.20f, 0, 7);
			renderCircle(pBlockEntity, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, itemRenderer, inv,
					23.5f, 25, 0.15f, 8, 15);
			renderCircle(pBlockEntity, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, itemRenderer, inv,
					23.5f * 2, 15, 0.1f, 16, 23);
			renderStack(pBlockEntity, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, itemRenderer, inv, 24,
					31);
			renderSolid(pBlockEntity, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, itemRenderer, inv);
			renderFinish(pBlockEntity, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, itemRenderer, inv);
		});
		timer++;
	}

	private void renderSolid(MixingBowlBlockEntity pBlockEntity, PoseStack pPoseStack, MultiBufferSource pBufferSource,
			int pPackedLight, int pPackedOverlay, ItemRenderer itemRenderer, IItemHandler inv) {

		ItemStack item = inv.getStackInSlot(32);

		if (!item.isEmpty()) {
			BakedModel bakedmodel = itemRenderer.getModel(item, pBlockEntity.getLevel(), null, 0);
			pPoseStack.pushPose();

			pPoseStack.translate(0.5f, 0.4f, 0.5f);
			pPoseStack.mulPose(new Quaternion(0, timer % 360, 0, true));

			float uniscale = 1.0f;
			pPoseStack.scale(uniscale, uniscale, uniscale);
			itemRenderer.render(item, ItemTransforms.TransformType.GROUND, false, pPoseStack, pBufferSource,
					pPackedLight, pPackedOverlay, bakedmodel);
			pPoseStack.popPose();
		}
	}

	private void renderCircle(MixingBowlBlockEntity pBlockEntity, PoseStack pPoseStack, MultiBufferSource pBufferSource,
			int pPackedLight, int pPackedOverlay, ItemRenderer itemRenderer, @NotNull IItemHandler inv,
			float rotYoffset, float rotX, float transX, int start, int stop) {
		for (int i = start; i <= stop; i++) {
			ItemStack item = inv.getStackInSlot(i);
			int g = pBlockEntity.getStirs();

			if (!item.isEmpty()) {
				BakedModel bakedmodel = itemRenderer.getModel(item, pBlockEntity.getLevel(), null, 0);
				pPoseStack.pushPose();

				float rotY = ((i % 8) * 45) + ((g % 4)*90);

				pPoseStack.translate(0.5f, 0.25f, 0.5f);
				pPoseStack.mulPose(new Quaternion(0, rotYoffset + rotY, 0, true));

				pPoseStack.translate(transX, 0, 0);
				pPoseStack.mulPose(new Quaternion(0, 90, 0, true));
				pPoseStack.mulPose(new Quaternion(rotX, 0, 0, true));

				float uniscale = 0.65f;
				pPoseStack.scale(uniscale, uniscale, uniscale);
				itemRenderer.render(item, ItemTransforms.TransformType.GROUND, false, pPoseStack, pBufferSource,
						pPackedLight, pPackedOverlay, bakedmodel);
				pPoseStack.popPose();
			}
		}
	}

	private void renderStack(MixingBowlBlockEntity pBlockEntity, PoseStack pPoseStack, MultiBufferSource pBufferSource,
			int pPackedLight, int pPackedOverlay, ItemRenderer itemRenderer, @NotNull IItemHandler inv, int start,
			int stop) {
		for (int i = start; i <= stop; i++) {
			ItemStack item = inv.getStackInSlot(i);
			int g = pBlockEntity.getStirs();

			if (!item.isEmpty()) {
				BakedModel bakedmodel = itemRenderer.getModel(item, pBlockEntity.getLevel(), null, 0);
				pPoseStack.pushPose();

				float transY = (i % 8) * 0.025f;
				float rotY = (i % 8) * 45;

				pPoseStack.translate(0.5f, 0.25f + transY, 0.5f);
				pPoseStack.mulPose(new Quaternion(90, 0, 90, true));
				pPoseStack.mulPose(new Quaternion(0, 0, rotY, true));

				float uniscale = 0.65f;
				pPoseStack.scale(uniscale, uniscale, uniscale);
				itemRenderer.render(item, ItemTransforms.TransformType.GROUND, false, pPoseStack, pBufferSource,
						pPackedLight, pPackedOverlay, bakedmodel);
				pPoseStack.popPose();
			}
		}
	}

	private void renderFinish(MixingBowlBlockEntity pBlockEntity, PoseStack pPoseStack, MultiBufferSource pBufferSource,
			int pPackedLight, int pPackedOverlay, ItemRenderer itemRenderer, IItemHandler inv) {

		// ItemStack container = pBlockEntity.containerItem;
		ItemStack item = pBlockEntity.containerItem;

		if (!item.isEmpty()) {
			BakedModel bakedmodel = itemRenderer.getModel(item, pBlockEntity.getLevel(), null, 0);
			pPoseStack.pushPose();

			pPoseStack.translate(0.3f, 0.4f, 0.7f);
			pPoseStack.mulPose(new Quaternion(90, 0, 0, true));
			pPoseStack.mulPose(new Quaternion(0, 0, 45, true));

			float uniscale = 1.0f;
			pPoseStack.scale(uniscale, uniscale, uniscale);
			itemRenderer.render(item, ItemTransforms.TransformType.GROUND, false, pPoseStack, pBufferSource,
					pPackedLight, pPackedOverlay, bakedmodel);
			pPoseStack.popPose();
		}
	}

}
