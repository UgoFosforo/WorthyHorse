package me.architetto.worthyhorses.cmd.admin;

import me.architetto.worthyhorses.cmd.SubCommand;
import me.architetto.worthyhorses.util.PowerUpUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.*;

public class PowerupCmd extends SubCommand {
    @Override
    public String getName() {
        return "powerup";
    }

    @Override
    public String getDescription() {
        return "null";
    }

    @Override
    public String getSyntax() {
        return "/wh powerup <attribute_name> [qty]";
    }

    @Override
    public String getPermission() {
        return "worthyhorse.admin.powerup";
    }

    @Override
    public int getArgsRequired() {
        return 2;
    }

    private final HashMap<String, Attribute> attributeList = new HashMap<String, Attribute>() {{
        put("RANDOM",null);
        put("MAX_HEALTH",Attribute.GENERIC_MAX_HEALTH);
        put("MOV_SPEED",Attribute.GENERIC_MOVEMENT_SPEED);
        put("JUMP_STRENGTH",Attribute.HORSE_JUMP_STRENGTH);
    }};

    @Override
    public void perform(Player sender, String[] args) {

        int qty = 1;

        if (args.length == 3) {

            try {
                qty = Math.min(64,Integer.parseInt(args[2]));
            } catch (NumberFormatException ignored) {}
        }

        sender.getInventory().addItem(PowerUpUtil.getPowerUpItem(attributeList.get(args[1]),qty));

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2)
            return new ArrayList<>(attributeList.keySet());
        return null;
    }
}
