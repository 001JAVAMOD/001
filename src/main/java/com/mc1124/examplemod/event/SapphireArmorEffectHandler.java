package com.mc1124.examplemod.event;

import com.mc1124.examplemod.armor.SapphireArmor;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "examplemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SapphireArmorEffectHandler {
    
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player != null) {
            Player player = event.player;
            
            // 检查是否穿戴完整的红宝石盔甲
            if (isWearingFullRubyArmor(player)) {
                // 添加效果 - 这里以力量、生命恢复为例
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 2, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 5, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 100, 5, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 5, false, false));
            }
            
            // 检查是否穿戴部分红宝石盔甲（至少2件）
            if (getRubyArmorCount(player) >= 4) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 0, false, false));
            }
        }
    }
    
    // 检查是否穿戴完整的红宝石盔甲
    private static boolean isWearingFullRubyArmor(Player player) {
        return getRubyArmorCount(player) == 4;
    }
    
    // 计算穿戴的红宝石盔甲数量
    private static int getRubyArmorCount(Player player) {
        int count = 0;
        for (ItemStack armor : player.getArmorSlots()) {
            if (armor.getItem() == SapphireArmor.SAPPHIRE_HELMET.get() ||
                armor.getItem() == SapphireArmor.SAPPHIRE_CHESTPLATE.get() ||
                armor.getItem() == SapphireArmor.SAPPHIRE_LEGGINGS.get() ||
                armor.getItem() == SapphireArmor.SAPPHIRE_BOOTS.get()) {
                count++;
            }
        }
        return count;
    }
}