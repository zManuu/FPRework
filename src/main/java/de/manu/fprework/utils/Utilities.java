package de.manu.fprework.utils;

import java.util.regex.Pattern;

public class Utilities {

    public static boolean isNum(String in) {
        var pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return in != null && pattern.matcher(in).matches();
    }

}
