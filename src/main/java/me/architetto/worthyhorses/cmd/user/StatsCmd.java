package me.architetto.worthyhorses.cmd.user;

import me.architetto.worthyhorses.cmd.SubCommand;
import me.architetto.worthyhorses.service.WorthyHorseAction;
import me.architetto.worthyhorses.service.WorthyHorseService;
import org.bukkit.entity.Player;

import java.util.List;

public class StatsCmd extends SubCommand {
    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "show horse statistics";
    }

    @Override
    public String getSyntax() {
        return "/wh stats";
    }

    @Override
    public String getPermission() {
        return "worthyhorse.user.stats";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {
        WorthyHorseService worthyHorseService = WorthyHorseService.getInstance();
        worthyHorseService.addHorsePicker(sender, WorthyHorseAction.SEE_STATS);

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
