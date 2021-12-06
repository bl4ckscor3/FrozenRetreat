package frozenretreat.registration;

import java.util.List;

import frozenretreat.FrozenRetreat;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;

public class FRConfiguredFeatures {
	public static final ConfiguredFeature<?,?> WINTERBERRY_PATCH = register("winterberry_patch", Feature.RANDOM_PATCH
			.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK
					.configured(new SimpleBlockConfiguration(new RandomizedIntStateProvider(
							BlockStateProvider.simple(FRBlocks.WINTERBERRY_BUSH.get()),
							SweetBerryBushBlock.AGE,
							UniformInt.of(1, 3)))),
					List.of(Blocks.GRASS_BLOCK, Blocks.SNOW_BLOCK))));

	private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> feature) {
		return FeatureUtils.register(FrozenRetreat.MODID + ":" + name, feature);
	}
}
