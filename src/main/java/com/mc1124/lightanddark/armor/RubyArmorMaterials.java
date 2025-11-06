package com.mc1124.lightanddark.armor;

import com.mc1124.lightanddark.LightAndDarkMod;
import com.mc1124.lightanddark.items.Items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class RubyArmorMaterials {
    public static final ArmorMaterial RUBY = new ArmorMaterial() {
        // 耐久乘数 - 比钻石更高
        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> 495;    // 头盔: 11 * 45
                case CHESTPLATE -> 720; // 胸甲: 16 * 45  
                case LEGGINGS -> 675;   // 护腿: 15 * 45
                case BOOTS -> 405;      // 靴子: 13 * 45
            };
        }

        // 防御值 - 自定义数值
        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> 4;      // 比钻石头盔高1点
                case CHESTPLATE -> 9;   // 比钻石胸甲高1点
                case LEGGINGS -> 7;     // 比钻石护腿高1点
                case BOOTS -> 4;        // 比钻石靴子高1点
            };
        }

        // 附魔能力 - 比钻石更高
        @Override
        public int getEnchantmentValue() {
            return 18; // 钻石是10
        }

        // 装备音效
        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_DIAMOND;
        }

        // 修复材料 - 使用红宝石
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Items.EXAMPLE_RUBY.get());
        }

        // 名称
        @Override
        public String getName() {
            return LightAndDarkMod.MOD_ID + ":ruby";
        }

        // 韧性 - 比钻石更高
        @Override
        public float getToughness() {
            return 3.0f; // 钻石是2.0
        }

        // 击退抗性 - 添加击退抗性
        @Override
        public float getKnockbackResistance() {
            return 0.1f; // 钻石是0.0
        }
    };
}
