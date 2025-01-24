package net.kogane.fairytalemod.worldgen.biome;

import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.worldgen.biome.custom.ModOverworldRegion;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerraBlenderAPI {
    public static void registerRegions() {
        Regions.register(new ModOverworldRegion(new ResourceLocation(FairyTaleMod.MOD_ID, "overworld"), 5));
    }
}
