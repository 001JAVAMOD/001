package com.mc1124.lightanddark.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomArmorItem extends ArmorItem {
    private final String tooltipKey;
    
    public CustomArmorItem(ArmorMaterial material, Type type, Properties properties, String tooltipKey) {
        super(material, type, properties);
        this.tooltipKey = tooltipKey;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, 
                               List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        
        // 从语言文件获取装备描述
        tooltip.add(Component.translatable(tooltipKey + ".description").withStyle(ChatFormatting.BLUE));
        
        // 添加套装效果标题
        tooltip.add(Component.translatable("tooltip.lightanddark.armor.set_bonus").withStyle(ChatFormatting.LIGHT_PURPLE));
        
        // 从语言文件获取套装效果列表
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("tooltip.lightanddark.armor.bonus_2").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable("tooltip.lightanddark.armor.bonus_3").withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.translatable("tooltip.lightanddark.armor.bonus_4").withStyle(ChatFormatting.GOLD));
    }
    @Override
    public boolean isDamageable(ItemStack stack) {
        return true; // 确保装备可损坏
    }
}
