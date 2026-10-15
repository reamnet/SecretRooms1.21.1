package com.wynprice.secretrooms.server.data;

import com.wynprice.secretrooms.server.blocks.SecretBlocks;
import com.wynprice.secretrooms.server.items.SecretItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SecretBlockLootTableProvider extends LootTableProvider {
    public SecretBlockLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, Set.of(), List.of(new SubProviderEntry(SecretRoomsBlockLootTables::new, LootContextParamSets.BLOCK)), lookup);
    }

    private static class SecretRoomsBlockLootTables extends BlockLootSubProvider {
        protected SecretRoomsBlockLootTables(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected void generate() {
            SecretBlocks.REGISTRY.getEntries().forEach(holder -> {
                var block = holder.get();
                if (block == SecretBlocks.SECRET_DUMMY_BLOCK.get()) return;
                if (block == SecretBlocks.SECRET_DOOR.get() || block == SecretBlocks.SECRET_IRON_DOOR.get()) {
                    add(block, createDoorTable(block));
                } else if (block == SecretBlocks.WALL_TORCH_LEVER.get()) {
                    dropOther(block, SecretItems.TORCH_LEVER.get());
                } else {
                    dropSelf(block);
                }
            });
        }

        @Override
        protected Iterable<net.minecraft.world.level.block.Block> getKnownBlocks() {
            return SecretBlocks.REGISTRY.getEntries().stream().<net.minecraft.world.level.block.Block>map(holder -> holder.get()).toList();
        }
    }
}
