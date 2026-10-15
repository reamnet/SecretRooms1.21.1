package com.wynprice.secretrooms.server.data;

import com.wynprice.secretrooms.SecretRooms6;
import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.wynprice.secretrooms.server.data.SecretItemTags.*;

public class SecretItemTagsProvider extends ItemTagsProvider {

    public SecretItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookup, blockTags, SecretRooms6.MODID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider registries) {
        tag(EARTH_ITEM).add(Items.CLAY_BALL, Items.DIRT, Items.SAND);
        tag(SECRET_RECIPE_ITEMS).add(Items.ROTTEN_FLESH).addTags(ItemTags.WOOL);
        tag(CLEAR_GLASS).add(Items.GLASS);
    }
}
