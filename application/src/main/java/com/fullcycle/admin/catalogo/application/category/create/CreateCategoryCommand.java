package com.fullcycle.admin.catalogo.application.category.create;

public record CreateCategoryCommand(
        String name,
        String description,
        boolean isActive
) {
    public static CreateCategoryCommand with(
            final String aName,
            final String aDescription,
            final Boolean isActive
    ) {
        return new CreateCategoryCommand(aName, aDescription, isActive);
    }
}
