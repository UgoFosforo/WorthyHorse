package me.architetto.worthyhorses.cmd.admin;

import me.architetto.worthyhorses.WorthyHorses;
import me.architetto.worthyhorses.cmd.SubCommand;
import me.architetto.worthyhorses.util.HorseUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class TestCmd extends SubCommand {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "null";
    }

    @Override
    public String getSyntax() {
        return "null";
    }

    @Override
    public String getPermission() {
        return "worthyhorse.admin.test";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {

        Entity entity = sender.getTargetEntity(20);

        if (!(entity instanceof Horse))
            return;

        Horse horse = (Horse) entity;

        horse.setAware(true);

        horse.setTarget(sender);



    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
