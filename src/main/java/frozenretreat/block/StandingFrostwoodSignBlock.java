package frozenretreat.block;

import frozenretreat.block.entity.FrostwoodSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class StandingFrostwoodSignBlock extends StandingSignBlock {
	public StandingFrostwoodSignBlock(Properties properties, WoodType woodType) {
		super(properties, woodType);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new FrostwoodSignBlockEntity(pos, state);
	}
}
