package me.architetto.worthyhorses.cmd.admin;

import me.architetto.worthyhorses.cmd.SubCommand;
import me.architetto.worthyhorses.service.WorthyHorseAction;
import me.architetto.worthyhorses.service.WorthyHorseService;
import org.bukkit.entity.Player;

import java.util.List;

public class TameCmd extends SubCommand {
    @Override
    public String getName() {
        return "tame";
    }

    @Override
    public String getDescription() {
        return "instantly tame the selected horse";
    }

    @Override
    public String getSyntax() {
        return "/wh tame";
    }

    @Override
    public String getPermission() {
        return "worthyhorse.admin.tame";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {
        WorthyHorseService worthyHorseService = WorthyHorseService.getInstance();
        worthyHorseService.addHorsePicker(sender, WorthyHorseAction.TAME);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
