package net.kogane.fairytalemod.event;

import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.entity.ModEntities;
import net.kogane.fairytalemod.entity.client.FancyPigModel;
import net.kogane.fairytalemod.entity.custom.FancyPigEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.kogane.fairytalemod.entity.layers.ModModelLayers;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FairyTaleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.FANCY_PIG_LAYER, FancyPigModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FANCY_PIG.get(), FancyPigEntity.createAttributes().build());
    }

}
