package frozenretreat.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class WinterberriesItem extends ItemNameBlockItem {
	public WinterberriesItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		//makes eating berries take precedence over placing them if the player is sneaking
		if (ctx.getPlayer().isShiftKeyDown()) {
			InteractionResult eatResult = use(ctx.getLevel(), ctx.getPlayer(), ctx.getHand()).getResult();

			return eatResult == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : super.useOn(ctx);
		}

		return super.useOn(ctx);
	}
}
