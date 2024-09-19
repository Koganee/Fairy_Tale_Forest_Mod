package net.kogane.fairytalemod.enchantment;

import net.kogane.fairytalemod.FairyTaleMod;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, FairyTaleMod.MOD_ID);

    public static final RegistryObject<Enchantment> WOLFS_FANG =
            ENCHANTMENTS.register("wolfs_fang",
                    () -> new WolfsFangEnchantment(Enchantment.Rarity.COMMON, EnchantmentCategory.ARMOR_HEAD,
                            EquipmentSlot.HEAD));
    public static final RegistryObject<Enchantment> SWEET_TOOTH =
            ENCHANTMENTS.register("sweet_tooth",
                    () -> new SweetToothEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON,
                            EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
