package de.manu.fprework.handler;

import de.manu.fprework.FPRework;
import de.manu.fprework.models.database.ServerSkill;
import de.manu.fprework.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaminaHandler {

    private static class StaminaDataHolder {
        Player player;
        float stamina;
        Map<ServerSkill, Float> cooldown;
        public StaminaDataHolder(Player player) {
            this.player = player;
            this.stamina = Constants.MAX_STAMINA;
            this.cooldown = new HashMap<>();
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public float getStamina() {
            return stamina;
        }

        public void setStamina(float stamina) {
            this.stamina = stamina;
        }

        public Map<ServerSkill, Float> getCooldown() {
            return cooldown;
        }

        public void setCooldown(Map<ServerSkill, Float> cooldown) {
            this.cooldown = cooldown;
        }
    }

    private static List<StaminaDataHolder> dataList;

    public static void init() {
        dataList = new ArrayList<>();

        FPRework.setInterval(() -> {
            for (var player : Bukkit.getOnlinePlayers()) {
                var data = getStaminaData(player);
                if (data == null) {
                    data = new StaminaDataHolder(player);
                    dataList.add(data);
                }
                data.setStamina(data.getStamina() + 0.5f);
                if (data.getStamina() > Constants.MAX_STAMINA)
                    data.setStamina(Constants.MAX_STAMINA);
                ActionbarHandler.send(player, 1, "§8[§a" + CharacterHandler.getActionBarXpString(player)
                        + "§8] - [§9" + data.getStamina() + "✶§8]");
            }
        }, 10);
    }

    /**
     * Will probably not be null!
     * Players are checked every 10 ticks,
     * if the player doesn't have a StaminaDataHolder
     * at that point, one will be created!
     * Null-Check not necessary.
     */
    @Nullable
    public static StaminaDataHolder getStaminaData(@NotNull Player player) {
        return dataList.stream()
                .filter(e -> e.player.equals(player))
                .findAny()
                .orElse(null);
    }

    public static boolean doesPlayerHaveStamina(Player player, int stamina) {
        return getStaminaData(player).getStamina() >= stamina;
    }

    public static void removePlayerStamina(Player player, int stamina) {
        var obj = getStaminaData(player);
        obj.setStamina(obj.getStamina() - stamina);
    }

}
