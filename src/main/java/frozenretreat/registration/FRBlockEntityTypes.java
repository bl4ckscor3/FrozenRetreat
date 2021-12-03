package frozenretreat.registration;

import frozenretreat.FrozenRetreat;
import frozenretreat.block.entity.FrostwoodSignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FRBlockEntityTypes {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, FrozenRetreat.MODID);

	public static final RegistryObject<BlockEntityType<FrostwoodSignBlockEntity>> FROSTWOOD_SIGN = BLOCK_ENTITY_TYPES.register("frostwood_sign", () -> BlockEntityType.Builder.of(FrostwoodSignBlockEntity::new,
			FRBlocks.FROSTWOOD_SIGN.get(),
			FRBlocks.FROSTWOOD_WALL_SIGN.get()).build(null));

}
