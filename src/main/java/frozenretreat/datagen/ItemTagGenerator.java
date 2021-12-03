package frozenretreat.datagen;

import frozenretreat.FrozenRetreat;
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
		tag(ItemTags.LOGS).addTag(FRTags.Items.FROSTWOOD_LOGS);
		tag(ItemTags.LOGS_THAT_BURN).addTag(FRTags.Items.FROSTWOOD_LOGS);
	}

	@Override
	public String getName() {
		return "Item Tags: " + FrozenRetreat.MODID;
	}
}
