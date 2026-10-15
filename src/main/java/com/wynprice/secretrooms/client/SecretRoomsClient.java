package com.wynprice.secretrooms.client;

import com.wynprice.secretrooms.client.model.OneWayGlassModel;
import com.wynprice.secretrooms.client.model.quads.TrueVisionBakedQuad;
import com.wynprice.secretrooms.server.blocks.SecretBaseBlock;
import com.wynprice.secretrooms.server.blocks.SecretBlocks;
import com.wynprice.secretrooms.server.items.SecretItems;
import com.wynprice.secretrooms.server.items.SwitchProbe;
import com.wynprice.secretrooms.server.items.TrueVisionGogglesClientHandler;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;

/** Client registrations are kept out of the dedicated server's initialization path. */
public final class SecretRoomsClient {
    private SecretRoomsClient() {}

    public static void register(IEventBus bus) {
        bus.addListener(SecretModelHandler::onBlockColors);
        bus.addListener(SecretModelHandler::onModelBaked);
        bus.addListener(SecretModelHandler::onEntityModelRegistered);
        bus.addListener(OneWayGlassModel::onModelsReady);
        bus.addListener(TrueVisionBakedQuad::onTextureStitched);
        bus.addListener(SwitchProbe::onRegisterTooltipFactories);
        bus.addListener(SecretRoomsClient::registerExtensions);
        bus.addListener(SecretRoomsClient::registerReloads);
        NeoForge.EVENT_BUS.addListener(SwitchProbe::appendHover);
        NeoForge.EVENT_BUS.addListener(TrueVisionGogglesClientHandler::onClientWorldLoad);
        NeoForge.EVENT_BUS.addListener(TrueVisionGogglesClientHandler::onClientWorldTick);
    }

    private static void registerExtensions(RegisterClientExtensionsEvent event) {
        SecretBlocks.REGISTRY.getEntries().forEach(holder -> {
            if (holder.get() instanceof SecretBaseBlock block) {
                block.registerClientExtensions(extension -> event.registerBlock(extension, block));
            }
        });
        SecretItems.TRUE_VISION_GOGGLES.get().registerClientExtensions(extension ->
            event.registerItem(extension, SecretItems.TRUE_VISION_GOGGLES.get()));
    }

    private static void registerReloads(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((ResourceManagerReloadListener) manager -> SecretItems.TRUE_VISION_GOGGLES.get().refreshArmorModel());
    }
}
