package de.manu.fprework.handler;

import de.manu.fprework.FPRework;
import de.manu.fprework.models.database.*;
import de.manu.fprework.models.database.Character;
import de.manu.fprework.utils.javaef.Database;
import de.manu.fprework.utils.javaef.Entity;
import de.manu.fprework.utils.javaef.Table;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class DatabaseHandler {

    public static Database Database;

    // Tables
    public static List<Table<? extends Entity>> Tables;

    // Lists
    public static List<Account> Accounts;
    public static List<Character> Characters;
    public static List<CharacterLockedItem> CharacterLockedItems;
    public static List<ServerItem> ServerItems;
    public static List<de.manu.fprework.models.database.ServerItemStatsConsumable> ServerItemStatsConsumable;
    public static List<ServerItemStatsWeapon> ServerItemStatsWeapon;
    public static List<ServerSkill> ServerSkills;
    public static List<CharacterSkillBind> CharacterSkillBinds;
    public static List<ServerShop> ServerShops;
    public static List<ServerShopItem> ServerShopItems;

    @NotNull
    public static <T extends Entity> Table<T> table(Class<T> clazz) {
        return (Table<T>) Tables.stream()
                .filter(e -> e.mappedEntityClass.equals(clazz))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static void loadAll() {
        Database = new Database("localhost", "root", "", "fprework", 3306);

        Tables = Arrays.asList(
                new Table<>("accounts", Account.class),
                new Table<>("characters", Character.class),
                new Table<>("characters_lockeditems", CharacterLockedItem.class),
                new Table<>("server_items", ServerItem.class),
                new Table<>("server_items_stats_consumable", ServerItemStatsConsumable.class),
                new Table<>("server_items_stats_weapon", ServerItemStatsWeapon.class),
                new Table<>("server_skills", ServerSkill.class),
                new Table<>("character_skill_binds", CharacterSkillBind.class),
                new Table<>("server_shops", ServerShop.class),
                new Table<>("server_shop_items", ServerShopItem.class)
        );

        Accounts = table(Account.class).getAll();
        Characters = table(Character.class).getAll();
        CharacterLockedItems = table(CharacterLockedItem.class).getAll();
        ServerItems = table(ServerItem.class).getAll();
        ServerItemStatsConsumable = table(ServerItemStatsConsumable.class).getAll();
        ServerItemStatsWeapon = table(ServerItemStatsWeapon.class).getAll();
        ServerSkills = table(ServerSkill.class).getAll();
        CharacterSkillBinds = table(CharacterSkillBind.class).getAll();
        ServerShops = table(ServerShop.class).getAll();
        ServerShopItems = table(ServerShopItem.class).getAll();

        FPRework.print("§c[FP] DB-Load | Accounts: " + Accounts.size());
        FPRework.print("§c[FP] DB-Load | Characters: " + Characters.size());
        FPRework.print("§c[FP] DB-Load | CharacterLockedItems: " + CharacterLockedItems.size());
        FPRework.print("§c[FP] DB-Load | ServerItems: " + ServerItems.size());
        FPRework.print("§c[FP] DB-Load | ServerItemStatsConsumable: " + ServerItemStatsConsumable.size());
        FPRework.print("§c[FP] DB-Load | ServerItemStatsWeapon: " + ServerItemStatsWeapon.size());
        FPRework.print("§c[FP] DB-Load | ServerSkills: " + ServerSkills.size());
        FPRework.print("§c[FP] DB-Load | CharacterSkillBinds: " + CharacterSkillBinds.size());
        FPRework.print("§c[FP] DB-Load | ServerShops: " + ServerShops.size());
        FPRework.print("§c[FP] DB-Load | ServerShopItems: " + ServerShopItems.size());
    }

}
