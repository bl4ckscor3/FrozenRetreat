package frozenretreat.datagen;

import frozenretreat.FrozenRetreat;
import frozenretreat.registration.FRItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {
	public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, FrozenRetreat.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		flatItem(FRItems.WINTERBERRIES.get());
	}

	private ItemModelBuilder flatItem(Item item) {
		String name = item.getRegistryName().getPath();

		return getBuilder(name).parent(new UncheckedModelFile("item/generated")).texture("layer0", new ResourceLocation(FrozenRetreat.MODID, "item/" + name));
	}
}
