package frozenretreat;

import frozenretreat.registration.FRBlockEntityTypes;
import frozenretreat.registration.FRBlocks;
import frozenretreat.registration.FREntityTypes;
import frozenretreat.registration.FRItems;
import frozenretreat.registration.FRPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
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

		//FRBiomes.BIOMES.register(modEventBus);
		FRBlocks.BLOCKS.register(modEventBus);
		FRBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
		FREntityTypes.ENTITY_TYPES.register(modEventBus);
		FRItems.ITEMS.register(modEventBus);
		MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
	}

	public void onBiomeLoading(BiomeLoadingEvent event) {
		if (event.getClimate().precipitation == Precipitation.SNOW) {
			if (event.getName().equals(Biomes.SNOWY_PLAINS.getRegistryName()))
				event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, FRPlacements.COMMON_WINTERBERRY_PATCH);
			else
				event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, FRPlacements.SPARSE_WINTERBERRY_PATCH);
		}
	}
}