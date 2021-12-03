package frozenretreat.registration;

import frozenretreat.FrozenRetreat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class FRTags {
	public static class Blocks {
		public static final IOptionalNamedTag<Block> FROSTWOOD_LOGS = tag("frostwood_logs");

		private static IOptionalNamedTag<Block> tag(String name) {
			return BlockTags.createOptional(new ResourceLocation(FrozenRetreat.MODID, name));
		}
	}

	public static class Items {
		public static final IOptionalNamedTag<Item> FROSTWOOD_LOGS = tag("frostwood_logs");

		private static IOptionalNamedTag<Item> tag(String name) {
			return ItemTags.createOptional(new ResourceLocation(FrozenRetreat.MODID, name));
		}
	}
}
