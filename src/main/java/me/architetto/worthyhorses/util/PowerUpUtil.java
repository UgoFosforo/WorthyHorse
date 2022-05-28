package me.architetto.worthyhorses.util;

import me.architetto.worthyhorses.util.value.LoreValue;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PowerUpUtil {

    private static final Component carrotName = Component.text("'WorthyHorse' PowerUp")
            .color(TextColor.color(255,127,80))
            .decorate(TextDecoration.BOLD);

    private static final Component carrotLoreSignature = Component.text("'WorthyHorse' PowerUp!")
            .color(TextColor.color(59,172,182)).decorate(TextDecoration.ITALIC);

    private static final Enchantment eggLoreEnchant = Enchantment.DURABILITY;

    private static final Component loreLine = Component.text("|==========================|")
            .color(TextColor.fromCSSHexString("#008F7A"))
            .decorate(TextDecoration.BOLD);

    private static final List<Attribute> attributeList = new ArrayList<Attribute>() {{
        add(Attribute.GENERIC_MAX_HEALTH);
        add(Attribute.GENERIC_MOVEMENT_SPEED);
        add(Attribute.HORSE_JUMP_STRENGTH);
    }};

    public static boolean isWorthyHorsePowerUp(ItemStack itemStack) {
        return itemStack != null
                && itemStack.getType() != Material.AIR
                && itemStack.getItemMeta().hasLore()
                && Objects.requireNonNull(itemStack.getItemMeta().lore()).get(0).asComponent().equals(carrotLoreSignature);
    }

    private static Component formattedAttributePowerUp(Attribute attribute) {
        return Component.text("ATTRIBUTE : [")
                .color(NamedTextColor.AQUA)
                .decoration(TextDecoration.BOLD,true)
                .append(Component.text(attribute.name())
                        .color(NamedTextColor.YELLOW)
                        .decorate(TextDecoration.ITALIC)
                        .decoration(TextDecoration.BOLD,false))
                .append(Component.text("]"));
    }

    public static ItemStack getPowerUpItem(@Nullable Attribute attribute, int qty) {

        ItemStack itemStack = new ItemStack(Material.GOLDEN_CARROT,qty);

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(carrotName);

        List<Component> carrotLore = new ArrayList<>();

        carrotLore.add(0,carrotLoreSignature);

        if (attribute != null) {
            carrotLore.add(1,loreLine);
            carrotLore.add(2,formattedAttributePowerUp(attribute));
        }

        itemMeta.lore(carrotLore);

        itemStack.setItemMeta(itemMeta);

        itemStack.addUnsafeEnchantment(eggLoreEnchant,1);

        itemStack.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        return itemStack;

    }

    private static Attribute getRandomAttribute() {
        Random rand = new Random();
        return attributeList.get(rand.nextInt(attributeList.size()));
    }

    public static Attribute applyPowerUp(Horse horse, List<Component> lore) {
        Attribute attribute;

        if (lore.size() == 3)
            attribute = Attribute.valueOf(LoreValue.POWERUP.toString(lore));
        else
            attribute = getRandomAttribute();

        double value = Objects.requireNonNull(horse.getAttribute(attribute)).getBaseValue();

        switch (attribute) {
            case GENERIC_MAX_HEALTH:
                value += 4;
                Objects.requireNonNull(horse.getAttribute(attribute)).setBaseValue(value);
                break;
            case HORSE_JUMP_STRENGTH:
                value += 0.03;
                Objects.requireNonNull(horse.getAttribute(attribute)).setBaseValue(value);
                break;
            case GENERIC_MOVEMENT_SPEED:
                value += 0.04;
                Objects.requireNonNull(horse.getAttribute(attribute)).setBaseValue(value);
                break;
        }
        return attribute;
    }
}
