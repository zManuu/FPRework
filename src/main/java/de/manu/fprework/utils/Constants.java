package de.manu.fprework.utils;

import org.bukkit.*;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static String LINE_T = "§7-----------------------------";
    public static String LINE_B = "\n§7-----------------------------";

    public static String M_PREFIX = "§8[§5Fantasy§6Pixel§8] ";
    public static String M_INFO = M_PREFIX + "§7";
    public static String M_SUCCESS = M_PREFIX + "§a";
    public static String M_ERROR = M_PREFIX + "§c";
    public static String M_WARNING = M_PREFIX + "§e";
    public static String M_SYNTAX = M_WARNING + "Syntax: ";


    public static World WORLD = Bukkit.getWorld("Ephesus");
    public static Location FIRST_SPAWN = new Location(WORLD, 1246, 170, 1047);
    public static Location SPAWN = new Location(WORLD, 1275, 156, 1031);
    public static Location DARK = new Location(WORLD, 1275.5, 62, 1031.5);
    public static NamespacedKey KEY_ITEM_ID;
    public static NamespacedKey KEY_BOW_DAMAGE;
    public static List<String> INTERACT_DISALLOWED_BLOCKS = Arrays.asList(
            "CHEST", "BLAST_FURNACE", "BARREL", "STONECUTTER", "ANVIL", "CHIPPED_ANVIL", "DAMAGED_ANVIL", "LEGACY_ANVIL",
            "LEGACY_WORKBENCH", "LEVER", "SMOKER", "CRAFTING_TABLE", "LEGACY_CHEST", "TRAPPED_CHEST", "LEGACY_FURNACE",
            "LEGACY_BURNING_FURNACE", "FURNACE", "BREWING_STAND", "LEGACY_BREWING_STAND", "LEGACY_BREWING_STAND_ITEM",
            "LOOM", "LECTERN", "ITEM_FRAME", "SMITHING_TABLE", "CARTOGRAPHY_TABLE", "FLOWER_POT"
    );
    public static List<String> SKILL_BINDS = Arrays.asList("LLL", "LLR", "LRR", "RRR", "RRL", "RLL");

}
