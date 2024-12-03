package org.phinix.lib.common.util;

public interface Model {
    default String getDynamicModelName() {
        return this.getClass().getSimpleName().toLowerCase();
    }
    static String getModelName() {
        return Model.class.getSimpleName().toLowerCase();
    }
}
