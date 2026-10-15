package com.wynprice.secretrooms.server.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class TrueVisionGogglesHandler {

    private static final ResourceLocation END_CHEST_LOOT = ResourceLocation.parse("chests/end_city_treasure");

    public static final float GOGGLES_BREAK_CHANCE = 0.2F;

    public static void onLootTableLoad(LootTableLoadEvent event) {
        if(END_CHEST_LOOT.equals(event.getName())) {
            event.getTable().addPool(
                LootPool.lootPool().name("secretroomsmod:true_vision_goggles")
                    .add(LootItem.lootTableItem(SecretItems.TRUE_VISION_GOGGLES.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                    )
                .build());
        }
    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if(!event.getEntity().level().isClientSide && event.getEntity().tickCount % 20 == 0 && event.getEntity().getRandom().nextFloat() < GOGGLES_BREAK_CHANCE) {
            ItemStack stack = event.getEntity().getItemBySlot(EquipmentSlot.HEAD);
            if(stack.getItem() == SecretItems.TRUE_VISION_GOGGLES.get()) {
                stack.hurtAndBreak(1, event.getEntity(), EquipmentSlot.HEAD);
            }
        }
    }
}
