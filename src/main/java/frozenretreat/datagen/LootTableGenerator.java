package frozenretreat.datagen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import frozenretreat.FrozenRetreat;
import frozenretreat.block.WinterberryBushBlock;
import frozenretreat.registration.FRBlocks;
import frozenretreat.registration.FRItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class LootTableGenerator implements DataProvider {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private final DataGenerator generator;

	public LootTableGenerator(DataGenerator generator) {
		this.generator = generator;
	}

	private Map<ResourceLocation, LootTable.Builder> generateBlockLootTables() {
		Map<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();
		Block winterberryBush = FRBlocks.WINTERBERRY_BUSH.get();
		Block frostwoodDoor = FRBlocks.FROSTWOOD_DOOR.get();

		for (RegistryObject<Block> ro : FRBlocks.BLOCKS.getEntries()) {
			Block block = ro.get();

			if (block != winterberryBush)
				lootTables.put(block.getLootTable(), createStandardBlockLootTable(block));
		}

		lootTables.put(frostwoodDoor.getLootTable(), BlockLoot.createDoorTable(frostwoodDoor));
		lootTables.put(FRBlocks.FROSTWOOD_SIGN.get().getLootTable(), createStandardBlockLootTable(FRItems.FROSTWOOD_SIGN.get()));
		lootTables.put(FRBlocks.ICE_SPIKE.get().getLootTable(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.when(MatchTool.toolMatches(ItemPredicate.Builder.item()
								.hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))))
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(FRBlocks.ICE_SPIKE.get()))));
		lootTables.put(winterberryBush.getLootTable(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(winterberryBush)
								.setProperties(StatePropertiesPredicate.Builder.properties()
										.hasProperty(WinterberryBushBlock.AGE, 3)))
						.add(LootItem.lootTableItem(FRItems.WINTERBERRIES.get()))
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
						.apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))
				.withPool(LootPool.lootPool()
						.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(winterberryBush)
								.setProperties(StatePropertiesPredicate.Builder.properties()
										.hasProperty(WinterberryBushBlock.AGE, 2)))
						.add(LootItem.lootTableItem(FRItems.WINTERBERRIES.get()))
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
						.apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));

		return lootTables;
	}

	private final LootTable.Builder createStandardBlockLootTable(ItemLike drop) {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(drop))
						.when(ExplosionCondition.survivesExplosion()));
	}

	@Override
	public void run(HashCache cache) {
		Map<ResourceLocation, LootTable> tables = new HashMap<>();

		generateBlockLootTables().forEach((path, loot) -> tables.put(path, loot.setParamSet(LootContextParamSets.BLOCK).build()));
		tables.forEach((key, lootTable) -> {
			try {
				DataProvider.save(GSON, cache, LootTables.serialize(lootTable), generator.getOutputFolder().resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json"));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public String getName() {
		return "Loot Tables: " + FrozenRetreat.MODID;
	}
}
