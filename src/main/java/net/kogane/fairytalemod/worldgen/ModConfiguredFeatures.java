package net.kogane.fairytalemod.worldgen;

import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

import static com.ibm.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CANDYCANE_TREE_KEY = registerKey("candycane_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CANDYCANE_BLOCK_KEY = registerKey("candycane_block");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHOCOLATE_BLOCK_KEY = registerKey("chocolate_block");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_FAIRY_GEM_ORE_KEY = registerKey("fairy_gem_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_FAIRY_GEM_ESSENCE_KEY = registerKey("fairy_gem_essence");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_GEM_ESSENCE_LAKE_KEY = registerKey("gem_essence_lake");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceabeles = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceabeles = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceabeles = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceabeles = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldFairyGemOres = List.of(OreConfiguration.target(stoneReplaceabeles,
                        ModBlocks.FAIRY_GEM_ORE_BLOCK.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceabeles, ModBlocks.FAIRY_GEM_ORE_BLOCK.get().defaultBlockState()));

        register(context, CANDYCANE_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CANDYCANE_BLOCK.get()),
                new CurvedCandyCaneTrunkPlacer(5, 4, 3),
                BlockStateProvider.simple(ModBlocks.CANDYCANE_BLOCK.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),
                new TwoLayersFeatureSize(1, 0, 2)).build());

        register(context, CANDYCANE_BLOCK_KEY, Feature.GEODE,
                new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(Blocks.DEEPSLATE),
                        BlockStateProvider.simple(ModBlocks.CANDYCANE_BLOCK.get()),
                        BlockStateProvider.simple(Blocks.DIRT),
                        BlockStateProvider.simple(Blocks.EMERALD_BLOCK),
                        List.of(ModBlocks.CANDYCANE_BLOCK.get().defaultBlockState()),
                        BlockTags.FEATURES_CANNOT_REPLACE , BlockTags.GEODE_INVALID_BLOCKS),
                        new GeodeLayerSettings(1.7D, 1.2D, 2.5D, 3.5D),
                        new GeodeCrackSettings(0.25D, 1.5D, 1), 0.5D, 0.1D,
                        true, UniformInt.of(3, 8),
                        UniformInt.of(2, 6), UniformInt.of(1, 2),
                        -18, 18, 0.075D, 1));

        register(context, CHOCOLATE_BLOCK_KEY, Feature.GEODE,
                new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(Blocks.DEEPSLATE),
                        BlockStateProvider.simple(ModBlocks.CHOCOLATE_BLOCK.get()),
                        BlockStateProvider.simple(Blocks.DIRT),
                        BlockStateProvider.simple(Blocks.CHERRY_LEAVES),
                        List.of(ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState()),
                        BlockTags.FEATURES_CANNOT_REPLACE , BlockTags.GEODE_INVALID_BLOCKS),
                        new GeodeLayerSettings(1.7D, 1.2D, 2.5D, 3.5D),
                        new GeodeCrackSettings(0.25D, 1.5D, 1), 0.5D, 0.1D,
                        true, UniformInt.of(3, 8),
                        UniformInt.of(2, 6), UniformInt.of(1, 2),
                        -18, 18, 0.075D, 1));

        register(context, OVERWORLD_FAIRY_GEM_ESSENCE_KEY, Feature.GEODE,
                new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(Blocks.DEEPSLATE),
                        BlockStateProvider.simple(ModBlocks.GEM_ESSENCE_BLOCK.get()),
                        BlockStateProvider.simple(Blocks.DIRT),
                        BlockStateProvider.simple(Blocks.LAPIS_BLOCK),
                        List.of(ModBlocks.GEM_ESSENCE_BLOCK.get().defaultBlockState()),
                        BlockTags.FEATURES_CANNOT_REPLACE , BlockTags.GEODE_INVALID_BLOCKS),
                        new GeodeLayerSettings(1.7D, 1.2D, 2.5D, 3.5D),
                        new GeodeCrackSettings(0.25D, 1.5D, 1), 0.5D, 0.1D,
                        true, UniformInt.of(3, 8),
                        UniformInt.of(2, 6), UniformInt.of(1, 2),
                        -18, 18, 0.075D, 1));


        register(context, OVERWORLD_FAIRY_GEM_ORE_KEY, Feature.ORE, new OreConfiguration(overworldFairyGemOres, 6));

        register(context, OVERWORLD_GEM_ESSENCE_LAKE_KEY, Feature.LAKE,
            new LakeFeature.Configuration(
                    BlockStateProvider.simple(ModBlocks.GEM_ESSENCE_BLOCK.get().defaultBlockState()),
                    BlockStateProvider.simple(Blocks.CAVE_AIR)
            )
        );


    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(FairyTaleMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
