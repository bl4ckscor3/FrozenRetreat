package frozenretreat;

import frozenretreat.registration.FRBiomes;
import frozenretreat.registration.FRBlocks;
import frozenretreat.registration.FRItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FrozenRetreat.MODID)
public class FrozenRetreat {
	public static final String MODID = "frozenretreat";
	public static final CreativeModeTab TAB = new CreativeModeTab(MODID) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(FRItems.WINTERBERRIES.get());
		}
	};

	public FrozenRetreat() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		FRBiomes.BIOMES.register(modEventBus);
		FRBlocks.BLOCKS.register(modEventBus);
		FRItems.ITEMS.register(modEventBus);
	}
}