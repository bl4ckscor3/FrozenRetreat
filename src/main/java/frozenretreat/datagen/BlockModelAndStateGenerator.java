package frozenretreat.datagen;

import frozenretreat.FrozenRetreat;
import frozenretreat.block.WinterberryBushBlock;
import frozenretreat.registration.FRBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelAndStateGenerator extends BlockStateProvider {
	public BlockModelAndStateGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, FrozenRetreat.MODID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		getVariantBuilder(FRBlocks.WINTERBERRY_BUSH.get()).forAllStates(state -> new ConfiguredModel[]{crossModel(FRBlocks.WINTERBERRY_BUSH.get(), state.getValue(WinterberryBushBlock.AGE))});
	}

	private ConfiguredModel crossModel(Block block, int age) {
		String name = block.getRegistryName().getPath() + "_stage" + age;

		return new ConfiguredModel(models().cross(name, modLoc("block/" + name)));
	}
}
