package frozenretreat.block.entity;

import frozenretreat.registration.FRBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FrostwoodSignBlockEntity extends SignBlockEntity {
	public FrostwoodSignBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return FRBlockEntityTypes.FROSTWOOD_SIGN.get();
	}
}
