package de.manu.fprework.handler;

import de.manu.fprework.FPRework;
import de.manu.fprework.models.database.CharacterSkillBind;
import de.manu.fprework.models.database.ServerSkill;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkillsHandler {

    @Nullable
    public static ServerSkill getSkill(int id) {
        return DatabaseHandler.ServerSkills.stream()
                .filter(e -> e.id == id)
                .findAny()
                .orElse(null);
    }

    @Nullable
    public static ServerSkill getSkill(@NotNull String name) {
        return DatabaseHandler.ServerSkills.stream()
                .filter(e -> e.name.equals(name))
                .findAny()
                .orElse(null);
    }

    public static void startSkill(@NotNull Player player, @NotNull ServerSkill skill) {
        skill.executor.accept(player);
    }

    @Nullable
    public static CharacterSkillBind getCharacterSkillBind(int charId, String bind) {
        return DatabaseHandler.CharacterSkillBinds.stream()
                .filter(e -> e.charId == charId && e.bind.equals(bind))
                .findAny()
                .orElse(null);
    }

    public static void init() {
        getSkill(1).setExecutor(player -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 1));
            player.setVelocity(player.getLocation().getDirection());
            FPRework.setTimeout(() -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 1));
            }, 10);
        });
    }

}
