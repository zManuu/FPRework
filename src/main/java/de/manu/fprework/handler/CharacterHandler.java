package de.manu.fprework.handler;

import de.manu.fprework.models.database.Account;
import de.manu.fprework.models.database.Character;
import de.manu.fprework.utils.Constants;
import de.manu.fprework.utils.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CharacterHandler {

    @Nullable
    public static Character getSelectedCharacter(int accountId) {
        var selectedCharacterId = AccountHandler.getAccount(accountId).selectedChar;
        return DatabaseHandler.Characters.stream()
                .filter(e -> e.id == selectedCharacterId)
                .findAny()
                .orElse(null);
    }

    public static int getCharId(@NotNull Player player) {
        return AccountHandler.getAccount(player).selectedChar;
    }

    @Nullable
    public static Character getCharacter(int charId) {
        return DatabaseHandler.Characters.stream()
                .filter(e -> e.id == charId)
                .findAny()
                .orElse(null);
    }

    @Nullable
    public static Character getCharacter(@NotNull Account account) {
        return getCharacter(account.selectedChar);
    }

    @Nullable
    public static Character getCharacter(@NotNull Player player) {
        return getCharacter(getCharId(player));
    }

    public static void openSelectCharTypeMenu(@NotNull Player player) {
        player.setInvisible(true);
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(Constants.DARK);
        InventoryHandler.buildInventory(player, "§lWähle einen Charakter-Typen", InventoryType.HOPPER, () -> openSelectCharTypeMenu(player),
                new InventoryHandler.CustomInventoryItem(1, new ItemBuilder(Material.STICK, "§6§lMagier").build(), () -> createChar(player, 1), true, true),
                new InventoryHandler.CustomInventoryItem(2, new ItemBuilder(Material.BOW, "§6§lBogenschütze").build(), () -> createChar(player, 2), true, true),
                new InventoryHandler.CustomInventoryItem(3, new ItemBuilder(Material.IRON_SWORD, "§6§lKrieger").build(), () -> createChar(player, 3), true,true));
   }

   public static String getActionBarXpString(Player player) {
        var character = getCharacter(player);

        if (character == null)
            return "§4§lERROR";

        var level = character.level;
        var xp = character.xp;
        var xpNeeded = (level + 1) * 1000;

        return ((xp / xpNeeded) * 100) + "%";
   }

    private static void createChar(@NotNull Player player, int characterClass) {
        var account = AccountHandler.getAccount(player);
        var character = new Character(account.id, characterClass, 0, 0);
        DatabaseHandler.Characters.add(character);
        DatabaseHandler.table(Character.class).insert(character);
        account.setSelectedChar(character.id);
        DatabaseHandler.table(Account.class).save(account);
        player.sendMessage(Constants.M_SUCCESS + "Dein Charakter ist erstellt worden!");
        player.setInvisible(false);
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(Constants.FIRST_SPAWN);
        player.getInventory().clear();

        ItemHandler.setItem(player, "static_Questbook", 1, 7);
        ItemHandler.setItem(player, "static_Charactermanagement", 1, 8);
        ItemHandler.setItem(player, "food_Bread", 1, 1);
    }

}
