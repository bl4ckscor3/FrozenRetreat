package frozenretreat.registration;

import frozenretreat.FrozenRetreat;
import frozenretreat.item.WinterberriesItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = FrozenRetreat.MODID, bus = Bus.MOD)
public class FRItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FrozenRetreat.MODID);

	public static final RegistryObject<WinterberriesItem> WINTERBERRIES = ITEMS.register("winterberries", () -> new WinterberriesItem(FRBlocks.WINTERBERRY_BUSH.get(), new Item.Properties().tab(FrozenRetreat.TAB).food(FRFood.WINTERBERRIES)));

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		for(RegistryObject<Block> ro : FRBlocks.BLOCKS.getEntries()) {
			Block block = ro.get();

			if(block != FRBlocks.WINTERBERRY_BUSH.get())
				event.getRegistry().register(new BlockItem(block, new Item.Properties().tab(FrozenRetreat.TAB)).setRegistryName(block.getRegistryName()));
		}
	}
}
