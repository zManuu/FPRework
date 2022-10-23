package de.manu.fprework.handler;

import de.manu.fprework.FPRework;
import de.manu.fprework.models.database.CharacterSkillBind;
import de.manu.fprework.models.database.ServerSkill;
import de.manu.fprework.utils.Constants;
import de.manu.fprework.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
    public static CharacterSkillBind getCharacterSkillBind(int charId, @NotNull String bind) {
        return DatabaseHandler.CharacterSkillBinds.stream()
                .filter(e -> e.charId == charId && e.bind.equals(bind))
                .findAny()
                .orElse(null);
    }

    public static void init() {
        getSkill(1).setExecutor(player -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 1));
            player.setVelocity(player.getLocation().getDirection().multiply(1.25).setY(0.5));
            FPRework.setTimeout(() -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 1));
            }, 15);
        });
    }

    public static void openSkillBindMenu(@NotNull Player player) {
        var charId = CharacterHandler.getCharId(player);

        // Normal Items (Combos)
        var items = new InventoryHandler.CustomInventoryItem[9];
        for (var i=0; i<8; i++) {
            var bind = Constants.SKILL_BINDS.get(i);
            var currentBind = getCharacterSkillBind(charId, bind);
            var itemBuilder = new ItemBuilder(Material.FILLED_MAP);
            itemBuilder.displayname("§b" + bind);
            if (currentBind != null)
                itemBuilder.lore( "§8➥ §7Momentan: §b" + getSkill(currentBind.skillId).name);
            items[i] = new InventoryHandler.CustomInventoryItem(i, itemBuilder.build(),
                    () -> openSkillBindSubMenu(player, bind), true, true);
        }

        // Close Item
        items[8] = new InventoryHandler.CustomInventoryItem(8, Constants.CLOSE_ITEM, null, true, true);

        InventoryHandler.buildInventory(player, "§9Wähle eine Komibination", InventoryType.DROPPER, items);
    }

    private static void openSkillBindSubMenu(@NotNull Player player, @NotNull String bind) {
        var skills = DatabaseHandler.ServerSkills.toArray(ServerSkill[]::new);
        var items = new InventoryHandler.CustomInventoryItem[skills.length];
        for (var i=0; i<items.length; i++) {
            var skill = skills[i];
            var material = skill.bindMenuMaterial.isEmpty() ? Material.FILLED_MAP : Material.valueOf(skill.bindMenuMaterial);
            var itemBuilder = new ItemBuilder(material);
            itemBuilder.displayname("§b" + skill.name);
            itemBuilder.lore(" §8➥ §7Mindest-Level: §b" + skill.requiredLevel);
            itemBuilder.lore(" §8➥ §7Stamina: §b" + skill.price);
            itemBuilder.lore(" §8➥ §7Cooldown: §b" + skill.cooldown + "s");
            items[i] = new InventoryHandler.CustomInventoryItem(i, itemBuilder.build(),
                    () -> bindSkill(player, bind, skill.name), true, true);
        }
        InventoryHandler.buildInventory(player, "§9Wähle einen Skill", InventoryType.CHEST, items);
    }

    public static void bindSkill(@NotNull Player player, @NotNull String bind, @NotNull String skillName) {
        var charId = CharacterHandler.getCharId(player);
        var skill = SkillsHandler.getSkill(skillName);

        if (skill == null) {
            player.sendMessage(Constants.M_ERROR + "Skill wurde nicht gefunden!");
            return;
        }

        if (!Constants.SKILL_BINDS.contains(bind)) {
            player.sendMessage(Constants.M_ERROR + "Ungültiger Bind!");
            return;
        }

        var existingBind = SkillsHandler.getCharacterSkillBind(charId, bind);
        if (existingBind != null) {
            DatabaseHandler.CharacterSkillBinds.remove(existingBind);
            DatabaseHandler.table(CharacterSkillBind.class).remove(existingBind);
        }

        var skillBind = new CharacterSkillBind(charId, skill.id, bind);
        DatabaseHandler.CharacterSkillBinds.add(skillBind);
        DatabaseHandler.table(CharacterSkillBind.class).insert(skillBind);

        player.sendMessage(Constants.M_SUCCESS + "Der Skill " + skillName + " ist jetzt auf die Tastenkombination " + bind + " gespeichert!");
    }

}
