package de.manu.fprework.utils.javaef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * When this Annotation is applied to an OBJECT!, it won't be recognized
 * by the JavaEF when writing / reading from the database.
 * This Object MUST NOT be present in the Constructor of the Entity-Class.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JavaEFIgnore {
}
