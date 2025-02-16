package net.kogane.fairytalemod.particle;

import net.kogane.fairytalemod.FairyTaleMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FairyTaleMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> GEM_ESSENCE_PARTICLES =
            PARTICLE_TYPES.register("gem_essence_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GEM_ESSENCE_BUBBLE_PARTICLES =
            PARTICLE_TYPES.register("gem_essence_bubble_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
