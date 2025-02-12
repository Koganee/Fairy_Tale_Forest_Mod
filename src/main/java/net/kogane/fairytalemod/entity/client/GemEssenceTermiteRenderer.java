package net.kogane.fairytalemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.entity.custom.GemEssenceFairyEntity;
import net.kogane.fairytalemod.entity.custom.GemEssenceTermiteEntity;
import net.kogane.fairytalemod.entity.layers.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GemEssenceTermiteRenderer extends MobRenderer<GemEssenceTermiteEntity, GemEssenceTermiteModel<GemEssenceTermiteEntity>> {
    private static final ResourceLocation GEM_ESSENCE_TERMITE_LOCATION = new ResourceLocation(FairyTaleMod.MOD_ID,"textures/entity/gem_essence_termite.png");

    public GemEssenceTermiteRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GemEssenceTermiteModel<>(pContext.bakeLayer(ModModelLayers.GEM_ESSENCE_TERMITE_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(GemEssenceTermiteEntity pEntity) { return GEM_ESSENCE_TERMITE_LOCATION; }

    @Override
    public void render(GemEssenceTermiteEntity pEntity, float pEntityYaw, float pPartialTicks,
                       PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.05f, 0.05f, 0.05f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
