package frozenretreat.registration;

import frozenretreat.FrozenRetreat;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class FRPlacements {
	public static final PlacedFeature COMMON_WINTERBERRY_PATCH = register("common_winterberry_patch", FRConfiguredFeatures.WINTERBERRY_PATCH
			.placed(RarityFilter.onAverageOnceEvery(4),
					InSquarePlacement.spread(),
					PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
					BiomeFilter.biome()));
	public static final PlacedFeature SPARSE_WINTERBERRY_PATCH = register("sparse_winterberry_patch", FRConfiguredFeatures.WINTERBERRY_PATCH
			.placed(RarityFilter.onAverageOnceEvery(8),
					InSquarePlacement.spread(),
					PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
					BiomeFilter.biome()));

	private static PlacedFeature register(String name, PlacedFeature feature) {
		return PlacementUtils.register(FrozenRetreat.MODID + ":" + name, feature);
	}
}
