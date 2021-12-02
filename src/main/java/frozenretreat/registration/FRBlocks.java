package frozenretreat.registration;

import frozenretreat.FrozenRetreat;
import frozenretreat.block.WinterberryBushBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FRBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FrozenRetreat.MODID);

	public static final RegistryObject<WinterberryBushBlock> WINTERBERRY_BUSH = BLOCKS.register("winterberry_bush", () -> new WinterberryBushBlock(BlockBehaviour.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)));
}
