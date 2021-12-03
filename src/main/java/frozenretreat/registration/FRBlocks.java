package frozenretreat.registration;

import frozenretreat.FrozenRetreat;
import frozenretreat.block.StrippableFrostwoodBlock;
import frozenretreat.block.WinterberryBushBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//TODO: properly set material colors after textures are done
public class FRBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FrozenRetreat.MODID);

	public static final RegistryObject<WinterberryBushBlock> WINTERBERRY_BUSH = BLOCKS.register("winterberry_bush", () -> new WinterberryBushBlock(Properties
			.of(Material.PLANT)
			.randomTicks()
			.noCollission()
			.sound(SoundType.SWEET_BERRY_BUSH)));
	public static final RegistryObject<RotatedPillarBlock> FROSTWOOD_LOG = BLOCKS.register("frostwood_log", () -> new StrippableFrostwoodBlock(Properties
			.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.COLOR_BLACK : MaterialColor.COLOR_BLACK)
			.strength(2.0F)
			.sound(SoundType.WOOD),
			getStrippedFrostwoodLog()));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_FROSTWOOD_LOG = BLOCKS.register("stripped_frostwood_log", () -> new RotatedPillarBlock(Properties
			.of(Material.WOOD, MaterialColor.COLOR_BLACK)
			.strength(2.0F)
			.sound(SoundType.WOOD)));
	public static final RegistryObject<RotatedPillarBlock> FROSTWOOD = BLOCKS.register("frostwood", () -> new StrippableFrostwoodBlock(Properties
			.of(Material.WOOD, MaterialColor.WOOD)
			.strength(2.0F)
			.sound(SoundType.WOOD),
			getStrippedFrostwood()));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_FROSTWOOD = BLOCKS.register("stripped_frostwood", () -> new RotatedPillarBlock(Properties
			.of(Material.WOOD, MaterialColor.WOOD)
			.strength(2.0F)
			.sound(SoundType.WOOD)));

	private static RegistryObject<RotatedPillarBlock> getStrippedFrostwoodLog() {
		return STRIPPED_FROSTWOOD_LOG;
	}

	private static RegistryObject<RotatedPillarBlock> getStrippedFrostwood() {
		return STRIPPED_FROSTWOOD;
	}
}
