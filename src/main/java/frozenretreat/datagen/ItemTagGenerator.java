package frozenretreat.datagen;

import frozenretreat.FrozenRetreat;
import frozenretreat.registration.FRBlocks;
import frozenretreat.registration.FRItems;
import frozenretreat.registration.FRTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagGenerator extends ItemTagsProvider {
	public ItemTagGenerator(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(generator, blockTagsProvider, FrozenRetreat.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		copy(FRTags.Blocks.FROSTWOOD_LOGS, FRTags.Items.FROSTWOOD_LOGS);
		tag(ItemTags.BUTTONS).add(FRBlocks.FROSTWOOD_BUTTON.get().asItem());
		tag(ItemTags.DOORS).add(FRBlocks.FROSTWOOD_DOOR.get().asItem());
		tag(ItemTags.FENCES).add(FRBlocks.FROSTWOOD_FENCE.get().asItem());
		tag(ItemTags.LOGS).addTag(FRTags.Items.FROSTWOOD_LOGS);
		tag(ItemTags.LOGS_THAT_BURN).addTag(FRTags.Items.FROSTWOOD_LOGS);
		tag(ItemTags.SIGNS).add(FRItems.FROSTWOOD_SIGN.get());
		tag(ItemTags.SLABS).add(FRBlocks.FROSTWOOD_SLAB.get().asItem());
		tag(ItemTags.STAIRS).add(FRBlocks.FROSTWOOD_STAIRS.get().asItem());
		tag(ItemTags.TRAPDOORS).add(FRBlocks.FROSTWOOD_TRAPDOOR.get().asItem());
		tag(ItemTags.WOODEN_BUTTONS).add(FRBlocks.FROSTWOOD_BUTTON.get().asItem());
		tag(ItemTags.WOODEN_DOORS).add(FRBlocks.FROSTWOOD_DOOR.get().asItem());
		tag(ItemTags.WOODEN_FENCES).add(FRBlocks.FROSTWOOD_FENCE.get().asItem());
		tag(ItemTags.WOODEN_PRESSURE_PLATES).add(FRBlocks.FROSTWOOD_PRESSURE_PLATE.get().asItem());
		tag(ItemTags.WOODEN_SLABS).add(FRBlocks.FROSTWOOD_SLAB.get().asItem());
		tag(ItemTags.WOODEN_STAIRS).add(FRBlocks.FROSTWOOD_STAIRS.get().asItem());
		tag(ItemTags.WOODEN_TRAPDOORS).add(FRBlocks.FROSTWOOD_TRAPDOOR.get().asItem());
	}

	@Override
	public String getName() {
		return "Item Tags: " + FrozenRetreat.MODID;
	}
}
