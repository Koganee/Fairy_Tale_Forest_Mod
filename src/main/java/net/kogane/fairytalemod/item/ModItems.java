package net.kogane.fairytalemod.item;

import net.kogane.fairytalemod.FairyTaleMod;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FairyTaleMod.MOD_ID);
    public static final RegistryObject<Item> CANDYCANE = ITEMS.register("candycane",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CANDYCANE)));
    public static final RegistryObject<Item> CHOCOLATE_ITEM = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CHOCOLATE_ITEM)));


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
