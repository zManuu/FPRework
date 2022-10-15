package de.manu.fprework.utils.javaef;

import org.jetbrains.annotations.Nullable;

public abstract class Entity {

    public abstract int getId();

    public abstract void setId(int id);

    public boolean equalsId(@Nullable Entity other) {
        return other != null && other.getId() == this.getId();
    }

}