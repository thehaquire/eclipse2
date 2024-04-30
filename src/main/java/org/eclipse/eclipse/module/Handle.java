package org.eclipse.eclipse.module;

import org.eclipse.eclipse.module.Category;
import org.eclipse.eclipse.module.Module;

public class Handle {
    /**
     * Checks if a specific module within a category is enabled.
     *
     * @param categoryName The name of the category.
     * @param moduleName The name of the module to check.
     * @return true if the module is enabled, false otherwise.
     */
    public static boolean isModuleEnabled(String categoryName, String moduleName) {
        for (Category category : Category.values()) {
            if (category.getDisplayName().equalsIgnoreCase(categoryName)) {
                return category.getModules()
                        .filter(module -> module.getDisplayName().equalsIgnoreCase(moduleName))
                        .findFirst()
                        .map(module -> module.isEnabled().isOn())
                        .orElse(false);
            }
        }
        return false;
    }
}