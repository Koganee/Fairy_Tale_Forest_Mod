package net.kogane.fairytalemod.worldgen;

import net.kogane.fairytalemod.FairyTaleMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModFeatures {
    // Corrected: Use Registry.TRUNK_PLACER_TYPE (singular)
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, FairyTaleMod.MOD_ID);

    // Register the custom TrunkPlacerType for the CurvedCandyCaneTrunkPlacer
    public static final RegistryObject<TrunkPlacerType<CurvedCandyCaneTrunkPlacer>> CURVED_CANDYCANE_TRUNK_PLACER =
            TRUNK_PLACER_TYPES.register("curved_candycane_trunk_placer",
                    () -> new TrunkPlacerType<>(CurvedCandyCaneTrunkPlacer.CODEC));

    // Call this method to register features
    public static void register(IEventBus eventBus) {
        TRUNK_PLACER_TYPES.register(eventBus);
    }
}