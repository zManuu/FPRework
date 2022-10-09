package de.manu.fprework.utils.javaef;

import org.jetbrains.annotations.NotNull;

public interface EntityConsumer<T extends Entity> {

    boolean test(@NotNull T entity);

}
