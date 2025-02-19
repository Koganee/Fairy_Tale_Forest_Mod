package net.kogane.fairytalemod.event;

import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.particle.GemEssenceParticles;
import net.kogane.fairytalemod.particle.ModParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FairyTaleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.GEM_ESSENCE_PARTICLES.get(), GemEssenceParticles.Provider::new);
        event.registerSpriteSet(ModParticles.GEM_ESSENCE_BUBBLE_PARTICLES.get(), GemEssenceParticles.Provider::new);
        event.registerSpriteSet(ModParticles.GEM_ESSENCE_DEFAULT_PARTICLES.get(), GemEssenceParticles.Provider::new);
    }
}
