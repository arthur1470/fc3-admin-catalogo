package com.fullcycle.admin.catalogo.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UseCaseTest {

    @Test
    void testCreateUseCase() {
        Assertions.assertNotNull(new UseCase());
        Assertions.assertNotNull(new UseCase().execute());
    }
}
