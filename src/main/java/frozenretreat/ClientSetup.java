package frozenretreat;

import frozenretreat.registration.FRBlockEntityTypes;
import frozenretreat.registration.FRBlocks;
import frozenretreat.registration.FREntityTypes;
import frozenretreat.registration.FRWoodTypes;
import frozenretreat.renderer.FrozenBoatRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = FrozenRetreat.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
	@SubscribeEvent
	public static void onFMLClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> Sheets.addWoodType(FRWoodTypes.FROSTWOOD));
		ItemBlockRenderTypes.setRenderLayer(FRBlocks.WINTERBERRY_BUSH.get(), RenderType.cutout());
	}

	@SubscribeEvent
	public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(FREntityTypes.FROSTWOOD_BOAT.get(), FrozenBoatRenderer::new);
		event.registerBlockEntityRenderer(FRBlockEntityTypes.FROSTWOOD_SIGN.get(), SignRenderer::new);
	}
}
