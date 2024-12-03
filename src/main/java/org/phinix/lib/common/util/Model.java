package org.phinix.lib.common.util;

public interface Model {
    default String getModelName() {
        return this.getClass().getSimpleName().toLowerCase();
    }
}
