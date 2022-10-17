package de.manu.fprework.utils.javaef;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final String host;
    private final String user;
    private final String password;
    private final String name;
    private final int port;

    public Database(@NotNull String host, @NotNull String user, @NotNull String password, @NotNull String name, int port) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.name = name;
        this.port = port;
    }

    @Nullable
    public Connection generateConnection() {
        try {
            var connectionString = "jdbc:mysql://" + host + ":" + port + "/" + name;
            return DriverManager.getConnection(connectionString, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
