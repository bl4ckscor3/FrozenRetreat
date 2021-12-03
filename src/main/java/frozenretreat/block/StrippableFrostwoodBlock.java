package frozenretreat.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.RegistryObject;

public class StrippableFrostwoodBlock extends RotatedPillarBlock {
	private final RegistryObject<RotatedPillarBlock> strippedBlock;

	public StrippableFrostwoodBlock(Properties properties, RegistryObject<RotatedPillarBlock> strippedBlock) {
		super(properties);

		this.strippedBlock = strippedBlock;
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, Level level, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {
		if(stack.canPerformAction(toolAction) && ToolActions.AXE_STRIP.equals(toolAction))
			return strippedBlock.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));

		return null;
	}
}
