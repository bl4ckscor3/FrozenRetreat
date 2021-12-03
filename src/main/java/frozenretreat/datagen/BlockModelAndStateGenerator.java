package frozenretreat.datagen;

import frozenretreat.FrozenRetreat;
import frozenretreat.block.WinterberryBushBlock;
import frozenretreat.registration.FRBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelAndStateGenerator extends BlockStateProvider {
	public BlockModelAndStateGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, FrozenRetreat.MODID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		ResourceLocation frostwoodPlanksLocation = modLoc(ModelProvider.BLOCK_FOLDER + "/frostwood_planks");

		getVariantBuilder(FRBlocks.WINTERBERRY_BUSH.get()).forAllStates(state -> new ConfiguredModel[]{crossModel(FRBlocks.WINTERBERRY_BUSH.get(), state.getValue(WinterberryBushBlock.AGE))});
		cubeAll(FRBlocks.FROSTWOOD_PLANKS.get());
		logBlock(FRBlocks.FROSTWOOD_LOG.get());
		logBlock(FRBlocks.STRIPPED_FROSTWOOD_LOG.get());
		woodBlock(FRBlocks.FROSTWOOD.get(), FRBlocks.FROSTWOOD_LOG.get());
		woodBlock(FRBlocks.STRIPPED_FROSTWOOD.get(), FRBlocks.STRIPPED_FROSTWOOD_LOG.get());
		slabBlock(FRBlocks.FROSTWOOD_SLAB.get(), frostwoodPlanksLocation, frostwoodPlanksLocation);
		stairsBlock(FRBlocks.FROSTWOOD_STAIRS.get(), frostwoodPlanksLocation);
		fenceBlock(FRBlocks.FROSTWOOD_FENCE.get(), frostwoodPlanksLocation);
		models().fenceInventory("frostwood_frence_inventory", frostwoodPlanksLocation);
		buttonBlock(FRBlocks.FROSTWOOD_BUTTON.get(), frostwoodPlanksLocation);
		models().buttonInventory("frostwood_button_inventory", frostwoodPlanksLocation);
		pressurePlateBlock(FRBlocks.FROSTWOOD_PRESSURE_PLATE.get(), frostwoodPlanksLocation);
		doorBlock(FRBlocks.FROSTWOOD_DOOR.get(), modLoc(ModelProvider.BLOCK_FOLDER + "/frostwood_door_bottom"), modLoc(ModelProvider.BLOCK_FOLDER + "/frostwood_door_top"));
		trapdoorBlock(FRBlocks.FROSTWOOD_TRAPDOOR.get(), modLoc(ModelProvider.BLOCK_FOLDER + "/frostwood_trapdoor"), true);
		fenceGateBlock(FRBlocks.FROSTWOOD_FENCE_GATE.get(), frostwoodPlanksLocation);
	}

	private ConfiguredModel crossModel(Block block, int age) {
		String name = block.getRegistryName().getPath() + "_stage" + age;

		return new ConfiguredModel(models().cross(name, modLoc("block/" + name)));
	}

	private void woodBlock(RotatedPillarBlock block, RotatedPillarBlock log) {
		ResourceLocation texture = blockTexture(log);

		axisBlock(block, texture, texture);
	}
}
