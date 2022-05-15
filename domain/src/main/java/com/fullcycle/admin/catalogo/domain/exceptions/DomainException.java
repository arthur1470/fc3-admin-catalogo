package com.fullcycle.admin.catalogo.domain.exceptions;

import java.util.List;
import com.fullcycle.admin.catalogo.domain.validation.Error;

public class DomainException extends NoStacktraceException {

    private final List<Error> errors;

    private DomainException(final List<Error> anErrors) {
        super("");
        this.errors = anErrors;
    }

    public static DomainException with(final Error anError) {
        return new DomainException(List.of(anError));
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException(anErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
