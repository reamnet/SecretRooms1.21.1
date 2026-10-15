package com.wynprice.secretrooms.server.items;

import com.wynprice.secretrooms.client.model.TrueVisionGogglesModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TrueVisionGoggles extends ArmorItem {

    @OnlyIn(Dist.CLIENT)
    private TrueVisionGogglesModel model;

    public TrueVisionGoggles(Properties properties) {
        super(SecretItems.GOGGLES_MATERIAL, Type.HELMET, properties);
    }

    public void registerClientExtensions(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if(model == null) {
                    refreshArmorModel();
                }
                return model;
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    public void refreshArmorModel() {
        this.model = new TrueVisionGogglesModel(Minecraft.getInstance().getEntityModels().bakeLayer(TrueVisionGogglesModel.TRUE_VISION_GOGGLES_MODEL));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, net.minecraft.core.Holder<Enchantment> enchantment) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

}
