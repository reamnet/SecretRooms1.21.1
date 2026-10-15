package com.wynprice.secretrooms;

import com.wynprice.secretrooms.server.blocks.SecretBlocks;
import com.wynprice.secretrooms.server.data.SecretBlockTags;
import com.wynprice.secretrooms.server.data.SecretData;
import com.wynprice.secretrooms.server.items.SecretItems;
import com.wynprice.secretrooms.server.items.SwitchProbe;
import com.wynprice.secretrooms.server.tileentity.SecretChestTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.testframework.junit.EphemeralTestServerProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(EphemeralTestServerProvider.class)
class SecretRoomsPortTest {
    @Test
    void camouflagePreservesBlockPropertiesAndBlockEntityData(MinecraftServer server) {
        var state = Blocks.OAK_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Z);
        var tileData = new CompoundTag();
        tileData.putString("id", "minecraft:sign");
        var original = new SecretData(null);
        original.setBlockState(state);
        original.setTileEntityNBT(tileData);
        var restored = new SecretData(null);
        restored.readNBT(original.writeNBT(new CompoundTag()));
        assertEquals(state, restored.getBlockState());
        assertEquals(tileData, restored.getTileEntityNBT());
        assertNull(restored.getTileEntityCache(), "Probe data has no world in which to create a mirrored block entity");
    }

    @Test
    void camouflageRejectsRecursiveSecretBlockMirrors(MinecraftServer server) {
        var data = new SecretData(null);
        data.setBlockState(SecretBlocks.GHOST_BLOCK.get().defaultBlockState());
        assertEquals(Blocks.STONE.defaultBlockState(), data.getBlockState());
    }

    @Test
    void secretChestPersistsInventoryAndCamouflage(MinecraftServer server) {
        var chest = new SecretChestTileEntity(BlockPos.ZERO, SecretBlocks.SECRET_CHEST.get().defaultBlockState());
        chest.getData().setBlockState(Blocks.GOLD_BLOCK.defaultBlockState());
        chest.setItem(13, new ItemStack(Items.DIAMOND, 7));
        var saved = chest.saveWithFullMetadata(server.registryAccess());
        var restored = assertInstanceOf(SecretChestTileEntity.class,
            BlockEntity.loadStatic(BlockPos.ZERO, chest.getBlockState(), saved, server.registryAccess()));
        assertEquals(7, restored.getItem(13).getCount());
        assertTrue(restored.getItem(13).is(Items.DIAMOND));
        assertEquals(Blocks.GOLD_BLOCK.defaultBlockState(), restored.getData().getBlockState());
        assertEquals(2, restored.removeItem(13, 2).getCount());
        assertEquals(5, restored.getItem(13).getCount());
    }

    @Test
    void probeDataSurvivesItemComponentSerialization(MinecraftServer server) {
        var probe = new ItemStack(SecretItems.SWITCH_PROBE.get());
        var mirror = new SecretData(null);
        mirror.setBlockState(Blocks.COPPER_BLOCK.defaultBlockState());
        CustomData.update(DataComponents.CUSTOM_DATA, probe,
            tag -> tag.put(SwitchProbe.PROBE_HIT_DATA, mirror.writeNBT(new CompoundTag())));
        var ops = RegistryOps.create(NbtOps.INSTANCE, server.registryAccess());
        var saved = ItemStack.CODEC.encodeStart(ops, probe).getOrThrow();
        var restored = ItemStack.CODEC.parse(ops, saved).getOrThrow();
        assertEquals(SwitchProbe.getProbeData(probe), SwitchProbe.getProbeData(restored));
        assertTrue(SwitchProbe.getProbeData(new ItemStack(SecretItems.SWITCH_PROBE.get())).isEmpty());
    }

    @Test
    void migratedRecipesTagsAndLootTablesLoad(MinecraftServer server) {
        for (String recipe : new String[]{"camouflage_paste", "ghost_block", "secret_chest", "switch_probe", "one_way_glass_up"}) {
            assertTrue(server.getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(SecretRooms6.MODID, recipe)).isPresent(), recipe);
        }
        assertTrue(SecretBlocks.ONE_WAY_GLASS.get().builtInRegistryHolder().is(SecretBlockTags.ONE_WAY_GLASS_CULL));
        assertNotEquals(net.minecraft.world.level.storage.loot.LootTable.EMPTY,
            server.reloadableRegistries().getLootTable(SecretBlocks.SECRET_CHEST.get().getLootTable()));
        assertNotNull(server.reloadableRegistries().getLootTable(net.minecraft.world.level.storage.loot.BuiltInLootTables.END_CITY_TREASURE)
            .getPool("secretroomsmod:true_vision_goggles"));
    }

    @Test
    void gogglesRetainHelmetSlotAndDurability(MinecraftServer server) {
        var goggles = SecretItems.TRUE_VISION_GOGGLES.get();
        assertEquals(net.minecraft.world.entity.EquipmentSlot.HEAD, goggles.getEquipmentSlot());
        assertEquals(900, new ItemStack(goggles).getMaxDamage());
        assertFalse(goggles.isEnchantable(new ItemStack(goggles)));
    }
}
