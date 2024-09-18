package net.kogane.fairytalemod.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kogane.fairytalemod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class CurvedCandyCaneTrunkPlacer extends TrunkPlacer {
    public static final Codec<CurvedCandyCaneTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            trunkPlacerParts(instance).apply(instance, CurvedCandyCaneTrunkPlacer::new));

    public CurvedCandyCaneTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModFeatures.CURVED_CANDYCANE_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> replacer,
            RandomSource random, int trunkHeight, BlockPos pos, TreeConfiguration config) {

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        int curveStartHeight = (int) (trunkHeight * 0.7); // Start the curve at 70% of the tree height

        // Straight trunk part
        for (int y = 0; y < curveStartHeight; y++) {
            mutable.set(pos).move(0, y, 0);
            BlockState blockState = (y % 2 == 0)
                    ? ModBlocks.CANDYCANE_BLOCK.get().defaultBlockState()
                    : ModBlocks.CANDYCANE_BLOCK.get().defaultBlockState();
            replacer.accept(mutable, blockState);
        }

        // Curved "cane" top part
        int curveLength = trunkHeight - curveStartHeight;
        for (int y = 0; y < curveLength; y++) {
            int xOffset = (int) (y * 0.5); // Increase offset to make curve more pronounced
            mutable.set(pos).move(xOffset, curveStartHeight + y, 0); // Curve to the east
            BlockState blockState = (y % 2 == 0)
                    ? ModBlocks.CANDYCANE_BLOCK.get().defaultBlockState()
                    : ModBlocks.CANDYCANE_BLOCK.get().defaultBlockState();
            replacer.accept(mutable, blockState);
        }

        // No foliage attachment as you don't want leaves
        return new ArrayList<>();
    }
}
