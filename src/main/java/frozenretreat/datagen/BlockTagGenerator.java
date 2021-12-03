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
		tag(BlockTags.BUTTONS).add(FRBlocks.FROSTWOOD_BUTTON.get());
		tag(BlockTags.DOORS).add(FRBlocks.FROSTWOOD_DOOR.get());
		tag(BlockTags.FENCES).add(FRBlocks.FROSTWOOD_FENCE.get());
		tag(BlockTags.LOGS).addTag(FRTags.Blocks.FROSTWOOD_LOGS);
		tag(BlockTags.LOGS_THAT_BURN).addTag(FRTags.Blocks.FROSTWOOD_LOGS);
		tag(BlockTags.MINEABLE_WITH_AXE).addTag(FRTags.Blocks.FROSTWOOD_LOGS).add(
				FRBlocks.FROSTWOOD_PLANKS.get(),
				FRBlocks.FROSTWOOD_SLAB.get(),
				FRBlocks.FROSTWOOD_STAIRS.get(),
				FRBlocks.FROSTWOOD_FENCE.get(),
				FRBlocks.FROSTWOOD_SIGN.get(),
				FRBlocks.FROSTWOOD_WALL_SIGN.get(),
				FRBlocks.FROSTWOOD_BUTTON.get(),
				FRBlocks.FROSTWOOD_PRESSURE_PLATE.get(),
				FRBlocks.FROSTWOOD_DOOR.get(),
				FRBlocks.FROSTWOOD_TRAPDOOR.get(),
				FRBlocks.FROSTWOOD_FENCE_GATE.get());
		tag(BlockTags.PLANKS).add(FRBlocks.FROSTWOOD_PLANKS.get());
		tag(BlockTags.PRESSURE_PLATES).add(FRBlocks.FROSTWOOD_PRESSURE_PLATE.get());
		tag(BlockTags.SIGNS).add(
				FRBlocks.FROSTWOOD_SIGN.get(),
				FRBlocks.FROSTWOOD_WALL_SIGN.get());
		tag(BlockTags.SLABS).add(FRBlocks.FROSTWOOD_SLAB.get());
		tag(BlockTags.STAIRS).add(FRBlocks.FROSTWOOD_STAIRS.get());
		tag(BlockTags.STANDING_SIGNS).add(FRBlocks.FROSTWOOD_SIGN.get());
		tag(BlockTags.TRAPDOORS).add(FRBlocks.FROSTWOOD_TRAPDOOR.get());
		tag(BlockTags.WALL_SIGNS).add(FRBlocks.FROSTWOOD_WALL_SIGN.get());
		tag(BlockTags.WOODEN_BUTTONS).add(FRBlocks.FROSTWOOD_BUTTON.get());
		tag(BlockTags.WOODEN_DOORS).add(FRBlocks.FROSTWOOD_DOOR.get());
		tag(BlockTags.WOODEN_FENCES).add(FRBlocks.FROSTWOOD_FENCE.get());
		tag(BlockTags.WOODEN_PRESSURE_PLATES).add(FRBlocks.FROSTWOOD_PRESSURE_PLATE.get());
		tag(BlockTags.WOODEN_SLABS).add(FRBlocks.FROSTWOOD_SLAB.get());
		tag(BlockTags.WOODEN_STAIRS).add(FRBlocks.FROSTWOOD_STAIRS.get());
		tag(BlockTags.TRAPDOORS).add(FRBlocks.FROSTWOOD_TRAPDOOR.get());
	}

	@Override
	public String getName() {
		return "Block Tags: " + FrozenRetreat.MODID;
	}
}
