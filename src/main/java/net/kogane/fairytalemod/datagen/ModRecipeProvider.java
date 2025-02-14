package net.kogane.fairytalemod.datagen;

import net.kogane.fairytalemod.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput)
    {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CANDYCANE_BLADE.get())
                .pattern(" X ")
                .pattern(" X ")
                .pattern(" Y ")
                .define('X', ModItems.CANDYCANE.get())
                .define('Y', Items.STICK)
                .unlockedBy("has_candycane", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.CANDYCANE.get()).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CHOCOLATE_CLUB.get())
                .pattern("XXX")
                .pattern("XYX")
                .pattern(" Y ")
                .define('X', ModItems.CHOCOLATE_ITEM.get())
                .define('Y', Items.STICK)
                .unlockedBy("has_chocolate", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.CHOCOLATE_ITEM.get()).build()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GEM_ESSENCE_EXTRACT_BOTTLE.get())
                .pattern("XXX")
                .pattern("XYX")
                .pattern("XXX")
                .define('X', Items.GLASS_BOTTLE)
                .define('Y', ModItems.GEM_ESSENCE_BUCKET.get())
                .unlockedBy("has_gem_essence_bucket", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.GEM_ESSENCE_BUCKET.get()).build()))
                .save(pWriter);
    }
}

