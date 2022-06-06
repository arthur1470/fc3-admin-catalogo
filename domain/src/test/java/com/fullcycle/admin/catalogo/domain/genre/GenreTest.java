package com.fullcycle.admin.catalogo.domain.genre;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenreTest {

    @Test
    void givenValidParams_whenCallNewGenre_shouldInstantiateAGenre() {
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = 0;

        final var actualGenre = Genre.newGenre(expectedName, expectedIsActive);

        Assertions.assertNotNull(actualGenre);
        Assertions.assertNotNull(actualGenre.getId());
        assertEquals(expectedName, actualGenre.getName());
        assertEquals(expectedIsActive, actualGenre.isActive());
        assertEquals(expectedCategories, actualGenre.getCategories().size());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
    }

    @Test
    void givenInvalidNullName_whenCallNewGenreAndValidate_shouldReceiveAError() {
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualGenre = Genre.newGenre(null, expectedIsActive);

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> actualGenre.validate(new ThrowsValidationHandler())
        );

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    void givenInvalidEmptyName_whenCallNewGenreAndValidate_shouldReceiveAError() {
        final var expectedName = "     ";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualGenre = Genre.newGenre(expectedName, expectedIsActive);

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> actualGenre.validate(new ThrowsValidationHandler())
        );

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    void givenInvalidNameWithLengthGreaterThan255_whenCallNewGenreAndValidate_shouldReceiveAError() {
        final var expectedName = """
                Acima de tudo, é fundamental ressaltar que o início da atividade geral de 
                formação de atitudes representa uma abertura para a melhoria das regras de 
                normativas. Percebemos, cada vez mais, que a necessidade de renovação 
                processual garante a contribuição de um grupo importante na determinação do 
                sistema de formação de quadros que corresponde às necessidades. A prática 
                cotidiana prova que o desafiador cenário globalizado assume importantes posições 
                no estabelecimento das direções preferenciais no sentido do progresso. 
                O que temos que ter sempre em mente é que o entendimento das metas propostas exige a 
                precisão e a definição do sistema de participação geral.
                """;
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 1 and 255 characters";

        final var actualGenre = Genre.newGenre(expectedName, expectedIsActive);

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> actualGenre.validate(new ThrowsValidationHandler())
        );

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
}
