package net.kogane.fairytalemod.item;

import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.fluid.ModFluids;
import net.kogane.fairytalemod.item.custom.CandycaneBladeItem;
import net.kogane.fairytalemod.item.custom.ChocolateClubItem;
import net.kogane.fairytalemod.item.custom.SweetBoostedBladeItem;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
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

    //Food Items-----------------------------------------------------------------------------------------
    public static final RegistryObject<Item> CANDYCANE = ITEMS.register("candycane",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CANDYCANE)));
    public static final RegistryObject<Item> CHOCOLATE_ITEM = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CHOCOLATE_ITEM)));
    //Food Items-----------------------------------------------------------------------------------------

    //Weapon Items-----------------------------------------------------------------------------------------
    public static final RegistryObject<Item> CANDYCANE_BLADE = ITEMS.register("candycane_blade",
            () -> new CandycaneBladeItem(Tiers.WOOD, 2, 2, new Item.Properties().durability(64)));
    public static final RegistryObject<Item> SWEET_BOOSTED_BLADE = ITEMS.register("sweet_boosted_blade",
            () -> new SweetBoostedBladeItem(Tiers.DIAMOND, 3, 2, new Item.Properties().durability(1056)));
    public static final RegistryObject<Item> CHOCOLATE_CLUB = ITEMS.register("chocolate_club",
            () -> new ChocolateClubItem(Tiers.WOOD, 3, 1, new Item.Properties().durability(64)));
    //Weapon Items-----------------------------------------------------------------------------------------

    public static final RegistryObject<Item> FAIRY_GEM = ITEMS.register("fairy_gem",
            () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> GEM_ESSENCE_BUCKET = ITEMS.register("gem_essence_bucket",
            () -> new BucketItem(ModFluids.SOURCE_GEM_ESSENCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}

