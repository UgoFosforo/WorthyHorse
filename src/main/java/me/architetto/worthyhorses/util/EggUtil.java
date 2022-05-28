package me.architetto.worthyhorses.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EggUtil {

    private static final Component eggName = Component.text("'WorthyHorse' Spawn Egg")
            .color(TextColor.color(255,127,80))
            .decorate(TextDecoration.BOLD);

    private static final Component eggLoreSignature = Component.text("This egg contains a 'WorthyHorse'!")
                .color(TextColor.color(59,172,182)).decorate(TextDecoration.ITALIC);

    private static final Enchantment eggLoreEnchant = Enchantment.DURABILITY;

    private static final Component loreLine = Component.text("|==========================|")
            .color(TextColor.fromCSSHexString("#008F7A"))
            .decorate(TextDecoration.BOLD);

    private static final HashMap<Attribute,String> attributeFixedName = new HashMap<Attribute,String>() {{
        put(Attribute.GENERIC_MAX_HEALTH,"MAX_HEALTH");
        put(Attribute.GENERIC_MOVEMENT_SPEED,"MOV_SPEED");
        put(Attribute.HORSE_JUMP_STRENGTH,"MAX_JUMP");

    }};

    private static Component formattedHorseCustomName(Horse horse) {
        return Component.text("NAME : [")
                .color(NamedTextColor.AQUA)
                .decoration(TextDecoration.BOLD,true)
                .append(Component.text(HorseUtil.getHorseFixedCustomName(horse))
                        .color(NamedTextColor.YELLOW)
                        .decorate(TextDecoration.ITALIC)
                        .decoration(TextDecoration.BOLD,false))
                .append(Component.text("]"));
    }

    public static Component formattedHorseLevel(Horse horse) {
        return Component.text("LEVEL : [")
                .color(NamedTextColor.AQUA)
                .decoration(TextDecoration.BOLD,true)
                .append(Component.text(HorseUtil.getFixedHorseLevelToString(horse))
                        .color(NamedTextColor.YELLOW)
                        .decorate(TextDecoration.ITALIC)
                        .decoration(TextDecoration.BOLD,false))
                .append(Component.text("]"));
    }

    public static Component formattedHorseAttribute(Horse horse, Attribute attribute) {
        return Component.text(attributeFixedName.getOrDefault(attribute,"ERR") + " : [")
                .color(NamedTextColor.AQUA)
                .decoration(TextDecoration.BOLD,true)
                .append(Component.text(HorseUtil.getFixedHorseAttributeValue(horse,attribute))
                        .color(NamedTextColor.YELLOW)
                        .decorate(TextDecoration.ITALIC)
                        .decoration(TextDecoration.BOLD,false))
                .append(Component.text("]"));
    }

    private static Component formattedHorseStyle(Horse horse) {
        return Component.text("STYLE : [")
                .color(NamedTextColor.AQUA)
                .decoration(TextDecoration.BOLD, true)
                .append(Component.text(horse.getColor().name() + "," + horse.getStyle().name())
                        .color(NamedTextColor.YELLOW)
                        .decorate(TextDecoration.ITALIC)
                        .decoration(TextDecoration.BOLD, false))
                .append(Component.text("]"));
    }

    private static Component formattedHorseArmor(Horse horse) {
        return Component.text("ARMOR : [")
                .color(NamedTextColor.AQUA)
                .decoration(TextDecoration.BOLD, true)
                .append(Component.text(HorseUtil.getFixedHorseArmor(horse))
                        .color(NamedTextColor.YELLOW)
                        .decorate(TextDecoration.ITALIC)
                        .decoration(TextDecoration.BOLD, false))
                .append(Component.text("]"));
    }



    public static ItemStack horseSpawnEgg(Horse horse) {
        ItemStack itemStack = new ItemStack(Material.HORSE_SPAWN_EGG);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(eggName);
        List<Component> eggLore = new ArrayList<>();

        eggLore.add(0,eggLoreSignature);
        eggLore.add(1,loreLine);
        eggLore.add(2,formattedHorseLevel(horse));
        eggLore.add(3,formattedHorseCustomName(horse));
        eggLore.add(4,loreLine);
        eggLore.add(5,formattedHorseAttribute(horse,Attribute.GENERIC_MAX_HEALTH));
        eggLore.add(6,formattedHorseAttribute(horse,Attribute.GENERIC_MOVEMENT_SPEED));
        eggLore.add(7,formattedHorseAttribute(horse,Attribute.HORSE_JUMP_STRENGTH));
        eggLore.add(8,loreLine);
        eggLore.add(9,formattedHorseStyle(horse));
        eggLore.add(10,formattedHorseArmor(horse));

        itemMeta.lore(eggLore);
        itemStack.setItemMeta(itemMeta);
        itemStack.addUnsafeEnchantment(eggLoreEnchant,1);
        itemStack.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        return itemStack;

    }

    public static boolean isWorthyHorseEgg(ItemStack itemStack) {
        return itemStack.getItemMeta().hasLore()
                && Objects.requireNonNull(itemStack.getItemMeta().lore()).get(0).asComponent().equals(eggLoreSignature);
    }


}
