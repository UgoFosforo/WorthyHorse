package me.architetto.worthyhorses.cmd.user;

import me.architetto.worthyhorses.cmd.SubCommand;
import me.architetto.worthyhorses.service.WorthyHorseAction;
import me.architetto.worthyhorses.service.WorthyHorseService;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import java.util.List;

public class EggifyCmd extends SubCommand {
    @Override
    public String getName() {
        return "eggify";
    }

    @Override
    public String getDescription() {
        return "drop spawn egg of selected horse";
    }

    @Override
    public String getSyntax() {
        return "/wh eggify";
    }

    @Override
    public String getPermission() {
        return "worthyhorse.user.eggify";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {
        WorthyHorseService worthyHorseService = WorthyHorseService.getInstance();
        worthyHorseService.addHorsePicker(sender, WorthyHorseAction.EGGIFY);

        /*
        Entity entity = sender.getVehicle();
        if (entity instanceof Horse)
            worthyHorseService.horsePickerActionHandler(sender, (Horse) entity);
         */

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
