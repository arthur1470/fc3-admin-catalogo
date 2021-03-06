package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        assertAll(() -> {
            assertNotNull(actualCategory);
            assertNotNull(actualCategory.getId());

            assertEquals(expectedName, actualCategory.getName());
            assertEquals(expectedDescription, actualCategory.getDescription());
            assertEquals(expectedIsActive, actualCategory.isActive());

            assertNotNull(actualCategory.getCreatedAt());
            assertNotNull(actualCategory.getUpdatedAt());
            assertNull(actualCategory.getDeletedAt());
        });
    }

    @Test
    void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(actualException.getErrors().size(), expectedErrorCount);
        assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
    }

    @Test
    void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedName = "    ";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(actualException.getErrors().size(), expectedErrorCount);
        assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
    }

    @Test
    void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedName = "Fi ";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(actualException.getErrors().size(), expectedErrorCount);
        assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
    }

    @Test
    void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedName = """
                Acima de tudo, ?? fundamental ressaltar que o in??cio da atividade geral de 
                forma????o de atitudes representa uma abertura para a melhoria das regras de 
                normativas. Percebemos, cada vez mais, que a necessidade de renova????o 
                processual garante a contribui????o de um grupo importante na determina????o do 
                sistema de forma????o de quadros que corresponde ??s necessidades. A pr??tica 
                cotidiana prova que o desafiador cen??rio globalizado assume importantes posi????es 
                no estabelecimento das dire????es preferenciais no sentido do progresso. 
                O que temos que ter sempre em mente ?? que o entendimento das metas propostas exige a 
                precis??o e a defini????o do sistema de participa????o geral.
                """;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(actualException.getErrors().size(), expectedErrorCount);
        assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
    }

    @Test
    void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldNotReceiveError() {
        final var expectedName = "filmes";
        final var expectedDescription = "    ";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        assertAll(() -> {
            assertNotNull(actualCategory);
            assertNotNull(actualCategory.getId());

            assertEquals(expectedName, actualCategory.getName());
            assertEquals(expectedDescription, actualCategory.getDescription());
            assertEquals(expectedIsActive, actualCategory.isActive());

            assertNotNull(actualCategory.getCreatedAt());
            assertNotNull(actualCategory.getUpdatedAt());
            assertNull(actualCategory.getDeletedAt());
        });
    }

    @Test
    void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldNotReceiveError() {
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        assertAll(() -> {
            assertNotNull(actualCategory);
            assertNotNull(actualCategory.getId());

            assertEquals(expectedName, actualCategory.getName());
            assertEquals(expectedDescription, actualCategory.getDescription());
            assertEquals(expectedIsActive, actualCategory.isActive());

            assertNotNull(actualCategory.getCreatedAt());
            assertNotNull(actualCategory.getUpdatedAt());
            assertNotNull(actualCategory.getDeletedAt());
        });
    }

    @Test
    void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated() throws InterruptedException {
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                true
        );

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertNull(aCategory.getDeletedAt());
        Assertions.assertTrue(aCategory.isActive());

        Thread.sleep(1);

        final var actualCategory = aCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertEquals(actualCategory.getCreatedAt(), createdAt);

        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    void givenAValidActiveCategory_whenCallActivate_thenReturnCategoryActivated() {
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(
                expectedName,
                expectedDescription,
                false
        );

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertNotNull(aCategory.getDeletedAt());
        Assertions.assertFalse(aCategory.isActive());

        final var actualCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertEquals(actualCategory.getCreatedAt(), createdAt);

        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() throws InterruptedException {
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(
                "Film",
                "A categoria",
                expectedIsActive
        );

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Thread.sleep(1);

        final var actualCategory = aCategory.update(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertEquals(actualCategory.getCreatedAt(), createdAt);

        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated() throws InterruptedException {
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory(
                "Film",
                "A categoria",
                true
        );

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNull(aCategory.getDeletedAt());
        Assertions.assertTrue(aCategory.isActive());

        Thread.sleep(1);

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertEquals(actualCategory.getCreatedAt(), createdAt);

        Assertions.assertNotNull(actualCategory.getDeletedAt());

        Assertions.assertFalse(actualCategory.isActive());
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    }

    @Test
    void givenAValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated() throws InterruptedException {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(
                "Filmes",
                "A categoria",
                expectedIsActive
        );

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Thread.sleep(1);

        final var actualCategory = aCategory.update(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        assertEquals(aCategory.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertEquals(actualCategory.getCreatedAt(), createdAt);

        Assertions.assertNull(actualCategory.getDeletedAt());

        Assertions.assertTrue(actualCategory.isActive());
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    }
}
