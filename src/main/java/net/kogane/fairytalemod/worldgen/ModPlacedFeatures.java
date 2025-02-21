package net.kogane.fairytalemod.worldgen;

import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.fml.IModStateTransition;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> CANDYCANE_TREE_PLACED_KEY = registerKey("candycane_tree_placed");
    public static final ResourceKey<PlacedFeature> CANDYCANE_BLOCK_PLACED_KEY = registerKey("candycane_block_placed");
    public static final ResourceKey<PlacedFeature> CHOCOLATE_BLOCK_PLACED_KEY = registerKey("chocolate_block_placed");
    public static final ResourceKey<PlacedFeature> FAIRY_GEM_ORE_PLACED_KEY = registerKey("fairy_gem_ore_placed");
    public static final ResourceKey<PlacedFeature> FAIRY_GEM_ESSENCE_PLACED_KEY = registerKey("fairy_gem_essence_placed");
    public static final ResourceKey<PlacedFeature> GEM_ESSENCE_LAKE_PLACED_KEY = registerKey("gem_essence_lake_placed");



    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, CANDYCANE_TREE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CANDYCANE_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1f, 2),
                        Blocks.OAK_SAPLING));
        register(context, CANDYCANE_BLOCK_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CANDYCANE_BLOCK_KEY),
                List.of(RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(50)),
                        BiomeFilter.biome()));
        register(context, CHOCOLATE_BLOCK_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CHOCOLATE_BLOCK_KEY),
                List.of(RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(50)),
                        BiomeFilter.biome()));

        register(context, FAIRY_GEM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_GEM_ESSENCE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, FAIRY_GEM_ESSENCE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_GEM_ESSENCE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(50)),
                        BiomeFilter.biome()));
        // In your bootstrap method in ModPlacedFeatures:
        register(context, GEM_ESSENCE_LAKE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_GEM_ESSENCE_LAKE_KEY),
                List.of(
                        // Adjust these modifiers as needed:
                        RarityFilter.onAverageOnceEvery(10),  // Controls frequency
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(80)),
                        BiomeFilter.biome()
                )
        );

    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(FairyTaleMod.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
