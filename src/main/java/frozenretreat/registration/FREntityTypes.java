package frozenretreat.registration;

import frozenretreat.FrozenRetreat;
import frozenretreat.entity.FrostwoodBoat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FREntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, FrozenRetreat.MODID);

	public static final RegistryObject<EntityType<FrostwoodBoat>> FROSTWOOD_BOAT = ENTITY_TYPES.register("frostwood_boat", () -> EntityType.Builder.<FrostwoodBoat>of(FrostwoodBoat::new, MobCategory.MISC)
			.sized(1.375F, 0.5625F)
			.clientTrackingRange(10)
			.build("frostwood_boat"));
}
