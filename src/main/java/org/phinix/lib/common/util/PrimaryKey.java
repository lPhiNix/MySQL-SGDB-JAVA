package org.phinix.lib.common.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark a field as the primary key in a model class.
 * <p>
 * This annotation can be applied to fields in a model class to indicate
 * that they represent the primary key of the entity.
 * It is retained at runtime, allowing reflection-based tools to access this information.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrimaryKey {}