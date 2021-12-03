package frozenretreat.datagen;

import java.util.function.Consumer;

import frozenretreat.FrozenRetreat;
import frozenretreat.registration.FRBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.ItemLike;

public class RecipeGenerator extends RecipeProvider {
	public RecipeGenerator(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		twoByTwo(consumer, FRBlocks.FROSTWOOD_LOG.get(), FRBlocks.FROSTWOOD.get(), 3);
		twoByTwo(consumer, FRBlocks.STRIPPED_FROSTWOOD_LOG.get(), FRBlocks.STRIPPED_FROSTWOOD.get(), 3);
	}

	protected final void twoByTwo(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output, int amount) {
		ShapedRecipeBuilder.shaped(output, amount)
		.pattern("II")
		.pattern("II")
		.define('I', input)
		.unlockedBy("has_input", has(input))
		.save(consumer);
	}

	@Override
	public String getName() {
		return "Recipes: " + FrozenRetreat.MODID;
	}
}
