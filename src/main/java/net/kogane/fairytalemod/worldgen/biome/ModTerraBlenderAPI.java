package net.kogane.fairytalemod.worldgen.biome;

import net.kogane.fairytalemod.FairyTaleMod;
import net.minecraft.resources.ResourceLocation;

public class ModTerraBlenderAPI {
    public static void registerRegions() {
        Regions.register(new ModOverworldRegion(new ResourceLocation(FairyTaleMod.MOD_ID, "overworld"), 5));
    }
}
