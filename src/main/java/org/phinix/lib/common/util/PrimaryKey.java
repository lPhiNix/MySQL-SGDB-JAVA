package org.phinix.lib.common.util;

import java.lang.annotation.*;

/**
 * Annotation used to mark a field as the primary key in a model class.
 * <p>
 * This annotation can be applied to fields in a model class to indicate
 * that they represent the primary key of the entity.
 * It is retained at runtime, allowing reflection-based tools to access this information.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrimaryKey {}