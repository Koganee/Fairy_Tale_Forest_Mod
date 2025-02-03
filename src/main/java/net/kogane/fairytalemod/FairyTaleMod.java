package net.kogane.fairytalemod;

import com.mojang.logging.LogUtils;
import net.kogane.fairytalemod.block.ModBlocks;
import net.kogane.fairytalemod.enchantment.ModEnchantments;
import net.kogane.fairytalemod.entity.ModEntities;
import net.kogane.fairytalemod.entity.client.FancyPigRenderer;
import net.kogane.fairytalemod.entity.client.GemEssenceFairyRenderer;
import net.kogane.fairytalemod.fluid.ModFluidTypes;
import net.kogane.fairytalemod.fluid.ModFluids;
import net.kogane.fairytalemod.item.ModItems;
import net.kogane.fairytalemod.potion.BetterBrewingRecipe;
import net.kogane.fairytalemod.potion.ModPotions;
import net.kogane.fairytalemod.worldgen.ModFeatures;
import net.kogane.fairytalemod.worldgen.biome.ModTerraBlenderAPI;
import net.kogane.fairytalemod.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import terrablender.api.SurfaceRuleManager;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FairyTaleMod.MOD_ID)
public class FairyTaleMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "fairytalemod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public FairyTaleMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModFeatures.register(modEventBus);
        ModPotions.register(modEventBus);
        ModEnchantments.register(modEventBus);
        ModTerraBlenderAPI.registerRegions();
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());

        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD, ModItems.CHOCOLATE_ITEM.get(), ModPotions.CHOCOLATE_ELIXIR.get()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.CHOCOLATE_BLOCK);
            event.accept(ModBlocks.CANDYCANE_BLOCK);
            event.accept(ModBlocks.FAIRY_GEM_ORE_BLOCK);
        }
        if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS)
        {
            event.accept(ModItems.CANDYCANE);
            event.accept(ModItems.CHOCOLATE_ITEM);
        }
        if(event.getTabKey() == CreativeModeTabs.COMBAT)
        {
            event.accept(ModItems.CANDYCANE_BLADE);
            event.accept(ModItems.SWEET_BOOSTED_BLADE);
            event.accept(ModItems.CHOCOLATE_CLUB);
        }
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
        {
            event.accept(ModItems.FAIRY_GEM);
            event.accept(ModItems.GEM_ESSENCE_BUCKET);
            event.accept(ModItems.GEM_ESSENCE_EXTRACT_BOTTLE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.FANCY_PIG.get(), FancyPigRenderer::new);
            EntityRenderers.register(ModEntities.GEM_ESSENCE_FAIRY.get(), GemEssenceFairyRenderer::new);

            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_GEM_ESSENCE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_GEM_ESSENCE.get(), RenderType.translucent());
            });
        }
    }
}
