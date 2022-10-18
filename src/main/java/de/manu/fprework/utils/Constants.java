package de.manu.fprework.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Constants {

    public static String LINE_T = "§7-----------------------------";
    public static String LINE_B = "\n§7-----------------------------";

    public static String M_PREFIX = "§8[§5Fantasy§6Pixel§8] ";
    public static String M_INFO = M_PREFIX + "§7";
    public static String M_SUCCESS = M_PREFIX + "§a";
    public static String M_ERROR = M_PREFIX + "§c";
    public static String M_WARNING = M_PREFIX + "§e";


    public static World WORLD = Bukkit.getWorld("Ephesus");
    public static Location FIRST_SPAWN = new Location(WORLD, 1246, 170, 1047);
    public static Location SPAWN = new Location(WORLD, 1275, 156, 1031);
    public static Location DARK = new Location(WORLD, 1275.5, 62, 1031.5);

}
