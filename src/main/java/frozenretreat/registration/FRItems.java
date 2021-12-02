package frozenretreat.registration;

import frozenretreat.FrozenRetreat;
import frozenretreat.item.WinterberriesItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FRItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FrozenRetreat.MODID);

	public static final RegistryObject<WinterberriesItem> WINTERBERRIES = ITEMS.register("winterberries", () -> new WinterberriesItem(FRBlocks.WINTERBERRY_BUSH.get(), new Item.Properties().tab(FrozenRetreat.TAB).food(FRFood.WINTERBERRIES)));
}
