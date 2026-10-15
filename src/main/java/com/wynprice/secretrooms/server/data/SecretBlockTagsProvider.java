package com.wynprice.secretrooms.server.data;

import com.wynprice.secretrooms.SecretRooms6;
import com.wynprice.secretrooms.server.blocks.SecretBlocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SecretBlockTagsProvider extends BlockTagsProvider {
    public SecretBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper existingFileHelper) {
        super(output, lookup, SecretRooms6.MODID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider registries) {
        tag(SecretBlockTags.ONE_WAY_GLASS_CULL)
            .add(SecretBlocks.ONE_WAY_GLASS.get())
            .addTags(Tags.Blocks.GLASS_BLOCKS);
    }
}
