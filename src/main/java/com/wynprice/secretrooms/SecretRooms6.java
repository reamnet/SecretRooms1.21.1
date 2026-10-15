package com.wynprice.secretrooms;

import com.wynprice.secretrooms.client.SecretRoomsClient;
import com.wynprice.secretrooms.server.blocks.SecretBaseBlock;
import com.wynprice.secretrooms.server.blocks.SecretBlocks;
import com.wynprice.secretrooms.server.data.*;
import com.wynprice.secretrooms.server.items.SecretItems;
import com.wynprice.secretrooms.server.items.TrueVisionGogglesHandler;
import com.wynprice.secretrooms.server.tileentity.SecretTileEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SecretRooms6.MODID)
public class SecretRooms6 {
    public static final String MODID = "secretroomsmod";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    static {
        TABS.register("secretrooms", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.secretroomsmod"))
            .icon(() -> new ItemStack(SecretItems.CAMOUFLAGE_PASTE.get()))
            .displayItems((parameters, output) -> SecretItems.REGISTRY.getEntries().forEach(item -> output.accept(item.get())))
            .build());
    }

    public SecretRooms6(IEventBus bus) {
        SecretBlocks.REGISTRY.register(bus);
        SecretItems.ARMOR_MATERIALS.register(bus);
        SecretItems.REGISTRY.register(bus);
        SecretTileEntities.REGISTRY.register(bus);
        TABS.register(bus);
        bus.addListener(this::gatherData);
        NeoForge.EVENT_BUS.addListener(this::modifyBreakSpeed);
        NeoForge.EVENT_BUS.addListener(TrueVisionGogglesHandler::onLootTableLoad);
        NeoForge.EVENT_BUS.addListener(TrueVisionGogglesHandler::onPlayerTick);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            SecretRoomsClient.register(bus);
        }
    }

    private boolean recurse;

    public void modifyBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (recurse) return;
        recurse = true;
        try {
            Player player = event.getEntity();
            event.getPosition().flatMap(pos -> SecretBaseBlock.getMirrorState(player.level(), pos)
                .map(mirror -> player.getDigSpeed(mirror, pos))).ifPresent(event::setNewSpeed);
        } finally {
            recurse = false;
        }
    }

    public void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var lookup = event.getLookupProvider();
        var helper = event.getExistingFileHelper();
        var blocks = new SecretBlockTagsProvider(output, lookup, helper);
        generator.addProvider(event.includeServer(), new SecretRecipeProvider(output, lookup));
        generator.addProvider(event.includeServer(), blocks);
        generator.addProvider(event.includeServer(), new SecretItemTagsProvider(output, lookup, blocks.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new SecretBlockLootTableProvider(output, lookup));
    }
}
