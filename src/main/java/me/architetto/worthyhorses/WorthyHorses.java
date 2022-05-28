package me.architetto.worthyhorses;

import me.architetto.worthyhorses.cmd.CommandManager;
import me.architetto.worthyhorses.config.ConfigManager;
import me.architetto.worthyhorses.listener.WorthyHorseListeners;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class WorthyHorses extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        ConfigManager.getInstance().setPlugin(plugin);

        Objects.requireNonNull(getCommand("worthyhorse")).setExecutor(new CommandManager());

        getServer().getPluginManager().registerEvents(new WorthyHorseListeners(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
