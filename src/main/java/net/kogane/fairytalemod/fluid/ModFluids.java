package net.kogane.fairytalemod.fluid;

import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.block.ModBlocks;
import net.kogane.fairytalemod.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, FairyTaleMod.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_GEM_ESSENCE = FLUIDS.register("gem_essence_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.GEM_ESSENCE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_GEM_ESSENCE = FLUIDS.register("flowing_gem_essence_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.GEM_ESSENCE_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties GEM_ESSENCE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.GEM_ESSENCE_FLUID_TYPE, SOURCE_GEM_ESSENCE, FLOWING_GEM_ESSENCE)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.GEM_ESSENCE_BLOCK)
            .bucket(ModItems.GEM_ESSENCE_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
