package frozenretreat.registration;

import java.util.function.Function;

import frozenretreat.FrozenRetreat;
import frozenretreat.block.StandingFrostwoodSignBlock;
import frozenretreat.block.StrippableFrostwoodBlock;
import frozenretreat.block.WallFrostwoodSignBlock;
import frozenretreat.block.WinterberryBushBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//TODO: properly set material colors after textures are done
public class FRBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FrozenRetreat.MODID);
	private static final MaterialColor FROSTWOOD_PLANKS_MATERIAL_COLOR = MaterialColor.COLOR_BLACK;

	public static final RegistryObject<WinterberryBushBlock> WINTERBERRY_BUSH = BLOCKS.register("winterberry_bush", () -> new WinterberryBushBlock(
			Properties.of(Material.PLANT)
			.randomTicks()
			.noCollission()
			.sound(SoundType.SWEET_BERRY_BUSH)));
	public static final RegistryObject<Block> FROSTWOOD_PLANKS = BLOCKS.register("frostwood_planks", () -> new Block(frostwoodTypeProperties()));
	public static final RegistryObject<RotatedPillarBlock> FROSTWOOD_LOG = BLOCKS.register("frostwood_log", () -> new StrippableFrostwoodBlock(
			frostwoodTypeProperties(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.COLOR_BLACK : MaterialColor.COLOR_BLACK, 2.0F, 2.0F),
			getStrippedFrostwoodLog()));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_FROSTWOOD_LOG = BLOCKS.register("stripped_frostwood_log", () -> new RotatedPillarBlock(frostwoodTypeProperties(MaterialColor.COLOR_BLACK, 2.0F, 2.0F)));
	public static final RegistryObject<RotatedPillarBlock> FROSTWOOD = BLOCKS.register("frostwood", () -> new StrippableFrostwoodBlock(
			frostwoodTypeProperties(MaterialColor.WOOD, 2.0F, 2.0F),
			getStrippedFrostwood()));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_FROSTWOOD = BLOCKS.register("stripped_frostwood", () -> new RotatedPillarBlock(frostwoodTypeProperties(MaterialColor.WOOD, 2.0F, 2.0F)));
	public static final RegistryObject<SlabBlock> FROSTWOOD_SLAB = BLOCKS.register("frostwood_slab", () -> new SlabBlock(frostwoodTypeProperties()));
	public static final RegistryObject<StairBlock> FROSTWOOD_STAIRS = BLOCKS.register("frostwood_stairs", () -> new StairBlock(() -> FROSTWOOD_PLANKS.get().defaultBlockState(), frostwoodTypeProperties()));
	public static final RegistryObject<FenceBlock> FROSTWOOD_FENCE = BLOCKS.register("frostwood_fence", () -> new FenceBlock(frostwoodTypeProperties()));
	public static final RegistryObject<StandingFrostwoodSignBlock> FROSTWOOD_SIGN = BLOCKS.register("frostwood_sign", () -> new StandingFrostwoodSignBlock(
			frostwoodTypeProperties(1.0F)
			.noCollission(), FRWoodTypes.FROSTWOOD));
	public static final RegistryObject<WallFrostwoodSignBlock> FROSTWOOD_WALL_SIGN = BLOCKS.register("frostwood_wall_sign", () -> new WallFrostwoodSignBlock(
			frostwoodTypeProperties(1.0F)
			.noCollission()
			.lootFrom(FROSTWOOD_SIGN), FRWoodTypes.FROSTWOOD));
	public static final RegistryObject<WoodButtonBlock> FROSTWOOD_BUTTON = BLOCKS.register("frostwood_button", () -> new WoodButtonBlock(
			Properties.of(Material.DECORATION)
			.noCollission()
			.strength(0.5F)
			.sound(SoundType.WOOD)));
	public static final RegistryObject<PressurePlateBlock> FROSTWOOD_PRESSURE_PLATE = BLOCKS.register("frostwood_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING,
			frostwoodTypeProperties(0.5F)
			.noCollission()));
	public static final RegistryObject<DoorBlock> FROSTWOOD_DOOR = BLOCKS.register("frostwood_door", () -> new DoorBlock(
			frostwoodTypeProperties(3.0F)
			.noOcclusion()));
	public static final RegistryObject<TrapDoorBlock> FROSTWOOD_TRAPDOOR = BLOCKS.register("frostwood_trapdoor", () -> new TrapDoorBlock(
			frostwoodTypeProperties(3.0F)
			.noOcclusion()
			.isValidSpawn(($0, $1, $2, $3) -> false)));
	public static final RegistryObject<FenceGateBlock> FROSTWOOD_FENCE_GATE = BLOCKS.register("frostwood_fence_gate", () -> new FenceGateBlock(frostwoodTypeProperties()));

	private static Properties frostwoodTypeProperties() {
		return frostwoodTypeProperties(2.0F, 3.0F);
	}

	private static Properties frostwoodTypeProperties(float strength) {
		return frostwoodTypeProperties(strength, strength);
	}

	private static Properties frostwoodTypeProperties(float destroyTime, float explosionResistance) {
		return frostwoodTypeProperties(FROSTWOOD_PLANKS_MATERIAL_COLOR, destroyTime, explosionResistance);
	}

	private static Properties frostwoodTypeProperties(MaterialColor color, float destroyTime, float explosionResistance) {
		return frostwoodTypeProperties(state -> color, destroyTime, explosionResistance);
	}

	private static Properties frostwoodTypeProperties(Function<BlockState, MaterialColor> colorFunction, float destroyTime, float explosionResistance) {
		return Properties.of(Material.WOOD, colorFunction).strength(destroyTime, explosionResistance).sound(SoundType.WOOD).friction(0.7F).speedFactor(1.1F);
	}

	private static RegistryObject<RotatedPillarBlock> getStrippedFrostwoodLog() {
		return STRIPPED_FROSTWOOD_LOG;
	}

	private static RegistryObject<RotatedPillarBlock> getStrippedFrostwood() {
		return STRIPPED_FROSTWOOD;
	}
}
