package me.architetto.worthyhorses.listener;

import me.architetto.worthyhorses.service.WorthyHorseService;
import me.architetto.worthyhorses.util.EggUtil;
import me.architetto.worthyhorses.util.HorseUtil;
import me.architetto.worthyhorses.util.PowerUpUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class WorthyHorseListeners implements Listener {

    @EventHandler
    public void onHorseRightClick(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Horse))
            return;

        WorthyHorseService worthyHorseService = WorthyHorseService.getInstance();

        if (worthyHorseService.isHorsePicker(event.getPlayer())) {
            worthyHorseService.horsePickerActionHandler(event.getPlayer(), (Horse) event.getRightClicked());
            event.setCancelled(true);
            return;
        }

        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();

        if (PowerUpUtil.isWorthyHorsePowerUp(itemStack)) {
            if (!worthyHorseService.horsePowerUp(event.getPlayer(), (Horse) event.getRightClicked(),
                    event.getPlayer().getInventory().getItemInMainHand() , false))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWorthyHorseEggUse(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK
                && event.getItem() != null
                && EggUtil.isWorthyHorseEgg(event.getItem())) {
            WorthyHorseService.getInstance().releaseHorse(event.getPlayer(),event.getItem(),
                    Objects.requireNonNull(event.getClickedBlock()).getLocation().add(0,1,0));

            if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getItem().getAmount() - 1);

            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onBreeding(EntityBreedEvent event) {

        if ((event.getMother() instanceof Horse
                && HorseUtil.isWorthyHorse((Horse) event.getMother()))
                || (event.getFather() instanceof Horse
                && HorseUtil.isWorthyHorse((Horse) event.getFather()))) {

            if (event.getBreeder() instanceof Player)
                event.getBreeder().sendMessage("Warning : You can't breed these horses");

            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onBreeding(PrepareAnvilEvent event) {
        if (event.getResult() != null && EggUtil.isWorthyHorseEgg(event.getResult()))
            event.setResult(null);

    }

}
