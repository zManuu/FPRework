package de.manu.fprework.listeners;

import com.google.gson.Gson;
import de.manu.fprework.FPRework;
import de.manu.fprework.handler.*;
import de.manu.fprework.models.database.ServerSkill;
import de.manu.fprework.utils.Constants;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillListener implements Listener {

    private static class Combo {
        public Player player;
        public long last;
        public String combo;
        public Combo(Player player, long last, String combo) {
            this.player = player;
            this.last = last;
            this.combo = combo;
        }
    }

    private final List<Combo> activeCombos = new ArrayList<>();

    @Nullable
    private Combo getCombo(Player player) {
        return activeCombos.stream()
                .filter(e -> e.player.equals(player))
                .findAny()
                .orElse(null);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var itemType = ItemHandler.getItemDataByPDC(event.getItem());

        if (itemType == null || itemType.type != 2) return;

        var combo = getCombo(player);
        var clicked = switch (event.getAction()) {
            case PHYSICAL -> "E";
            case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> "L";
            case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> "R";
        };

        if (combo == null) {
            combo = new Combo(player, System.currentTimeMillis(), "");
            activeCombos.add(combo);
        }

        combo.last = System.currentTimeMillis();
        combo.combo += clicked;

        var actionBarMsg = switch (combo.combo.length()) {
            case 1 -> "§8[§b§l" + combo.combo.toCharArray()[0] + "§8]";
            case 2 -> "§8[§b§l" + combo.combo.toCharArray()[0] + "§8] [§b§l" + combo.combo.toCharArray()[1] + "§8]";
            case 3 -> "§8[§b§l" + combo.combo.toCharArray()[0] + "§8] [§b§l" + combo.combo.toCharArray()[1] + "§8] [§b§l" + combo.combo.toCharArray()[2] + "§8]";
            default -> "§4ERROR";
        };
        ActionbarHandler.send(player, 2, actionBarMsg);

        if (combo.combo.length() == 3) {
            // Finished
            activeCombos.remove(combo);
            var bind = SkillsHandler.getCharacterSkillBind(CharacterHandler.getCharId(player), combo.combo);
            var skill = bind != null ? SkillsHandler.getSkill(bind.skillId) : null;
            if (bind == null || skill == null) {
                ActionbarHandler.send(player, 2, "§cKein Skill gespeichert...");
                return;
            }
            if (!SkillsHandler.doesPlayerHaveStamina(player, skill.price)) {
                ActionbarHandler.send(player, 2, "§cNicht genügend Stamina...");
                return;
            }
            if (SkillsHandler.isSkillInCooldown(player, skill)) {
                var remainingTime = SkillsHandler.getSkillCooldown(player, skill);
                ActionbarHandler.send(player, 2, "§cDer Skill ist noch im Cooldown! (" + remainingTime + ")");
                return;
            }
            SkillsHandler.removePlayerStamina(player, skill.price);
            ActionbarHandler.send(player, 2, "§a" + skill.name + "...");
            SkillsHandler.startSkill(player, skill);
        }
    }

    public SkillListener() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(FPRework.INSTANCE, () -> {
            var time = System.currentTimeMillis();
            activeCombos.removeIf(combo -> {
                var last = combo.last;
                var diff = time - last;
                if (diff > 2000) {
                    ActionbarHandler.send(combo.player, 2, "§cAbgebrochen...");
                    return true;
                }
                return false;
            });
        }, 0, 10);
    }

}
