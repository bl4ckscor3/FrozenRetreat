package frozenretreat.datagen;

import frozenretreat.FrozenRetreat;
import frozenretreat.registration.FRBlocks;
import frozenretreat.registration.FRItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ItemModelGenerator extends ItemModelProvider {
	public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, FrozenRetreat.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		for(RegistryObject<Block> ro : FRBlocks.BLOCKS.getEntries()) {
			if(ro.get().asItem() instanceof BlockItem blockItem && blockItem != FRItems.WINTERBERRIES.get())
				simpleParent(blockItem);
		}

		flatItem(FRItems.WINTERBERRIES.get());
	}

	private ItemModelBuilder flatItem(Item item) {
		String name = item.getRegistryName().getPath();

		return getBuilder(name).parent(new UncheckedModelFile("item/generated")).texture("layer0", new ResourceLocation(FrozenRetreat.MODID, "item/" + name));
	}

	private void simpleParent(Item item) {
		String name = item.getRegistryName().getPath();

		getBuilder(name).parent(new UncheckedModelFile(modLoc(BLOCK_FOLDER + "/" + name)));
	}
}
