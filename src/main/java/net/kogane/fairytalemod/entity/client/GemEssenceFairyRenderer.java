package net.kogane.fairytalemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.entity.custom.FancyPigEntity;
import net.kogane.fairytalemod.entity.custom.GemEssenceFairyEntity;
import net.kogane.fairytalemod.entity.layers.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GemEssenceFairyRenderer extends MobRenderer<GemEssenceFairyEntity, GemEssenceFairyModel<GemEssenceFairyEntity>> {
    private static final ResourceLocation GEM_ESSENCE_FAIRY_LOCATION = new ResourceLocation(FairyTaleMod.MOD_ID,"textures/entity/gem_essence_fairy.png");

    public GemEssenceFairyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GemEssenceFairyModel<>(pContext.bakeLayer(ModModelLayers.GEM_ESSENCE_FAIRY_LAYER)), 2f);
    }

    @Override
    public ResourceLocation getTextureLocation(GemEssenceFairyEntity pEntity) { return GEM_ESSENCE_FAIRY_LOCATION; }

    @Override
    public void render(GemEssenceFairyEntity pEntity, float pEntityYaw, float pPartialTicks,
                       PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.45f, 0.45f, 0.45f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
