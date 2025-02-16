package net.kogane.fairytalemod.entity.layers;

import io.netty.handler.codec.compression.Snappy;
import net.kogane.fairytalemod.FairyTaleMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation FANCY_PIG_LAYER = new ModelLayerLocation(
            new ResourceLocation(FairyTaleMod.MOD_ID, "fancy_pig_layer"), "fancy_pig_layer");

    public static final ModelLayerLocation GEM_ESSENCE_FAIRY_LAYER = new ModelLayerLocation(
            new ResourceLocation(FairyTaleMod.MOD_ID, "gem_essence_fairy_layer"), "gem_essence_fairy_layer");

    public static final ModelLayerLocation GEM_ESSENCE_TERMITE_LAYER = new ModelLayerLocation(
            new ResourceLocation(FairyTaleMod.MOD_ID, "gem_essence_termite_layer"), "gem_essence_termite_layer");

    public static final ModelLayerLocation GEM_ESSENCE_KING_LAYER = new ModelLayerLocation(
            new ResourceLocation(FairyTaleMod.MOD_ID, "gem_essence_king_layer"), "gem_essence_king_layer");
}