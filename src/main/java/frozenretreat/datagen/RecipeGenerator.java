package frozenretreat.datagen;

import java.util.function.Consumer;

import frozenretreat.FrozenRetreat;
import frozenretreat.registration.FRBlocks;
import frozenretreat.registration.FRTags;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.ItemLike;

public class RecipeGenerator extends RecipeProvider {
	private final BlockFamily frostwoodFamily = new BlockFamily.Builder(FRBlocks.FROSTWOOD_PLANKS.get())
			.slab(FRBlocks.FROSTWOOD_SLAB.get())
			.stairs(FRBlocks.FROSTWOOD_STAIRS.get())
			.fence(FRBlocks.FROSTWOOD_FENCE.get())
			.sign(FRBlocks.FROSTWOOD_SIGN.get(), FRBlocks.FROSTWOOD_WALL_SIGN.get())
			.button(FRBlocks.FROSTWOOD_BUTTON.get())
			.pressurePlate(FRBlocks.FROSTWOOD_PRESSURE_PLATE.get())
			.door(FRBlocks.FROSTWOOD_DOOR.get())
			.trapdoor(FRBlocks.FROSTWOOD_TRAPDOOR.get())
			.fenceGate(FRBlocks.FROSTWOOD_FENCE_GATE.get())
			.recipeUnlockedBy("has_planks")
			.getFamily();

	public RecipeGenerator(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		RecipeProvider.generateRecipes(consumer, frostwoodFamily);

		ShapelessRecipeBuilder.shapeless(FRBlocks.FROSTWOOD_PLANKS.get(), 4)
		.requires(FRTags.Items.FROSTWOOD_LOGS)
		.unlockedBy("has_logs", has(FRTags.Items.FROSTWOOD_LOGS));

		twoByTwo(consumer, FRBlocks.FROSTWOOD_LOG.get(), FRBlocks.FROSTWOOD.get(), 3);
		twoByTwo(consumer, FRBlocks.STRIPPED_FROSTWOOD_LOG.get(), FRBlocks.STRIPPED_FROSTWOOD.get(), 3);
	}

	private final void twoByTwo(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output, int amount) {
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
