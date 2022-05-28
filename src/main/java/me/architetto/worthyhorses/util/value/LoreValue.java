package me.architetto.worthyhorses.util.value;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.List;

public enum LoreValue {

    LEVEL (2),
    NAME (3),
    MAX_HEALTH (5),
    SPEED (6),
    MAX_JUMP (7),
    STYLE (9),
    ARMOR (10),
    POWERUP (2);

    private final int valueIndex;

    LoreValue(int i) {
        this.valueIndex = i;
    }

    private String isolated(List<Component> lore) {
        String string = PlainTextComponentSerializer.plainText().serialize(lore.get(valueIndex));
        int firstIndex = string.indexOf("[") + 1;
        int lastIndex = string.lastIndexOf("]");
        return firstIndex != lastIndex - 1 ?  string.substring(firstIndex, lastIndex) : String.valueOf(string.charAt(firstIndex));
    }

    public int index() {
        return valueIndex;
    }

    public int toInt(List<Component> lore) {
        return Integer.parseInt(isolated(lore));
    }

    public String toString(List<Component> lore) {
        return isolated(lore);
    }

    public double toDouble(List<Component> lore) {
        return Double.parseDouble(isolated(lore));
    }

    public Component toComponent(List<Component> lore) {
        return Component.text(isolated(lore));
    }


}
