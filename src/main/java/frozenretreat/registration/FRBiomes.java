package frozenretreat.registration;

import java.util.function.Supplier;

import frozenretreat.FrozenRetreat;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FRBiomes {
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, FrozenRetreat.MODID);

	public static final RegistryObject<Biome> BIG_FROZEN_LAKES = registerBiome("unnamed_winter_biome", () -> unnamedWinterBiome(), Type.COLD, Type.SNOWY, Type.OVERWORLD);

	private static RegistryObject<Biome> registerBiome(String name, Supplier<Biome> biome, Type... types) {
		ResourceLocation biomeLocation = new ResourceLocation(FrozenRetreat.MODID, name);
		ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, biomeLocation);

		BiomeManager.addBiome(BiomeType.ICY, new BiomeEntry(biomeKey, 10));
		BiomeDictionary.addTypes(biomeKey, types);
		return BIOMES.register(name, biome);
	}

	private static Biome unnamedWinterBiome() {
		MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
		BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();

		BiomeDefaultFeatures.snowySpawns(spawnSettings);
		OverworldBiomes.globalOverworldGeneration(generationSettings);
		BiomeDefaultFeatures.addFrozenSprings(generationSettings);
		BiomeDefaultFeatures.addDefaultOres(generationSettings);
		BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);
		BiomeDefaultFeatures.addDefaultExtraVegetation(generationSettings);
		BiomeDefaultFeatures.addDefaultFlowers(generationSettings);
		BiomeDefaultFeatures.addDefaultGrass(generationSettings);
		return new Biome.BiomeBuilder()
				.precipitation(Precipitation.SNOW)
				.biomeCategory(BiomeCategory.ICY)
				.temperature(0.0F)
				.temperatureAdjustment(TemperatureModifier.FROZEN)
				.downfall(0.8F)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.waterColor(0x3938C9)
						.waterFogColor(0x50533)
						.fogColor(0xC0D8FF)
						.skyColor(OverworldBiomes.calculateSkyColor(0.0F))
						.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
						.build())
				.mobSpawnSettings(spawnSettings.build())
				.generationSettings(generationSettings.build())
				.build();
	}
}
