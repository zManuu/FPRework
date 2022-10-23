package de.manu.fprework.handler;

import de.manu.fprework.FPRework;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ActionbarHandler {

    private record DataHolder(@NotNull Player player, long send, int priority) {}

    private final static List<DataHolder> dataList = new ArrayList<>();

    @Nullable
    private static DataHolder get(@NotNull Player player) {
        return dataList.stream()
                .filter(e -> e.player().equals(player))
                .findAny()
                .orElse(null);
    }

    public static void send(@NotNull Player player, int priority, @NotNull String msg) {
        var current = get(player);
        if (current == null || current.priority <= priority) {
            if (current != null)
                dataList.remove(current);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
            var entry = new DataHolder(player, System.currentTimeMillis(), priority);
            dataList.add(entry);
        }
    }

    public static void init() {
        FPRework.setInterval(() -> {
            var systemTime = System.currentTimeMillis();
            dataList.removeIf(e -> {
                var sendTime = e.send();
                var diff = systemTime - sendTime;
                if (diff > 3000) {
                    send(e.player(), -1, "");
                    return true;
                } else
                    return false;
            });
        }, 11);
    }

}
