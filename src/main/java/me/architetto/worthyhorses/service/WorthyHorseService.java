package me.architetto.worthyhorses.service;

import me.architetto.worthyhorses.util.EggUtil;
import me.architetto.worthyhorses.util.HorseUtil;
import me.architetto.worthyhorses.util.PowerUpUtil;
import me.architetto.worthyhorses.util.value.LoreValue;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WorthyHorseService {

    private static WorthyHorseService instance;

    private final HashMap<UUID, WorthyHorseAction> horsePickerMap;

    private WorthyHorseService() {
        if (instance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");

        this.horsePickerMap = new HashMap<>();
    }

    public static WorthyHorseService getInstance() {
        if (instance == null)
            instance = new WorthyHorseService();
        return instance;
    }

    public boolean isHorsePicker(Player player) {
        return this.horsePickerMap.containsKey(player.getUniqueId());
    }

    public void addHorsePicker(Player player,WorthyHorseAction action) {

        if (this.horsePickerMap.putIfAbsent(player.getUniqueId(),action) != null)
            player.sendMessage("Warning : You are already picking!");
        else
            player.sendMessage("Pick a horse...");
        //todo: auto-remove after x seconds
    }

    public void horsePickerActionHandler(Player player,Horse horse) {

        switch (this.horsePickerMap.get(player.getUniqueId())) {
            case EGGIFY:
                actionEggify(horse, player);
                break;
            case TAME:
                actionTame(player,horse);
                break;
            case SEE_STATS:
                actionPrintStats(player,horse);
                break;
            default:
                return;
        }

        this.horsePickerMap.remove(player.getUniqueId());
    }

    private void actionTame(Player player, Horse  horse) {
        horse.setOwner(player);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
    }

    private void actionEggify(Horse  horse, Player player) {
        if (!horse.isTamed()) {
            player.sendMessage("Warning : You can't eggify untamed horse");
            return;
        }

        if (horse.getOwner() != null && horse.getOwner().getUniqueId() != player.getUniqueId()) {
            player.sendMessage("Warning : You are not the owner of this horse");
            return;
        }

        ItemStack itemStack = EggUtil.horseSpawnEgg(horse);
        horse.getWorld().dropItem(horse.getLocation(),itemStack);
        horse.remove();
    }

    private void actionPrintStats(Player player, Horse  horse) {
        player.sendMessage(EggUtil.formattedHorseLevel(horse));
        player.sendMessage(EggUtil.formattedHorseAttribute(horse, Attribute.GENERIC_MAX_HEALTH));
        player.sendMessage(EggUtil.formattedHorseAttribute(horse, Attribute.GENERIC_MOVEMENT_SPEED));
        player.sendMessage(EggUtil.formattedHorseAttribute(horse, Attribute.HORSE_JUMP_STRENGTH));
        horse.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,20,1,false,false,false));
    }

    public void releaseHorse(Player player, ItemStack itemStack, Location location) {
        List<Component> eggLore = itemStack.lore();

        assert eggLore != null;

        Horse horse = (Horse) location.getWorld()
                .spawnEntity(location, EntityType.HORSE, CreatureSpawnEvent.SpawnReason.COMMAND);

        horse.setOwner(player);

        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

        horse.setAware(false);

        HorseUtil.setFixedHorseLevel(horse, LoreValue.LEVEL.toInt(eggLore));

        String horseName = LoreValue.NAME.toString(eggLore);

        if (!horseName.equals("NONE"))
            horse.customName(Component.text(horseName));

        Objects.requireNonNull(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH))
                .setBaseValue(LoreValue.MAX_HEALTH.toDouble(eggLore));

        Objects.requireNonNull(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED))
                .setBaseValue(LoreValue.SPEED.toDouble(eggLore));

        Objects.requireNonNull(horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH))
                .setBaseValue(LoreValue.MAX_JUMP.toDouble(eggLore));

        String[] style = StringUtils.split(LoreValue.STYLE.toString(eggLore),",");

        horse.setColor(Horse.Color.valueOf(style[0]));

        horse.setStyle(Horse.Style.valueOf(style[1]));

        Material armorMaterial = Material.getMaterial(LoreValue.ARMOR.toString(eggLore));

        if (armorMaterial != null)
            horse.getInventory().setArmor(new ItemStack(armorMaterial));


    }

    public boolean horsePowerUp(Player player, Horse horse, ItemStack itemStack , boolean force) {
        if (horse.getOwner() == null
                || horse.getOwner().getUniqueId() != player.getUniqueId()) {
            player.sendMessage("Warning: This horse is not tamed or you are not its owner");
            return false;
        }

        if (HorseUtil.getFixedHorseLevel(horse) >= 10 && !force) {
            player.sendMessage("Warning: This horse has already reached the maximum level");
            return false;
        }

        Attribute attribute = PowerUpUtil.applyPowerUp(horse, Objects.requireNonNull(itemStack.lore()));

        player.sendMessage("Power Up applied for " + attribute.name());
        player.sendMessage("This horse has reached level " + HorseUtil.addFixedHorseLevel(horse));
        horse.getWorld().playSound(horse.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT,5,1);
       return true;
    }


}
