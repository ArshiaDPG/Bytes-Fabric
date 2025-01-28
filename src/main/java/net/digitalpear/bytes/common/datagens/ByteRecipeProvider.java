package net.digitalpear.bytes.common.datagens;



import net.digitalpear.bytes.init.ByteBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import java.util.concurrent.CompletableFuture;

public class ByteRecipeProvider extends FabricRecipeProvider {

    public ByteRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                RegistryEntryLookup<Item> lookup = registries.getOrThrow(RegistryKeys.ITEM);
                ShapedRecipeJsonBuilder.create(lookup, RecipeCategory.DECORATIONS, ByteBlocks.TURTLE_TOTE)
                        .input('b', Items.BARREL)
                        .input('h', Items.TURTLE_HELMET)
                        .pattern(" h ")
                        .pattern(" b ")
                        .pattern(" h ")
                        .criterion(hasItem(Items.TURTLE_SCUTE), conditionsFromPredicates(ItemPredicate.Builder.create().items(lookup, Items.TURTLE_SCUTE, Items.TURTLE_HELMET)))
                        .offerTo(exporter);
                offerNetheriteUpgradeRecipe(ByteBlocks.TURTLE_TOTE.asItem(), RecipeCategory.DECORATIONS, ByteBlocks.NETHERITE_TURTLE_TOTE.asItem());
            }
        };
    }

    @Override
    public String getName() {
        return "recipe";
    }
}
