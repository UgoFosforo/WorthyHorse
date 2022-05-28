package me.architetto.worthyhorses.util;

import me.architetto.worthyhorses.WorthyHorses;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Horse;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class HorseUtil {

    private static final NamespacedKey WORTHY_HORSE_KEY = new NamespacedKey(WorthyHorses.plugin, "WorthyHorse");

    public static String getHorseFixedCustomName(Horse horse) {
        return horse.customName() == null ? "NONE" : PlainTextComponentSerializer.plainText()
                .serialize(Objects.requireNonNull(horse.customName()));
    }

    public static String getFixedHorseAttributeValue(Horse horse, Attribute attribute) {
        return NumUtil.fixedValueToString(Objects.requireNonNull(horse.getAttribute(attribute)).getBaseValue());
    }

    public static String getFixedHorseArmor(Horse horse) {
        return horse.getInventory().getArmor() == null ? "NONE" : horse.getInventory().getArmor().getType().name();
    }

    public static String getFixedHorseLevelToString(Horse horse) {
        return horse.getPersistentDataContainer().getOrDefault(WORTHY_HORSE_KEY, PersistentDataType.INTEGER,0).toString();
    }

    public static int getFixedHorseLevel(Horse horse) {
        return horse.getPersistentDataContainer().getOrDefault(WORTHY_HORSE_KEY, PersistentDataType.INTEGER,0);
    }

    public static void setFixedHorseLevel(Horse horse, int level) {
        horse.getPersistentDataContainer().set(WORTHY_HORSE_KEY,PersistentDataType.INTEGER,level);
    }

    public static boolean isWorthyHorse(Horse horse) {
        return horse.getPersistentDataContainer().get(WORTHY_HORSE_KEY,PersistentDataType.INTEGER) != null;
    }

    public static int addFixedHorseLevel(Horse horse) {
        int level = getFixedHorseLevel(horse) + 1;
        setFixedHorseLevel(horse,level);
        return level;
    }

}
