package frozenretreat.datagen;

import frozenretreat.FrozenRetreat;
import frozenretreat.registration.FRBlocks;
import frozenretreat.registration.FRTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagGenerator extends BlockTagsProvider {
	public BlockTagGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, FrozenRetreat.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		tag(FRTags.Blocks.FROSTWOOD_LOGS).add(
				FRBlocks.FROSTWOOD_LOG.get(),
				FRBlocks.STRIPPED_FROSTWOOD_LOG.get(),
				FRBlocks.FROSTWOOD.get(),
				FRBlocks.STRIPPED_FROSTWOOD.get());
		tag(BlockTags.MINEABLE_WITH_AXE).addTag(FRTags.Blocks.FROSTWOOD_LOGS);
		tag(BlockTags.LOGS).addTag(FRTags.Blocks.FROSTWOOD_LOGS);
		tag(BlockTags.LOGS_THAT_BURN).addTag(FRTags.Blocks.FROSTWOOD_LOGS);
	}

	@Override
	public String getName() {
		return "Block Tags: " + FrozenRetreat.MODID;
	}
}
