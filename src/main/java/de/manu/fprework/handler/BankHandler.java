package de.manu.fprework.handler;

import de.manu.fprework.utils.Constants;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BankHandler {

    /**
     * Checks the Players Inventory for Emeralds (Money) and removed Emeralds if they were found.
     * Example: Player can't afford the payment -> msg will be sent to the Player and false is returned.
     * Example 2: Player can afford the payment -> price Emeralds will be removed from the Player and true is returned.
     * @param msg will be sent to the Player, when he can't afford the payment
     * @return whether the Player can afford the payment
     */
    public static boolean handlePayment(@NotNull Player player, int price, @NotNull String msg) {
        if (!ItemHandler.doesPlayerHaveItems(player, 3, price)) {
            player.sendMessage(msg);
            return false;
        }
        ItemHandler.removePlayerItems(player, 3, price);
        return true;
    }

    /**
     * Uses {@link BankHandler#handlePayment(Player, int, String)} with a pre-written msg:
     * [FantasyPixel] Du kannst dir das nicht leisten ($price).
     */
    public static boolean handlePayment(@NotNull Player player, int price) {
        return handlePayment(player, price,
                Constants.M_ERROR + "Du kannst dir das nicht leisten (%s).".formatted(price));
    }

}
