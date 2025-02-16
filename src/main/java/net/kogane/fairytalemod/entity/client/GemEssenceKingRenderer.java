package net.kogane.fairytalemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.entity.custom.FancyPigEntity;
import net.kogane.fairytalemod.entity.custom.GemEssenceFairyEntity;
import net.kogane.fairytalemod.entity.custom.GemEssenceTermiteEntity;
import net.kogane.fairytalemod.entity.layers.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GemEssenceKingRenderer extends MobRenderer<GemEssenceKingEntity, GemEssenceKingModel<GemEssenceKingEntity>> {
    private static final ResourceLocation FANCY_PIG_LOCATION = new ResourceLocation(FairyTaleMod.MOD_ID,"textures/entity/fancy_pig.png");

    public FancyPigRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FancyPigModel<>(pContext.bakeLayer(ModModelLayers.GEM_ESSENCE_KING_LAYER)), 0.9f);
    }

    @Override
    public ResourceLocation getTextureLocation(GemEssenceKingEntity pEntity) {
        return GEM_ESSENCE_KING_LOCATION;
    }

    @Override
    public void render(GemEssenceKingEntity pEntity, float pEntityYaw, float pPartialTicks,
                       PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.45f, 0.45f, 0.45f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
